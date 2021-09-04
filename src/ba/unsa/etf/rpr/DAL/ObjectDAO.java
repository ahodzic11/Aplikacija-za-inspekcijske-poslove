package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Object;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ObjectDAO {
    private static ObjectDAO instance = null;
    private static InspectorDAO inspectorDAO = InspectorDAO.getInstance();
    private Connection conn;
    private PreparedStatement allObjectsQuery, newIdQuery, addingQuery, searchQuery, allObjectsFromOwnerQuery,
            deleteObjectQuery, objectNameQuery, objectAddressQuery, objectQuery, ownerQuery,
            deleteOwnersObjectsQuery, objectTypeQuery, modifyQuery;

    private ObjectDAO() throws SQLException {
        String url = "jdbc:sqlite:inspection.db";
        try {
            if(inspectorDAO.getConn()!=null)
                conn = inspectorDAO.getConn();
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            allObjectsQuery = conn.prepareStatement("SELECT * FROM object");
        } catch (SQLException e) {
            createDatabase();
            allObjectsQuery = conn.prepareStatement("SELECT * FROM object");
        }
        newIdQuery = conn.prepareStatement("SELECT Max(id)+1 FROM object");
        addingQuery = conn.prepareStatement("INSERT INTO object VALUES(?, ?, ?, ?, ?)");
        searchQuery = conn.prepareStatement("SELECT * FROM object");
        allObjectsFromOwnerQuery = conn.prepareStatement("SELECT * FROM object WHERE ownerId=?");
        deleteObjectQuery = conn.prepareStatement("DELETE FROM object WHERE id=?");
        objectNameQuery = conn.prepareStatement("SELECT name FROM object WHERE id=?");
        objectAddressQuery = conn.prepareStatement("SELECT address FROM object WHERE id=?");
        objectQuery = conn.prepareStatement("SELECT * FROM object WHERE id=?");
        ownerQuery = conn.prepareStatement("SELECT ownerId FROM object WHERE id=?");
        deleteOwnersObjectsQuery = conn.prepareStatement("DELETE FROM object WHERE ownerID=?");
        objectTypeQuery = conn.prepareStatement("SELECT type FROM object WHERE id=?");
        modifyQuery = conn.prepareStatement("UPDATE object SET ownerId=?, name=?, address=?, type=? WHERE id=?");
    }

    public void modifyObject(int id, int ownerId, String name, String address, String type){
        try{
            modifyQuery.setInt(1, ownerId);
            modifyQuery.setString(2, name);
            modifyQuery.setString(3, address);
            modifyQuery.setString(4, type);
            modifyQuery.setInt(5, id);
            modifyQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Object> getAllObjects(){
        ObservableList<Object> result = FXCollections.observableArrayList();
        try{
            ResultSet rs = searchQuery.executeQuery();
            while(rs.next()){
                result.add(new Object(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ObjectDAO getInstance() throws SQLException {
        if(instance==null) instance = new ObjectDAO();
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

    public void addObject(Object o) throws SQLException {
        try{
            ResultSet rs = newIdQuery.executeQuery();
            if(rs.next())
                o.setId(rs.getInt(1));
            else
                o.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addingQuery.setInt(1, o.getId());
        addingQuery.setInt(2, o.getOwnerId());
        addingQuery.setString(3, o.getName());
        addingQuery.setString(4, o.getAddress());
        addingQuery.setString(5, o.getType());
        addingQuery.execute();
    }

    public ObservableList<Object> getObjectsFromOwner(int ownerId) {
        ObservableList<Object> result = FXCollections.observableArrayList();
        try{
            allObjectsFromOwnerQuery.setInt(1, ownerId);
            ResultSet rs = allObjectsFromOwnerQuery.executeQuery();
            while(rs.next()){
                result.add(new Object(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteObjectWithID(int objectId) {
        try{
            deleteObjectQuery.setInt(1, objectId);
            deleteObjectQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAddressForObjectID(int objectId) {
        try{
            objectAddressQuery.setInt(1, objectId);
            ResultSet rs = objectAddressQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getNameForID(int objectId){
        try{
            objectNameQuery.setInt(1, objectId);
            ResultSet rs = objectNameQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getObjectTypeForID(int objectId){
        try{
            objectTypeQuery.setInt(1, objectId);
            ResultSet rs = objectTypeQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getOwnerForID(int objectId) {
        try{
            ownerQuery.setInt(1, objectId);
            ResultSet rs = ownerQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteOwnersObjects(int ownerId) {
        try{
            deleteOwnersObjectsQuery.setInt(1, ownerId);
            deleteOwnersObjectsQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
