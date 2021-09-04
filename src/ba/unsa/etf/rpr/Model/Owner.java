package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Owner {
    private int id;
    private SimpleIntegerProperty phoneNumber;
    private SimpleStringProperty name, surename, jmbg, email;

    public Owner(int id, String name, String surename, String jmbg, int phoneNumber, String email) {
        this.id = id;;
        this.name = new SimpleStringProperty(name);
        this.surename = new SimpleStringProperty(surename);
        this.jmbg = new SimpleStringProperty(jmbg);
        this.phoneNumber = new SimpleIntegerProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
    }

    @Override
    public String toString() {
        return name.get() + " " + surename.get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleIntegerProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurename() {
        return surename.get();
    }

    public SimpleStringProperty surenameProperty() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename.set(surename);
    }

    public String getJmbg() {
        return jmbg.get();
    }

    public SimpleStringProperty jmbgProperty() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg.set(jmbg);
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
}
