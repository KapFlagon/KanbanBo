package domain.entities;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class AbstractObservableEntity<T> {


    // Variables
    private SimpleBooleanProperty dataChangePending;
    private SimpleBooleanProperty editingPermitted;
    protected ChangeListener<String> stringChangeListener;
    protected ChangeListener<Number> numberChangeListener;
    protected ChangeListener<Boolean> booleanChangeListener;


    // Constructors
    public AbstractObservableEntity() {
        initializeBaseProperties();
        initStringChangeListener();
        initNumberChangeListener();
        initBooleanChangeListener();
    }

    // Getters and Setters
    public boolean isDataChangePending() {
        return dataChangePending.get();
    }
    public SimpleBooleanProperty dataChangePendingProperty() {
        return dataChangePending;
    }

    public boolean isEditingPermitted() {
        return editingPermitted.get();
    }

    public SimpleBooleanProperty editingPermittedProperty() {
        return editingPermitted;
    }


    // Initialisation methods
    private void initDataChangeProperty() {
        this.dataChangePending = new SimpleBooleanProperty(false);
    }

    private void initEditingPermittedProperty() {
        this.editingPermitted = new SimpleBooleanProperty(true);
    }

    protected void initializeBaseProperties() {
        initDataChangeProperty();
        initEditingPermittedProperty();
    }

    // Other methods
    protected void initStringChangeListener() {
        stringChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                dataChangePendingProperty().set(true);
            }
        };
    }

    protected void initNumberChangeListener() {
        numberChangeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                dataChangePendingProperty().set(true);
            }
        };
    }

    protected void initBooleanChangeListener() {
        booleanChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                dataChangePendingProperty().set(true);
            }
        };
    }

}
