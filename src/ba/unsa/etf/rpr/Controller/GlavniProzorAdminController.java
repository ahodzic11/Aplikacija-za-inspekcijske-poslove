package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.Model.Inspektor;
import ba.unsa.etf.rpr.Model.Log;
import ba.unsa.etf.rpr.Model.LogAkcije;
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
    private InspektorDAO inspektorDao;
    private AdministratorDAO administratorDAO;
    private PrijavljeniUserDAO prijavljeniUserDAO;
    private LogDAO logDAO;
    private LogAkcijaDAO logAkcijaDAO;

    @FXML
    public void initialize() throws SQLException {
        inspektorDao = InspektorDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
        prijavljeniUserDAO = PrijavljeniUserDAO.getInstance();
        logAkcijaDAO = LogAkcijaDAO.getInstance();
        logDAO = LogDAO.getInstance();

        disableujDugmad();

        listaInspektora.setItems(inspektorDao.sviInspektori());
        listaInspektora.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Inspektor novi = (Inspektor) newItem;
            if(novi!=null){
                labelInfo.setText(novi.getIme() + " " + novi.getPrezime());
                labelJedinstvenaSifra.setText("UNIQUE ID: " + novi.getJedinstvenaSifra());
                idTrenutnoPrikazanog = novi.getId();
                trenutnoPrikazani = novi;
                enableujDugmad();
            }else{
                disableujDugmad();
            }
        });
    }

    private void disableujDugmad() {
        profilBtn.setDisable(true);
        modifikujBtn.setDisable(true);
        izvjestajiBtn.setDisable(true);
        obrisiBtn.setDisable(true);
        dodijeliZadatakBtn.setDisable(true);
        pregledajZadatkeBtn.setDisable(true);
    }

    private void enableujDugmad(){
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
        refresujListu();
        listaInspektora.getSelectionModel().select(listaInspektora.getItems().size()-1);
    }

    public void profilBtn(ActionEvent actionEvent) throws IOException, SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profil.fxml"));
        Parent root = loader.load();
        ProfilController profilController = loader.getController();
        profilController.labUnikatnaSifra.setText(trenutnoPrikazani.getJedinstvenaSifra());
        profilController.labIme.setText(inspektorDao.dajImeZaID(idTrenutnoPrikazanog));
        profilController.labPrezime.setText(inspektorDao.dajPrezimeZaID(idTrenutnoPrikazanog));
        profilController.labDatumRodjenja.setText(inspektorDao.dajDatumRodjenjaZaID(idTrenutnoPrikazanog));
        profilController.labJMBG.setText(inspektorDao.dajJMBGZaID(idTrenutnoPrikazanog));
        if(inspektorDao.dajSpolZaID(idTrenutnoPrikazanog)==1) profilController.labSpol.setText("M");
        else profilController.labSpol.setText("Ž");
        profilController.labBrojL.setText(inspektorDao.dajBrojLicneZaID(idTrenutnoPrikazanog));
        profilController.labMjestoPreb.setText(inspektorDao.dajMjestoPrebivalistaZaID(idTrenutnoPrikazanog));
        profilController.labTelefon.setText(inspektorDao.dajTelefonZaID(idTrenutnoPrikazanog));
        profilController.labMail.setText(inspektorDao.dajPersonalniEmailZaID(idTrenutnoPrikazanog));
        profilController.labPristupniMail.setText(inspektorDao.dajPristupniEmailZaID(idTrenutnoPrikazanog));
        profilController.labPristupnaSifra.setText(inspektorDao.dajPristupnuSifruZaID(idTrenutnoPrikazanog));
        profilController.labOblastInspekcije.setText(inspektorDao.dajOblastInspekcijeZaID(idTrenutnoPrikazanog));
        profilController.labTipInspektora.setText(inspektorDao.dajTipInspektoraZaID(idTrenutnoPrikazanog));
        if(inspektorDao.dajVozackuZaID(idTrenutnoPrikazanog)==1) profilController.labVozacka.setText("Posjeduje");
        else profilController.labVozacka.setText("Ne posjeduje");
        String akcija = "Administrator[" + prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()+"] opened account - " + inspektorDao.dajTipInspektoraZaID(idTrenutnoPrikazanog) + " " + inspektorDao.dajImePrezimeInspektora(idTrenutnoPrikazanog) + "[" + inspektorDao.dajJedinstvenuSifruZaID(idTrenutnoPrikazanog)+"]";
        logAkcijaDAO.dodaj(new LogAkcije(1, LocalDateTime.now().format(formatter), akcija, prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()));
        myStage.setTitle("Inspector profile");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }

    public void obrisiBtn(ActionEvent actionEvent) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String akcija = "Administrator[" + prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()+"] deleted account - " + inspektorDao.dajTipInspektoraZaID(idTrenutnoPrikazanog) + " " + inspektorDao.dajImePrezimeInspektora(idTrenutnoPrikazanog) + "[" + inspektorDao.dajJedinstvenuSifruZaID(idTrenutnoPrikazanog)+"]";
        logAkcijaDAO.dodaj(new LogAkcije(1, LocalDateTime.now().format(formatter), akcija, prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()));
        inspektorDao.obrisi(trenutnoPrikazani);
        listaInspektora.setItems(inspektorDao.sviInspektori());
        labelInfo.setText("");
        labelJedinstvenaSifra.setText("");
    }

    public void modifikujBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifikujProfil.fxml"));
        Parent root = loader.load();
        ModifikujProfilController mod = loader.getController();
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
        refresujListu();
        listaInspektora.getSelectionModel().select(idTrenutnoPrikazanog);
        labelInfo.setText(inspektorDao.dajImeZaID(idTrenutnoPrikazanog) + " " + inspektorDao.dajPrezimeZaID(idTrenutnoPrikazanog));
        labelJedinstvenaSifra.setText("UNIQUE ID: " + inspektorDao.dajJedinstvenuSifruZaID(idTrenutnoPrikazanog));
    }

    public void refresujListu(){
        listaInspektora.setItems(inspektorDao.sviInspektori());
    }

    public void logoutBtn(ActionEvent actionEvent) throws IOException {
        logDAO.logujOdjavu(prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog());
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/preview.fxml"));
        myStage.initStyle(StageStyle.UNDECORATED);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
        Stage stage = (Stage) labelJedinstvenaSifra.getScene().getWindow();
        stage.close();
    }


    public void izvjestajiBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/izvjestaji.fxml"));
        Parent root = loader.load();
        IzvjestajiController cont = loader.getController();
        cont.setIdInspektora(idTrenutnoPrikazanog);
        cont.refresujIzvjestaj();
        myStage.setTitle("Reports");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
    }

    public void exitBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) listaInspektora.getScene().getWindow();
        stage.close();
    }

    public void promijeniPodatkeAdminaBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifikujAdministratora.fxml"));
        Parent root = loader.load();
        ModifikujAdministratoraController cont = loader.getController();
        String jedinstvenaSifraUlogovanogAdmina = prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog();
        cont.fldEmail.setText(administratorDAO.dajEmailZaJedinstvenuSifru(jedinstvenaSifraUlogovanogAdmina));
        cont.fldSifra.setText(administratorDAO.dajSifruZaJedinstvenuSifru(jedinstvenaSifraUlogovanogAdmina));
        cont.fldJedinstvenaSifra.setText(jedinstvenaSifraUlogovanogAdmina);
        myStage.setTitle("Modify the administrator");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
    }

    public void dodajAdminaBtn(ActionEvent actionEvent) {
    }

    public void logoviBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/logovi.fxml"));
        Parent root = loader.load();
        LogoviController cont = loader.getController();
        myStage.setTitle("Logs");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
    }

    public void dodijeliZadatakBtn(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dodijeliZadatak.fxml"));
        Parent root = loader.load();
        DodijeliZadatakController cont = loader.getController();
        cont.setIdInspektora(idTrenutnoPrikazanog);
        myStage.setTitle("Assign a task");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
    }
}
