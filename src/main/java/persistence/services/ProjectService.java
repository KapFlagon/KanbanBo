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
import domain.entities.project.ObservableWorkspaceProject;
import domain.entities.relateditem.ObservableRelatedItem;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistence.mappers.DTOToTable;
import persistence.mappers.TableToDTO;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.relateditems.RelatedItemTable;
import persistence.tables.relateditems.RelatedItemTypeTable;
import utils.enums.RelatedItemTypeEnum;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Callable;

public class ProjectService extends AbstractService{


    // Variables
    private final Locale locale;
    private final ResourceBundle resourceBundle;
    private ObservableList<ObservableWorkspaceProject> projectsList;
    private ObservableList<ObservableWorkspaceProject> workspaceProjectsList;

    private Dao<ProjectTable, UUID> projectDao;
    private Dao<ProjectStatusTable, Integer> projectStatusDao;
    private Dao<ColumnTable, UUID> columnDao;
    private Dao<CardTable, UUID> cardDao;
    private Dao<RelatedItemTable, UUID> relatedItemDao;
    private Dao<RelatedItemTypeTable, Integer> relatedItemTypeDao;

    private QueryBuilder<ColumnTable, UUID> columnTableQueryBuilder;
    private QueryBuilder<CardTable, UUID> cardTableQueryBuilder;
    private QueryBuilder<RelatedItemTable, UUID> relatedItemTableQueryBuilder;
    private QueryBuilder<RelatedItemTypeTable, Integer> relatedItemTypeTableQueryBuilder;

    private DeleteBuilder<ColumnTable, UUID> columnTableDeleteBuilder;
    private DeleteBuilder<CardTable, UUID> cardTableDeleteBuilder;
    private DeleteBuilder<RelatedItemTable, UUID> resourceItemTableDeleteBuilder;




