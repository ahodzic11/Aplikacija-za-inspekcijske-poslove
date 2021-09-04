package ba.unsa.etf.rpr.DAL.done;

import ba.unsa.etf.rpr.Model.Owner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class OwnerDAO {
    private static OwnerDAO instance = null;
    private static InspectorDAO inspectorDAO = InspectorDAO.getInstance();
    private Connection conn;
    private PreparedStatement ownersQuery, newIDQuery, addOwnerQuery, allOwnersQuery, deleteOwnerQuery, ownerNameQuery,
    ownerSurenameQuery;

    private OwnerDAO() throws SQLException {
        String url = "jdbc:sqlite:inspection.db";
        try {
            if (inspectorDAO.getConn() != null)
                conn = inspectorDAO.getConn();
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            ownersQuery = conn.prepareStatement("SELECT * FROM owner");
        } catch (SQLException e) {
            createDatabase();
            ownersQuery = conn.prepareStatement("SELECT * FROM owner");
        }
        newIDQuery = conn.prepareStatement("SELECT Max(id)+1 FROM owner");
        addOwnerQuery = conn.prepareStatement("INSERT INTO owner VALUES(?, ?, ?, ?, ?, ?)");
        allOwnersQuery = conn.prepareStatement("SELECT * FROM owner");
        deleteOwnerQuery = conn.prepareStatement("DELETE FROM owner WHERE id=?");
        ownerNameQuery = conn.prepareStatement("SELECT name FROM owner WHERE id=?");
        ownerSurenameQuery = conn.prepareStatement("SELECT surename FROM owner WHERE id=?");
    }

    public ObservableList<Owner> allOwners(){
        ObservableList<Owner> result = FXCollections.observableArrayList();
        try{
            ResultSet rs = allOwnersQuery.executeQuery();
            while(rs.next()){
                result.add(new Owner(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addOwner(Owner v) throws SQLException {
        try{
            ResultSet rs = newIDQuery.executeQuery();
            if(rs.next())
                v.setId(rs.getInt(1));
            else
                v.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addOwnerQuery.setInt(1, v.getId());
        addOwnerQuery.setString(2, v.getName());
        addOwnerQuery.setString(3, v.getSurename());
        addOwnerQuery.setString(4, v.getJmbg());
        addOwnerQuery.setInt(5, v.getPhoneNumber());
        addOwnerQuery.setString(6, v.getEmail());
        addOwnerQuery.execute();
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

    public static OwnerDAO getInstance() throws SQLException {
        if(instance==null) instance = new OwnerDAO();
        return instance;
    }

    public void deleteOwner(int ownerId) {
        try{
            deleteOwnerQuery.setInt(1, ownerId);
            deleteOwnerQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getNameLastNameForID(int ownerId) {
        String nameSurename = "";
        try{
            ownerNameQuery.setInt(1, ownerId);
            ResultSet rs = ownerNameQuery.executeQuery();
            rs.next();
            nameSurename += rs.getString(1);
            ownerSurenameQuery.setInt(1, ownerId);
            ResultSet rs2 = ownerSurenameQuery.executeQuery();
            rs2.next();
            nameSurename += " " + rs2.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nameSurename;
    }
}
