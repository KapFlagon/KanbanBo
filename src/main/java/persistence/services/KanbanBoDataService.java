package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.*;
import domain.entities.project.ObservableProject;
import domain.entities.column.ObservableColumn;
import domain.entities.card.ObservableCard;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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



    // Constructors

    public KanbanBoDataService() throws SQLException, IOException {
        locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        projectsList = FXCollections.observableArrayList();
        initProjectsList();
        workspaceProjectsList = FXCollections.observableArrayList();
        workspaceProjectsList.addListener(new ListChangeListener<ObservableProject>() {
            @Override
            public void onChanged(Change<? extends ObservableProject> c) {
                while (c.next())
                    if(c.wasAdded()) {
                    for(ObservableProject observableProject: c.getAddedSubList()) {
                        try {
                            fillProjectFromDb(observableProject);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            ObservableProject projectDomainObject = new ObservableProject(projectEntry, localisedStatusText);
            ChangeListener<Boolean> changeListener = addProjectChangeListener(projectDomainObject.dataChangePendingProperty(), projectDomainObject);
            projectDomainObject.dataChangePendingProperty().addListener(changeListener);
            projectsList.add(projectDomainObject);
        }
    }

    // Other methods


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
                ObservableCard observableCard = new ObservableCard(cardTable);
                observableCard.setResourceItems(cardObservableResourceItemList);
                observableCard.positionProperty().addListener((observable, oldVal, newVal) -> {
                    // TODO implement function to change position of the Card in its list
                });
                observableCardsList.add(observableCard);
            }
            ObservableColumn observableColumn = new ObservableColumn(columnTableItem, observableCardsList);
            observableColumn.columnPositionProperty().addListener((observable, oldVal, newVal) -> {
                // TODO implement function to change position of the Column in its list
            });
            observableColumnsList.add(observableColumn);
        }


        observableProject.setColumns(observableColumnsList);
        observableProject.setResourceItems(projectResourceItems);

        teardownDbConnection();
    }


    public boolean createProject(String title, String description) throws SQLException, IOException {
        ProjectTable projectTable = new ProjectTable();
        projectTable.setProject_title(title);
        projectTable.setProject_description(description);
        projectTable.setProject_status_id(1);
        projectTable.setCreation_timestamp(new Date());
        projectTable.setLast_changed_timestamp(new Date());
        ProjectStatusTable statusKey;
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectStatusDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);
        int result = projectDao.create(projectTable);
        statusKey = projectStatusDao.queryForId(1);
        teardownDbConnection();
        if (result > 0) {
            String localizedStatusText = resourceBundle.getString(statusKey.getProject_status_text_key());
            ObservableProject observableProject = new ObservableProject(projectTable, localizedStatusText);
            ChangeListener<Boolean> changeListener = addProjectChangeListener(observableProject.dataChangePendingProperty(), observableProject);
            observableProject.dataChangePendingProperty().addListener(changeListener);
            projectsList.add(observableProject);
            workspaceProjectsList.add(observableProject);
            System.out.println("Project updated successfully");
            return true;
        } else {
            // TODO respond to a failure...
            System.out.println("Project creation failed...");
            return false;
        }
    }

    public boolean updateProject(ObservableProject projectDomainObject) throws ParseException, SQLException, IOException {
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        ProjectTable projectTableData = projectDao.queryForId(projectDomainObject.getProjectUUID());
        projectTableData.setProject_title(projectDomainObject.projectTitleProperty().getValue());
        projectTableData.setProject_description(projectDomainObject.projectDescriptionProperty().getValue());
        projectTableData.setProject_status_id(projectDomainObject.statusIDProperty().getValue());
        projectTableData.setLast_changed_timestamp(new Date());
        int result = projectDao.update(projectTableData);
        teardownDbConnection();
        if(result > 0) {
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
        // TODO respond to a failure...
    }

    private void copyProject(ObservableProject originalProject) throws SQLException, ParseException, IOException {
        // TODO Implement this
    }


    public boolean createColumn(UUID parentProjectUUID, String title, boolean finalColumn) throws SQLException, IOException {
        ColumnTable columnTable = new ColumnTable();
        columnTable.setParent_project_uuid(parentProjectUUID);
        columnTable.setColumn_title(title);
        columnTable.setFinal_column(finalColumn);
        setupDbConnection();

        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        columnTableQueryBuilder = columnDao.queryBuilder();

        columnTableQueryBuilder.where().eq(ColumnTable.FOREIGN_KEY_NAME, parentProjectUUID);
        long columnCount = columnTableQueryBuilder.countOf();
        int position = (int) (columnCount + 1);
        columnTable.setColumn_position(position);
        columnTableQueryBuilder.where().eq(ColumnTable.FOREIGN_KEY_NAME, parentProjectUUID).and().eq(ColumnTable.FINAL_FLAG_NAME, true);
        long countOfFinalColumns = columnTableQueryBuilder.countOf();
        if (countOfFinalColumns > 0) {
            return false;
        } else {
            int result = columnDao.create(columnTable);
            teardownDbConnection();
            if (result > 0) {
                ObservableProject observableProject = null;
                for (ObservableProject op : workspaceProjectsList) {
                    if(op.getProjectUUID().equals(parentProjectUUID)){
                        observableProject = op;
                    }
                }
                ObservableList<ObservableCard> emptyCardList = FXCollections.observableArrayList();
                ObservableColumn observableColumn = new ObservableColumn(columnTable, emptyCardList);
                observableColumn.columnPositionProperty().addListener((observable, oldVal, newVal) -> {
                    // TODO implement function to change position of the Column in its list
                });
                observableColumn.dataChangePendingProperty().addListener(((observable, oldValue, newValue) -> {
                    if(newValue) {
                        try {
                            updateColumn(observableColumn);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
                // TODO finish this
                observableProject.getColumns().add(observableColumn);
                System.out.println("Column was created successfully");
                return true;
            } else {
                // TODO respond to a failure...
                System.out.println("Column creation failed...");
                return false;
            }
        }
    }

    public boolean updateColumn(ObservableColumn observableColumn) throws ParseException, SQLException, IOException {
        ColumnTable columnTableData = new ColumnTable();
        columnTableData.setColumn_uuid(observableColumn.getColumnUUID());
        columnTableData.setParent_project_uuid(observableColumn.getParentProjectUUID());
        columnTableData.setColumn_title(observableColumn.columnTitleProperty().getValue());
        columnTableData.setColumn_position(observableColumn.columnPositionProperty().get());
        columnTableData.setFinal_column(observableColumn.isFinalColumn());
        setupDbConnection();
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                ProjectTable parentProject = projectDao.queryForId(columnTableData.getParent_project_uuid());
                parentProject.setLast_changed_timestamp(new Date());
                columnDao.update(columnTableData);
                projectDao.update(parentProject);
                System.out.println("Column and project updated successfully");
                return null;
            }
        });
        teardownDbConnection();
        return true;
        // TODO respond to a failure...
    }

    public void deleteColumn(ObservableColumn column) throws SQLException, IOException {
        setupDbConnection();

        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);

        ProjectTable project = projectDao.queryForId(column.getParentProjectUUID());
        project.setLast_changed_timestamp(new Date());

        cardTableQueryBuilder = cardDao.queryBuilder();
        cardTableDeleteBuilder = cardDao.deleteBuilder();
        List<PreparedDelete<CardTable>> cardPreparedDeleteList = new ArrayList<PreparedDelete<CardTable>>();
        resourceItemTableDeleteBuilder = resourceItemDao.deleteBuilder();
        List<PreparedDelete<ResourceItemTable>> resourceItemPreparedDeleteList = new ArrayList<PreparedDelete<ResourceItemTable>>();

        cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, column.getColumnUUID());
        List<CardTable> allCards = cardTableQueryBuilder.query();
        cardTableDeleteBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, column.getColumnUUID());
        cardPreparedDeleteList.add(cardTableDeleteBuilder.prepare());


        for(CardTable card : allCards) {
            resourceItemTableDeleteBuilder.reset();
            resourceItemTableDeleteBuilder.where().eq(ResourceItemTable.FOREIGN_KEY_NAME, card.getID());
            PreparedDelete<ResourceItemTable> preparedDelete = resourceItemTableDeleteBuilder.prepare();
            resourceItemPreparedDeleteList.add(preparedDelete);
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
                columnDao.deleteById(column.getColumnUUID());
                projectDao.update(project);
                return true;
            }
        });
        teardownDbConnection();
        for(ObservableProject observableProject : workspaceProjectsList) {
            if(observableProject.getProjectUUID().equals(column.getParentProjectUUID())){
                for(Iterator<ObservableColumn> observableColumnIterator = observableProject.getColumns().listIterator(); observableColumnIterator.hasNext();) {
                    UUID tempUUID = observableColumnIterator.next().getColumnUUID();
                    if(tempUUID.equals(column.getColumnUUID())) {
                        observableColumnIterator.remove();
                    }
                }
            }
        }
    }


    public void createCard(UUID parentColumnUUID, String title, String description) throws SQLException, IOException {
        CardTable card = new CardTable();
        card.setParent_column_uuid(parentColumnUUID);
        card.setCard_title(title);
        card.setCard_description_text(description);

        setupDbConnection();

        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        cardTableQueryBuilder = cardDao.queryBuilder();
        cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, parentColumnUUID);
        long cardCount = cardTableQueryBuilder.countOf();
        int position = (int) (cardCount + 1);
        card.setCard_position(position);

        ColumnTable column = columnDao.queryForId(parentColumnUUID);
        ProjectTable project = projectDao.queryForId(column.getParent_project_uuid());
        project.setLast_changed_timestamp(new Date());
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                cardDao.create(card);
                projectDao.update(project);
                return null;
            }
        });
        teardownDbConnection();

        for(ObservableProject observableProject : workspaceProjectsList) {
            if(project.getID().equals(observableProject.getProjectUUID())) {
                for(ObservableColumn observableColumn : observableProject.getColumns()) {
                    if(observableColumn.getColumnUUID().equals(parentColumnUUID)) {
                        ObservableCard newObservableCard = new ObservableCard(card, FXCollections.observableArrayList());
                        newObservableCard.positionProperty().addListener((observable, oldVal, newVal) -> {
                            // TODO implement function to change position of the Card in its list
                        });
                        observableColumn.getCards().add(newObservableCard);
                    }
                }
            }
        }
    }

    public void updateCard(ObservableCard observableCard) throws SQLException, IOException {
        CardTable cardTable = new CardTable();
        cardTable.setCard_uuid(observableCard.getCardUUID());
        cardTable.setParent_column_uuid(observableCard.getParentColumnUUID());
        cardTable.setCard_title(observableCard.cardTitleProperty().getValue());
        cardTable.setCard_description_text(observableCard.cardDescriptionProperty().getValue());
        cardTable.setCard_position(observableCard.positionProperty().getValue());

        setupDbConnection();
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        ColumnTable column = columnDao.queryForId(cardTable.getParent_column_uuid());
        ProjectTable project = projectDao.queryForId(column.getParent_project_uuid());
        project.setLast_changed_timestamp(new Date());
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                cardDao.update(cardTable);
                projectDao.update(project);
                return null;
            }
        });
        teardownDbConnection();
    }

    public void deleteCard(ObservableCard card) throws SQLException, IOException {
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        resourceItemTableDeleteBuilder = resourceItemDao.deleteBuilder();
        ColumnTable column = columnDao.queryForId(card.getParentColumnUUID());
        ProjectTable project = projectDao.queryForId(column.getParent_project_uuid());
        project.setLast_changed_timestamp(new Date());

        List<PreparedDelete<ResourceItemTable>> resourceItemPreparedDeleteList = new ArrayList<PreparedDelete<ResourceItemTable>>();

        for (ObservableResourceItem observableResourceItem : card.getResourceItems()) {
            resourceItemTableDeleteBuilder.reset();
            resourceItemTableDeleteBuilder.where().eq(ResourceItemTable.FOREIGN_KEY_NAME, card.getCardUUID());
            resourceItemPreparedDeleteList.add(resourceItemTableDeleteBuilder.prepare());
        }
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for(PreparedDelete<ResourceItemTable> resourceItemPreparedDelete : resourceItemPreparedDeleteList) {
                    resourceItemDao.delete(resourceItemPreparedDelete);
                }
                cardDao.deleteById(card.getCardUUID());
                projectDao.update(project);
                return null;
            }
        });
        teardownDbConnection();
        for(ObservableProject observableProject : workspaceProjectsList) {
            if(observableProject.getProjectUUID().equals(column.getParent_project_uuid())){
                for(ObservableColumn observableColumn : observableProject.getColumns()) {
                    for(Iterator<ObservableCard> observableCardIterator = observableColumn.getCards().listIterator(); observableCardIterator.hasNext();) {
                        UUID tempUUID = observableCardIterator.next().getCardUUID();
                        if(tempUUID.equals(card.getCardUUID())) {
                            observableCardIterator.remove();
                        }
                    }

                }
            }
        }
    }


    private void createResourceItem(UUID topLevelProjectUUID, ResourceItemTable resourceItemTable) throws SQLException, IOException {
        setupDbConnection();
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        ProjectTable projectTable = projectDao.queryForId(topLevelProjectUUID);
        projectTable.setLast_changed_timestamp(new Date());

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
        projectTable.setLast_changed_timestamp(new Date());

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
        projectTable.setLast_changed_timestamp(new Date());

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

    private ChangeListener addProjectChangeListener(SimpleBooleanProperty booleanProperty, ObservableProject observableProject) {
        ChangeListener<Boolean> projectChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    try {
                        updateProject(observableProject);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                booleanProperty.set(false);
            }
        };
        return projectChangeListener;
    }

    private ChangeListener addColumnChangeListener(SimpleBooleanProperty booleanProperty, ObservableColumn observableColumn) {
        ChangeListener<Boolean> projectChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    try {
                        updateColumn(observableColumn);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                booleanProperty.set(false);
            }
        };
        return projectChangeListener;
    }

}