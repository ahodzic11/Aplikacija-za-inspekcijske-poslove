package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.Model.Report;
import ba.unsa.etf.rpr.Model.Witness;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class KreirajIzvjestajController {
    public TextField fldNazivObjekta, fldAdresaObjekta, s1Ime, s1prezime, s1JMBG, s2ime, s2prezime, s2JMBG, fldPrekrsaj, fldKazna,
    fldZahtjevi, fldVlasnik, fldBrojZaposlenih, fldPotvrdaORadu, fldBrojRadnika, fldBrojDanaZabrane, fldUslovZabrane, fldNedostatak;
    public TextArea opisTerena, s1Izjava, s2Izjava;
    public CheckBox cbPrekrsaj, cbPrijaviRadiliste, cbKrivicnoDjelo, cbUzorak, cbFitocertifikat, cbZabranaRada, cbEvidencijaRadnika, cbNedostatak;
    public RadioButton rbBrojDana, rbIspunjenjeUslova;
    public DatePicker datumInspekcije;
    public ToggleGroup groupZabrana;
    public TextField fldJedinstvenaSifra;
    private InspectorDAO inspektorDao;
    private ObjectDAO objekatDao;
    private OwnerDAO vlasnikDao;
    private WitnessDAO svjedokDao;
    private ReportDAO izvjestajDao;
    private UserDAO prijavljeniUserDao;
    public int idObjekta;

    @FXML
    public void initialize() throws SQLException, IOException {
        inspektorDao = InspectorDAO.getInstance();
        objekatDao = ObjectDAO.getInstance();
        vlasnikDao = OwnerDAO.getInstance();
        izvjestajDao = ReportDAO.getInstance();
        svjedokDao = WitnessDAO.getInstance();
        prijavljeniUserDao = UserDAO.getInstance();
        fldPrekrsaj.setDisable(true); fldKazna.setDisable(true); fldZahtjevi.setDisable(true);
        cbPrekrsaj.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldPrekrsaj.setDisable(false);
                fldKazna.setDisable(false);
                fldZahtjevi.setDisable(false);
            }else{
                fldPrekrsaj.setDisable(true); fldKazna.setDisable(true); fldZahtjevi.setDisable(true);
            }
        });

        fldBrojRadnika.setDisable(true);
        cbEvidencijaRadnika.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldBrojRadnika.setDisable(false);
            }else{
                fldBrojRadnika.setDisable(true);
            }
        });

        rbBrojDana.setDisable(true); rbIspunjenjeUslova.setDisable(true); fldBrojDanaZabrane.setDisable(true); fldUslovZabrane.setDisable(true);
        cbZabranaRada.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                rbBrojDana.setDisable(false); rbIspunjenjeUslova.setDisable(false);
            }else{
                rbBrojDana.setDisable(true); rbIspunjenjeUslova.setDisable(true); fldBrojDanaZabrane.setDisable(true); fldUslovZabrane.setDisable(true);
            }
        });

        rbBrojDana.selectedProperty().addListener((obs, oldItem, newItem) -> {
            if(newItem){
                fldUslovZabrane.setDisable(true);
                fldBrojDanaZabrane.setDisable(false);
            }else{
                fldUslovZabrane.setDisable(false);
                fldBrojDanaZabrane.setDisable(true);
            }
        });

        fldVlasnik.setDisable(true); fldBrojZaposlenih.setDisable(true); fldPotvrdaORadu.setDisable(true);
        cbPrijaviRadiliste.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldVlasnik.setDisable(false); fldBrojZaposlenih.setDisable(false); fldPotvrdaORadu.setDisable(false);
            }else{
                fldVlasnik.setDisable(true); fldBrojZaposlenih.setDisable(true); fldPotvrdaORadu.setDisable(true);
            }
        });

        fldNedostatak.setDisable(true);
        cbNedostatak.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldNedostatak.setDisable(false);
            }else{
                fldNedostatak.setDisable(true);
            }
        });
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldAdresaObjekta.getScene().getWindow();
        stage.close();
    }

    public void okBtn(ActionEvent actionEvent) throws SQLException {
        int krivicnoDjelo = 0, evidencijaRadnika = 0, fitocertifikat = 0, uzorak = 0, prijavljenoRadiliste = 0;
        if(cbKrivicnoDjelo.isSelected()) krivicnoDjelo = 1;
        if(cbEvidencijaRadnika.isSelected()) evidencijaRadnika = 1;
        if(cbFitocertifikat.isSelected()) fitocertifikat = 1;
        if(cbUzorak.isSelected()) uzorak = 1;
        if(cbPrijaviRadiliste.isSelected()) prijavljenoRadiliste = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int idPrvogSvjedoka = svjedokDao.getNewWitnessID();
        int idNovogIzvjestaja = izvjestajDao.getNewReportID();
        svjedokDao.addWitness(new Witness(idPrvogSvjedoka, idNovogIzvjestaja, s1Ime.getText(), s1prezime.getText(), s1JMBG.getText(), s1Izjava.getText()));
        int idDrugogSvjedoka = svjedokDao.getNewWitnessID();
        svjedokDao.addWitness(new Witness(idDrugogSvjedoka, idNovogIzvjestaja, s2ime.getText(), s2prezime.getText(), s2JMBG.getText(), s2Izjava.getText()));
        Report noviIzvjestaj = new Report(1, prijavljeniUserDao.getLoggedUserID(), datumInspekcije.getValue().format(formatter),
                opisTerena.getText(), fldPrekrsaj.getText(), Integer.parseInt(fldKazna.getText()), fldZahtjevi.getText(), evidencijaRadnika, krivicnoDjelo, fitocertifikat, uzorak,
                Integer.parseInt(fldBrojDanaZabrane.getText()), fldUslovZabrane.getText(), prijavljenoRadiliste,
                Integer.parseInt(fldBrojZaposlenih.getText()), Integer.parseInt(fldPotvrdaORadu.getText()), fldNedostatak.getText(), idPrvogSvjedoka, idDrugogSvjedoka, idObjekta,
                fldNazivObjekta.getText(), fldAdresaObjekta.getText(), fldJedinstvenaSifra.getText());
        izvjestajDao.addReport(noviIzvjestaj);
        Stage stage = (Stage) fldAdresaObjekta.getScene().getWindow();
        stage.close();
    }
}
