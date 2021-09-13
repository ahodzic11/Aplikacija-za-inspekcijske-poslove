package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Administrator;
import ba.unsa.etf.rpr.Model.Inspector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class InspectorDAOTest {
    InspectorDAO inspectorDAO = InspectorDAO.getInstance();

    @BeforeEach
    public void setUp() throws SQLException {
        inspectorDAO.resetDatabase();
        Inspector inspector = new Inspector(0, "Adnan", "Hodzic", "07.04.2000", "1234567891234",
                1, "ABCD123", "Kakanj", "062734086", "adnan@gmail.com",
                "ahodzic11@etf.unsa.ba", "adnan123", 1, "ASDFEO", "Federal inspector",
                "Business property");

        inspectorDAO.addInspector(inspector);
    }

    @Test
    void getIdForUniqueID() {
        assertEquals(0, inspectorDAO.getIdForUniqueID("ASDFEO"));
    }

    @Test
    void getNameSurenameForID() {
        assertEquals("Hodzic", inspectorDAO.getSurenameForID(0));
    }

    @Test
    void getUniqueIDForID() {
        assertEquals("ASDFEO", inspectorDAO.getUniqueIDForID(0));
    }

    @Test
    void getDriversLicenseForID() {
        assertEquals(1, inspectorDAO.getDriversLicenseForID(0));
    }

    @Test
    void getPasswordForID() {
        assertEquals("adnan123", inspectorDAO.getPasswordForID(0));
    }

    @Test
    void getLoginEmailForID() {
        assertEquals("ahodzic11@etf.unsa.ba", inspectorDAO.getLoginEmailForID(0));
    }

    @Test
    void getEmailForID() {
        assertEquals("adnan@gmail.com", inspectorDAO.getEmailForID(0));
    }

    @Test
    void getPhoneNumberForID() {
        assertEquals("062734086", inspectorDAO.getPhoneNumberForID(0));
    }

    @Test
    void getResidenceForID() {
        assertEquals("Kakanj", inspectorDAO.getResidenceForID(0));
    }

    @Test
    void getIDNumberForID() {
        assertEquals("ABCD123", inspectorDAO.getIDNumberForID(0));
    }

    @Test
    void getGenderForID() {
        assertEquals(1, inspectorDAO.getGenderForID(0));
    }

    @Test
    void getJMBGForID() {
        assertEquals("1234567891234", inspectorDAO.getJMBGForID(0));
    }

    @Test
    void getBirthdateForID() {
        assertEquals("07.04.2000", inspectorDAO.getBirthdateForID(0));
    }

    @Test
    void getSurenameForID() {
        assertEquals("Hodzic", inspectorDAO.getSurenameForID(0));
    }

    @Test
    void getFirstNameForID() {
        assertEquals("Adnan", inspectorDAO.getFirstNameForID(0));
    }

    @Test
    void getIDForLoginEmail() {
        assertEquals(0, inspectorDAO.getIDForLoginEmail("ahodzic11@etf.unsa.ba"));
    }

    @Test
    void modifyInspector() {
        inspectorDAO.modifyInspector(0, "novoIme", "novoPrezime", "ASDFEO", "Kakanj",
                "062333333", "ahodzic11@etf.unsa.ba", "ahodzic11@etf.unsa.ba", "adnan123",
                1, "Major federal inspector", "School business");
        assertEquals("novoIme", inspectorDAO.getFirstNameForID(0));
        assertEquals("novoPrezime", inspectorDAO.getSurenameForID(0));
        assertEquals("062333333", inspectorDAO.getPhoneNumberForID(0));
        assertEquals("ahodzic11@etf.unsa.ba", inspectorDAO.getEmailForID(0));
        assertTrue(inspectorDAO.isMajorInspector(0));
        assertEquals("School business", inspectorDAO.getInspectionAreaForID(0));
    }

    @Test
    void deleteInspector() {
        assertEquals(1, inspectorDAO.allInspectors().size());
        inspectorDAO.deleteInspector(0);
        assertEquals(0, inspectorDAO.allInspectors().size());
    }

    @Test
    void addInspector() throws SQLException {
        assertEquals(1, inspectorDAO.allInspectors().size());
        Inspector newInspector = new Inspector(1, "Dino", "Mujkić", "03.03.1986", "6334567631234",
                1, "ASDF12", "Sarajevo", "063863940", "dino@gmail.com",
                "dmujkic1@etf.unsa.ba", "dino123", 0, "SADKOF", "Major federal inspector",
                "School property");
        inspectorDAO.addInspector(newInspector);
        assertEquals(2, inspectorDAO.allInspectors().size());
    }

    @Test
    void allValidInspectors() {
        assertEquals(1, inspectorDAO.allValidInspectors().size());
    }

    @Test
    void allInspectors() {
        assertEquals(1, inspectorDAO.allInspectors().size());
    }

    @Test
    void getInspectorIDForEmail() {
        assertEquals(0, inspectorDAO.getInspectorIDForEmail("ahodzic11@etf.unsa.ba"));
    }

    @Test
    void getInspectorTypeForID() {
        assertEquals("Federal inspector", inspectorDAO.getInspectorTypeForID(0));
    }

    @Test
    void getInspectionAreaForID() {
        assertEquals("Business property", inspectorDAO.getInspectionAreaForID(0));
    }

    @Test
    void isMajorInspector() throws SQLException {
        assertFalse(inspectorDAO.isMajorInspector(0));
        Inspector newInspector = new Inspector(1, "Dino", "Mujkić", "03.03.1986", "6334567631234",
                1, "ASDF12", "Sarajevo", "063863940", "dino@gmail.com",
                "dmujkic1@etf.unsa.ba", "dino123", 0, "SADKOF", "Major federal inspector",
                "School property");
        inspectorDAO.addInspector(newInspector);
        assertTrue(inspectorDAO.isMajorInspector(1));
    }
}