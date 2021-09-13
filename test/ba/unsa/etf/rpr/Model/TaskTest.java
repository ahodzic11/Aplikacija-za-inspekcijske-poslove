package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void constructorTest(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime taskDate = LocalDateTime.of(2021, 9, 13, 7, 12, 53);
        Task task = new Task(35, 3, 1, taskDate.format(formatter), "No notes taken", 1, 13);
        assertEquals(35, task.getId());
        assertEquals(3, task.getObjectId());
        assertEquals(1, task.getInspectorId());
        assertEquals("13/09/2021 07:12:53", task.getDatetime());
        assertEquals("No notes taken", task.getNotes());
        assertEquals(1, task.getCompleted());
        assertEquals(13, task.getAssignedInspectorID());
    }


    @Test
    void testToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime taskDate = LocalDateTime.of(2021, 9, 13, 7, 12, 53);
        Task task = new Task(35, 3, 1, taskDate.format(formatter), "No notes taken", 1, 13);
        assertEquals("13/09/2021 07:12:53", task.toString());
    }
}