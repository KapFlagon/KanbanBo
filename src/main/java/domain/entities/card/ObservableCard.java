package domain.entities.card;

import javafx.beans.property.SimpleIntegerProperty;
import persistence.tables.card.CardTable;

import java.util.UUID;

public class ObservableCard extends AbstractObservableCardBase<CardTable> {


    // Variables
    private SimpleIntegerProperty position;

    public ObservableCard(CardTable domainObject) {
        super(domainObject);
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
