package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.InspektorDAO;
import ba.unsa.etf.rpr.Model.Inspektor;
import ba.unsa.etf.rpr.Model.Status;
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
    public ComboBox comboVrstaInspektora;
    public RadioButton rbGlavniInspektor;
    public ToggleGroup vrstaInspektoraGroup;
    public RadioButton rbFederalniInspektor;
    private InspektorDAO inspektorDAO;
    public TextField fldBrojTelefona;
    public TextField fldPersonalniMail;
    public TextField fldKorisnickiMail;
    public TextField fldSifra;
    public TextField fldBrojLicne;
    public CheckBox vozackaCB;
    public TextField fldMjestoPrebivalista;
    public TextField fldIme;
    public TextField fldPrezime;
    public DatePicker birthdate;
    public TextField fldJMBG;
    public RadioButton maleRB;
    public RadioButton femaleRB;
    private Status status;

    @FXML
    public void initialize() throws SQLException {
        inspektorDAO = InspektorDAO.getInstance();
        rbFederalniInspektor.setSelected(true);
        status = Status.getInstance();
        federalInspectorComboSetup();
        // validation
        fldIme.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if (fldIme.getText().isBlank() || containsNumber(fldIme.getText())) {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNeispravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeNeispravno");
                fldIme.getStyleClass().add("poljeIspravno");
            }
        });
        fldPrezime.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldPrezime.getText().isBlank() || containsNumber(fldPrezime.getText())){
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNeispravno");
            }else{
                fldPrezime.getStyleClass().removeAll("poljeNeispravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
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
        fldBrojLicne.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldBrojLicne.getText().isBlank()){
                fldBrojLicne.getStyleClass().removeAll("poljeIspravno");
                fldBrojLicne.getStyleClass().add("poljeNeispravno");
            }else{
                fldBrojLicne.getStyleClass().removeAll("poljeNeispravno");
                fldBrojLicne.getStyleClass().add("poljeIspravno");
            }
        });
        fldMjestoPrebivalista.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldMjestoPrebivalista.getText().isBlank()){
                fldMjestoPrebivalista.getStyleClass().removeAll("poljeIspravno");
                fldMjestoPrebivalista.getStyleClass().add("poljeNeispravno");
            }else{
                fldMjestoPrebivalista.getStyleClass().removeAll("poljeNeispravno");
                fldMjestoPrebivalista.getStyleClass().add("poljeIspravno");
            }
        });
        fldBrojTelefona.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldBrojTelefona.getText().isBlank() || containsLetter(fldBrojTelefona.getText())){
                fldBrojTelefona.getStyleClass().removeAll("poljeIspravno");
                fldBrojTelefona.getStyleClass().add("poljeNeispravno");
            }else{
                fldBrojTelefona.getStyleClass().removeAll("poljeNeispravno");
                fldBrojTelefona.getStyleClass().add("poljeIspravno");
            }
        });
        fldPersonalniMail.textProperty().addListener((observableValue, o, n) -> {
            if(!validEmail(fldPersonalniMail.getText())){
                fldPersonalniMail.getStyleClass().removeAll("poljeIspravno");
                fldPersonalniMail.getStyleClass().add("poljeNeispravno");
            }else{
                fldPersonalniMail.getStyleClass().removeAll("poljeNeispravno");
                fldPersonalniMail.getStyleClass().add("poljeIspravno");
            }
        });
        fldKorisnickiMail.textProperty().addListener((observableValue, o, n) -> {
            if(!validEmail(fldKorisnickiMail.getText())){
                fldKorisnickiMail.getStyleClass().removeAll("poljeIspravno");
                fldKorisnickiMail.getStyleClass().add("poljeNeispravno");
            }else{
                fldKorisnickiMail.getStyleClass().removeAll("poljeNeispravno");
                fldKorisnickiMail.getStyleClass().add("poljeIspravno");
            }
        });
        fldSifra.textProperty().addListener((observableValue, o, n) -> {
            if(fldSifra.getText().length()<6){
                fldSifra.getStyleClass().removeAll("poljeIspravno");
                fldSifra.getStyleClass().add("poljeNeispravno");
            }else{
                fldSifra.getStyleClass().removeAll("poljeNeispravno");
                fldSifra.getStyleClass().add("poljeIspravno");
            }
        });
        comboVrstaInspektora.getEditor().textProperty().addListener((obs, oldItem, newItem)->{
            if(newItem.isBlank() || newItem.length()<14){
                comboVrstaInspektora.getStyleClass().removeAll("poljeIspravno");
                comboVrstaInspektora.getStyleClass().add("poljeNeispravno");
            }else{
                comboVrstaInspektora.getStyleClass().removeAll("poljeNeispravno");
                comboVrstaInspektora.getStyleClass().add("poljeIspravno");
            }
        });
        rbFederalniInspektor.selectedProperty().addListener((obs, oldItem, newItem)->{
            federalInspectorComboSetup();
        });
        rbGlavniInspektor.selectedProperty().addListener((obs, oldItem, newItem)->{
            comboVrstaInspektora.getItems().removeAll();
            ObservableList<String> listaInspektora = FXCollections.observableArrayList();
            listaInspektora.addAll("Tržišni inspektor", "Inspektor za hranu", "Zdravstveni inspektor", "Inspektor rada",
                    "Urbanističko-ekološki inspektor", "Saobraćajni inspektor", "Poljoprivredni inspektor", "Inspektor za šumarstvo",
                    "Vodni inspektor", "Veterinarski inspektor", "Elektro-energetski inspektor");
            java.util.Collections.sort(listaInspektora);
            comboVrstaInspektora.setItems(listaInspektora);
            comboVrstaInspektora.getItems().addAll();
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
        char[] charovi = text.toCharArray();
        for(char c : charovi)
            if(Character.isLetter(c)) return true;
        return false;
    }

    private boolean containsNumber(String text){
        char[] charovi = text.toCharArray();
        for(char c : charovi)
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
        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }

    public void createInspectorBtn(ActionEvent actionEvent) throws SQLException {
        //if(!isValid()) return;
        int spol=0, vozacka=0;
        if(vozackaCB.isSelected()) vozacka=1;
        if(maleRB.isSelected()) spol=1;
        else if(femaleRB.isSelected()) spol = 2; // spol=1 - musko, spol=2 - zensko
        String tipInspektora = "";
        if(rbGlavniInspektor.isSelected()) tipInspektora = "Major federal inspector";
        else tipInspektora = "Federal inspector";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        inspektorDAO.dodaj(new Inspektor(0, fldIme.getText(), fldPrezime.getText(), birthdate.getValue().format(formatter), fldJMBG.getText(),
                spol, fldBrojLicne.getText(), fldMjestoPrebivalista.getText(), fldBrojTelefona.getText(), fldPersonalniMail.getText(),
                fldKorisnickiMail.getText(), fldSifra.getText(), vozacka, fldUniqueID.getText(), tipInspektora, comboVrstaInspektora.getEditor().getText()));
        status.setStatus("New inspector profile added - " + fldIme.getText() + " " + fldPrezime.getText() + " [" + fldJMBG.getText() + "].");
        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }

    private void federalInspectorComboSetup(){
        comboVrstaInspektora.getItems().removeAll();
        ObservableList<String> listaInspektora = FXCollections.observableArrayList();
        listaInspektora.addAll("Zdravstveni inspektor", "Farmaceutski inspektor", "Inspektor za hranu", "Urbanistički inspektor",
                "Građevinski Inspektor", "Inspektor za ceste", "Inspektor zaštite prirode", "Inspektor zaštite okoliša", "Sanitarni inspektor",
                "Turističko-ugostiteljski inspektor", "Šumarski inspektor", "Vodni inspektor", "Veterinarski inspektor", "Rudarski inspektor",
                "Geološki inspektor");
        java.util.Collections.sort(listaInspektora);
        comboVrstaInspektora.setItems(listaInspektora);
        comboVrstaInspektora.getItems().addAll();
    }

    private boolean isValid(){
        if(fldIme.getText().isBlank() || containsNumber(fldIme.getText())) return false;
        if(fldPrezime.getText().isBlank() || containsNumber(fldPrezime.getText())) return false;
        if(birthdate.getValue().isAfter(LocalDate.now())) return false;
        if(fldJMBG.getLength()!=13 || containsLetter(fldJMBG.getText())) return false;
        if(fldBrojLicne.getText().isBlank() || fldMjestoPrebivalista.getText().isBlank()) return false;
        if(fldBrojTelefona.getText().isBlank() || containsLetter(fldBrojTelefona.getText())) return false;
        if(!validEmail(fldPersonalniMail.getText()) || !validEmail(fldKorisnickiMail.getText())) return false;
        if(fldSifra.getText().length()<6 || fldUniqueID.getText().length()!=6) return false;
        return true;
    }
}
