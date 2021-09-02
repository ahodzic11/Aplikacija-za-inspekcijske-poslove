package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Izvjestaj {
    private int id, inspektorId, objekatId, svjedok1Id, svjedok2Id;
    private SimpleStringProperty datumInspekcije, opis, prekrsaj, dodatniZahtjevi, uslovZabrane, nedostatak, nazivObjekta, adresaObjekta,
    jedinstvenaSifra;
    private SimpleIntegerProperty novcanaKazna, brojEvidentiranihRadnika, prijavaOKrivicnomDjelu, fitocertifikat,
    uzetUzorak, brojDanaZabrane, prijavljenoRadiliste, brojZaposlenih, brojPotvrdeORadu;

    public Izvjestaj(int id, int inspektorId, String datumInspekcije, String opis, String prekrsaj, int novcanaKazna,
                     String dodatniZahtjevi, int brojEvidentiranihRadnika, int prijavaOKrivicnomDjelu, int fitocertifikat,
                     int uzetUzorak, int brojDanaZabrane, String uslovZabrane, int prijavljenoRadiliste, int brojZaposlenih,
                     int brojPotvrdeORadu, String nedostatak, int svjedok1Id, int svjedok2Id, int objekatId, String nazivObjekta, String adresaObjekta,
                     String jedinstvenaSifra){
        this.id = id;
        this.inspektorId = inspektorId;
        this.datumInspekcije = new SimpleStringProperty(datumInspekcije);
        this.opis = new SimpleStringProperty(opis);
        this.prekrsaj = new SimpleStringProperty(prekrsaj);
        this.novcanaKazna = new SimpleIntegerProperty(novcanaKazna);
        this.dodatniZahtjevi = new SimpleStringProperty(dodatniZahtjevi);
        this.brojEvidentiranihRadnika = new SimpleIntegerProperty(brojEvidentiranihRadnika);
        this.prijavaOKrivicnomDjelu = new SimpleIntegerProperty(prijavaOKrivicnomDjelu);
        this.fitocertifikat = new SimpleIntegerProperty(fitocertifikat);
        this.uzetUzorak = new SimpleIntegerProperty(uzetUzorak);
        this.brojDanaZabrane = new SimpleIntegerProperty(brojDanaZabrane);
        this.uslovZabrane = new SimpleStringProperty(uslovZabrane);
        this.prijavljenoRadiliste = new SimpleIntegerProperty(prijavljenoRadiliste);
        this.brojZaposlenih = new SimpleIntegerProperty(brojZaposlenih);
        this.brojPotvrdeORadu = new SimpleIntegerProperty(brojPotvrdeORadu);
        this.nedostatak = new SimpleStringProperty(nedostatak);
        this.svjedok1Id = svjedok1Id;
        this.svjedok2Id = svjedok2Id;
        this.objekatId = objekatId;
        this.nazivObjekta = new SimpleStringProperty(nazivObjekta);
        this.adresaObjekta = new SimpleStringProperty(adresaObjekta);
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

    public SimpleStringProperty nazivObjektaProperty() {
        return nazivObjekta;
    }

    public SimpleStringProperty adresaObjektaProperty() {
        return adresaObjekta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInspektorId() {
        return inspektorId;
    }

    public void setInspektorId(int inspektorId) {
        this.inspektorId = inspektorId;
    }

    public int getObjekatId() {
        return objekatId;
    }

    public void setObjekatId(int objekatId) {
        this.objekatId = objekatId;
    }

    public int getSvjedok1Id() {
        return svjedok1Id;
    }

    public void setSvjedok1Id(int svjedok1Id) {
        this.svjedok1Id = svjedok1Id;
    }

    public int getSvjedok2Id() {
        return svjedok2Id;
    }

    public void setSvjedok2Id(int svjedok2Id) {
        this.svjedok2Id = svjedok2Id;
    }

    public String getDatumInspekcije() {
        return datumInspekcije.get();
    }

    public SimpleStringProperty datumInspekcijeProperty() {
        return datumInspekcije;
    }

    public void setDatumInspekcije(String datumInspekcije) {
        this.datumInspekcije.set(datumInspekcije);
    }

    public String getOpis() {
        return opis.get();
    }

    public SimpleStringProperty opisProperty() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis.set(opis);
    }

    public String getPrekrsaj() {
        return prekrsaj.get();
    }

    public SimpleStringProperty prekrsajProperty() {
        return prekrsaj;
    }

    public void setPrekrsaj(String prekrsaj) {
        this.prekrsaj.set(prekrsaj);
    }

    public String getDodatniZahtjevi() {
        return dodatniZahtjevi.get();
    }

    public SimpleStringProperty dodatniZahtjeviProperty() {
        return dodatniZahtjevi;
    }

    public void setDodatniZahtjevi(String dodatniZahtjevi) {
        this.dodatniZahtjevi.set(dodatniZahtjevi);
    }

    public String getUslovZabrane() {
        return uslovZabrane.get();
    }

    public SimpleStringProperty uslovZabraneProperty() {
        return uslovZabrane;
    }

    public void setUslovZabrane(String uslovZabrane) {
        this.uslovZabrane.set(uslovZabrane);
    }

    public String getNedostatak() {
        return nedostatak.get();
    }

    public SimpleStringProperty nedostatakProperty() {
        return nedostatak;
    }

    public void setNedostatak(String nedostatak) {
        this.nedostatak.set(nedostatak);
    }

    public int getNovcanaKazna() {
        return novcanaKazna.get();
    }

    public SimpleIntegerProperty novcanaKaznaProperty() {
        return novcanaKazna;
    }

    public void setNovcanaKazna(int novcanaKazna) {
        this.novcanaKazna.set(novcanaKazna);
    }

    public int getBrojEvidentiranihRadnika() {
        return brojEvidentiranihRadnika.get();
    }

    public SimpleIntegerProperty brojEvidentiranihRadnikaProperty() {
        return brojEvidentiranihRadnika;
    }

    public void setBrojEvidentiranihRadnika(int brojEvidentiranihRadnika) {
        this.brojEvidentiranihRadnika.set(brojEvidentiranihRadnika);
    }

    public int getPrijavaOKrivicnomDjelu() {
        return prijavaOKrivicnomDjelu.get();
    }

    public SimpleIntegerProperty prijavaOKrivicnomDjeluProperty() {
        return prijavaOKrivicnomDjelu;
    }

    public void setPrijavaOKrivicnomDjelu(int prijavaOKrivicnomDjelu) {
        this.prijavaOKrivicnomDjelu.set(prijavaOKrivicnomDjelu);
    }

    public int getFitocertifikat() {
        return fitocertifikat.get();
    }

    public SimpleIntegerProperty fitocertifikatProperty() {
        return fitocertifikat;
    }

    public void setFitocertifikat(int fitocertifikat) {
        this.fitocertifikat.set(fitocertifikat);
    }

    public int getUzetUzorak() {
        return uzetUzorak.get();
    }

    public SimpleIntegerProperty uzetUzorakProperty() {
        return uzetUzorak;
    }

    public void setUzetUzorak(int uzetUzorak) {
        this.uzetUzorak.set(uzetUzorak);
    }

    public int getBrojDanaZabrane() {
        return brojDanaZabrane.get();
    }

    public SimpleIntegerProperty brojDanaZabraneProperty() {
        return brojDanaZabrane;
    }

    public void setBrojDanaZabrane(int brojDanaZabrane) {
        this.brojDanaZabrane.set(brojDanaZabrane);
    }

    public int getPrijavljenoRadiliste() {
        return prijavljenoRadiliste.get();
    }

    public SimpleIntegerProperty prijavljenoRadilisteProperty() {
        return prijavljenoRadiliste;
    }

    public void setPrijavljenoRadiliste(int prijavljenoRadiliste) {
        this.prijavljenoRadiliste.set(prijavljenoRadiliste);
    }

    public int getBrojZaposlenih() {
        return brojZaposlenih.get();
    }

    public SimpleIntegerProperty brojZaposlenihProperty() {
        return brojZaposlenih;
    }

    public void setBrojZaposlenih(int brojZaposlenih) {
        this.brojZaposlenih.set(brojZaposlenih);
    }

    public int getBrojPotvrdeORadu() {
        return brojPotvrdeORadu.get();
    }

    public SimpleIntegerProperty brojPotvrdeORaduProperty() {
        return brojPotvrdeORadu;
    }

    public void setBrojPotvrdeORadu(int brojPotvrdeORadu) {
        this.brojPotvrdeORadu.set(brojPotvrdeORadu);
    }

    public String getNazivObjekta() {
        return nazivObjekta.get();
    }

    public void setNazivObjekta(String nazivObjekta) {
        this.nazivObjekta.set(nazivObjekta);
    }

    public String getAdresaObjekta() {
        return adresaObjekta.get();
    }

    public void setAdresaObjekta(String adresaObjekta) {
        this.adresaObjekta.set(adresaObjekta);
    }

    @Override
    public String toString() {
        return nazivObjekta.get() + ", " + adresaObjekta.get();
    }
}
