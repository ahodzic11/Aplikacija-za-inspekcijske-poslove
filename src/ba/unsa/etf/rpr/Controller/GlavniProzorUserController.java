package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.Model.Report;
import ba.unsa.etf.rpr.Model.Task;
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
    private ReportDAO izvjestajDAO;
    private UserDAO prijavljeniDao;
    private int idTrenutnogIzvjestaja = -1;
    private ObjectDAO objekatDao;
    private OwnerDAO vlasnikDao;
    private TaskDAO terminDao;
    private InspectorDAO inspektorDao;
    private LoginLogDAO logDAO;
    private int idTrenutnogTermina = -1;

    @FXML
    public void initialize() throws SQLException {
        svjedokDAO = WitnessDAO.getInstance();
        prijavljeniDao = UserDAO.getInstance();
        izvjestajDAO = ReportDAO.getInstance();
        objekatDao = ObjectDAO.getInstance();
        vlasnikDao = OwnerDAO.getInstance();
        terminDao = TaskDAO.getInstance();
        inspektorDao = InspectorDAO.getInstance();
        logDAO = LoginLogDAO.getInstance();
        listaIzvjestaja.setItems(izvjestajDAO.getReportsForInspectorID(prijavljeniDao.getLoggedUserID()));
        listaIzvjestaja.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Report noviIzvjestaj = (Report) newItem;
            if(noviIzvjestaj!=null)
                idTrenutnogIzvjestaja = noviIzvjestaj.getId();
        });
        listaTermina.setItems(terminDao.getAllTasks());
        listaTermina.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Task noviTermin = (Task) newItem;
            if(noviTermin != null){
                idTrenutnogTermina = noviTermin.getId();
            }
        });
        rbSviTermini.setSelected(true);
        rbSviTermini.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                listaTermina.setItems(terminDao.getAllTasks());
        });
        rbMojiTermini.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                listaTermina.setItems(terminDao.getAllTasksForInspector(prijavljeniDao.getLoggedUserID()));
        });
    }

    public void kreirajIzvjestaj(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/objectPicking.fxml"));
        myStage.setTitle("Odaberi objekat");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        refreshReports();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        logDAO.logout(inspektorDao.getUniqueIDForID(prijavljeniDao.getLoggedUserID()));
        prijavljeniDao.deleteLoggedUser();
        Stage stage = (Stage) listaIzvjestaja.getScene().getWindow();
        stage.close();
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        //myStage.initStyle(StageStyle.UNDECORATED);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) listaIzvjestaja.getScene().getWindow();
        stage.close();
    }

    public void deleteReportBtn(ActionEvent actionEvent) {
        izvjestajDAO.deleteReportWithID(idTrenutnogIzvjestaja);
        refreshReports();
    }

    private void refreshReports() {
        try {
            listaIzvjestaja.setItems(izvjestajDAO.getReportsForInspectorID(prijavljeniDao.getLoggedUserID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyReportBtn(ActionEvent actionEvent) throws IOException {
        if(idTrenutnogIzvjestaja == -1) return;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifikujIzvjestaj.fxml"));
        Parent root = loader.load();
        ModifikujIzvjestajController cont = loader.getController();
        int idObjekta = izvjestajDAO.getObjectIDForReport(idTrenutnogIzvjestaja);
        cont.fldNazivObjekta.setText(objekatDao.getNameForID(idObjekta));
        cont.fldAdresaObjekta.setText(objekatDao.getAddressForObjectID(idObjekta));
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
            int idVlasnika = objekatDao.getOwnerForID(idObjekta);
            cont.cbPrijaviRadiliste.setSelected(true);
            cont.fldVlasnik.setText(vlasnikDao.getNameLastNameForID(idVlasnika));
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
        refreshReports();
    }

    public void pogledajIzvjestajBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reportDetails.fxml"));
        Parent root = loader.load();
        PregledIzvjestajaController cont = loader.getController();
        int idObjekta = izvjestajDAO.getObjectIDForReport(idTrenutnogIzvjestaja);
        cont.labObject.setText(objekatDao.getNameForID(idObjekta));
        cont.labObjectType.setText("Kafana");
        cont.labObjectAddress.setText(objekatDao.getAddressForObjectID(idObjekta));
        cont.areaInspectorStatement.setText(izvjestajDAO.getDescriptionForReportID(idTrenutnogIzvjestaja));
        int idVlasnika = objekatDao.getOwnerForID(idObjekta);
        cont.labOwner.setText(vlasnikDao.getNameLastNameForID(idVlasnika));
        int idPrvogSvjedoka = svjedokDAO.getFirstWitnessID(idTrenutnogIzvjestaja);
        int idDrugogSvjedoka = svjedokDAO.getSecondWitnessID(idTrenutnogIzvjestaja);
        cont.labFirstWitnessInfo.setText(svjedokDAO.getWitnessName(idPrvogSvjedoka) + " " + svjedokDAO.getWitnessSurename(idPrvogSvjedoka) + ", (" + svjedokDAO.getWitnessJMBG(idPrvogSvjedoka) + ")");
        cont.areaFirstWitnessStatement.setText(svjedokDAO.getWitnessStatementForWitness(idPrvogSvjedoka));
        cont.labSecondWitnessInfo.setText(svjedokDAO.getWitnessName(idDrugogSvjedoka) + " " + svjedokDAO.getWitnessSurename(idDrugogSvjedoka) + ", (" + svjedokDAO.getWitnessJMBG(idDrugogSvjedoka) + ")");
        cont.areaSecondWitnessStatement.setText(svjedokDAO.getWitnessStatementForWitness(idDrugogSvjedoka));
        cont.labInspectionDate.setText(izvjestajDAO.getInspectionDateForReportID(idTrenutnogIzvjestaja));
        boolean izdatNalog = izvjestajDAO.violationBooked(idTrenutnogIzvjestaja);
        if(izdatNalog) {
            cont.labMisdemeanor.setText("Izdat");
            cont.labViolation.setText(izvjestajDAO.getViolationForReportID(idTrenutnogIzvjestaja));
            cont.labFine.setText(izvjestajDAO.getFineForReportID(idTrenutnogIzvjestaja) + " KM");
            cont.labAdditionalRequirements.setText(izvjestajDAO.getAdditionalRequirementsForReportID(idTrenutnogIzvjestaja));
        } else {
            cont.labMisdemeanor.setText("Nije izdat");
            cont.labViolation.setText("Nije počinjen prekršaj");
            cont.labFine.setText("Nije izdata novčana kazna");
            cont.labAdditionalRequirements.setText("Nema dodatnih zahtjeva");
        }
        int brojEvidentiranihRadnika = izvjestajDAO.getRecordedWorkersForReportID(idTrenutnogIzvjestaja);
        if(brojEvidentiranihRadnika!=0){
            cont.labWorkers.setText("Podnesena evidencija o " + brojEvidentiranihRadnika + " radnika na crno");
        }else{
            cont.labWorkers.setText("Nisu evidentirani radnici na crno");
        }
        if(izvjestajDAO.getCriminalOffenseForReportID(idTrenutnogIzvjestaja)==1){
            cont.labCriminalOffense.setText("Podnesena je prijava o krivičnom djelu");
        }else{
            cont.labCriminalOffense.setText("Nije podnesena prijava o krivičnom djelu");
        }
        if(izvjestajDAO.getPhytocertificateForReportID(idTrenutnogIzvjestaja)==1) cont.labPhytocertificate.setText("Izdat je fitocertifikat");
        else cont.labPhytocertificate.setText("Nije izdat fitocertifikat");
        if(izvjestajDAO.isUzetUzorakZaID(idTrenutnogIzvjestaja)) cont.labSampleTaken.setText("Uzet uzorak na GP/CI/MC");
        else cont.labSampleTaken.setText("Nije uzet uzorak na GP/CI/MC");
        cont.labWorkBan.setText(izvjestajDAO.getWorkProhibitionForReportID(idTrenutnogIzvjestaja));
        boolean prijavljenoRadiliste = izvjestajDAO.isReportedWorksite(idTrenutnogIzvjestaja);
        if(prijavljenoRadiliste){
            cont.labReportedWorksite.setText("Da");
            cont.labEmployeeNumber.setText(String.valueOf(izvjestajDAO.getEmployeeNumberForReportID(idTrenutnogIzvjestaja)));
            cont.labWorkPermit.setText(String.valueOf(izvjestajDAO.getOpeningCertificateNumberForReportID(idTrenutnogIzvjestaja)));
        }else{
            cont.labReportedWorksite.setText("Ne");
            cont.labEmployeeNumber.setText("Nema zaposlenih");
            cont.labWorkPermit.setText("Nema potvrdu za rad");
        }
        if(izvjestajDAO.getDefectForReportID(idTrenutnogIzvjestaja).isBlank()) {
            cont.labDefectIssued.setText("Nije izdato");
            cont.labDefect.setText("N/A");
        }
        else {
            cont.labDefectIssued.setText("Izdato");
            cont.labDefect.setText(izvjestajDAO.getDefectForReportID(idTrenutnogIzvjestaja));
        }
        cont.labUniqueID.setText(izvjestajDAO.getUniqueIDForReportID(idTrenutnogIzvjestaja));
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
        int idInspektora = terminDao.getInspectorForID(idTrenutnogTermina);
        int idObjekta = terminDao.getObjectID(idTrenutnogTermina);
        cont.labTerminZakazao.setText(inspektorDao.getNameSurenameForID(idInspektora));
        cont.labNazivObjekta.setText(objekatDao.getNameForID(idObjekta));
        cont.labAdresaObjekta.setText(objekatDao.getAddressForObjectID(idObjekta));
        cont.labDatumVrijemeTermina.setText(terminDao.getDatetime(idTrenutnogTermina));
        cont.areaNapomeneTermina.setText(terminDao.getNotesForTask(idTrenutnogTermina));
        int idZaduzenogInspektora = terminDao.getAssignedInspectorID(idTrenutnogTermina);
        System.out.println(idZaduzenogInspektora);
        if(idZaduzenogInspektora != -1) cont.labZaduzeniInspektor.setText(inspektorDao.getNameSurenameForID(idZaduzenogInspektora));
        else cont.labZaduzeniInspektor.setText("Nema zaduženog inspektora");
        boolean terminObavljen = terminDao.isCompleted(idTrenutnogTermina);
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
        int idObjekta = terminDao.getObjectID(idTrenutnogTermina);
        cont.fldNazivObjekta.setText(objekatDao.getNameForID(idObjekta));
        cont.fldAdresaObjekta.setText(objekatDao.getAddressForObjectID(idObjekta));
        cont.datePickTermin.getEditor().setText(terminDao.getDatetime(idTrenutnogTermina));
        cont.areaNapomene.setText(terminDao.getNotesForTask(idTrenutnogTermina));
        cont.fldSati.setText("");
        cont.fldMinuta.setText("");
        cont.idTermina = idTrenutnogTermina;
        myStage.setTitle("Modifikuj termin");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        refreshTasks();
    }

    public void obrisiTerminBtn(ActionEvent actionEvent) {
        terminDao.deleteTask(idTrenutnogTermina);
        refreshTasks();
    }

    private void refreshTasks() {
        listaTermina.setItems(terminDao.getAllTasksForInspector(prijavljeniDao.getLoggedUserID()));
    }

    public void uzmiTerminBtn(ActionEvent actionEvent) {
        if(!terminDao.isTaken(idTrenutnogTermina))terminDao.takeTask(idTrenutnogTermina, prijavljeniDao.getLoggedUserID());
    }
}
