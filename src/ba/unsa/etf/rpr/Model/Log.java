package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class Log {
    private int id;
    private SimpleStringProperty prijava, odjava, jedinstvenaSifra;

    public Log(int id, String prijava, String odjava, String jedinstvenaSifra) {
        this.id = id;
        this.prijava = new SimpleStringProperty(prijava);
        this.odjava = new SimpleStringProperty(odjava);
        this.jedinstvenaSifra = new SimpleStringProperty(jedinstvenaSifra);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrijava() {
        return prijava.get();
    }

    public SimpleStringProperty prijavaProperty() {
        return prijava;
    }

    public void setPrijava(String prijava) {
        this.prijava.set(prijava);
    }

    public String getOdjava() {
        return odjava.get();
    }

    public SimpleStringProperty odjavaProperty() {
        return odjava;
    }

    public void setOdjava(String odjava) {
        this.odjava.set(odjava);
    }
}
