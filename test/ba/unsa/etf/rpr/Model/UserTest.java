package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void constructorTest(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime loginDate = LocalDateTime.of(2021, 9, 25, 4, 5, 3);
        User user = new User(18, loginDate.format(formatter), 1, "ABCDPF");
        assertEquals(18, user.getId());
        assertEquals("25/09/2021 04:05:03", user.getLoginDate());
        assertEquals(1, user.getLoggedIn());
        assertEquals("ABCDPF", user.getUniqueID());
    }
}