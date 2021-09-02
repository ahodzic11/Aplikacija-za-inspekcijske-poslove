package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Vlasnik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class VlasnikDAO {
    private static VlasnikDAO instance = null;
    private static InspektorDAO inspektorDao = InspektorDAO.getInstance();
    private Connection conn;
    private PreparedStatement pretragaUpit, noviIdUpit, dodavanjeUpit, sviVlasniciUpit, brisanjeVlasnikaUpit, dajImeVlasnika,
    dajPrezimeVlasika;

    private VlasnikDAO() throws SQLException {
        String url = "jdbc:sqlite:inspekcija.db";
        try {
            if (inspektorDao.getConn() != null)
                conn = inspektorDao.getConn();
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            pretragaUpit = conn.prepareStatement("SELECT * FROM vlasnik");
        } catch (SQLException e) {
            kreirajBazu();
            pretragaUpit = conn.prepareStatement("SELECT * FROM vlasnik");
        }
        noviIdUpit = conn.prepareStatement("SELECT Max(id)+1 FROM vlasnik");
        dodavanjeUpit = conn.prepareStatement("INSERT INTO vlasnik VALUES(?, ?, ?, ?, ?, ?)");
        sviVlasniciUpit = conn.prepareStatement("SELECT * FROM vlasnik");
        brisanjeVlasnikaUpit = conn.prepareStatement("DELETE FROM vlasnik WHERE id=?");
        dajImeVlasnika = conn.prepareStatement("SELECT ime FROM vlasnik WHERE id=?");
        dajPrezimeVlasika = conn.prepareStatement("SELECT prezime FROM vlasnik WHERE id=?");
    }

    public ObservableList<Vlasnik> sviVlasnici(){
        ObservableList<Vlasnik> rezultat = FXCollections.observableArrayList();
        try{
            ResultSet rs = sviVlasniciUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Vlasnik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodaj(Vlasnik v) throws SQLException {
        try{
            ResultSet rs = noviIdUpit.executeQuery();
            if(rs.next())
                v.setId(rs.getInt(1));
            else
                v.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dodavanjeUpit.setInt(1, v.getId());
        dodavanjeUpit.setString(2, v.getIme());
        dodavanjeUpit.setString(3, v.getPrezime());
        dodavanjeUpit.setString(4, v.getJmbg());
        dodavanjeUpit.setInt(5, v.getTelefon());
        dodavanjeUpit.setString(6, v.getEmail());
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

    public static VlasnikDAO getInstance() throws SQLException {
        if(instance==null) instance = new VlasnikDAO();
        return instance;
    }

    public void obrisiVlasnika(int idTrenutnogVlasnika) {
        try{
            brisanjeVlasnikaUpit.setInt(1, idTrenutnogVlasnika);
            brisanjeVlasnikaUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String dajPodatkeVlasnikaZaId(int idTrenutnogVlasnika) {
        String imePrezime = "";
        try{
            dajImeVlasnika.setInt(1, idTrenutnogVlasnika);
            ResultSet rs = dajImeVlasnika.executeQuery();
            rs.next();
            imePrezime += rs.getString(1);
            dajPrezimeVlasika.setInt(1, idTrenutnogVlasnika);
            ResultSet rs2 = dajPrezimeVlasika.executeQuery();
            rs2.next();
            imePrezime += " " + rs2.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imePrezime;
    }
}
