package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Inspector {
    private int id;
    private SimpleIntegerProperty gender, driversLicense;
    private SimpleStringProperty firstName, lastName, birthdate, residence, email, loginEmail, password, phoneNumber,
    jmbg, idNumber, uniqueId, inspectorType, inspectionArea;

    public Inspector(int id, String firstName, String lastName, String birthdate, String jmbg, int gender, String idNumber, String residence,
                     String phoneNumber, String email, String loginEmail, String password, int driversLicense, String uniqueId, String inspectorType,
                     String inspectionArea){
        this.id = id;
        this.gender = new SimpleIntegerProperty(gender);
        this.idNumber = new SimpleStringProperty(idNumber);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.driversLicense = new SimpleIntegerProperty(driversLicense);
        this.jmbg = new SimpleStringProperty(jmbg);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.birthdate = new SimpleStringProperty(birthdate);
        this.residence = new SimpleStringProperty(residence);
        this.email = new SimpleStringProperty(email);
        this.loginEmail = new SimpleStringProperty(loginEmail);
        this.password = new SimpleStringProperty(password);
        this.uniqueId = new SimpleStringProperty(uniqueId);
        this.inspectorType = new SimpleStringProperty(inspectorType);
        this.inspectionArea = new SimpleStringProperty(inspectionArea);
    }

    public String getInspectionArea() {
        return inspectionArea.get();
    }

    public SimpleStringProperty inspectionAreaProperty() {
        return inspectionArea;
    }

    public void setInspectionArea(String inspectionArea) {
        this.inspectionArea.set(inspectionArea);
    }

    public String getInspectorType() {
        return inspectorType.get();
    }

    public SimpleStringProperty inspectorTypeProperty() {
        return inspectorType;
    }

    public void setInspectorType(String inspectorType) {
        this.inspectorType.set(inspectorType);
    }

    public String getUniqueId() {
        return uniqueId.get();
    }

    public SimpleStringProperty uniqueIdProperty() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId.set(uniqueId);
    }

    @Override
    public String toString() {
        return firstName.get() + " " + lastName.get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender.get();
    }

    public SimpleIntegerProperty genderProperty() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender.set(gender);
    }

    public String getIdNumber() {
        return idNumber.get();
    }

    public SimpleStringProperty idNumberProperty() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber.set(idNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public int getDriversLicense() {
        return driversLicense.get();
    }

    public SimpleIntegerProperty driversLicenseProperty() {
        return driversLicense;
    }

    public void setDriversLicense(int driversLicense) {
        this.driversLicense.set(driversLicense);
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

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getBirthdate() {
        return birthdate.get();
    }

    public SimpleStringProperty birthdateProperty() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate.set(birthdate);
    }

    public String getResidence() {
        return residence.get();
    }

    public SimpleStringProperty residenceProperty() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence.set(residence);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getLoginEmail() {
        return loginEmail.get();
    }

    public SimpleStringProperty loginEmailProperty() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail.set(loginEmail);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}


