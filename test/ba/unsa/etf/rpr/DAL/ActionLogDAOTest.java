package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.ActionLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ActionLogDAOTest {
    ActionLogDAO actionLogDAO = ActionLogDAO.getInstance();

    ActionLogDAOTest() throws SQLException {
    }

    @BeforeEach
    public void resetBase() throws SQLException {
        actionLogDAO.resetDatabase();
    }

    @Test
    void deleteLogs() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime newDate = LocalDateTime.of(2021, 9, 16, 2, 23, 12);
        LocalDateTime newDate1 = LocalDateTime.of(2021, 9, 17, 2, 2, 1);
        ActionLog actionLog = new ActionLog(0, newDate.format(formatter), "Action log #0", "ABCDEF");
        ActionLog actionLog1 = new ActionLog(1, newDate1.format(formatter), "Action log #1", "BASDFS");
        assertEquals(0, actionLogDAO.getAllLogs().size());
        actionLogDAO.addLog(actionLog);
        actionLogDAO.addLog(actionLog1);
        assertEquals(2, actionLogDAO.getAllLogs().size());
        actionLogDAO.deleteLogs();
        assertEquals(0, actionLogDAO.getAllLogs().size());
    }

    @Test
    void addLog() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime newDate = LocalDateTime.of(2021, 9, 16, 2, 23, 37);
        ActionLog actionLog = new ActionLog(0, newDate.format(formatter), "User ran DAL test/s", "ABCDEF");
        try {
            actionLogDAO.addLog(actionLog);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(actionLogDAO.getAllLogs().size(), 1);
    }

    @Test
    void getAllLogs() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime newDate = LocalDateTime.of(2021, 9, 16, 2, 23, 12);
        LocalDateTime newDate1 = LocalDateTime.of(2021, 9, 17, 2, 2, 1);
        LocalDateTime newDate2 = LocalDateTime.of(2021, 1, 12, 7, 26, 37);
        LocalDateTime newDate3 = LocalDateTime.of(2021, 8, 17, 3, 23, 8);
        LocalDateTime newDate4 = LocalDateTime.of(2021, 3, 6, 3, 12, 53);
        ActionLog actionLog = new ActionLog(0, newDate.format(formatter), "Action log #0", "ABCDEF");
        ActionLog actionLog1 = new ActionLog(1, newDate1.format(formatter), "Action log #1", "BASDFS");
        ActionLog actionLog2 = new ActionLog(2, newDate2.format(formatter), "Action log #2", "ASGEGS");
        ActionLog actionLog3 = new ActionLog(3, newDate3.format(formatter), "Action log #3", "RHRFEW");
        ActionLog actionLog4 = new ActionLog(4, newDate4.format(formatter), "Action log #4", "HDFDES");
        actionLogDAO.addLog(actionLog);
        actionLogDAO.addLog(actionLog1);
        assertEquals(2, actionLogDAO.getAllLogs().size());
        actionLogDAO.addLog(actionLog2);
        assertEquals(3, actionLogDAO.getAllLogs().size());
        actionLogDAO.addLog(actionLog3);
        actionLogDAO.addLog(actionLog4);
        assertEquals(5, actionLogDAO.getAllLogs().size());
    }
}