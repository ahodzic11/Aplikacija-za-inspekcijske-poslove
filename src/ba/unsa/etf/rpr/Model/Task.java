package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {
    private int id, objectId, inspectorId, assignedInspectorID;
    private SimpleStringProperty datetime, notes;
    private SimpleIntegerProperty completed;

    public Task(int id, int objectId, int inspectorId, String datetime, String notes, int completed, int assignedInspectorID) {
        this.id = id;
        this.objectId = objectId;
        this.inspectorId = inspectorId;
        this.datetime = new SimpleStringProperty(datetime);
        this.notes = new SimpleStringProperty(notes);
        this.completed = new SimpleIntegerProperty(completed);
        this.assignedInspectorID = assignedInspectorID;
    }

    public int getAssignedInspectorID() {
        return assignedInspectorID;
    }

    public void setAssignedInspectorID(int assignedInspectorID) {
        this.assignedInspectorID = assignedInspectorID;
    }

    public int getCompleted() {
        return completed.get();
    }

    public SimpleIntegerProperty completedProperty() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed.set(completed);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(int inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getDatetime() {
        return datetime.get();
    }

    public SimpleStringProperty datetimeProperty() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime.set(datetime);
    }

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    @Override
    public String toString() {
        return datetime.get();
    }
}
