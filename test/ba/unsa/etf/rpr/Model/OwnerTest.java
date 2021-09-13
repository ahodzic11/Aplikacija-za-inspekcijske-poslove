package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    @Test
    void constructorTest(){
        Owner owner = new Owner(35, "Adnan", "Hodzic", "1234567891234", 8539340, "ahodzic11@etf.unsa.ba");
        assertEquals(35, owner.getId());
        assertEquals("Adnan", owner.getName());
        assertEquals("Hodzic", owner.getSurename());
        assertEquals("1234567891234", owner.getJmbg());
        assertEquals(8539340, owner.getPhoneNumber());
        assertEquals("ahodzic11@etf.unsa.ba", owner.getEmail());
    }

    @Test
    void testToString() {
        Owner owner = new Owner(35, "Adnan", "Hodzic", "1234567891234", 8539340, "ahodzic11@etf.unsa.ba");
        assertEquals("Adnan Hodzic", owner.toString());
    }
}