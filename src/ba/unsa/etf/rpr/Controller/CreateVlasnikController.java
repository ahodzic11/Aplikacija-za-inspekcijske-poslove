package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.VlasnikDAO;
import ba.unsa.etf.rpr.Model.Vlasnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CreateVlasnikController {
    private VlasnikDAO dao;
    public TextField fldEmail;
    public TextField fldTelefon;
    public TextField fldPrezime;
    public TextField fldIme;
    public TextField fldJMBG;

    @FXML
    public void initialize() throws SQLException {
        dao = VlasnikDAO.getInstance();
    }

    public void dodajBtn(ActionEvent actionEvent) throws SQLException, IOException {
        dao.dodaj(new Vlasnik(1, fldIme.getText(), fldPrezime.getText(), fldJMBG.getText(), Integer.parseInt(fldTelefon.getText()), fldEmail.getText()));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/objektiVlasnici.fxml"));
        Parent root = loader.load();
        ObjektiVlasniciController ovc = loader.getController();
        ovc.refresujVlasnike();
        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }
}
