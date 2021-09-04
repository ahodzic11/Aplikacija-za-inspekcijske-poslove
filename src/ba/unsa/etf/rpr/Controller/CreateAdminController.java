package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ActionLogDAO;
import ba.unsa.etf.rpr.DAL.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.UserDAO;
import ba.unsa.etf.rpr.Model.ActionLog;
import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAdminController {
    private AdministratorDAO administratorDAO;
    private ActionLogDAO actionLogDAO;
    private UserDAO userDAO;
    public TextField fldEmail;
    public TextField fldPassword;
    public TextField fldUniqueID;
    private Status status;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @FXML
    public void initialize() throws SQLException {
        administratorDAO = AdministratorDAO.getInstance();
        status = Status.getInstance();
        actionLogDAO = ActionLogDAO.getInstance();
        userDAO = UserDAO.getInstance();

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

    private boolean isValid(){
        if(!validEmail(fldEmail.getText())) return false;
        if(fldPassword.getText().length()<6 || fldUniqueID.getText().length()!=6) return false;
        return true;
    }

    public void createAdminBtn(ActionEvent actionEvent) throws SQLException {
        if(!isValid()) return;
        administratorDAO.addAdministrator(new Administrator(1, fldEmail.getText(), fldPassword.getText(), fldUniqueID.getText()));
        status.setStatus("New administrator account made - [" + fldUniqueID.getText() + "].");
        actionLogDAO.addLog(new ActionLog(1, LocalDateTime.now().format(formatter), "Administrator [" + userDAO.getLoggedUserUniqueID()+ "] made a new administrator account - " + fldEmail.getText() + " [" + fldUniqueID.getText()+ "]", userDAO.getLoggedUserUniqueID()));
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
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
}
