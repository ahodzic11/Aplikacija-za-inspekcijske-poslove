package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.TaskDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ModifyTerminController {
    public TextField fldNazivObjekta;
    public TextField fldAdresaObjekta;
    public TextField fldSati;
    public TextField fldMinuta;
    public TextArea areaNapomene;
    public DatePicker datePickTermin;
    private TaskDAO terminDAO;
    public int idTermina;

    @FXML
    public void initialize() throws SQLException {
        terminDAO = TaskDAO.getInstance();
        fldNazivObjekta.setDisable(true);
        fldAdresaObjekta.setDisable(true);
    }

    public void modifikujBtn(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        terminDAO.modifyTask(idTermina, terminDAO.getObjectID(idTermina), terminDAO.getInspectorForID(idTermina),
                datePickTermin.getValue().format(formatter), areaNapomene.getText(), terminDAO.getTaskDoneStatus(idTermina),
                terminDAO.getAssignedInspectorID(idTermina));
        Stage stage = (Stage) fldNazivObjekta.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldNazivObjekta.getScene().getWindow();
        stage.close();
    }
}
