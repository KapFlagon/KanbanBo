package domain.entities.column;

import persistence.dto.column.AbstractColumnDTO;
import domain.entities.card.AbstractObservableCardBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.entities.AbstractObservableEntity;

import java.util.UUID;

public abstract class AbstractObservableColumnBase<T extends AbstractColumnDTO, U extends AbstractObservableCardBase> extends AbstractObservableEntity<T> {


    // Variables
    protected UUID parentProjectUUID;
    protected UUID columnUUID;
    protected SimpleStringProperty columnTitle;
    private SimpleBooleanProperty finalColumn;
    protected SimpleStringProperty creationTimestamp;
    protected SimpleStringProperty lastChangedTimestamp;
    protected ObservableList<U> cards;

    // Constructors
    public AbstractObservableColumnBase(T columnDTO, ObservableList<U> cardsList) {
        super();
        this.parentProjectUUID = columnDTO.getParentProjectUUID();
        this.columnUUID = columnDTO.getUuid();
        initAllProperties();
        initObservableList(cardsList);
    }


    // Getters and Setters
    public UUID getParentProjectUUID() {
        return parentProjectUUID;
    }

    public void setParentProjectUUID(UUID parentProjectUUID) {
        this.parentProjectUUID = parentProjectUUID;
    }

    public UUID getColumnUUID() {
        return columnUUID;
    }

    public SimpleStringProperty columnTitleProperty() {
        return columnTitle;
    }

    public boolean isFinalColumn() {
        return finalColumn.get();
    }

    public SimpleBooleanProperty finalColumnProperty() {
        return finalColumn;
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
        this.columnTitle = new SimpleStringProperty(domainObject.getTitle());
    }

    protected void initObservableList() {
        this.cards = FXCollections.observableArrayList();
    }

    protected void initObservableList(ObservableList<U> cardsList) {
        this.cards = cardsList;
    }


    // Other methods
}
