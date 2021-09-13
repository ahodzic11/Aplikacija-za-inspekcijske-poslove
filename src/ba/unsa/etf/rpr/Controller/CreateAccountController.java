package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.InspectorDAO;
import ba.unsa.etf.rpr.Model.Inspector;
import ba.unsa.etf.rpr.Utility.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountController {
    public TextField fldUniqueID;
    public ComboBox comboInspectorType;
    public RadioButton rbMajorInspector;
    public RadioButton rbFederalInspector;
    private InspectorDAO inspectorDAO;
    public TextField fldPhoneNumber;
    public TextField fldEmail;
    public TextField fldLoginEmail;
    public TextField fldPassword;
    public TextField fldIDNumber;
    public CheckBox cbDriversLicense;
    public TextField fldResidence;
    public TextField fldName;
    public TextField fldSurename;
    public DatePicker birthdate;
    public TextField fldJMBG;
    public RadioButton maleRB;
    public RadioButton femaleRB;
    private Status status;

    @FXML
    public void initialize() throws SQLException {
        inspectorDAO = InspectorDAO.getInstance();
        rbFederalInspector.setSelected(true);
        status = Status.getInstance();
        federalInspectorComboSetup();
        // validation
        fldName.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if (fldName.getText().isBlank() || containsNumber(fldName.getText())) {
                fldName.getStyleClass().removeAll("poljeIspravno");
                fldName.getStyleClass().add("poljeNeispravno");
            } else {
                fldName.getStyleClass().removeAll("poljeNeispravno");
                fldName.getStyleClass().add("poljeIspravno");
            }
        });
        fldSurename.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldSurename.getText().isBlank() || containsNumber(fldSurename.getText())){
                fldSurename.getStyleClass().removeAll("poljeIspravno");
                fldSurename.getStyleClass().add("poljeNeispravno");
            }else{
                fldSurename.getStyleClass().removeAll("poljeNeispravno");
                fldSurename.getStyleClass().add("poljeIspravno");
            }
        });
        birthdate.getEditor().textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(birthdate.getValue() != null)
            if(birthdate.getValue().isAfter(LocalDate.now())){
                birthdate.getEditor().getStyleClass().removeAll("poljeIspravno");
                birthdate.getEditor().getStyleClass().add("poljeNeispravno");
            }else{
                birthdate.getEditor().getStyleClass().removeAll("poljeNeispravno");
                birthdate.getEditor().getStyleClass().add("poljeIspravno");
            }
        });
        fldJMBG.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldJMBG.getLength()!=13 || containsLetter(fldJMBG.getText())){
                fldJMBG.getStyleClass().removeAll("poljeIspravno");
                fldJMBG.getStyleClass().add("poljeNeispravno");
            }else{
                fldJMBG.getStyleClass().removeAll("poljeNeispravno");
                fldJMBG.getStyleClass().add("poljeIspravno");
            }
        });
        fldIDNumber.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldIDNumber.getText().isBlank()){
                fldIDNumber.getStyleClass().removeAll("poljeIspravno");
                fldIDNumber.getStyleClass().add("poljeNeispravno");
            }else{
                fldIDNumber.getStyleClass().removeAll("poljeNeispravno");
                fldIDNumber.getStyleClass().add("poljeIspravno");
            }
        });
        fldResidence.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldResidence.getText().isBlank()){
                fldResidence.getStyleClass().removeAll("poljeIspravno");
                fldResidence.getStyleClass().add("poljeNeispravno");
            }else{
                fldResidence.getStyleClass().removeAll("poljeNeispravno");
                fldResidence.getStyleClass().add("poljeIspravno");
            }
        });
        fldPhoneNumber.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldPhoneNumber.getText().isBlank() || containsLetter(fldPhoneNumber.getText())){
                fldPhoneNumber.getStyleClass().removeAll("poljeIspravno");
                fldPhoneNumber.getStyleClass().add("poljeNeispravno");
            }else{
                fldPhoneNumber.getStyleClass().removeAll("poljeNeispravno");
                fldPhoneNumber.getStyleClass().add("poljeIspravno");
            }
        });
        fldEmail.textProperty().addListener((observableValue, o, n) -> {
            if(!validEmail(fldEmail.getText())){
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNeispravno");
            }else{
                fldEmail.getStyleClass().removeAll("poljeNeispravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            }
        });
        fldLoginEmail.textProperty().addListener((observableValue, o, n) -> {
            if(!validEmail(fldLoginEmail.getText())){
                fldLoginEmail.getStyleClass().removeAll("poljeIspravno");
                fldLoginEmail.getStyleClass().add("poljeNeispravno");
            }else{
                fldLoginEmail.getStyleClass().removeAll("poljeNeispravno");
                fldLoginEmail.getStyleClass().add("poljeIspravno");
            }
        });
        fldPassword.textProperty().addListener((observableValue, o, n) -> {
            if(fldPassword.getText().length()<6){
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNeispravno");
            }else{
                fldPassword.getStyleClass().removeAll("poljeNeispravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            }
        });
        comboInspectorType.getEditor().textProperty().addListener((obs, oldItem, newItem)->{
            if(newItem.isBlank() || newItem.length()<14){
                comboInspectorType.getStyleClass().removeAll("poljeIspravno");
                comboInspectorType.getStyleClass().add("poljeNeispravno");
            }else{
                comboInspectorType.getStyleClass().removeAll("poljeNeispravno");
                comboInspectorType.getStyleClass().add("poljeIspravno");
            }
        });
        rbFederalInspector.selectedProperty().addListener((obs, oldItem, newItem)->{
            federalInspectorComboSetup();
        });
        rbMajorInspector.selectedProperty().addListener((obs, oldItem, newItem)->{
            comboInspectorType.getItems().removeAll();
            ObservableList<String> listaInspektora = FXCollections.observableArrayList();
            listaInspektora.addAll("Tržišni inspektor", "Inspektor za hranu", "Zdravstveni inspektor", "Inspektor rada",
                    "Urbanističko-ekološki inspektor", "Saobraćajni inspektor", "Poljoprivredni inspektor", "Inspektor za šumarstvo",
                    "Vodni inspektor", "Veterinarski inspektor", "Elektro-energetski inspektor");
            java.util.Collections.sort(listaInspektora);
            comboInspectorType.setItems(listaInspektora);
            comboInspectorType.getItems().addAll();
        });
        fldUniqueID.textProperty().addListener((observableValue, o, n) -> {
            if(fldUniqueID.getText().length()!=6){
                fldUniqueID.getStyleClass().removeAll("poljeIspravno");
                fldUniqueID.getStyleClass().add("poljeNeispravno");
            }else{
                fldUniqueID.getStyleClass().removeAll("poljeNeispravno");
                fldUniqueID.getStyleClass().add("poljeIspravno");
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

    private boolean validEmail(String input){
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(input);
        return matcher.find();
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }

    public void createInspectorBtn(ActionEvent actionEvent) throws SQLException {
        //if(!isValid()) return;
        int gender=0, driversLicense=0;
        if(cbDriversLicense.isSelected()) driversLicense=1;
        if(maleRB.isSelected()) gender=1;
        else if(femaleRB.isSelected()) gender = 2; // gender = 1 - male / 2 - female
        String tipInspektora = "";
        if(rbMajorInspector.isSelected()) tipInspektora = "Major federal inspector";
        else tipInspektora = "Federal inspector";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        inspectorDAO.addInspector(new Inspector(0, fldName.getText(), fldSurename.getText(), birthdate.getValue().format(formatter), fldJMBG.getText(),
                gender, fldIDNumber.getText(), fldResidence.getText(), fldPhoneNumber.getText(), fldEmail.getText(),
                fldLoginEmail.getText(), fldPassword.getText(), driversLicense, fldUniqueID.getText(), tipInspektora, comboInspectorType.getEditor().getText()));
        status.setStatus("New inspector profile added - " + fldName.getText() + " " + fldSurename.getText() + " [" + fldUniqueID.getText() + "].");
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }

    private void federalInspectorComboSetup(){
        comboInspectorType.getItems().removeAll();
        ObservableList<String> listaInspektora = FXCollections.observableArrayList();
        listaInspektora.addAll("Zdravstveni inspektor", "Farmaceutski inspektor", "Inspektor za hranu", "Urbanistički inspektor",
                "Građevinski Inspektor", "Inspektor za ceste", "Inspektor zaštite prirode", "Inspektor zaštite okoliša", "Sanitarni inspektor",
                "Turističko-ugostiteljski inspektor", "Šumarski inspektor", "Vodni inspektor", "Veterinarski inspektor", "Rudarski inspektor",
                "Geološki inspektor");
        java.util.Collections.sort(listaInspektora);
        comboInspectorType.setItems(listaInspektora);
        comboInspectorType.getItems().addAll();
    }

    private boolean isValid(){
        if(fldName.getText().isBlank() || containsNumber(fldName.getText())) return false;
        if(fldSurename.getText().isBlank() || containsNumber(fldSurename.getText())) return false;
        if(birthdate.getValue().isAfter(LocalDate.now())) return false;
        if(fldJMBG.getLength()!=13 || containsLetter(fldJMBG.getText())) return false;
        if(fldIDNumber.getText().isBlank() || fldResidence.getText().isBlank()) return false;
        if(fldPhoneNumber.getText().isBlank() || containsLetter(fldPhoneNumber.getText())) return false;
        if(!validEmail(fldEmail.getText()) || !validEmail(fldLoginEmail.getText())) return false;
        return fldPassword.getText().length() >= 6 && fldUniqueID.getText().length() == 6;
    }
}
