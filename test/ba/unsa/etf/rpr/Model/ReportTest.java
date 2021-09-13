package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    void constructorTest(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime inspectionDate = LocalDateTime.of(2021, 9, 13, 4, 5, 3);
        Report report = new Report(1, 2, inspectionDate.format(formatter),
                "Description filled", "Products expired", 135, "None",
                0, 0, 1, 1, 25, "None",
                1, 15, 123539, "No defect",
                13, 14, 3, "Del", "Alije Izetbegovića 13", "ABCDEF");
        assertEquals(1, report.getId());
        assertEquals(2, report.getInspectorId());
        assertEquals("13/09/2021 04:05:03", report.getInspectionDate());
        assertEquals("Description filled", report.getDescription());
        assertEquals("Products expired", report.getViolation());
        assertEquals(135, report.getFine());
        assertEquals("None", report.getAdditionalRequirements());
        assertEquals(0, report.getRecordedWorkers());
        assertEquals(0, report.getCriminalOffense());
        assertEquals(1, report.getPhytocertificate());
        assertEquals(1, report.getPhytocertificate());
        assertEquals(25, report.getDaysClosed());
        assertEquals("None", report.getOpeningCondition());
        assertEquals(1, report.getReportedWorksite());
        assertEquals(15, report.getEmployeesNumber());
        assertEquals(123539, report.getOpeningCertificationNumber());
        assertEquals("No defect", report.getDefect());
        assertEquals(13, report.getFirstWitnessId());
        assertEquals(14, report.getSecondWitnessId());
        assertEquals(3, report.getObjectId());
        assertEquals("Del", report.getObjectName());
        assertEquals("Alije Izetbegovića 13", report.getObjectAddress());
        assertEquals("ABCDEF", report.getUniqueID());
    }

    @Test
    void testToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime inspectionDate = LocalDateTime.of(2021, 9, 13, 4, 5, 3);
        Report report = new Report(1, 2, inspectionDate.format(formatter),
                "Description filled", "Products expired", 135, "None",
                0, 0, 1, 1, 25, "None",
                1, 15, 123539, "No defect",
                13, 14, 3, "Del", "Alije Izetbegovića 13", "ABCDEF");
        assertEquals("Del, Alije Izetbegovića 13", report.toString());
    }
}