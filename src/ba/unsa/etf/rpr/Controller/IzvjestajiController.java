package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.IzvjestajDAO;
import ba.unsa.etf.rpr.DAL.done.ObjectDAO;
import ba.unsa.etf.rpr.DAL.done.WitnessDAO;
import ba.unsa.etf.rpr.DAL.VlasnikDAO;
import ba.unsa.etf.rpr.Model.Izvjestaj;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class IzvjestajiController {
    public int idInspektora;
    public Label labVlasnikObjekta;
    public Label labStanjeObjekta;
    public Label labJedinstvenaSifra;
    public Label labNazivAdresaObjekta;
    public Label labDatumInspekcije;
    private IzvjestajDAO izvjestajDAO;
    public ListView listaIzvjestaja;
    private int idTrenutnogIzvjestaja =- 1;
    private ObjectDAO objekatDAO;
    private WitnessDAO svjedokDAO;
    private VlasnikDAO vlasnikDAO;

    @FXML
    public void initialize() throws SQLException {
        izvjestajDAO = IzvjestajDAO.getInstance();
        vlasnikDAO = VlasnikDAO.getInstance();
        objekatDAO = ObjectDAO.getInstance();
        svjedokDAO = WitnessDAO.getInstance();
        listaIzvjestaja.setItems(izvjestajDAO.dajIzvjestajeInspektoraSaIDem(idInspektora));
        listaIzvjestaja.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Izvjestaj noviIzvjestaj = (Izvjestaj) newItem;
            if(noviIzvjestaj != null){
                idTrenutnogIzvjestaja = noviIzvjestaj.getId();
                int idObjekta = izvjestajDAO.dajIDObjektaZaIzvjestajID(idTrenutnogIzvjestaja);
                labNazivAdresaObjekta.setText(objekatDAO.getNameForID(idObjekta) + ", " + objekatDAO.getAddressForObjectID(idObjekta));
                int vlasnikObjekta = objekatDAO.getOwnerForID(idObjekta);
                labVlasnikObjekta.setText(vlasnikDAO.dajPodatkeVlasnikaZaId(vlasnikObjekta));
                labDatumInspekcije.setText(izvjestajDAO.dajDatumInspekcije(idTrenutnogIzvjestaja));
                labStanjeObjekta.setText("Radi");
                labJedinstvenaSifra.setText(izvjestajDAO.dajJedinstvenuSifruZaID(idTrenutnogIzvjestaja));
            }else{

            }
        });
    }

    public int getIdInspektora() {
        return idInspektora;
    }

    public void setIdInspektora(int idInspektora) {
        this.idInspektora = idInspektora;
    }

    public void detaljiIzvjestaja(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pregledIzvjestaja.fxml"));
        Parent root = loader.load();
        PregledIzvjestajaController cont = loader.getController();
        int idObjekta = izvjestajDAO.dajIDObjektaZaIzvjestajID(idTrenutnogIzvjestaja);
        cont.labObjekat.setText(objekatDAO.getNameForID(idObjekta));
        cont.labVrstaObjekta.setText("Kafana");
        cont.labAdresaObjekta.setText(objekatDAO.getAddressForObjectID(idObjekta));
        cont.areaOpisTerena.setText(izvjestajDAO.dajOpisTerenaZaID(idTrenutnogIzvjestaja));
        int idVlasnika = objekatDAO.getOwnerForID(idObjekta);
        cont.labVlasnik.setText(vlasnikDAO.dajPodatkeVlasnikaZaId(idVlasnika));
        int idPrvogSvjedoka = svjedokDAO.getFirstWitnessID(idTrenutnogIzvjestaja);
        int idDrugogSvjedoka = svjedokDAO.getSecondWitnessID(idTrenutnogIzvjestaja);
        cont.labPodaciPrvogSvjedoka.setText(svjedokDAO.getWitnessName(idPrvogSvjedoka) + " " + svjedokDAO.getWitnessSurename(idPrvogSvjedoka) + ", (" + svjedokDAO.getWitnessJMBG(idPrvogSvjedoka) + ")");
        cont.areaIzjavaPrvogSvjedoka.setText(svjedokDAO.getWitnessStatementForWitness(idPrvogSvjedoka));
        cont.labPodaciDrugogSvjedoka.setText(svjedokDAO.getWitnessName(idDrugogSvjedoka) + " " + svjedokDAO.getWitnessSurename(idDrugogSvjedoka) + ", (" + svjedokDAO.getWitnessJMBG(idDrugogSvjedoka) + ")");
        cont.areaIzjavaDrugogSvjedoka.setText(svjedokDAO.getWitnessStatementForWitness(idDrugogSvjedoka));
        cont.labDatumInspekcije.setText(izvjestajDAO.dajDatumInspekcije(idTrenutnogIzvjestaja));
        boolean izdatNalog = izvjestajDAO.izdatPrekršajniNalog(idTrenutnogIzvjestaja);
        if(izdatNalog) {
            cont.labNalog.setText("Izdat");
            cont.labPrekršaj.setText(izvjestajDAO.dajPrekrsajZaID(idTrenutnogIzvjestaja));
            cont.labNovcanaKazna.setText(izvjestajDAO.dajNovcanuKaznuZaID(idTrenutnogIzvjestaja) + " KM");
            cont.labDodatniZahtjevi.setText(izvjestajDAO.dajDodatneZahtjeveZaID(idTrenutnogIzvjestaja));
        } else {
            cont.labNalog.setText("Nije izdat");
            cont.labPrekršaj.setText("Nije počinjen prekršaj");
            cont.labNovcanaKazna.setText("Nije izdata novčana kazna");
            cont.labDodatniZahtjevi.setText("Nema dodatnih zahtjeva");
        }
        int brojEvidentiranihRadnika = izvjestajDAO.dajBrojEvidentiranihRadnikaZaID(idTrenutnogIzvjestaja);
        if(brojEvidentiranihRadnika!=0){
            cont.labRadnici.setText("Podnesena evidencija o " + brojEvidentiranihRadnika + " radnika na crno");
        }else{
            cont.labRadnici.setText("Nisu evidentirani radnici na crno");
        }
        if(izvjestajDAO.dajPrijavuOKrivicnomDjeluZaID(idTrenutnogIzvjestaja)==1){
            cont.labKrivicnoDjelo.setText("Podnesena je prijava o krivičnom djelu");
        }else{
            cont.labKrivicnoDjelo.setText("Nije podnesena prijava o krivičnom djelu");
        }
        if(izvjestajDAO.dajFitocertifikatZaID(idTrenutnogIzvjestaja)==1) cont.labFitocertifikat.setText("Izdat je fitocertifikat");
        else cont.labFitocertifikat.setText("Nije izdat fitocertifikat");
        if(izvjestajDAO.isUzetUzorakZaID(idTrenutnogIzvjestaja)) cont.labUzetUzorak.setText("Uzet uzorak na GP/CI/MC");
        else cont.labUzetUzorak.setText("Nije uzet uzorak na GP/CI/MC");
        cont.labZabranaRada.setText(izvjestajDAO.dajPrivremenuZabranu(idTrenutnogIzvjestaja));
        boolean prijavljenoRadiliste = izvjestajDAO.isPrijavljenoRadilisteZaID(idTrenutnogIzvjestaja);
        if(prijavljenoRadiliste){
            cont.labPrijavljenoRadiliste.setText("Da");
            cont.labBrojZaposlenih.setText(String.valueOf(izvjestajDAO.dajBrojZaposlenihZaID(idTrenutnogIzvjestaja)));
            cont.labPotvrdaORadu.setText(String.valueOf(izvjestajDAO.dajBrojPotvrdeORaduZaID(idTrenutnogIzvjestaja)));
        }else{
            cont.labPrijavljenoRadiliste.setText("Ne");
            cont.labBrojZaposlenih.setText("Nema zaposlenih");
            cont.labPotvrdaORadu.setText("Nema potvrdu za rad");
        }
        if(izvjestajDAO.dajNedostatakZaID(idTrenutnogIzvjestaja).isBlank()) {
            cont.labRjesenjeNedostatak.setText("Nije izdato");
            cont.labNedostatak.setText("N/A");
        }
        else {
            cont.labRjesenjeNedostatak.setText("Izdato");
            cont.labNedostatak.setText(izvjestajDAO.dajNedostatakZaID(idTrenutnogIzvjestaja));
        }
        myStage.setResizable(false);
        myStage.setTitle("Pregledaj izvještaj");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void obrisiIzvjestaj(ActionEvent actionEvent) {
        izvjestajDAO.obrisiIzvjestajSaIDem(idTrenutnogIzvjestaja);
        refresujIzvjestaj();
    }

    public void modifikujIzvjestaj(ActionEvent actionEvent) throws IOException {
        if(idTrenutnogIzvjestaja == -1) return;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifikujIzvjestaj.fxml"));
        Parent root = loader.load();
        ModifikujIzvjestajController cont = loader.getController();
        int idObjekta = izvjestajDAO.dajIDObjektaZaIzvjestajID(idTrenutnogIzvjestaja);
        cont.fldNazivObjekta.setText(objekatDAO.getNameForID(idObjekta));
        cont.fldAdresaObjekta.setText(objekatDAO.getAddressForObjectID(idObjekta));
        cont.datumInspekcije.getEditor().setText(izvjestajDAO.dajDatumInspekcije(idTrenutnogIzvjestaja));
        cont.opisTerena.setText(izvjestajDAO.dajOpisTerenaZaID(idTrenutnogIzvjestaja));
        int idPrvogSvjedoka = svjedokDAO.getFirstWitnessID(idTrenutnogIzvjestaja);
        int idDrugogSvjedoka = svjedokDAO.getSecondWitnessID(idTrenutnogIzvjestaja);
        cont.s1Ime.setText(svjedokDAO.getWitnessName(idPrvogSvjedoka));
        cont.s1prezime.setText(svjedokDAO.getWitnessSurename(idPrvogSvjedoka));
        cont.s1JMBG.setText(svjedokDAO.getWitnessJMBG(idPrvogSvjedoka));
        cont.s1Izjava.setText(svjedokDAO.getWitnessStatementForWitness(idPrvogSvjedoka));
        cont.s2ime.setText(svjedokDAO.getWitnessName(idDrugogSvjedoka));
        cont.s2prezime.setText(svjedokDAO.getWitnessSurename(idDrugogSvjedoka));
        cont.s2JMBG.setText(svjedokDAO.getWitnessJMBG(idDrugogSvjedoka));
        cont.s2Izjava.setText(svjedokDAO.getWitnessStatementForWitness(idDrugogSvjedoka));
        if(izvjestajDAO.dajPrekrsajZaID(idTrenutnogIzvjestaja).isBlank()){
            cont.cbPrekrsaj.setSelected(false);
        }else{
            cont.cbPrekrsaj.setSelected(true);
            cont.fldPrekrsaj.setText(izvjestajDAO.dajPrekrsajZaID(idTrenutnogIzvjestaja));
            cont.fldKazna.setText(izvjestajDAO.dajNovcanuKaznuZaID(idTrenutnogIzvjestaja));
            cont.fldZahtjevi.setText(izvjestajDAO.dajDodatneZahtjeveZaID(idTrenutnogIzvjestaja));
        }
        if(izvjestajDAO.dajBrojEvidentiranihRadnikaZaID(idTrenutnogIzvjestaja)!=0){
            cont.cbEvidencijaRadnika.setSelected(true);
            cont.fldBrojRadnika.setText(String.valueOf(izvjestajDAO.dajBrojEvidentiranihRadnikaZaID(idTrenutnogIzvjestaja)));
        }else{
            cont.cbEvidencijaRadnika.setSelected(false);
        }
        if(izvjestajDAO.dajPrijavuOKrivicnomDjeluZaID(idTrenutnogIzvjestaja)==1){
            cont.cbKrivicnoDjelo.setSelected(true);
        }
        if(izvjestajDAO.dajFitocertifikatZaID(idTrenutnogIzvjestaja)==1) cont.cbFitocertifikat.setSelected(true);
        if(izvjestajDAO.isUzetUzorakZaID(idTrenutnogIzvjestaja)) cont.cbUzorak.setSelected(true);
        else cont.cbUzorak.setSelected(false);
        if(izvjestajDAO.dajBrojDanaZabraneZaID(idTrenutnogIzvjestaja)!=0){
            cont.cbZabranaRada.setSelected(true);
            cont.rbBrojDana.setSelected(true);
            cont.fldBrojDanaZabrane.setText(String.valueOf(izvjestajDAO.dajBrojDanaZabraneZaID(idTrenutnogIzvjestaja)));
            cont.fldUslovZabrane.setDisable(true);
        }else if(!izvjestajDAO.dajUslovZabraneZaID(idTrenutnogIzvjestaja).isBlank()){
            cont.cbZabranaRada.setSelected(true);
            cont.rbIspunjenjeUslova.setSelected(true);
            cont.fldBrojDanaZabrane.setDisable(true);
            cont.fldUslovZabrane.setText(izvjestajDAO.dajUslovZabraneZaID(idTrenutnogIzvjestaja));
        }
        if(izvjestajDAO.isPrijavljenoRadilisteZaID(idTrenutnogIzvjestaja)){
            int idVlasnika = objekatDAO.getOwnerForID(idObjekta);
            cont.cbPrijaviRadiliste.setSelected(true);
            cont.fldVlasnik.setText(vlasnikDAO.dajPodatkeVlasnikaZaId(idVlasnika));
            cont.fldBrojZaposlenih.setText(String.valueOf(izvjestajDAO.dajBrojZaposlenihZaID(idTrenutnogIzvjestaja)));
            cont.fldPotvrdaORadu.setText(String.valueOf(izvjestajDAO.dajBrojPotvrdeORaduZaID(idTrenutnogIzvjestaja)));
        }else{
            cont.cbPrijaviRadiliste.setSelected(false);
            cont.fldVlasnik.setDisable(true);
            cont.fldBrojZaposlenih.setDisable(true);
            cont.fldPotvrdaORadu.setDisable(true);
        }
        if(!izvjestajDAO.dajNedostatakZaID(idTrenutnogIzvjestaja).isBlank()){
            cont.cbNedostatak.setSelected(true);
            cont.fldNedostatak.setText(izvjestajDAO.dajNedostatakZaID(idTrenutnogIzvjestaja));
        }else{
            cont.cbNedostatak.setSelected(false);
            cont.fldNedostatak.setDisable(true);
        }
        cont.idOtvorenogIzvjestaja = idTrenutnogIzvjestaja;
        myStage.setTitle("Modifikuj izvještaj");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        refresujIzvjestaj();
    }

    public void refresujIzvjestaj() {
        try {
            listaIzvjestaja.setItems(izvjestajDAO.dajIzvjestajeInspektoraSaIDem(idInspektora));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) listaIzvjestaja.getScene().getWindow();
        stage.close();
    }
}
