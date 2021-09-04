package ba.unsa.etf.rpr.DAL.done;

import ba.unsa.etf.rpr.Model.Inspector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InspectorDAO {
    private PreparedStatement allInspectorsQuery, newIdQuery, addingQuery, everyInspectorQuery, deletingQuery,
            modifyingQuery, IDquery, firstNameQuery, lastNameQuery, birthdateQuery, jmbgQuery, genderQuery, idNumberQuery,
            residenceQuery, phoneNumberQuery, emailQuery, loginQuery, passwordQuery, driversLicenseQuery, uniqueIdQuery,
            inspectorIdQuery, inspectionAreaQuery, inspectorTypeQuery, idForUniqueIDQuery;
    private static InspectorDAO instance = null;
    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    private InspectorDAO(){
        String url = "jdbc:sqlite:inspection.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            allInspectorsQuery = conn.prepareStatement("SELECT * FROM inspector");
        }catch (SQLException e){
            createDatabase();
            try {
                allInspectorsQuery = conn.prepareStatement("SELECT * FROM inspector");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        try {
            newIdQuery = conn.prepareStatement("SELECT Max(id)+1 FROM inspector");
            addingQuery = conn.prepareStatement("INSERT INTO inspector VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            everyInspectorQuery = conn.prepareStatement("SELECT * FROM inspector ORDER BY name");
            deletingQuery = conn.prepareStatement("DELETE FROM inspector WHERE id=?");
            modifyingQuery = conn.prepareStatement("UPDATE inspector SET name=?, lastName=?, idNumber=?, residence=?, phoneNumber=?, email=?, loginEmail=?, password=?, license=?, inspectorType=?, inspectionArea=? WHERE id=?");
            IDquery = conn.prepareStatement("SELECT id FROM inspector WHERE loginEmail=?");
            firstNameQuery = conn.prepareStatement("SELECT name FROM inspector WHERE id=?");
            lastNameQuery = conn.prepareStatement("SELECT lastName FROM inspector WHERE id=?");
            birthdateQuery = conn.prepareStatement("SELECT birthdate FROM inspector WHERE id=?");
            jmbgQuery = conn.prepareStatement("SELECT jmbg FROM inspector WHERE id=?");
            genderQuery = conn.prepareStatement("SELECT gender FROM inspector WHERE id=?");
            idNumberQuery = conn.prepareStatement("SELECT idNumber FROM inspektor WHERE id=?");
            residenceQuery = conn.prepareStatement("SELECT residence FROM inspector WHERE id=?");
            phoneNumberQuery = conn.prepareStatement("SELECT phoneNumber FROM inspector WHERE id=?");
            emailQuery = conn.prepareStatement("SELECT email FROM inspector WHERE id=?");
            loginQuery = conn.prepareStatement("SELECT loginEmail FROM inspector WHERE id=?");
            passwordQuery = conn.prepareStatement("SELECT password FROM inspector WHERE id=?");
            driversLicenseQuery = conn.prepareStatement("SELECT license FROM inspector WHERE id=?");
            uniqueIdQuery = conn.prepareStatement("SELECT uniqueId FROM inspector WHERE id=?");
            inspectorIdQuery = conn.prepareStatement("SELECT id FROM inspector WHERE loginEmail=?");
            inspectionAreaQuery = conn.prepareStatement("SELECT inspectionArea FROM inspector WHERE id=?");
            inspectorTypeQuery = conn.prepareStatement("SELECT inspectorType FROM inspector WHERE id=?");
            idForUniqueIDQuery = conn.prepareStatement("SELECT id FROM inspector WHERE uniqueId=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIdForUniqueID(String uniqueId){
        try{
            idForUniqueIDQuery.setString(1, uniqueId);
            ResultSet rs = idForUniqueIDQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getNameSurenameForID(int inspectorId){
        return getFirstNameForID(inspectorId) + " " + getSurenameForID(inspectorId);
    }

    public String getUniqueIDForID(int inspectorId){
        try{
            uniqueIdQuery.setInt(1, inspectorId);
            ResultSet rs = uniqueIdQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getDriversLicenseForID(int inspectorId){
        try{
            driversLicenseQuery.setInt(1, inspectorId);
            ResultSet rs = driversLicenseQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getPasswordForID(int inspectorId){
        try{
            passwordQuery.setInt(1, inspectorId);
            ResultSet rs = passwordQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getLoginEmailForID(int inspectorId){
        try{
            loginQuery.setInt(1, inspectorId);
            ResultSet rs = loginQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getEmailForID(int inspectorId){
        try{
            emailQuery.setInt(1, inspectorId);
            ResultSet rs = emailQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPhoneNumberForID(int inspectorId){
        try{
            phoneNumberQuery.setInt(1, inspectorId);
            ResultSet rs = phoneNumberQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getResidenceForID(int inspectorId){
        try{
            residenceQuery.setInt(1, inspectorId);
            ResultSet rs = residenceQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getIDNumberForID(int inspectorId){
        try{
            idNumberQuery.setInt(1, inspectorId);
            ResultSet rs = idNumberQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getGenderForID(int inspectorId){
        try{
            genderQuery.setInt(1, inspectorId);
            ResultSet rs = genderQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getJMBGForID(int inspectorId){
        try{
            jmbgQuery.setInt(1, inspectorId);
            ResultSet rs = jmbgQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getBirthdateForID(int inspectorId){
        try{
            birthdateQuery.setInt(1, inspectorId);
            ResultSet rs = birthdateQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getSurenameForID(int inspectorId){
        try{
            lastNameQuery.setInt(1, inspectorId);
            ResultSet rs = lastNameQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getFirstNameForID(int inspectorId){
        try{
            firstNameQuery.setInt(1, inspectorId);
            ResultSet rs = firstNameQuery.executeQuery();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getIDForLoginEmail(String loginEmail){
        try{
            IDquery.setString(1, loginEmail);
            ResultSet rs = IDquery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void createDatabase() {
        Scanner entry = null;
        try {
            entry = new Scanner(new FileInputStream("inspection.sql"));
            String sqlQuery = "";
            while (entry.hasNext()) {
                sqlQuery += entry.nextLine();
                if ( sqlQuery.length() > 1 && sqlQuery.charAt( sqlQuery.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            entry.close();
        } catch (FileNotFoundException e) {
            System.out.println("No SQL database found... I'm continuing with an empty database");
        }
    }

    public void modifyInspector(int id, String firstName, String Surename, String IDNumber, String residence, String phoneNumber, String email,
                                String loginEmail, String password, int driversLicense, String inspectorType, String inspectionArea){
        try{
            modifyingQuery.setString(1, firstName);
            modifyingQuery.setString(2, Surename);
            modifyingQuery.setString(3, IDNumber);
            modifyingQuery.setString(4, residence);
            modifyingQuery.setString(5, phoneNumber);
            modifyingQuery.setString(6, email);
            modifyingQuery.setString(7, loginEmail);
            modifyingQuery.setString(8, password);
            modifyingQuery.setInt(9, driversLicense);
            modifyingQuery.setString(10, inspectorType);
            modifyingQuery.setString(11, inspectionArea);
            modifyingQuery.setInt(12, id);
            modifyingQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteInspector(Inspector i){
        try{
            deletingQuery.setInt(1, i.getId());
            deletingQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addInspector(Inspector i) throws SQLException {
        try{
            ResultSet rs = newIdQuery.executeQuery();
            if(rs.next())
                i.setId(rs.getInt(1));
            else
                i.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addingQuery.setInt(1, i.getId());
        addingQuery.setString(2, i.getFirstName());
        addingQuery.setString(3, i.getLastName());
        addingQuery.setString(4, i.getBirthdate());
        addingQuery.setString(5, i.getJmbg());
        addingQuery.setInt(6, i.getGender());
        addingQuery.setString(7, i.getIdNumber());
        addingQuery.setString(8, i.getResidence());
        addingQuery.setString(9, i.getPhoneNumber());
        addingQuery.setString(10, i.getEmail());
        addingQuery.setString(11, i.getLoginEmail());
        addingQuery.setString(12, i.getPassword());
        addingQuery.setInt(13, i.getDriversLicense());
        addingQuery.setString(14, i.getUniqueId());
        addingQuery.setString(15, i.getInspectorType());
        addingQuery.setString(16, i.getInspectionArea());
        addingQuery.execute();
    }

    public ArrayList<Inspector> allValidInspectors(){
        ArrayList<Inspector> result = new ArrayList();
        try{
            ResultSet rs = everyInspectorQuery.executeQuery();
            while(rs.next()){
                result.add(new Inspector(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getInt(13),
                        rs.getString(14), rs.getString(15), rs.getString(16)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ObservableList<Inspector> allInspectors(){
        ObservableList<Inspector> result = FXCollections.observableArrayList();
        try{
            ResultSet rs = everyInspectorQuery.executeQuery();
            while(rs.next()){
                result.add(new Inspector(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getInt(13),
                        rs.getString(14), rs.getString(15), rs.getString(16)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static InspectorDAO getInstance(){
        if(instance==null) instance = new InspectorDAO();
        return instance;
    }

    public int getInspectorIDForEmail(String email) {
        try{
            inspectorIdQuery.setString(1, email);
            ResultSet rs = inspectorIdQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getInspectorTypeForID(int inspectorId) {
        try{
            inspectorTypeQuery.setInt(1, inspectorId);
            ResultSet rs = inspectorTypeQuery.executeQuery();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getInspectionAreaForID(int inspectorId) {
        try{
            inspectionAreaQuery.setInt(1, inspectorId);
            ResultSet rs = inspectionAreaQuery.executeQuery();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isMajorInspector(int inspectorId) {
        try{
            inspectorTypeQuery.setInt(1, inspectorId);
            ResultSet rs = inspectorTypeQuery.executeQuery();
            if(rs.getString(1).equals("Glavni federalni inspektor")) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
