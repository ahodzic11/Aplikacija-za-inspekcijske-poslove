package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private int id, loggedIn;
    private SimpleStringProperty loginDate, uniqueID;

    public User(int id, String loginDate, int loggedIn, String uniqueID) {
        this.id = id;
        this.loginDate = new SimpleStringProperty(loginDate);
        this.loggedIn = loggedIn;
        this.uniqueID = new SimpleStringProperty(uniqueID);
    }

    public String getUniqueID() {
        return uniqueID.get();
    }

    public SimpleStringProperty uniqueIDProperty() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID.set(uniqueID);
    }

    public String getLoginDate() {
        return loginDate.get();
    }

    public SimpleStringProperty loginDateProperty() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate.set(loginDate);
    }

    public int getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(int loggedIn) {
        this.loggedIn = loggedIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
