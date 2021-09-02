package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Inspektor;
import ba.unsa.etf.rpr.Model.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdministratorDAO {
    private static AdministratorDAO instance = null;
    private static InspektorDAO inspektorDao = InspektorDAO.getInstance();
    private Connection conn;
    private PreparedStatement pretragaUpit, noviIdUpit, dodavanjeUpit, brisanjeUpit, jedinstvenaSifraUpit, emailUpit,
            sifraUpit, modifikujUpit, idUpit;

    private AdministratorDAO() throws SQLException {
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
            pretragaUpit = conn.prepareStatement("SELECT * FROM administrator");
        } catch (SQLException e) {
            kreirajBazu();
            pretragaUpit = conn.prepareStatement("SELECT * FROM administrator");
        }
        noviIdUpit = conn.prepareStatement("SELECT Max(id)+1 FROM administrator");
        dodavanjeUpit = conn.prepareStatement("INSERT INTO administrator VALUES(?, ?, ?, ?)");
        brisanjeUpit = conn.prepareStatement("DELETE FROM administrator WHERE id=?");
        jedinstvenaSifraUpit = conn.prepareStatement("SELECT jedinstvenaSifra FROM administrator WHERE email=?");
        emailUpit = conn.prepareStatement("SELECT email FROM administrator WHERE jedinstvenaSifra=?");
        sifraUpit = conn.prepareStatement("SELECT sifra FROM administrator WHERE jedinstvenaSifra=?");
        modifikujUpit = conn.prepareStatement("UPDATE administrator SET email=?, sifra=?, jedinstvenaSifra=? WHERE id=?");
        idUpit = conn.prepareStatement("SELECT id FROM administrator WHERE jedinstvenaSifra=?");
    }

    public int dajIDZaJedinstvenuSifru(String jedinstvenaSifra) {
        try{
            idUpit.setString(1, jedinstvenaSifra);
            ResultSet rs = idUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -2;
    }

    public void modifikuj(int id, String email, String sifra, String jedinstvenaSifra){
        try{
            modifikujUpit.setString(1, email);
            modifikujUpit.setString(2, sifra);
            modifikujUpit.setString(3, jedinstvenaSifra);
            modifikujUpit.setInt(4, id);
            modifikujUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static AdministratorDAO getInstance() throws SQLException {
        if(instance==null) instance = new AdministratorDAO();
        return instance;
    }

    public void dodaj(Administrator a) throws SQLException {
        try{
            ResultSet rs = noviIdUpit.executeQuery();
            if(rs.next())
                a.setId(rs.getInt(1));
            else
                a.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dodavanjeUpit.setInt(1, a.getId());
        dodavanjeUpit.setString(2, a.getEmail());
        dodavanjeUpit.setString(3, a.getSifra());
        dodavanjeUpit.setString(4, a.getJedinstvenaSifra());
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

    public String dajJedinstvenuSifruZaEmail(String email) {
        try{
            jedinstvenaSifraUpit.setString(1, email);
            ResultSet rs = jedinstvenaSifraUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajEmailZaJedinstvenuSifru(String jedinstvenaSifra) {
        try{
            emailUpit.setString(1, jedinstvenaSifra);
            ResultSet rs = emailUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajSifruZaJedinstvenuSifru(String jedinstvenaSifra) {
        try{
            sifraUpit.setString(1, jedinstvenaSifra);
            ResultSet rs = sifraUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<Administrator> dajSveAdministratore() {
        ArrayList<Administrator> rezultat = new ArrayList();
        try{
            ResultSet rs = pretragaUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Administrator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }
}
