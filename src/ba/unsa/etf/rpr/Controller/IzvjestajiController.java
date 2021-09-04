package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ReportDAO;
import ba.unsa.etf.rpr.DAL.ObjectDAO;
import ba.unsa.etf.rpr.DAL.WitnessDAO;
import ba.unsa.etf.rpr.DAL.OwnerDAO;
import ba.unsa.etf.rpr.Model.Report;
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
    private ReportDAO izvjestajDAO;
    public ListView listaIzvjestaja;
    private int idTrenutnogIzvjestaja =- 1;
    private ObjectDAO objekatDAO;
    private WitnessDAO svjedokDAO;
    private OwnerDAO vlasnikDAO;

    @FXML
    public void initialize() throws SQLException {
        izvjestajDAO = ReportDAO.getInstance();
        vlasnikDAO = OwnerDAO.getInstance();
        objekatDAO = ObjectDAO.getInstance();
        svjedokDAO = WitnessDAO.getInstance();
        listaIzvjestaja.setItems(izvjestajDAO.getReportsForInspectorID(idInspektora));
        listaIzvjestaja.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Report noviIzvjestaj = (Report) newItem;
            if(noviIzvjestaj != null){
                idTrenutnogIzvjestaja = noviIzvjestaj.getId();
                int idObjekta = izvjestajDAO.getObjectIDForReport(idTrenutnogIzvjestaja);
                labNazivAdresaObjekta.setText(objekatDAO.getNameForID(idObjekta) + ", " + objekatDAO.getAddressForObjectID(idObjekta));
                int vlasnikObjekta = objekatDAO.getOwnerForID(idObjekta);
                labVlasnikObjekta.setText(vlasnikDAO.getNameLastNameForID(vlasnikObjekta));
                labDatumInspekcije.setText(izvjestajDAO.getInspectionDateForReportID(idTrenutnogIzvjestaja));
                labStanjeObjekta.setText("Radi");
                labJedinstvenaSifra.setText(izvjestajDAO.getUniqueIDForReportID(idTrenutnogIzvjestaja));
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
        int idObjekta = izvjestajDAO.getObjectIDForReport(idTrenutnogIzvjestaja);
        cont.labObjekat.setText(objekatDAO.getNameForID(idObjekta));
        cont.labVrstaObjekta.setText("Kafana");
        cont.labAdresaObjekta.setText(objekatDAO.getAddressForObjectID(idObjekta));
        cont.areaOpisTerena.setText(izvjestajDAO.getDescriptionForReportID(idTrenutnogIzvjestaja));
        int idVlasnika = objekatDAO.getOwnerForID(idObjekta);
        cont.labVlasnik.setText(vlasnikDAO.getNameLastNameForID(idVlasnika));
        int idPrvogSvjedoka = svjedokDAO.getFirstWitnessID(idTrenutnogIzvjestaja);
        int idDrugogSvjedoka = svjedokDAO.getSecondWitnessID(idTrenutnogIzvjestaja);
        cont.labPodaciPrvogSvjedoka.setText(svjedokDAO.getWitnessName(idPrvogSvjedoka) + " " + svjedokDAO.getWitnessSurename(idPrvogSvjedoka) + ", (" + svjedokDAO.getWitnessJMBG(idPrvogSvjedoka) + ")");
        cont.areaIzjavaPrvogSvjedoka.setText(svjedokDAO.getWitnessStatementForWitness(idPrvogSvjedoka));
        cont.labPodaciDrugogSvjedoka.setText(svjedokDAO.getWitnessName(idDrugogSvjedoka) + " " + svjedokDAO.getWitnessSurename(idDrugogSvjedoka) + ", (" + svjedokDAO.getWitnessJMBG(idDrugogSvjedoka) + ")");
        cont.areaIzjavaDrugogSvjedoka.setText(svjedokDAO.getWitnessStatementForWitness(idDrugogSvjedoka));
        cont.labDatumInspekcije.setText(izvjestajDAO.getInspectionDateForReportID(idTrenutnogIzvjestaja));
        boolean izdatNalog = izvjestajDAO.violationBooked(idTrenutnogIzvjestaja);
        if(izdatNalog) {
            cont.labNalog.setText("Izdat");
            cont.labPrekršaj.setText(izvjestajDAO.getViolationForReportID(idTrenutnogIzvjestaja));
            cont.labNovcanaKazna.setText(izvjestajDAO.getFineForReportID(idTrenutnogIzvjestaja) + " KM");
            cont.labDodatniZahtjevi.setText(izvjestajDAO.getAdditionalRequirementsForReportID(idTrenutnogIzvjestaja));
        } else {
            cont.labNalog.setText("Nije izdat");
            cont.labPrekršaj.setText("Nije počinjen prekršaj");
            cont.labNovcanaKazna.setText("Nije izdata novčana kazna");
            cont.labDodatniZahtjevi.setText("Nema dodatnih zahtjeva");
        }
        int brojEvidentiranihRadnika = izvjestajDAO.getRecordedWorkersForReportID(idTrenutnogIzvjestaja);
        if(brojEvidentiranihRadnika!=0){
            cont.labRadnici.setText("Podnesena evidencija o " + brojEvidentiranihRadnika + " radnika na crno");
        }else{
            cont.labRadnici.setText("Nisu evidentirani radnici na crno");
        }
        if(izvjestajDAO.getCriminalOffenseForReportID(idTrenutnogIzvjestaja)==1){
            cont.labKrivicnoDjelo.setText("Podnesena je prijava o krivičnom djelu");
        }else{
            cont.labKrivicnoDjelo.setText("Nije podnesena prijava o krivičnom djelu");
        }
        if(izvjestajDAO.getPhytocertificateForReportID(idTrenutnogIzvjestaja)==1) cont.labFitocertifikat.setText("Izdat je fitocertifikat");
        else cont.labFitocertifikat.setText("Nije izdat fitocertifikat");
        if(izvjestajDAO.isUzetUzorakZaID(idTrenutnogIzvjestaja)) cont.labUzetUzorak.setText("Uzet uzorak na GP/CI/MC");
        else cont.labUzetUzorak.setText("Nije uzet uzorak na GP/CI/MC");
        cont.labZabranaRada.setText(izvjestajDAO.getWorkProhibitionForReportID(idTrenutnogIzvjestaja));
        boolean prijavljenoRadiliste = izvjestajDAO.isReportedWorksite(idTrenutnogIzvjestaja);
        if(prijavljenoRadiliste){
            cont.labPrijavljenoRadiliste.setText("Da");
            cont.labBrojZaposlenih.setText(String.valueOf(izvjestajDAO.getEmployeeNumberForReportID(idTrenutnogIzvjestaja)));
            cont.labPotvrdaORadu.setText(String.valueOf(izvjestajDAO.getOpeningCertificateNumberForReportID(idTrenutnogIzvjestaja)));
        }else{
            cont.labPrijavljenoRadiliste.setText("Ne");
            cont.labBrojZaposlenih.setText("Nema zaposlenih");
            cont.labPotvrdaORadu.setText("Nema potvrdu za rad");
        }
        if(izvjestajDAO.getDefectForReportID(idTrenutnogIzvjestaja).isBlank()) {
            cont.labRjesenjeNedostatak.setText("Nije izdato");
            cont.labNedostatak.setText("N/A");
        }
        else {
            cont.labRjesenjeNedostatak.setText("Izdato");
            cont.labNedostatak.setText(izvjestajDAO.getDefectForReportID(idTrenutnogIzvjestaja));
        }
        myStage.setResizable(false);
        myStage.setTitle("Pregledaj izvještaj");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void obrisiIzvjestaj(ActionEvent actionEvent) {
        izvjestajDAO.deleteReportWithID(idTrenutnogIzvjestaja);
        refresujIzvjestaj();
    }

    public void modifikujIzvjestaj(ActionEvent actionEvent) throws IOException {
        if(idTrenutnogIzvjestaja == -1) return;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifikujIzvjestaj.fxml"));
        Parent root = loader.load();
        ModifikujIzvjestajController cont = loader.getController();
        int idObjekta = izvjestajDAO.getObjectIDForReport(idTrenutnogIzvjestaja);
        cont.fldNazivObjekta.setText(objekatDAO.getNameForID(idObjekta));
        cont.fldAdresaObjekta.setText(objekatDAO.getAddressForObjectID(idObjekta));
        cont.datumInspekcije.getEditor().setText(izvjestajDAO.getInspectionDateForReportID(idTrenutnogIzvjestaja));
        cont.opisTerena.setText(izvjestajDAO.getDescriptionForReportID(idTrenutnogIzvjestaja));
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
        if(izvjestajDAO.getViolationForReportID(idTrenutnogIzvjestaja).isBlank()){
            cont.cbPrekrsaj.setSelected(false);
        }else{
            cont.cbPrekrsaj.setSelected(true);
            cont.fldPrekrsaj.setText(izvjestajDAO.getViolationForReportID(idTrenutnogIzvjestaja));
            cont.fldKazna.setText(izvjestajDAO.getFineForReportID(idTrenutnogIzvjestaja));
            cont.fldZahtjevi.setText(izvjestajDAO.getAdditionalRequirementsForReportID(idTrenutnogIzvjestaja));
        }
        if(izvjestajDAO.getRecordedWorkersForReportID(idTrenutnogIzvjestaja)!=0){
            cont.cbEvidencijaRadnika.setSelected(true);
            cont.fldBrojRadnika.setText(String.valueOf(izvjestajDAO.getRecordedWorkersForReportID(idTrenutnogIzvjestaja)));
        }else{
            cont.cbEvidencijaRadnika.setSelected(false);
        }
        if(izvjestajDAO.getCriminalOffenseForReportID(idTrenutnogIzvjestaja)==1){
            cont.cbKrivicnoDjelo.setSelected(true);
        }
        if(izvjestajDAO.getPhytocertificateForReportID(idTrenutnogIzvjestaja)==1) cont.cbFitocertifikat.setSelected(true);
        if(izvjestajDAO.isUzetUzorakZaID(idTrenutnogIzvjestaja)) cont.cbUzorak.setSelected(true);
        else cont.cbUzorak.setSelected(false);
        if(izvjestajDAO.getDaysClosedForReportID(idTrenutnogIzvjestaja)!=0){
            cont.cbZabranaRada.setSelected(true);
            cont.rbBrojDana.setSelected(true);
            cont.fldBrojDanaZabrane.setText(String.valueOf(izvjestajDAO.getDaysClosedForReportID(idTrenutnogIzvjestaja)));
            cont.fldUslovZabrane.setDisable(true);
        }else if(!izvjestajDAO.getOpeningConditionsForReportID(idTrenutnogIzvjestaja).isBlank()){
            cont.cbZabranaRada.setSelected(true);
            cont.rbIspunjenjeUslova.setSelected(true);
            cont.fldBrojDanaZabrane.setDisable(true);
            cont.fldUslovZabrane.setText(izvjestajDAO.getOpeningConditionsForReportID(idTrenutnogIzvjestaja));
        }
        if(izvjestajDAO.isReportedWorksite(idTrenutnogIzvjestaja)){
            int idVlasnika = objekatDAO.getOwnerForID(idObjekta);
            cont.cbPrijaviRadiliste.setSelected(true);
            cont.fldVlasnik.setText(vlasnikDAO.getNameLastNameForID(idVlasnika));
            cont.fldBrojZaposlenih.setText(String.valueOf(izvjestajDAO.getEmployeeNumberForReportID(idTrenutnogIzvjestaja)));
            cont.fldPotvrdaORadu.setText(String.valueOf(izvjestajDAO.getOpeningCertificateNumberForReportID(idTrenutnogIzvjestaja)));
        }else{
            cont.cbPrijaviRadiliste.setSelected(false);
            cont.fldVlasnik.setDisable(true);
            cont.fldBrojZaposlenih.setDisable(true);
            cont.fldPotvrdaORadu.setDisable(true);
        }
        if(!izvjestajDAO.getDefectForReportID(idTrenutnogIzvjestaja).isBlank()){
            cont.cbNedostatak.setSelected(true);
            cont.fldNedostatak.setText(izvjestajDAO.getDefectForReportID(idTrenutnogIzvjestaja));
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
            listaIzvjestaja.setItems(izvjestajDAO.getReportsForInspectorID(idInspektora));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) listaIzvjestaja.getScene().getWindow();
        stage.close();
    }
}
