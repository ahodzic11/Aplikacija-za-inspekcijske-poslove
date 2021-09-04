package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.Model.Report;
import ba.unsa.etf.rpr.Model.Witness;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateReportController {
    public TextField fldObjectName, fldObjectAddress, fldFirstWitnessName, fldFirstWitnessSurename, fldFirstWitnessJMBG, fldSecondWitnessName, fldSecondWitnessSurename, fldSecondWitnessJMBG, fldViolation, fldFine,
            fldAdditionalRequirements, fldOwner, fldEmployeeNumber, fldOpeningCertificationNumber, fldReportedWorkers, fldClosedDays, fldOpeningCondition, fldDefect;
    public TextArea reportDescriptionArea, firstWitnessStatementArea, secondWitnessStatementArea;
    public CheckBox cbViolation, cbReportWorksite, cbCriminalOffense, cbSampleTaken, cbPhytocertificate, cbWorkban, cbWorkersReported, cbDefectDetected;
    public RadioButton rbDaysBan, rbConditionsBan;
    public DatePicker inspectionDate;
    public TextField fldUniqueID;
    private WitnessDAO witnessDAO;
    private ReportDAO reportDAO;
    private UserDAO userDAO;
    public int objectId;

    @FXML
    public void initialize() throws SQLException, IOException {
        reportDAO = ReportDAO.getInstance();
        witnessDAO = WitnessDAO.getInstance();
        userDAO = UserDAO.getInstance();

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

        reportDescriptionArea.textProperty().addListener((observableValue, o, n) -> {
            if(reportDescriptionArea.getText().length()<50 || reportDescriptionArea.getText().isBlank()){
                reportDescriptionArea.getStyleClass().removeAll("poljeIspravno");
                reportDescriptionArea.getStyleClass().add("poljeNeispravno");
            }else{
                reportDescriptionArea.getStyleClass().removeAll("poljeNeispravno");
                reportDescriptionArea.getStyleClass().add("poljeIspravno");
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

        firstWitnessStatementArea.textProperty().addListener((observableValue, o, n) -> {
            if(firstWitnessStatementArea.getText().length()<20 || firstWitnessStatementArea.getText().isBlank()){
                firstWitnessStatementArea.getStyleClass().removeAll("poljeIspravno");
                firstWitnessStatementArea.getStyleClass().add("poljeNeispravno");
            }else{
                firstWitnessStatementArea.getStyleClass().removeAll("poljeNeispravno");
                firstWitnessStatementArea.getStyleClass().add("poljeIspravno");
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

        secondWitnessStatementArea.textProperty().addListener((observableValue, o, n) -> {
            if(secondWitnessStatementArea.getText().length()<20 || secondWitnessStatementArea.getText().isBlank()){
                secondWitnessStatementArea.getStyleClass().removeAll("poljeIspravno");
                secondWitnessStatementArea.getStyleClass().add("poljeNeispravno");
            }else{
                secondWitnessStatementArea.getStyleClass().removeAll("poljeNeispravno");
                secondWitnessStatementArea.getStyleClass().add("poljeIspravno");
            }
        });

        fldViolation.setDisable(true); fldFine.setDisable(true); fldAdditionalRequirements.setDisable(true);
        cbViolation.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldViolation.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldViolation.getText().isBlank() || containsNumber(fldViolation.getText())) {
                        fldViolation.getStyleClass().removeAll("poljeIspravno");
                        fldViolation.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldViolation.getStyleClass().removeAll("poljeNeispravno");
                        fldViolation.getStyleClass().add("poljeIspravno");
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
                fldViolation.setDisable(false);
                fldFine.setDisable(false);
                fldAdditionalRequirements.setDisable(false);
            }else{
                fldViolation.setDisable(true); fldFine.setDisable(true); fldAdditionalRequirements.setDisable(true);
                fldViolation.setText(""); fldFine.setText(""); fldAdditionalRequirements.setText("");
            }
        });
        fldReportedWorkers.setDisable(true);
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

        rbDaysBan.setDisable(true); rbConditionsBan.setDisable(true); fldClosedDays.setDisable(true); fldOpeningCondition.setDisable(true);
        cbWorkban.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                rbDaysBan.setDisable(false); rbConditionsBan.setDisable(false);
            }else{
                rbDaysBan.setDisable(true); rbConditionsBan.setDisable(true); fldClosedDays.setDisable(true); fldOpeningCondition.setDisable(true);
            }
        });
        fldOwner.setDisable(true); fldEmployeeNumber.setDisable(true); fldOpeningCertificationNumber.setDisable(true);
        rbDaysBan.selectedProperty().addListener((obs, oldItem, newItem) -> {
            if(newItem){
                fldOpeningCondition.setDisable(true);
                fldClosedDays.setDisable(false);
                fldClosedDays.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldClosedDays.getText().isBlank() || containsLetter(fldClosedDays.getText())) {
                        fldClosedDays.getStyleClass().removeAll("poljeIspravno");
                        fldClosedDays.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldClosedDays.getStyleClass().removeAll("poljeNeispravno");
                        fldClosedDays.getStyleClass().add("poljeIspravno");
                    }
                });
            }else{
                /*fldOpeningCondition.setDisable(false);
                fldClosedDays.setDisable(true);
                fldOpeningCondition.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldOpeningCondition.getText().isBlank() || containsNumber(fldOpeningCondition.getText())) {
                        fldOpeningCondition.getStyleClass().removeAll("poljeIspravno");
                        fldOpeningCondition.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldOpeningCondition.getStyleClass().removeAll("poljeNeispravno");
                        fldOpeningCondition.getStyleClass().add("poljeIspravno");
                    }
                });*/
            }
        });
        rbConditionsBan.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldOpeningCondition.setDisable(false);
                fldClosedDays.setDisable(true);
                fldOpeningCondition.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldOpeningCondition.getText().isBlank() || containsNumber(fldOpeningCondition.getText())) {
                        fldOpeningCondition.getStyleClass().removeAll("poljeIspravno");
                        fldOpeningCondition.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldOpeningCondition.getStyleClass().removeAll("poljeNeispravno");
                        fldOpeningCondition.getStyleClass().add("poljeIspravno");
                    }
                });
            }
        });
        fldDefect.setDisable(true);
        cbReportWorksite.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldOwner.setDisable(false); fldEmployeeNumber.setDisable(false); fldOpeningCertificationNumber.setDisable(false);
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
                fldOpeningCertificationNumber.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
                    if (fldOpeningCertificationNumber.getText().isBlank() || containsLetter(fldOpeningCertificationNumber.getText())) {
                        fldOpeningCertificationNumber.getStyleClass().removeAll("poljeIspravno");
                        fldOpeningCertificationNumber.getStyleClass().add("poljeNeispravno");
                    } else {
                        fldOpeningCertificationNumber.getStyleClass().removeAll("poljeNeispravno");
                        fldOpeningCertificationNumber.getStyleClass().add("poljeIspravno");
                    }
                });

            }else{
                fldOwner.setDisable(true); fldEmployeeNumber.setDisable(true); fldOpeningCertificationNumber.setDisable(true);
            }
        });

        cbDefectDetected.selectedProperty().addListener((obs, oldItem, newItem)->{
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

    public void okBtn(ActionEvent actionEvent) throws SQLException {
        if(!isValid()) return;
        int criminalOffense = 0, reportedWorkers = 0, phytocertificate = 0, sampleTaken = 0, reportedWorksite = 0, fine = 0,
                numberOfDaysBan = 0, openingCertificationNumber = 0, numberOfEmployees = 0;
        if(cbCriminalOffense.isSelected()) criminalOffense = 1;
        if(cbWorkersReported.isSelected() && !fldReportedWorkers.getText().equals("")) reportedWorkers = Integer.parseInt(fldReportedWorkers.getText());
        if(cbPhytocertificate.isSelected()) phytocertificate = 1;
        if(cbSampleTaken.isSelected()) sampleTaken = 1;
        if(cbReportWorksite.isSelected()) reportedWorksite = 1;
        int firstWitnessID = witnessDAO.getNewWitnessID();
        int newReportID = reportDAO.getNewReportID();
        witnessDAO.addWitness(new Witness(firstWitnessID, newReportID, fldFirstWitnessName.getText(), fldFirstWitnessSurename.getText(), fldFirstWitnessJMBG.getText(), firstWitnessStatementArea.getText()));
        int secondWitnessID = witnessDAO.getNewWitnessID();
        witnessDAO.addWitness(new Witness(secondWitnessID, newReportID, fldSecondWitnessName.getText(), fldSecondWitnessSurename.getText(), fldSecondWitnessJMBG.getText(), secondWitnessStatementArea.getText()));
        if(!fldFine.getText().equals("")) fine = Integer.parseInt(fldFine.getText());
        if(!fldClosedDays.getText().equals("")) numberOfDaysBan = Integer.parseInt(fldClosedDays.getText());
        if(!fldEmployeeNumber.getText().equals("")) numberOfEmployees = Integer.parseInt(fldEmployeeNumber.getText());
        if(!fldOpeningCertificationNumber.getText().equals("")) openingCertificationNumber = Integer.parseInt(fldOpeningCertificationNumber.getText());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Report newReport = new Report(1, userDAO.getLoggedUserID(), inspectionDate.getValue().format(formatter),
                reportDescriptionArea.getText(), fldViolation.getText(), fine, fldAdditionalRequirements.getText(), reportedWorkers, criminalOffense, phytocertificate, sampleTaken,
                numberOfDaysBan, fldOpeningCondition.getText(), reportedWorksite,
                numberOfEmployees, openingCertificationNumber, fldDefect.getText(), firstWitnessID, secondWitnessID, objectId,
                fldObjectName.getText(), fldObjectAddress.getText(), fldUniqueID.getText());

        reportDAO.addReport(newReport);
        Stage stage = (Stage) fldObjectAddress.getScene().getWindow();
        stage.close();
    }

    private boolean isValid(){
        if(fldUniqueID.getText().length()!=6) return false;
        if(inspectionDate.getValue() == null || inspectionDate.getValue().isAfter(LocalDate.now())) return false;
        if(reportDescriptionArea.getText().length()<50 || reportDescriptionArea.getText().isBlank()) return false;
        if(fldFirstWitnessName.getText().isBlank() || containsNumber(fldFirstWitnessName.getText())) return false;
        if(fldFirstWitnessSurename.getText().isBlank() || containsNumber(fldFirstWitnessSurename.getText())) return false;
        if(fldFirstWitnessJMBG.getText().length()!=13 || containsLetter(fldFirstWitnessJMBG.getText())) return false;
        if(firstWitnessStatementArea.getText().length()<20 || firstWitnessStatementArea.getText().isBlank()) return false;
        if(fldSecondWitnessName.getText().isBlank() || containsNumber(fldFirstWitnessName.getText())) return false;
        if(fldSecondWitnessSurename.getText().isBlank() || containsNumber(fldFirstWitnessSurename.getText())) return false;
        if(fldSecondWitnessJMBG.getText().length()!=13 || containsLetter(fldFirstWitnessJMBG.getText())) return false;
        if (fldViolation.getText().isBlank() || containsNumber(fldViolation.getText())) return false;
        if (fldFine.getText().isBlank() || containsLetter(fldFine.getText())) return false;
        if (fldAdditionalRequirements.getText().isBlank() || containsNumber(fldAdditionalRequirements.getText())) return false;
        if (fldReportedWorkers.getText().isBlank() || containsLetter(fldReportedWorkers.getText())) return false;
        if (fldOpeningCondition.getText().isBlank() || containsNumber(fldOpeningCondition.getText())) return false;
        if (fldOwner.getText().isBlank() || containsNumber(fldOwner.getText())) return false;
        if (fldEmployeeNumber.getText().isBlank() || containsLetter(fldEmployeeNumber.getText())) return false;
        if(rbDaysBan.isSelected())
            if (fldClosedDays.getText().isBlank() || containsLetter(fldClosedDays.getText())) return false;
        if(rbConditionsBan.isSelected())
            if (fldOpeningCondition.getText().isBlank() || containsNumber(fldOpeningCondition.getText())) return false;
        if (fldOpeningCertificationNumber.getText().isBlank() || containsLetter(fldOpeningCertificationNumber.getText())) return false;
        if (fldDefect.getText().isBlank() || containsNumber(fldDefect.getText())) return false;
        return secondWitnessStatementArea.getText().length() >= 20 && !firstWitnessStatementArea.getText().isBlank();
    }
}
