package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.OwnerDAO;
import ba.unsa.etf.rpr.Model.Owner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateVlasnikController {
    private OwnerDAO ownerDAO;
    public TextField fldEmail;
    public TextField fldPhoneNumber;
    public TextField fldLastName;
    public TextField fldFirstName;
    public TextField fldJMBG;

    @FXML
    public void initialize() throws SQLException {
        ownerDAO = OwnerDAO.getInstance();

        fldFirstName.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if (fldFirstName.getText().isBlank() || containsNumber(fldFirstName.getText())) {
                fldFirstName.getStyleClass().removeAll("poljeIspravno");
                fldFirstName.getStyleClass().add("poljeNeispravno");
            } else {
                fldFirstName.getStyleClass().removeAll("poljeNeispravno");
                fldFirstName.getStyleClass().add("poljeIspravno");
            }
        });
        fldLastName.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if(fldLastName.getText().isBlank() || containsNumber(fldFirstName.getText())){
                fldLastName.getStyleClass().removeAll("poljeIspravno");
                fldLastName.getStyleClass().add("poljeNeispravno");
            }else{
                fldLastName.getStyleClass().removeAll("poljeNeispravno");
                fldLastName.getStyleClass().add("poljeIspravno");
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
    }

    public void createOwnerBtn(ActionEvent actionEvent) throws SQLException, IOException {
        if(!isValid()) return;
        ownerDAO.addOwner(new Owner(1, fldFirstName.getText(), fldLastName.getText(), fldJMBG.getText(), Integer.parseInt(fldPhoneNumber.getText()), fldEmail.getText()));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/objectPicking.fxml"));
        Parent root = loader.load();
        ObjectPickingController objectPickingController = loader.getController();
        objectPickingController.refreshOwnersList();
        Stage stage = (Stage) fldFirstName.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldFirstName.getScene().getWindow();
        stage.close();
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

    private boolean isValid(){
        if(fldFirstName.getText().isBlank() || containsNumber(fldFirstName.getText())) return false;
        if(fldLastName.getText().isBlank() || containsNumber(fldLastName.getText())) return false;
        if(fldJMBG.getLength()!=13 || containsLetter(fldJMBG.getText())) return false;
        if(fldPhoneNumber.getText().isBlank() || containsLetter(fldPhoneNumber.getText())) return false;
        if(!validEmail(fldEmail.getText())) return false;
        return true;
    }
}
