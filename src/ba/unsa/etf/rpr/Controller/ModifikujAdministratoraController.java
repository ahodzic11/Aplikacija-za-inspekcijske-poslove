package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.PrijavljeniUserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ModifikujAdministratoraController {
    private PrijavljeniUserDAO prijavljeniUserDAO;
    private AdministratorDAO administratorDAO;
    public TextField fldEmail;
    public TextField fldSifra;
    public TextField fldJedinstvenaSifra;

    @FXML
    public void initialize() throws SQLException {
        prijavljeniUserDAO = PrijavljeniUserDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
    }

    public void okBtn(ActionEvent actionEvent) {
        String jedinstvenaSifra = prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog();
        int idAdministratora = administratorDAO.dajIDZaJedinstvenuSifru(jedinstvenaSifra);
        administratorDAO.modifikuj(idAdministratora, fldEmail.getText(), fldSifra.getText(), fldJedinstvenaSifra.getText());
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldEmail.getScene().getWindow();
        stage.close();
    }
}
