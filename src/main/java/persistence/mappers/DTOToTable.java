package persistence.mappers;

import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;
import persistence.dto.project.ProjectDTO;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectTable;

import java.util.UUID;

public class DTOToTable {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public static ProjectTable mapProjectDTOToProjectTable(ProjectDTO projectDTO) {
        ProjectTable projectTable = new ProjectTable();
        projectTable.setProject_uuid(UUID.fromString(projectDTO.getUuid()));
        projectTable.setProject_title(projectDTO.getTitle());
        projectTable.setProject_description(projectDTO.getDescription());
        projectTable.setProject_status_id(projectDTO.getStatusId());
        projectTable.setCreation_timestamp(projectDTO.getCreatedOnTimeStamp().toString());
        projectTable.setLast_changed_timestamp(projectDTO.getLastChangedOnTimeStamp().toString());
        projectTable.setDue_on_date(projectDTO.getDueOnDate().toString());
        return projectTable;
    }

    public static ColumnTable mapColumnDTOToColumnTable(ColumnDTO columnDTO) {
        ColumnTable columnTable = new ColumnTable();
        columnTable.setParent_project_uuid(UUID.fromString(columnDTO.getParentProjectUUID()));
        columnTable.setColumn_uuid(UUID.fromString(columnDTO.getUuid()));
        columnTable.setColumn_title(columnDTO.getTitle());
        columnTable.setColumn_position(columnDTO.getPosition());
        columnTable.setFinal_column(columnDTO.isFinalColumn());
        columnTable.setCreation_timestamp(columnDTO.getCreatedOnTimeStamp().toString());
        columnTable.setLast_changed_timestamp(columnDTO.getLastChangedOnTimeStamp().toString());
        return columnTable;
    }

    public static CardTable mapCardDTOToCardTable(CardDTO cardDTO) {
        CardTable cardTable = new CardTable();
        cardTable.setParent_column_uuid(UUID.fromString(cardDTO.getParentColumnUUID()));
        cardTable.setCard_uuid(UUID.fromString(cardDTO.getUuid()));
        cardTable.setCard_title(cardDTO.getTitle());
        cardTable.setCard_description_text(cardDTO.getDescription());
        cardTable.setCard_position(cardDTO.getPosition());
        cardTable.setCreation_timestamp(cardDTO.getCreatedOnTimeStamp().toString());
        cardTable.setLast_changed_timestamp(cardDTO.getLastChangedOnTimeStamp().toString());
        cardTable.setDue_on_date(cardDTO.getDueOnDate().toString());
        return cardTable;
    }


}