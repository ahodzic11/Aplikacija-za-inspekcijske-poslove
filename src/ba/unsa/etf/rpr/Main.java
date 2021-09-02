package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAL.AdministratorDAO;
import ba.unsa.etf.rpr.DAL.InspektorDAO;
import ba.unsa.etf.rpr.DAL.PrijavljeniUserDAO;
import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Inspektor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        PrijavljeniUserDAO prijavljeniUserDao = PrijavljeniUserDAO.getInstance();
        AdministratorDAO administratorDAO = AdministratorDAO.getInstance();
        InspektorDAO inspektorDAO = InspektorDAO.getInstance();
        if(prijavljeniUserDao.dajUlogovanost()){
            ArrayList<Administrator> administratori = administratorDAO.dajSveAdministratore();
            for(Administrator a : administratori)
                if(a.getJedinstvenaSifra().equals(prijavljeniUserDao.dajJedinstvenuSifruUlogovanog())){
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/glavniProzorAdmin.fxml"));
                    primaryStage.setTitle("Administrator");
                    primaryStage.setMinWidth(603);
                    primaryStage.setMinHeight(505);
                    primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                    primaryStage.show();
                }
            ArrayList<Inspektor> inspektori = inspektorDAO.sviUpisaniInspektori();
            for(Inspektor i : inspektori)
                if(i.getJedinstvenaSifra().equals(prijavljeniUserDao.dajJedinstvenuSifruUlogovanog())){
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/glavniProzorUser.fxml"));
                    primaryStage.setTitle("Inspector");
                    primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                    primaryStage.show();
                }
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/preview.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setTitle("Preview");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
