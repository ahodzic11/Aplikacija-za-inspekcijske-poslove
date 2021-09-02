package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.PrijavljeniUserDAO;
import ba.unsa.etf.rpr.DAL.TerminDAO;
import ba.unsa.etf.rpr.Model.Termin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class CreateTerminController {
    public TextField fldNazivObjekta;
    public TextField fldAdresaObjekta;
    public TextField fldSati;
    public TextField fldMinuta;
    public TextArea areaNapomene;
    public DatePicker datePickTermin;
    private TerminDAO terminDAO;
    private PrijavljeniUserDAO prijavljeniUserDAO;
    public int idObjekta;

    @FXML
    public void initialize() throws SQLException {
        terminDAO = TerminDAO.getInstance();
        prijavljeniUserDAO = PrijavljeniUserDAO.getInstance();
    }

    public void okBtn(ActionEvent actionEvent) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        terminDAO.dodajTermin(new Termin(1, idObjekta, prijavljeniUserDAO.dajIdUlogovanogInspektora(), datePickTermin.getValue().format(formatter), areaNapomene.getText(), 0, -1));
        Stage stage = (Stage) fldNazivObjekta.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldNazivObjekta.getScene().getWindow();
        stage.close();
    }
}
