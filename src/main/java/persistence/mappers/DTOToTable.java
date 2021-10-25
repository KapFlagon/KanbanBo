package persistence.mappers;

import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;
import persistence.dto.project.ProjectDTO;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectTable;

public class DTOToTable {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public static ProjectTable mapProjectDTOToProjectTable(ProjectDTO projectDTO) {
        ProjectTable projectTable = new ProjectTable();
        projectTable.setProject_uuid(projectDTO.getUuid());
        projectTable.setProject_title(projectDTO.getTitle());
        projectTable.setProject_description(projectDTO.getDescription());
        projectTable.setProject_status_id(projectDTO.getStatus());
        if(projectDTO.getCreatedOnTimeStamp() != null) {
            projectTable.setCreation_timestamp(projectDTO.getCreatedOnTimeStamp().toString());
        }
        if(projectDTO.getLastChangedOnTimeStamp() != null) {
            projectTable.setLast_changed_timestamp(projectDTO.getLastChangedOnTimeStamp().toString());
        }
        if(projectDTO.getDueOnDate() != null) {
            projectTable.setDue_on_date(projectDTO.getDueOnDate().toString());
        }
        return projectTable;
    }

    public static ColumnTable mapColumnDTOToColumnTable(ColumnDTO columnDTO) {
        ColumnTable columnTable = new ColumnTable();
        columnTable.setParent_project_uuid(columnDTO.getParentProjectUUID());
        columnTable.setColumn_uuid(columnDTO.getUuid());
        columnTable.setColumn_title(columnDTO.getTitle());
        columnTable.setColumn_position(columnDTO.getPosition());
        columnTable.setFinal_column(columnDTO.isFinalColumn());
        if(columnTable.getCreation_timestamp() != null) {
            columnTable.setCreation_timestamp(columnDTO.getCreatedOnTimeStamp().toString());
        }
        if(columnTable.getLast_changed_timestamp() != null) {
            columnTable.setLast_changed_timestamp(columnDTO.getLastChangedOnTimeStamp().toString());
        }
        return columnTable;
    }

    public static CardTable mapCardDTOToCardTable(CardDTO cardDTO) {
        CardTable cardTable = new CardTable();
        cardTable.setParent_column_uuid(cardDTO.getParentColumnUUID());
        cardTable.setCard_uuid(cardDTO.getUuid());
        cardTable.setCard_title(cardDTO.getTitle());
        cardTable.setCard_description_text(cardDTO.getDescription());
        cardTable.setCard_position(cardDTO.getPosition());
        if(cardTable.getCreation_timestamp() != null) {
            cardTable.setCreation_timestamp(cardDTO.getCreatedOnTimeStamp().toString());
        }
        if(cardTable.getLast_changed_timestamp() != null) {
            cardTable.setLast_changed_timestamp(cardDTO.getLastChangedOnTimeStamp().toString());
        }
        if(cardDTO.getDueOnDate() != null) {
            cardTable.setDue_on_date(cardDTO.getDueOnDate().toString());
        }
        return cardTable;
    }


}
