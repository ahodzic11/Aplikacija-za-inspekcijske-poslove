package ba.unsa.etf.rpr.Controller;

import ba.unsa.etf.rpr.DAL.done.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.done.InspectorDAO;
import ba.unsa.etf.rpr.DAL.LogDAO;
import ba.unsa.etf.rpr.DAL.PrijavljeniUserDAO;
import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Inspector;
import ba.unsa.etf.rpr.Model.Log;
import ba.unsa.etf.rpr.Model.PrijavljeniUser;
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

public class PreviewController {
    private InspectorDAO inspektorDao;
    private PrijavljeniUserDAO prijavljeniUserDao;
    private AdministratorDAO administratorDAO;
    private LogDAO logDAO;
    public Button loginBtn;
    public TextField emailFld;
    public PasswordField pswFld;
    public CheckBox rememberCB;

    @FXML
    public void initialize() throws SQLException {
        inspektorDao = InspectorDAO.getInstance();
        prijavljeniUserDao = PrijavljeniUserDAO.getInstance();
        logDAO = LogDAO.getInstance();
        administratorDAO = AdministratorDAO.getInstance();
    }

    public void zatvoriPreview(ActionEvent actionEvent) {
        Stage stage = (Stage) emailFld.getScene().getWindow();
        stage.close();
    }

    public void login(ActionEvent actionEvent) throws Exception {
        int ostaniUlogovan = 0;
        if(rememberCB.isSelected()) ostaniUlogovan = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        ArrayList<Administrator> listaAdministratora = administratorDAO.getAllAdministrators();
        for(Administrator a : listaAdministratora)
            if(emailFld.getText().equals(a.getEmail()) && pswFld.getText().equals(a.getPassword())){
                String jedinstvenaSifraAdmina = administratorDAO.getUniqueIDForEmail(emailFld.getText());
                prijavljeniUserDao.dodaj(new PrijavljeniUser(-1, LocalDateTime.now().format(formatter), ostaniUlogovan, jedinstvenaSifraAdmina));
                logDAO.dodaj(new Log(1, LocalDateTime.now().format(formatter), "", jedinstvenaSifraAdmina));
                Stage myStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/glavniProzorAdmin.fxml"));
                myStage.setTitle("Inspekcijski poslovi - Administrator");
                myStage.setMinWidth(603);
                myStage.setMinHeight(505);
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.show();
                Stage stage = (Stage) emailFld.getScene().getWindow();
                stage.close();
            }
        ArrayList<Inspector> upisaniInspektori = inspektorDao.allValidInspectors();
        for(Inspector i : upisaniInspektori)
            if(emailFld.getText().equals(i.getLoginEmail()) && pswFld.getText().equals(i.getPassword())){
                int idInspektora = inspektorDao.getInspectorIDForEmail(emailFld.getText());
                prijavljeniUserDao.dodaj(new PrijavljeniUser(idInspektora, LocalDateTime.now().format(formatter), ostaniUlogovan, inspektorDao.getUniqueIDForID(idInspektora)));
                logDAO.dodaj(new Log(1, LocalDateTime.now().format(formatter), "", inspektorDao.getUniqueIDForID(idInspektora)));
                Stage myStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/glavniProzorUser.fxml"));
                myStage.setTitle("Inspekcijski poslovi - User");
                myStage.setMinWidth(60);
                myStage.setMaxHeight(60);
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.show();
                Stage stage = (Stage) emailFld.getScene().getWindow();
                stage.close();
            }

    }
}
