package domain.entities.column;

import domain.entities.card.AbstractObservableCardBase;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistence.tables.column.AbstractColumnBaseTable;
import domain.entities.AbstractObservableEntity;

import java.util.UUID;

public abstract class AbstractObservableColumnBase<T extends AbstractColumnBaseTable, U extends AbstractObservableCardBase> extends AbstractObservableEntity<T> {


    // Variables
    protected UUID parentProjectUUID;
    protected UUID columnUUID;
    protected SimpleStringProperty columnTitle;
    protected ObservableList<U> cards;

    // Constructors

    public AbstractObservableColumnBase() {
        super();
        this.columnUUID = UUID.randomUUID();
        initAllProperties();
        initObservableList();
    }

    public AbstractObservableColumnBase(T domainObject, ObservableList<U> cardsList) {
        super();
        this.parentProjectUUID = domainObject.getParent_project_uuid();
        this.columnUUID = domainObject.getColumn_uuid();
        initAllProperties();
        initObservableList(cardsList);
    }

    public AbstractObservableColumnBase(UUID parentProjectUUID) {
        super();
        this.parentProjectUUID = parentProjectUUID;
        columnUUID = UUID.randomUUID();
        initAllProperties();
        initObservableList();
    }

    public AbstractObservableColumnBase(UUID parentProjectUUID, T domainObject, ObservableList<U> cardsList) {
        super();
        this.parentProjectUUID = parentProjectUUID;
        this.columnUUID = domainObject.getColumn_uuid();
        initAllProperties();
        initObservableList(cardsList);
    }


    // Getters and Setters
    public UUID getParentProjectUUID() {
        return parentProjectUUID;
    }

    public UUID getColumnUUID() {
        return columnUUID;
    }

    public SimpleStringProperty columnTitleProperty() {
        return columnTitle;
    }

    public ObservableList<U> getCards() {
        return cards;
    }

    public void setCards(ObservableList<U> cards) {
        this.cards = cards;
    }

    // Initialisation methods
    protected void initAllProperties() {
        this.columnTitle = new SimpleStringProperty();
    }

    protected void initAllProperties(T domainObject) {
        this.columnTitle = new SimpleStringProperty(domainObject.getColumn_title());
    }

    protected void initObservableList() {
        this.cards = FXCollections.observableArrayList();
    }

    protected void initObservableList(ObservableList<U> cardsList) {
        this.cards = cardsList;
    }


    // Other methods
}
