package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Log;
import ba.unsa.etf.rpr.Model.Svjedok;
import ba.unsa.etf.rpr.Model.Termin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class LogDAO {
    private static LogDAO instance = null;
    private static InspektorDAO inspektorDao = InspektorDAO.getInstance();
    private Connection conn;
    private PreparedStatement pretragaUpit, noviIdUpit, dodavanjeUpit, brisanjeUpit, odjaviUpit, obrisiUpit;

    private LogDAO() throws SQLException {
        String url = "jdbc:sqlite:inspekcija.db";
        try{
            if(inspektorDao.getConn()!=null)
                conn = inspektorDao.getConn();
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            pretragaUpit = conn.prepareStatement("SELECT * FROM logovi");
        } catch (SQLException e) {
            kreirajBazu();
            pretragaUpit = conn.prepareStatement("SELECT * FROM logovi");
        }
        noviIdUpit = conn.prepareStatement("SELECT Max(id)+1 FROM logovi");
        dodavanjeUpit = conn.prepareStatement("INSERT INTO logovi VALUES(?, ?, ?, ?)");
        brisanjeUpit = conn.prepareStatement("DELETE FROM logovi WHERE id=?");
        odjaviUpit = conn.prepareStatement("UPDATE logovi SET datumVrijemeOdjave=? WHERE jedinstvenaSifra=? AND datumVrijemeOdjave LIKE ''");
        obrisiUpit = conn.prepareStatement("DELETE FROM logovi");
    }

    public void dodaj(Log l) throws SQLException {
        try{
            ResultSet rs = noviIdUpit.executeQuery();
            if(rs.next())
                l.setId(rs.getInt(1));
            else
                l.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dodavanjeUpit.setInt(1, l.getId());
        dodavanjeUpit.setString(2, l.getPrijava());
        dodavanjeUpit.setString(3, l.getOdjava());
        dodavanjeUpit.setString(4, l.getJedinstvenaSifra());
        dodavanjeUpit.execute();
    }

    private void kreirajBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("inspection.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka... nastavljam sa praznom bazom");
        }
    }

    public static LogDAO getInstance() throws SQLException {
        if(instance==null) instance = new LogDAO();
        return instance;
    }

    public void logout(String jedinstvenaSifra) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try{
            odjaviUpit.setString(1, LocalDateTime.now().format(formatter));
            odjaviUpit.setString(2, jedinstvenaSifra);
            odjaviUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Log> dajSveLogove() {
        ArrayList<Log> rezultat = new ArrayList();
        try{
            ResultSet rs = pretragaUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Log(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void obrisiSveLogove() {
        try{
            obrisiUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
