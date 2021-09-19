package domain.viewmodels.column;

import javafx.beans.property.SimpleStringProperty;
import persistence.tables.column.AbstractColumnBaseTable;
import domain.viewmodels.AbstractViewModel;

public abstract class AbstractColumnBaseViewModel<T extends AbstractColumnBaseTable> extends AbstractViewModel<T> {


    // Variables
    protected SimpleStringProperty columnTitle;

    // Constructors
    public AbstractColumnBaseViewModel(T domainObject) {
        super(domainObject);
    }


    // Getters and Setters


    // Initialisation methods
    @Override
    protected void initAllProperties() {
        this.columnTitle = new SimpleStringProperty(domainObject.getColumn_title());
    }

    @Override
    protected void initPropertyListeners() {
        columnTitle.addListener(((observable, oldValue, newValue) -> {
            this.dataChangePending.set(true);
        }));
    }


    // Other methods
    @Override
    protected void pushPropertyDataIntoDomainObject() {
        domainObject.setColumn_title(columnTitle.getValue());
    }

}
