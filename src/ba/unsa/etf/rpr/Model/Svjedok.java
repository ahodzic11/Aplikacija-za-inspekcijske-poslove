package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class Svjedok {
    private int id, idIzvjestaja;
    private SimpleStringProperty ime, prezime, jmbg, izjava;


    public Svjedok(int id, int idIzvjestaja, String ime, String prezime, String jmbg, String izjava) {
        this.id = id;
        this.idIzvjestaja = idIzvjestaja;
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.jmbg = new SimpleStringProperty(jmbg);
        this.izjava = new SimpleStringProperty(izjava);
    }

    public int getIdIzvjestaja() {
        return idIzvjestaja;
    }

    public void setIdIzvjestaja(int idIzvjestaja) {
        this.idIzvjestaja = idIzvjestaja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIzjava() {
        return izjava.get();
    }

    public SimpleStringProperty izjavaProperty() {
        return izjava;
    }

    public void setIzjava(String izjava) {
        this.izjava.set(izjava);
    }

    @Override
    public String toString() {
        return ime.get() + " " + prezime.get();
    }
}
