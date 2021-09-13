package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Task;
import ba.unsa.etf.rpr.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    UserDAO userDAO = UserDAO.getInstance();

    UserDAOTest() throws SQLException {
    }

    @BeforeEach
    public void setUp() throws SQLException {
        userDAO.resetDatabase();
        LocalDateTime loginDate = LocalDateTime.of(2021, 9, 25, 4, 5, 3);
        User user = new User(5, loginDate.format(formatter), 1, "ABCDPF");
        userDAO.addUser(user);
    }

    @Test
    void deleteLoggedUser() {
        assertEquals("ABCDPF", userDAO.getLoggedUserUniqueID());
        userDAO.deleteLoggedUser();
        assertEquals("", userDAO.getLoggedUserUniqueID());
    }

    @Test
    void isLoggedIn() {
        assertTrue(userDAO.isLoggedIn());
    }

    @Test
    void getLoggedUserID() {
        assertEquals(5, userDAO.getLoggedUserID());
    }

    @Test
    void getLoggedUserUniqueID() {
        assertEquals("ABCDPF", userDAO.getLoggedUserUniqueID());
    }
}