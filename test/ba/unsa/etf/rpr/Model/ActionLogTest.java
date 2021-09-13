package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ActionLogTest {
    @Test
    void testKonstruktora(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime newDate = LocalDateTime.of(2021, 9, 13, 4, 5, 3);
        ActionLog actionLog = new ActionLog(0, newDate.format(formatter), "new action", "ABCDEF");
        assertEquals(0, actionLog.getId());
        assertEquals("13/09/2021 04:05:03", actionLog.getDateTime());
        assertEquals("new action", actionLog.getAction());
        assertEquals("ABCDEF", actionLog.getUniqueId());
    }
}