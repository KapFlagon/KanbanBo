package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.QueryBuilder;
import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableProject;
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
import java.util.*;
import java.util.concurrent.Callable;

public class ColumnService extends AbstractService{


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

    public ColumnService(Locale locale, ResourceBundle resourceBundle, ObservableList<ObservableProject> projectsList, ObservableList<ObservableProject> workspaceProjectsList) {
        this.locale = locale;
        this.resourceBundle = resourceBundle;
        this.projectsList = projectsList;
        this.workspaceProjectsList = workspaceProjectsList;
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
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
                parentProject.setLast_changed_timestamp(getOffsetNowTime());
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
        project.setLast_changed_timestamp(getOffsetNowTime());

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

    public void copyColumn(ObservableColumn observableColumn) {
        // TODO Implement This
    }


}
