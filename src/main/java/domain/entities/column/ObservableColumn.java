package domain.entities.column;

import domain.entities.card.ObservableCard;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.tables.column.ColumnTable;

import java.util.UUID;

public class ObservableColumn extends AbstractObservableColumnBase<ColumnTable, ObservableCard>{


    // Variables
    private SimpleIntegerProperty columnPosition;
    private SimpleBooleanProperty finalColumn;

    // Constructors

    public ObservableColumn(UUID parentProjectUUID) {
        super(parentProjectUUID);
    }

    public ObservableColumn(UUID parentProjectUUID, int position) {
        initializeBaseProperties();
        initAllProperties(position);
    }

    public ObservableColumn(ColumnTable domainObject, ObservableList<ObservableCard> cardsList) {
        super(domainObject, cardsList);
    }

    public ObservableColumn(UUID parentProjectUUID, ColumnTable domainObject, ObservableList<ObservableCard> cardsList) {
        super(parentProjectUUID, domainObject, cardsList);
    }

    // Getters and Setters
    public SimpleIntegerProperty columnPositionProperty() {
        return columnPosition;
    }

    public boolean isFinalColumn() {
        return finalColumn.get();
    }

    public SimpleBooleanProperty finalColumnProperty() {
        return finalColumn;
    }


    // Initialisation methods

    @Override
    protected void initAllProperties() {
        super.initAllProperties();
        this.columnPosition = new SimpleIntegerProperty();
        this.finalColumn = new SimpleBooleanProperty(false);
    }

    protected void initAllProperties(int position) {
        this.columnPosition = new SimpleIntegerProperty(position);
        this.finalColumn = new SimpleBooleanProperty(false);
    }

    protected void initAllProperties(ColumnTable domainObject) {
        super.initAllProperties(domainObject);
        this.columnPosition = new SimpleIntegerProperty(domainObject.getColumn_position());
        this.finalColumn = new SimpleBooleanProperty(domainObject.isFinal_column());
    }



    @Override
    protected void initPropertyListeners() {
        super.initPropertyListeners();
        this.columnPosition.addListener(numberChangeListener);
        this.finalColumn.addListener(booleanChangeListener);
    }


    // Other methods


}
