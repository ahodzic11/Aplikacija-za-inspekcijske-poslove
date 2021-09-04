package ba.unsa.etf.rpr.DAL.done;

import ba.unsa.etf.rpr.Model.LoginLog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginLogDAO {
    private static LoginLogDAO instance = null;
    private static InspectorDAO inspectorDAO = InspectorDAO.getInstance();
    private Connection conn;
    private PreparedStatement allLogsQuery, newIdQuery, addQuery, deletingQuery, logoutQuery, deleteAllLogsQuery;

    private LoginLogDAO() throws SQLException {
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
            allLogsQuery = conn.prepareStatement("SELECT * FROM loginLog");
        } catch (SQLException e) {
            createDatabase();
            allLogsQuery = conn.prepareStatement("SELECT * FROM loginLog");
        }
        newIdQuery = conn.prepareStatement("SELECT Max(id)+1 FROM loginLog");
        addQuery = conn.prepareStatement("INSERT INTO loginLog VALUES(?, ?, ?, ?)");
        deletingQuery = conn.prepareStatement("DELETE FROM loginLog WHERE id=?");
        logoutQuery = conn.prepareStatement("UPDATE loginLog SET logoutDateTime=? WHERE uniqueId=? AND logoutDateTime=? LIKE ''");
        deleteAllLogsQuery = conn.prepareStatement("DELETE FROM loginLog");
    }

    public void addLog(LoginLog l) throws SQLException {
        try{
            ResultSet rs = newIdQuery.executeQuery();
            if(rs.next())
                l.setId(rs.getInt(1));
            else
                l.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addQuery.setInt(1, l.getId());
        addQuery.setString(2, l.getLogin());
        addQuery.setString(3, l.getLogout());
        addQuery.setString(4, l.getUniqueId());
        addQuery.execute();
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

    public static LoginLogDAO getInstance() throws SQLException {
        if(instance==null) instance = new LoginLogDAO();
        return instance;
    }

    public void logout(String uniqueId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try{
            logoutQuery.setString(1, LocalDateTime.now().format(formatter));
            logoutQuery.setString(2, uniqueId);
            logoutQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LoginLog> getAllLogs() {
        ArrayList<LoginLog> result = new ArrayList();
        try{
            ResultSet rs = allLogsQuery.executeQuery();
            while(rs.next()){
                result.add(new LoginLog(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteAllLogs() {
        try{
            deleteAllLogsQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
