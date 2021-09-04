package ba.unsa.etf.rpr.DAL.done;

import ba.unsa.etf.rpr.Model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class UserDAO {
    private static UserDAO instance = null;
    private InspectorDAO inspectorDAO = InspectorDAO.getInstance();
    private Connection conn;
    private PreparedStatement allLoggedUsersQuery, addingQuery, deletingQuery, loggedInQuery, loggedUserIDQuery,
            uniqueIDQuery;

    public UserDAO() throws SQLException {
        String url = "jdbc:sqlite:inspection.db";
        try{
            if(inspectorDAO.getConn() != null)
                conn = inspectorDAO.getConn();
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            allLoggedUsersQuery = conn.prepareStatement("SELECT * FROM loginData");
        } catch (SQLException e) {
            createDatabase();
            allLoggedUsersQuery = conn.prepareStatement("SELECT * FROM loginData");
        }
        addingQuery = conn.prepareStatement("INSERT INTO loginData VALUES(?, ?, ?, ?)");
        deletingQuery = conn.prepareStatement("DELETE FROM loginData");
        loggedUserIDQuery = conn.prepareStatement("SELECT stayLogged FROM loginData");
        loggedUserIDQuery = conn.prepareStatement("SELECT userID FROM loginData");
        uniqueIDQuery = conn.prepareStatement("SELECT uniqueId FROM loginData");
    }

    public void deleteLoggedUser(){
        try{
            deletingQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean isLoggedIn(){
        try{
            ResultSet rs = loggedUserIDQuery.executeQuery();
            if(!rs.next()) return false;
            if(rs.getInt(1) == 1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(User pu) throws SQLException {
        deletingQuery.execute();
        addingQuery.setInt(1, pu.getId());
        addingQuery.setString(2, pu.getLoginDate());
        addingQuery.setInt(3, pu.getLoggedIn());
        addingQuery.setString(4, pu.getUniqueID());
        addingQuery.execute();
    }

    public static UserDAO getInstance() throws SQLException {
        if(instance==null) instance = new UserDAO();
        return instance;
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

    public int getLoggedUserID() {
        try{
            ResultSet rs = loggedUserIDQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getLoggedUserUniqueID() {
        try{
            ResultSet rs = uniqueIDQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
