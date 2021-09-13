package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.ActionLog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ActionLogDAO {
    private static ActionLogDAO instance = null;
    private static InspectorDAO inspectorDAO = InspectorDAO.getInstance();
    private Connection conn;
    private PreparedStatement allActionsQuery, newIdQuery, addingQuery, deletingQuery;

    private ActionLogDAO() throws SQLException {
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
            allActionsQuery = conn.prepareStatement("SELECT * FROM actionLog");
        } catch (SQLException e) {
            createDatabase();
            allActionsQuery = conn.prepareStatement("SELECT * FROM actionLog");
        }
        newIdQuery = conn.prepareStatement("SELECT Max(id)+1 FROM actionLog");
        addingQuery = conn.prepareStatement("INSERT INTO actionLog VALUES(?, ?, ?, ?)");
        deletingQuery = conn.prepareStatement("DELETE FROM actionLog");
    }

    public void deleteLogs(){
        try{
            deletingQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLog(ActionLog l) throws SQLException {
        try{
            ResultSet rs = newIdQuery.executeQuery();
            if(rs.next())
                l.setId(rs.getInt(1));
            else
                l.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addingQuery.setInt(1, l.getId());
        addingQuery.setString(2, l.getDateTime());
        addingQuery.setString(3, l.getAction());
        addingQuery.setString(4, l.getUniqueId());
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

    public static ActionLogDAO getInstance() throws SQLException {
        if(instance==null) instance = new ActionLogDAO();
        return instance;
    }

    public ArrayList<ActionLog> getAllLogs() {
        ArrayList<ActionLog> result = new ArrayList();
        try{
            ResultSet rs = allActionsQuery.executeQuery();
            while(rs.next()){
                result.add(new ActionLog(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void resetDatabase() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM actionLog");
    }
}
