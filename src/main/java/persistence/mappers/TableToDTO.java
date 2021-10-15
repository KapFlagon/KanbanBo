package persistence.mappers;

import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;
import persistence.dto.project.ProjectDTO;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectTable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TableToDTO {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public static ProjectDTO mapProjectTableToProjectDTO(ProjectTable projectTable) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setUuid(projectTable.getProject_uuid());
        projectDTO.setTitle(projectTable.getProject_title());
        projectDTO.setDescription(projectTable.getProject_description());
        projectDTO.setStatus(projectTable.getProject_status_id());
        projectDTO.setCreatedOnDate(LocalDateTime.parse(projectTable.getCreation_timestamp()));
        projectDTO.setLastChangedOnDate(LocalDateTime.parse(projectTable.getLast_changed_timestamp()));
        projectDTO.setDueOnDate(LocalDate.parse(projectTable.getDue_on_date()));
        return projectDTO;
    }

    public static ColumnDTO mapColumnTableToColumnDTO(ColumnTable columnTable) {
        ColumnDTO columnDTO = new ColumnDTO();
        columnDTO.setUuid(columnTable.getColumn_uuid());
        columnDTO.setParentProjectUUID(columnTable.getParent_project_uuid());
        columnDTO.setTitle(columnTable.getColumn_title());
        columnDTO.setPosition(columnTable.getColumn_position());
        columnDTO.setFinalColumn(columnTable.isFinal_column());
        return columnDTO;
    }

    public static CardDTO mapCardTableToColumnDTO(CardTable cardTable) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setUuid(cardTable.getID());
        cardDTO.setParentColumnUUID(cardTable.getParent_column_uuid());
        cardDTO.setTitle(cardTable.getCard_title());
        cardDTO.setDescription(cardTable.getCard_description_text());
        cardDTO.setPosition(cardTable.getCard_position());
        cardDTO.setDueOnDate(LocalDate.parse(cardTable.getDue_on_date()));
        return null;
    }


}
