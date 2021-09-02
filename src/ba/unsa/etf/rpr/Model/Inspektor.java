package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Inspektor {
    private int id;
    private SimpleIntegerProperty spol, vozacka;
    private SimpleStringProperty ime, prezime, datumRodjenja, mjestoPrebivalista, personalniEmail, pristupniEmail, pristupnaSifra, kontaktTelefon,
    jmbg, brojLicne, jedinstvenaSifra, tipInspektora, oblastInspektora;

    public Inspektor(int id, String ime, String prezime, String datumRodjenja, String jmbg, int spol, String brojLicne, String mjestoPrebivalista,
    String kontaktTelefon, String personalniEmail, String pristupniEmail, String pristupnaSifra, int vozacka, String jedinstvenaSifra, String tipInspektora,
                     String oblastInspektora){
        this.id = id;
        this.spol = new SimpleIntegerProperty(spol);
        this.brojLicne = new SimpleStringProperty(brojLicne);
        this.kontaktTelefon = new SimpleStringProperty(kontaktTelefon);
        this.vozacka = new SimpleIntegerProperty(vozacka);
        this.jmbg = new SimpleStringProperty(jmbg);
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.datumRodjenja = new SimpleStringProperty(datumRodjenja);
        this.mjestoPrebivalista = new SimpleStringProperty(mjestoPrebivalista);
        this.personalniEmail = new SimpleStringProperty(personalniEmail);
        this.pristupniEmail = new SimpleStringProperty(pristupniEmail);
        this.pristupnaSifra = new SimpleStringProperty(pristupnaSifra);
        this.jedinstvenaSifra = new SimpleStringProperty(jedinstvenaSifra);
        this.tipInspektora = new SimpleStringProperty(tipInspektora);
        this.oblastInspektora = new SimpleStringProperty(oblastInspektora);
    }

    public String getOblastInspektora() {
        return oblastInspektora.get();
    }

    public SimpleStringProperty oblastInspektoraProperty() {
        return oblastInspektora;
    }

    public void setOblastInspektora(String oblastInspektora) {
        this.oblastInspektora.set(oblastInspektora);
    }

    public String getTipInspektora() {
        return tipInspektora.get();
    }

    public SimpleStringProperty tipInspektoraProperty() {
        return tipInspektora;
    }

    public void setTipInspektora(String tipInspektora) {
        this.tipInspektora.set(tipInspektora);
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

    public int getSpol() {
        return spol.get();
    }

    public SimpleIntegerProperty spolProperty() {
        return spol;
    }

    public void setSpol(int spol) {
        this.spol.set(spol);
    }

    public String getBrojLicne() {
        return brojLicne.get();
    }

    public SimpleStringProperty brojLicneProperty() {
        return brojLicne;
    }

    public void setBrojLicne(String brojLicne) {
        this.brojLicne.set(brojLicne);
    }

    public String getKontaktTelefon() {
        return kontaktTelefon.get();
    }

    public SimpleStringProperty kontaktTelefonProperty() {
        return kontaktTelefon;
    }

    public void setKontaktTelefon(String kontaktTelefon) {
        this.kontaktTelefon.set(kontaktTelefon);
    }

    public int getVozacka() {
        return vozacka.get();
    }

    public SimpleIntegerProperty vozackaProperty() {
        return vozacka;
    }

    public void setVozacka(int vozacka) {
        this.vozacka.set(vozacka);
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

    public String getDatumRodjenja() {
        return datumRodjenja.get();
    }

    public SimpleStringProperty datumRodjenjaProperty() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja.set(datumRodjenja);
    }

    public String getMjestoPrebivalista() {
        return mjestoPrebivalista.get();
    }

    public SimpleStringProperty mjestoPrebivalistaProperty() {
        return mjestoPrebivalista;
    }

    public void setMjestoPrebivalista(String mjestoPrebivalista) {
        this.mjestoPrebivalista.set(mjestoPrebivalista);
    }

    public String getPersonalniEmail() {
        return personalniEmail.get();
    }

    public SimpleStringProperty personalniEmailProperty() {
        return personalniEmail;
    }

    public void setPersonalniEmail(String personalniEmail) {
        this.personalniEmail.set(personalniEmail);
    }

    public String getPristupniEmail() {
        return pristupniEmail.get();
    }

    public SimpleStringProperty pristupniEmailProperty() {
        return pristupniEmail;
    }

    public void setPristupniEmail(String pristupniEmail) {
        this.pristupniEmail.set(pristupniEmail);
    }

    public String getPristupnaSifra() {
        return pristupnaSifra.get();
    }

    public SimpleStringProperty pristupnaSifraProperty() {
        return pristupnaSifra;
    }

    public void setPristupnaSifra(String pristupnaSifra) {
        this.pristupnaSifra.set(pristupnaSifra);
    }
}


