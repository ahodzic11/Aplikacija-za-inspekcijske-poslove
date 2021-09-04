package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ReportDAO;
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
    public ListView vlasnici;
    public ListView objektiVlasnika;
    public TextField fldNaziv;
    public TextField fldAdresa;
    public ComboBox comboVrstaObjekta;
    public TextField fldJMBG;
    public TextField fldTelefon;
    public TextField fldEmail;
    public TextField fldPrezime;
    public TextField fldIme;
    private ReportDAO izvjestajDao;
    private VlasnikDAO vlasnikdao;
    private ObjekatDAO objekatDao;
    private SvjedokDAO svjedokDao;
    private int idTrenutnogVlasnika;
    private int idTrenutnogObjekta = -1;

    @FXML
    public void initialize() throws SQLException {
        vlasnikdao = VlasnikDAO.getInstance();
        objekatDao = ObjekatDAO.getInstance();
        izvjestajDao = IzvjestajDAO.getInstance();
        svjedokDao = SvjedokDAO.getInstance();
        comboVrstaObjekta.getItems().addAll("Obrazovna institucija", "Zdravstvena institucija", "Ugostiteljski objekat");
        vlasnici.setItems(vlasnikdao.sviVlasnici());
        vlasnici.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Vlasnik novi = (Vlasnik) newItem;
            if(novi != null){
                idTrenutnogVlasnika = novi.getId();
                fldIme.setText(novi.getIme());
                fldPrezime.setText(novi.getPrezime());
                fldJMBG.setText(novi.getJmbg());
                fldTelefon.setText(String.valueOf(novi.getTelefon()));
                fldEmail.setText(novi.getEmail());
                if(objekatDao.sviObjektiZaVlasnika(idTrenutnogVlasnika) != null)
                    objektiVlasnika.setItems(objekatDao.sviObjektiZaVlasnika(idTrenutnogVlasnika));
                objektiVlasnika.getSelectionModel().selectedItemProperty().addListener((obs1, oldObjekat, newObjekat)->{
                    Objekat noviObjekat = (Objekat) newObjekat;
                    if(noviObjekat != null){
                        idTrenutnogObjekta = noviObjekat.getId();
                        fldNaziv.setText(noviObjekat.getNazivObjekta());
                        fldAdresa.setText(noviObjekat.getAdresa());
                        comboVrstaObjekta.getSelectionModel().select(objekatDao.dajVrstuObjektaZaID(idTrenutnogObjekta));
                    }else{
                        fldNaziv.setText("");
                        fldAdresa.setText("");
                        comboVrstaObjekta.getSelectionModel().select("");
                        idTrenutnogObjekta = -1;
                    }
                });
            }else{
                fldIme.setText("");
                fldPrezime.setText("");
                fldJMBG.setText("");
                fldTelefon.setText("");
                fldEmail.setText("");
                idTrenutnogVlasnika = -1;
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
        cont.setOwnerId(idTrenutnogVlasnika);
        myStage.setTitle("Create an object!");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        refreshOwners();
        refreshObjects();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) vlasnici.getScene().getWindow();
        stage.close();
    }

    public void deleteObject(ActionEvent actionEvent) {
        objekatDao.obrisiSaIDem(idTrenutnogObjekta);
        refreshObjects();
    }

    private void refreshObjects() {
        objektiVlasnika.setItems(objekatDao.sviObjektiZaVlasnika(idTrenutnogVlasnika));
    }

    public void refreshOwners() {
        vlasnici.setItems(vlasnikdao.sviVlasnici());
    }

    public void deleteVlasnikBtn(ActionEvent actionEvent) {
        objekatDao.obrisiObjekteVlasnika(idTrenutnogVlasnika);
        vlasnikdao.obrisiVlasnika(idTrenutnogVlasnika);
        refreshOwners();
        refreshObjects();
    }

    public void otvoriObjekatBtn(ActionEvent actionEvent) throws IOException {
        if(idTrenutnogObjekta!=-1){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Stage myStage = new Stage();
            myStage.setTitle("Create a task!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/createTermin.fxml"));
            Parent root = loader.load();
            CreateTerminController cont = loader.getController();
            cont.fldObjectName.setText(objekatDao.dajNazivObjektaZaID(idTrenutnogObjekta));
            cont.fldObjectAddress.setText(objekatDao.dajAdresuObjektaZaID(idTrenutnogObjekta));
            cont.datePickTask.getEditor().setText(LocalDate.now().format(formatter));
            cont.objectId = idTrenutnogObjekta;
            myStage.setResizable(false);
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.showAndWait();
            Stage stage = (Stage) fldIme.getScene().getWindow();
            stage.close();
        }
    }
}
