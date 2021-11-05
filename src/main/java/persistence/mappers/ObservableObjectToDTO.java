package persistence.mappers;

import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class ObservableObjectToDTO {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public static ColumnDTO mapObservableColumnToColumnDTO(ObservableColumn observableColumn) {
        ColumnDTO columnDTO = new ColumnDTO();
        columnDTO.setUuid(observableColumn.getColumnUUID());
        columnDTO.setParentProjectUUID(observableColumn.getParentProjectUUID());
        columnDTO.setTitle(observableColumn.columnTitleProperty().getValue());
        columnDTO.setPosition(observableColumn.columnPositionProperty().get());
        columnDTO.setFinalColumn(observableColumn.isFinalColumn());
        columnDTO.setCreatedOnTimeStamp(ZonedDateTime.parse(observableColumn.creationTimestampProperty().getValue()));
        columnDTO.setLastChangedOnTimeStamp(ZonedDateTime.parse(observableColumn.lastChangedTimestampProperty().getValue()));
        return columnDTO;
    }

    public static CardDTO mapObservableCardToCardDTO(ObservableCard observableCard) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setParentColumnUUID(observableCard.getParentColumnUUID());
        cardDTO.setUuid(observableCard.getCardUUID());
        cardDTO.setTitle(observableCard.cardTitleProperty().getValue());
        cardDTO.setDescription(observableCard.cardDescriptionProperty().getValue());
        cardDTO.setPosition(observableCard.positionProperty().getValue());
        if(observableCard.creationTimestampProperty() != null) {
            cardDTO.setCreatedOnTimeStamp(ZonedDateTime.parse(observableCard.creationTimestampProperty().getValue()));
        }
        if(observableCard.lastChangedTimestampProperty() != null) {
            cardDTO.setLastChangedOnTimeStamp(ZonedDateTime.parse(observableCard.lastChangedTimestampProperty().getValue()));
        }
        //cardDTO.setDueOnDate();
        return cardDTO;
    }

}
