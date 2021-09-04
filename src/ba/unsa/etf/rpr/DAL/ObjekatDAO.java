package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Objekat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ObjekatDAO {
    private static ObjekatDAO instance = null;
    private static InspektorDAO inspektorDao = InspektorDAO.getInstance();
    private Connection conn;
    private PreparedStatement pretragaUpit, noviIdUpit, dodavanjeUpit, sviObjektiUpit, sviObjektiVlasnikaUpit,
    obrisiObjekatUpit, dajNazivObjektaUpit, dajAdresuObjektaUpit, dajObjekatUpit, dajOpisTerenaUpit, dajVlasnikaUpit,
    obrisiObjekteVlasnikaUpit, vrstaObjektaUpit, izmjenaUpit;

    private ObjekatDAO() throws SQLException {
        String url = "jdbc:sqlite:inspection.db";
        try {
            if(inspektorDao.getConn()!=null)
                conn = inspektorDao.getConn();
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            pretragaUpit = conn.prepareStatement("SELECT * FROM objekat");
        } catch (SQLException e) {
            kreirajBazu();
            pretragaUpit = conn.prepareStatement("SELECT * FROM objekat");
        }
        noviIdUpit = conn.prepareStatement("SELECT Max(id)+1 FROM objekat");
        dodavanjeUpit = conn.prepareStatement("INSERT INTO objekat VALUES(?, ?, ?, ?, ?)");
        sviObjektiUpit = conn.prepareStatement("SELECT * FROM objekat");
        sviObjektiVlasnikaUpit = conn.prepareStatement("SELECT * FROM objekat WHERE vlasnikId=?");
        obrisiObjekatUpit = conn.prepareStatement("DELETE FROM objekat WHERE id=?");
        dajNazivObjektaUpit = conn.prepareStatement("SELECT nazivObjekta FROM objekat WHERE id=?");
        dajAdresuObjektaUpit = conn.prepareStatement("SELECT adresa FROM objekat WHERE id=?");
        dajObjekatUpit = conn.prepareStatement("SELECT * FROM objekat WHERE id=?");
        dajVlasnikaUpit = conn.prepareStatement("SELECT vlasnikId FROM objekat WHERE id=?");
        obrisiObjekteVlasnikaUpit = conn.prepareStatement("DELETE FROM objekat WHERE vlasnikId=?");
        vrstaObjektaUpit = conn.prepareStatement("SELECT vrstaObjekta FROM objekat WHERE id=?");
        izmjenaUpit = conn.prepareStatement("UPDATE objekat SET vlasnikId=?, nazivObjekta=?, adresa=?, vrstaObjekta=? WHERE id=?");
    }

    public void modifikuj(int id, int vlasnikId, String nazivObjekta, String adresa, String vrsta){
        try{
            izmjenaUpit.setInt(1, vlasnikId);
            izmjenaUpit.setString(2, nazivObjekta);
            izmjenaUpit.setString(3, adresa);
            izmjenaUpit.setString(4, vrsta);
            izmjenaUpit.setInt(5, id);
            izmjenaUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Objekat> sviObjekti(){
        ObservableList<Objekat> rezultat = FXCollections.observableArrayList();
        try{
            ResultSet rs = sviObjektiUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Objekat(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public static ObjekatDAO getInstance() throws SQLException {
        if(instance==null) instance = new ObjekatDAO();
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

    public void addObject(Objekat o) throws SQLException {
        try{
            ResultSet rs = noviIdUpit.executeQuery();
            if(rs.next())
                o.setId(rs.getInt(1));
            else
                o.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dodavanjeUpit.setInt(1, o.getId());
        dodavanjeUpit.setInt(2, o.getVlasnikId());
        dodavanjeUpit.setString(3, o.getNazivObjekta());
        dodavanjeUpit.setString(4, o.getAdresa());
        dodavanjeUpit.setString(5, o.getVrstaObjekta());
        dodavanjeUpit.execute();
    }

    public ObservableList<Objekat> sviObjektiZaVlasnika(int idTrenutnogVlasnika) {
        ObservableList<Objekat> rezultat = FXCollections.observableArrayList();
        try{
            sviObjektiVlasnikaUpit.setInt(1, idTrenutnogVlasnika);
            ResultSet rs = sviObjektiVlasnikaUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Objekat(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void obrisiSaIDem(int idTrenutnogObjekta) {
        try{
            obrisiObjekatUpit.setInt(1, idTrenutnogObjekta);
            obrisiObjekatUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String dajAdresuObjektaZaID(int idObjekta) {
        try{
            dajAdresuObjektaUpit.setInt(1, idObjekta);
            ResultSet rs = dajAdresuObjektaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajNazivObjektaZaID(int idObjekta){
        try{
            dajNazivObjektaUpit.setInt(1, idObjekta);
            ResultSet rs = dajNazivObjektaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajVrstuObjektaZaID(int idObjekta){
        try{
            vrstaObjektaUpit.setInt(1, idObjekta);
            ResultSet rs = vrstaObjektaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int dajVlasnikaZaID(int idObjekta) {
        try{
            dajVlasnikaUpit.setInt(1, idObjekta);
            ResultSet rs = dajVlasnikaUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void obrisiObjekteVlasnika(int idVlasnika) {
        try{
            obrisiObjekteVlasnikaUpit.setInt(1, idVlasnika);
            obrisiObjekteVlasnikaUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
