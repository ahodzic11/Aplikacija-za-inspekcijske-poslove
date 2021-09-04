package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class Administrator {
    private int id;
    private SimpleStringProperty email, password, uniqueId;

    public Administrator(int id, String email, String password, String uniqueId) {
        this.id = id;
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.uniqueId = new SimpleStringProperty(uniqueId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
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
