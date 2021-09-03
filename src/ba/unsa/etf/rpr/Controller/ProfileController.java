package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.LogAkcijaDAO;
import ba.unsa.etf.rpr.DAL.PrijavljeniUserDAO;
import ba.unsa.etf.rpr.Model.LogAkcije;
import ba.unsa.etf.rpr.Model.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProfileController {
    public Label labFirstName;
    public Label labLastName;
    public Label labBirthdate;
    public Label labJMBG;
    public Label labGender;
    public Label labIDNumber;
    public Label labEmail;
    public Label labPhoneNumber;
    public Label labResidence;
    public Label labUniqueID;
    public Label labLoginEmail;
    public Label labPassword;
    public Label labDriversLicense;
    public Label labInspectionArea;
    public Label labInspectorType;
    private Status status;
    private LogAkcijaDAO logAkcijaDAO;
    private PrijavljeniUserDAO prijavljeniUserDAO;

    @FXML
    public void initialize() throws SQLException {
        status = Status.getInstance();
        logAkcijaDAO = LogAkcijaDAO.getInstance();
        prijavljeniUserDAO = PrijavljeniUserDAO.getInstance();
    }



    public void closeBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) labInspectorType.getScene().getWindow();
        stage.close();
    }
}
