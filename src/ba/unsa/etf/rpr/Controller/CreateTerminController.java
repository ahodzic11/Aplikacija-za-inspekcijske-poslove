package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.UserDAO;
import ba.unsa.etf.rpr.DAL.TaskDAO;
import ba.unsa.etf.rpr.Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class CreateTerminController {
    public TextField fldObjectName;
    public TextField fldObjectAddress;
    public TextField fldHours;
    public TextField fldMinutes;
    public TextArea areaNotes;
    public DatePicker datePickTask;
    private TaskDAO taskDAO;
    private UserDAO userDAO;
    public int idObjekta;

    @FXML
    public void initialize() throws SQLException {
        taskDAO = TaskDAO.getInstance();
        userDAO = UserDAO.getInstance();
    }

    public void okBtn(ActionEvent actionEvent) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        taskDAO.addTask(new Task(1, idObjekta, userDAO.getLoggedUserID(), datePickTask.getValue().format(formatter), areaNotes.getText(), 0, -1));
        Stage stage = (Stage) fldObjectName.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldObjectName.getScene().getWindow();
        stage.close();
    }
}
