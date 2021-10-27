package persistence.mappers;

import domain.entities.column.ObservableColumn;
import persistence.dto.column.ColumnDTO;

import java.time.ZonedDateTime;

public class ObservableObjectToDTO {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public static ColumnDTO mapColumnTableToColumnDTO(ObservableColumn observableColumn) {
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

}
