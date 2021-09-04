package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class Object {
    private int id, ownerId;
    private SimpleStringProperty name, address, type;

    public Object(int id, int ownerId, String name, String address, String type) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.type = new SimpleStringProperty(type);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name.get() + ", " + address.get();
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}
