package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class Witness {
    private int id, reportId;
    private SimpleStringProperty name, surename, jmbg, statement;


    public Witness(int id, int reportId, String name, String surename, String jmbg, String statement) {
        this.id = id;
        this.reportId = reportId;
        this.name = new SimpleStringProperty(name);
        this.surename = new SimpleStringProperty(surename);
        this.jmbg = new SimpleStringProperty(jmbg);
        this.statement = new SimpleStringProperty(statement);
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurename() {
        return surename.get();
    }

    public SimpleStringProperty surenameProperty() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename.set(surename);
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

    public String getStatement() {
        return statement.get();
    }

    public SimpleStringProperty statementProperty() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement.set(statement);
    }

    @Override
    public String toString() {
        return name.get() + " " + surename.get();
    }
}
