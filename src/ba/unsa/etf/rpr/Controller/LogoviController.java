package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.done.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.done.InspectorDAO;
import ba.unsa.etf.rpr.DAL.done.ActionLogDAO;
import ba.unsa.etf.rpr.DAL.LogDAO;
import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Inspector;
import ba.unsa.etf.rpr.Model.Log;
import ba.unsa.etf.rpr.Model.ActionLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogoviController {
    private LogDAO logDAO;
    private ActionLogDAO logAkcijaDAO;
    private InspectorDAO inspektorDAO;
    private AdministratorDAO administratorDAO;
    public TextArea areaPrijavljivanja;
    public TextArea areaAkcije;

    @FXML
    public void initialize() throws SQLException {
        logDAO = LogDAO.getInstance();
        inspektorDAO = InspectorDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
        logAkcijaDAO = ActionLogDAO.getInstance();
        refresujLogove();
        refresujLogoveAkcija();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) areaAkcije.getScene().getWindow();
        stage.close();
    }

    public void exportAkcijaBtn(ActionEvent actionEvent) throws IOException {
    }

    public void exportPrijavljivanjaBtn(ActionEvent actionEvent) {
        FileChooser izbornik = new FileChooser();
        izbornik.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tekstualna datoteka", "*.txt"));
        File file = izbornik.showSaveDialog(areaPrijavljivanja.getScene().getWindow());
        if(file!=null){
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(areaPrijavljivanja.getText());
                writer.close();
            } catch (IOException ex) {
                System.out.println("Greška pri exportovanju fajla");
            }
        }
    }

    public void obrisiLogovePrijavljivanjaBtn(ActionEvent actionEvent) {
        logDAO.obrisiSveLogove();
        refresujLogove();
    }

    public void obrisiLogoveAkcijaBtn(ActionEvent actionEvent) {
        logAkcijaDAO.deleteLogs();
        refresujLogoveAkcija();
    }

    private void refresujLogoveAkcija() {
        ArrayList<ActionLog> logoviAkcija = logAkcijaDAO.dajSveLogove();
        String akcije = "";
        for(ActionLog l : logoviAkcija){
            akcije += l.getAction() + " date/time: " + l.getDateTime() + "\n";
        }
        areaAkcije.setText(akcije);
    }

    private void refresujLogove() {
        ArrayList<Log> logovi = logDAO.dajSveLogove();
        ArrayList<String> jedinstveneSifreInspektora = new ArrayList<>();
        ArrayList<String> jedinstveneSifreAdministratora = new ArrayList<>();
        String historijaPrijavljivanja = "";
        ArrayList<Inspector> inspektori = inspektorDAO.allValidInspectors();
        for(Inspector i : inspektori)
            jedinstveneSifreInspektora.add(i.getUniqueId());
        ArrayList<Administrator> administratori = administratorDAO.getAllAdministrators();
        for(Administrator a : administratori)
            jedinstveneSifreAdministratora.add(a.getUniqueId());
        for(Log l: logovi){
            String imePrijavljenog ="";
            if(jedinstveneSifreInspektora.contains(l.getJedinstvenaSifra())){
                int idInspektora = inspektorDAO.getIdForUniqueID(l.getJedinstvenaSifra());
                imePrijavljenog = inspektorDAO.getInspectorTypeForID(idInspektora);
                historijaPrijavljivanja += imePrijavljenog + " | login: " + l.getPrijava() + " logout: " + l.getOdjava() + "\n";
            }else if(jedinstveneSifreAdministratora.contains(l.getJedinstvenaSifra())){
                historijaPrijavljivanja += "Administrator | login: " + l.getPrijava() + " logout: " + l.getOdjava() + "\n";
            }else{
                historijaPrijavljivanja += "Obrisan račun | login: " + l.getPrijava() + " logout: " + l.getOdjava() + "\n";
            }
        }
        areaPrijavljivanja.setText(historijaPrijavljivanja);
    }
}
