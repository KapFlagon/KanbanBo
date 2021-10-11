package domain.entities.card;

import domain.entities.AbstractObservableEntity;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistence.tables.card.AbstractCardBaseTable;

import java.util.UUID;

public abstract class AbstractObservableCardBase<T extends AbstractCardBaseTable> extends AbstractObservableEntity<T> {


    // Variables
    private UUID parentColumnUUID;
    private UUID cardUUID;
    private SimpleStringProperty cardTitle;
    private SimpleStringProperty cardDescription;
    protected ObservableList<ObservableResourceItem> resourceItems;

    // Constructors
    public AbstractObservableCardBase(T domainObject) {
        super();
        this.parentColumnUUID = domainObject.getParent_column_uuid();
        this.cardUUID = domainObject.getCard_uuid();
        initAllProperties(domainObject);
    }

    public AbstractObservableCardBase(T domainObject, ObservableList<ObservableResourceItem> resourceItems) {
        super();
        this.parentColumnUUID = domainObject.getParent_column_uuid();
        this.cardUUID = domainObject.getCard_uuid();
        this.resourceItems = resourceItems;
        initAllProperties(domainObject);
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

    public ObservableList<ObservableResourceItem> getResourceItems() {
        return resourceItems;
    }
    public void setResourceItems(ObservableList<ObservableResourceItem> resourceItems) {
        this.resourceItems = resourceItems;
    }


    // Initialisation methods


    // Other methods

    protected void initAllProperties(T domainObject) {
        this.cardTitle = new SimpleStringProperty(domainObject.getCard_title());
        this.cardDescription = new SimpleStringProperty(domainObject.getCard_description_text());
    }

    protected void initObservableList() {
        this.resourceItems = FXCollections.observableArrayList();
    }

    protected void initObservableList(ObservableList<ObservableResourceItem> resourceItemsList) {
        this.resourceItems = resourceItemsList;
    }




}
