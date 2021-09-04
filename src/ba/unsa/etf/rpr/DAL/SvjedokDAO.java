package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.DAL.done.InspectorDAO;
import ba.unsa.etf.rpr.Model.Svjedok;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SvjedokDAO {
    private static SvjedokDAO instance = null;
    private static InspectorDAO inspektorDao = InspectorDAO.getInstance();
    private Connection conn;
    private PreparedStatement pretragaUpit, noviIdUpit, dodavanjeUpit, sviSvjedociUpit, obrisiSveSvjedokeUpit,
            dajImeSvjedokaUpit, dajPrezimeSvjedokaUpit, dajJMBGSvjedokaUpit, dajSvjedokeZaIzvjestajUpit, dajIzjavuSvjedokaUpit,
            modifikujSvjedokaUpit;

    private SvjedokDAO() throws SQLException {
        String url = "jdbc:sqlite:inspection.db";
        try{
            if(inspektorDao.getConn()!=null)
                conn = inspektorDao.getConn();
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            pretragaUpit = conn.prepareStatement("SELECT * FROM svjedok");
        } catch (SQLException e) {
            kreirajBazu();
            pretragaUpit = conn.prepareStatement("SELECT * FROM svjedok");
        }
        noviIdUpit = conn.prepareStatement("SELECT Max(id)+1 FROM svjedok");
        dodavanjeUpit = conn.prepareStatement("INSERT INTO svjedok VALUES(?, ?, ?, ?, ?, ?)");
        sviSvjedociUpit = conn.prepareStatement("SELECT * FROM svjedok");
        obrisiSveSvjedokeUpit = conn.prepareStatement("DELETE FROM svjedok");
        dajImeSvjedokaUpit = conn.prepareStatement("SELECT ime FROM svjedok WHERE id=?");
        dajPrezimeSvjedokaUpit = conn.prepareStatement("SELECT prezime FROM svjedok WHERE id=?");
        dajJMBGSvjedokaUpit = conn.prepareStatement("SELECT jmbg FROM svjedok WHERE id=?");
        dajSvjedokeZaIzvjestajUpit = conn.prepareStatement("SELECT * FROM svjedok WHERE idIzvjestaja=?");
        dajIzjavuSvjedokaUpit = conn.prepareStatement("SELECT izjava FROM svjedok WHERE id=?");
        modifikujSvjedokaUpit = conn.prepareStatement("UPDATE svjedok SET idIzvjestaja=?, ime=?, prezime=?, jmbg=?, izjava=? WHERE id=?");
    }

    public void modifikuj(int id, int idIzvjestaja, String ime, String prezime, String jmbg, String izjava){
        try{
            modifikujSvjedokaUpit.setInt(1, idIzvjestaja);
            modifikujSvjedokaUpit.setString(2, ime);
            modifikujSvjedokaUpit.setString(3, prezime);
            modifikujSvjedokaUpit.setString(4, jmbg);
            modifikujSvjedokaUpit.setString(5, izjava);
            modifikujSvjedokaUpit.setInt(6, id);
            modifikujSvjedokaUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int dajIdPrvogSvjedoka(int idIzvjestaja){
        ArrayList<Svjedok> svjedoci = new ArrayList();
        try{
            dajSvjedokeZaIzvjestajUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajSvjedokeZaIzvjestajUpit.executeQuery();
            while(rs.next()){
                svjedoci.add(new Svjedok(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(svjedoci.get(0).getId()<svjedoci.get(1).getId()) return svjedoci.get(0).getId();
        return svjedoci.get(1).getId();
    }

    public int dajIdDrugogSvjedoka(int idIzvjestaja){
        ArrayList<Svjedok> svjedoci = new ArrayList();
        try{
            dajSvjedokeZaIzvjestajUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajSvjedokeZaIzvjestajUpit.executeQuery();
            while(rs.next()){
                svjedoci.add(new Svjedok(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(svjedoci.get(0).getId()<svjedoci.get(1).getId()) return svjedoci.get(1).getId();
        return svjedoci.get(0).getId();
    }

    public ObservableList<Svjedok> sviSvjedoci(){
        ObservableList<Svjedok> rezultat = FXCollections.observableArrayList();
        try{
            ResultSet rs = sviSvjedociUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Svjedok(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public String dajIzjavuSvjedoka(int idSvjedoka) {
        try{
            dajIzjavuSvjedokaUpit.setInt(1, idSvjedoka);
            ResultSet rs = dajIzjavuSvjedokaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajImeSvjedoka(int idSvjedoka) {
        try{
            dajImeSvjedokaUpit.setInt(1, idSvjedoka);
            ResultSet rs = dajImeSvjedokaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajPrezimeSvjedoka(int idSvjedoka) {
        try{
            dajPrezimeSvjedokaUpit.setInt(1, idSvjedoka);
            ResultSet rs = dajPrezimeSvjedokaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajJMBGSvjedoka(int idSvjedoka) {
        try{
            dajJMBGSvjedokaUpit.setInt(1, idSvjedoka);
            ResultSet rs = dajJMBGSvjedokaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int dajIdNovogSvjedoka(){
        ResultSet rs = null;
        try{
            rs = noviIdUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public void dodaj(Svjedok s) throws SQLException {
        try{
            ResultSet rs = noviIdUpit.executeQuery();
            if(rs.next())
                s.setId(rs.getInt(1));
            else
                s.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dodavanjeUpit.setInt(1, s.getId());
        dodavanjeUpit.setInt(2, s.getIdIzvjestaja());
        dodavanjeUpit.setString(3, s.getIme());
        dodavanjeUpit.setString(4, s.getPrezime());
        dodavanjeUpit.setString(5, s.getJmbg());
        dodavanjeUpit.setString(6, s.getIzjava());
        dodavanjeUpit.execute();
    }

    public static SvjedokDAO getInstance() throws SQLException {
        if(instance==null) instance = new SvjedokDAO();
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

    public void obrisiSveSvjedoke() {
        try{
            obrisiSveSvjedokeUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
