package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.UserDAO;
import ba.unsa.etf.rpr.Model.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyAdministratorController {
    private UserDAO userDAO;
    private AdministratorDAO administratorDAO;
    public TextField fldEmail;
    public TextField fldPassword;
    public TextField fldUniqueID;
    private Status status;

    @FXML
    public void initialize() throws SQLException {
        userDAO = UserDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
        status = Status.getInstance();

        fldEmail.textProperty().addListener((observableValue, o, n) -> {
            if(!validEmail(fldEmail.getText())){
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNeispravno");
            }else{
                fldEmail.getStyleClass().removeAll("poljeNeispravno");
                fldEmail.getStyleClass().add("poljeIspravno");
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

    public void okBtn(ActionEvent actionEvent) {
        if(!isValid()) return;
        String userUniqueID = userDAO.getLoggedUserUniqueID();
        int administratorID = administratorDAO.getIdForUniqueID(userUniqueID);
        administratorDAO.modifyAdministrator(administratorID, fldEmail.getText(), fldPassword.getText(), fldUniqueID.getText());
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();

        status.setStatus("Administrator profile [" + userUniqueID + "] login data changed.");
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
    }

    private boolean validEmail(String input){
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(input);
        return matcher.find();
    }

    private boolean isValid(){
        if(!validEmail(fldEmail.getText())) return false;
        if(fldPassword.getText().length()<6 || fldUniqueID.getText().length()!=6) return false;
        return true;
    }
}
