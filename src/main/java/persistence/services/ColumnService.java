package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.QueryBuilder;
import persistence.dto.column.ColumnDTO;
import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableWorkspaceProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.mappers.DTOToTable;
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
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Callable;

public class ColumnService extends AbstractService{


    // Variables
    private final Locale locale;
    private final ResourceBundle resourceBundle;
    private ObservableList<ObservableWorkspaceProject> workspaceProjectsList;

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

    public ColumnService(Locale locale, ResourceBundle resourceBundle, ObservableList<ObservableWorkspaceProject> workspaceProjectsList) {
        this.locale = locale;
        this.resourceBundle = resourceBundle;
        this.workspaceProjectsList = workspaceProjectsList;
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public boolean createColumn(ColumnDTO columnDTO) throws SQLException, IOException {
        ColumnTable columnTable = DTOToTable.mapColumnDTOToColumnTable(columnDTO);
        //columnTable.setParent_project_uuid(columnDTO.getParentProjectUUID());
        //columnTable.setColumn_title(columnDTO.getTitle());
        //columnTable.setFinal_column(columnDTO.isFinalColumn());
        columnTable.setCreation_timestamp(ZonedDateTime.now().toString());
        columnTable.setLast_changed_timestamp(ZonedDateTime.now().toString());
        setupDbConnection();

        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        columnTableQueryBuilder = columnDao.queryBuilder();

        columnTableQueryBuilder.where().eq(ColumnTable.FOREIGN_KEY_NAME, UUID.fromString(columnDTO.getParentProjectUUID()));
        long columnCount = columnTableQueryBuilder.countOf();
        int position = (int) (columnCount);
        columnTable.setColumn_position(position);
        columnTableQueryBuilder.where().eq(ColumnTable.FOREIGN_KEY_NAME, UUID.fromString(columnDTO.getParentProjectUUID())).and().eq(ColumnTable.FINAL_FLAG_NAME, true);
        long countOfFinalColumns = columnTableQueryBuilder.countOf();
        if (countOfFinalColumns > 0 && columnDTO.isFinalColumn()) {
            System.out.println("Cannot create another final column");
            teardownDbConnection();
            return false;
        } else {
            int result = columnDao.create(columnTable);
            teardownDbConnection();
            if (result > 0) {
                ObservableWorkspaceProject observableWorkspaceProject = null;
                for (ObservableWorkspaceProject op : workspaceProjectsList) {
                    if(op.getProjectUUID().equals(UUID.fromString(columnDTO.getParentProjectUUID()))){
                        observableWorkspaceProject = op;
                    }
                }
                //columnDTO.setUuid(columnTable.getColumn_uuid());
                //columnDTO.setCreatedOnTimeStamp(ZonedDateTime.parse(columnTable.getCreation_timestamp()));
                //columnDTO.setLastChangedOnTimeStamp(ZonedDateTime.parse(columnTable.getLast_changed_timestamp()));
                ObservableList<ObservableCard> emptyCardList = FXCollections.observableArrayList();
                ObservableColumn observableColumn = new ObservableColumn(columnDTO, emptyCardList);
                observableColumn.columnPositionProperty().addListener((observable, oldVal, newVal) -> {
                    // TODO implement function to change position of the Column in its list
                });
                // TODO finish this
                observableWorkspaceProject.getColumns().add(observableColumn);
                System.out.println("Column was created successfully");
                return true;
            } else {
                // TODO respond to a failure...
                System.out.println("Column creation failed...");
                teardownDbConnection();
                return false;
            }
        }
    }

    public void updateColumn(ColumnDTO columnDTO, ObservableColumn observableColumn) throws ParseException, SQLException, IOException {
        ColumnTable columnTableData = DTOToTable.mapColumnDTOToColumnTable(columnDTO);
        setupDbConnection();
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        final int[] result = new int[1];
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                result[0] = 0;
                ProjectTable parentProject = projectDao.queryForId(columnTableData.getParent_project_uuid());
                parentProject.setLast_changed_timestamp(ZonedDateTime.now().toString());
                result[0] += columnDao.update(columnTableData);
                result[0] += projectDao.update(parentProject);
                System.out.println("Column and project updated successfully");
                return 1;
            }
        });
        if (result[0] > 1) {
            observableColumn.setParentProjectUUID(UUID.fromString(columnDTO.getParentProjectUUID()));
            observableColumn.columnTitleProperty().setValue(columnDTO.getTitle());
            observableColumn.columnPositionProperty().setValue(columnDTO.getPosition());
            observableColumn.finalColumnProperty().setValue(columnDTO.isFinalColumn());
        }
        teardownDbConnection();
    }

    public void updateColumns(List<ColumnDTO> columnDTOList, ObservableList<ObservableColumn> observableColumns) throws SQLException, IOException {
        List<ColumnTable> columnTableList = new ArrayList<>();
        for(ColumnDTO columnDTO : columnDTOList) {
            ColumnTable columnTable = DTOToTable.mapColumnDTOToColumnTable(columnDTO);
            columnTableList.add(columnTable);
        }
        updateColumnTables(columnTableList, observableColumns);
    }

    public void updateColumnTables(List<ColumnTable> columnTableList, ObservableList<ObservableColumn> observableColumns) throws SQLException, IOException {

        setupDbConnection();
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        final int[] result = new int[1];
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                result[0] = 0;
                ProjectTable parentProject = projectDao.queryForId(columnTableList.get(0).getParent_project_uuid());
                parentProject.setLast_changed_timestamp(ZonedDateTime.now().toString());

                for(ColumnTable columnTable : columnTableList) {
                    result[0] += columnDao.update(columnTable);
                }
                result[0] += projectDao.update(parentProject);
                System.out.println("Column and project updated successfully");
                return 1;
            }
        });
        teardownDbConnection();
        if (result[0] > 1) {

            for(ObservableColumn observableColumn : observableColumns) {
                for(ColumnTable columnTable : columnTableList) {
                    if(observableColumn.getColumnUUID().equals(columnTable.getColumn_uuid())) {
                        observableColumn.setParentProjectUUID(columnTable.getParent_project_uuid());
                        observableColumn.columnTitleProperty().setValue(columnTable.getColumn_title());
                        observableColumn.columnPositionProperty().setValue(columnTable.getColumn_position());
                        observableColumn.finalColumnProperty().setValue(columnTable.isFinal_column());
                        observableColumn.creationTimestampProperty().setValue(columnTable.getCreation_timestamp());
                        observableColumn.lastChangedTimestampProperty().setValue(columnTable.getLast_changed_timestamp());
                    }
                }
            }
            observableColumns.sort(new Comparator<ObservableColumn>() {
                @Override
                public int compare(ObservableColumn o1, ObservableColumn o2) {
                    return o1.columnPositionProperty().getValue().compareTo(o2.columnPositionProperty().getValue());
                }
            });
        }
    }

    public void deleteColumn(ObservableColumn column) throws SQLException, IOException {
        // TODO need to update other column position values if a column is removed from the list...
        setupDbConnection();

        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);

        ProjectTable project = projectDao.queryForId(column.getParentProjectUUID());
        project.setLast_changed_timestamp(ZonedDateTime.now().toString());

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
        for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
            if(observableWorkspaceProject.getProjectUUID().equals(column.getParentProjectUUID())){
                for(Iterator<ObservableColumn> observableColumnIterator = observableWorkspaceProject.getColumns().listIterator(); observableColumnIterator.hasNext();) {
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

    public void moveColumn(ColumnDTO newColumnDataDTO, ObservableColumn oldObservableColumn) throws SQLException, IOException {
        int newPosition = newColumnDataDTO.getPosition();
        int oldPosition = oldObservableColumn.columnPositionProperty().getValue();
        if(newPosition != oldPosition) {
            setupDbConnection();
            columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
            columnTableQueryBuilder = columnDao.queryBuilder();
            columnTableQueryBuilder.where().eq(ColumnTable.FOREIGN_KEY_NAME, oldObservableColumn.getParentProjectUUID());
            List<ColumnTable> columnTableList = columnTableQueryBuilder.query();
            teardownDbConnection();
            ColumnTable movedColumn = null;
            for(Iterator<ColumnTable> iterator = columnTableList.listIterator(); iterator.hasNext();) {
                ColumnTable innerTestTable = iterator.next();
                if(UUID.fromString(newColumnDataDTO.getUuid()).equals(innerTestTable.getColumn_uuid())) {
                    movedColumn = innerTestTable;
                    movedColumn.setColumn_position(newColumnDataDTO.getPosition());
                    iterator.remove();
                }
            }
            /*for(ColumnTable columnTable : columnTableList) {
                if(UUID.fromString(newColumnDataDTO.getUuid()).equals(columnTable.getColumn_uuid())){
                    movedColumn = columnTable;
                    movedColumn.setColumn_position(newColumnDataDTO.getPosition());
                    columnTableList.remove(columnTable);
                }
            }*/
            if(newPosition < oldPosition) {
                int diffVector = oldPosition - newPosition;
                shiftSurroundingColumnsRight(columnTableList, newPosition, diffVector);
            } else if(newPosition > oldPosition) {
                int diffVector = newPosition - oldPosition;
                shiftSurroundingColumnsLeft(columnTableList, newPosition, diffVector);
            }
            columnTableList.add(movedColumn);
            ObservableList<ObservableColumn> observableColumnObservableList = FXCollections.observableArrayList();
            for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                if(observableWorkspaceProject.getProjectUUID().equals(oldObservableColumn.getParentProjectUUID())){
                    observableColumnObservableList = observableWorkspaceProject.getColumns();
                }
            }
            updateColumnTables(columnTableList, observableColumnObservableList);
        }

    }

    public ObservableList<ObservableColumn> getRelatedColumns(UUID columnUUID) {
        for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
            for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                if(observableColumn.getColumnUUID().equals(columnUUID)) {
                    return observableWorkspaceProject.getColumns();
                }
            }
        }
        return FXCollections.observableArrayList();
    }

    private void shiftSurroundingColumnsRight(List<ColumnTable> columnTableList, int insertPosition, int diffVector) {
        for(int iterator = 0; iterator < columnTableList.size(); iterator ++) {
            ColumnTable columnTable = columnTableList.get(iterator);
            if(columnTable.getColumn_position() >= insertPosition
                    && columnTable.getColumn_position() < columnTableList.size()
                    && diffVector > 0) {
                columnTable.setColumn_position(columnTable.getColumn_position() + 1);
                diffVector -= 1;
            }
        }
    }

    private void shiftSurroundingColumnsLeft(List<ColumnTable> columnTableList, int insertPosition, int diffVector) {
        for(int iterator = columnTableList.size() - 1; iterator >= 0; iterator --) {
            ColumnTable columnTable = columnTableList.get(iterator);
            if(columnTable.getColumn_position() > 0
                    && columnTable.getColumn_position() <= insertPosition
                    && diffVector > 0) {
                columnTable.setColumn_position(columnTable.getColumn_position() - 1);
                diffVector -= 1;
            }
        }
    }


}
