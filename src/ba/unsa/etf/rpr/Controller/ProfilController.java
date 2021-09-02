package ba.unsa.etf.rpr.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class ProfilController {
    public Label labIme;
    public Label labPrezime;
    public Label labDatumRodjenja;
    public Label labJMBG;
    public Label labSpol;
    public Label labBrojL;
    public Label labMail;
    public Label labTelefon;
    public Label labMjestoPreb;
    public Label labUnikatnaSifra;
    public Label labPristupniMail;
    public Label labPristupnaSifra;
    public Label labVozacka;
    public Label labOblastInspekcije;
    public Label labTipInspektora;

    public void exportBtn(ActionEvent actionEvent) {
        FileChooser izbornik = new FileChooser();
        izbornik.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tekstualna datoteka", "*.txt"));
        File file = izbornik.showSaveDialog(labIme.getScene().getWindow());
        if(file!=null){
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                String podaci = labTipInspektora.getText() + " " + labIme.getText() + " " + labPrezime.getText() + "[" + labUnikatnaSifra.getText() + "]\n\n";
                podaci += "Oblast inspekcije: " + labOblastInspekcije.getText() + "\n";
                podaci += "Datum rođenja: " + labDatumRodjenja.getText() + "\n";
                podaci += "JMBG: " + labJMBG.getText() + "\n";
                if(labSpol.getText().equals("M")) podaci+= "Spol: Muški\n";
                else podaci += "Spol: Ženski\n";
                podaci += "Broj lične: " + labBrojL.getText() + "\n";
                podaci += "E-mail: " + labMail.getText() + "\n";
                podaci += "Broj telefona: " + labTelefon.getText() + "\n";
                podaci += "Mjesto prebivališta: " + labMjestoPreb.getText() + "\n";
                podaci += "Pristupni e-mail: " + labPristupniMail.getText() + "\n";
                if(labVozacka.getText().equals("Posjeduje")) podaci+= "Vozačka dozvola: Posjeduje\n";
                else podaci += "Vozačka dozvola: Ne posjeduje\n";
                writer.println(podaci);
                writer.close();
            } catch (IOException ex) {
                System.out.println("Greška pri exportovanju fajla");
            }
        }
    }

    public void closeBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) labTipInspektora.getScene().getWindow();
        stage.close();
    }
}
