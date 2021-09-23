package domain.entities.card;

import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import persistence.tables.card.CardTable;

import java.util.UUID;

public class ObservableCard extends AbstractObservableCardBase<CardTable> {


    // Variables
    private SimpleIntegerProperty position;

    public ObservableCard(CardTable domainObject) {
        super(domainObject);
    }

    public ObservableCard(CardTable domainObject, ObservableList<ObservableResourceItem> resourceItems) {
        super(domainObject, resourceItems);
    }


    // Constructors


    // Getters and Setters
    public SimpleIntegerProperty positionProperty() {
        return position;
    }
    public void setPosition(int position) {
        this.position.set(position);
    }


    // Initialisation methods

    @Override
    protected void initPropertyListeners() {
        super.initPropertyListeners();
        position.addListener(numberChangeListener);
    }


    // Other methods


}
