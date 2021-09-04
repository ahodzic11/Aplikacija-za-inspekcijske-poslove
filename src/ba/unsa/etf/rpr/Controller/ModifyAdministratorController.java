package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.UserDAO;
import ba.unsa.etf.rpr.Model.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ModifyAdministratorController {
    private UserDAO prijavljeniUserDAO;
    private AdministratorDAO administratorDAO;
    public TextField fldEmail;
    public TextField fldSifra;
    public TextField fldJedinstvenaSifra;
    private Status status;

    @FXML
    public void initialize() throws SQLException {
        prijavljeniUserDAO = UserDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
        status = Status.getInstance();
    }

    public void okBtn(ActionEvent actionEvent) {
        String jedinstvenaSifra = prijavljeniUserDAO.getLoggedUserUniqueID();
        int idAdministratora = administratorDAO.getIdForUniqueID(jedinstvenaSifra);
        administratorDAO.modifyAdministrator(idAdministratora, fldEmail.getText(), fldSifra.getText(), fldJedinstvenaSifra.getText());
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
        status.setStatus("Administrator profile [" + jedinstvenaSifra + "] login data changed.");
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
    }
}
