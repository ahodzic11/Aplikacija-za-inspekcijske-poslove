package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.ReportDAO;
import ba.unsa.etf.rpr.DAL.ObjectDAO;
import ba.unsa.etf.rpr.DAL.WitnessDAO;
import ba.unsa.etf.rpr.DAL.OwnerDAO;
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

public class ObjectPickingController {
    public ComboBox comboVrstaObjekta;
    private ReportDAO izvjestajDao;
    public ListView objektiVlasnika;
    public TextField fldNaziv;
    public TextField fldAdresa;
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldJMBG;
    public TextField fldTelefon;
    public TextField fldEmail;
    private OwnerDAO vlasnikdao;
    private ObjectDAO objekatDao;
    private WitnessDAO svjedokDao;
    public ListView vlasnici;
    private int idTrenutnogVlasnika;
    private int idTrenutnogObjekta = -1;

    @FXML
    public void initialize() throws SQLException {
        vlasnikdao = OwnerDAO.getInstance();
        objekatDao = ObjectDAO.getInstance();
        izvjestajDao = ReportDAO.getInstance();
        svjedokDao = WitnessDAO.getInstance();
        comboVrstaObjekta.getItems().addAll("Obrazovna institucija", "Zdravstvena institucija", "Ugostiteljski objekat");
        vlasnici.setItems(vlasnikdao.allOwners());
        vlasnici.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem)->{
            Owner novi = (Owner) newItem;
            if(novi != null){
                idTrenutnogVlasnika = novi.getId();
                fldIme.setText(novi.getName());
                fldPrezime.setText(novi.getSurename());
                fldJMBG.setText(novi.getJmbg());
                fldTelefon.setText(String.valueOf(novi.getPhoneNumber()));
                fldEmail.setText(novi.getEmail());
                if(objekatDao.getObjectsFromOwner(idTrenutnogVlasnika) != null)
                objektiVlasnika.setItems(objekatDao.getObjectsFromOwner(idTrenutnogVlasnika));
                objektiVlasnika.getSelectionModel().selectedItemProperty().addListener((obs1, oldObjekat, newObjekat)->{
                    Object noviObjekat = (Object) newObjekat;
                    if(noviObjekat != null){
                        idTrenutnogObjekta = noviObjekat.getId();
                        fldNaziv.setText(noviObjekat.getName());
                        fldAdresa.setText(noviObjekat.getAddress());
                        comboVrstaObjekta.getSelectionModel().select(objekatDao.getObjectTypeForID(idTrenutnogObjekta));
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

    public void dodajVlasnika(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/createOwner.fxml"));
        myStage.setTitle("Create an owner!");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        refreshOwnersList();
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
        refreshOwnersList();
        refresujObjekte();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) vlasnici.getScene().getWindow();
        stage.close();
    }

    public void deleteObjekat(ActionEvent actionEvent) {
        objekatDao.deleteObjectWithID(idTrenutnogObjekta);
        refresujObjekte();
    }

    private void refresujObjekte() {
        objektiVlasnika.setItems(objekatDao.getObjectsFromOwner(idTrenutnogVlasnika));
    }

    public void refreshOwnersList() {
        vlasnici.setItems(vlasnikdao.allOwners());
    }

    public void deleteVlasnikBtn(ActionEvent actionEvent) {
        objekatDao.deleteOwnersObjects(idTrenutnogVlasnika);
        vlasnikdao.deleteOwner(idTrenutnogVlasnika);
        refreshOwnersList();
        refresujObjekte();
    }

    public void otvoriObjekatBtn(ActionEvent actionEvent) throws IOException {
        if(idTrenutnogObjekta!=-1){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            Stage myStage = new Stage();
            myStage.setTitle("Kreiraj izvještaj!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kreirajIzvjestaj.fxml"));
            Parent root = loader.load();
            KreirajIzvjestajController cont = loader.getController();
            cont.fldNazivObjekta.setText(objekatDao.getNameForID(idTrenutnogObjekta));
            cont.fldAdresaObjekta.setText(objekatDao.getAddressForObjectID(idTrenutnogObjekta));
            cont.fldVlasnik.setText(vlasnikdao.getNameLastNameForID(idTrenutnogVlasnika));
            cont.datumInspekcije.setValue(LocalDate.now());
            cont.idObjekta = idTrenutnogObjekta;
            Stage stage = (Stage) fldIme.getScene().getWindow();
            stage.close();
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();

        }
    }

    public void obrisiIzvjestajeBtn(ActionEvent actionEvent) {
        izvjestajDao.deleteAllReports();
    }

    public void obrisiSvjedokeBtn(ActionEvent actionEvent) {
        svjedokDao.deleteAllWitnesses();
    }
}
