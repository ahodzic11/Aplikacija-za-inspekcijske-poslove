package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class LogAkcije {
    private int id;
    private SimpleStringProperty datumVrijeme, akcija, jedinstvenaSifra;

    public LogAkcije(int id, String datumVrijeme, String akcija, String jedinstvenaSifra) {
        this.id = id;
        this.datumVrijeme = new SimpleStringProperty(datumVrijeme);
        this.akcija = new SimpleStringProperty(akcija);
        this.jedinstvenaSifra = new SimpleStringProperty(jedinstvenaSifra);
    }

    public String getDatumVrijeme() {
        return datumVrijeme.get();
    }

    public SimpleStringProperty datumVrijemeProperty() {
        return datumVrijeme;
    }

    public void setDatumVrijeme(String datumVrijeme) {
        this.datumVrijeme.set(datumVrijeme);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAkcija() {
        return akcija.get();
    }

    public SimpleStringProperty akcijaProperty() {
        return akcija;
    }

    public void setAkcija(String akcija) {
        this.akcija.set(akcija);
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
