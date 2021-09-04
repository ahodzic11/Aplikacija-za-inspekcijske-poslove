package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ObjectDAO;
import ba.unsa.etf.rpr.DAL.InspectorDAO;
import ba.unsa.etf.rpr.DAL.TaskDAO;
import ba.unsa.etf.rpr.Model.Status;
import ba.unsa.etf.rpr.Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AssignTaskController {
    public Button assignBtn;
    private TaskDAO taskDAO;
    private ObjectDAO objectDAO;
    private InspectorDAO inspectorDAO;
    public RadioButton rbAllTasks;
    public RadioButton rbUntakenTasks;
    public ListView tasksList;
    private int inspectorID;
    private int currentTaskID = -1;
    private Status status;

    @FXML
    public void initialize() throws SQLException {
        taskDAO = TaskDAO.getInstance();
        status = Status.getInstance();
        objectDAO = ObjectDAO.getInstance();
        inspectorDAO = InspectorDAO.getInstance();
        assignBtn.setDisable(true);
        rbAllTasks.setSelected(true);
        tasksList.setItems(taskDAO.getAllTasks());
        tasksList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Task newTask = (Task) newItem;
            if(newTask != null) {
                currentTaskID = newTask.getId();
                assignBtn.setDisable(false);
            }else{
                assignBtn.setDisable(true);
            }
        });

        rbAllTasks.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                tasksList.setItems(taskDAO.getAllTasks());
        });
        rbUntakenTasks.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                tasksList.setItems(taskDAO.getUntakenTasks());
        });
    }

    public void assignBtn(ActionEvent actionEvent) {
        taskDAO.assignTaskToInspectorID(inspectorID, currentTaskID);
        int objectId = taskDAO.getObjectID(currentTaskID);
        status.setStatus("Task [" + objectDAO.getNameForID(objectId) + ", " + objectDAO.getAddressForObjectID(objectId) + " - "
        + taskDAO.getDatetime(currentTaskID) + "] assigned to inspector " + inspectorDAO.getNameSurenameForID(inspectorID) + " [" + inspectorDAO.getUniqueIDForID(inspectorID) + "].");
        Stage stage = (Stage) rbAllTasks.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) rbAllTasks.getScene().getWindow();
        stage.close();
    }

    public void setInspectorID(int idInspektora) {
        this.inspectorID = idInspektora;
    }
}
