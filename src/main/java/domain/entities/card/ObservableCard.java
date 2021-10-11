package domain.entities.card;

import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import persistence.tables.card.CardTable;

import java.util.UUID;

public class ObservableCard extends AbstractObservableCardBase<CardTable> {


    // Variables
    private SimpleIntegerProperty position;




    // Constructors
    public ObservableCard(CardTable domainObject) {
        super(domainObject);
        this.position = new SimpleIntegerProperty(domainObject.getCard_position());
    }
    public ObservableCard(CardTable domainObject, ObservableList<ObservableResourceItem> resourceItems) {
        super(domainObject, resourceItems);
        this.position = new SimpleIntegerProperty(domainObject.getCard_position());
    }

    // Getters and Setters
    public SimpleIntegerProperty positionProperty() {
        return position;
    }
    public void setPosition(int position) {
        this.position.set(position);
    }


    // Initialisation methods



    // Other methods


}
