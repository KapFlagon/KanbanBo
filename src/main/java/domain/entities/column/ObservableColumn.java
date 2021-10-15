package domain.entities.column;

import persistence.dto.column.ColumnDTO;
import domain.entities.card.ObservableCard;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class ObservableColumn extends AbstractObservableColumnBase<ColumnDTO, ObservableCard>{


    // Variables
    private SimpleIntegerProperty columnPosition;


    // Constructors
    public ObservableColumn(ColumnDTO columnDTO, ObservableList<ObservableCard> cardsList) {
        super(columnDTO, cardsList);
    }

    // Getters and Setters
    public SimpleIntegerProperty columnPositionProperty() {
        return columnPosition;
    }


    // Initialisation methods
    @Override
    protected void initAllProperties() {
        super.initAllProperties();
        this.columnPosition = new SimpleIntegerProperty();
    }

    protected void initAllProperties(int position) {
        this.columnPosition = new SimpleIntegerProperty(position);
    }

    protected void initAllProperties(ColumnDTO columnDTO) {
        super.initAllProperties(columnDTO);
        this.columnPosition = new SimpleIntegerProperty(columnDTO.getPosition());
    }


    // Other methods


}
