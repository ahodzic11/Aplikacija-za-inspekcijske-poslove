package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InspectorTest {
    @Test
    void constructorTest(){
        Inspector inspector = new Inspector(0, "Adnan", "Hodzic", "07.04.2000", "1234567891234",
                1, "ABCD123", "Kakanj", "062734086", "adnan@gmail.com",
                "ahodzic11@etf.unsa.ba", "adnan123", 1, "ASDFEO", "Federal inspector",
                "Business property");
        assertEquals(0, inspector.getId());
        assertEquals("Adnan", inspector.getFirstName());
        assertEquals("Hodzic", inspector.getLastName());
        assertEquals("07.04.2000", inspector.getBirthdate());
        assertEquals("1234567891234", inspector.getJmbg());
        assertEquals(1, inspector.getGender());
        assertEquals("ABCD123", inspector.getIdNumber());
        assertEquals("Kakanj", inspector.getResidence());
        assertEquals("062734086", inspector.getPhoneNumber());
        assertEquals("adnan@gmail.com", inspector.getEmail());
        assertEquals("ahodzic11@etf.unsa.ba", inspector.getLoginEmail());
        assertEquals("adnan123", inspector.getPassword());
        assertEquals(1, inspector.getDriversLicense());
        assertEquals("ASDFEO", inspector.getUniqueId());
        assertEquals("Federal inspector", inspector.getInspectorType());
        assertEquals("Business property", inspector.getInspectionArea());
    }

    @Test
    void testToString() {
        Inspector inspector = new Inspector(0, "Adnan", "Hodzic", "07.04.2000", "1234567891234",
                1, "ABCD123", "Kakanj", "062734086", "adnan@gmail.com",
                "ahodzic11@etf.unsa.ba", "adnan123", 1, "ASDFEO", "Federal inspector",
                "Business property");
        assertEquals("Adnan Hodzic", inspector.toString());
    }
}