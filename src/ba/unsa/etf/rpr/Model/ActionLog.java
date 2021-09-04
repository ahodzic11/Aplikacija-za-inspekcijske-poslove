package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class ActionLog {
    private int id;
    private SimpleStringProperty dateTime, action, uniqueId;

    public ActionLog(int id, String dateTime, String action, String uniqueId) {
        this.id = id;
        this.dateTime = new SimpleStringProperty(dateTime);
        this.action = new SimpleStringProperty(action);
        this.uniqueId = new SimpleStringProperty(uniqueId);
    }

    public String getDateTime() {
        return dateTime.get();
    }

    public SimpleStringProperty dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime.set(dateTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action.get();
    }

    public SimpleStringProperty actionProperty() {
        return action;
    }

    public void setAction(String action) {
        this.action.set(action);
    }

    public String getUniqueId() {
        return uniqueId.get();
    }

    public SimpleStringProperty uniqueIdProperty() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId.set(uniqueId);
    }
}
