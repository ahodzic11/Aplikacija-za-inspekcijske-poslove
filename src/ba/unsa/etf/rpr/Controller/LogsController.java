package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.InspectorDAO;
import ba.unsa.etf.rpr.DAL.ActionLogDAO;
import ba.unsa.etf.rpr.DAL.LoginLogDAO;
import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Inspector;
import ba.unsa.etf.rpr.Model.LoginLog;
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

public class LogsController {
    private LoginLogDAO loginLogDAO;
    private ActionLogDAO actionLogDAO;
    private InspectorDAO inspectorDAO;
    private AdministratorDAO administratorDAO;
    public TextArea areaLogins;
    public TextArea areaActions;

    @FXML
    public void initialize() throws SQLException {
        loginLogDAO = LoginLogDAO.getInstance();
        inspectorDAO = InspectorDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
        actionLogDAO = ActionLogDAO.getInstance();
        refreshLoginLogs();
        refreshActionLogs();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) areaActions.getScene().getWindow();
        stage.close();
    }

    public void actionLogsExportBtn(ActionEvent actionEvent) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text document", "*.txt"));
        File file = chooser.showSaveDialog(areaLogins.getScene().getWindow());
        if(file!=null){
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(areaActions.getText());
                writer.close();
            } catch (IOException ex) {
                System.out.println("Error while exporting logs");
            }
        }
    }

    public void loginLogsExportBtn(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text document", "*.txt"));
        File file = chooser.showSaveDialog(areaLogins.getScene().getWindow());
        if(file!=null){
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(areaLogins.getText());
                writer.close();
            } catch (IOException ex) {
                System.out.println("Error while exporting logs");
            }
        }
    }

    public void deleteLoginLogsBtn(ActionEvent actionEvent) {
        loginLogDAO.deleteAllLogs();
        refreshLoginLogs();
    }

    public void deleteActionLogsBtn(ActionEvent actionEvent) {
        actionLogDAO.deleteLogs();
        refreshActionLogs();
    }

    private void refreshActionLogs() {
        ArrayList<ActionLog> actionLogs = actionLogDAO.dajSveLogove();
        String actions = "";
        for(ActionLog l : actionLogs){
            actions += l.getAction() + " date/time: " + l.getDateTime() + "\n";
        }
        areaActions.setText(actions);
    }

    private void refreshLoginLogs() {
        ArrayList<LoginLog> logs = loginLogDAO.getAllLogs();
        ArrayList<String> inspectorUniqueIDs = new ArrayList<>();
        ArrayList<String> adminUniqueIDs = new ArrayList<>();
        String logHistory = "";
        ArrayList<Inspector> inspectors = inspectorDAO.allValidInspectors();
        for(Inspector i : inspectors)
            inspectorUniqueIDs.add(i.getUniqueId());
        ArrayList<Administrator> administrators = administratorDAO.getAllAdministrators();
        for(Administrator a : administrators)
            adminUniqueIDs.add(a.getUniqueId());
        for(LoginLog l: logs){
            String loggedInUserInfo ="";
            if(inspectorUniqueIDs.contains(l.getUniqueId())){
                int idInspektora = inspectorDAO.getIdForUniqueID(l.getUniqueId());
                logHistory += loggedInUserInfo + " | login: " + l.getLogin() + " logout: " + l.getLogout() + "\n";
            }else if(adminUniqueIDs.contains(l.getUniqueId())){
                logHistory += "Administrator | login: " + l.getLogin() + " logout: " + l.getLogout() + "\n";
            }else{
                logHistory += "Deleted account | login: " + l.getLogin() + " logout: " + l.getLogout() + "\n";
            }
        }
        areaLogins.setText(logHistory);
    }
}
