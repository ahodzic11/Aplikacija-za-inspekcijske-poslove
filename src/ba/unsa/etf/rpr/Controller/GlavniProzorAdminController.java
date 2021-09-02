package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.Model.Inspektor;
import ba.unsa.etf.rpr.Model.LogAkcije;
import ba.unsa.etf.rpr.Model.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavniProzorAdminController {
    public ListView listaInspektora;
    public Label labelInfo;
    public Label labelJedinstvenaSifra;
    public Inspektor trenutnoPrikazani;
    public int idTrenutnoPrikazanog;
    public ImageView imgAvatar;
    public Button profilBtn;
    public Button izvjestajiBtn;
    public Button modifikujBtn;
    public Button obrisiBtn;
    public Button dodijeliZadatakBtn;
    public Button pregledajZadatkeBtn;
    public Label labStatusBar;
    private InspektorDAO inspektorDao;
    private AdministratorDAO administratorDAO;
    private PrijavljeniUserDAO prijavljeniUserDAO;
    private LogDAO logDAO;
    private LogAkcijaDAO logAkcijaDAO;
    private Status status;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @FXML
    public void initialize() throws SQLException {
        inspektorDao = InspektorDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
        prijavljeniUserDAO = PrijavljeniUserDAO.getInstance();
        logAkcijaDAO = LogAkcijaDAO.getInstance();
        logDAO = LogDAO.getInstance();
        status = Status.getInstance();

        disableButtons();

        listaInspektora.setItems(inspektorDao.sviInspektori());
        listaInspektora.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Inspektor novi = (Inspektor) newItem;
            if(novi!=null){
                labelInfo.setText(novi.getIme() + " " + novi.getPrezime());
                labelJedinstvenaSifra.setText("UNIQUE ID: " + novi.getJedinstvenaSifra());
                idTrenutnoPrikazanog = novi.getId();
                trenutnoPrikazani = novi;
                enableButtons();
            }else{
                disableButtons();
            }
        });
    }

    private void disableButtons() {
        profilBtn.setDisable(true);
        modifikujBtn.setDisable(true);
        izvjestajiBtn.setDisable(true);
        obrisiBtn.setDisable(true);
        dodijeliZadatakBtn.setDisable(true);
        pregledajZadatkeBtn.setDisable(true);
    }

    private void enableButtons(){
        profilBtn.setDisable(false);
        modifikujBtn.setDisable(false);
        izvjestajiBtn.setDisable(false);
        obrisiBtn.setDisable(false);
        dodijeliZadatakBtn.setDisable(false);
        pregledajZadatkeBtn.setDisable(false);
    }

    public void openCreateAccount(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/createAccount.fxml"));
        myStage.setTitle("Create an account!");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        refreshInspectorsList();
        listaInspektora.getSelectionModel().select(listaInspektora.getItems().size()-1);
    }

    public void profilBtn(ActionEvent actionEvent) throws IOException, SQLException {
        status.setStatus("Inspector profile - " + inspektorDao.dajImePrezimeInspektora(idTrenutnoPrikazanog) + " [" + inspektorDao.dajJedinstvenuSifruZaID(idTrenutnoPrikazanog) + "] opened.");
        updateStatus();

        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inspectorProfile.fxml"));
        Parent root = loader.load();

        ProfileController profileController = loader.getController();
        profileController.labUniqueID.setText(trenutnoPrikazani.getJedinstvenaSifra());
        profileController.labFirstName.setText(inspektorDao.dajImeZaID(idTrenutnoPrikazanog));
        profileController.labLastName.setText(inspektorDao.dajPrezimeZaID(idTrenutnoPrikazanog));
        profileController.labBirthdate.setText(inspektorDao.dajDatumRodjenjaZaID(idTrenutnoPrikazanog));
        profileController.labJMBG.setText(inspektorDao.dajJMBGZaID(idTrenutnoPrikazanog));
        if(inspektorDao.dajSpolZaID(idTrenutnoPrikazanog)==1) profileController.labGender.setText("M");
        else profileController.labGender.setText("F");
        profileController.labIDNumber.setText(inspektorDao.dajBrojLicneZaID(idTrenutnoPrikazanog));
        profileController.labResidence.setText(inspektorDao.dajMjestoPrebivalistaZaID(idTrenutnoPrikazanog));
        profileController.labPhoneNumber.setText(inspektorDao.dajTelefonZaID(idTrenutnoPrikazanog));
        profileController.labEmail.setText(inspektorDao.dajPersonalniEmailZaID(idTrenutnoPrikazanog));
        profileController.labLoginEmail.setText(inspektorDao.dajPristupniEmailZaID(idTrenutnoPrikazanog));
        profileController.labPassword.setText(inspektorDao.dajPristupnuSifruZaID(idTrenutnoPrikazanog));
        profileController.labInspectionArea.setText(inspektorDao.dajOblastInspekcijeZaID(idTrenutnoPrikazanog));
        profileController.labInspectorType.setText(inspektorDao.dajTipInspektoraZaID(idTrenutnoPrikazanog));
        if(inspektorDao.dajVozackuZaID(idTrenutnoPrikazanog)==1) profileController.labDriversLicense.setText("Owns a license");
        else profileController.labDriversLicense.setText("Doesn't own a license");

        String action = "Administrator[" + prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()+"] opened account - " + inspektorDao.dajTipInspektoraZaID(idTrenutnoPrikazanog) + " " + inspektorDao.dajImePrezimeInspektora(idTrenutnoPrikazanog) + "[" + inspektorDao.dajJedinstvenuSifruZaID(idTrenutnoPrikazanog)+"]";

        logAkcijaDAO.dodaj(new LogAkcije(1, LocalDateTime.now().format(formatter), action, prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()));

        myStage.setTitle("Inspector profile");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();

        updateStatus();
    }

    public void deleteBtn(ActionEvent actionEvent) throws SQLException {
        System.out.println("otvoren delete");
        status.setStatus("Inspector profile - " + inspektorDao.dajImePrezimeInspektora(idTrenutnoPrikazanog) + " [" + inspektorDao.dajJedinstvenuSifruZaID(idTrenutnoPrikazanog) + "] deleted.");

        String action = "Administrator[" + prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()+"] deleted account - " + inspektorDao.dajTipInspektoraZaID(idTrenutnoPrikazanog) + " " + inspektorDao.dajImePrezimeInspektora(idTrenutnoPrikazanog) + "[" + inspektorDao.dajJedinstvenuSifruZaID(idTrenutnoPrikazanog)+"]";
        logAkcijaDAO.dodaj(new LogAkcije(1, LocalDateTime.now().format(formatter), action, prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()));

        inspektorDao.obrisi(trenutnoPrikazani);
        refreshInspectorsList();

        labelInfo.setText("");
        labelJedinstvenaSifra.setText("");

        updateStatus();
    }

    public void modifyBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifyProfile.fxml"));
        Parent root = loader.load();

        ModifyProfileController mod = loader.getController();
        mod.idOtvorenog = idTrenutnoPrikazanog;
        mod.fldIme.setText(inspektorDao.dajImeZaID(idTrenutnoPrikazanog));
        mod.fldPrezime.setText(inspektorDao.dajPrezimeZaID(idTrenutnoPrikazanog));
        mod.fldBrojLicne.setText(inspektorDao.dajBrojLicneZaID(idTrenutnoPrikazanog));
        mod.fldMjestoPrebivalista.setText(inspektorDao.dajMjestoPrebivalistaZaID(idTrenutnoPrikazanog));
        mod.fldKontaktTelefon.setText(inspektorDao.dajTelefonZaID(idTrenutnoPrikazanog));
        mod.fldPersonalniMail.setText(inspektorDao.dajPersonalniEmailZaID(idTrenutnoPrikazanog));
        mod.fldPristupniMail.setText(inspektorDao.dajPristupniEmailZaID(idTrenutnoPrikazanog));
        mod.fldPristupnaSifra.setText(inspektorDao.dajPristupnuSifruZaID(idTrenutnoPrikazanog));
        mod.comboOblastInspekcije.setValue(inspektorDao.dajOblastInspekcijeZaID(idTrenutnoPrikazanog));
        if(inspektorDao.isGlavniInspektor(idTrenutnoPrikazanog)) mod.rbGlavniInspektor.setSelected(true);
        else mod.rbFederalniInspektor.setSelected(true);
        if(inspektorDao.dajVozackuZaID(idTrenutnoPrikazanog)==1) mod.cbVozacka.setSelected(true);
        else mod.cbVozacka.setSelected(false);

        myStage.setTitle("Modify the inspector");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        refreshInspectorsList();

        listaInspektora.getSelectionModel().select(idTrenutnoPrikazanog);
        labelInfo.setText(inspektorDao.dajImeZaID(idTrenutnoPrikazanog) + " " + inspektorDao.dajPrezimeZaID(idTrenutnoPrikazanog));
        labelJedinstvenaSifra.setText("UNIQUE ID: " + inspektorDao.dajJedinstvenuSifruZaID(idTrenutnoPrikazanog));
        updateStatus();
    }

    public void refreshInspectorsList(){
        listaInspektora.setItems(inspektorDao.sviInspektori());
    }

    public void logoutBtn(ActionEvent actionEvent) throws IOException {
        logDAO.logout(prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog());

        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/preview.fxml"));
        myStage.initStyle(StageStyle.UNDECORATED);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
        Stage stage = (Stage) labelJedinstvenaSifra.getScene().getWindow();
        stage.close();
    }

    public void reportsBtn(ActionEvent actionEvent) throws IOException {
        status.setStatus("Reports for inspector profile - " + inspektorDao.dajImePrezimeInspektora(idTrenutnoPrikazanog) + " [" + inspektorDao.dajJedinstvenuSifruZaID(idTrenutnoPrikazanog) + "] opened.");
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/izvjestaji.fxml"));
        Parent root = loader.load();
        IzvjestajiController cont = loader.getController();
        cont.setIdInspektora(idTrenutnoPrikazanog);
        cont.refresujIzvjestaj();

        myStage.setTitle("Reports");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        updateStatus();
    }

    public void exitBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) listaInspektora.getScene().getWindow();
        stage.close();
    }

    public void changeAdminLoginData(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifyAdministrator.fxml"));
        Parent root = loader.load();

        ModifyAdministratorController cont = loader.getController();
        String adminUniqueID = prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog();
        cont.fldEmail.setText(administratorDAO.dajEmailZaJedinstvenuSifru(adminUniqueID));
        cont.fldSifra.setText(administratorDAO.dajSifruZaJedinstvenuSifru(adminUniqueID));
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
        cont.setInspectorID(idTrenutnoPrikazanog);
        myStage.setTitle("Assign a task");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        updateStatus();
    }

    private void updateStatus(){
        labStatusBar.setText(status.getStatus());
    }

    public void showDutiesBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/showDuties.fxml"));
        Parent root = loader.load();
        ShowDutiesController cont = loader.getController();
        cont.inspectorId = idTrenutnoPrikazanog;
        myStage.setTitle("Inspekcijski poslovi - User");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
    }
}
