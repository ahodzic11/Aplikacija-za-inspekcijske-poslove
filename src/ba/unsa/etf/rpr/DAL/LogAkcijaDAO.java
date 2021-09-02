package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Log;
import ba.unsa.etf.rpr.Model.LogAkcije;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LogAkcijaDAO {
    private static LogAkcijaDAO instance = null;
    private static InspektorDAO inspektorDao = InspektorDAO.getInstance();
    private Connection conn;
    private PreparedStatement pretragaUpit, noviIdUpit, dodavanjeUpit, brisanjeUpit, obrisiUpit;

    private LogAkcijaDAO() throws SQLException {
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
            pretragaUpit = conn.prepareStatement("SELECT * FROM logoviAkcija");
        } catch (SQLException e) {
            kreirajBazu();
            pretragaUpit = conn.prepareStatement("SELECT * FROM logoviAkcija");
        }
        noviIdUpit = conn.prepareStatement("SELECT Max(id)+1 FROM logoviAkcija");
        dodavanjeUpit = conn.prepareStatement("INSERT INTO logoviAkcija VALUES(?, ?, ?, ?)");
        brisanjeUpit = conn.prepareStatement("DELETE FROM logoviAkcija");
    }

    public void obrisiLogove(){
        try{
            brisanjeUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodaj(LogAkcije l) throws SQLException {
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
        dodavanjeUpit.setString(2, l.getDatumVrijeme());
        dodavanjeUpit.setString(3, l.getAkcija());
        dodavanjeUpit.setString(4, l.getJedinstvenaSifra());
        dodavanjeUpit.execute();
    }

    private void kreirajBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("inspekcija.sql"));
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

    public static LogAkcijaDAO getInstance() throws SQLException {
        if(instance==null) instance = new LogAkcijaDAO();
        return instance;
    }

    public ArrayList<LogAkcije> dajSveLogove() {
        ArrayList<LogAkcije> rezultat = new ArrayList();
        try{
            ResultSet rs = pretragaUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new LogAkcije(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }
}
