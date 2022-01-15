package domain.viewmodels;

import javafx.beans.property.SimpleBooleanProperty;

public abstract class AbstractViewModel<T> {


    // Variables
    protected T domainObject;
    protected SimpleBooleanProperty dataChangePending;


    // Constructors
    public AbstractViewModel() {
        this.dataChangePending = new SimpleBooleanProperty(false);
    }

    public AbstractViewModel(T domainObject) {
        this();
        this.domainObject = domainObject;
    }

    // Getters and Setters
    public boolean isDataChangePending() {
        return dataChangePending.get();
    }
    public SimpleBooleanProperty dataChangePendingProperty() {
        return dataChangePending;
    }


    // Initialisation methods
    protected abstract void initAllProperties();
    protected abstract void initPropertyListeners();

    // Other methods
    public void dataChangesFinished() {
        dataChangePending.set(false);
    }

    public T readyDomainObjectForUpdate() {
        pushPropertyDataIntoDomainObject();
        return domainObject;
    }

    protected abstract void pushPropertyDataIntoDomainObject();
}
