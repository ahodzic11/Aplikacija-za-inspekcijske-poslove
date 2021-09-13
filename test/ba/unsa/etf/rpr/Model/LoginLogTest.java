package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LoginLogTest {
    @Test
    void constructorTest(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime logIn = LocalDateTime.of(2021, 9, 13, 4, 5, 3);
        LocalDateTime logOut = LocalDateTime.of(2021, 9, 13, 6, 7, 8);
        LoginLog loginLog = new LoginLog(13, logIn.format(formatter), logOut.format(formatter), "PLSFOE");
        assertEquals(13, loginLog.getId());
        assertEquals("13/09/2021 04:05:03", loginLog.getLogin());
        assertEquals("13/09/2021 06:07:08", loginLog.getLogout());
        assertEquals("PLSFOE", loginLog.getUniqueId());
    }
}