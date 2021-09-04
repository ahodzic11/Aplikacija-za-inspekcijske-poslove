package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.DAL.done.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ModifikujIzvjestajController {
    public TextField fldNazivObjekta;
    public TextField fldAdresaObjekta;
    public TextArea opisTerena;
    public CheckBox cbPrekrsaj;
    public TextField fldPrekrsaj;
    public TextField fldKazna;
    public DatePicker datumInspekcije;
    public TextField fldZahtjevi;
    public CheckBox cbPrijaviRadiliste;
    public TextField fldVlasnik;
    public TextField fldBrojZaposlenih;
    public TextField fldPotvrdaORadu;
    public CheckBox cbNedostatak;
    public TextField fldBrojRadnika;
    public CheckBox cbKrivicnoDjelo;
    public CheckBox cbUzorak;
    public CheckBox cbFitocertifikat;
    public CheckBox cbZabranaRada;
    public RadioButton rbBrojDana;
    public ToggleGroup groupZabrana;
    public RadioButton rbIspunjenjeUslova;
    public TextField fldBrojDanaZabrane;
    public TextField fldUslovZabrane;
    public CheckBox cbEvidencijaRadnika;
    public TextField fldNedostatak;
    public TextField s1Ime;
    public TextField s1prezime;
    public TextField s1JMBG;
    public TextArea s1Izjava;
    public TextField s2ime;
    public TextField s2prezime;
    public TextField s2JMBG;
    public TextArea s2Izjava;
    public TextField fldJedinstvenaSifra;
    private IzvjestajDAO izvjestajDao;
    private UserDAO prijavljeniUserDao;
    public int idOtvorenogIzvjestaja;
    private SvjedokDAO svjedokDao;
    private ObjekatDAO objekatDao;

    @FXML
    public void initialize() throws SQLException {
        izvjestajDao = IzvjestajDAO.getInstance();
        prijavljeniUserDao = UserDAO.getInstance();
        objekatDao = ObjekatDAO.getInstance();
        svjedokDao = SvjedokDAO.getInstance();
        cbPrekrsaj.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldPrekrsaj.setDisable(false);
                fldKazna.setDisable(false);
                fldZahtjevi.setDisable(false);
            }else{
                fldPrekrsaj.setDisable(true); fldKazna.setDisable(true); fldZahtjevi.setDisable(true);
                fldPrekrsaj.setText(""); fldKazna.setText(""); fldZahtjevi.setText("");
            }
        });

        cbEvidencijaRadnika.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldBrojRadnika.setDisable(false);
            }else{
                fldBrojRadnika.setDisable(true);
            }
        });

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

        cbPrijaviRadiliste.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldVlasnik.setDisable(false); fldBrojZaposlenih.setDisable(false); fldPotvrdaORadu.setDisable(false);
            }else{
                fldVlasnik.setDisable(true); fldBrojZaposlenih.setDisable(true); fldPotvrdaORadu.setDisable(true);
            }
        });

        cbNedostatak.selectedProperty().addListener((obs, oldItem, newItem)->{
            if(newItem){
                fldNedostatak.setDisable(false);
            }else{
                fldNedostatak.setDisable(true);
            }
        });
    }

    public void modifikujBtn(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        int krivicnoDjelo = 0, evidencijaRadnika = 0, fitocertifikat = 0, uzorak = 0, prijavljenoRadiliste = 0;
        if(cbKrivicnoDjelo.isSelected()) krivicnoDjelo = 1;
        if(cbEvidencijaRadnika.isSelected()) evidencijaRadnika = 1;
        if(cbFitocertifikat.isSelected()) fitocertifikat = 1;
        if(cbUzorak.isSelected()) uzorak = 1;
        if(cbPrijaviRadiliste.isSelected()) prijavljenoRadiliste = 1;
        int idPrvogSvjedoka = svjedokDao.dajIdPrvogSvjedoka(idOtvorenogIzvjestaja);
        int idDrugogSvjedoka = svjedokDao.dajIdDrugogSvjedoka(idOtvorenogIzvjestaja);
        String stringDatumInspekcije = datumInspekcije.getEditor().getText();
        svjedokDao.modifikuj(idPrvogSvjedoka, idOtvorenogIzvjestaja, s1Ime.getText(), s1prezime.getText(), s1JMBG.getText(), s1Izjava.getText());
        svjedokDao.modifikuj(idDrugogSvjedoka, idOtvorenogIzvjestaja, s2ime.getText(), s2prezime.getText(), s2JMBG.getText(), s2Izjava.getText());
        int idObjekta = izvjestajDao.dajIDObjektaZaIzvjestajID(idOtvorenogIzvjestaja);
        izvjestajDao.modifikuj(idOtvorenogIzvjestaja, prijavljeniUserDao.getLoggedUserID(), stringDatumInspekcije,
                opisTerena.getText(), fldPrekrsaj.getText(), Integer.parseInt(fldKazna.getText()), fldZahtjevi.getText(), evidencijaRadnika, krivicnoDjelo, fitocertifikat, uzorak,
                Integer.parseInt(fldBrojDanaZabrane.getText()), fldUslovZabrane.getText(), prijavljenoRadiliste,
                Integer.parseInt(fldBrojZaposlenih.getText()), Integer.parseInt(fldPotvrdaORadu.getText()), fldNedostatak.getText(), idPrvogSvjedoka, idDrugogSvjedoka, idObjekta,
                fldNazivObjekta.getText(), fldAdresaObjekta.getText(), fldJedinstvenaSifra.getText());
        objekatDao.modifikuj(idObjekta, objekatDao.dajVlasnikaZaID(idObjekta), fldNazivObjekta.getText(), fldAdresaObjekta.getText(), objekatDao.dajVrstuObjektaZaID(idObjekta));
        Stage stage = (Stage) fldAdresaObjekta.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) fldAdresaObjekta.getScene().getWindow();
        stage.close();
    }
}
