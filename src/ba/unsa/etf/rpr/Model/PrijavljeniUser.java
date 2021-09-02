package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class PrijavljeniUser {
    private int id, ulogovan;
    private SimpleStringProperty datumLogovanja, jedinstvenaSifra;

    public PrijavljeniUser(int id, String datumLogovanja, int ulogovan, String jedinstvenaSifra) {
        this.id = id;
        this.datumLogovanja = new SimpleStringProperty(datumLogovanja);
        this.ulogovan = ulogovan;
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

    public String getDatumLogovanja() {
        return datumLogovanja.get();
    }

    public SimpleStringProperty datumLogovanjaProperty() {
        return datumLogovanja;
    }

    public void setDatumLogovanja(String datumLogovanja) {
        this.datumLogovanja.set(datumLogovanja);
    }

    public int getUlogovan() {
        return ulogovan;
    }

    public void setUlogovan(int ulogovan) {
        this.ulogovan = ulogovan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
