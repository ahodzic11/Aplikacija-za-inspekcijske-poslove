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
    public int idOtvorenog;
    public RadioButton rbGlavniInspektor;
    public RadioButton rbFederalniInspektor;
    public ComboBox comboOblastInspekcije;
    private InspectorDAO inspektorDao;
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldBrojLicne;
    public TextField fldMjestoPrebivalista;
    public TextField fldKontaktTelefon;
    public TextField fldPersonalniMail;
    public TextField fldPristupniMail;
    public TextField fldPristupnaSifra;
    public CheckBox cbVozacka;
    private Status status;

    @FXML
    public void initialize(){
        inspektorDao = InspectorDAO.getInstance();
        status = Status.getInstance();

        fldIme.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if (fldIme.getText().isBlank() || sadrziBroj(fldIme.getText())) {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNeispravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeNeispravno");
                fldIme.getStyleClass().add("poljeIspravno");
            }
        });
        fldPrezime.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldPrezime.getText().isBlank() || sadrziBroj(fldPrezime.getText())){
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNeispravno");
            }else{
                fldPrezime.getStyleClass().removeAll("poljeNeispravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
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

        fldKontaktTelefon.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldKontaktTelefon.getText().isBlank() || sadrziSlovo(fldKontaktTelefon.getText())){
                fldKontaktTelefon.getStyleClass().removeAll("poljeIspravno");
                fldKontaktTelefon.getStyleClass().add("poljeNeispravno");
            }else{
                fldKontaktTelefon.getStyleClass().removeAll("poljeNeispravno");
                fldKontaktTelefon.getStyleClass().add("poljeIspravno");
            }
        });

        fldPersonalniMail.textProperty().addListener((observableValue, o, n) -> {
            if(!valEmail(fldPersonalniMail.getText())){
                fldPersonalniMail.getStyleClass().removeAll("poljeIspravno");
                fldPersonalniMail.getStyleClass().add("poljeNeispravno");
            }else{
                fldPersonalniMail.getStyleClass().removeAll("poljeNeispravno");
                fldPersonalniMail.getStyleClass().add("poljeIspravno");
            }
        });

        fldPristupniMail.textProperty().addListener((observableValue, o, n) -> {
            if(!valEmail(fldPristupniMail.getText())){
                fldPristupniMail.getStyleClass().removeAll("poljeIspravno");
                fldPristupniMail.getStyleClass().add("poljeNeispravno");
            }else{
                fldPristupniMail.getStyleClass().removeAll("poljeNeispravno");
                fldPristupniMail.getStyleClass().add("poljeIspravno");
            }
        });

        fldPristupnaSifra.textProperty().addListener((observableValue, o, n) -> {
            if(fldPristupnaSifra.getText().length()<6){
                fldPristupnaSifra.getStyleClass().removeAll("poljeIspravno");
                fldPristupnaSifra.getStyleClass().add("poljeNeispravno");
            }else{
                fldPristupnaSifra.getStyleClass().removeAll("poljeNeispravno");
                fldPristupnaSifra.getStyleClass().add("poljeIspravno");
            }
        });

        rbFederalniInspektor.selectedProperty().addListener((obs, oldItem, newItem)->{
            comboOblastInspekcije.getItems().removeAll();
            ObservableList<String> listaInspektora = FXCollections.observableArrayList();
            listaInspektora.addAll("Zdravstveni inspektor", "Farmaceutski inspektor", "Inspektor za hranu", "Urbanistički inspektor",
                    "Građevinski Inspektor", "Inspektor za ceste", "Inspektor zaštite prirode", "Inspektor zaštite okoliša", "Sanitarni inspektor",
                    "Turističko-ugostiteljski inspektor", "Šumarski inspektor", "Vodni inspektor", "Veterinarski inspektor", "Rudarski inspektor",
                    "Geološki inspektor");
            java.util.Collections.sort(listaInspektora);
            comboOblastInspekcije.setItems(listaInspektora);
            comboOblastInspekcije.getItems().addAll();
        });
        rbGlavniInspektor.selectedProperty().addListener((obs, oldItem, newItem)->{
            comboOblastInspekcije.getItems().removeAll();
            ObservableList<String> listaInspektora = FXCollections.observableArrayList();
            listaInspektora.addAll("Tržišni inspektor", "Inspektor za hranu", "Zdravstveni inspektor", "Inspektor rada",
                    "Urbanističko-ekološki inspektor", "Saobraćajni inspektor", "Poljoprivredni inspektor", "Inspektor za šumarstvo",
                    "Vodni inspektor", "Veterinarski inspektor", "Elektro-energetski inspektor");
            java.util.Collections.sort(listaInspektora);
            comboOblastInspekcije.setItems(listaInspektora);
            comboOblastInspekcije.getItems().addAll();
        });
    }


    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }

    public void okBtn(ActionEvent actionEvent) throws SQLException, IOException {
        int vozacka = 0;
        if(cbVozacka.isSelected()) vozacka = 1;
        String tipInspektora = "";
        if(rbGlavniInspektor.isSelected()) tipInspektora = "Major federal inspector";
        else tipInspektora = "Federal inspector";
        inspektorDao.modifyInspector(idOtvorenog, fldIme.getText(), fldPrezime.getText(), fldBrojLicne.getText(), fldMjestoPrebivalista.getText(),
                fldKontaktTelefon.getText(), fldPersonalniMail.getText(), fldPristupniMail.getText(), fldPristupnaSifra.getText(), vozacka, tipInspektora, comboOblastInspekcije.getEditor().getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavniProzorAdmin.fxml"));
        Parent root = loader.load();
        GlavniProzorAdminController glavni = loader.getController();
        glavni.refreshInspectorsList();
        status.setStatus("Inspector profile - " + inspektorDao.getNameSurenameForID(idOtvorenog) + " [" + inspektorDao.getUniqueIDForID(idOtvorenog) + "] modified.");

        Stage stage = (Stage) fldIme.getScene().getWindow();
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
