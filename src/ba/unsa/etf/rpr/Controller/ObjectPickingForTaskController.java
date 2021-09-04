package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ObjectDAO;
import ba.unsa.etf.rpr.DAL.OwnerDAO;
import ba.unsa.etf.rpr.DAL.ReportDAO;
import ba.unsa.etf.rpr.DAL.WitnessDAO;
import ba.unsa.etf.rpr.Model.Object;
import ba.unsa.etf.rpr.Model.Owner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ObjectPickingForTaskController {
    public ListView ownersList;
    public ListView objectsList;
    public TextField fldName;
    public TextField fldSurename;
    public TextField fldAddress;
    public ComboBox comboObjectType;
    public TextField fldJMBG;
    public TextField fldPhoneNumber;
    public TextField fldEmail;
    public TextField fldOwnerName;
    private ReportDAO reportDAO;
    private OwnerDAO ownerDAO;
    private ObjectDAO objectDAO;
    private WitnessDAO witnessDAO;
    private int selectedOwnerID;
    private int selectedObjectID = -1;

    @FXML
    public void initialize() throws SQLException {
        ownerDAO = OwnerDAO.getInstance();
        objectDAO = ObjectDAO.getInstance();
        reportDAO = ReportDAO.getInstance();
        witnessDAO = WitnessDAO.getInstance();
        comboObjectType.getItems().addAll("Obrazovna institucija", "Zdravstvena institucija", "Ugostiteljski objekat");
        ownersList.setItems(ownerDAO.allOwners());
        ownersList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Owner newOwner = (Owner) newItem;
            if(newOwner != null){
                selectedOwnerID = newOwner.getId();
                fldOwnerName.setText(newOwner.getName());
                fldSurename.setText(newOwner.getSurename());
                fldJMBG.setText(newOwner.getJmbg());
                fldPhoneNumber.setText(String.valueOf(newOwner.getPhoneNumber()));
                fldEmail.setText(newOwner.getEmail());
                if(objectDAO.getObjectsFromOwner(selectedOwnerID) != null)
                    objectsList.setItems(objectDAO.getObjectsFromOwner(selectedOwnerID));
                objectsList.getSelectionModel().selectedItemProperty().addListener((obs1, oldObjekat, newObjekat)->{
                    Object newObject = (Object) newObjekat;
                    if(newObject != null){
                        selectedObjectID = newObject.getId();
                        fldName.setText(newObject.getName());
                        fldAddress.setText(newObject.getAddress());
                        comboObjectType.getSelectionModel().select(objectDAO.getObjectTypeForID(selectedObjectID));
                    }else{
                        fldName.setText("");
                        fldAddress.setText("");
                        comboObjectType.getSelectionModel().select("");
                        selectedObjectID = -1;
                    }
                });
            }else{
                fldOwnerName.setText("");
                fldSurename.setText("");
                fldJMBG.setText("");
                fldPhoneNumber.setText("");
                fldEmail.setText("");
                selectedOwnerID = -1;
            }
        });
    }

    public void createOwner(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/createOwner.fxml"));
        myStage.setTitle("Create an owner!");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        refreshOwners();
    }

    public void createObjekatBtn(ActionEvent actionEvent) throws SQLException, IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/createObject.fxml"));
        Parent root = loader.load();
        CreateObjekatController cont = loader.getController();
        cont.setOwnerId(selectedOwnerID);
        myStage.setTitle("Create an object!");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        refreshOwners();
        refreshObjects();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ownersList.getScene().getWindow();
        stage.close();
    }

    public void deleteObject(ActionEvent actionEvent) {
        objectDAO.deleteObjectWithID(selectedObjectID);
        refreshObjects();
    }

    private void refreshObjects() {
        objectsList.setItems(objectDAO.getObjectsFromOwner(selectedOwnerID));
    }

    public void refreshOwners() {
        ownersList.setItems(ownerDAO.allOwners());
    }

    public void deleteVlasnikBtn(ActionEvent actionEvent) {
        objectDAO.deleteOwnersObjects(selectedOwnerID);
        ownerDAO.deleteOwner(selectedOwnerID);
        refreshOwners();
        refreshObjects();
    }

    public void pickObjectBtn(ActionEvent actionEvent) throws IOException {
        if(selectedObjectID !=-1){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Stage myStage = new Stage();
            myStage.setTitle("Create a task!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/createTermin.fxml"));
            Parent root = loader.load();
            CreateTerminController cont = loader.getController();
            cont.fldObjectName.setText(objectDAO.getNameForID(selectedObjectID));
            cont.fldObjectAddress.setText(objectDAO.getAddressForObjectID(selectedObjectID));
            cont.datePickTask.getEditor().setText(LocalDate.now().format(formatter));
            cont.objectId = selectedObjectID;
            myStage.setResizable(false);
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.showAndWait();
            Stage stage = (Stage) fldOwnerName.getScene().getWindow();
            stage.close();
        }
    }
}
