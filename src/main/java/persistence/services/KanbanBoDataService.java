package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.*;
import persistence.dto.RelatedItemDTO;
import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;
import persistence.dto.project.ProjectDTO;
import domain.entities.project.ObservableWorkspaceProject;
import domain.entities.column.ObservableColumn;
import domain.entities.card.ObservableCard;
import domain.entities.relateditem.ObservableRelatedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.relateditems.RelatedItemTable;
import persistence.tables.relateditems.RelatedItemTypeTable;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class KanbanBoDataService extends AbstractService{


    // Variables
    private final Locale locale;
    private final ResourceBundle resourceBundle;
    private ObservableList<ObservableWorkspaceProject> projectsList;
    private ObservableList<ObservableWorkspaceProject> workspaceProjectsList;

    private Dao<ProjectTable, UUID> projectDao;
    private Dao<ProjectStatusTable, Integer> projectStatusDao;
    private Dao<ColumnTable, UUID> columnDao;
    private Dao<CardTable, UUID> cardDao;
    private Dao<RelatedItemTable, UUID> resourceItemDao;
    private Dao<RelatedItemTypeTable, Integer> resourceItemTypeDao;

    private QueryBuilder<ColumnTable, UUID> columnTableQueryBuilder;
    private QueryBuilder<CardTable, UUID> cardTableQueryBuilder;
    private QueryBuilder<RelatedItemTable, UUID> resourceItemTableQueryBuilder;
    private QueryBuilder<RelatedItemTypeTable, Integer> resourceItemTypeTableQueryBuilder;

    private DeleteBuilder<ColumnTable, UUID> columnTableDeleteBuilder;
    private DeleteBuilder<CardTable, UUID> cardTableDeleteBuilder;
    private DeleteBuilder<RelatedItemTable, UUID> resourceItemTableDeleteBuilder;

    private ProjectService projectService;
    private ColumnService columnService;
    private CardService cardService;
    private RelatedItemService relatedItemService;



    // Constructors

    public KanbanBoDataService() throws SQLException, IOException {
        locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        projectsList = FXCollections.observableArrayList();
        workspaceProjectsList = FXCollections.observableArrayList();
        projectService = new ProjectService(locale, resourceBundle, projectsList, workspaceProjectsList);
        columnService = new ColumnService(locale, resourceBundle, workspaceProjectsList);
        cardService = new CardService(locale, resourceBundle, workspaceProjectsList);
        relatedItemService = new RelatedItemService(locale, resourceBundle, workspaceProjectsList);
    }


    // Getters and Setters
    public ObservableList<ObservableWorkspaceProject> getProjectsList() {
        return projectsList;
    }

    public ObservableList<ObservableWorkspaceProject> getRelatedItemProjectSubList(UUID prospectiveParentUUID) throws SQLException, IOException {
        return projectService.getRelatedItemProjectSubList(prospectiveParentUUID);
    }

    public ObservableList<ObservableWorkspaceProject> getWorkspaceProjectsList() {
        return workspaceProjectsList;
    }



    // Initialisation methods


    // Other methods
    public void createProject(ProjectDTO projectDTO) throws SQLException, IOException {
        projectService.createProject(projectDTO);
    }

    public void updateProject(ProjectDTO projectDTO, ObservableWorkspaceProject observableWorkspaceProject) throws ParseException, SQLException, IOException {
        projectService.updateProject(projectDTO, observableWorkspaceProject);
    }

    public void deleteProject(ObservableWorkspaceProject observableWorkspaceProject) throws ParseException, SQLException, IOException {
        projectService.deleteProject(observableWorkspaceProject);
    }

    private void copyProject(ObservableWorkspaceProject originalProject) throws SQLException, ParseException, IOException {
        projectService.copyProject(originalProject);
    }

    public List getProjectStatusTableAsList() throws SQLException, IOException {
        return projectService.getProjectStatusTableAsList();
    }

    public void openProjectInWorkspace(UUID projectUUID) throws SQLException, IOException {
        projectService.openProjectInWorkspace(projectUUID);
    }

    public void createColumn(ColumnDTO columnDTO) throws SQLException, IOException {
        columnService.createColumn(columnDTO);
    }

    public void updateColumn(ColumnDTO columnDTO, ObservableColumn observableColumn) throws ParseException, SQLException, IOException {
        columnService.updateColumn(columnDTO, observableColumn);
    }

    public void updateColumns(List<ColumnDTO> columnDTOList, ObservableList<ObservableColumn> observableColumns) throws SQLException, IOException {
        columnService.updateColumns(columnDTOList, observableColumns);
    }

    public ObservableList<ObservableColumn> getRelatedColumns(UUID columnUUID) {
        return columnService.getRelatedColumns(columnUUID);
    }

    public void moveColumn(ColumnDTO newColumnDataDTO, ObservableColumn oldObservableColumn) throws SQLException, IOException {
        columnService.moveColumn(newColumnDataDTO, oldObservableColumn);
    }

    public void deleteColumn(ObservableColumn column) throws SQLException, IOException {
        columnService.deleteColumn(column);
    }


    public void createCard(CardDTO cardDTO) throws SQLException, IOException {
        cardService.createCard(cardDTO);
    }

    public void updateCard(CardDTO cardDTO, ObservableCard observableCard) throws SQLException, IOException {
        cardService.updateCard(cardDTO, observableCard);
    }

    public void moveCard(CardDTO newCardDataDTO, ObservableCard oldObservableCard) throws SQLException, IOException {
        cardService.moveCard(newCardDataDTO, oldObservableCard);
    }

    public void deleteCard(ObservableCard card) throws SQLException, IOException {
        cardService.deleteCard(card);
    }


    public List getRelatedItemTypeTableAsList() throws SQLException, IOException {
        return relatedItemService.getRelatedItemTypeTableAsList();
    }

    public void createProjectRelatedItem(RelatedItemDTO newRelatedItemDTO) throws SQLException, IOException {
        relatedItemService.createProjectRelatedItem(newRelatedItemDTO);
    }

    public void updateProjectRelatedItem(RelatedItemDTO relatedItemDTO, ObservableRelatedItem observableRelatedItem) throws SQLException, IOException {
        relatedItemService.updateProjectRelatedItem(relatedItemDTO, observableRelatedItem);
    }

    public void deleteProjectRelatedItem(ObservableRelatedItem observableRelatedItem) throws SQLException, IOException {
        relatedItemService.deleteProjectRelatedItem(observableRelatedItem);
    }

    public void createCardRelatedItem(RelatedItemDTO newRelatedItemDTO) throws SQLException, IOException {
        relatedItemService.createCardRelatedItem(newRelatedItemDTO);
    }

    public void updateCardRelatedItem(RelatedItemDTO relatedItemDTO, ObservableRelatedItem observableRelatedItem) throws SQLException, IOException {
        relatedItemService.updateCardRelatedItem(relatedItemDTO, observableRelatedItem);
    }

    public void deleteCardRelatedItem(ObservableRelatedItem observableRelatedItem) throws SQLException, IOException {
        relatedItemService.deleteCardRelatedItem(observableRelatedItem);
    }


    private UUID getParentProjectUUIDOfCard(UUID cardUUID) throws SQLException, IOException {
        setupDbConnection();
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);

        CardTable cardTable = cardDao.queryForId(cardUUID);
        ColumnTable columnTable = columnDao.queryForId(cardTable.getParent_column_uuid());
        teardownDbConnection();
        return columnTable.getParent_project_uuid();
    }

    public void openCardParentProjectInWorkspace(UUID parentItemUUID) throws SQLException, IOException {
        projectService.openCardParentProjectInWorkspace(parentItemUUID);
    }
}