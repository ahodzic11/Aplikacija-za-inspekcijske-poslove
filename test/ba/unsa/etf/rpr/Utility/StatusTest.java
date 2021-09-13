package ba.unsa.etf.rpr.Utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void statusTest() {
        Status status = Status.getInstance();
        status.setStatus("Probna akcija");
        assertEquals("Probna akcija", status.getStatus());
    }
}