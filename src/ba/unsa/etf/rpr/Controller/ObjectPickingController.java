package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.IzvjestajDAO;
import ba.unsa.etf.rpr.DAL.ObjekatDAO;
import ba.unsa.etf.rpr.DAL.SvjedokDAO;
import ba.unsa.etf.rpr.DAL.VlasnikDAO;
import ba.unsa.etf.rpr.Model.Objekat;
import ba.unsa.etf.rpr.Model.Vlasnik;
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
    private IzvjestajDAO izvjestajDao;
    public ListView objektiVlasnika;
    public TextField fldNaziv;
    public TextField fldAdresa;
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldJMBG;
    public TextField fldTelefon;
    public TextField fldEmail;
    private VlasnikDAO vlasnikdao;
    private ObjekatDAO objekatDao;
    private SvjedokDAO svjedokDao;
    public ListView vlasnici;
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
        objekatDao.obrisiSaIDem(idTrenutnogObjekta);
        refresujObjekte();
    }

    private void refresujObjekte() {
        objektiVlasnika.setItems(objekatDao.sviObjektiZaVlasnika(idTrenutnogVlasnika));
    }

    public void refreshOwnersList() {
        vlasnici.setItems(vlasnikdao.sviVlasnici());
    }

    public void deleteVlasnikBtn(ActionEvent actionEvent) {
        objekatDao.obrisiObjekteVlasnika(idTrenutnogVlasnika);
        vlasnikdao.obrisiVlasnika(idTrenutnogVlasnika);
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
            cont.fldNazivObjekta.setText(objekatDao.dajNazivObjektaZaID(idTrenutnogObjekta));
            cont.fldAdresaObjekta.setText(objekatDao.dajAdresuObjektaZaID(idTrenutnogObjekta));
            cont.fldVlasnik.setText(vlasnikdao.dajPodatkeVlasnikaZaId(idTrenutnogVlasnika));
            cont.datumInspekcije.setValue(LocalDate.now());
            cont.idObjekta = idTrenutnogObjekta;
            Stage stage = (Stage) fldIme.getScene().getWindow();
            stage.close();
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();

        }
    }

    public void obrisiIzvjestajeBtn(ActionEvent actionEvent) {
        izvjestajDao.obrisiSveIzvjestaje();
    }

    public void obrisiSvjedokeBtn(ActionEvent actionEvent) {
        svjedokDao.obrisiSveSvjedoke();
    }
}
