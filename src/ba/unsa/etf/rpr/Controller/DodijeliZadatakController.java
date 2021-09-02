package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.TerminDAO;
import ba.unsa.etf.rpr.Model.Termin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DodijeliZadatakController {
    private TerminDAO terminDAO;
    public RadioButton rbSviTermini;
    public RadioButton rbSlobodniTermini;
    public ListView listaTermina;
    private int idInspektora;
    private int idTrenutnoOdabranogTermina = -1;

    @FXML
    public void initialize() throws SQLException {
        terminDAO = TerminDAO.getInstance();
        rbSviTermini.setSelected(true);
        listaTermina.setItems(terminDAO.dajSveTermine());
        listaTermina.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Termin noviTermin = (Termin) newItem;
            if(noviTermin != null)
                idTrenutnoOdabranogTermina = noviTermin.getId();
        });

        rbSviTermini.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                listaTermina.setItems(terminDAO.dajSveTermine());
        });
        rbSlobodniTermini.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                listaTermina.setItems(terminDAO.dajSlobodneTermine());
        });
    }

    public void okBtn(ActionEvent actionEvent) {
        System.out.println(idInspektora);
        System.out.println(idTrenutnoOdabranogTermina);
        if(idTrenutnoOdabranogTermina != -1)
            terminDAO.dodijeliZadatakInspektoru(idInspektora, idTrenutnoOdabranogTermina);
        Stage stage = (Stage) rbSviTermini.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) rbSviTermini.getScene().getWindow();
        stage.close();
    }

    public void setIdInspektora(int idInspektora) {
        this.idInspektora = idInspektora;
    }
}
