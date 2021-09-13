package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.User;
import ba.unsa.etf.rpr.Model.Witness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class WitnessDAOTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    WitnessDAO witnessDAO = WitnessDAO.getInstance();

    WitnessDAOTest() throws SQLException {
    }

    @BeforeEach
    public void setUp() throws SQLException {
        witnessDAO.resetDatabase();
        Witness witness = new Witness(0, 1239, "Dino", "Mujkić", "1295120351235",
                "I have found a product with an expired date");
        Witness witness2 = new Witness(1, 1239, "Adnan", "Hodzic", "1350124060239",
                "Product date expired...");
        witnessDAO.addWitness(witness);
        witnessDAO.addWitness(witness2);
    }

    @Test
    void modifyWitness() {
        witnessDAO.modifyWitness(0, 1239, "Dino", "Mujkić", "1295120351235",
                "New statement");
        assertEquals("New statement", witnessDAO.getWitnessStatementForWitness(0));
    }

    @Test
    void getFirstWitnessID() {
        assertEquals(0, witnessDAO.getFirstWitnessID(1239));
    }

    @Test
    void getSecondWitnessID() {
        assertEquals(1, witnessDAO.getSecondWitnessID(1239));
    }

    @Test
    void allWitnesses() {
        assertEquals(2, witnessDAO.allWitnesses().size());
    }

    @Test
    void getWitnessStatementForWitness() {
        assertEquals("I have found a product with an expired date", witnessDAO.getWitnessStatementForWitness(0));
        assertEquals("Product date expired...", witnessDAO.getWitnessStatementForWitness(1));
    }

    @Test
    void getWitnessName() {
        assertEquals("Dino", witnessDAO.getWitnessName(0));
        assertEquals("Adnan", witnessDAO.getWitnessName(1));
    }

    @Test
    void getWitnessSurename() {
        assertEquals("Mujkić", witnessDAO.getWitnessSurename(0));
        assertEquals("Hodzic", witnessDAO.getWitnessSurename(1));
    }

    @Test
    void getWitnessJMBG() {
        assertEquals("1295120351235", witnessDAO.getWitnessJMBG(0));
        assertEquals("1350124060239", witnessDAO.getWitnessJMBG(1));
    }

    @Test
    void getNewWitnessID() {
        assertEquals(2, witnessDAO.getNewWitnessID());
    }

    @Test
    void addWitness() throws SQLException {
        assertEquals(2, witnessDAO.allWitnesses().size());
        Witness witness = new Witness(0, 1239, "Mujo", "Mujić", "125102302152",
                "Test statement");
        witnessDAO.addWitness(witness);
        assertEquals(3, witnessDAO.allWitnesses().size());
    }

    @Test
    void deleteAllWitnesses() {
        assertEquals(2, witnessDAO.allWitnesses().size());
        witnessDAO.deleteAllWitnesses();
        assertEquals(0, witnessDAO.allWitnesses().size());
    }
}