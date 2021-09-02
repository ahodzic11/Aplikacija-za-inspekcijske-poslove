package ba.unsa.etf.rpr.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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

    public void exportBtn(ActionEvent actionEvent) {
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
                writer.println(exportData);
                writer.close();
            } catch (IOException ex) {
                System.out.println("Error exporting file");
            }
        }
    }

    public void closeBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) labInspectorType.getScene().getWindow();
        stage.close();
    }
}
