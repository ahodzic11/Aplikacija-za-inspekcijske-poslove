package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Svjedok;
import ba.unsa.etf.rpr.Model.Termin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class TerminDAO {
    private static TerminDAO instance = null;
    private static InspektorDAO inspektorDao = InspektorDAO.getInstance();
    private Connection conn;
    private PreparedStatement pretragaUpit, noviIdUpit, dodavanjeUpit, brisanjeUpit, terminiInspektoraUpit, inspektorUpit,
            objekatUpit, datumVrijemeUpit, napomeneUpit, zaduzeniInspektorUpit, obavljenUpit, zaduziInspektoraUpit,
            zauzetUpit, modifikujUpit, slobodniTerminiUpit, dodijeliUpit;

    private TerminDAO() throws SQLException {
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
            pretragaUpit = conn.prepareStatement("SELECT * FROM termin");
        } catch (SQLException e) {
            kreirajBazu();
            pretragaUpit = conn.prepareStatement("SELECT * FROM termin");
        }
        noviIdUpit = conn.prepareStatement("SELECT Max(id)+1 FROM termin");
        dodavanjeUpit = conn.prepareStatement("INSERT INTO termin VALUES(?, ?, ?, ?, ?, ?, ?)");
        brisanjeUpit = conn.prepareStatement("DELETE FROM termin WHERE id=?");
        terminiInspektoraUpit = conn.prepareStatement("SELECT * FROM termin WHERE inspektorId=?");
        inspektorUpit = conn.prepareStatement("SELECT inspektorId FROM termin WHERE id=?");
        objekatUpit = conn.prepareStatement("SELECT objekatId FROM termin WHERE id=?");
        datumVrijemeUpit = conn.prepareStatement("SELECT datumVrijeme FROM termin WHERE id=?");
        napomeneUpit = conn.prepareStatement("SELECT napomene FROM termin WHERE id=?");
        zaduzeniInspektorUpit = conn.prepareStatement("SELECT zaduzeniInspektorId FROM termin WHERE id=?");
        obavljenUpit = conn.prepareStatement("SELECT odrađen FROM termin WHERE id=?");
        zaduziInspektoraUpit = conn.prepareStatement("UPDATE termin SET zaduzeniInspektorId=? WHERE id=?");
        zauzetUpit = conn.prepareStatement("SELECT zaduzeniInspektorId FROM termin WHERE id=?");
        modifikujUpit = conn.prepareStatement("UPDATE termin SET objekatId=?, inspektorId=?, datumVrijeme=?, napomene=?, odrađen=?, zaduzeniInspektorId=? WHERE id=?");
        slobodniTerminiUpit = conn.prepareStatement("SELECT * FROM termin WHERE zaduzeniInspektorId=-1");
        dodijeliUpit = conn.prepareStatement("UPDATE termin SET zaduzeniInspektorId=? WHERE id=?");
    }

    public void modifikuj(int id, int objekatId, int inspektorId, String datumVrijeme, String napomene, int odrađen, int zaduzeniInspektorId){
        try{
            modifikujUpit.setInt(1, objekatId);
            modifikujUpit.setInt(2, inspektorId);
            modifikujUpit.setString(3, datumVrijeme);
            modifikujUpit.setString(4, napomene);
            modifikujUpit.setInt(5, odrađen);
            modifikujUpit.setInt(6, zaduzeniInspektorId);
            modifikujUpit.setInt(7, id);
            modifikujUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int dajOdradjen(int idTermina){
        try{
            obavljenUpit.setInt(1, idTermina);
            ResultSet rs = obavljenUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void obrisiTermin(int idTermina){
        try{
            brisanjeUpit.setInt(1, idTermina);
            brisanjeUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TerminDAO getInstance() throws SQLException {
        if(instance==null) instance = new TerminDAO();
        return instance;
    }

    public void addTask(Termin t) throws SQLException {
        try{
            ResultSet rs = noviIdUpit.executeQuery();
            if(rs.next())
                t.setId(rs.getInt(1));
            else
                t.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dodavanjeUpit.setInt(1, t.getId());
        dodavanjeUpit.setInt(2, t.getObjekatId());
        dodavanjeUpit.setInt(3, t.getInspektorId());
        dodavanjeUpit.setString(4, t.getDatumVrijeme());
        dodavanjeUpit.setString(5, t.getNapomene());
        dodavanjeUpit.setInt(6, t.getOdrađen());
        dodavanjeUpit.setInt(7, t.getZaduzeniInspektorId());
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

    public ObservableList<Termin> dajSveTermine() {
        ObservableList<Termin> rezultat = FXCollections.observableArrayList();
        try{
            ResultSet rs = pretragaUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Termin(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ObservableList<Termin> dajSveTermineInspektora(int idInspektora) {
        ObservableList<Termin> rezultat = FXCollections.observableArrayList();
        try{
            terminiInspektoraUpit.setInt(1, idInspektora);
            ResultSet rs = terminiInspektoraUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Termin(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public int dajInspektoraZaIDTermina(int idTermina) {
        try{
            inspektorUpit.setInt(1, idTermina);
            ResultSet rs = inspektorUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int dajIDObjektaZaIDTermina(int idTermina) {
        try{
            objekatUpit.setInt(1, idTermina);
            ResultSet rs = objekatUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String dajVrijemeZaID(int idTermina) {
        try{
            datumVrijemeUpit.setInt(1, idTermina);
            ResultSet rs = datumVrijemeUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajNapomeneTerminaZaID(int idTermina) {
        try{
            napomeneUpit.setInt(1, idTermina);
            ResultSet rs = napomeneUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int dajIDZaduzenogInspektora(int idTermina) {
        try{
            zaduzeniInspektorUpit.setInt(1, idTermina);
            ResultSet rs = zaduzeniInspektorUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isObavljen(int idTermina) {
        try{
            obavljenUpit.setInt(1, idTermina);
            ResultSet rs = obavljenUpit.executeQuery();
            rs.next();
            if(rs.getInt(1)==1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void uzmiTermin(int idTermina, int idInspektora) {
        try{
            zaduziInspektoraUpit.setInt(1, idInspektora);
            zaduziInspektoraUpit.setInt(2, idTermina);
            zaduziInspektoraUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isZauzetTermin(int idTermina) {
        try{
            zauzetUpit.setInt(1, idTermina);
            ResultSet rs = zauzetUpit.executeQuery();
            rs.next();
            if(rs.getInt(1)==-1) return false;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<Termin> dajSlobodneTermine(){
        ObservableList<Termin> rezultat = FXCollections.observableArrayList();
        try{
            ResultSet rs = slobodniTerminiUpit.executeQuery();
            while(rs.next()){
                rezultat.add(new Termin(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void assignTaskToInspectorID(int idInspektora, int idTermina) {
        try{
            dodijeliUpit.setInt(1, idInspektora);
            dodijeliUpit.setInt(2, idTermina);
            dodijeliUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
