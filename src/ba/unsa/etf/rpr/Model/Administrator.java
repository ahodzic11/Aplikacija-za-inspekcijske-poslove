package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Administrator {
    private int id;
    private SimpleStringProperty email, sifra, jedinstvenaSifra;

    public Administrator(int id, String email, String sifra, String jedinstvenaSifra) {
        this.id = id;
        this.email = new SimpleStringProperty(email);
        this.sifra = new SimpleStringProperty(sifra);
        this.jedinstvenaSifra = new SimpleStringProperty(jedinstvenaSifra);
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

    public String getSifra() {
        return sifra.get();
    }

    public SimpleStringProperty sifraProperty() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra.set(sifra);
    }

    public String getJedinstvenaSifra() {
        return jedinstvenaSifra.get();
    }

    public SimpleStringProperty jedinstvenaSifraProperty() {
        return jedinstvenaSifra;
    }

    public void setJedinstvenaSifra(String jedinstvenaSifra) {
        this.jedinstvenaSifra.set(jedinstvenaSifra);
    }
}
