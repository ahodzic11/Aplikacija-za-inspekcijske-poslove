package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Termin {
    private int id, objekatId, inspektorId, zaduzeniInspektorId;
    private SimpleStringProperty datumVrijeme, napomene;
    private SimpleIntegerProperty odrađen;

    public Termin(int id, int objekatId, int inspektorId, String datumVrijeme, String napomene, int odrađen, int zaduzeniInspektorId) {
        this.id = id;
        this.objekatId = objekatId;
        this.inspektorId = inspektorId;
        this.datumVrijeme = new SimpleStringProperty(datumVrijeme);
        this.napomene = new SimpleStringProperty(napomene);
        this.odrađen = new SimpleIntegerProperty(odrađen);
        this.zaduzeniInspektorId = zaduzeniInspektorId;
    }

    public int getZaduzeniInspektorId() {
        return zaduzeniInspektorId;
    }

    public void setZaduzeniInspektorId(int zaduzeniInspektorId) {
        this.zaduzeniInspektorId = zaduzeniInspektorId;
    }

    public int getOdrađen() {
        return odrađen.get();
    }

    public SimpleIntegerProperty odrađenProperty() {
        return odrađen;
    }

    public void setOdrađen(int odrađen) {
        this.odrađen.set(odrađen);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjekatId() {
        return objekatId;
    }

    public void setObjekatId(int objekatId) {
        this.objekatId = objekatId;
    }

    public int getInspektorId() {
        return inspektorId;
    }

    public void setInspektorId(int inspektorId) {
        this.inspektorId = inspektorId;
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

    public String getNapomene() {
        return napomene.get();
    }

    public SimpleStringProperty napomeneProperty() {
        return napomene;
    }

    public void setNapomene(String napomene) {
        this.napomene.set(napomene);
    }

    @Override
    public String toString() {
        return datumVrijeme.get();
    }
}
