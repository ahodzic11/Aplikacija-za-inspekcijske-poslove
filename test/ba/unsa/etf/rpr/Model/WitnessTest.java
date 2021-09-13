package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WitnessTest {
    @Test
    void constructorTest(){
        Witness witness = new Witness(96, 1239, "Dino", "Mujkić", "1295120351235",
                "I have found a product with an expired date");
        assertEquals(96, witness.getId());
        assertEquals(1239, witness.getReportId());
        assertEquals("Dino", witness.getName());
        assertEquals("Mujkić", witness.getSurename());
        assertEquals("1295120351235", witness.getJmbg());
        assertEquals("I have found a product with an expired date", witness.getStatement());
    }
}