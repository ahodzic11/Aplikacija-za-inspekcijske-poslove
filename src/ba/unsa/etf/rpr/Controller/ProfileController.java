package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.LogAkcijaDAO;
import ba.unsa.etf.rpr.DAL.PrijavljeniUserDAO;
import ba.unsa.etf.rpr.Model.LogAkcije;
import ba.unsa.etf.rpr.Model.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProfileController {
    public Label labFirstName;
    public Label labLastName;
    public Label labBirthdate;
    public Label labJMBG;
    public Label labGender;
    public Label labIDNumber;
    public Label labEmail;
    public Label labPhoneNumber;
    public Label labResidence;
    public Label labUniqueID;
    public Label labLoginEmail;
    public Label labPassword;
    public Label labDriversLicense;
    public Label labInspectionArea;
    public Label labInspectorType;
    private Status status;
    private LogAkcijaDAO logAkcijaDAO;
    private PrijavljeniUserDAO prijavljeniUserDAO;

    @FXML
    public void initialize() throws SQLException {
        status = Status.getInstance();
        logAkcijaDAO = LogAkcijaDAO.getInstance();
        prijavljeniUserDAO = PrijavljeniUserDAO.getInstance();
    }

    public void exportBtn(ActionEvent actionEvent) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text document", "*.txt"));
        File file = chooser.showSaveDialog(labFirstName.getScene().getWindow());
        if(file!=null){
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                String exportData = labInspectorType.getText() + " " + labFirstName.getText() + " " + labLastName.getText() + "[" + labUniqueID.getText() + "]\n\n";
                exportData += "Inspection area: " + labInspectionArea.getText() + "\n";
                exportData += "Birthdate: " + labBirthdate.getText() + "\n";
                exportData += "JMBG: " + labJMBG.getText() + "\n";
                if(labGender.getText().equals("M")) exportData+= "Gender: Male\n";
                else exportData += "Gender: Female\n";
                exportData += "ID number: " + labIDNumber.getText() + "\n";
                exportData += "E-mail: " + labEmail.getText() + "\n";
                exportData += "Phone number: " + labPhoneNumber.getText() + "\n";
                exportData += "Residency: " + labResidence.getText() + "\n";
                exportData += "Login e-mail: " + labLoginEmail.getText() + "\n";
                if(labDriversLicense.getText().equals("owns")) exportData+= "Has a valid driver's license\n";
                else exportData += "Doesn't have a valid driver's license\n";
                status.setStatus("Inspector profile - " + labFirstName.getText() + " " + labLastName.getText() +  " [" + labUniqueID.getText() + "] exported.");
                logAkcijaDAO.dodaj(new LogAkcije(1, LocalDateTime.now().format(formatter), "Administrator [" + prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()+ "] exported profile - " + labFirstName.getText() + " " + labLastName.getText() +  " [" + labUniqueID.getText() + "] ", prijavljeniUserDAO.dajJedinstvenuSifruUlogovanog()));
                writer.println(exportData);
                writer.close();
              } catch (IOException | SQLException ex) {
                System.out.println("Error exporting file");
            }
        }
    }

    public void closeBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) labInspectorType.getScene().getWindow();
        stage.close();
    }
}