    // Constructors
    public ProjectService(Locale locale, ResourceBundle resourceBundle, ObservableList<ObservableWorkspaceProject> projectsList, ObservableList<ObservableWorkspaceProject> workspaceProjectsList) throws SQLException, IOException {
        this.locale = locale;
        this.resourceBundle = resourceBundle;
        this.projectsList = projectsList;
        initProjectsList();
        this.workspaceProjectsList = workspaceProjectsList;
        workspaceProjectsList.addListener(new ListChangeListener<ObservableWorkspaceProject>() {
            @Override
            public void onChanged(Change<? extends ObservableWorkspaceProject> c) {
                while (c.next())
                    if(c.wasAdded()) {
                        for(ObservableWorkspaceProject addedObservableWorkspaceProject : c.getAddedSubList()) {
                            try {
                                fillProjectFromDb(addedObservableWorkspaceProject);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                if(c.wasRemoved()) {
                    for(ObservableWorkspaceProject removedObservableWorkspaceProject : c.getRemoved()) {
                        purgeProjectData(removedObservableWorkspaceProject);
                    }
                }
            }
        });
    }

    // Getters and Setters
    public ObservableList<ObservableWorkspaceProject> getProjectsList() {
        return projectsList;
    }

    public ObservableList<ObservableWorkspaceProject> getWorkspaceProjectsList() {
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

    public List<ProjectStatusTable> getProjectStatusTableAsList() throws SQLException, IOException {
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
            ObservableWorkspaceProject observableWorkspaceProject = getProjectHeader(projectEntry, projectStatuses);
            projectsList.add(observableWorkspaceProject);
        }
    }

    // Other methods
    private void fillProjectFromDb(ObservableWorkspaceProject observableWorkspaceProject) throws SQLException, IOException {
        ObservableList<ObservableRelatedItem> projectResourceItems = FXCollections.observableArrayList();
        ObservableList<ObservableColumn> observableColumnsList = FXCollections.observableArrayList();

        setupDbConnection();

        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        relatedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        relatedItemTypeDao = DaoManager.createDao(connectionSource, RelatedItemTypeTable.class);

        columnTableQueryBuilder = columnDao.queryBuilder();
        cardTableQueryBuilder = cardDao.queryBuilder();
        relatedItemTableQueryBuilder = relatedItemDao.queryBuilder();
        relatedItemTypeTableQueryBuilder = relatedItemTypeDao.queryBuilder();

        columnTableQueryBuilder.orderBy(ColumnTable.POSITION_KEY_NAME, true).where().eq(ColumnTable.FOREIGN_KEY_NAME, observableWorkspaceProject.getProjectUUID());

        relatedItemTableQueryBuilder.where().eq(RelatedItemTable.PATH_COLUMN_NAME, observableWorkspaceProject.getProjectUUID());
        List<RelatedItemTable> projectCardRelatedItemTables = relatedItemTableQueryBuilder.query();
        for(RelatedItemTable projectCardRelatedItemTable : projectCardRelatedItemTables) {
            if(projectCardRelatedItemTable.getRelated_item_type() == RelatedItemTypeEnum.CHILD_PROJECT.getId()) {
                projectCardRelatedItemTable.setRelated_item_type(RelatedItemTypeEnum.PARENT_CARD.getId());
            }
        }

        relatedItemTableQueryBuilder.where().eq(RelatedItemTable.FOREIGN_KEY_NAME, observableWorkspaceProject.getProjectUUID());
        List<RelatedItemTable> projectSpecificRelatedItemTables = relatedItemTableQueryBuilder.query();

        List<RelatedItemTable> allProjectRelatedItems = new ArrayList<>(projectCardRelatedItemTables);
        allProjectRelatedItems.addAll(projectSpecificRelatedItemTables);

        for(RelatedItemTable projectResourceTableItem : allProjectRelatedItems) {
            relatedItemTypeTableQueryBuilder.reset();
            relatedItemTypeTableQueryBuilder.where().eq(RelatedItemTypeTable.TYPE_KEY_NAME, projectResourceTableItem.getRelated_item_type());
            RelatedItemTypeTable relatedItemTypeTable = relatedItemTypeTableQueryBuilder.queryForFirst();
            String typeText = resourceBundle.getString(relatedItemTypeTable.getRelated_item_type_text_key());
            ObservableRelatedItem observableRelatedItem = new ObservableRelatedItem(projectResourceTableItem, typeText);
            projectResourceItems.add(observableRelatedItem);
        }
        List<ColumnTable> columnTables = columnTableQueryBuilder.query();


        for(ColumnTable columnTableItem: columnTables) {
            cardTableQueryBuilder.reset();
            cardTableQueryBuilder.orderBy(CardTable.POSITION_KEY_NAME, true).where().eq(CardTable.FOREIGN_KEY_NAME, columnTableItem.getColumn_uuid());
            List<CardTable> columnCardsTableList = cardTableQueryBuilder.query();

            ObservableList<ObservableCard> observableCardsList = FXCollections.observableArrayList();
            for (CardTable cardTable : columnCardsTableList) {
                ObservableList<ObservableRelatedItem> cardObservableRelatedItemList = FXCollections.observableArrayList();
                relatedItemTableQueryBuilder.reset();
                relatedItemTableQueryBuilder.where().eq(RelatedItemTable.FOREIGN_KEY_NAME, cardTable.getID());
                List<RelatedItemTable> cardResourceItemsList = relatedItemTableQueryBuilder.query();
                for(RelatedItemTable relatedItemTableItem : cardResourceItemsList) {
                    relatedItemTypeTableQueryBuilder.reset();
                    relatedItemTypeTableQueryBuilder.where().eq(RelatedItemTypeTable.TYPE_KEY_NAME, relatedItemTableItem.getRelated_item_type());
                    RelatedItemTypeTable relatedItemTypeTable = relatedItemTypeTableQueryBuilder.queryForFirst();
                    String typeText = resourceBundle.getString(relatedItemTypeTable.getRelated_item_type_text_key());
                    ObservableRelatedItem cardObservableRelatedItem = new ObservableRelatedItem(relatedItemTableItem, typeText);
                    cardObservableRelatedItemList.add(cardObservableRelatedItem);
                }

                CardDTO cardDTO = TableToDTO.mapCardTableToCardDTO(cardTable);
                ObservableCard observableCard = new ObservableCard(cardDTO);
                observableCard.setResourceItems(cardObservableRelatedItemList);
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


        observableWorkspaceProject.setColumns(observableColumnsList);
        observableWorkspaceProject.setResourceItems(projectResourceItems);

        teardownDbConnection();
    }

    private void purgeProjectData(ObservableWorkspaceProject observableWorkspaceProject) {
        for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
            for(ObservableCard observableCard : observableColumn.getCards()) {
                for(ObservableRelatedItem observableCardResourceItem : observableCard.getResourceItems()) {
                    observableCardResourceItem = null;
                }
                observableCard = null;
            }
            observableColumn = null;
        }
    }

    public ObservableList<ObservableWorkspaceProject> getRelatedItemProjectSubList(UUID potentialParentItemUUID) throws SQLException, IOException {
        setupDbConnection();

        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        relatedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        relatedItemTypeDao = DaoManager.createDao(connectionSource, RelatedItemTypeTable.class);

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

        relatedItemTableQueryBuilder = relatedItemDao.queryBuilder();
        relatedItemTypeTableQueryBuilder.where().eq(RelatedItemTypeTable.TYPE_KEY_NAME, 4);
        List<RelatedItemTable> resourceList = relatedItemTableQueryBuilder.query();
        buildResourceItemProjectOmissionList(projectUUIDsToOmit, startingUUID, resourceList);
        ObservableList<ObservableWorkspaceProject> projectSubList = FXCollections.observableArrayList();
        for(ObservableWorkspaceProject observableWorkspaceProject : projectsList) {
            boolean additionPermitted = true;
            for(UUID ommittedProjectUUID : projectUUIDsToOmit) {
                if (ommittedProjectUUID.equals(observableWorkspaceProject.getProjectUUID())) {
                    additionPermitted = false;
                }
            }
            if(additionPermitted) {
                projectSubList.add(observableWorkspaceProject);
            }
        }
        teardownDbConnection();
        return projectSubList;
    }

    private void buildResourceItemProjectOmissionList(ArrayList<UUID> omissionList, UUID startingUUID, List<RelatedItemTable> resourceList) throws SQLException {
        for(RelatedItemTable relatedItemTable : resourceList) {
            if (startingUUID.toString().equals(relatedItemTable.getRelated_item_path())) {
                UUID cardUUID = relatedItemTable.getParent_item_uuid();
                CardTable cardTable = cardDao.queryForId(cardUUID);
                ColumnTable columnTable = columnDao.queryForId(cardTable.getParent_column_uuid());
                omissionList.add(columnTable.getParent_project_uuid());
                buildResourceItemProjectOmissionList(omissionList, columnTable.getParent_project_uuid(),resourceList);
            }
        }
    }


    public void createProject(ProjectDTO projectDTO) throws SQLException, IOException {
        ProjectTable projectTable = DTOToTable.mapProjectDTOToProjectTable(projectDTO);
        //projectTable.setProject_title(projectDTO.getTitle());
        //projectTable.setProject_description(projectDTO.getDescription());
        projectTable.setProject_status_id(1);
        //if(projectDTO.getDueOnDate() != null) {
        //    projectTable.setDue_on_date(projectDTO.getDueOnDate().toString());
        //}
        projectTable.setCreation_timestamp(ZonedDateTime.now().toString());
        projectTable.setLast_changed_timestamp(ZonedDateTime.now().toString());
        ProjectStatusTable statusKey;

        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectStatusDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);

        int result = projectDao.create(projectTable);
        statusKey = projectStatusDao.queryForId(projectTable.getProject_status_id());
        teardownDbConnection();

        if (result > 0) {
            String localizedStatusText = resourceBundle.getString(statusKey.getProject_status_text_key());
            //projectDTO.setUuid(projectTable.getProject_uuid());
            //projectDTO.setCreatedOnTimeStamp(ZonedDateTime.parse(projectTable.getCreation_timestamp()));
            //projectDTO.setLastChangedOnTimeStamp(ZonedDateTime.parse(projectTable.getLast_changed_timestamp()));
            ObservableWorkspaceProject observableWorkspaceProject = new ObservableWorkspaceProject(projectDTO, localizedStatusText);
            projectsList.add(observableWorkspaceProject);
            workspaceProjectsList.add(observableWorkspaceProject);
            System.out.println("Project created successfully");
        } else {
            // TODO respond to a failure...
            System.out.println("Project creation failed...");
        }
    }

    public boolean updateProject(ProjectDTO newProjectData, ObservableWorkspaceProject observableWorkspaceProject) throws ParseException, SQLException, IOException {
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectStatusDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);
        ProjectTable projectTableData = projectDao.queryForId(observableWorkspaceProject.getProjectUUID());
        projectTableData.setProject_title(newProjectData.getTitle());
        projectTableData.setProject_description(newProjectData.getDescription());
        projectTableData.setProject_status_id(observableWorkspaceProject.statusIDProperty().getValue());
        projectTableData.setLast_changed_timestamp(ZonedDateTime.now().toString());
        projectTableData.setDue_on_date(newProjectData.getDueOnDate());
        int result = projectDao.update(projectTableData);

        projectStatusDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);
        ProjectStatusTable statusKey = projectStatusDao.queryForId(newProjectData.getStatusId());
        teardownDbConnection();
        if(result > 0) {
            observableWorkspaceProject.projectTitleProperty().setValue(newProjectData.getTitle());
            observableWorkspaceProject.projectDescriptionProperty().setValue(newProjectData.getDescription());
            observableWorkspaceProject.lastChangedTimestampProperty().setValue(newProjectData.getLastChangedOnTimeStamp().toString());
            observableWorkspaceProject.dueOnDateProperty().set(newProjectData.getDueOnDate());
            observableWorkspaceProject.statusIDProperty().setValue(newProjectData.getStatusId());
            String localizedStatusText = resourceBundle.getString(statusKey.getProject_status_text_key());
            observableWorkspaceProject.statusTextProperty().setValue(localizedStatusText);
            System.out.println("Project updated successfully");
            return true;
        } else {
            // TODO respond to a failure...
            System.out.println("Project update failed");
            return false;
        }
    }

    public void deleteProject(ObservableWorkspaceProject projectDomainObject) throws ParseException, SQLException, IOException {
        setupDbConnection();

        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        relatedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);

        resourceItemTableDeleteBuilder = relatedItemDao.deleteBuilder();
        resourceItemTableDeleteBuilder.where().eq(RelatedItemTable.FOREIGN_KEY_NAME, projectDomainObject.getProjectUUID());
        List<PreparedDelete<RelatedItemTable>> resourceItemPreparedDeleteList = new ArrayList<PreparedDelete<RelatedItemTable>>();
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
            cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, column.getColumn_uuid());
            allCards.add(cardTableQueryBuilder.query());
            cardTableDeleteBuilder.reset();
            cardTableDeleteBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, column.getColumn_uuid());
            cardPreparedDeleteList.add(cardTableDeleteBuilder.prepare());
        }

        for(List<CardTable> cardSubList : allCards) {
            for(CardTable card : cardSubList) {
                resourceItemTableDeleteBuilder.reset();
                resourceItemTableDeleteBuilder.where().eq(RelatedItemTable.FOREIGN_KEY_NAME, card.getID());
                PreparedDelete<RelatedItemTable> preparedDelete = resourceItemTableDeleteBuilder.prepare();
                resourceItemPreparedDeleteList.add(preparedDelete);
            }
        }

        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for(PreparedDelete<RelatedItemTable> resourceDelete: resourceItemPreparedDeleteList) {
                    relatedItemDao.delete(resourceDelete);
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

    public void copyProject(ObservableWorkspaceProject originalProject) throws SQLException, ParseException, IOException {
        // TODO Implement this
    }

    public void openProjectInWorkspace(UUID projectUUID) throws SQLException, IOException {
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        ProjectTable projectTable = projectDao.queryForId(projectUUID);
        List<ProjectStatusTable> projectStatuses = getProjectStatusTableAsList();
        teardownDbConnection();

        ObservableWorkspaceProject observableWorkspaceProject = getProjectHeader(projectTable, projectStatuses);
        if (getWorkspaceProjectsList().size() == 0) {
            //System.out.println("No projects opened yet in workspace");
            getWorkspaceProjectsList().add(observableWorkspaceProject);
        } else {
            boolean projectCanBeFreshlyOpened = true;
            for (ObservableWorkspaceProject innerObservableWorkspaceProject : getWorkspaceProjectsList()) {
                if (observableWorkspaceProject.getProjectUUID().equals(innerObservableWorkspaceProject.getProjectUUID())) {
                    projectCanBeFreshlyOpened = false;
                }
            }
            if(projectCanBeFreshlyOpened) {
                getWorkspaceProjectsList().add(observableWorkspaceProject);
                System.out.println("Project opened");
            } else {
                // TODO Need some mechanism here to open the existing project in the view...
                System.out.println("Project already open");
            }
        }
    }

    public void openCardParentProjectInWorkspace(UUID parentItemUUID) throws SQLException, IOException {
        setupDbConnection();

        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);

        CardTable cardtable = cardDao.queryForId(parentItemUUID);
        ColumnTable columnTable = columnDao.queryForId(cardtable.getParent_column_uuid());
        teardownDbConnection();

        openProjectInWorkspace(columnTable.getParent_project_uuid());
    }

    private ObservableWorkspaceProject getProjectHeader(ProjectTable projectTableEntry, List<ProjectStatusTable> projectStatuses) {
        int statusId = projectTableEntry.getProject_status_id();
        String localisedStatusText = "";
        for(ProjectStatusTable projectStatus : projectStatuses) {
            if (projectStatus.getProject_status_id() == statusId) {
                String statusTextKey = projectStatus.getProject_status_text_key();
                localisedStatusText = resourceBundle.getString(statusTextKey);
            }
        }
        ProjectDTO projectDTO = TableToDTO.mapProjectTableToProjectDTO(projectTableEntry);

        ObservableWorkspaceProject observableWorkspaceProject = new ObservableWorkspaceProject(projectDTO, localisedStatusText);
        return observableWorkspaceProject;
    }


}
