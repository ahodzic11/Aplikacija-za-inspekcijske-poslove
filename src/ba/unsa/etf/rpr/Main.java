package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAL.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.InspectorDAO;
import ba.unsa.etf.rpr.DAL.UserDAO;
import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Inspector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        UserDAO prijavljeniUserDao = UserDAO.getInstance();
        AdministratorDAO administratorDAO = AdministratorDAO.getInstance();
        InspectorDAO inspektorDAO = InspectorDAO.getInstance();
        if(prijavljeniUserDao.isLoggedIn()){
            ArrayList<Administrator> administratori = administratorDAO.getAllAdministrators();
            for(Administrator a : administratori)
                if(a.getUniqueId().equals(prijavljeniUserDao.getLoggedUserUniqueID())){
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/glavniProzorAdmin.fxml"));
                    primaryStage.setTitle("Administrator");
                    primaryStage.setMinWidth(603);
                    primaryStage.setMinHeight(505);
                    primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                    primaryStage.show();
                }
            ArrayList<Inspector> inspektori = inspektorDAO.allValidInspectors();
            for(Inspector i : inspektori)
                if(i.getUniqueId().equals(prijavljeniUserDao.getLoggedUserUniqueID())){
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/glavniProzorUser.fxml"));
                    primaryStage.setTitle("Inspector");
                    primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                    primaryStage.show();
                }
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/preview.fxml"));
            primaryStage.setTitle("Log in");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
