package domain.entities.card;

import persistence.dto.card.CardDTO;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class ObservableCard extends AbstractObservableCardBase<CardDTO> {


    // Variables
    private SimpleIntegerProperty position;
    private SimpleStringProperty dueOnDate;



    // Constructors
    public ObservableCard(CardDTO cardDTO) {
        super(cardDTO);
        this.position = new SimpleIntegerProperty(cardDTO.getPosition());
    }
    public ObservableCard(CardDTO cardDTO, ObservableList<ObservableResourceItem> resourceItems) {
        super(cardDTO, resourceItems);
        this.position = new SimpleIntegerProperty(cardDTO.getPosition());
        if(cardDTO.getDueOnDate() != null) {
            this.dueOnDate = new SimpleStringProperty(cardDTO.getDueOnDate().toString());
        }
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
