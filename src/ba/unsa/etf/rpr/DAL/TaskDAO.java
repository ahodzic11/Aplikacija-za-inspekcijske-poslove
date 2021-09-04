package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class TaskDAO {
    private static TaskDAO instance = null;
    private static InspectorDAO inspectorDAO = InspectorDAO.getInstance();
    private Connection conn;
    private PreparedStatement taskQuery, newIdQuery, addTaskQuery, deleteTaskQuery, inspectorTasksQuery, inspectorQuery,
            objectQuery, datetimeQuery, notesQuery, assignedInspectorQuery, doneQuery, assignInspectorQuery,
            takenQuery, modifyTaskQuery, untakenTasksQuery, assignTaskQuery;

    private TaskDAO() throws SQLException {
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
            taskQuery = conn.prepareStatement("SELECT * FROM task");
        } catch (SQLException e) {
            createDatabase();
            taskQuery = conn.prepareStatement("SELECT * FROM task");
        }
        newIdQuery = conn.prepareStatement("SELECT Max(id)+1 FROM task");
        addTaskQuery = conn.prepareStatement("INSERT INTO task VALUES(?, ?, ?, ?, ?, ?, ?)");
        deleteTaskQuery = conn.prepareStatement("DELETE FROM task WHERE id=?");
        inspectorTasksQuery = conn.prepareStatement("SELECT * FROM task WHERE inspectorId=?");
        inspectorQuery = conn.prepareStatement("SELECT inspectorId FROM task WHERE id=?");
        objectQuery = conn.prepareStatement("SELECT objectId FROM task WHERE id=?");
        datetimeQuery = conn.prepareStatement("SELECT datetime FROM task WHERE id=?");
        notesQuery = conn.prepareStatement("SELECT notes FROM task WHERE id=?");
        assignedInspectorQuery = conn.prepareStatement("SELECT assignedInspectorId FROM task WHERE id=?");
        doneQuery = conn.prepareStatement("SELECT done FROM task WHERE id=?");
        assignInspectorQuery = conn.prepareStatement("UPDATE task SET assignedInspectorId=? WHERE id=?");
        takenQuery = conn.prepareStatement("SELECT assignedInspectorId FROM task WHERE id=?");
        modifyTaskQuery = conn.prepareStatement("UPDATE task SET objectId=?, inspectorId=?, datetime=?, notes=?, done=?, assignedInspectorId=? WHERE id=?");
        untakenTasksQuery = conn.prepareStatement("SELECT * FROM task WHERE assignedInspectorId=-1");
        assignTaskQuery = conn.prepareStatement("UPDATE task SET assignedInspectorId=? WHERE id=?");
    }

    public void modifyTask(int id, int objectId, int inspectorId, String datetime, String notes, int done, int assignedInspectorId){
        try{
            modifyTaskQuery.setInt(1, objectId);
            modifyTaskQuery.setInt(2, inspectorId);
            modifyTaskQuery.setString(3, datetime);
            modifyTaskQuery.setString(4, notes);
            modifyTaskQuery.setInt(5, done);
            modifyTaskQuery.setInt(6, assignedInspectorId);
            modifyTaskQuery.setInt(7, id);
            modifyTaskQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTaskDoneStatus(int taskId){
        try{
            doneQuery.setInt(1, taskId);
            ResultSet rs = doneQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteTask(int taskId){
        try{
            deleteTaskQuery.setInt(1, taskId);
            deleteTaskQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TaskDAO getInstance() throws SQLException {
        if(instance==null) instance = new TaskDAO();
        return instance;
    }

    public void addTask(Task t) throws SQLException {
        try{
            ResultSet rs = newIdQuery.executeQuery();
            if(rs.next())
                t.setId(rs.getInt(1));
            else
                t.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addTaskQuery.setInt(1, t.getId());
        addTaskQuery.setInt(2, t.getObjectId());
        addTaskQuery.setInt(3, t.getInspectorId());
        addTaskQuery.setString(4, t.getDatetime());
        addTaskQuery.setString(5, t.getNotes());
        addTaskQuery.setInt(6, t.getCompleted());
        addTaskQuery.setInt(7, t.getAssignedInspectorID());
        addTaskQuery.execute();
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

    public ObservableList<Task> getAllTasks() {
        ObservableList<Task> result = FXCollections.observableArrayList();
        try{
            ResultSet rs = taskQuery.executeQuery();
            while(rs.next()){
                result.add(new Task(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ObservableList<Task> getAllTasksForInspector(int inspectorId) {
        ObservableList<Task> rezultat = FXCollections.observableArrayList();
        try{
            inspectorTasksQuery.setInt(1, inspectorId);
            ResultSet rs = inspectorTasksQuery.executeQuery();
            while(rs.next()){
                rezultat.add(new Task(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public int getInspectorForID(int taskId) {
        try{
            inspectorQuery.setInt(1, taskId);
            ResultSet rs = inspectorQuery.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getObjectID(int taskId) {
        try{
            objectQuery.setInt(1, taskId);
            ResultSet rs = objectQuery.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getDatetime(int taskId) {
        try{
            datetimeQuery.setInt(1, taskId);
            ResultSet rs = datetimeQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getNotesForTask(int taskId) {
        try{
            notesQuery.setInt(1, taskId);
            ResultSet rs = notesQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getAssignedInspectorID(int taskId) {
        try{
            assignedInspectorQuery.setInt(1, taskId);
            ResultSet rs = assignedInspectorQuery.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isCompleted(int taskId) {
        try{
            doneQuery.setInt(1, taskId);
            ResultSet rs = doneQuery.executeQuery();
            rs.next();
            if(rs.getInt(1)==1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void takeTask(int taskId, int inspectorId) {
        try{
            assignInspectorQuery.setInt(1, inspectorId);
            assignInspectorQuery.setInt(2, taskId);
            assignInspectorQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isTaken(int taskId) {
        try{
            takenQuery.setInt(1, taskId);
            ResultSet rs = takenQuery.executeQuery();
            rs.next();
            if(rs.getInt(1)==-1) return false;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<Task> getUntakenTasks(){
        ObservableList<Task> result = FXCollections.observableArrayList();
        try{
            ResultSet rs = untakenTasksQuery.executeQuery();
            while(rs.next()){
                result.add(new Task(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void assignTaskToInspectorID(int inspectorId, int taskId) {
        try{
            assignTaskQuery.setInt(1, inspectorId);
            assignTaskQuery.setInt(2, taskId);
            assignTaskQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
