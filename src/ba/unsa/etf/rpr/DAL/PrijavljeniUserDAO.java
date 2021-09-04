package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.PrijavljeniUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class PrijavljeniUserDAO {
    private static PrijavljeniUserDAO instance = null;
    private InspektorDAO inspektorDao = InspektorDAO.getInstance();
    private Connection conn;
    private PreparedStatement pretragaUpit, dodavanjeUpit, brisanjeUpit, prijavljenUpit, idUlogovanogInspektoraUpit,
            jedinstvenaSifraUpit;

    public PrijavljeniUserDAO() throws SQLException {
        String url = "jdbc:sqlite:inspection.db";
        try{
            if(inspektorDao.getConn() != null)
                conn = inspektorDao.getConn();
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            pretragaUpit = conn.prepareStatement("SELECT * FROM loginPodaci");
        } catch (SQLException e) {
            kreirajBazu();
            pretragaUpit = conn.prepareStatement("SELECT * FROM loginPodaci");
        }
        dodavanjeUpit = conn.prepareStatement("INSERT INTO loginPodaci VALUES(?, ?, ?, ?)");
        brisanjeUpit = conn.prepareStatement("DELETE FROM loginPodaci");
        prijavljenUpit = conn.prepareStatement("SELECT ostaniUlogovan FROM loginPodaci");
        idUlogovanogInspektoraUpit = conn.prepareStatement("SELECT idUsera FROM loginPodaci");
        jedinstvenaSifraUpit = conn.prepareStatement("SELECT jedinstvenaSifra FROM loginPodaci");
    }

    public void obrisiPrijavljenog(){
        try{
            brisanjeUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean dajUlogovanost(){
        try{
            ResultSet rs = prijavljenUpit.executeQuery();
            if(!rs.next()) return false;
            if(rs.getInt(1) == 1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void dodaj(PrijavljeniUser pu) throws SQLException {
        brisanjeUpit.execute();
        dodavanjeUpit.setInt(1, pu.getId());
        dodavanjeUpit.setString(2, pu.getDatumLogovanja());
        dodavanjeUpit.setInt(3, pu.getUlogovan());
        dodavanjeUpit.setString(4, pu.getJedinstvenaSifra());
        dodavanjeUpit.execute();
    }

    public static PrijavljeniUserDAO getInstance() throws SQLException {
        if(instance==null) instance = new PrijavljeniUserDAO();
        return instance;
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

    public int dajIdUlogovanogInspektora() {
        try{
            ResultSet rs = idUlogovanogInspektoraUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String dajJedinstvenuSifruUlogovanog() {
        try{
            ResultSet rs = jedinstvenaSifraUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
