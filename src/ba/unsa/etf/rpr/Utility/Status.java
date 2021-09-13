package ba.unsa.etf.rpr.Utility;

import javafx.beans.property.SimpleStringProperty;

public class Status {
    private static Status instance = null;
    private SimpleStringProperty status = new SimpleStringProperty();

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }


    public static Status getInstance(){
        if(instance==null) instance = new Status();
        return instance;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
