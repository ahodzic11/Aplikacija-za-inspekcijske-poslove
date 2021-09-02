package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.AdministratorDAO;
import ba.unsa.etf.rpr.Model.Administrator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateAdminController {
    private AdministratorDAO administratorDAO;
    public Label fldEmail;
    public Label fldSifra;
    public Label fldJedinstvenaSifra;

    @FXML
    public void initialize() throws SQLException {
        administratorDAO = AdministratorDAO.getInstance();
    }

    public void kreirajAdminaBtn(ActionEvent actionEvent) throws SQLException {
        administratorDAO.dodaj(new Administrator(1, fldEmail.getText(), fldSifra.getText(), fldJedinstvenaSifra.getText()));
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
    }
}
