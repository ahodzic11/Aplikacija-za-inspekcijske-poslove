package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Administrator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorDAOTest {
    AdministratorDAO administratorDAO = AdministratorDAO.getInstance();

    AdministratorDAOTest() throws SQLException {
    }

    @BeforeEach
    public void setUp() throws SQLException {
        administratorDAO.resetDatabase();
        Administrator admin = new Administrator(0, "ahodzic11@etf.unsa.ba", "admin123", "ABCDEF");
        administratorDAO.addAdministrator(admin);
    }

    @Test
    void getIdForUniqueID() {
        assertEquals(0, administratorDAO.getIdForUniqueID("ABCDEF"));
    }

    @Test
    void modifyAdministrator() {
        administratorDAO.modifyAdministrator(0, "novimail@etf.unsa.ba", "novipassword123", "NewUID");
        assertEquals("novimail@etf.unsa.ba", administratorDAO.getEmailForUniqueID("NewUID"));
        assertEquals("novipassword123", administratorDAO.getPasswordForUniqueID("NewUID"));
        assertEquals("NewUID", administratorDAO.getUniqueIDForEmail("novimail@etf.unsa.ba"));
    }

    @Test
    void addAdministrator() throws SQLException {
        assertEquals(1, administratorDAO.getAllAdministrators().size());
        Administrator newAdmin = new Administrator(1, "probniMail@etf.unsa.ba", "probni123", "KSOAFA");
        administratorDAO.addAdministrator(newAdmin);
        assertEquals(2, administratorDAO.getAllAdministrators().size());
    }

    @Test
    void getUniqueIDForEmail() {
        assertEquals("ABCDEF", administratorDAO.getUniqueIDForEmail("ahodzic11@etf.unsa.ba"));
    }

    @Test
    void getEmailForUniqueID() {
        assertEquals("ahodzic11@etf.unsa.ba", administratorDAO.getEmailForUniqueID("ABCDEF"));
    }

    @Test
    void getPasswordForUniqueID() {
        assertEquals("admin123", administratorDAO.getPasswordForUniqueID("ABCDEF"));
    }

    @Test
    void getAllAdministrators(){
        assertEquals(1, administratorDAO.getAllAdministrators().size());
        administratorDAO.resetDatabase();
        assertEquals(0, administratorDAO.getAllAdministrators().size());
        Administrator newAdmin = new Administrator(1, "probniMail@etf.unsa.ba", "probni123", "KSOAFA");
        administratorDAO.addAdministrator(newAdmin);
        assertEquals(1, administratorDAO.getAllAdministrators().size());
    }
}