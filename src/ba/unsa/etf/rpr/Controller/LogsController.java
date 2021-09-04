package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.*;
import ba.unsa.etf.rpr.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LogsController {
    private LoginLogDAO loginLogDAO;
    private ActionLogDAO actionLogDAO;
    private InspectorDAO inspectorDAO;
    private AdministratorDAO administratorDAO;
    private UserDAO userDAO;
    private Status status;
    public TextArea areaLogins;
    public TextArea areaActions;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @FXML
    public void initialize() throws SQLException {
        loginLogDAO = LoginLogDAO.getInstance();
        inspectorDAO = InspectorDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
        actionLogDAO = ActionLogDAO.getInstance();
        userDAO = UserDAO.getInstance();
        status = Status.getInstance();

        refreshLoginLogs();
        refreshActionLogs();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) areaActions.getScene().getWindow();
        stage.close();
    }

    public void actionLogsExportBtn(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text document", "*.txt"));
        File file = chooser.showSaveDialog(areaLogins.getScene().getWindow());
        if(file!=null){
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(areaActions.getText());
                status.setStatus("Exported action logs. (" + LocalDateTime.now().format(formatter) + ")");
                actionLogDAO.addLog(new ActionLog(1, LocalDateTime.now().format(formatter), "Administrator [" + userDAO.getLoggedUserUniqueID()+ "] exported action logs", userDAO.getLoggedUserUniqueID()));
                writer.close();
            } catch (IOException | SQLException ex) {
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
                status.setStatus("Exported login logs. (" + LocalDateTime.now().format(formatter) + ")");
                actionLogDAO.addLog(new ActionLog(1, LocalDateTime.now().format(formatter), "Administrator [" + userDAO.getLoggedUserUniqueID()+ "] exported login logs", userDAO.getLoggedUserUniqueID()));
                writer.close();
            } catch (IOException | SQLException ex) {
                System.out.println("Error while exporting logs");
            }
        }
    }

    public void deleteLoginLogsBtn(ActionEvent actionEvent) throws SQLException {
        loginLogDAO.deleteAllLogs();
        status.setStatus("Deleted login logs. (" + LocalDateTime.now().format(formatter) + ")");
        actionLogDAO.addLog(new ActionLog(1, LocalDateTime.now().format(formatter), "Administrator [" + userDAO.getLoggedUserUniqueID()+ "] deleted login logs", userDAO.getLoggedUserUniqueID()));
        refreshLoginLogs();
    }

    public void deleteActionLogsBtn(ActionEvent actionEvent) throws SQLException {
        actionLogDAO.deleteLogs();
        status.setStatus("Deleted action logs. (" + LocalDateTime.now().format(formatter) + ")");
        actionLogDAO.addLog(new ActionLog(1, LocalDateTime.now().format(formatter), "Administrator [" + userDAO.getLoggedUserUniqueID()+ "] deleted action logs", userDAO.getLoggedUserUniqueID()));
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
