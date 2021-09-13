package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Owner;
import ba.unsa.etf.rpr.Model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ReportDAOTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    ReportDAO reportDAO = ReportDAO.getInstance();

    ReportDAOTest() throws SQLException {
    }

    @BeforeEach
    public void setUp() throws SQLException {
        reportDAO.resetDatabase();
        LocalDateTime inspectionDate = LocalDateTime.of(2021, 9, 13, 4, 5, 3);
        Report report = new Report(1, 2, inspectionDate.format(formatter),
                "Description filled", "Products expired", 135, "None",
                0, 0, 1, 1, 25, "None",
                1, 15, 123539, "No defect",
                13, 14, 3, "Del", "Alije Izetbegovića 13", "ABCDEF");
        reportDAO.addReport(report);
    }

    @Test
    void modifyReport() {
        LocalDateTime inspectionDate = LocalDateTime.of(2021, 9, 13, 4, 5, 3);
        // new report description to check if modifying works fine
        reportDAO.modifyReport(0, 2, inspectionDate.format(formatter),
                "New report description", "Products expired", 135, "None",
                0, 0, 1, 1, 25, "None",
                1, 15, 123539, "No defect",
                13, 14, 3, "Del", "Alije Izetbegovića 13", "ABCDEF");
        assertEquals("New report description", reportDAO.getDescriptionForReportID(0));
    }

    @Test
    void deleteAllReports() {
        assertEquals(1, reportDAO.allReports().size());
        reportDAO.deleteAllReports();
        assertEquals(0, reportDAO.allReports().size());
    }

    @Test
    void getNewReportID() {
        assertEquals(1, reportDAO.getNewReportID());
    }

    @Test
    void addReport() throws SQLException {
        LocalDateTime inspectionDate = LocalDateTime.of(2021, 9, 13, 4, 5, 3);
        assertEquals(1, reportDAO.allReports().size());
        Report newReport = new Report(1, 2, inspectionDate.format(formatter),
                "New report description", "Products expired", 135, "None",
                0, 0, 1, 1, 25, "None",
                1, 15, 123539, "No defect",
                13, 14, 3, "Del", "Alije Izetbegovića 13", "ABCDEF");
        reportDAO.addReport(newReport);
        assertEquals(2, reportDAO.allReports().size());
    }

    @Test
    void getReportsForInspectorID() throws SQLException {
        assertEquals(1, reportDAO.getReportsForInspectorID(2).size());
    }

    @Test
    void deleteReportWithID() {
        assertEquals(1, reportDAO.allReports().size());
        reportDAO.deleteReportWithID(0);
        assertEquals(0, reportDAO.allReports().size());
    }

    @Test
    void getObjectIDForReport() {
        assertEquals(3, reportDAO.getObjectIDForReport(0));
    }

    @Test
    void getViolationForReportID() {
        assertEquals("Products expired", reportDAO.getViolationForReportID(0));
    }

    @Test
    void getDescriptionForReportID() {
        assertEquals("Description filled", reportDAO.getDescriptionForReportID(0));
    }

    @Test
    void getInspectionDateForReportID() {
        assertEquals("13/09/2021 04:05:03", reportDAO.getInspectionDateForReportID(0));
    }

    @Test
    void violationBooked() {
        assertTrue(reportDAO.violationBooked(0));
    }

    @Test
    void getFineForReportID() {
        assertEquals(String.valueOf(135), reportDAO.getFineForReportID(0));
    }

    @Test
    void getAdditionalRequirementsForReportID() {
        assertEquals("None", reportDAO.getAdditionalRequirementsForReportID(0));
    }

    @Test
    void getRecordedWorkersForReportID() {
        assertEquals(0, reportDAO.getRecordedWorkersForReportID(0));
    }

    @Test
    void getCriminalOffenseForReportID() {
        assertEquals(0, reportDAO.getCriminalOffenseForReportID(0));
    }

    @Test
    void getPhytocertificateForReportID() {
        assertEquals(1, reportDAO.getPhytocertificateForReportID(0));
    }

    @Test
    void sampleTakenForID() {
        assertTrue(reportDAO.sampleTakenForID(0));
    }

    @Test
    void getDaysClosedForReportID() {
        assertEquals(25, reportDAO.getDaysClosedForReportID(0));
    }

    @Test
    void getOpeningConditionsForReportID() {
        assertEquals("None", reportDAO.getOpeningConditionsForReportID(0));
    }

    @Test
    void isReportedWorksite() {
        assertTrue(reportDAO.isReportedWorksite(0));
    }

    @Test
    void getEmployeeNumberForReportID() {
        assertEquals(15, reportDAO.getEmployeeNumberForReportID(0));
    }

    @Test
    void getOpeningCertificateNumberForReportID() {
        assertEquals(123539, reportDAO.getOpeningCertificateNumberForReportID(0));
    }

    @Test
    void getDefectForReportID() {
        assertEquals("No defect", reportDAO.getDefectForReportID(0));
    }

    @Test
    void getWorkProhibitionForReportID() {
        assertEquals("Work prohibition has been issued for 25 day/s", reportDAO.getWorkProhibitionForReportID(0));
    }

    @Test
    void getUniqueIDForReportID() {
        assertEquals("ABCDEF", reportDAO.getUniqueIDForReportID(0));
    }
}