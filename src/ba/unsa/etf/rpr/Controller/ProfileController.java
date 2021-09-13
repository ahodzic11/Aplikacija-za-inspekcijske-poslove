package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ActionLogDAO;
import ba.unsa.etf.rpr.DAL.UserDAO;
import ba.unsa.etf.rpr.Utility.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
    private ActionLogDAO actionLogDAO;
    private UserDAO userDAO;

    @FXML
    public void initialize() throws SQLException {
        status = Status.getInstance();
        actionLogDAO = ActionLogDAO.getInstance();
        userDAO = UserDAO.getInstance();
    }



    public void closeBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) labInspectorType.getScene().getWindow();
        stage.close();
    }
}
