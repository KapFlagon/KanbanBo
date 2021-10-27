package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.*;
import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;
import persistence.dto.project.ProjectDTO;
import domain.entities.project.ObservableProject;
import domain.entities.column.ObservableColumn;
import domain.entities.card.ObservableCard;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.resourceitems.ResourceItemTable;
import persistence.tables.resourceitems.ResourceItemTypeTable;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Callable;

public class KanbanBoDataService extends AbstractService{


    // Variables
    private final Locale locale;
    private final ResourceBundle resourceBundle;
    private ObservableList<ObservableProject> projectsList;
    private ObservableList<ObservableProject> workspaceProjectsList;

    private Dao<ProjectTable, UUID> projectDao;
    private Dao<ProjectStatusTable, Integer> projectStatusDao;
    private Dao<ColumnTable, UUID> columnDao;
    private Dao<CardTable, UUID> cardDao;
    private Dao<ResourceItemTable, UUID> resourceItemDao;
    private Dao<ResourceItemTypeTable, Integer> resourceItemTypeDao;

    private QueryBuilder<ColumnTable, UUID> columnTableQueryBuilder;
    private QueryBuilder<CardTable, UUID> cardTableQueryBuilder;
    private QueryBuilder<ResourceItemTable, UUID> resourceItemTableQueryBuilder;
    private QueryBuilder<ResourceItemTypeTable, Integer> resourceItemTypeTableQueryBuilder;

    private DeleteBuilder<ColumnTable, UUID> columnTableDeleteBuilder;
    private DeleteBuilder<CardTable, UUID> cardTableDeleteBuilder;
    private DeleteBuilder<ResourceItemTable, UUID> resourceItemTableDeleteBuilder;

    private ProjectService projectService;
    private ColumnService columnService;
    private CardService cardService;



    // Constructors

    public KanbanBoDataService() throws SQLException, IOException {
        locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        projectsList = FXCollections.observableArrayList();
        workspaceProjectsList = FXCollections.observableArrayList();
        projectService = new ProjectService(locale, resourceBundle, projectsList, workspaceProjectsList);
        columnService = new ColumnService(locale, resourceBundle, workspaceProjectsList);
        cardService = new CardService(locale, resourceBundle, workspaceProjectsList);
    }


    // Getters and Setters
    public ObservableList<ObservableProject> getProjectsList() {
        return projectsList;
    }

    public ObservableList<ObservableProject> getWorkspaceProjectsList() {
        return workspaceProjectsList;
    }



    // Initialisation methods


    // Other methods
    public void createProject(ProjectDTO projectDTO) throws SQLException, IOException {
        projectService.createProject(projectDTO);
    }

    public void updateProject(ProjectDTO projectDTO, ObservableProject observableProject) throws ParseException, SQLException, IOException {
        projectService.updateProject(projectDTO, observableProject);
    }

    public void deleteProject(ObservableProject observableProject) throws ParseException, SQLException, IOException {
        projectService.deleteProject(observableProject);
    }

