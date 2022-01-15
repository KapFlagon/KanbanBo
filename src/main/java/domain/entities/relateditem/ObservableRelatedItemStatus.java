package domain.entities.relateditem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ObservableRelatedItemStatus {


    // Variables
    private SimpleIntegerProperty statusID;
    private SimpleStringProperty statusText;


    // Constructors
    public ObservableRelatedItemStatus(int statusIDInt, String statusTextString) {
        this.statusID = new SimpleIntegerProperty(statusIDInt);
        this.statusText = new SimpleStringProperty(statusTextString);
    }


    // Getters and Setters
    public int getStatusID() {
        return statusID.get();
    }
    public SimpleIntegerProperty statusIDProperty() {
        return statusID;
    }

    public String getStatusText() {
        return statusText.get();
    }
    public SimpleStringProperty statusTextProperty() {
        return statusText;
    }


    // Initialisation methods


    // Other methods


}
