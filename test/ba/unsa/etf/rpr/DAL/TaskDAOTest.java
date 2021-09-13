package ba.unsa.etf.rpr.DAL;

import ba.unsa.etf.rpr.Model.Report;
import ba.unsa.etf.rpr.Model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TaskDAOTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    TaskDAO taskDAO = TaskDAO.getInstance();

    TaskDAOTest() throws SQLException {
    }

    @BeforeEach
    public void setUp() throws SQLException {
        taskDAO.resetDatabase();
        LocalDateTime taskDate = LocalDateTime.of(2021, 9, 13, 7, 12, 53);
        Task task = new Task(0, 3, 1, taskDate.format(formatter), "No notes taken", 1, 13);
        taskDAO.addTask(task);
    }

    @Test
    void modifyTask() {
        LocalDateTime taskDate = LocalDateTime.of(2021, 9, 13, 7, 12, 53);
        taskDAO.modifyTask(0, 3, 1, taskDate.format(formatter), "Some notes taken", 1, 13);
        assertEquals("Some notes taken", taskDAO.getNotesForTask(0));
    }

    @Test
    void getTaskDoneStatus() {
        assertEquals(1, taskDAO.getTaskDoneStatus(0));
    }

    @Test
    void deleteTask() {
        assertEquals(1, taskDAO.getAllTasks().size());
        taskDAO.deleteTask(0);
        assertEquals(0, taskDAO.getAllTasks().size());
    }

    @Test
    void addTask() throws SQLException {
        LocalDateTime taskDate = LocalDateTime.of(2021, 9, 13, 7, 12, 53);
        Task newTask = new Task(1, 2, 3, taskDate.format(formatter), "New task", 0, 2);
        taskDAO.addTask(newTask);
        assertEquals(2, taskDAO.getAllTasks().size());
    }

    @Test
    void getAllTasks() {
        assertEquals(1, taskDAO.getAllTasks().size());
    }

    @Test
    void getAllTasksForInspector() {
        assertEquals(1, taskDAO.getAllTasksForInspector(1).size());
    }

    @Test
    void getInspectorForID() {
        assertEquals(1, taskDAO.getInspectorForID(0));
    }

    @Test
    void getObjectID() {
        assertEquals(3, taskDAO.getObjectID(0));
    }

    @Test
    void getDatetime() {
        assertEquals("13/09/2021 07:12:53", taskDAO.getDatetime(0));
    }

    @Test
    void getNotesForTask() {
        assertEquals("No notes taken", taskDAO.getNotesForTask(0));
    }

    @Test
    void getAssignedInspectorID() {
        assertEquals(13, taskDAO.getAssignedInspectorID(0));
    }

    @Test
    void isCompleted() {
        assertTrue(taskDAO.isCompleted(0));
    }

    @Test
    void isTaken() {
        assertTrue(taskDAO.isTaken(0));
    }

    @Test
    void getUntakenTasks() {
        assertEquals(0, taskDAO.getUntakenTasks().size());
    }

    @Test
    void assignTaskToInspectorID() throws SQLException {
        LocalDateTime taskDate = LocalDateTime.of(2021, 9, 13, 7, 12, 53);
        Task newTask = new Task(3, 6, 3, taskDate.format(formatter), "New task 2", 0, -1);
        taskDAO.addTask(newTask);
        taskDAO.assignTaskToInspectorID(3, 1);
        assertEquals(3, taskDAO.getAssignedInspectorID(1));
    }
}