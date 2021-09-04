package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.done.InspectorDAO;
import ba.unsa.etf.rpr.DAL.ObjekatDAO;
import ba.unsa.etf.rpr.DAL.TerminDAO;
import ba.unsa.etf.rpr.Model.Status;
import ba.unsa.etf.rpr.Model.Termin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ShowDutiesController {
    private InspectorDAO inspectorDAO;
    private ObjekatDAO objectDAO;
    private TerminDAO taskDAO;
    private Status status;
    public ListView tasksList;
    public int inspectorId = 0;
    private int currentTaskID;

    @FXML
    public void initialize() throws SQLException {
        taskDAO = TerminDAO.getInstance();
        inspectorDAO = InspectorDAO.getInstance();
        objectDAO = ObjekatDAO.getInstance();
        status = Status.getInstance();

        tasksList.setItems(taskDAO.dajSveTermineInspektora(inspectorId));
        tasksList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Termin newTask = (Termin) newItem;
            if(newTask != null)
                currentTaskID = newTask.getId();
        });
    }

    public void taskDetailsBtn(ActionEvent actionEvent) throws IOException {
        int objectId = taskDAO.dajIDObjektaZaIDTermina(currentTaskID);
        status.setStatus("Task details for task [" + objectDAO.dajNazivObjektaZaID(objectId) + ", " + objectDAO.dajAdresuObjektaZaID(objectId) + " - "
                + taskDAO.dajVrijemeZaID(currentTaskID) + "] have been shown.");
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pregledTermina.fxml"));
        Parent root = loader.load();
        PregledTerminaController cont = loader.getController();
        int idInspektora = taskDAO.dajInspektoraZaIDTermina(currentTaskID);
        int idObjekta = taskDAO.dajIDObjektaZaIDTermina(currentTaskID);
        cont.labTerminZakazao.setText(inspectorDAO.getNameSurenameForID(idInspektora));
        cont.labNazivObjekta.setText(objectDAO.dajNazivObjektaZaID(idObjekta));
        cont.labAdresaObjekta.setText(objectDAO.dajAdresuObjektaZaID(idObjekta));
        cont.labDatumVrijemeTermina.setText(taskDAO.dajVrijemeZaID(currentTaskID));
        cont.areaNapomeneTermina.setText(taskDAO.dajNapomeneTerminaZaID(currentTaskID));
        int idZaduzenogInspektora = taskDAO.dajIDZaduzenogInspektora(currentTaskID);
        if(idZaduzenogInspektora != -1) cont.labZaduzeniInspektor.setText(inspectorDAO.getNameSurenameForID(idZaduzenogInspektora));
        else cont.labZaduzeniInspektor.setText("No assigned inspector");
        boolean terminObavljen = taskDAO.isObavljen(currentTaskID);
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
