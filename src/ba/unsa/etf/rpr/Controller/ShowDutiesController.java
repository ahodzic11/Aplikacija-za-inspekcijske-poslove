package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ObjectDAO;
import ba.unsa.etf.rpr.DAL.InspectorDAO;
import ba.unsa.etf.rpr.DAL.TaskDAO;
import ba.unsa.etf.rpr.Utility.Status;
import ba.unsa.etf.rpr.Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ShowDutiesController {
    public Button detailsBtn;
    private InspectorDAO inspectorDAO;
    private ObjectDAO objectDAO;
    private TaskDAO taskDAO;
    private Status status;
    public ListView tasksList;
    public int inspectorId = 0;
    private int currentTaskID;

    @FXML
    public void initialize() throws SQLException {
        taskDAO = TaskDAO.getInstance();
        inspectorDAO = InspectorDAO.getInstance();
        objectDAO = ObjectDAO.getInstance();
        status = Status.getInstance();
        detailsBtn.setDisable(true);
        tasksList.setItems(taskDAO.getAllTasksForInspector(inspectorId));
        tasksList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Task newTask = (Task) newItem;
            if(newTask != null){
                currentTaskID = newTask.getId();
                detailsBtn.setDisable(false);
            }else{
                detailsBtn.setDisable(true);
            }
        });
    }

    public void taskDetailsBtn(ActionEvent actionEvent) throws IOException {
        int objectId = taskDAO.getObjectID(currentTaskID);
        status.setStatus("Task details for task [" + objectDAO.getNameForID(objectId) + ", " + objectDAO.getAddressForObjectID(objectId) + " - "
                + taskDAO.getDatetime(currentTaskID) + "] have been shown.");
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pregledTermina.fxml"));
        Parent root = loader.load();
        PregledTerminaController cont = loader.getController();
        int idInspektora = taskDAO.getInspectorForID(currentTaskID);
        int idObjekta = taskDAO.getObjectID(currentTaskID);
        cont.labTerminZakazao.setText(inspectorDAO.getNameSurenameForID(idInspektora));
        cont.labNazivObjekta.setText(objectDAO.getNameForID(idObjekta));
        cont.labAdresaObjekta.setText(objectDAO.getAddressForObjectID(idObjekta));
        cont.labDatumVrijemeTermina.setText(taskDAO.getDatetime(currentTaskID));
        cont.areaNapomeneTermina.setText(taskDAO.getNotesForTask(currentTaskID));
        int idZaduzenogInspektora = taskDAO.getAssignedInspectorID(currentTaskID);
        if(idZaduzenogInspektora != -1) cont.labZaduzeniInspektor.setText(inspectorDAO.getNameSurenameForID(idZaduzenogInspektora));
        else cont.labZaduzeniInspektor.setText("No assigned inspector");
        boolean terminObavljen = taskDAO.isCompleted(currentTaskID);
        if(terminObavljen) cont.labTerminObavljen.setText("Done");
        else cont.labTerminObavljen.setText("Not done");
        myStage.setTitle("Task details");
        myStage.setResizable(false);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        updateStatus();
    }

    public void closeBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) tasksList.getScene().getWindow();
        stage.close();
    }

    public int getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(int inspectorId) {
        this.inspectorId = inspectorId;
    }

    private void updateStatus() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavniProzorAdmin.fxml"));
        Parent root = loader.load();
        GlavniProzorAdminController cont = loader.getController();
        cont.updateStatus();
    }
}
