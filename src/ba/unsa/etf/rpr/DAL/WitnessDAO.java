package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Witness;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WitnessDAO {
    private static WitnessDAO instance = null;
    private static InspectorDAO inspectorDAO = InspectorDAO.getInstance();
    private Connection conn;
    private PreparedStatement allWitnessesQuery, newIdQuery, addingQuery, allQuery, deleteAllQuery,
            witnessNameQuery, witnessSurenameQuery, witnessJMBGQuery, witnessesForReportQuery, statementQuery,
            modifyQuery;

    private WitnessDAO() throws SQLException {
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
            allWitnessesQuery = conn.prepareStatement("SELECT * FROM witness");
        } catch (SQLException e) {
            createDatabase();
            allWitnessesQuery = conn.prepareStatement("SELECT * FROM witness");
        }
        newIdQuery = conn.prepareStatement("SELECT Max(id)+1 FROM witness");
        addingQuery = conn.prepareStatement("INSERT INTO witness VALUES(?, ?, ?, ?, ?, ?)");
        allQuery = conn.prepareStatement("SELECT * FROM witness");
        deleteAllQuery = conn.prepareStatement("DELETE FROM witness");
        witnessNameQuery = conn.prepareStatement("SELECT name FROM witness WHERE id=?");
        witnessSurenameQuery = conn.prepareStatement("SELECT surename FROM witness WHERE id=?");
        witnessJMBGQuery = conn.prepareStatement("SELECT jmbg FROM witness WHERE id=?");
        witnessesForReportQuery = conn.prepareStatement("SELECT * FROM witness WHERE reportId=?");
        statementQuery = conn.prepareStatement("SELECT statement FROM witness WHERE id=?");
        modifyQuery = conn.prepareStatement("UPDATE witness SET reportId=?, name=?, surename=?, jmbg=?, statement=? WHERE id=?");
    }

    public void modifyWitness(int id, int reportId, String name, String surename, String jmbg, String statement){
        try{
            modifyQuery.setInt(1, reportId);
            modifyQuery.setString(2, name);
            modifyQuery.setString(3, surename);
            modifyQuery.setString(4, jmbg);
            modifyQuery.setString(5, statement);
            modifyQuery.setInt(6, id);
            modifyQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getFirstWitnessID(int reportId){
        ArrayList<Witness> witnesses = new ArrayList();
        try{
            witnessesForReportQuery.setInt(1, reportId);
            ResultSet rs = witnessesForReportQuery.executeQuery();
            while(rs.next()){
                witnesses.add(new Witness(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(witnesses.get(0).getId()<witnesses.get(1).getId()) return witnesses.get(0).getId();
        return witnesses.get(1).getId();
    }

    public int getSecondWitnessID(int reportId){
        ArrayList<Witness> witnesses = new ArrayList();
        try{
            witnessesForReportQuery.setInt(1, reportId);
            ResultSet rs = witnessesForReportQuery.executeQuery();
            while(rs.next()){
                witnesses.add(new Witness(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(witnesses.get(0).getId()<witnesses.get(1).getId()) return witnesses.get(1).getId();
        return witnesses.get(0).getId();
    }

    public ObservableList<Witness> allWitnesses(){
        ObservableList<Witness> result = FXCollections.observableArrayList();
        try{
            ResultSet rs = allQuery.executeQuery();
            while(rs.next()){
                result.add(new Witness(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getWitnessStatementForWitness(int witnessId) {
        try{
            statementQuery.setInt(1, witnessId);
            ResultSet rs = statementQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getWitnessName(int witnessId) {
        try{
            witnessNameQuery.setInt(1, witnessId);
            ResultSet rs = witnessNameQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getWitnessSurename(int witnessId) {
        try{
            witnessSurenameQuery.setInt(1, witnessId);
            ResultSet rs = witnessSurenameQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getWitnessJMBG(int witnessId) {
        try{
            witnessJMBGQuery.setInt(1, witnessId);
            ResultSet rs = witnessJMBGQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getNewWitnessID(){
        ResultSet rs = null;
        try{
            rs = newIdQuery.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public void addWitness(Witness s) throws SQLException {
        try{
            ResultSet rs = newIdQuery.executeQuery();
            if(rs.next())
                s.setId(rs.getInt(1));
            else
                s.setId(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addingQuery.setInt(1, s.getId());
        addingQuery.setInt(2, s.getReportId());
        addingQuery.setString(3, s.getName());
        addingQuery.setString(4, s.getSurename());
        addingQuery.setString(5, s.getJmbg());
        addingQuery.setString(6, s.getStatement());
        addingQuery.execute();
    }

    public static WitnessDAO getInstance() throws SQLException {
        if(instance==null) instance = new WitnessDAO();
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

    public void deleteAllWitnesses() {
        try{
            deleteAllQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
