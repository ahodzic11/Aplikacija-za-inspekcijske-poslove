package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.done.InspectorDAO;
import ba.unsa.etf.rpr.DAL.ObjekatDAO;
import ba.unsa.etf.rpr.DAL.TerminDAO;
import ba.unsa.etf.rpr.Model.Status;
import ba.unsa.etf.rpr.Model.Termin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AssignTaskController {
    private TerminDAO taskDAO;
    private ObjekatDAO objectDAO;
    private InspectorDAO inspectorDAO;
    public RadioButton rbAllTasks;
    public RadioButton rbUntakenTasks;
    public ListView tasksList;
    private int inspectorID;
    private int currentTaskID = -1;
    private Status status;

    @FXML
    public void initialize() throws SQLException {
        taskDAO = TerminDAO.getInstance();
        status = Status.getInstance();
        objectDAO = ObjekatDAO.getInstance();
        inspectorDAO = InspectorDAO.getInstance();

        rbAllTasks.setSelected(true);
        tasksList.setItems(taskDAO.dajSveTermine());
        tasksList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Termin newTask = (Termin) newItem;
            if(newTask != null)
                currentTaskID = newTask.getId();
        });

        rbAllTasks.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                tasksList.setItems(taskDAO.dajSveTermine());
        });
        rbUntakenTasks.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem)
                tasksList.setItems(taskDAO.dajSlobodneTermine());
        });
    }

    public void okBtn(ActionEvent actionEvent) {
        if(currentTaskID != -1){
            taskDAO.assignTaskToInspectorID(inspectorID, currentTaskID);
            int objectId = taskDAO.dajIDObjektaZaIDTermina(currentTaskID);
            status.setStatus("Task [" + objectDAO.dajNazivObjektaZaID(objectId) + ", " + objectDAO.dajAdresuObjektaZaID(objectId) + " - "
            + taskDAO.dajVrijemeZaID(currentTaskID) + "] assigned to inspector " + inspectorDAO.getNameSurenameForID(inspectorID) + " [" + inspectorDAO.getUniqueIDForID(inspectorID) + "].");
        }
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
