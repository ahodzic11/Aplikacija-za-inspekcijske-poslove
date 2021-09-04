package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ReportDAO {
    private static ReportDAO instance = null;
    private static InspectorDAO inspectorDAO = InspectorDAO.getInstance();
    private Connection conn;
    private PreparedStatement reportsQuery, newIdQuery, addingQuery, deleteAllReportsQuery, inspectorReportsQuery,
            deleteReportQuery, reportObjectIdQuery, descriptionQuery, inspectionDateQuery, violationQuery, fineQuery,
            additionalRequirementsQuery, recordedWorkersQuery, criminalOffenseQuery, phytocertificateQuery,
            sampleTakenQuery, daysClosedQuery, openingConditionQuery, reportedWorksiteQuery, employeesQuery, openingCertificateQuery,
            defectQuery, modifyReportQuery, uniqueIDQuery;

    private ReportDAO(){
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
            reportsQuery = conn.prepareStatement("SELECT * FROM report");
        } catch (SQLException e) {
            createDatabase();
            try {
                reportsQuery = conn.prepareStatement("SELECT * FROM report");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        try {
            newIdQuery = conn.prepareStatement("SELECT Max(id)+1 FROM report");
            addingQuery = conn.prepareStatement("INSERT INTO report VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            deleteAllReportsQuery = conn.prepareStatement("DELETE FROM report");
            inspectorReportsQuery = conn.prepareStatement("SELECT * FROM report WHERE inspectorId=?");
            deleteReportQuery = conn.prepareStatement("DELETE FROM report WHERE id=?");
            reportObjectIdQuery = conn.prepareStatement("SELECT objectId FROM report WHERE id=?");
            descriptionQuery = conn.prepareStatement("SELECT description FROM report WHERE id=?");
            inspectionDateQuery = conn.prepareStatement("SELECT inspectionDate FROM report WHERE id=?");
            violationQuery = conn.prepareStatement("SELECT violation FROM report WHERE id=?");
            fineQuery = conn.prepareStatement("SELECT fine FROM report WHERE id=?");
            additionalRequirementsQuery = conn.prepareStatement("SELECT additionalRequirements FROM report WHERE id=?");
            recordedWorkersQuery = conn.prepareStatement("SELECT recordedWorkersNumber FROM report WHERE id=?");
            criminalOffenseQuery = conn.prepareStatement("SELECT criminalOffense FROM report WHERE id=?");
            phytocertificateQuery = conn.prepareStatement("SELECT phytocertificate FROM report WHERE id=?");
            sampleTakenQuery = conn.prepareStatement("SELECT sampleTaken FROM report WHERE id=?");
            daysClosedQuery = conn.prepareStatement("SELECT daysClosed FROM report WHERE id=?");
            openingConditionQuery = conn.prepareStatement("SELECT openingCondition FROM report WHERE id=?");
            reportedWorksiteQuery = conn.prepareStatement("SELECT reportedWorksite FROM report WHERE id=?");
            employeesQuery = conn.prepareStatement("SELECT employeeNumber FROM report WHERE id=?");
            openingCertificateQuery = conn.prepareStatement("SELECT openingCertificateNumber FROM report WHERE id=?");
            defectQuery = conn.prepareStatement("SELECT defect FROM report WHERE id=?");
            modifyReportQuery = conn.prepareStatement("UPDATE report SET inspectorId=?, objectId=?, inspectionDate=?, description=?, violation=?, fine=?, additionalRequirements=?, recordedWorkersNumber=?, criminalOffense=?, phytocertificate=?, sampleTaken=?, daysClosed=?, openingCondition=?, reportedWorksite=?, employeeNumber=?, openingCertificateNumber=?, defect=?, firstWitnessId=?, secondWitnessId=?, objectName=?, objectAddress=?, uniqueId=? WHERE id=?");
            uniqueIDQuery = conn.prepareStatement("SELECT uniqueId FROM report WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyReport(int id, int inspectorId, String inspectionDate, String description, String violation, int fine,
                             String additionalRequirements, int recordedWorkersNumber, int criminalOffense, int phytocertificate,
                             int sampleTaken, int daysClosed, String openingCondition, int reportedWorksite, int employeesNumber,
                             int openingCertificateNumber, String defect, int firstWitnessId, int secondWitnessId, 
                             int objectId, String objectName, String objectAddress, String uniqueId){
        try{
            modifyReportQuery.setInt(1, inspectorId);
            modifyReportQuery.setInt(2, objectId);
            modifyReportQuery.setString(3, inspectionDate);
            modifyReportQuery.setString(4, description);
            modifyReportQuery.setString(5, violation);
            modifyReportQuery.setInt(6, fine);
            modifyReportQuery.setString(7, additionalRequirements);
            modifyReportQuery.setInt(8, recordedWorkersNumber);
            modifyReportQuery.setInt(9, criminalOffense);
            modifyReportQuery.setInt(10, phytocertificate);
            modifyReportQuery.setInt(11, sampleTaken);
            modifyReportQuery.setInt(12, daysClosed);
            modifyReportQuery.setString(13, openingCondition);
            modifyReportQuery.setInt(14, reportedWorksite);
            modifyReportQuery.setInt(15, employeesNumber);
            modifyReportQuery.setInt(16, openingCertificateNumber);
            modifyReportQuery.setString(17, defect);
            modifyReportQuery.setInt(18, firstWitnessId);
            modifyReportQuery.setInt(19, secondWitnessId);
            modifyReportQuery.setString(20, objectName);
            modifyReportQuery.setString(21, objectAddress);
            modifyReportQuery.setString(22, uniqueId);
            modifyReportQuery.setInt(23, id);
            modifyReportQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllReports(){
        try{
            deleteAllReportsQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNewReportID(){
        try{
            ResultSet rs = newIdQuery.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addReport(Report i) throws SQLException {
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
        addingQuery.setInt(2, i.getInspectorId());
        addingQuery.setString(3, i.getInspectionDate());
        addingQuery.setString(4, i.getDescription());
        addingQuery.setString(5, i.getViolation());
        addingQuery.setInt(6, i.getFine());
        addingQuery.setString(7, i.getAdditionalRequirements());
        addingQuery.setInt(8, i.getRecordedWorkers());
        addingQuery.setInt(9, i.getCriminalOffense());
        addingQuery.setInt(10, i.getPhytocertificate());
        addingQuery.setInt(11, i.getSampleTaken());
        addingQuery.setInt(12, i.getDaysClosed());
        addingQuery.setString(13, i.getOpeningCondition());
        addingQuery.setInt(14, i.getReportedWorksite());
        addingQuery.setInt(15, i.getEmployeesNumber());
        addingQuery.setInt(16, i.getOpeningCertificationNumber());
        addingQuery.setString(17, i.getDefect());
        addingQuery.setInt(18, i.getFirstWitnessId());
        addingQuery.setInt(19, i.getSecondWitnessId());
        addingQuery.setInt(20, i.getObjectId());
        addingQuery.setString(21, i.getObjectName());
        addingQuery.setString(22, i.getObjectAddress());
        addingQuery.setString(23, i.getJedinstvenaSifra());
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

    public static ReportDAO getInstance() throws SQLException {
        if(instance==null) instance = new ReportDAO();
        return instance;
    }

    public ObservableList<Report> getReportsForInspectorID(int inspectorId) throws SQLException {
        ObservableList<Report> result = FXCollections.observableArrayList();
        try{
            inspectorReportsQuery.setInt(1, inspectorId);
            ResultSet rs = inspectorReportsQuery.executeQuery();
            while(rs.next()){
                Report noviIzvjestaj = new Report(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8),
                        rs.getInt( 9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getString(13),
                        rs.getInt(14), rs.getInt(15), rs.getInt(16), rs.getString(17), rs.getInt(18), rs.getInt(18), rs.getInt(20), rs.getString(21), rs.getString(22), rs.getString(23));
                result.add(noviIzvjestaj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteReportWithID(int reportId) {
        try{
            deleteReportQuery.setInt(1, reportId);
            deleteReportQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getObjectIDForReport(int reportId) {
        try{
            reportObjectIdQuery.setInt(1, reportId);
            ResultSet rs = reportObjectIdQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getViolationForReportID(int reportId) {
        try{
            violationQuery.setInt(1, reportId);
            ResultSet rs = violationQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getDescriptionForReportID(int reportId) {
        try{
            descriptionQuery.setInt(1, reportId);
            ResultSet rs = descriptionQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getInspectionDateForReportID(int reportId) {
        try{
            inspectionDateQuery.setInt(1, reportId);
            ResultSet rs = inspectionDateQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean violationBooked(int reportId) {
        if(getViolationForReportID(reportId).isBlank()) return false;
        return true;
    }

    public String getFineForReportID(int reportId) {
        try{
            fineQuery.setInt(1, reportId);
            ResultSet rs = fineQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getAdditionalRequirementsForReportID(int reportId) {
        try{
            additionalRequirementsQuery.setInt(1, reportId);
            ResultSet rs = additionalRequirementsQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getRecordedWorkersForReportID(int reportId) {
        try{
            recordedWorkersQuery.setInt(1, reportId);
            ResultSet rs = recordedWorkersQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getCriminalOffenseForReportID(int reportId) {
        try{
            criminalOffenseQuery.setInt(1, reportId);
            ResultSet rs = criminalOffenseQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getPhytocertificateForReportID(int reportId) {
        try{
            phytocertificateQuery.setInt(1, reportId);
            ResultSet rs = phytocertificateQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isUzetUzorakZaID(int idIzvjestaja) {
        try{
            phytocertificateQuery.setInt(1, idIzvjestaja);
            ResultSet rs = phytocertificateQuery.executeQuery();
            rs.next();
            if(rs.getInt(1)==1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getDaysClosedForReportID(int reportId) {
        try{
            daysClosedQuery.setInt(1, reportId);
            ResultSet rs = daysClosedQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getOpeningConditionsForReportID(int reportId) {
        try{
            openingConditionQuery.setInt(1, reportId);
            ResultSet rs = openingConditionQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isReportedWorksite(int reportId) {
        try{
            reportedWorksiteQuery.setInt(1, reportId);
            ResultSet rs = reportedWorksiteQuery.executeQuery();
            rs.next();
            if(rs.getInt(1)==1) return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getEmployeeNumberForReportID(int reportId) {
        try{
            employeesQuery.setInt(1, reportId);
            ResultSet rs = employeesQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getOpeningCertificateNumberForReportID(int reportId) {
        try{
            openingCertificateQuery.setInt(1, reportId);
            ResultSet rs = openingCertificateQuery.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getDefectForReportID(int reportId) {
        try{
            defectQuery.setInt(1, reportId);
            ResultSet rs = defectQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getWorkProhibitionForReportID(int reportId){
        if(getDaysClosedForReportID(reportId)!=0){
            return "Work prohibition has been issued for " + getDaysClosedForReportID(reportId) + " day/s";
        }else{
            return "Work prohibition has been issued, required conditions: " + getOpeningConditionsForReportID(reportId);
        }
    }

    public String getUniqueIDForReportID(int reportId) {
        try{
            uniqueIDQuery.setInt(1, reportId);
            ResultSet rs = uniqueIDQuery.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
