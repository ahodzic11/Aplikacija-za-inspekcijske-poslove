package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Vlasnik {
    private int id;
    private SimpleIntegerProperty telefon;
    private SimpleStringProperty ime, prezime, jmbg, email;

    public Vlasnik(int id, String ime, String prezime, String jmbg, int telefon, String email) {
        this.id = id;;
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.jmbg = new SimpleStringProperty(jmbg);
        this.telefon = new SimpleIntegerProperty(telefon);
        this.email = new SimpleStringProperty(email);
    }

    @Override
    public String toString() {
        return ime.get() + " " + prezime.get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTelefon() {
        return telefon.get();
    }

    public SimpleIntegerProperty telefonProperty() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon.set(telefon);
    }

    public String getIme() {
        return ime.get();
    }

    public SimpleStringProperty imeProperty() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public String getPrezime() {
        return prezime.get();
    }

    public SimpleStringProperty prezimeProperty() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime.set(prezime);
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
