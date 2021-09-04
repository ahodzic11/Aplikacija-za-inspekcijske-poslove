package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Izvjestaj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class IzvjestajDAO {
    private static IzvjestajDAO instance = null;
    private static InspektorDAO inspektorDao = InspektorDAO.getInstance();
    private ObjekatDAO objekatDao;
    private Connection conn;
    private PreparedStatement pretragaUpit, noviIdUpit, dodavanjeUpit, brisanjeSvihIzvjestajaUpit, dajIzvjestajeInspektora,
            obrisiIzvjestajUpit, dajObjekatIDUpit, dajOpisTerenaUpit, dajDatumInspekcijeUpit, dajPrekrsajUpit, dajNovcanuKaznuUpit,
            dajDodatneZahtjeveUpit, dajBrojEvidentiranihRadnikaUpit, dajPrijavuOKrivicnomDjeluUpit, dajFitocertifikatUpit,
        dajUzorakUpit, brojDanaZabraneUpit, uslovZabraneUpit, prijavljenoRadilisteUpit, brojZaposlenihUpit, brojPotvrdeORaduUpit,
            nedostatakUpit, izmjenaUpit, jedinstvenaSifraUpit;

    private IzvjestajDAO(){
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
            pretragaUpit = conn.prepareStatement("SELECT * FROM izvjestaj");
        } catch (SQLException e) {
            kreirajBazu();
            try {
                pretragaUpit = conn.prepareStatement("SELECT * FROM izvjestaj");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        try {
            noviIdUpit = conn.prepareStatement("SELECT Max(id)+1 FROM izvjestaj");
            dodavanjeUpit = conn.prepareStatement("INSERT INTO izvjestaj VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            brisanjeSvihIzvjestajaUpit = conn.prepareStatement("DELETE FROM izvjestaj");
            dajIzvjestajeInspektora = conn.prepareStatement("SELECT * FROM izvjestaj WHERE inspektorId=?");
            obrisiIzvjestajUpit = conn.prepareStatement("DELETE FROM izvjestaj WHERE id=?");
            dajObjekatIDUpit = conn.prepareStatement("SELECT objekatId FROM izvjestaj WHERE id=?");
            dajOpisTerenaUpit = conn.prepareStatement("SELECT opis FROM izvjestaj WHERE id=?");
            dajDatumInspekcijeUpit = conn.prepareStatement("SELECT datumInspekcije FROM izvjestaj WHERE id=?");
            dajPrekrsajUpit = conn.prepareStatement("SELECT prekrsaj FROM izvjestaj WHERE id=?");
            dajNovcanuKaznuUpit = conn.prepareStatement("SELECT novcanaKazna FROM izvjestaj WHERE id=?");
            dajDodatneZahtjeveUpit = conn.prepareStatement("SELECT dodatniZahtjevi FROM izvjestaj WHERE id=?");
            dajBrojEvidentiranihRadnikaUpit = conn.prepareStatement("SELECT brojEvidentiranihRadnika FROM izvjestaj WHERE id=?");
            dajPrijavuOKrivicnomDjeluUpit = conn.prepareStatement("SELECT prijavaOKrivicnomDjelu FROM izvjestaj WHERE id=?");
            dajFitocertifikatUpit = conn.prepareStatement("SELECT fitocertifikat FROM izvjestaj WHERE id=?");
            dajUzorakUpit = conn.prepareStatement("SELECT uzetUzorak FROM izvjestaj WHERE id=?");
            brojDanaZabraneUpit = conn.prepareStatement("SELECT brojDanaZabrane FROM izvjestaj WHERE id=?");
            uslovZabraneUpit = conn.prepareStatement("SELECT uslovZabrane FROM izvjestaj WHERE id=?");
            prijavljenoRadilisteUpit = conn.prepareStatement("SELECT prijavljenoRadiliste FROM izvjestaj WHERE id=?");
            brojZaposlenihUpit = conn.prepareStatement("SELECT brojZaposlenih FROM izvjestaj WHERE id=?");
            brojPotvrdeORaduUpit = conn.prepareStatement("SELECT brojPotvrdeORadu FROM izvjestaj WHERE id=?");
            nedostatakUpit = conn.prepareStatement("SELECT nedostatak FROM izvjestaj WHERE id=?");
            izmjenaUpit = conn.prepareStatement("UPDATE izvjestaj SET inspektorId=?, objekatId=?, datumInspekcije=?, opis=?, prekrsaj=?, novcanaKazna=?, dodatniZahtjevi=?, brojEvidentiranihRadnika=?, prijavaOKrivicnomDjelu=?, fitocertifikat=?, uzetUzorak=?, brojDanaZabrane=?, uslovZabrane=?, prijavljenoRadiliste=?, brojZaposlenih=?, brojPotvrdeORadu=?, nedostatak=?, svjedok1=?, svjedok2=?, nazivObjekta=?, adresaObjekta=?, jedinstvenaSifra=? WHERE id=?");
            jedinstvenaSifraUpit = conn.prepareStatement("SELECT jedinstvenaSifra FROM izvjestaj WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifikuj(int id, int inspektorId, String datumInspekcije, String opis, String prekrsaj, int novcanaKazna,
                          String dodatniZahtjevi, int brojEvidentiranihRadnika, int prijavaOKrivicnomDjelu, int fitocertifikat,
                          int uzetUzorak, int brojDanaZabrane, String uslovZabrane, int prijavljenoRadiliste, int brojZaposlenih,
                          int brojPotvrdeORadu, String nedostatak, int svjedok1Id, int svjedok2Id, int objekatId, String naslovObjekta,
                          String adresaObjekta, String jedinstvenaSifra){
        try{
            izmjenaUpit.setInt(1, inspektorId);
            izmjenaUpit.setInt(2, objekatId);
            izmjenaUpit.setString(3, datumInspekcije);
            izmjenaUpit.setString(4, opis);
            izmjenaUpit.setString(5, prekrsaj);
            izmjenaUpit.setInt(6, novcanaKazna);
            izmjenaUpit.setString(7, dodatniZahtjevi);
            izmjenaUpit.setInt(8, brojEvidentiranihRadnika);
            izmjenaUpit.setInt(9, prijavaOKrivicnomDjelu);
            izmjenaUpit.setInt(10, fitocertifikat);
            izmjenaUpit.setInt(11, uzetUzorak);
            izmjenaUpit.setInt(12, brojDanaZabrane);
            izmjenaUpit.setString(13, uslovZabrane);
            izmjenaUpit.setInt(14, prijavljenoRadiliste);
            izmjenaUpit.setInt(15, brojZaposlenih);
            izmjenaUpit.setInt(16, brojPotvrdeORadu);
            izmjenaUpit.setString(17, nedostatak);
            izmjenaUpit.setInt(18, svjedok1Id);
            izmjenaUpit.setInt(19, svjedok2Id);
            izmjenaUpit.setString(20, naslovObjekta);
            izmjenaUpit.setString(21, adresaObjekta);
            izmjenaUpit.setString(22, jedinstvenaSifra);
            izmjenaUpit.setInt(23, id);
            izmjenaUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiSveIzvjestaje(){
        try{
            brisanjeSvihIzvjestajaUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int dajIdNovogIzvjestaja(){
        try{
            ResultSet rs = noviIdUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void dodaj(Izvjestaj i) throws SQLException {
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
        dodavanjeUpit.setInt(2, i.getInspektorId());
        dodavanjeUpit.setString(3, i.getDatumInspekcije());
        dodavanjeUpit.setString(4, i.getOpis());
        dodavanjeUpit.setString(5, i.getPrekrsaj());
        dodavanjeUpit.setInt(6, i.getNovcanaKazna());
        dodavanjeUpit.setString(7, i.getDodatniZahtjevi());
        dodavanjeUpit.setInt(8, i.getBrojEvidentiranihRadnika());
        dodavanjeUpit.setInt(9, i.getPrijavaOKrivicnomDjelu());
        dodavanjeUpit.setInt(10, i.getFitocertifikat());
        dodavanjeUpit.setInt(11, i.getUzetUzorak());
        dodavanjeUpit.setInt(12, i.getBrojDanaZabrane());
        dodavanjeUpit.setString(13, i.getUslovZabrane());
        dodavanjeUpit.setInt(14, i.getPrijavljenoRadiliste());
        dodavanjeUpit.setInt(15, i.getBrojZaposlenih());
        dodavanjeUpit.setInt(16, i.getBrojPotvrdeORadu());
        dodavanjeUpit.setString(17, i.getNedostatak());
        dodavanjeUpit.setInt(18, i.getSvjedok1Id());
        dodavanjeUpit.setInt(19, i.getSvjedok2Id());
        dodavanjeUpit.setInt(20, i.getObjekatId());
        dodavanjeUpit.setString(21, i.getNazivObjekta());
        dodavanjeUpit.setString(22, i.getAdresaObjekta());
        dodavanjeUpit.setString(23, i.getJedinstvenaSifra());
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

    public static IzvjestajDAO getInstance() throws SQLException {
        if(instance==null) instance = new IzvjestajDAO();
        return instance;
    }

    public ObservableList<Izvjestaj> dajIzvjestajeInspektoraSaIDem(int idInspektora) throws SQLException {
        ObservableList<Izvjestaj> rezultat = FXCollections.observableArrayList();
        try{
            dajIzvjestajeInspektora.setInt(1, idInspektora);
            ResultSet rs = dajIzvjestajeInspektora.executeQuery();
            while(rs.next()){
                Izvjestaj noviIzvjestaj = new Izvjestaj(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8),
                        rs.getInt( 9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getString(13),
                        rs.getInt(14), rs.getInt(15), rs.getInt(16), rs.getString(17), rs.getInt(18), rs.getInt(18), rs.getInt(20), rs.getString(21), rs.getString(22), rs.getString(23));
                rezultat.add(noviIzvjestaj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void obrisiIzvjestajSaIDem(int idTrenutnogIzvjestaja) {
        try{
            obrisiIzvjestajUpit.setInt(1, idTrenutnogIzvjestaja);
            obrisiIzvjestajUpit.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int dajIDObjektaZaIzvjestajID(int idTrenutnoOtvorenogIzvjestaja) {
        try{
            dajObjekatIDUpit.setInt(1, idTrenutnoOtvorenogIzvjestaja);
            ResultSet rs = dajObjekatIDUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String dajPrekrsajZaID(int idIzvjestaja) {
        try{
            dajPrekrsajUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajPrekrsajUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajOpisTerenaZaID(int idIzvjestaja) {
        try{
            dajOpisTerenaUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajOpisTerenaUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajDatumInspekcije(int idIzvjestaja) {
        try{
            dajDatumInspekcijeUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajDatumInspekcijeUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean izdatPrekršajniNalog(int idIzvjestaja) {
        if(dajPrekrsajZaID(idIzvjestaja).isBlank()) return false;
        return true;
    }

    public String dajNovcanuKaznuZaID(int idIzvjestaja) {
        try{
            dajNovcanuKaznuUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajNovcanuKaznuUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajDodatneZahtjeveZaID(int idIzvjestaja) {
        try{
            dajDodatneZahtjeveUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajDodatneZahtjeveUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int dajBrojEvidentiranihRadnikaZaID(int idIzvjestaja) {
        try{
            dajBrojEvidentiranihRadnikaUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajBrojEvidentiranihRadnikaUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int dajPrijavuOKrivicnomDjeluZaID(int idIzvjestaja) {
        try{
            dajPrijavuOKrivicnomDjeluUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajPrijavuOKrivicnomDjeluUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int dajFitocertifikatZaID(int idIzvjestaja) {
        try{
            dajFitocertifikatUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajFitocertifikatUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isUzetUzorakZaID(int idIzvjestaja) {
        try{
            dajFitocertifikatUpit.setInt(1, idIzvjestaja);
            ResultSet rs = dajFitocertifikatUpit.executeQuery();
            rs.next();
            if(rs.getInt(1)==1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int dajBrojDanaZabraneZaID(int idIzvjestaja) {
        try{
            brojDanaZabraneUpit.setInt(1, idIzvjestaja);
            ResultSet rs = brojDanaZabraneUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String dajUslovZabraneZaID(int idIzvjestaja) {
        try{
            uslovZabraneUpit.setInt(1, idIzvjestaja);
            ResultSet rs = uslovZabraneUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isPrijavljenoRadilisteZaID(int idIzvjestaja) {
        try{
            prijavljenoRadilisteUpit.setInt(1, idIzvjestaja);
            ResultSet rs = prijavljenoRadilisteUpit.executeQuery();
            rs.next();
            if(rs.getInt(1)==1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int dajBrojZaposlenihZaID(int idIzvjestaja) {
        try{
            brojZaposlenihUpit.setInt(1, idIzvjestaja);
            ResultSet rs = brojZaposlenihUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int dajBrojPotvrdeORaduZaID(int idIzvjestaja) {
        try{
            brojPotvrdeORaduUpit.setInt(1, idIzvjestaja);
            ResultSet rs = brojPotvrdeORaduUpit.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String dajNedostatakZaID(int idIzvjestaja) {
        try{
            nedostatakUpit.setInt(1, idIzvjestaja);
            ResultSet rs = nedostatakUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String dajPrivremenuZabranu(int idIzvjestaja){
        if(dajBrojDanaZabraneZaID(idIzvjestaja)!=0){
            return "Izdata privremena zabrana rada na " + dajBrojDanaZabraneZaID(idIzvjestaja) + " dana";
        }else{
            return "Izdata zabrana do ispunjenja uslova: " + dajUslovZabraneZaID(idIzvjestaja);
        }
    }

    public String dajJedinstvenuSifruZaID(int idIzvjestaja) {
        try{
            jedinstvenaSifraUpit.setInt(1, idIzvjestaja);
            ResultSet rs = jedinstvenaSifraUpit.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
