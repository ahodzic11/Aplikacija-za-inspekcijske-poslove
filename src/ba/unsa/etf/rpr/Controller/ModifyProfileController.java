package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.InspectorDAO;
import ba.unsa.etf.rpr.Model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyProfileController {
    public int inspectorId;
    public RadioButton rbMajorInspector;
    public RadioButton rbFederalInspector;
    public ComboBox comboInspectionArea;
    private InspectorDAO inspectorDAO;
    public TextField fldName;
    public TextField fldSurename;
    public TextField fldIDNumber;
    public TextField fldResidency;
    public TextField fldPhoneNumber;
    public TextField fldEmail;
    public TextField fldLoginEmail;
    public TextField fldPassword;
    public CheckBox cbDriversLicense;
    private Status status;

    @FXML
    public void initialize(){
        inspectorDAO = InspectorDAO.getInstance();
        status = Status.getInstance();

        comboInspectionArea.getEditor().textProperty().addListener((obs, oldItem, newItem)->{
            if(newItem.isBlank() || newItem.length()<14){
                comboInspectionArea.getStyleClass().removeAll("poljeIspravno");
                comboInspectionArea.getStyleClass().add("poljeNeispravno");
            }else{
                comboInspectionArea.getStyleClass().removeAll("poljeNeispravno");
                comboInspectionArea.getStyleClass().add("poljeIspravno");
            }
        });

        fldName.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if (fldName.getText().isBlank() || sadrziBroj(fldName.getText())) {
                fldName.getStyleClass().removeAll("poljeIspravno");
                fldName.getStyleClass().add("poljeNeispravno");
            } else {
                fldName.getStyleClass().removeAll("poljeNeispravno");
                fldName.getStyleClass().add("poljeIspravno");
            }
        });
        fldSurename.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldSurename.getText().isBlank() || sadrziBroj(fldSurename.getText())){
                fldSurename.getStyleClass().removeAll("poljeIspravno");
                fldSurename.getStyleClass().add("poljeNeispravno");
            }else{
                fldSurename.getStyleClass().removeAll("poljeNeispravno");
                fldSurename.getStyleClass().add("poljeIspravno");
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
        fldResidency.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldResidency.getText().isBlank()){
                fldResidency.getStyleClass().removeAll("poljeIspravno");
                fldResidency.getStyleClass().add("poljeNeispravno");
            }else{
                fldResidency.getStyleClass().removeAll("poljeNeispravno");
                fldResidency.getStyleClass().add("poljeIspravno");
            }
        });

        fldPhoneNumber.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldPhoneNumber.getText().isBlank() || sadrziSlovo(fldPhoneNumber.getText())){
                fldPhoneNumber.getStyleClass().removeAll("poljeIspravno");
                fldPhoneNumber.getStyleClass().add("poljeNeispravno");
            }else{
                fldPhoneNumber.getStyleClass().removeAll("poljeNeispravno");
                fldPhoneNumber.getStyleClass().add("poljeIspravno");
            }
        });

        fldEmail.textProperty().addListener((observableValue, o, n) -> {
            if(!valEmail(fldEmail.getText())){
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNeispravno");
            }else{
                fldEmail.getStyleClass().removeAll("poljeNeispravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            }
        });

        fldLoginEmail.textProperty().addListener((observableValue, o, n) -> {
            if(!valEmail(fldLoginEmail.getText())){
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

        rbFederalInspector.selectedProperty().addListener((obs, oldItem, newItem)->{
            comboInspectionArea.getItems().removeAll();
            ObservableList<String> listaInspektora = FXCollections.observableArrayList();
            listaInspektora.addAll("Zdravstveni inspektor", "Farmaceutski inspektor", "Inspektor za hranu", "Urbanistički inspektor",
                    "Građevinski Inspektor", "Inspektor za ceste", "Inspektor zaštite prirode", "Inspektor zaštite okoliša", "Sanitarni inspektor",
                    "Turističko-ugostiteljski inspektor", "Šumarski inspektor", "Vodni inspektor", "Veterinarski inspektor", "Rudarski inspektor",
                    "Geološki inspektor");
            java.util.Collections.sort(listaInspektora);
            comboInspectionArea.setItems(listaInspektora);
            comboInspectionArea.getItems().addAll();
        });
        rbMajorInspector.selectedProperty().addListener((obs, oldItem, newItem)->{
            comboInspectionArea.getItems().removeAll();
            ObservableList<String> listaInspektora = FXCollections.observableArrayList();
            listaInspektora.addAll("Tržišni inspektor", "Inspektor za hranu", "Zdravstveni inspektor", "Inspektor rada",
                    "Urbanističko-ekološki inspektor", "Saobraćajni inspektor", "Poljoprivredni inspektor", "Inspektor za šumarstvo",
                    "Vodni inspektor", "Veterinarski inspektor", "Elektro-energetski inspektor");
            java.util.Collections.sort(listaInspektora);
            comboInspectionArea.setItems(listaInspektora);
            comboInspectionArea.getItems().addAll();
        });
    }


    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }

    public void okBtn(ActionEvent actionEvent) throws SQLException, IOException {
        int vozacka = 0;
        if(cbDriversLicense.isSelected()) vozacka = 1;
        String tipInspektora = "";
        if(rbMajorInspector.isSelected()) tipInspektora = "Major federal inspector";
        else tipInspektora = "Federal inspector";
        inspectorDAO.modifyInspector(inspectorId, fldName.getText(), fldSurename.getText(), fldIDNumber.getText(), fldResidency.getText(),
                fldPhoneNumber.getText(), fldEmail.getText(), fldLoginEmail.getText(), fldPassword.getText(), vozacka, tipInspektora, comboInspectionArea.getEditor().getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavniProzorAdmin.fxml"));
        Parent root = loader.load();
        GlavniProzorAdminController glavni = loader.getController();
        glavni.refreshInspectorsList();
        status.setStatus("Inspector profile - " + inspectorDAO.getNameSurenameForID(inspectorId) + " [" + inspectorDAO.getUniqueIDForID(inspectorId) + "] modified.");

        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }

    private boolean sadrziSlovo(String text) {
        char[] charovi = text.toCharArray();
        for(char c : charovi)
            if(Character.isLetter(c)) return true;
        return false;
    }

    private boolean sadrziBroj(String text){
        char[] charovi = text.toCharArray();
        for(char c : charovi)
            if(Character.isDigit(c)) return true;
        return false;
    }

    private boolean valEmail(String input){
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(input);
        return matcher.find();
    }
}
