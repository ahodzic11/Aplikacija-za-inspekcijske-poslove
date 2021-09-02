package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class Objekat {
    private int id, vlasnikId;
    private SimpleStringProperty nazivObjekta, adresa, vrstaObjekta;

    public Objekat(int id, int vlasnikId, String nazivObjekta, String adresa, String vrsta) {
        this.id = id;
        this.vlasnikId = vlasnikId;
        this.nazivObjekta = new SimpleStringProperty(nazivObjekta);
        this.adresa = new SimpleStringProperty(adresa);
        this.vrstaObjekta = new SimpleStringProperty(vrsta);
    }

    public String getVrstaObjekta() {
        return vrstaObjekta.get();
    }

    public SimpleStringProperty vrstaObjektaProperty() {
        return vrstaObjekta;
    }

    public void setVrstaObjekta(String vrstaObjekta) {
        this.vrstaObjekta.set(vrstaObjekta);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nazivObjekta.get() + ", " + adresa.get();
    }

    public int getVlasnikId() {
        return vlasnikId;
    }

    public void setVlasnikId(int vlasnikId) {
        this.vlasnikId = vlasnikId;
    }

    public String getNazivObjekta() {
        return nazivObjekta.get();
    }

    public SimpleStringProperty nazivObjektaProperty() {
        return nazivObjekta;
    }

    public void setNazivObjekta(String nazivObjekta) {
        this.nazivObjekta.set(nazivObjekta);
    }

    public String getAdresa() {
        return adresa.get();
    }

    public SimpleStringProperty adresaProperty() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa.set(adresa);
    }
}