    private void copyProject(ObservableProject originalProject) throws SQLException, ParseException, IOException {
        projectService.copyProject(originalProject);
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

    public void deleteColumn(ObservableColumn column) throws SQLException, IOException {
        columnService.deleteColumn(column);
    }


    public void createCard(CardDTO cardDTO) throws SQLException, IOException {
        cardService.createCard(cardDTO);
    }

    public void updateCard(CardDTO cardDTO, ObservableCard observableCard) throws SQLException, IOException {
        cardService.updateCard(cardDTO, observableCard);
    }

    public void deleteCard(ObservableCard card) throws SQLException, IOException {
        cardService.deleteCard(card);
    }


    private void createResourceItem(UUID topLevelProjectUUID, ResourceItemTable resourceItemTable) throws SQLException, IOException {
        setupDbConnection();
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        ProjectTable projectTable = projectDao.queryForId(topLevelProjectUUID);
        projectTable.setLast_changed_timestamp(ZonedDateTime.now().toString());

        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                resourceItemDao.create(resourceItemTable);
                projectDao.update(projectTable);
                return null;
            }
        });

        teardownDbConnection();
    }

    private void updateResourceItem(UUID topLevelProjectUUID, ResourceItemTable resourceItemTable) throws SQLException, IOException {

        setupDbConnection();
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        ProjectTable projectTable = projectDao.queryForId(topLevelProjectUUID);
        projectTable.setLast_changed_timestamp(ZonedDateTime.now().toString());

        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                resourceItemDao.update(resourceItemTable);
                projectDao.update(projectTable);
                return null;
            }
        });

        teardownDbConnection();
    }

    private void deleteResourceItem(UUID topLevelProjectUUID, ResourceItemTable resourceItemTable) throws SQLException, IOException {
        setupDbConnection();
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        ProjectTable projectTable = projectDao.queryForId(topLevelProjectUUID);
        projectTable.setLast_changed_timestamp(ZonedDateTime.now().toString());

        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                resourceItemDao.delete(resourceItemTable);
                projectDao.update(projectTable);
                return null;
            }
        });

        teardownDbConnection();
    }


    public void createProjectResourceItem(UUID projectUUID, String title, String description, int type, String path) throws SQLException, IOException {
        ResourceItemTable resourceItemTable= new ResourceItemTable();
        resourceItemTable.setResource_item_uuid(UUID.randomUUID());
        resourceItemTable.setParent_item_uuid(projectUUID);
        resourceItemTable.setResource_item_title(title);
        resourceItemTable.setResource_item_description(description);
        resourceItemTable.setResource_item_type(type);
        resourceItemTable.setResource_item_path(path);

        createResourceItem(projectUUID, resourceItemTable);
    }


    public void updateProjectResourceItem(ObservableResourceItem observableResourceItem) throws SQLException, IOException {
        ResourceItemTable resourceItemTable= new ResourceItemTable();
        resourceItemTable.setResource_item_uuid(observableResourceItem.getResourceItemUUID());
        resourceItemTable.setParent_item_uuid(observableResourceItem.getParentItemUUID());
        resourceItemTable.setResource_item_title(observableResourceItem.titleProperty().getValue());
        resourceItemTable.setResource_item_description(observableResourceItem.descriptionProperty().getValue());
        resourceItemTable.setResource_item_type(observableResourceItem.typeProperty().getValue());
        resourceItemTable.setResource_item_path(observableResourceItem.pathProperty().getValue());

        updateResourceItem(resourceItemTable.getParent_item_uuid(), resourceItemTable);
    }

    public void deleteProjectResourceItem(ObservableResourceItem observableResourceItem) throws SQLException, IOException {
        ResourceItemTable resourceItemTable= new ResourceItemTable();
        resourceItemTable.setResource_item_uuid(observableResourceItem.getResourceItemUUID());
        resourceItemTable.setParent_item_uuid(observableResourceItem.getParentItemUUID());
        resourceItemTable.setResource_item_title(observableResourceItem.titleProperty().getValue());
        resourceItemTable.setResource_item_description(observableResourceItem.descriptionProperty().getValue());
        resourceItemTable.setResource_item_type(observableResourceItem.typeProperty().getValue());
        resourceItemTable.setResource_item_path(observableResourceItem.pathProperty().getValue());

        deleteResourceItem(resourceItemTable.getResource_item_uuid(), resourceItemTable);
    }

    private void deleteProjectResourceItems(ObservableList<ObservableResourceItem> projectResourceItemsList) {
        // todo
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

    public void createCardResourceItem(UUID cardUUID, String title, String description, int type, String path) throws SQLException, IOException {
        ResourceItemTable resourceItemTable= new ResourceItemTable();
        resourceItemTable.setResource_item_uuid(UUID.randomUUID());
        resourceItemTable.setParent_item_uuid(cardUUID);
        resourceItemTable.setResource_item_title(title);
        resourceItemTable.setResource_item_description(description);
        resourceItemTable.setResource_item_type(type);
        resourceItemTable.setResource_item_path(path);

        UUID parentProjectUUID = getParentProjectUUIDOfCard(cardUUID);
        createResourceItem(parentProjectUUID, resourceItemTable);
    }

    public void updateCardResourceItem(ObservableResourceItem observableResourceItem) throws SQLException, IOException {
        ResourceItemTable resourceItemTable= new ResourceItemTable();
        resourceItemTable.setResource_item_uuid(observableResourceItem.getResourceItemUUID());
        resourceItemTable.setParent_item_uuid(observableResourceItem.getParentItemUUID());
        resourceItemTable.setResource_item_title(observableResourceItem.titleProperty().getValue());
        resourceItemTable.setResource_item_description(observableResourceItem.descriptionProperty().getValue());
        resourceItemTable.setResource_item_type(observableResourceItem.typeProperty().getValue());
        resourceItemTable.setResource_item_path(observableResourceItem.pathProperty().getValue());

        UUID parentProjectUUID = getParentProjectUUIDOfCard(resourceItemTable.getParent_item_uuid());
        updateResourceItem(parentProjectUUID, resourceItemTable);
    }

    public void deleteCardResourceItem(ObservableResourceItem observableResourceItem) throws SQLException, IOException {
        ResourceItemTable resourceItemTable= new ResourceItemTable();
        resourceItemTable.setResource_item_uuid(observableResourceItem.getResourceItemUUID());
        resourceItemTable.setParent_item_uuid(observableResourceItem.getParentItemUUID());
        resourceItemTable.setResource_item_title(observableResourceItem.titleProperty().getValue());
        resourceItemTable.setResource_item_description(observableResourceItem.descriptionProperty().getValue());
        resourceItemTable.setResource_item_type(observableResourceItem.typeProperty().getValue());
        resourceItemTable.setResource_item_path(observableResourceItem.pathProperty().getValue());

        UUID parentProjectUUID = getParentProjectUUIDOfCard(resourceItemTable.getParent_item_uuid());
        deleteResourceItem(parentProjectUUID, resourceItemTable);
    }

    public void deleteCardResourceItems(ObservableList resourceItemsList) {
        // todo
    }

}