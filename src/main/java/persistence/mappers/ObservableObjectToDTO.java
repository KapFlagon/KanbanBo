package persistence.mappers;

import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;

public class ObservableObjectToDTO {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public static ColumnDTO mapObservableColumnToColumnDTO(ObservableColumn observableColumn) {
        ColumnDTO.Builder builder = ColumnDTO.Builder.newInstance(observableColumn.getParentProjectUUID().toString())
                .uuid(observableColumn.getColumnUUID().toString())
                .title(observableColumn.columnTitleProperty().getValue())
                .position(observableColumn.columnPositionProperty().get())
                .finalColumn(observableColumn.isFinalColumn())
                .createdOnTimeStamp(observableColumn.creationTimestampProperty().getValue())
                .lastChangedOnTimeStamp(observableColumn.lastChangedTimestampProperty().getValue());
        ColumnDTO columnDTO = new ColumnDTO(builder);
        return columnDTO;
    }

    public static CardDTO mapObservableCardToCardDTO(ObservableCard observableCard) {
        CardDTO.Builder builder = CardDTO.Builder.newInstance(observableCard.getParentColumnUUID().toString())
                .uuid(observableCard.getCardUUID().toString())
                .title(observableCard.cardTitleProperty().getValue())
                .description(observableCard.cardDescriptionProperty().getValue())
                .position(observableCard.positionProperty().getValue())
                .createdOnTimeStamp(observableCard.creationTimestampProperty().getValue())
                .lastChangedOnTimeStamp(observableCard.lastChangedTimestampProperty().getValue());
                //.dueOnDate();
        CardDTO cardDTO = new CardDTO(builder);
        return cardDTO;
    }

}