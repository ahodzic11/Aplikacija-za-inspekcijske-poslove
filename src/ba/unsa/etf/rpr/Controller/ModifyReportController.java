package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.DAL.ObjectDAO;
import ba.unsa.etf.rpr.DAL.UserDAO;
import ba.unsa.etf.rpr.DAL.WitnessDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifyReportController {
    public TextField fldObjectName;
    public TextField fldObjectAddress;
    public TextArea areaInspectorsStatement;
    public CheckBox cbViolation;
    public TextField fldMisdemeanor;
    public TextField fldFine;
    public DatePicker inspectionDate;
    public TextField fldAdditionalRequirements;
    public CheckBox cbReportWorkplace;
    public TextField fldOwner;
    public TextField fldEmployeeNumber;
    public TextField fldWorkPermit;
    public CheckBox cbDefect;
    public TextField fldReportedWorkers;
    public CheckBox cbCriminalOffense;
    public CheckBox cbSampleTaken;
    public CheckBox cbPhytocertificate;
    public CheckBox cbWorkBan;
    public RadioButton rbDaysClosed;
    public RadioButton rbConditionClosed;
    public TextField fldDaysClosed;
    public TextField fldConditionClosed;
    public CheckBox cbWorkersReported;
    public TextField fldDefect;
    public TextField fldFirstWitnessName;
    public TextField fldFirstWitnessSurename;
    public TextField fldFirstWitnessJMBG;
    public TextArea areaFirstWitnessStatement;
    public TextField fldSecondWitnessName;
    public TextField fldSecondWitnessSurename;
    public TextField fldSecondWitnessJMBG;
    public TextArea areaSecondWitnessStatement;
    public TextField fldUniqueID;
    private ReportDAO reportDAO;
    private UserDAO userDAO;
    public int reportId;
    private WitnessDAO witnessDAO;
    private ObjectDAO objectDAO;

    @FXML
    public void initialize() throws SQLException {
        reportDAO = ReportDAO.getInstance();
        userDAO = UserDAO.getInstance();
        objectDAO = ObjectDAO.getInstance();
        witnessDAO = WitnessDAO.getInstance();

        fldUniqueID.textProperty().addListener((observableValue, o, n) -> {
            if(fldUniqueID.getText().length()!=6){
                fldUniqueID.getStyleClass().removeAll("poljeIspravno");
                fldUniqueID.getStyleClass().add("poljeNeispravno");
            }else{
                fldUniqueID.getStyleClass().removeAll("poljeNeispravno");
                fldUniqueID.getStyleClass().add("poljeIspravno");
            }
        });

        inspectionDate.getEditor().textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(inspectionDate.getValue() != null)
                if(inspectionDate.getValue().isAfter(LocalDate.now())){
                    inspectionDate.getEditor().getStyleClass().removeAll("poljeIspravno");
                    inspectionDate.getEditor().getStyleClass().add("poljeNeispravno");
                }else{
                    inspectionDate.getEditor().getStyleClass().removeAll("poljeNeispravno");
                    inspectionDate.getEditor().getStyleClass().add("poljeIspravno");
                }
        });

        areaInspectorsStatement.textProperty().addListener((observableValue, o, n) -> {
            if(areaInspectorsStatement.getText().length()<50 || areaInspectorsStatement.getText().isBlank()){
                areaInspectorsStatement.getStyleClass().removeAll("poljeIspravno");
                areaInspectorsStatement.getStyleClass().add("poljeNeispravno");
            }else{
                areaInspectorsStatement.getStyleClass().removeAll("poljeNeispravno");
                areaInspectorsStatement.getStyleClass().add("poljeIspravno");
            }
        });

        fldFirstWitnessName.textProperty().addListener((observableValue, o, n) -> {
            if(fldFirstWitnessName.getText().isBlank() || containsNumber(fldFirstWitnessName.getText())){
                fldFirstWitnessName.getStyleClass().removeAll("poljeIspravno");
                fldFirstWitnessName.getStyleClass().add("poljeNeispravno");
            }else{
                fldFirstWitnessName.getStyleClass().removeAll("poljeNeispravno");
                fldFirstWitnessName.getStyleClass().add("poljeIspravno");
            }
        });

        fldFirstWitnessSurename.textProperty().addListener((observableValue, o, n) -> {
            if(fldFirstWitnessSurename.getText().isBlank() || containsNumber(fldFirstWitnessSurename.getText())){
                fldFirstWitnessSurename.getStyleClass().removeAll("poljeIspravno");
                fldFirstWitnessSurename.getStyleClass().add("poljeNeispravno");
            }else{
                fldFirstWitnessSurename.getStyleClass().removeAll("poljeNeispravno");
                fldFirstWitnessSurename.getStyleClass().add("poljeIspravno");
            }
        });

        fldFirstWitnessJMBG.textProperty().addListener((observableValue, o, n) -> {
            if(fldFirstWitnessJMBG.getText().length()!=13 || containsLetter(fldFirstWitnessJMBG.getText())){
                fldFirstWitnessJMBG.getStyleClass().removeAll("poljeIspravno");
                fldFirstWitnessJMBG.getStyleClass().add("poljeNeispravno");
            }else{
                fldFirstWitnessJMBG.getStyleClass().removeAll("poljeNeispravno");
                fldFirstWitnessJMBG.getStyleClass().add("poljeIspravno");
            }
        });

        areaFirstWitnessStatement.textProperty().addListener((observableValue, o, n) -> {
            if(areaFirstWitnessStatement.getText().length()<20 || areaFirstWitnessStatement.getText().isBlank()){
                areaFirstWitnessStatement.getStyleClass().removeAll("poljeIspravno");
                areaFirstWitnessStatement.getStyleClass().add("poljeNeispravno");
            }else{
                areaFirstWitnessStatement.getStyleClass().removeAll("poljeNeispravno");
                areaFirstWitnessStatement.getStyleClass().add("poljeIspravno");
            }
        });

        fldSecondWitnessName.textProperty().addListener((observableValue, o, n) -> {
            if(fldSecondWitnessName.getText().isBlank() || containsNumber(fldSecondWitnessName.getText())){
                fldSecondWitnessName.getStyleClass().removeAll("poljeIspravno");
                fldSecondWitnessName.getStyleClass().add("poljeNeispravno");
            }else{
                fldSecondWitnessName.getStyleClass().removeAll("poljeNeispravno");
                fldSecondWitnessName.getStyleClass().add("poljeIspravno");
            }
        });

        fldSecondWitnessSurename.textProperty().addListener((observableValue, o, n) -> {
            if(fldSecondWitnessSurename.getText().isBlank() || containsNumber(fldSecondWitnessSurename.getText())){
                fldSecondWitnessSurename.getStyleClass().removeAll("poljeIspravno");
                fldSecondWitnessSurename.getStyleClass().add("poljeNeispravno");
            }else{
                fldSecondWitnessSurename.getStyleClass().removeAll("poljeNeispravno");
                fldSecondWitnessSurename.getStyleClass().add("poljeIspravno");
            }
        });

        fldSecondWitnessJMBG.textProperty().addListener((observableValue, o, n) -> {
            if(fldSecondWitnessJMBG.getText().length()!=13 || containsLetter(fldFirstWitnessJMBG.getText())){
                fldSecondWitnessJMBG.getStyleClass().removeAll("poljeIspravno");
                fldSecondWitnessJMBG.getStyleClass().add("poljeNeispravno");
            }else{
                fldSecondWitnessJMBG.getStyleClass().removeAll("poljeNeispravno");
                fldSecondWitnessJMBG.getStyleClass().add("poljeIspravno");
            }
        });

        areaSecondWitnessStatement.textProperty().addListener((observableValue, o, n) -> {
            if(areaSecondWitnessStatement.getText().length()<20 || areaSecondWitnessStatement.getText().isBlank()){
                areaSecondWitnessStatement.getStyleClass().removeAll("poljeIspravno");
                areaSecondWitnessStatement.getStyleClass().add("poljeNeispravno");
            }else{
                areaSecondWitnessStatement.getStyleClass().removeAll("poljeNeispravno");
                areaSecondWitnessStatement.getStyleClass().add("poljeIspravno");
            }
        });

        cbViolation.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldMisdemeanor.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldMisdemeanor.getText().isBlank() || containsNumber(fldMisdemeanor.getText())) {
                        fldMisdemeanor.getStyleClass().removeAll("poljeIspravno");
                        fldMisdemeanor.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldMisdemeanor.getStyleClass().removeAll("poljeNeispravno");
                        fldMisdemeanor.getStyleClass().add("poljeIspravno");
                    }
                });
                fldFine.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldFine.getText().isBlank() || containsLetter(fldFine.getText())) {
                        fldFine.getStyleClass().removeAll("poljeIspravno");
                        fldFine.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldFine.getStyleClass().removeAll("poljeNeispravno");
                        fldFine.getStyleClass().add("poljeIspravno");
                    }
                });
                fldAdditionalRequirements.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldAdditionalRequirements.getText().isBlank() || containsNumber(fldAdditionalRequirements.getText())) {
                        fldAdditionalRequirements.getStyleClass().removeAll("poljeIspravno");
                        fldAdditionalRequirements.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldAdditionalRequirements.getStyleClass().removeAll("poljeNeispravno");
                        fldAdditionalRequirements.getStyleClass().add("poljeIspravno");
                    }
                });
                fldMisdemeanor.setDisable(false);
                fldFine.setDisable(false);
                fldAdditionalRequirements.setDisable(false);
            }else{
                fldMisdemeanor.setDisable(true); fldFine.setDisable(true); fldAdditionalRequirements.setDisable(true);
                fldMisdemeanor.setText(""); fldFine.setText(""); fldAdditionalRequirements.setText("");
            }
        });

        cbWorkersReported.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldReportedWorkers.setDisable(false);
                fldReportedWorkers.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldReportedWorkers.getText().isBlank() || containsLetter(fldReportedWorkers.getText())) {
                        fldReportedWorkers.getStyleClass().removeAll("poljeIspravno");
                        fldReportedWorkers.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldReportedWorkers.getStyleClass().removeAll("poljeNeispravno");
                        fldReportedWorkers.getStyleClass().add("poljeIspravno");
                    }
                });
            }else{
                fldReportedWorkers.setDisable(true);
            }
        });

        cbWorkBan.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                rbDaysClosed.setDisable(false); rbConditionClosed.setDisable(false);
            }else{
                rbDaysClosed.setDisable(true); rbConditionClosed.setDisable(true); fldDaysClosed.setDisable(true); fldConditionClosed.setDisable(true);
            }
        });

        rbDaysClosed.selectedProperty().addListener((obs, oldItem, newItem) -> {
            if(newItem){
                fldConditionClosed.setDisable(true);
                fldDaysClosed.setDisable(false);
                fldDaysClosed.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldDaysClosed.getText().isBlank() || containsLetter(fldDaysClosed.getText())) {
                        fldDaysClosed.getStyleClass().removeAll("poljeIspravno");
                        fldDaysClosed.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldDaysClosed.getStyleClass().removeAll("poljeNeispravno");
                        fldDaysClosed.getStyleClass().add("poljeIspravno");
                    }
                });
            }else{
                fldConditionClosed.setDisable(false);
                fldDaysClosed.setDisable(true);
                fldConditionClosed.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldConditionClosed.getText().isBlank() || containsNumber(fldConditionClosed.getText())) {
                        fldConditionClosed.getStyleClass().removeAll("poljeIspravno");
                        fldConditionClosed.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldConditionClosed.getStyleClass().removeAll("poljeNeispravno");
                        fldConditionClosed.getStyleClass().add("poljeIspravno");
                    }
                });
            }
        });

        rbConditionClosed.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldConditionClosed.setDisable(false);
                fldDaysClosed.setDisable(true);
                fldConditionClosed.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldConditionClosed.getText().isBlank() || containsNumber(fldConditionClosed.getText())) {
                        fldConditionClosed.getStyleClass().removeAll("poljeIspravno");
                        fldConditionClosed.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldConditionClosed.getStyleClass().removeAll("poljeNeispravno");
                        fldConditionClosed.getStyleClass().add("poljeIspravno");
                    }
                });
            }
        });

        cbReportWorkplace.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldOwner.setDisable(false); fldEmployeeNumber.setDisable(false); fldWorkPermit.setDisable(false);
                fldOwner.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldOwner.getText().isBlank() || containsNumber(fldOwner.getText())) {
                        fldOwner.getStyleClass().removeAll("poljeIspravno");
                        fldOwner.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldOwner.getStyleClass().removeAll("poljeNeispravno");
                        fldOwner.getStyleClass().add("poljeIspravno");
                    }
                });
                fldEmployeeNumber.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldEmployeeNumber.getText().isBlank() || containsLetter(fldEmployeeNumber.getText())) {
                        fldEmployeeNumber.getStyleClass().removeAll("poljeIspravno");
                        fldEmployeeNumber.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldEmployeeNumber.getStyleClass().removeAll("poljeNeispravno");
                        fldEmployeeNumber.getStyleClass().add("poljeIspravno");
                    }
                });
                fldWorkPermit.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldWorkPermit.getText().isBlank() || containsLetter(fldWorkPermit.getText())) {
                        fldWorkPermit.getStyleClass().removeAll("poljeIspravno");
                        fldWorkPermit.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldWorkPermit.getStyleClass().removeAll("poljeNeispravno");
                        fldWorkPermit.getStyleClass().add("poljeIspravno");
                    }
                });

            }else{
                fldOwner.setDisable(true); fldEmployeeNumber.setDisable(true); fldWorkPermit.setDisable(true);
            }
        });

        cbDefect.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldDefect.setDisable(false);
                fldDefect.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldDefect.getText().isBlank() || containsNumber(fldDefect.getText())) {
                        fldDefect.getStyleClass().removeAll("poljeIspravno");
                        fldDefect.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldDefect.getStyleClass().removeAll("poljeNeispravno");
                        fldDefect.getStyleClass().add("poljeIspravno");
                    }
                });
            }else{
                fldDefect.setDisable(true);
            }
        });
    }

    public void modifyBtn(ActionEvent actionEvent) {
        if(!isValid()) return;
        int criminalOffense = 0, workersReported = 0, phytocertificate = 0, sampleTaken = 0, workplaceReported = 0;
        if(cbCriminalOffense.isSelected()) criminalOffense = 1;
        if(cbWorkersReported.isSelected() && !fldReportedWorkers.getText().equals("")) workersReported = Integer.parseInt(fldReportedWorkers.getText());
        if(cbPhytocertificate.isSelected()) phytocertificate = 1;
        if(cbSampleTaken.isSelected()) sampleTaken = 1;
        if(cbReportWorkplace.isSelected()) workplaceReported = 1;
        int firstWitnessID = witnessDAO.getFirstWitnessID(reportId);
        int secondWitnessID = witnessDAO.getSecondWitnessID(reportId);
        String stringDatumInspekcije = inspectionDate.getEditor().getText();
        String openingConditions = "";
        witnessDAO.modifyWitness(firstWitnessID, reportId, fldFirstWitnessName.getText(), fldFirstWitnessSurename.getText(), fldFirstWitnessJMBG.getText(), areaFirstWitnessStatement.getText());
        witnessDAO.modifyWitness(secondWitnessID, reportId, fldSecondWitnessName.getText(), fldSecondWitnessSurename.getText(), fldSecondWitnessJMBG.getText(), areaSecondWitnessStatement.getText());
        int objectID = reportDAO.getObjectIDForReport(reportId);
        int fine = 0, daysClosed = 0, employeeNumber = 0, workPermitNumber = 0;
        if(!containsLetter(fldFine.getText()) && !fldFine.getText().equals("")) fine = Integer.parseInt(fldFine.getText());
        if(rbDaysClosed.isSelected())
            if(!containsLetter(fldDaysClosed.getText()) && !fldDaysClosed.getText().equals("")) daysClosed = Integer.parseInt(fldDaysClosed.getText());
        if(rbConditionClosed.isSelected())
            openingConditions = fldConditionClosed.getText();
        if(!containsLetter(fldEmployeeNumber.getText()) && !fldEmployeeNumber.getText().equals("")) employeeNumber = Integer.parseInt(fldEmployeeNumber.getText());
        if(!containsLetter(fldWorkPermit.getText()) && !fldWorkPermit.getText().equals("")) workPermitNumber = Integer.parseInt(fldWorkPermit.getText());
        if(!cbWorkBan.isSelected()){
            daysClosed = 0;
            openingConditions = "";
        }
        String defect = "";
        if(cbDefect.isSelected()) defect = fldDefect.getText();
        reportDAO.modifyReport(reportId, userDAO.getLoggedUserID(), stringDatumInspekcije,
                areaInspectorsStatement.getText(), fldMisdemeanor.getText(), fine, fldAdditionalRequirements.getText(), workersReported, criminalOffense, phytocertificate, sampleTaken,
                daysClosed, openingConditions, workplaceReported,
                employeeNumber, workPermitNumber, defect, firstWitnessID, secondWitnessID, objectID,
                fldObjectName.getText(), fldObjectAddress.getText(), fldUniqueID.getText());
        objectDAO.modifyObject(objectID, objectDAO.getOwnerForID(objectID), fldObjectName.getText(), fldObjectAddress.getText(), objectDAO.getObjectTypeForID(objectID));
        Stage stage = (Stage) fldObjectAddress.getScene().getWindow();
        stage.close();
    }

    private boolean isValid(){
        if(fldUniqueID.getText().length()!=6) return false;
        if(inspectionDate.getValue() == null || inspectionDate.getValue().isAfter(LocalDate.now())) return false;
        if(areaInspectorsStatement.getText().length()<50 || areaInspectorsStatement.getText().isBlank()) return false;
        if(fldFirstWitnessName.getText().isBlank() || containsNumber(fldFirstWitnessName.getText())) return false;
        if(fldFirstWitnessSurename.getText().isBlank() || containsNumber(fldFirstWitnessSurename.getText())) return false;
        if(fldFirstWitnessJMBG.getText().length()!=13 || containsLetter(fldFirstWitnessJMBG.getText())) return false;
        if(areaFirstWitnessStatement.getText().length()<20 || areaFirstWitnessStatement.getText().isBlank()) return false;
        if(fldSecondWitnessName.getText().isBlank() || containsNumber(fldFirstWitnessName.getText())) return false;
        if(fldSecondWitnessSurename.getText().isBlank() || containsNumber(fldFirstWitnessSurename.getText())) return false;
        if(fldSecondWitnessJMBG.getText().length()!=13 || containsLetter(fldFirstWitnessJMBG.getText())) return false;
        if (fldMisdemeanor.getText().isBlank() || containsNumber(fldMisdemeanor.getText())) return false;
        if (fldFine.getText().isBlank() || containsLetter(fldFine.getText())) return false;
        if (fldAdditionalRequirements.getText().isBlank() || containsNumber(fldAdditionalRequirements.getText())) return false;
        if (fldReportedWorkers.getText().isBlank() || containsLetter(fldReportedWorkers.getText())) return false;
        if (fldDaysClosed.getText().isBlank() || containsLetter(fldDaysClosed.getText())) return false;
        if (fldConditionClosed.getText().isBlank() || containsNumber(fldConditionClosed.getText())) return false;
        if (fldOwner.getText().isBlank() || containsNumber(fldOwner.getText())) return false;
        if (fldEmployeeNumber.getText().isBlank() || containsLetter(fldEmployeeNumber.getText())) return false;
        if (fldWorkPermit.getText().isBlank() || containsLetter(fldWorkPermit.getText())) return false;
        if (fldDefect.getText().isBlank() || containsNumber(fldDefect.getText())) return false;
        return areaSecondWitnessStatement.getText().length() >= 20 && !areaFirstWitnessStatement.getText().isBlank();
    }

    private boolean containsLetter(String text) {
        char[] charArray = text.toCharArray();
        for(char c : charArray)
            if(Character.isLetter(c)) return true;
        return false;
    }

    private boolean containsNumber(String text){
        char[] charArray = text.toCharArray();
        for(char c : charArray)
            if(Character.isDigit(c)) return true;
        return false;
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldObjectAddress.getScene().getWindow();
        stage.close();
    }
}
