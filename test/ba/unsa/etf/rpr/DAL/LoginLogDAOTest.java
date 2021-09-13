package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Inspector;
import ba.unsa.etf.rpr.Model.LoginLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LoginLogDAOTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LoginLogDAO loginLogDAO = LoginLogDAO.getInstance();

    LoginLogDAOTest() throws SQLException {
    }

    @BeforeEach
    public void setUp() throws SQLException {
        loginLogDAO.resetDatabase();

        LocalDateTime logIn = LocalDateTime.of(2021, 9, 13, 4, 5, 3);
        LocalDateTime logOut = LocalDateTime.of(2021, 9, 13, 6, 7, 8);
        LoginLog loginLog = new LoginLog(13, logIn.format(formatter), logOut.format(formatter), "PLSFOE");
        loginLogDAO.addLog(loginLog);
    }

    @Test
    void addLog() throws SQLException {
        assertEquals(1, loginLogDAO.getAllLogs().size());
        LocalDateTime logIn = LocalDateTime.of(2021, 10, 3, 9, 5, 4);
        LocalDateTime logOut = LocalDateTime.of(2021, 10, 10, 8, 53, 12);
        LoginLog loginLog = new LoginLog(12, logIn.format(formatter), logOut.format(formatter), "PASDAS");
        loginLogDAO.addLog(loginLog);
        assertEquals(2, loginLogDAO.getAllLogs().size());
    }

    @Test
    void getAllLogs() throws SQLException {
        assertEquals(1, loginLogDAO.getAllLogs().size());
        LocalDateTime logIn = LocalDateTime.of(2021, 10, 3, 9, 5, 4);
        LocalDateTime logOut = LocalDateTime.of(2021, 10, 10, 8, 53, 12);
        LoginLog loginLog = new LoginLog(12, logIn.format(formatter), logOut.format(formatter), "PASDAS");
        loginLogDAO.addLog(loginLog);

        assertEquals(2, loginLogDAO.getAllLogs().size());
        logIn = LocalDateTime.of(2021, 11, 3, 9, 5, 4);
        logOut = LocalDateTime.of(2021, 11, 10, 8, 53, 12);
        loginLog = new LoginLog(13, logIn.format(formatter), logOut.format(formatter), "ASGWDS");
        loginLogDAO.addLog(loginLog);

        assertEquals(3, loginLogDAO.getAllLogs().size());
        logIn = LocalDateTime.of(2021, 12, 3, 9, 5, 4);
        logOut = LocalDateTime.of(2021, 12, 10, 8, 53, 12);
        loginLog = new LoginLog(12, logIn.format(formatter), logOut.format(formatter), "ASDFSA");
        loginLogDAO.addLog(loginLog);

        assertEquals(4, loginLogDAO.getAllLogs().size());
    }

    @Test
    void deleteAllLogs() {
        assertEquals(1, loginLogDAO.getAllLogs().size());
        loginLogDAO.deleteAllLogs();
        assertEquals(0, loginLogDAO.getAllLogs().size());
    }
}