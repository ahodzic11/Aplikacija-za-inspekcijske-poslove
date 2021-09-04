package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.done.AdministratorDAO;
import ba.unsa.etf.rpr.Model.Administrator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateAdminController {
    private AdministratorDAO administratorDAO;
    public Label fldEmail;
    public Label fldPassword;
    public Label fldUniqueID;

    @FXML
    public void initialize() throws SQLException {
        administratorDAO = AdministratorDAO.getInstance();
    }

    public void createAdminBtn(ActionEvent actionEvent) throws SQLException {
        administratorDAO.addAdministrator(new Administrator(1, fldEmail.getText(), fldPassword.getText(), fldUniqueID.getText()));
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
    }
}
