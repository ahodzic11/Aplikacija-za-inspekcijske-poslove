package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.InspectorDAO;
import ba.unsa.etf.rpr.DAL.LoginLogDAO;
import ba.unsa.etf.rpr.DAL.UserDAO;
import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Inspector;
import ba.unsa.etf.rpr.Model.LoginLog;
import ba.unsa.etf.rpr.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {
    private InspectorDAO inspectorDAO;
    private UserDAO userDAO;
    private AdministratorDAO administratorDAO;
    private LoginLogDAO loginLogDAO;
    public Button loginBtn;
    public TextField fldEmail;
    public PasswordField fldPassword;
    public CheckBox cbRememberMe;

    @FXML
    public void initialize() throws SQLException {
        inspectorDAO = InspectorDAO.getInstance();
        userDAO = UserDAO.getInstance();
        loginLogDAO = LoginLogDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
    }

    public void login(ActionEvent actionEvent) throws Exception {
        int stayLogged = 0;
        if(cbRememberMe.isSelected()) stayLogged = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        ArrayList<Administrator> administrators = administratorDAO.getAllAdministrators();
        for(Administrator a : administrators)
            if(fldEmail.getText().equals(a.getEmail()) && fldPassword.getText().equals(a.getPassword())){
                String adminUniqueID = administratorDAO.getUniqueIDForEmail(fldEmail.getText());
                userDAO.addUser(new User(-1, LocalDateTime.now().format(formatter), stayLogged, adminUniqueID));
                loginLogDAO.addLog(new LoginLog(1, LocalDateTime.now().format(formatter), "", adminUniqueID));
                Stage myStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/glavniProzorAdmin.fxml"));
                myStage.setTitle("Administrator");
                myStage.setMinWidth(603);
                myStage.setMinHeight(505);
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.show();
                Stage stage = (Stage) fldEmail.getScene().getWindow();
                stage.close();
            }
        ArrayList<Inspector> inspectors = inspectorDAO.allValidInspectors();
        for(Inspector i : inspectors)
            if(fldEmail.getText().equals(i.getLoginEmail()) && fldPassword.getText().equals(i.getPassword())){
                int inspectorId = inspectorDAO.getInspectorIDForEmail(fldEmail.getText());
                userDAO.addUser(new User(inspectorId, LocalDateTime.now().format(formatter), stayLogged, inspectorDAO.getUniqueIDForID(inspectorId)));
                loginLogDAO.addLog(new LoginLog(1, LocalDateTime.now().format(formatter), "", inspectorDAO.getUniqueIDForID(inspectorId)));
                Stage myStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/glavniProzorUser.fxml"));
                myStage.setTitle("Inspector");
                myStage.setMinWidth(60);
                myStage.setMaxHeight(60);
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.show();
                Stage stage = (Stage) fldEmail.getScene().getWindow();
                stage.close();
            }

    }
}
