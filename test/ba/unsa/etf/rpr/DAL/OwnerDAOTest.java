package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Object;
import ba.unsa.etf.rpr.Model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OwnerDAOTest {
    OwnerDAO ownerDAO = OwnerDAO.getInstance();

    OwnerDAOTest() throws SQLException {
    }

    @BeforeEach
    public void setUp() throws SQLException {
        ownerDAO.resetDatabase();
        Owner owner = new Owner(35, "Adnan", "Hodzic", "1234567891234", 8539340, "ahodzic11@etf.unsa.ba");
        ownerDAO.addOwner(owner);
    }

    @Test
    void allOwners() {
        assertEquals(1, ownerDAO.allOwners().size());
    }

    @Test
    void addOwner() throws SQLException {
        assertEquals(1, ownerDAO.allOwners().size());
        Owner owner = new Owner(3, "Dinio", "Mujkić", "319413953922", 213912, "dmujkic1@etf.unsa.ba");
        ownerDAO.addOwner(owner);
        assertEquals(2, ownerDAO.allOwners().size());
    }

    @Test
    void deleteOwner() {
        assertEquals(1, ownerDAO.allOwners().size());
        ownerDAO.deleteOwner(0);
        assertEquals(0, ownerDAO.allOwners().size());
    }

    @Test
    void getNameLastNameForID() {
        assertEquals("Adnan Hodzic", ownerDAO.getNameLastNameForID(0));
    }
}