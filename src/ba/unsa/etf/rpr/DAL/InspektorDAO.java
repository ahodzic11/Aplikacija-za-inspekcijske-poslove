package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Inspektor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InspektorDAO {
    private PreparedStatement pretragaUpit, noviIdUpit, dodavanjeUpit, sviInspektoriUpit, brisanjeUpit,
            izmjenaUpit, IDupit, imeUpit, prezimeUpit, datumRodjenjaUpit, jmbgUpit, spolUpit, brojLicneUpit,
            mjestoPrebivalistaUpit, telefonUpit, personalniMailUpit, pristupniMailUpit, pristupnaSifraUpit,
            vozackaUpit, jedinstvenaSifraUpit, idInspektoraZaEmailUpit, oblastInspekcijeUpit, tipInspektoraUpit, idZaJedinstvenuSifruUpit;
    private static InspektorDAO instance = null;
    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    private InspektorDAO(){
        String url = "jdbc:sqlite:inspection.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            pretragaUpit = conn.prepareStatement("SELECT * FROM inspektor");
        }catch (SQLException e){
            kreirajBazu();
            try {
                pretragaUpit = conn.prepareStatement("SELECT * FROM inspektor");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        try {
            noviIdUpit = conn.prepareStatement("SELECT Max(id)+1 FROM inspektor");
            dodavanjeUpit = conn.prepareStatement("INSERT INTO inspektor VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            sviInspektoriUpit = conn.prepareStatement("SELECT * FROM inspektor ORDER BY ime");
            brisanjeUpit = conn.prepareStatement("DELETE FROM inspektor WHERE id=?");
            izmjenaUpit = conn.prepareStatement("UPDATE inspektor SET ime=?, prezime=?, brojLicne=?, mjestoPrebivalista=?, kontaktTelefon=?, personalniEmail=?, pristupniEmail=?, pristupnaSifra=?, vozacka=?, tipInspektora=?, oblastInspektora=? WHERE id=?");
            IDupit = conn.prepareStatement("SELECT id FROM inspektor WHERE pristupniEmail=?");
            imeUpit = conn.prepareStatement("SELECT ime FROM inspektor WHERE id=?");
            prezimeUpit = conn.prepareStatement("SELECT prezime FROM inspektor WHERE id=?");
            datumRodjenjaUpit = conn.prepareStatement("SELECT datumRodjenja FROM inspektor WHERE id=?");
            jmbgUpit = conn.prepareStatement("SELECT jmbg FROM inspektor WHERE id=?");
            spolUpit = conn.prepareStatement("SELECT spol FROM inspektor WHERE id=?");
            brojLicneUpit = conn.prepareStatement("SELECT brojLicne FROM inspektor WHERE id=?");
            mjestoPrebivalistaUpit = conn.prepareStatement("SELECT mjestoPrebivalista FROM inspektor WHERE id=?");
            telefonUpit = conn.prepareStatement("SELECT kontaktTelefon FROM inspektor WHERE id=?");
            personalniMailUpit = conn.prepareStatement("SELECT personalniEmail FROM inspektor WHERE id=?");
            pristupniMailUpit = conn.prepareStatement("SELECT pristupniEmail FROM inspektor WHERE id=?");
            pristupnaSifraUpit = conn.prepareStatement("SELECT pristupnaSifra FROM inspektor WHERE id=?");
            vozackaUpit = conn.prepareStatement("SELECT vozacka FROM inspektor WHERE id=?");
            jedinstvenaSifraUpit = conn.prepareStatement("SELECT jedinstvenaSifra FROM inspektor WHERE id=?");
            idInspektoraZaEmailUpit = conn.prepareStatement("SELECT id FROM inspektor WHERE pristupniEmail=?");
            oblastInspekcijeUpit = conn.prepareStatement("SELECT oblastInspektora FROM inspektor WHERE id=?");
            tipInspektoraUpit = conn.prepareStatement("SELECT tipInspektora FROM inspektor WHERE id=?");
            idZaJedinstvenuSifruUpit = conn.prepareStatement("SELECT id FROM inspektor WHERE jedinstvenaSifra=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int dajIdZaJedinstvenuSifru(String jedinstvenaSifra){
        try{
            idZaJedinstvenuSifruUpit.setString(1, jedinstvenaSifra);
            ResultSet rs = idZaJedinstvenuSifruUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String dajImePrezimeInspektora(int idInspektora){
        return dajImeZaID(idInspektora) + " " + dajPrezimeZaID(idInspektora);
    }

    public String dajJedinstvenuSifruZaID(int id){
        try{
            jedinstvenaSifraUpit.setInt(1, id);
            ResultSet rs = jedinstvenaSifraUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int dajVozackuZaID(int id){
        try{
            vozackaUpit.setInt(1, id);
            ResultSet rs = vozackaUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String dajPristupnuSifruZaID(int id){
        try{
            pristupnaSifraUpit.setInt(1, id);
            ResultSet rs = pristupnaSifraUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajPristupniEmailZaID(int id){
        try{
            pristupniMailUpit.setInt(1, id);
            ResultSet rs = pristupniMailUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajPersonalniEmailZaID(int id){
        try{
            personalniMailUpit.setInt(1, id);
            ResultSet rs = personalniMailUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajTelefonZaID(int id){
        try{
            telefonUpit.setInt(1, id);
            ResultSet rs = telefonUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajMjestoPrebivalistaZaID(int id){
        try{
            mjestoPrebivalistaUpit.setInt(1, id);
            ResultSet rs = mjestoPrebivalistaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajBrojLicneZaID(int id){
        try{
            brojLicneUpit.setInt(1, id);
            ResultSet rs = brojLicneUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int dajSpolZaID(int id){
        try{
            spolUpit.setInt(1, id);
            ResultSet rs = spolUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String dajJMBGZaID(int id){
        try{
            jmbgUpit.setInt(1, id);
            ResultSet rs = jmbgUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajDatumRodjenjaZaID(int id){
        try{
            datumRodjenjaUpit.setInt(1, id);
            ResultSet rs = datumRodjenjaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajPrezimeZaID(int id){
        try{
            prezimeUpit.setInt(1, id);
            ResultSet rs = prezimeUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajImeZaID(int id){
        try{
            imeUpit.setInt(1, id);
            ResultSet rs = imeUpit.executeQuery();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int dajIDzaPristupniMail(String pristupniEmail){
        try{
            IDupit.setString(1, pristupniEmail);
            ResultSet rs = IDupit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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

    public void modifikuj(int id, String ime, String prezime, String brojLicne, String mjestoPrebivalista, String kontaktTelefon, String personalniEmail,
                          String pristupniEmail, String pristupnaSifra, int vozacka, String tipInspektora, String oblastInspektora){
        try{
            izmjenaUpit.setString(1, ime);
            izmjenaUpit.setString(2, prezime);
            izmjenaUpit.setString(3, brojLicne);
            izmjenaUpit.setString(4, mjestoPrebivalista);
            izmjenaUpit.setString(5, kontaktTelefon);
            izmjenaUpit.setString(6, personalniEmail);
            izmjenaUpit.setString(7, pristupniEmail);
            izmjenaUpit.setString(8, pristupnaSifra);
            izmjenaUpit.setInt(9, vozacka);
            izmjenaUpit.setString(10, tipInspektora);
            izmjenaUpit.setString(11, oblastInspektora);
            izmjenaUpit.setInt(12, id);
            izmjenaUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisi(Inspektor i){
        try{
            brisanjeUpit.setInt(1, i.getId());
            brisanjeUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodaj(Inspektor i) throws SQLException {
        try{
            ResultSet rs = noviIdUpit.executeQuery();
            if(rs.next())
                i.setId(rs.getInt(1));
            else
                i.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dodavanjeUpit.setInt(1, i.getId());
        dodavanjeUpit.setString(2, i.getIme());
        dodavanjeUpit.setString(3, i.getPrezime());
        dodavanjeUpit.setString(4, i.getDatumRodjenja());
        dodavanjeUpit.setString(5, i.getJmbg());
        dodavanjeUpit.setInt(6, i.getSpol());
        dodavanjeUpit.setString(7, i.getBrojLicne());
        dodavanjeUpit.setString(8, i.getMjestoPrebivalista());
        dodavanjeUpit.setString(9, i.getKontaktTelefon());
        dodavanjeUpit.setString(10, i.getPersonalniEmail());
        dodavanjeUpit.setString(11, i.getPristupniEmail());
        dodavanjeUpit.setString(12, i.getPristupnaSifra());
        dodavanjeUpit.setInt(13, i.getVozacka());
        dodavanjeUpit.setString(14, i.getJedinstvenaSifra());
        dodavanjeUpit.setString(15, i.getTipInspektora());
        dodavanjeUpit.setString(16, i.getOblastInspektora());
        dodavanjeUpit.execute();
    }

    public ArrayList<Inspektor> sviUpisaniInspektori(){
        ArrayList<Inspektor> rezultat = new ArrayList();
        try{
            ResultSet rs = sviInspektoriUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Inspektor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getInt(13),
                        rs.getString(14), rs.getString(15), rs.getString(16)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ObservableList<Inspektor> sviInspektori(){
        ObservableList<Inspektor> rezultat = FXCollections.observableArrayList();
        try{
            ResultSet rs = sviInspektoriUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Inspektor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getInt(13),
                        rs.getString(14), rs.getString(15), rs.getString(16)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public static InspektorDAO getInstance(){
        if(instance==null) instance = new InspektorDAO();
        return instance;
    }

    public int dajIdInspektoraZaEmail(String email) {
        try{
            idInspektoraZaEmailUpit.setString(1, email);
            ResultSet rs = idInspektoraZaEmailUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String dajTipInspektoraZaID(int idInspektora) {
        try{
            tipInspektoraUpit.setInt(1, idInspektora);
            ResultSet rs = tipInspektoraUpit.executeQuery();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajOblastInspekcijeZaID(int idInspektora) {
        try{
            oblastInspekcijeUpit.setInt(1, idInspektora);
            ResultSet rs = oblastInspekcijeUpit.executeQuery();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isGlavniInspektor(int idInspektora) {
        try{
            tipInspektoraUpit.setInt(1, idInspektora);
            ResultSet rs = tipInspektoraUpit.executeQuery();
            if(rs.getString(1).equals("Glavni federalni inspektor")) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
