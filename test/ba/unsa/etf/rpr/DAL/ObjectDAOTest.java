package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Object;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ObjectDAOTest {
    ObjectDAO objectDAO = ObjectDAO.getInstance();

    ObjectDAOTest() throws SQLException {
    }

    @BeforeEach
    public void setUp() throws SQLException {
        objectDAO.resetDatabase();
        Object o = new Object(15, 92, "Del", "Alije Izetbegovića 13", "Pub");
        objectDAO.addObject(o);
    }

    @Test
    void modifyObject() {
        objectDAO.modifyObject(0, 3, "ProbniObjekat", "ProbnaAdresa", "ProbniTip");
        assertEquals(3, objectDAO.getOwnerForID(0));
        assertEquals("ProbniObjekat", objectDAO.getNameForID(0));
        assertEquals("ProbnaAdresa", objectDAO.getAddressForObjectID(0));
        assertEquals("ProbniTip", objectDAO.getObjectTypeForID(0));
    }

    @Test
    void getAllObjects() {
        assertEquals(1, objectDAO.getAllObjects().size());
    }

    @Test
    void addObject() throws SQLException {
        assertEquals(1, objectDAO.getAllObjects().size());
        Object o = new Object(13, 52, "Donahas", "Povezice BB", "Pekara");
        objectDAO.addObject(o);
        assertEquals(2, objectDAO.getAllObjects().size());
    }

    @Test
    void getObjectsFromOwner() {
        assertEquals(1, objectDAO.getObjectsFromOwner(92).size());
    }

    @Test
    void deleteObjectWithID() {
        assertEquals(1, objectDAO.getAllObjects().size());
        objectDAO.deleteObjectWithID(0);
        assertEquals(0, objectDAO.getAllObjects().size());
    }

    @Test
    void getAddressForObjectID() {
        assertEquals("Alije Izetbegovića 13", objectDAO.getAddressForObjectID(0));
    }

    @Test
    void getNameForID() {
        assertEquals("Del", objectDAO.getNameForID(0));
    }

    @Test
    void getObjectTypeForID() {
        assertEquals("Pub", objectDAO.getObjectTypeForID(0));
    }

    @Test
    void getOwnerForID() {
        assertEquals(92, objectDAO.getOwnerForID(0));
    }

    @Test
    void deleteOwnersObjects() {
        assertEquals(1, objectDAO.getObjectsFromOwner(92).size());
        objectDAO.deleteOwnersObjects(92);
        assertEquals(0, objectDAO.getObjectsFromOwner(92).size());
    }
}