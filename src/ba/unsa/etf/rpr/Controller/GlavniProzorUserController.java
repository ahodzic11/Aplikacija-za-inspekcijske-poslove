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
    public ListView reportList;
    public ListView tasksList;
    public RadioButton rbAllTasks;
    public RadioButton rbMyTasks;
    private WitnessDAO witnessDAO;
    private ReportDAO reportDAO;
    private UserDAO userDAO;
    private int currentReportID = -1;
    private ObjectDAO objectDAO;
    private OwnerDAO ownerDAO;
    private TaskDAO taskDAO;
    private InspectorDAO inspectorDAO;
    private LoginLogDAO loginLogDAO;
    private int currentTaskID = -1;

    @FXML
    public void initialize() throws SQLException {
        witnessDAO = WitnessDAO.getInstance();
        userDAO = UserDAO.getInstance();
        reportDAO = ReportDAO.getInstance();
        objectDAO = ObjectDAO.getInstance();
        ownerDAO = OwnerDAO.getInstance();
        taskDAO = TaskDAO.getInstance();
        inspectorDAO = InspectorDAO.getInstance();
        loginLogDAO = LoginLogDAO.getInstance();
        reportList.setItems(reportDAO.getReportsForInspectorID(userDAO.getLoggedUserID()));
        reportList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Report newReport = (Report) newItem;
            if(newReport!=null)
                currentReportID = newReport.getId();
        });
        tasksList.setItems(taskDAO.getAllTasks());
        tasksList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Task newTask = (Task) newItem;
            if(newTask != null){
                currentTaskID = newTask.getId();
            }
        });
        rbAllTasks.setSelected(true);
        rbAllTasks.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                tasksList.setItems(taskDAO.getAllTasks());
        });
        rbMyTasks.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                tasksList.setItems(taskDAO.getAllTasksForInspector(userDAO.getLoggedUserID()));
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
        loginLogDAO.logout(inspectorDAO.getUniqueIDForID(userDAO.getLoggedUserID()));
        userDAO.deleteLoggedUser();
        Stage stage = (Stage) reportList.getScene().getWindow();
        stage.close();
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        //myStage.initStyle(StageStyle.UNDECORATED);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) reportList.getScene().getWindow();
        stage.close();
    }

    public void deleteReportBtn(ActionEvent actionEvent) {
        reportDAO.deleteReportWithID(currentReportID);
        refreshReports();
    }

    private void refreshReports() {
        try {
            reportList.setItems(reportDAO.getReportsForInspectorID(userDAO.getLoggedUserID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyReportBtn(ActionEvent actionEvent) throws IOException {
        if(currentReportID == -1) return;
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifyReport.fxml"));
        Parent root = loader.load();
        ModifyReportController cont = loader.getController();
        int idObjekta = reportDAO.getObjectIDForReport(currentReportID);
        cont.fldObjectName.setText(objectDAO.getNameForID(idObjekta));
        cont.fldObjectAddress.setText(objectDAO.getAddressForObjectID(idObjekta));
        cont.inspectionDate.getEditor().setText(reportDAO.getInspectionDateForReportID(currentReportID));
        cont.areaInspectorsStatement.setText(reportDAO.getDescriptionForReportID(currentReportID));
        int idPrvogSvjedoka = witnessDAO.getFirstWitnessID(currentReportID);
        int idDrugogSvjedoka = witnessDAO.getSecondWitnessID(currentReportID);
        cont.fldFirstWitnessName.setText(witnessDAO.getWitnessName(idPrvogSvjedoka));
        cont.fldFirstWitnessSurename.setText(witnessDAO.getWitnessSurename(idPrvogSvjedoka));
        cont.fldFirstWitnessJMBG.setText(witnessDAO.getWitnessJMBG(idPrvogSvjedoka));
        cont.areaFirstWitnessStatement.setText(witnessDAO.getWitnessStatementForWitness(idPrvogSvjedoka));
        cont.fldSecondWitnessName.setText(witnessDAO.getWitnessName(idDrugogSvjedoka));
        cont.fldSecondWitnessSurename.setText(witnessDAO.getWitnessSurename(idDrugogSvjedoka));
        cont.fldSecondWitnessJMBG.setText(witnessDAO.getWitnessJMBG(idDrugogSvjedoka));
        cont.areaSecondWitnessStatement.setText(witnessDAO.getWitnessStatementForWitness(idDrugogSvjedoka));
        if(reportDAO.getViolationForReportID(currentReportID).isBlank()){
            cont.cbViolation.setSelected(false);
        }else{
            cont.cbViolation.setSelected(true);
            cont.fldMisdemeanor.setText(reportDAO.getViolationForReportID(currentReportID));
            cont.fldFine.setText(reportDAO.getFineForReportID(currentReportID));
            cont.fldAdditionalRequirements.setText(reportDAO.getAdditionalRequirementsForReportID(currentReportID));
        }
        if(reportDAO.getRecordedWorkersForReportID(currentReportID)!=0){
            cont.cbWorkersReported.setSelected(true);
            cont.fldReportedWorkers.setText(String.valueOf(reportDAO.getRecordedWorkersForReportID(currentReportID)));
        }else{
            cont.cbWorkersReported.setSelected(false);
        }
        if(reportDAO.getCriminalOffenseForReportID(currentReportID)==1){
            cont.cbCriminalOffense.setSelected(true);
        }
        if(reportDAO.getPhytocertificateForReportID(currentReportID)==1) cont.cbPhytocertificate.setSelected(true);
        if(reportDAO.sampleTakenForID(currentReportID)) cont.cbSampleTaken.setSelected(true);
        else cont.cbSampleTaken.setSelected(false);
        if(reportDAO.getDaysClosedForReportID(currentReportID)!=0){
            cont.cbWorkBan.setSelected(true);
            cont.rbDaysClosed.setSelected(true);
            cont.fldDaysClosed.setText(String.valueOf(reportDAO.getDaysClosedForReportID(currentReportID)));
            cont.fldConditionClosed.setDisable(true);
            cont.fldDaysClosed.setDisable(false);
        }else if(!reportDAO.getOpeningConditionsForReportID(currentReportID).isBlank()){
            cont.cbWorkBan.setSelected(true);
            cont.rbConditionClosed.setSelected(true);
            cont.fldDaysClosed.setDisable(true);
            cont.fldConditionClosed.setText(reportDAO.getOpeningConditionsForReportID(currentReportID));
        }
        if(reportDAO.isReportedWorksite(currentReportID)){
            int idVlasnika = objectDAO.getOwnerForID(idObjekta);
            cont.cbReportWorkplace.setSelected(true);
            cont.fldOwner.setText(ownerDAO.getNameLastNameForID(idVlasnika));
            cont.fldEmployeeNumber.setText(String.valueOf(reportDAO.getEmployeeNumberForReportID(currentReportID)));
            cont.fldWorkPermit.setText(String.valueOf(reportDAO.getOpeningCertificateNumberForReportID(currentReportID)));
        }else{
            cont.cbReportWorkplace.setSelected(false);
            cont.fldOwner.setDisable(true);
            cont.fldEmployeeNumber.setDisable(true);
            cont.fldWorkPermit.setDisable(true);
        }
        if(!reportDAO.getDefectForReportID(currentReportID).isBlank()){
            cont.cbDefect.setSelected(true);
            cont.fldDefect.setText(reportDAO.getDefectForReportID(currentReportID));
        }else{
            cont.cbDefect.setSelected(false);
            cont.fldDefect.setDisable(true);
        }
        if(reportDAO.getViolationForReportID(currentReportID).isBlank()) {
            cont.fldMisdemeanor.setDisable(true);
            cont.fldFine.setDisable(true);
            cont.fldAdditionalRequirements.setDisable(true);
            cont.cbViolation.setSelected(false);
        }
        if(reportDAO.getDaysClosedForReportID(currentReportID)==0 && reportDAO.getOpeningConditionsForReportID(currentReportID).isBlank()){
            cont.fldDaysClosed.setDisable(true);
            cont.fldConditionClosed.setDisable(true);
            cont.rbConditionClosed.setSelected(false);
            cont.rbDaysClosed.setSelected(false);
            cont.cbWorkBan.setSelected(false);
            cont.rbDaysClosed.setDisable(true);
            cont.rbConditionClosed.setDisable(true);
        }if(reportDAO.getRecordedWorkersForReportID(currentReportID)==0){
            cont.fldReportedWorkers.setDisable(true);
        }
        if(reportDAO.getDaysClosedForReportID(currentReportID)==0){
            cont.fldDaysClosed.setDisable(true);
            cont.rbDaysClosed.setSelected(false);
        }
        if(reportDAO.getOpeningConditionsForReportID(currentReportID).isBlank()){
            cont.fldConditionClosed.setDisable(true);
            cont.rbConditionClosed.setSelected(false);
        }
        if(reportDAO.getDefectForReportID(currentReportID).isBlank()){
            cont.fldDefect.setDisable(true);
            cont.cbDefect.setSelected(false);
        }
        cont.fldUniqueID.setText(reportDAO.getUniqueIDForReportID(currentReportID));
        cont.reportId = currentReportID;
        myStage.setTitle("Modify report");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        refreshReports();
    }

    public void showReportDetailsBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reportDetails.fxml"));
        Parent root = loader.load();

        ReportDetailsController cont = loader.getController();
        int objectId = reportDAO.getObjectIDForReport(currentReportID);
        cont.labObject.setText(objectDAO.getNameForID(objectId));
        cont.labObjectType.setText("Kafana");
        cont.labObjectAddress.setText(objectDAO.getAddressForObjectID(objectId));
        cont.areaInspectorStatement.setText(reportDAO.getDescriptionForReportID(currentReportID));
        int ownerId = objectDAO.getOwnerForID(objectId);
        cont.labOwner.setText(ownerDAO.getNameLastNameForID(ownerId));
        int firstWitnessID = witnessDAO.getFirstWitnessID(currentReportID);
        int secondWitnessID = witnessDAO.getSecondWitnessID(currentReportID);
        cont.labFirstWitnessInfo.setText(witnessDAO.getWitnessName(firstWitnessID) + " " + witnessDAO.getWitnessSurename(firstWitnessID) + ", (" + witnessDAO.getWitnessJMBG(firstWitnessID) + ")");
        cont.areaFirstWitnessStatement.setText(witnessDAO.getWitnessStatementForWitness(firstWitnessID));
        cont.labSecondWitnessInfo.setText(witnessDAO.getWitnessName(secondWitnessID) + " " + witnessDAO.getWitnessSurename(secondWitnessID) + ", (" + witnessDAO.getWitnessJMBG(secondWitnessID) + ")");
        cont.areaSecondWitnessStatement.setText(witnessDAO.getWitnessStatementForWitness(secondWitnessID));
        cont.labInspectionDate.setText(reportDAO.getInspectionDateForReportID(currentReportID));
        boolean misdemeanorCommited = reportDAO.violationBooked(currentReportID);
        if(misdemeanorCommited) {
            cont.labMisdemeanor.setText("Issued");
            cont.labViolation.setText(reportDAO.getViolationForReportID(currentReportID));
            cont.labFine.setText(reportDAO.getFineForReportID(currentReportID) + " $");
            cont.labAdditionalRequirements.setText(reportDAO.getAdditionalRequirementsForReportID(currentReportID));
        } else {
            cont.labMisdemeanor.setText("Not issued");
            cont.labViolation.setText("No misdemeanors commited");
            cont.labFine.setText("No fine has been issued");
            cont.labAdditionalRequirements.setText("No additional requirements");
        }
        int brojEvidentiranihRadnika = reportDAO.getRecordedWorkersForReportID(currentReportID);
        if(brojEvidentiranihRadnika!=0){
            cont.labWorkers.setText("Reported " + brojEvidentiranihRadnika + " illegal worker/s");
        }else{
            cont.labWorkers.setText("No illegal workers reported");
        }
        if(reportDAO.getCriminalOffenseForReportID(currentReportID)==1){
            cont.labCriminalOffense.setText("Criminal offense has been filed");
        }else{
            cont.labCriminalOffense.setText("No criminal offense filed");
        }
        if(reportDAO.getPhytocertificateForReportID(currentReportID)==1) cont.labPhytocertificate.setText("Phytocertificate issued");
        else cont.labPhytocertificate.setText("Phytocertificate not issued");
        if(reportDAO.sampleTakenForID(currentReportID)) cont.labSampleTaken.setText("GP/CI/MC sample taken");
        else cont.labSampleTaken.setText("GP/CI/MC sample not taken");
        cont.labWorkBan.setText(reportDAO.getWorkProhibitionForReportID(currentReportID));
        boolean workplaceReported = reportDAO.isReportedWorksite(currentReportID);
        if(workplaceReported){
            cont.labReportedWorksite.setText("Yes");
            cont.labEmployeeNumber.setText(String.valueOf(reportDAO.getEmployeeNumberForReportID(currentReportID)));
            cont.labWorkPermit.setText(String.valueOf(reportDAO.getOpeningCertificateNumberForReportID(currentReportID)));
        }else{
            cont.labReportedWorksite.setText("No");
            cont.labEmployeeNumber.setText("No workers");
            cont.labWorkPermit.setText("No work permit certificate found");
        }
        if(reportDAO.getDefectForReportID(currentReportID).isBlank()) {
            cont.labDefectIssued.setText("Not issued");
            cont.labDefect.setText("No defects found");
        }
        else {
            cont.labDefectIssued.setText("Issued");
            cont.labDefect.setText(reportDAO.getDefectForReportID(currentReportID));
        }
        cont.labUniqueID.setText(reportDAO.getUniqueIDForReportID(currentReportID));
        myStage.setResizable(false);
        myStage.setTitle("Report details");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void exportReportBtn(ActionEvent actionEvent) {
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
        int idInspektora = taskDAO.getInspectorForID(currentTaskID);
        int idObjekta = taskDAO.getObjectID(currentTaskID);
        cont.labTerminZakazao.setText(inspectorDAO.getNameSurenameForID(idInspektora));
        cont.labNazivObjekta.setText(objectDAO.getNameForID(idObjekta));
        cont.labAdresaObjekta.setText(objectDAO.getAddressForObjectID(idObjekta));
        cont.labDatumVrijemeTermina.setText(taskDAO.getDatetime(currentTaskID));
        cont.areaNapomeneTermina.setText(taskDAO.getNotesForTask(currentTaskID));
        int idZaduzenogInspektora = taskDAO.getAssignedInspectorID(currentTaskID);
        System.out.println(idZaduzenogInspektora);
        if(idZaduzenogInspektora != -1) cont.labZaduzeniInspektor.setText(inspectorDAO.getNameSurenameForID(idZaduzenogInspektora));
        else cont.labZaduzeniInspektor.setText("Nema zaduženog inspektora");
        boolean terminObavljen = taskDAO.isCompleted(currentTaskID);
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
        int idObjekta = taskDAO.getObjectID(currentTaskID);
        cont.fldNazivObjekta.setText(objectDAO.getNameForID(idObjekta));
        cont.fldAdresaObjekta.setText(objectDAO.getAddressForObjectID(idObjekta));
        cont.datePickTermin.getEditor().setText(taskDAO.getDatetime(currentTaskID));
        cont.areaNapomene.setText(taskDAO.getNotesForTask(currentTaskID));
        cont.fldSati.setText("");
        cont.fldMinuta.setText("");
        cont.idTermina = currentTaskID;
        myStage.setTitle("Modifikuj termin");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        refreshTasks();
    }

    public void obrisiTerminBtn(ActionEvent actionEvent) {
        taskDAO.deleteTask(currentTaskID);
        refreshTasks();
    }

    private void refreshTasks() {
        tasksList.setItems(taskDAO.getAllTasksForInspector(userDAO.getLoggedUserID()));
    }

    public void uzmiTerminBtn(ActionEvent actionEvent) {
        if(!taskDAO.isTaken(currentTaskID)) taskDAO.takeTask(currentTaskID, userDAO.getLoggedUserID());
    }
}
