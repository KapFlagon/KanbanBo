package domain.entities.card;

import persistence.dto.card.AbstractCardDTO;
import domain.entities.AbstractObservableEntity;
import domain.entities.relateditem.ObservableRelatedItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.UUID;

public abstract class AbstractObservableCardBase<T extends AbstractCardDTO> extends AbstractObservableEntity<T> {


    // Variables
    private UUID parentColumnUUID;
    private UUID cardUUID;
    private SimpleStringProperty cardTitle;
    private SimpleStringProperty cardDescription;
    protected SimpleStringProperty creationTimestamp;
    protected SimpleStringProperty lastChangedTimestamp;

    protected ObservableList<ObservableRelatedItem> resourceItems;

    // Constructors
    public AbstractObservableCardBase(T cardDTO) {
        super();
        this.parentColumnUUID = UUID.fromString(cardDTO.getParentColumnUUID());
        this.cardUUID = UUID.fromString(cardDTO.getUuid());
        this.resourceItems = FXCollections.observableArrayList();
        initAllProperties(cardDTO);
    }

    public AbstractObservableCardBase(T cardDTO, ObservableList<ObservableRelatedItem> resourceItems) {
        super();
        this.parentColumnUUID = UUID.fromString(cardDTO.getParentColumnUUID());
        this.cardUUID = UUID.fromString(cardDTO.getUuid());
        this.resourceItems = resourceItems;
        initAllProperties(cardDTO);
    }


    // Getters and Setters
    public UUID getParentColumnUUID() {
        return parentColumnUUID;
    }

    public void setParentColumnUUID(UUID parentColumnUUID) {
        this.parentColumnUUID = parentColumnUUID;
    }

    public UUID getCardUUID() {
        return cardUUID;
    }

    public void setCardUUID(UUID cardUUID) {
        this.cardUUID = cardUUID;
    }

    public SimpleStringProperty cardTitleProperty() {
        return cardTitle;
    }
    public void setCardTitle(String cardTitle) {
        this.cardTitle.set(cardTitle);
    }

    public SimpleStringProperty cardDescriptionProperty() {
        return cardDescription;
    }
    public void setCardDescription(String cardDescription) {
        this.cardDescription.set(cardDescription);
    }

    public String getCreationTimestamp() {
        return creationTimestamp.get();
    }
    public SimpleStringProperty creationTimestampProperty() {
        return creationTimestamp;
    }

    public String getLastChangedTimestamp() {
        return lastChangedTimestamp.get();
    }
    public SimpleStringProperty lastChangedTimestampProperty() {
        return lastChangedTimestamp;
    }

    public ObservableList<ObservableRelatedItem> getResourceItems() {
        return resourceItems;
    }
    public void setResourceItems(ObservableList<ObservableRelatedItem> resourceItems) {
        this.resourceItems = resourceItems;
    }


    // Initialisation methods


    // Other methods
    protected void initAllProperties(T cardDTO) {
        this.cardTitle = new SimpleStringProperty(cardDTO.getTitle());
        this.cardDescription = new SimpleStringProperty(cardDTO.getDescription());
    }





}
