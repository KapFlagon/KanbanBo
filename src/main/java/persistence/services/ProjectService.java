package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.QueryBuilder;
import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;
import persistence.dto.project.ProjectDTO;
import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableProject;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistence.mappers.TableToDTO;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.resourceitems.ResourceItemTable;
import persistence.tables.resourceitems.ResourceItemTypeTable;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;

public class ProjectService extends AbstractService{


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




    // Constructors
    public ProjectService(Locale locale, ResourceBundle resourceBundle, ObservableList<ObservableProject> projectsList, ObservableList<ObservableProject> workspaceProjectsList) throws SQLException, IOException {
        this.locale = locale;
        this.resourceBundle = resourceBundle;
        this.projectsList = projectsList;
        initProjectsList();
        this.workspaceProjectsList = workspaceProjectsList;
        workspaceProjectsList.addListener(new ListChangeListener<ObservableProject>() {
            @Override
            public void onChanged(Change<? extends ObservableProject> c) {
                while (c.next())
                    if(c.wasAdded()) {
                        for(ObservableProject addedObservableProject : c.getAddedSubList()) {
                            try {
                                fillProjectFromDb(addedObservableProject);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                if(c.wasRemoved()) {
                    for(ObservableProject removedObservableProject : c.getRemoved()) {
                        purgeProjectData(removedObservableProject);
                    }
                }
            }
        });
    }

    // Getters and Setters
    public ObservableList<ObservableProject> getProjectsList() {
        return projectsList;
    }

    public ObservableList<ObservableProject> getWorkspaceProjectsList() {
        return workspaceProjectsList;
    }

    private List<ProjectTable> getProjectsTableAsList() throws SQLException, IOException {
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectDao.setObjectCache(true);
        List<ProjectTable> projectsList = projectDao.queryForAll();
        teardownDbConnection();
        return projectsList;
    }

    private List<ProjectStatusTable> getProjectStatusTableAsList() throws SQLException, IOException {
        setupDbConnection();
        projectStatusDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);
        projectStatusDao.setObjectCache(true);
        List<ProjectStatusTable> projectStatusList = projectStatusDao.queryForAll();
        teardownDbConnection();
        return projectStatusList;
    }


    // Initialisation methods
    public void initProjectsList() throws SQLException, IOException {
        projectsList.clear();
        List<ProjectTable> projects = getProjectsTableAsList();
        List<ProjectStatusTable> projectStatuses = getProjectStatusTableAsList();
        for(ProjectTable projectEntry : projects) {
            int statusId = projectEntry.getProject_status_id();
            String localisedStatusText = "";
            for(ProjectStatusTable projectStatus : projectStatuses) {
                if (projectStatus.getProject_status_id() == statusId) {
                    String statusTextKey = projectStatus.getProject_status_text_key();
                    localisedStatusText = resourceBundle.getString(statusTextKey);
                }
            }
            ProjectDTO projectDTO = TableToDTO.mapProjectTableToProjectDTO(projectEntry);
            ObservableProject projectDomainObject = new ObservableProject(projectDTO, localisedStatusText);
            projectsList.add(projectDomainObject);
        }
    }

    // Other methods
    private void fillProjectFromDb(ObservableProject observableProject) throws SQLException, IOException {
        ObservableList<ObservableResourceItem> projectResourceItems = FXCollections.observableArrayList();
        ObservableList<ObservableColumn> observableColumnsList = FXCollections.observableArrayList();

        setupDbConnection();

        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        resourceItemTypeDao = DaoManager.createDao(connectionSource, ResourceItemTypeTable.class);

        columnTableQueryBuilder = columnDao.queryBuilder();
        cardTableQueryBuilder = cardDao.queryBuilder();
        resourceItemTableQueryBuilder = resourceItemDao.queryBuilder();
        resourceItemTypeTableQueryBuilder = resourceItemTypeDao.queryBuilder();

        columnTableQueryBuilder.orderBy(ColumnTable.POSITION_KEY_NAME, true).where().eq(ColumnTable.FOREIGN_KEY_NAME, observableProject.getProjectUUID());

        resourceItemTableQueryBuilder.where().eq(ResourceItemTable.FOREIGN_KEY_NAME, observableProject.getProjectUUID());
        List<ResourceItemTable> projectResourcesTables = resourceItemTableQueryBuilder.query();
        for(ResourceItemTable projectResourceTableItem : projectResourcesTables) {
            resourceItemTypeTableQueryBuilder.reset();
            resourceItemTypeTableQueryBuilder.where().eq(ResourceItemTypeTable.TYPE_KEY_NAME, projectResourceTableItem.getResource_item_type());
            ResourceItemTypeTable resourceItemTypeTable = resourceItemTypeTableQueryBuilder.queryForFirst();
            String typeText = resourceBundle.getString(resourceItemTypeTable.getResource_item_type_text_key());
            ObservableResourceItem observableResourceItem = new ObservableResourceItem(projectResourceTableItem, typeText);
            projectResourceItems.add(observableResourceItem);
        }
        List<ColumnTable> columnTables = columnTableQueryBuilder.query();


        for(ColumnTable columnTableItem: columnTables) {
            cardTableQueryBuilder.reset();
            cardTableQueryBuilder.orderBy(CardTable.POSITION_KEY_NAME, true).where().eq(CardTable.FOREIGN_KEY_NAME, columnTableItem.getColumn_uuid());
            List<CardTable> columnCardsTableList = cardTableQueryBuilder.query();

            ObservableList<ObservableCard> observableCardsList = FXCollections.observableArrayList();
            for (CardTable cardTable : columnCardsTableList) {
                ObservableList<ObservableResourceItem> cardObservableResourceItemList = FXCollections.observableArrayList();
                resourceItemTableQueryBuilder.reset();
                resourceItemTableQueryBuilder.where().eq(ResourceItemTable.FOREIGN_KEY_NAME, cardTable.getID());
                List<ResourceItemTable> cardResourceItemsList = resourceItemTableQueryBuilder.query();
                for(ResourceItemTable resourceItemTableItem : cardResourceItemsList) {
                    resourceItemTypeTableQueryBuilder.reset();
                    resourceItemTypeTableQueryBuilder.where().eq(ResourceItemTypeTable.TYPE_KEY_NAME, resourceItemTableItem.getResource_item_type());
                    ResourceItemTypeTable resourceItemTypeTable = resourceItemTypeTableQueryBuilder.queryForFirst();
                    String typeText = resourceBundle.getString(resourceItemTypeTable.getResource_item_type_text_key());
                    ObservableResourceItem cardObservableResourceItem = new ObservableResourceItem(resourceItemTableItem, typeText);
                    cardObservableResourceItemList.add(cardObservableResourceItem);
                }

                CardDTO cardDTO = TableToDTO.mapCardTableToColumnDTO(cardTable);
                ObservableCard observableCard = new ObservableCard(cardDTO);
                observableCard.setResourceItems(cardObservableResourceItemList);
                observableCard.positionProperty().addListener((observable, oldVal, newVal) -> {
                    // TODO implement function to change position of the Card in its list
                });
                observableCardsList.add(observableCard);
            }
            ColumnDTO columnDTO = TableToDTO.mapColumnTableToColumnDTO(columnTableItem);
            ObservableColumn observableColumn = new ObservableColumn(columnDTO, observableCardsList);
            observableColumn.columnPositionProperty().addListener((observable, oldVal, newVal) -> {
                // TODO implement function to change position of the Column in its list
            });
            observableColumnsList.add(observableColumn);
        }


        observableProject.setColumns(observableColumnsList);
        observableProject.setResourceItems(projectResourceItems);

        teardownDbConnection();
    }

    private void purgeProjectData(ObservableProject observableProject) {
        for(ObservableColumn observableColumn : observableProject.getColumns()) {
            for(ObservableCard observableCard : observableColumn.getCards()) {
                for(ObservableResourceItem observableCardResourceItem : observableCard.getResourceItems()) {
                    observableCardResourceItem = null;
                }
                observableCard = null;
            }
            observableColumn = null;
        }
    }

    public ObservableList<ObservableProject> getResourceItemProjectSubList(UUID potentialParentItemUUID) throws SQLException, IOException {
        setupDbConnection();

        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        resourceItemTypeDao = DaoManager.createDao(connectionSource, ResourceItemTypeTable.class);

        ArrayList<UUID> projectUUIDsToOmit = new ArrayList<>();
        boolean isCard = cardDao.idExists(potentialParentItemUUID);
        boolean isProject = projectDao.idExists(potentialParentItemUUID);
        UUID startingUUID = null;
        if (isCard) {
            CardTable cardTable = cardDao.queryForId(potentialParentItemUUID);
            ColumnTable columnTable = columnDao.queryForId(cardTable.getParent_column_uuid());
            startingUUID = columnTable.getParent_project_uuid();
            projectUUIDsToOmit.add(startingUUID);
        } else if (isProject) {
            startingUUID = potentialParentItemUUID;
            projectUUIDsToOmit.add(potentialParentItemUUID);
        }

        resourceItemTableQueryBuilder = resourceItemDao.queryBuilder();
        resourceItemTypeTableQueryBuilder.where().eq(ResourceItemTable.TYPE_COLUMN_NAME, 4);
        List<ResourceItemTable> resourceList = resourceItemTableQueryBuilder.query();
        buildResourceItemProjectOmissionList(projectUUIDsToOmit, startingUUID, resourceList);
        ObservableList<ObservableProject> projectSubList = FXCollections.observableArrayList();
        for(ObservableProject observableProject : projectsList) {
            boolean additionPermitted = true;
            for(UUID ommittedProjectUUID : projectUUIDsToOmit) {
                if (ommittedProjectUUID.equals(observableProject.getProjectUUID())) {
                    additionPermitted = false;
                }
            }
            if(additionPermitted) {
                projectSubList.add(observableProject);
            }
        }
        teardownDbConnection();
        return projectSubList;
    }

    private void buildResourceItemProjectOmissionList(ArrayList<UUID> omissionList, UUID startingUUID, List<ResourceItemTable> resourceList) throws SQLException {
        for(ResourceItemTable resourceItemTable : resourceList) {
            if (startingUUID.toString().equals(resourceItemTable.getResource_item_path())) {
                UUID cardUUID = resourceItemTable.getParent_item_uuid();
                CardTable cardTable = cardDao.queryForId(cardUUID);
                ColumnTable columnTable = columnDao.queryForId(cardTable.getParent_column_uuid());
                omissionList.add(columnTable.getParent_project_uuid());
                buildResourceItemProjectOmissionList(omissionList, columnTable.getParent_project_uuid(),resourceList);
            }
        }
    }


    public void createProject(ProjectDTO projectDTO) throws SQLException, IOException {
        ProjectTable projectTable = new ProjectTable();
        projectTable.setProject_title(projectDTO.getTitle());
        projectTable.setProject_description(projectDTO.getDescription());
        projectTable.setProject_status_id(1);
        projectTable.setDue_on_date(projectDTO.getDueOnDate().toString());
        projectTable.setCreation_timestamp(getOffsetNowTime());
        projectTable.setLast_changed_timestamp(getOffsetNowTime());
        ProjectStatusTable statusKey;

        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectStatusDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);

        int result = projectDao.create(projectTable);
        statusKey = projectStatusDao.queryForId(1);
        teardownDbConnection();

        if (result > 0) {
            String localizedStatusText = resourceBundle.getString(statusKey.getProject_status_text_key());
            ObservableProject observableProject = new ObservableProject(projectDTO, localizedStatusText);
            projectsList.add(observableProject);
            workspaceProjectsList.add(observableProject);
            System.out.println("Project created successfully");
        } else {
            // TODO respond to a failure...
            System.out.println("Project creation failed...");
        }
    }

    public boolean updateProject(ProjectDTO newProjectData, ObservableProject observableProject) throws ParseException, SQLException, IOException {
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectStatusDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);
        ProjectTable projectTableData = projectDao.queryForId(observableProject.getProjectUUID());
        projectTableData.setProject_title(newProjectData.getTitle());
        projectTableData.setProject_description(newProjectData.getDescription());
        projectTableData.setProject_status_id(observableProject.statusIDProperty().getValue());
        projectTableData.setLast_changed_timestamp(getOffsetNowTime());
        int result = projectDao.update(projectTableData);

        projectStatusDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);
        ProjectStatusTable statusKey = projectStatusDao.queryForId(newProjectData.getStatus());
        teardownDbConnection();
        if(result > 0) {
            observableProject.projectTitleProperty().setValue(newProjectData.getTitle());
            observableProject.projectDescriptionProperty().setValue(newProjectData.getDescription());
            observableProject.lastChangedTimestampProperty().setValue(newProjectData.getLastChangedOnDate().toString());
            observableProject.statusIDProperty().setValue(newProjectData.getStatus());
            String localizedStatusText = resourceBundle.getString(statusKey.getProject_status_text_key());
            observableProject.statusTextProperty().setValue(localizedStatusText);
            System.out.println("Project updated successfully");
            return true;
        } else {
            // TODO respond to a failure...
            System.out.println("Project update failed");
            return false;
        }
    }

    public void deleteProject(ObservableProject projectDomainObject) throws ParseException, SQLException, IOException {
        setupDbConnection();

        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);

        resourceItemTableDeleteBuilder = resourceItemDao.deleteBuilder();
        resourceItemTableDeleteBuilder.where().eq(ResourceItemTable.FOREIGN_KEY_NAME, projectDomainObject.getProjectUUID());
        List<PreparedDelete<ResourceItemTable>> resourceItemPreparedDeleteList = new ArrayList<PreparedDelete<ResourceItemTable>>();
        resourceItemPreparedDeleteList.add(resourceItemTableDeleteBuilder.prepare());

        columnTableQueryBuilder = columnDao.queryBuilder();
        columnTableQueryBuilder.where().eq(ColumnTable.FOREIGN_KEY_NAME, projectDomainObject.getProjectUUID());
        List<ColumnTable> columnsList = columnTableQueryBuilder.query();

        columnTableDeleteBuilder = columnDao.deleteBuilder();
        columnTableDeleteBuilder.where().eq(ColumnTable.FOREIGN_KEY_NAME, projectDomainObject.getProjectUUID());
        PreparedDelete<ColumnTable> columnPreparedDelete = columnTableDeleteBuilder.prepare();

        cardTableQueryBuilder = cardDao.queryBuilder();
        List<List<CardTable>> allCards = new ArrayList<List<CardTable>>();
        cardTableDeleteBuilder = cardDao.deleteBuilder();
        List<PreparedDelete<CardTable>> cardPreparedDeleteList = new ArrayList<PreparedDelete<CardTable>>();
        for(ColumnTable column : columnsList) {
            cardTableQueryBuilder.reset();
            cardTableQueryBuilder.where().eq(ColumnTable.FOREIGN_KEY_NAME, column.getColumn_uuid());
            allCards.add(cardTableQueryBuilder.query());
            cardTableDeleteBuilder.reset();
            cardTableDeleteBuilder.where().eq(ColumnTable.FOREIGN_KEY_NAME, column.getColumn_uuid());
            cardPreparedDeleteList.add(cardTableDeleteBuilder.prepare());
        }

        for(List<CardTable> cardSubList : allCards) {
            for(CardTable card : cardSubList) {
                resourceItemTableDeleteBuilder.reset();
                resourceItemTableDeleteBuilder.where().eq(ResourceItemTable.FOREIGN_KEY_NAME, card.getID());
                PreparedDelete<ResourceItemTable> preparedDelete = resourceItemTableDeleteBuilder.prepare();
                resourceItemPreparedDeleteList.add(preparedDelete);
            }
        }

        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for(PreparedDelete<ResourceItemTable> resourceDelete: resourceItemPreparedDeleteList) {
                    resourceItemDao.delete(resourceDelete);
                }
                for(PreparedDelete<CardTable> cardDelete : cardPreparedDeleteList) {
                    cardDao.delete(cardDelete);
                }
                columnDao.delete(columnPreparedDelete);
                projectDao.deleteById(projectDomainObject.getProjectUUID());
                return true;
            }
        });

        teardownDbConnection();
        projectsList.remove(projectDomainObject);
        workspaceProjectsList.remove(projectDomainObject);
    }

    public void copyProject(ObservableProject originalProject) throws SQLException, ParseException, IOException {
        // TODO Implement this
    }

}
