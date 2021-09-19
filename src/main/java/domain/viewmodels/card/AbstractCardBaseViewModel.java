package domain.viewmodels.card;

import javafx.beans.property.SimpleStringProperty;
import persistence.tables.card.AbstractCardBaseTable;
import domain.viewmodels.AbstractViewModel;

public abstract class AbstractCardBaseViewModel<T extends AbstractCardBaseTable> extends AbstractViewModel<T> {


    // Variables
    protected SimpleStringProperty cardTitle;
    protected SimpleStringProperty cardDescription;


    // Constructors

    public AbstractCardBaseViewModel(T domainObject) {
        super(domainObject);
    }


    // Getters and Setters


    // Initialisation methods

    @Override
    protected void initAllProperties() {
        cardTitle = new SimpleStringProperty(domainObject.getCard_title());
        cardDescription = new SimpleStringProperty(domainObject.getCard_description_text());
    }

    @Override
    protected void initPropertyListeners() {
        cardTitle.addListener((observable, oldVal, newVal) -> {
            dataChangePending.set(true);
        });
        cardDescription.addListener((observable, oldVal, newVal) -> {
            dataChangePending.set(true);
        });
    }


    // Other methods
    @Override
    protected void pushPropertyDataIntoDomainObject() {
        domainObject.setCard_title(cardTitle.getValue());
        domainObject.setCard_description_text(cardDescription.getValue());
    }

}
