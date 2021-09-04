package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Report {
    private int id, inspectorId, objectId, firstWitnessId, secondWitnessId;
    private SimpleStringProperty inspectionDate, description, violation, additionalRequirements, openingCondition, defect, objectName, objectAddress,
    jedinstvenaSifra;
    private SimpleIntegerProperty fine, recordedWorkers, criminalOffense, phytocertificate,
            sampleTaken, daysClosed, reportedWorksite, employeesNumber, openingCertificationNumber;

    public Report(int id, int inspectorId, String inspectionDate, String description, String violation, int fine,
                  String additionalRequirements, int recordedWorkers, int criminalOffense, int phytocertificate,
                  int sampleTaken, int daysClosed, String openingCondition, int reportedWorksite, int employeesNumber,
                  int openingCertificationNumber, String defect, int firstWitnessId, int secondWitnessId, int objectId, String objectName, String objectAddress,
                  String jedinstvenaSifra){
        this.id = id;
        this.inspectorId = inspectorId;
        this.inspectionDate = new SimpleStringProperty(inspectionDate);
        this.description = new SimpleStringProperty(description);
        this.violation = new SimpleStringProperty(violation);
        this.fine = new SimpleIntegerProperty(fine);
        this.additionalRequirements = new SimpleStringProperty(additionalRequirements);
        this.recordedWorkers = new SimpleIntegerProperty(recordedWorkers);
        this.criminalOffense = new SimpleIntegerProperty(criminalOffense);
        this.phytocertificate = new SimpleIntegerProperty(phytocertificate);
        this.sampleTaken = new SimpleIntegerProperty(sampleTaken);
        this.daysClosed = new SimpleIntegerProperty(daysClosed);
        this.openingCondition = new SimpleStringProperty(openingCondition);
        this.reportedWorksite = new SimpleIntegerProperty(reportedWorksite);
        this.employeesNumber = new SimpleIntegerProperty(employeesNumber);
        this.openingCertificationNumber = new SimpleIntegerProperty(openingCertificationNumber);
        this.defect = new SimpleStringProperty(defect);
        this.firstWitnessId = firstWitnessId;
        this.secondWitnessId = secondWitnessId;
        this.objectId = objectId;
        this.objectName = new SimpleStringProperty(objectName);
        this.objectAddress = new SimpleStringProperty(objectAddress);
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

    public SimpleStringProperty objectNameProperty() {
        return objectName;
    }

    public SimpleStringProperty objectAddressProperty() {
        return objectAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(int inspectorId) {
        this.inspectorId = inspectorId;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getFirstWitnessId() {
        return firstWitnessId;
    }

    public void setFirstWitnessId(int firstWitnessId) {
        this.firstWitnessId = firstWitnessId;
    }

    public int getSecondWitnessId() {
        return secondWitnessId;
    }

    public void setSecondWitnessId(int secondWitnessId) {
        this.secondWitnessId = secondWitnessId;
    }

    public String getInspectionDate() {
        return inspectionDate.get();
    }

    public SimpleStringProperty inspectionDateProperty() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate.set(inspectionDate);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getViolation() {
        return violation.get();
    }

    public SimpleStringProperty violationProperty() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation.set(violation);
    }

    public String getAdditionalRequirements() {
        return additionalRequirements.get();
    }

    public SimpleStringProperty additionalRequirementsProperty() {
        return additionalRequirements;
    }

    public void setAdditionalRequirements(String additionalRequirements) {
        this.additionalRequirements.set(additionalRequirements);
    }

    public String getOpeningCondition() {
        return openingCondition.get();
    }

    public SimpleStringProperty openingConditionProperty() {
        return openingCondition;
    }

    public void setOpeningCondition(String openingCondition) {
        this.openingCondition.set(openingCondition);
    }

    public String getDefect() {
        return defect.get();
    }

    public SimpleStringProperty defectProperty() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect.set(defect);
    }

    public int getFine() {
        return fine.get();
    }

    public SimpleIntegerProperty fineProperty() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine.set(fine);
    }

    public int getRecordedWorkers() {
        return recordedWorkers.get();
    }

    public SimpleIntegerProperty recordedWorkersProperty() {
        return recordedWorkers;
    }

    public void setRecordedWorkers(int recordedWorkers) {
        this.recordedWorkers.set(recordedWorkers);
    }

    public int getCriminalOffense() {
        return criminalOffense.get();
    }

    public SimpleIntegerProperty criminalOffenseProperty() {
        return criminalOffense;
    }

    public void setCriminalOffense(int criminalOffense) {
        this.criminalOffense.set(criminalOffense);
    }

    public int getPhytocertificate() {
        return phytocertificate.get();
    }

    public SimpleIntegerProperty phytocertificateProperty() {
        return phytocertificate;
    }

    public void setPhytocertificate(int phytocertificate) {
        this.phytocertificate.set(phytocertificate);
    }

    public int getSampleTaken() {
        return sampleTaken.get();
    }

    public SimpleIntegerProperty sampleTakenProperty() {
        return sampleTaken;
    }

    public void setSampleTaken(int sampleTaken) {
        this.sampleTaken.set(sampleTaken);
    }

    public int getDaysClosed() {
        return daysClosed.get();
    }

    public SimpleIntegerProperty daysClosedProperty() {
        return daysClosed;
    }

    public void setDaysClosed(int daysClosed) {
        this.daysClosed.set(daysClosed);
    }

    public int getReportedWorksite() {
        return reportedWorksite.get();
    }

    public SimpleIntegerProperty reportedWorksiteProperty() {
        return reportedWorksite;
    }

    public void setReportedWorksite(int reportedWorksite) {
        this.reportedWorksite.set(reportedWorksite);
    }

    public int getEmployeesNumber() {
        return employeesNumber.get();
    }

    public SimpleIntegerProperty employeesNumberProperty() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber.set(employeesNumber);
    }

    public int getOpeningCertificationNumber() {
        return openingCertificationNumber.get();
    }

    public SimpleIntegerProperty openingCertificationNumberProperty() {
        return openingCertificationNumber;
    }

    public void setOpeningCertificationNumber(int openingCertificationNumber) {
        this.openingCertificationNumber.set(openingCertificationNumber);
    }

    public String getObjectName() {
        return objectName.get();
    }

    public void setObjectName(String objectName) {
        this.objectName.set(objectName);
    }

    public String getObjectAddress() {
        return objectAddress.get();
    }

    public void setObjectAddress(String objectAddress) {
        this.objectAddress.set(objectAddress);
    }

    @Override
    public String toString() {
        return objectName.get() + ", " + objectAddress.get();
    }
}
