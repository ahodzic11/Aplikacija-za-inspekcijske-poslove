package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class LoginLog {
    private int id;
    private SimpleStringProperty login, logout, uniqueId;

    public LoginLog(int id, String login, String logout, String uniqueId) {
        this.id = id;
        this.login = new SimpleStringProperty(login);
        this.logout = new SimpleStringProperty(logout);
        this.uniqueId = new SimpleStringProperty(uniqueId);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getLogout() {
        return logout.get();
    }

    public SimpleStringProperty logoutProperty() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout.set(logout);
    }
}
