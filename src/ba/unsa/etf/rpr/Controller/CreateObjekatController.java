package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ObjekatDAO;
import ba.unsa.etf.rpr.Model.Objekat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateObjekatController {
    private int idVlasnika;
    public TextField fldNaziv;
    public TextField fldAdresa;
    public ComboBox comboVrstaObjekta;
    private ObjekatDAO objekatDao;

    @FXML
    public void initialize() throws SQLException {
        objekatDao = ObjekatDAO.getInstance();
        comboVrstaObjekta.getItems().addAll("Obrazovna institucija", "Zdravstvena institucija", "Ugostiteljski objekat");
    }


    public void okBtn(ActionEvent actionEvent) throws SQLException {
        objekatDao.dodaj(new Objekat(1, idVlasnika, fldNaziv.getText(), fldAdresa.getText(), (String) comboVrstaObjekta.getValue()));
        Stage stage = (Stage) fldNaziv.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldNaziv.getScene().getWindow();
        stage.close();
    }

    public int getIdVlasnika() {
        return idVlasnika;
    }

    public void setIdVlasnika(int idVlasnika) {
        this.idVlasnika = idVlasnika;
    }
}
