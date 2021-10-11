package domain.entities;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class AbstractObservableEntity<T> {


    // Variables
    private SimpleBooleanProperty editingPermitted;


    // Constructors
    public AbstractObservableEntity() {
        initEditingPermittedProperty();
    }

    // Getters and Setters
    public boolean isEditingPermitted() {
        return editingPermitted.get();
    }
    public SimpleBooleanProperty editingPermittedProperty() {
        return editingPermitted;
    }


    // Initialisation methods
    private void initEditingPermittedProperty() {
        this.editingPermitted = new SimpleBooleanProperty(true);
    }

    // Other methods


}
