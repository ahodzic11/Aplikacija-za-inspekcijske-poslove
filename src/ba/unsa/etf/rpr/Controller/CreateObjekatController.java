package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ObjectDAO;
import ba.unsa.etf.rpr.Model.Object;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateObjekatController {
    private int ownerId;
    public TextField fldName;
    public TextField fldAddress;
    public ComboBox comboObjectType;
    private ObjectDAO objectDAO;

    @FXML
    public void initialize() throws SQLException {
        objectDAO = ObjectDAO.getInstance();
        comboObjectType.getItems().addAll("Obrazovna institucija", "Zdravstvena institucija", "Ugostiteljski objekat");
    }


    public void okBtn(ActionEvent actionEvent) throws SQLException {
        objectDAO.addObject(new Object(1, ownerId, fldName.getText(), fldAddress.getText(), (String) comboObjectType.getValue()));
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
