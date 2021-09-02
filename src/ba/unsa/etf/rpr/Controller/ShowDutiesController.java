package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.InspektorDAO;
import ba.unsa.etf.rpr.DAL.ObjekatDAO;
import ba.unsa.etf.rpr.DAL.TerminDAO;
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
    private InspektorDAO inspectorDAO;
    private ObjekatDAO objectDAO;
    private TerminDAO taskDAO;
    public ListView tasksList;
    public int inspectorId = 0;
    private int currentTaskID;

    @FXML
    public void initialize() throws SQLException {
        taskDAO = TerminDAO.getInstance();
        inspectorDAO = InspektorDAO.getInstance();
        objectDAO = ObjekatDAO.getInstance();
        tasksList.setItems(taskDAO.dajSveTermineInspektora(inspectorId));
        tasksList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Termin newTask = (Termin) newItem;
            if(newTask != null)
                currentTaskID = newTask.getId();
        });
    }

    public void taskDetailsBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pregledTermina.fxml"));
        Parent root = loader.load();
        PregledTerminaController cont = loader.getController();
        int idInspektora = taskDAO.dajInspektoraZaIDTermina(currentTaskID);
        int idObjekta = taskDAO.dajIDObjektaZaIDTermina(currentTaskID);
        cont.labTerminZakazao.setText(inspectorDAO.dajImePrezimeInspektora(idInspektora));
        cont.labNazivObjekta.setText(objectDAO.dajNazivObjektaZaID(idObjekta));
        cont.labAdresaObjekta.setText(objectDAO.dajAdresuObjektaZaID(idObjekta));
        cont.labDatumVrijemeTermina.setText(taskDAO.dajVrijemeZaID(currentTaskID));
        cont.areaNapomeneTermina.setText(taskDAO.dajNapomeneTerminaZaID(currentTaskID));
        int idZaduzenogInspektora = taskDAO.dajIDZaduzenogInspektora(currentTaskID);
        System.out.println(idZaduzenogInspektora);
        if(idZaduzenogInspektora != -1) cont.labZaduzeniInspektor.setText(inspectorDAO.dajImePrezimeInspektora(idZaduzenogInspektora));
        else cont.labZaduzeniInspektor.setText("Nema zaduženog inspektora");
        boolean terminObavljen = taskDAO.isObavljen(currentTaskID);
        if(terminObavljen) cont.labTerminObavljen.setText("Obavljen");
        else cont.labTerminObavljen.setText("Nije obavljen");
        myStage.setTitle("Pregledaj termin");
        myStage.setResizable(false);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
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
}
