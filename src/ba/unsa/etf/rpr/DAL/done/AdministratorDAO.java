package ba.unsa.etf.rpr.DAL.done;

import ba.unsa.etf.rpr.DAL.InspektorDAO;
import ba.unsa.etf.rpr.Model.Administrator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdministratorDAO {
    private static AdministratorDAO instance = null;
    private static InspektorDAO inspectorDAO = InspektorDAO.getInstance();
    private Connection conn;
    private PreparedStatement allAdminsQuery, newIdQuery, addingQuery, deletingQuery, uniqueIdQuery, emailQuery,
            passwordQuery, modifyQuery, idQuery;

    private AdministratorDAO() throws SQLException {
        String url = "jdbc:sqlite:inspection.db";
        try{
            if(inspectorDAO.getConn()!=null)
                conn = inspectorDAO.getConn();
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            allAdminsQuery = conn.prepareStatement("SELECT * FROM administrator");
        } catch (SQLException e) {
            createDatabase();
            allAdminsQuery = conn.prepareStatement("SELECT * FROM administrator");
        }
        newIdQuery = conn.prepareStatement("SELECT Max(id)+1 FROM administrator");
        addingQuery = conn.prepareStatement("INSERT INTO administrator VALUES(?, ?, ?, ?)");
        deletingQuery = conn.prepareStatement("DELETE FROM administrator WHERE id=?");
        uniqueIdQuery = conn.prepareStatement("SELECT uniqueId FROM administrator WHERE email=?");
        emailQuery = conn.prepareStatement("SELECT email FROM administrator WHERE uniqueId=?");
        passwordQuery = conn.prepareStatement("SELECT password FROM administrator WHERE uniqueId=?");
        modifyQuery = conn.prepareStatement("UPDATE administrator SET email=?, password=?, uniqueId=? WHERE id=?");
        idQuery = conn.prepareStatement("SELECT id FROM administrator WHERE uniqueId=?");
    }

    public int getIdForUniqueID(String jedinstvenaSifra) {
        try{
            idQuery.setString(1, jedinstvenaSifra);
            ResultSet rs = idQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -2;
    }

    public void modifyAdministrator(int id, String email, String password, String uniqueID){
        try{
            modifyQuery.setString(1, email);
            modifyQuery.setString(2, password);
            modifyQuery.setString(3, uniqueID);
            modifyQuery.setInt(4, id);
            modifyQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static AdministratorDAO getInstance() throws SQLException {
        if(instance==null) instance = new AdministratorDAO();
        return instance;
    }

    public void addAdministrator(Administrator a) throws SQLException {
        try{
            ResultSet rs = newIdQuery.executeQuery();
            if(rs.next())
                a.setId(rs.getInt(1));
            else
                a.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addingQuery.setInt(1, a.getId());
        addingQuery.setString(2, a.getEmail());
        addingQuery.setString(3, a.getPassword());
        addingQuery.setString(4, a.getUniqueId());
        addingQuery.execute();
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

    public String getUniqueIDForEmail(String email) {
        try{
            uniqueIdQuery.setString(1, email);
            ResultSet rs = uniqueIdQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getEmailForUniqueID(String jedinstvenaSifra) {
        try{
            emailQuery.setString(1, jedinstvenaSifra);
            ResultSet rs = emailQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPasswordForUniqueID(String jedinstvenaSifra) {
        try{
            passwordQuery.setString(1, jedinstvenaSifra);
            ResultSet rs = passwordQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<Administrator> getAllAdministrators() {
        ArrayList<Administrator> admins = new ArrayList();
        try{
            ResultSet rs = allAdminsQuery.executeQuery();
            while(rs.next()){
                admins.add(new Administrator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }
}
