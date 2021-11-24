package persistence.mappers;

import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;
import persistence.dto.project.ProjectDTO;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectTable;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class TableToDTO {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public static ProjectDTO mapProjectTableToProjectDTO(ProjectTable projectTable) {
        ProjectDTO.Builder builder = ProjectDTO.Builder.newInstance()
                .uuid(projectTable.getProject_uuid().toString())
                .title(projectTable.getProject_title())
                .description(projectTable.getProject_description())
                .statusId(projectTable.getProject_status_id())
                .createdOnTimeStamp(projectTable.getCreation_timestamp())
                .lastChangedOnTimeStamp(projectTable.getLast_changed_timestamp())
                .dueOnDate(projectTable.getDue_on_date());
        ProjectDTO projectDTO = new ProjectDTO(builder);
        return projectDTO;
    }

    public static ColumnDTO mapColumnTableToColumnDTO(ColumnTable columnTable) {
        ColumnDTO.Builder builder = ColumnDTO.Builder.newInstance(columnTable.getParent_project_uuid().toString())
                .uuid(columnTable.getColumn_uuid().toString())
                .title(columnTable.getColumn_title())
                .position(columnTable.getColumn_position())
                .finalColumn(columnTable.isFinal_column())
                .createdOnTimeStamp(columnTable.getCreation_timestamp())
                .lastChangedOnTimeStamp(columnTable.getLast_changed_timestamp());
        ColumnDTO columnDTO = new ColumnDTO(builder);
        return columnDTO;
    }

    public static CardDTO mapCardTableToCardDTO(CardTable cardTable) {
        CardDTO.Builder builder = CardDTO.Builder.newInstance(cardTable.getParent_column_uuid().toString())
                .uuid(cardTable.getID().toString())
                .title(cardTable.getCard_title())
                .description(cardTable.getCard_description_text())
                .position(cardTable.getCard_position())
                .createdOnTimeStamp(cardTable.getCreation_timestamp())
                .lastChangedOnTimeStamp(cardTable.getLast_changed_timestamp())
                .dueOnDate(cardTable.getDue_on_date());
        CardDTO cardDTO = new CardDTO(builder);
        return cardDTO;
    }


}