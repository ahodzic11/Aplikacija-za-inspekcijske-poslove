package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.Model.ActionLog;
import ba.unsa.etf.rpr.Model.Inspector;
import ba.unsa.etf.rpr.Model.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavniProzorAdminController {
    public ListView inspectorList;
    public Label labInfo;
    public Label labUniqueID;
    public Inspector currentInspector;
    public int currentInspectorID;
    public Button profileBtn;
    public Button reportsBtn;
    public Button modifyBtn;
    public Button deleteBtn;
    public Button assignTaskBtn;
    public Button showDutiesBtn;
    public Label labStatusBar;
    public Label labInspectorType;
    public Label labEmail;
    public Label labPhoneNumber;
    public Button exportBtn;
    private InspectorDAO inspectorDAO;
    private AdministratorDAO administratorDAO;
    private UserDAO userDAO;
    private LoginLogDAO loginLogDAO;
    private ActionLogDAO actionLogDAO;
    private Status status;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @FXML
    public void initialize() throws SQLException {
        inspectorDAO = InspectorDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
        userDAO = UserDAO.getInstance();
        actionLogDAO = ActionLogDAO.getInstance();
        loginLogDAO = LoginLogDAO.getInstance();
        status = Status.getInstance();

        disableButtons();

        inspectorList.setItems(inspectorDAO.allInspectors());
        inspectorList.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Inspector newInspector = (Inspector) newItem;
            if(newInspector!=null){
                labInfo.setText(newInspector.getFirstName() + " " + newInspector.getLastName());
                labUniqueID.setText("UNIQUE ID: " + newInspector.getUniqueId());
                currentInspectorID = newInspector.getId();
                labInspectorType.setText(newInspector.getInspectorType());
                labEmail.setText(newInspector.getLoginEmail());
                labPhoneNumber.setText(newInspector.getPhoneNumber());

                currentInspector = newInspector;
                enableButtons();
            }else{
                disableButtons();
            }
        });
    }

    public void openCreateAccount(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/createAccount.fxml"));
        myStage.setTitle("Create an account!");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        refreshInspectorsList();
        updateStatus();
        inspectorList.getSelectionModel().select(inspectorList.getItems().size()-1);
    }

    public void profilBtn(ActionEvent actionEvent) throws IOException, SQLException {
        status.setStatus("Inspector profile - " + inspectorDAO.getNameSurenameForID(currentInspectorID) + " [" + inspectorDAO.getUniqueIDForID(currentInspectorID) + "] opened.");
        updateStatus();

        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inspectorProfile.fxml"));
        Parent root = loader.load();

        ProfileController profileController = loader.getController();
        profileController.labUniqueID.setText(currentInspector.getUniqueId());
        profileController.labFirstName.setText(inspectorDAO.getFirstNameForID(currentInspectorID));
        profileController.labLastName.setText(inspectorDAO.getSurenameForID(currentInspectorID));
        profileController.labBirthdate.setText(inspectorDAO.getBirthdateForID(currentInspectorID));
        profileController.labJMBG.setText(inspectorDAO.getJMBGForID(currentInspectorID));
        if(inspectorDAO.getGenderForID(currentInspectorID)==1) profileController.labGender.setText("M");
        else profileController.labGender.setText("F");
        profileController.labIDNumber.setText(inspectorDAO.getIDNumberForID(currentInspectorID));
        profileController.labResidence.setText(inspectorDAO.getResidenceForID(currentInspectorID));
        profileController.labPhoneNumber.setText(inspectorDAO.getPhoneNumberForID(currentInspectorID));
        profileController.labEmail.setText(inspectorDAO.getEmailForID(currentInspectorID));
        profileController.labLoginEmail.setText(inspectorDAO.getLoginEmailForID(currentInspectorID));
        profileController.labPassword.setText(inspectorDAO.getPasswordForID(currentInspectorID));
        profileController.labInspectionArea.setText(inspectorDAO.getInspectionAreaForID(currentInspectorID));
        profileController.labInspectorType.setText(inspectorDAO.getInspectorTypeForID(currentInspectorID));
        if(inspectorDAO.getDriversLicenseForID(currentInspectorID)==1) profileController.labDriversLicense.setText("owns a license");
        else profileController.labDriversLicense.setText("doesn't own a license");

        String action = "Administrator[" + userDAO.getLoggedUserUniqueID()+"] opened account - " + inspectorDAO.getNameSurenameForID(currentInspectorID) + "[" + inspectorDAO.getUniqueIDForID(currentInspectorID)+"]";

        actionLogDAO.addLog(new ActionLog(1, LocalDateTime.now().format(formatter), action, userDAO.getLoggedUserUniqueID()));

        myStage.setTitle("Inspector profile");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();

        updateStatus();
    }

    public void deleteBtn(ActionEvent actionEvent) throws SQLException {
        status.setStatus("Inspector profile - " + inspectorDAO.getNameSurenameForID(currentInspectorID) + " [" + inspectorDAO.getUniqueIDForID(currentInspectorID) + "] deleted.");

        String action = "Administrator[" + userDAO.getLoggedUserUniqueID()+"] deleted account - " + inspectorDAO.getInspectorTypeForID(currentInspectorID) + " " + inspectorDAO.getNameSurenameForID(currentInspectorID) + "[" + inspectorDAO.getUniqueIDForID(currentInspectorID)+"]";
        actionLogDAO.addLog(new ActionLog(1, LocalDateTime.now().format(formatter), action, userDAO.getLoggedUserUniqueID()));

        inspectorDAO.deleteInspector(currentInspector);
        refreshInspectorsList();

        labInfo.setText("");
        labUniqueID.setText("");

        updateStatus();
    }

    public void modifyBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifyProfile.fxml"));
        Parent root = loader.load();

        ModifyProfileController mod = loader.getController();
        mod.idOtvorenog = currentInspectorID;
        mod.fldIme.setText(inspectorDAO.getFirstNameForID(currentInspectorID));
        mod.fldPrezime.setText(inspectorDAO.getSurenameForID(currentInspectorID));
        mod.fldBrojLicne.setText(inspectorDAO.getIDNumberForID(currentInspectorID));
        mod.fldMjestoPrebivalista.setText(inspectorDAO.getResidenceForID(currentInspectorID));
        mod.fldKontaktTelefon.setText(inspectorDAO.getPhoneNumberForID(currentInspectorID));
        mod.fldPersonalniMail.setText(inspectorDAO.getEmailForID(currentInspectorID));
        mod.fldPristupniMail.setText(inspectorDAO.getLoginEmailForID(currentInspectorID));
        mod.fldPristupnaSifra.setText(inspectorDAO.getPasswordForID(currentInspectorID));
        mod.comboOblastInspekcije.setValue(inspectorDAO.getInspectionAreaForID(currentInspectorID));
        if(inspectorDAO.isMajorInspector(currentInspectorID)) mod.rbGlavniInspektor.setSelected(true);
        else mod.rbFederalniInspektor.setSelected(true);
        if(inspectorDAO.getDriversLicenseForID(currentInspectorID)==1) mod.cbVozacka.setSelected(true);
        else mod.cbVozacka.setSelected(false);

        myStage.setTitle("Modify the inspector");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        refreshInspectorsList();

        inspectorList.getSelectionModel().select(currentInspectorID);
        labInfo.setText(inspectorDAO.getFirstNameForID(currentInspectorID) + " " + inspectorDAO.getSurenameForID(currentInspectorID));
        labUniqueID.setText("UNIQUE ID: " + inspectorDAO.getUniqueIDForID(currentInspectorID));
        updateStatus();
    }

    public void refreshInspectorsList(){
        inspectorList.setItems(inspectorDAO.allInspectors());
    }

    public void logoutBtn(ActionEvent actionEvent) throws IOException {
        loginLogDAO.logout(userDAO.getLoggedUserUniqueID());
        userDAO.deleteLoggedUser();
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/preview.fxml"));
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
        Stage stage = (Stage) labUniqueID.getScene().getWindow();
        stage.close();
    }

    public void reportsBtn(ActionEvent actionEvent) throws IOException {
        status.setStatus("Reports for inspector profile - " + inspectorDAO.getNameSurenameForID(currentInspectorID) + " [" + inspectorDAO.getUniqueIDForID(currentInspectorID) + "] opened.");
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/izvjestaji.fxml"));
        Parent root = loader.load();
        IzvjestajiController cont = loader.getController();
        cont.setIdInspektora(currentInspectorID);
        cont.refresujIzvjestaj();

        myStage.setTitle("Reports");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        updateStatus();
    }

    public void exitBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) inspectorList.getScene().getWindow();
        stage.close();
    }

    public void changeAdminLoginData(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifyAdministrator.fxml"));
        Parent root = loader.load();

        ModifyAdministratorController cont = loader.getController();
        String adminUniqueID = userDAO.getLoggedUserUniqueID();
        cont.fldEmail.setText(administratorDAO.getEmailForUniqueID(adminUniqueID));
        cont.fldSifra.setText(administratorDAO.getPasswordForUniqueID(adminUniqueID));
        cont.fldJedinstvenaSifra.setText(adminUniqueID);

        myStage.setTitle("Modify the administrator");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        updateStatus();
    }

    public void createAdminAccountBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/createAdmin.fxml"));
        myStage.setTitle("Create an admin account");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void logoviBtn(ActionEvent actionEvent) throws IOException {
        status.setStatus("Logs opened.");
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/logs.fxml"));
        Parent root = loader.load();
        LogoviController cont = loader.getController();
        myStage.setTitle("Logs");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        updateStatus();
    }

    public void assignTaskBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/assignTask.fxml"));
        Parent root = loader.load();
        AssignTaskController cont = loader.getController();
        cont.setInspectorID(currentInspectorID);
        myStage.setTitle("Assign a task");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        updateStatus();
    }

    public void updateStatus(){
        labStatusBar.setText(status.getStatus());
    }

    public void showDutiesBtn(ActionEvent actionEvent) throws IOException {
        status.setStatus("Duties assigned to inspector " + inspectorDAO.getNameSurenameForID(currentInspectorID) + " [" + inspectorDAO.getUniqueIDForID(currentInspectorID) + "] have been shown.");
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/showDuties.fxml"));
        Parent root = loader.load();
        ShowDutiesController cont = loader.getController();
        cont.inspectorId = currentInspectorID;
        myStage.setTitle("Duties assigned");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        updateStatus();
        myStage.showAndWait();
        updateStatus();
    }


    private void disableButtons() {
        profileBtn.setDisable(true);
        modifyBtn.setDisable(true);
        reportsBtn.setDisable(true);
        deleteBtn.setDisable(true);
        assignTaskBtn.setDisable(true);
        showDutiesBtn.setDisable(true);
        exportBtn.setDisable(true);
    }

    private void enableButtons(){
        profileBtn.setDisable(false);
        modifyBtn.setDisable(false);
        reportsBtn.setDisable(false);
        deleteBtn.setDisable(false);
        assignTaskBtn.setDisable(false);
        showDutiesBtn.setDisable(false);
        exportBtn.setDisable(false);
    }

    public void exportBtn(ActionEvent actionEvent) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text document", "*.txt"));
        File file = chooser.showSaveDialog(labPhoneNumber.getScene().getWindow());
        if(file!=null){
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                String exportData = labInspectorType.getText() + " " + inspectorDAO.getNameSurenameForID(currentInspectorID) + "[" + labUniqueID.getText() + "]\n\n";
                exportData += "Inspection area: " + inspectorDAO.getInspectionAreaForID(currentInspectorID) + "\n";
                exportData += "Birthdate: " + inspectorDAO.getBirthdateForID(currentInspectorID) + "\n";
                exportData += "JMBG: " + inspectorDAO.getJMBGForID(currentInspectorID) + "\n";
                if(inspectorDAO.getGenderForID(currentInspectorID)==1) exportData+= "Gender: Male\n";
                else exportData += "Gender: Female\n";
                exportData += "ID number: " + inspectorDAO.getIDNumberForID(currentInspectorID) + "\n";
                exportData += "E-mail: " + labEmail.getText() + "\n";
                exportData += "Phone number: " + labPhoneNumber.getText() + "\n";
                exportData += "Residency: " + inspectorDAO.getResidenceForID(currentInspectorID) + "\n";
                exportData += "Login e-mail: " + labEmail.getText() + "\n";
                if(inspectorDAO.getDriversLicenseForID(currentInspectorID)==1) exportData+= "Has a valid driver's license\n";
                else exportData += "Doesn't have a valid driver's license\n";
                status.setStatus("Inspector profile - " + inspectorDAO.getFirstNameForID(currentInspectorID) + " " + inspectorDAO.getSurenameForID(currentInspectorID) +  " [" + labUniqueID.getText() + "] exported.");
                actionLogDAO.addLog(new ActionLog(1, LocalDateTime.now().format(formatter), "Administrator [" + userDAO.getLoggedUserUniqueID()+ "] exported profile - " + inspectorDAO.getFirstNameForID(currentInspectorID) + " " + inspectorDAO.getSurenameForID(currentInspectorID) +  " [" + labUniqueID.getText() + "] ", userDAO.getLoggedUserUniqueID()));
                writer.println(exportData);
                writer.close();
            } catch (IOException | SQLException ex) {
                System.out.println("Error exporting file!");
            }
        }
    }

    public void aboutBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        myStage.setTitle("About");
        myStage.setResizable(false);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }
}
