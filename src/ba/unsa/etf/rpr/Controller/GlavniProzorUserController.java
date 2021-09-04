package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.DAL.done.*;
import ba.unsa.etf.rpr.Model.Izvjestaj;
import ba.unsa.etf.rpr.Model.Termin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavniProzorUserController {
    public ListView listaIzvjestaja;
    public ListView listaTermina;
    public RadioButton rbSviTermini;
    public RadioButton rbMojiTermini;
    private WitnessDAO svjedokDAO;
    private IzvjestajDAO izvjestajDAO;
    private UserDAO prijavljeniDao;
    private int idTrenutnogIzvjestaja = -1;
    private ObjectDAO objekatDao;
    private VlasnikDAO vlasnikDao;
    private TerminDAO terminDao;
    private InspectorDAO inspektorDao;
    private LoginLogDAO logDAO;
    private int idTrenutnogTermina = -1;

    @FXML
    public void initialize() throws SQLException {
        svjedokDAO = WitnessDAO.getInstance();
        prijavljeniDao = UserDAO.getInstance();
        izvjestajDAO = IzvjestajDAO.getInstance();
        objekatDao = ObjectDAO.getInstance();
        vlasnikDao = VlasnikDAO.getInstance();
        terminDao = TerminDAO.getInstance();
        inspektorDao = InspectorDAO.getInstance();
        logDAO = LoginLogDAO.getInstance();
        listaIzvjestaja.setItems(izvjestajDAO.dajIzvjestajeInspektoraSaIDem(prijavljeniDao.getLoggedUserID()));
        listaIzvjestaja.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Izvjestaj noviIzvjestaj = (Izvjestaj) newItem;
            if(noviIzvjestaj!=null)
                idTrenutnogIzvjestaja = noviIzvjestaj.getId();
        });
        listaTermina.setItems(terminDao.dajSveTermine());
        listaTermina.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Termin noviTermin = (Termin) newItem;
            if(noviTermin != null){
                idTrenutnogTermina = noviTermin.getId();
            }
        });
        rbSviTermini.setSelected(true);
        rbSviTermini.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                listaTermina.setItems(terminDao.dajSveTermine());
        });
        rbMojiTermini.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                listaTermina.setItems(terminDao.dajSveTermineInspektora(prijavljeniDao.getLoggedUserID()));
        });
    }

    public void kreirajIzvjestaj(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/objectPicking.fxml"));
        myStage.setTitle("Odaberi objekat");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        logDAO.logout(inspektorDao.getUniqueIDForID(prijavljeniDao.getLoggedUserID()));
        prijavljeniDao.deleteLoggedUser();
        Stage stage = (Stage) listaIzvjestaja.getScene().getWindow();
        stage.close();
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/preview.fxml"));
        //myStage.initStyle(StageStyle.UNDECORATED);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) listaIzvjestaja.getScene().getWindow();
        stage.close();
    }

    public void refresujIzvjestajeBtn(ActionEvent actionEvent) {
        refresujIzvjestaj();
        refreshTasks();
    }

    public void obrisiIzvjestajBtn(ActionEvent actionEvent) {
        izvjestajDAO.obrisiIzvjestajSaIDem(idTrenutnogIzvjestaja);
        refresujIzvjestaj();
    }

    private void refresujIzvjestaj() {
        try {
            listaIzvjestaja.setItems(izvjestajDAO.dajIzvjestajeInspektoraSaIDem(prijavljeniDao.getLoggedUserID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifikujIzvjestajBtn(ActionEvent actionEvent) throws IOException {
        if(idTrenutnogIzvjestaja == -1) return;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifikujIzvjestaj.fxml"));
        Parent root = loader.load();
        ModifikujIzvjestajController cont = loader.getController();
        int idObjekta = izvjestajDAO.dajIDObjektaZaIzvjestajID(idTrenutnogIzvjestaja);
        cont.fldNazivObjekta.setText(objekatDao.getNameForID(idObjekta));
        cont.fldAdresaObjekta.setText(objekatDao.getAddressForObjectID(idObjekta));
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
            int idVlasnika = objekatDao.getOwnerForID(idObjekta);
            cont.cbPrijaviRadiliste.setSelected(true);
            cont.fldVlasnik.setText(vlasnikDao.dajPodatkeVlasnikaZaId(idVlasnika));
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

    public void pogledajIzvjestajBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pregledIzvjestaja.fxml"));
        Parent root = loader.load();
        PregledIzvjestajaController cont = loader.getController();
        int idObjekta = izvjestajDAO.dajIDObjektaZaIzvjestajID(idTrenutnogIzvjestaja);
        cont.labObjekat.setText(objekatDao.getNameForID(idObjekta));
        cont.labVrstaObjekta.setText("Kafana");
        cont.labAdresaObjekta.setText(objekatDao.getAddressForObjectID(idObjekta));
        cont.areaOpisTerena.setText(izvjestajDAO.dajOpisTerenaZaID(idTrenutnogIzvjestaja));
        int idVlasnika = objekatDao.getOwnerForID(idObjekta);
        cont.labVlasnik.setText(vlasnikDao.dajPodatkeVlasnikaZaId(idVlasnika));
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
        cont.labJedinstvenaSifra.setText(izvjestajDAO.dajJedinstvenuSifruZaID(idTrenutnogIzvjestaja));
        myStage.setResizable(false);
        myStage.setTitle("Pregledaj izvještaj");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void exportujIzvjestajBtn(ActionEvent actionEvent) {
    }

    public void createTaskBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/objectPickingForTask.fxml"));
        myStage.setTitle("Choose an object");
        myStage.setResizable(false);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        refreshTasks();
    }

    public void pregledajTerminBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pregledTermina.fxml"));
        Parent root = loader.load();
        PregledTerminaController cont = loader.getController();
        int idInspektora = terminDao.dajInspektoraZaIDTermina(idTrenutnogTermina);
        int idObjekta = terminDao.dajIDObjektaZaIDTermina(idTrenutnogTermina);
        cont.labTerminZakazao.setText(inspektorDao.getNameSurenameForID(idInspektora));
        cont.labNazivObjekta.setText(objekatDao.getNameForID(idObjekta));
        cont.labAdresaObjekta.setText(objekatDao.getAddressForObjectID(idObjekta));
        cont.labDatumVrijemeTermina.setText(terminDao.dajVrijemeZaID(idTrenutnogTermina));
        cont.areaNapomeneTermina.setText(terminDao.dajNapomeneTerminaZaID(idTrenutnogTermina));
        int idZaduzenogInspektora = terminDao.dajIDZaduzenogInspektora(idTrenutnogTermina);
        System.out.println(idZaduzenogInspektora);
        if(idZaduzenogInspektora != -1) cont.labZaduzeniInspektor.setText(inspektorDao.getNameSurenameForID(idZaduzenogInspektora));
        else cont.labZaduzeniInspektor.setText("Nema zaduženog inspektora");
        boolean terminObavljen = terminDao.isObavljen(idTrenutnogTermina);
        if(terminObavljen) cont.labTerminObavljen.setText("Obavljen");
        else cont.labTerminObavljen.setText("Nije obavljen");
        myStage.setTitle("Pregledaj termin");
        myStage.setResizable(false);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void detaljiObjektaTerminaBtn(ActionEvent actionEvent) {

    }

    public void modifikacijaTerminaBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifyTermin.fxml"));
        Parent root = loader.load();
        ModifyTerminController cont = loader.getController();
        int idObjekta = terminDao.dajIDObjektaZaIDTermina(idTrenutnogTermina);
        cont.fldNazivObjekta.setText(objekatDao.getNameForID(idObjekta));
        cont.fldAdresaObjekta.setText(objekatDao.getAddressForObjectID(idObjekta));
        cont.datePickTermin.getEditor().setText(terminDao.dajVrijemeZaID(idTrenutnogTermina));
        cont.areaNapomene.setText(terminDao.dajNapomeneTerminaZaID(idTrenutnogTermina));
        cont.fldSati.setText("");
        cont.fldMinuta.setText("");
        cont.idTermina = idTrenutnogTermina;
        myStage.setTitle("Modifikuj termin");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        refreshTasks();
    }

    public void obrisiTerminBtn(ActionEvent actionEvent) {
        terminDao.obrisiTermin(idTrenutnogTermina);
        refreshTasks();
    }

    private void refreshTasks() {
        listaTermina.setItems(terminDao.dajSveTermineInspektora(prijavljeniDao.getLoggedUserID()));
    }

    public void uzmiTerminBtn(ActionEvent actionEvent) {
        if(!terminDao.isZauzetTermin(idTrenutnogTermina))terminDao.uzmiTermin(idTrenutnogTermina, prijavljeniDao.getLoggedUserID());
    }
}
