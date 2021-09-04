package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ObjectDAO;
import ba.unsa.etf.rpr.Model.Object;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateObjekatController {
    private int ownerId;
    public TextField fldName;
    public TextField fldAddress;
    public ComboBox comboObjectType;
    private ObjectDAO objectDAO;

    @FXML
    public void initialize() throws SQLException {
        objectDAO = ObjectDAO.getInstance();
        comboObjectType.getItems().addAll("Educational institution", "Health institution", "Catering facility");

        fldName.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if (fldName.getText().isBlank() || containsNumber(fldName.getText())) {
                fldName.getStyleClass().removeAll("poljeIspravno");
                fldName.getStyleClass().add("poljeNeispravno");
            } else {
                fldName.getStyleClass().removeAll("poljeNeispravno");
                fldName.getStyleClass().add("poljeIspravno");
            }
        });
        fldAddress.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if (fldAddress.getText().isBlank() || containsNumber(fldName.getText())) {
                fldAddress.getStyleClass().removeAll("poljeIspravno");
                fldAddress.getStyleClass().add("poljeNeispravno");
            } else {
                fldAddress.getStyleClass().removeAll("poljeNeispravno");
                fldAddress.getStyleClass().add("poljeIspravno");
            }
        });
        comboObjectType.getEditor().textProperty().addListener((obs, oldItem, newItem)->{
            if(newItem.isBlank() || newItem.length()<5){
                comboObjectType.getStyleClass().removeAll("poljeIspravno");
                comboObjectType.getStyleClass().add("poljeNeispravno");
            }else{
                comboObjectType.getStyleClass().removeAll("poljeNeispravno");
                comboObjectType.getStyleClass().add("poljeIspravno");
            }
        });
    }


    public void okBtn(ActionEvent actionEvent) throws SQLException {
        if(!isValid()) return;
        objectDAO.addObject(new Object(1, ownerId, fldName.getText(), fldAddress.getText(), (String) comboObjectType.getValue()));
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    private boolean isValid(){
        if(fldName.getText().isBlank() || containsNumber(fldName.getText())) return false;
        if (fldAddress.getText().isBlank() || containsNumber(fldName.getText())) return false;
        if(comboObjectType.getEditor().getText().isBlank() || comboObjectType.getEditor().getText().length()<5) return false;
        return true;
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
}
