package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {
    @Test
    void testKonstruktora(){
        Administrator admin = new Administrator(0, "probniMail@gmail.com", "admin123", "ABCDEF");
        assertEquals(0, admin.getId());
        assertEquals("probniMail@gmail.com", admin.getEmail());
        assertEquals("admin123", admin.getPassword());
        assertEquals("ABCDEF", admin.getUniqueId());
    }
}