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

public class ReportsController {
    public int inspectorId;
    public Label labObjectOwner;
    public Label labObjectStatus;
    public Label labUniqueID;
    public Label labObjectNameAddress;
    public Label labInspectionDate;
    private ReportDAO reportDAO;
    public ListView reportsList;
    private int currentReportId =- 1;
    private ObjectDAO objectDAO;
    private WitnessDAO witnessDAO;
    private OwnerDAO ownerDAO;

    @FXML
    public void initialize() throws SQLException {
        reportDAO = ReportDAO.getInstance();
        ownerDAO = OwnerDAO.getInstance();
        objectDAO = ObjectDAO.getInstance();
        witnessDAO = WitnessDAO.getInstance();
        reportsList.setItems(reportDAO.getReportsForInspectorID(inspectorId));
        reportsList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Report newReport = (Report) newItem;
            if(newReport != null){
                currentReportId = newReport.getId();
                int objectId = reportDAO.getObjectIDForReport(currentReportId);
                labObjectNameAddress.setText(objectDAO.getNameForID(objectId) + ", " + objectDAO.getAddressForObjectID(objectId));
                int ownerId = objectDAO.getOwnerForID(objectId);
                labObjectOwner.setText(ownerDAO.getNameLastNameForID(ownerId));
                labInspectionDate.setText(reportDAO.getInspectionDateForReportID(currentReportId));
                labObjectStatus.setText("In function");
                labUniqueID.setText(reportDAO.getUniqueIDForReportID(currentReportId));
            }else{

            }
        });
    }

    public int getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(int inspectorId) {
        this.inspectorId = inspectorId;
    }

    public void reportDetailsBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reportDetails.fxml"));
        Parent root = loader.load();
        PregledIzvjestajaController cont = loader.getController();

        int objectId = reportDAO.getObjectIDForReport(currentReportId);
        cont.labObjekat.setText(objectDAO.getNameForID(objectId));
        cont.labVrstaObjekta.setText("Kafana");
        cont.labAdresaObjekta.setText(objectDAO.getAddressForObjectID(objectId));
        cont.areaOpisTerena.setText(reportDAO.getDescriptionForReportID(currentReportId));
        int ownerId = objectDAO.getOwnerForID(objectId);
        cont.labVlasnik.setText(ownerDAO.getNameLastNameForID(ownerId));
        int firstWitnessID = witnessDAO.getFirstWitnessID(currentReportId);
        int secondWitnessID = witnessDAO.getSecondWitnessID(currentReportId);
        cont.labPodaciPrvogSvjedoka.setText(witnessDAO.getWitnessName(firstWitnessID) + " " + witnessDAO.getWitnessSurename(firstWitnessID) + ", (" + witnessDAO.getWitnessJMBG(firstWitnessID) + ")");
        cont.areaIzjavaPrvogSvjedoka.setText(witnessDAO.getWitnessStatementForWitness(firstWitnessID));
        cont.labPodaciDrugogSvjedoka.setText(witnessDAO.getWitnessName(secondWitnessID) + " " + witnessDAO.getWitnessSurename(secondWitnessID) + ", (" + witnessDAO.getWitnessJMBG(secondWitnessID) + ")");
        cont.areaIzjavaDrugogSvjedoka.setText(witnessDAO.getWitnessStatementForWitness(secondWitnessID));
        cont.labDatumInspekcije.setText(reportDAO.getInspectionDateForReportID(currentReportId));
        boolean violationIssued = reportDAO.violationBooked(currentReportId);
        if(violationIssued) {
            cont.labNalog.setText("Issued");
            cont.labPrekršaj.setText(reportDAO.getViolationForReportID(currentReportId));
            cont.labNovcanaKazna.setText(reportDAO.getFineForReportID(currentReportId) + " KM");
            cont.labDodatniZahtjevi.setText(reportDAO.getAdditionalRequirementsForReportID(currentReportId));
        } else {
            cont.labNalog.setText("Not issued");
            cont.labPrekršaj.setText("Violation hasn't been commited");
            cont.labNovcanaKazna.setText("No fines have been issued");
            cont.labDodatniZahtjevi.setText("No additional requirements");
        }
        int workersReported = reportDAO.getRecordedWorkersForReportID(currentReportId);
        if(workersReported!=0){
            cont.labRadnici.setText("Reported " + workersReported + " worker/s");
        }else{
            cont.labRadnici.setText("No workers reported");
        }
        if(reportDAO.getCriminalOffenseForReportID(currentReportId)==1){
            cont.labKrivicnoDjelo.setText("Criminal report issued");
        }else{
            cont.labKrivicnoDjelo.setText("Criminal report not issued");
        }
        if(reportDAO.getPhytocertificateForReportID(currentReportId)==1) cont.labFitocertifikat.setText("Phytocertificate issued");
        else cont.labFitocertifikat.setText("Phytocertificate not issued");
        if(reportDAO.isUzetUzorakZaID(currentReportId)) cont.labUzetUzorak.setText("GP/CI/MC sample taken");
        else cont.labUzetUzorak.setText("GP/CI/MC sample not taken");
        cont.labZabranaRada.setText(reportDAO.getWorkProhibitionForReportID(currentReportId));
        boolean prijavljenoRadiliste = reportDAO.isReportedWorksite(currentReportId);
        if(prijavljenoRadiliste){
            cont.labPrijavljenoRadiliste.setText("Yes");
            cont.labBrojZaposlenih.setText(String.valueOf(reportDAO.getEmployeeNumberForReportID(currentReportId)));
            cont.labPotvrdaORadu.setText(String.valueOf(reportDAO.getOpeningCertificateNumberForReportID(currentReportId)));
        }else{
            cont.labPrijavljenoRadiliste.setText("No");
            cont.labBrojZaposlenih.setText("No employees");
            cont.labPotvrdaORadu.setText("No certificate to work freely");
        }
        if(reportDAO.getDefectForReportID(currentReportId).isBlank()) {
            cont.labRjesenjeNedostatak.setText("Not issued");
            cont.labNedostatak.setText("No defects found");
        }
        else {
            cont.labRjesenjeNedostatak.setText("Issued");
            cont.labNedostatak.setText(reportDAO.getDefectForReportID(currentReportId));
        }
        myStage.setResizable(false);
        myStage.setTitle("Report details");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void deleteReportBtn(ActionEvent actionEvent) {
        reportDAO.deleteReportWithID(currentReportId);
        refreshReports();
    }

    public void modifyReportBtn(ActionEvent actionEvent) throws IOException {
        if(currentReportId == -1) return;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifikujIzvjestaj.fxml"));
        Parent root = loader.load();
        ModifikujIzvjestajController cont = loader.getController();

        int objectId = reportDAO.getObjectIDForReport(currentReportId);
        cont.fldNazivObjekta.setText(objectDAO.getNameForID(objectId));
        cont.fldAdresaObjekta.setText(objectDAO.getAddressForObjectID(objectId));
        cont.datumInspekcije.getEditor().setText(reportDAO.getInspectionDateForReportID(currentReportId));
        cont.opisTerena.setText(reportDAO.getDescriptionForReportID(currentReportId));
        int firstWitnessID = witnessDAO.getFirstWitnessID(currentReportId);
        int secondWitnessID = witnessDAO.getSecondWitnessID(currentReportId);
        cont.s1Ime.setText(witnessDAO.getWitnessName(firstWitnessID));
        cont.s1prezime.setText(witnessDAO.getWitnessSurename(firstWitnessID));
        cont.s1JMBG.setText(witnessDAO.getWitnessJMBG(firstWitnessID));
        cont.s1Izjava.setText(witnessDAO.getWitnessStatementForWitness(firstWitnessID));
        cont.s2ime.setText(witnessDAO.getWitnessName(secondWitnessID));
        cont.s2prezime.setText(witnessDAO.getWitnessSurename(secondWitnessID));
        cont.s2JMBG.setText(witnessDAO.getWitnessJMBG(secondWitnessID));
        cont.s2Izjava.setText(witnessDAO.getWitnessStatementForWitness(secondWitnessID));
        if(reportDAO.getViolationForReportID(currentReportId).isBlank()){
            cont.cbPrekrsaj.setSelected(false);
        }else{
            cont.cbPrekrsaj.setSelected(true);
            cont.fldPrekrsaj.setText(reportDAO.getViolationForReportID(currentReportId));
            cont.fldKazna.setText(reportDAO.getFineForReportID(currentReportId));
            cont.fldZahtjevi.setText(reportDAO.getAdditionalRequirementsForReportID(currentReportId));
        }
        if(reportDAO.getRecordedWorkersForReportID(currentReportId)!=0){
            cont.cbEvidencijaRadnika.setSelected(true);
            cont.fldBrojRadnika.setText(String.valueOf(reportDAO.getRecordedWorkersForReportID(currentReportId)));
        }else{
            cont.cbEvidencijaRadnika.setSelected(false);
        }
        if(reportDAO.getCriminalOffenseForReportID(currentReportId)==1){
            cont.cbKrivicnoDjelo.setSelected(true);
        }
        if(reportDAO.getPhytocertificateForReportID(currentReportId)==1) cont.cbFitocertifikat.setSelected(true);
        if(reportDAO.isUzetUzorakZaID(currentReportId)) cont.cbUzorak.setSelected(true);
        else cont.cbUzorak.setSelected(false);
        if(reportDAO.getDaysClosedForReportID(currentReportId)!=0){
            cont.cbZabranaRada.setSelected(true);
            cont.rbBrojDana.setSelected(true);
            cont.fldBrojDanaZabrane.setText(String.valueOf(reportDAO.getDaysClosedForReportID(currentReportId)));
            cont.fldUslovZabrane.setDisable(true);
        }else if(!reportDAO.getOpeningConditionsForReportID(currentReportId).isBlank()){
            cont.cbZabranaRada.setSelected(true);
            cont.rbIspunjenjeUslova.setSelected(true);
            cont.fldBrojDanaZabrane.setDisable(true);
            cont.fldUslovZabrane.setText(reportDAO.getOpeningConditionsForReportID(currentReportId));
        }
        if(reportDAO.isReportedWorksite(currentReportId)){
            int ownerId = objectDAO.getOwnerForID(objectId);
            cont.cbPrijaviRadiliste.setSelected(true);
            cont.fldVlasnik.setText(ownerDAO.getNameLastNameForID(ownerId));
            cont.fldBrojZaposlenih.setText(String.valueOf(reportDAO.getEmployeeNumberForReportID(currentReportId)));
            cont.fldPotvrdaORadu.setText(String.valueOf(reportDAO.getOpeningCertificateNumberForReportID(currentReportId)));
        }else{
            cont.cbPrijaviRadiliste.setSelected(false);
            cont.fldVlasnik.setDisable(true);
            cont.fldBrojZaposlenih.setDisable(true);
            cont.fldPotvrdaORadu.setDisable(true);
        }
        if(!reportDAO.getDefectForReportID(currentReportId).isBlank()){
            cont.cbNedostatak.setSelected(true);
            cont.fldNedostatak.setText(reportDAO.getDefectForReportID(currentReportId));
        }else{
            cont.cbNedostatak.setSelected(false);
            cont.fldNedostatak.setDisable(true);
        }
        cont.idOtvorenogIzvjestaja = currentReportId;
        myStage.setTitle("Modify report");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        refreshReports();
    }

    public void refreshReports() {
        try {
            reportsList.setItems(reportDAO.getReportsForInspectorID(inspectorId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) reportsList.getScene().getWindow();
        stage.close();
    }
}
