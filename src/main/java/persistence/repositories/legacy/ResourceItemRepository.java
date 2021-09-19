package persistence.repositories.legacy;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.activerecords.ResourceItemActiveRecord;
import domain.activerecords.ResourceItemTypeActiveRecord;
import persistence.tables.resourceitems.ResourceItemTable;
import persistence.tables.resourceitems.ResourceItemTypeTable;
import utils.database.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ResourceItemRepository {


    // Variables
    protected JdbcConnectionSource connectionSource;
    protected Dao<ResourceItemTable, UUID> modelDao;
    protected Dao<ResourceItemTypeTable, Integer> relatedItemTypeDao;
    //protected DomainObjectWithUUID parentItem;
    protected UUID parentItemUUID;
    protected ObservableList<ResourceItemActiveRecord> activeRecordObservableList;
    protected ObservableList<ResourceItemTypeActiveRecord> relatedItemTypeObservableList;


    // Constructors
    public ResourceItemRepository() {
        initActiveRecordObservableList();
    }
    public ResourceItemRepository(UUID parentItemUUID) {
        initActiveRecordObservableList();
        this.parentItemUUID = parentItemUUID;
    }

    // Getters and Setters
    public ObservableList<ResourceItemActiveRecord> getActiveRecordObservableList() {
        return activeRecordObservableList;
    }

    public ObservableList<ResourceItemTypeActiveRecord> getRelatedItemTypeObservableList() {
        return relatedItemTypeObservableList;
    }

    // Initialisation methods
    protected void initTempConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }
    protected void initDao() throws SQLException {
        modelDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        relatedItemTypeDao = DaoManager.createDao(connectionSource, ResourceItemTypeTable.class);
    }
    protected void initActiveRecordObservableList() {
        activeRecordObservableList = FXCollections.observableArrayList();
        relatedItemTypeObservableList = FXCollections.observableArrayList();
    }

    // Other methods
    protected void setupDbAccess() throws SQLException {
        initTempConnectionSource();
        initDao();
    }

    protected void teardownDbAccess() throws IOException {
        connectionSource.close();
    }

    public void readFromDb() throws IOException, SQLException {
        setupDbAccess();
        QueryBuilder<ResourceItemTable, UUID> queryBuilder = modelDao.queryBuilder();
        queryBuilder.where().eq(ResourceItemTable.FOREIGN_KEY_NAME, parentItemUUID);
        PreparedQuery<ResourceItemTable> preparedQuery = queryBuilder.prepare();
        List<ResourceItemTable> queryResultList = modelDao.query(preparedQuery);
        if ((queryResultList != null) && (queryResultList.size() != 0)) {
            for (ResourceItemTable tempResourceItemTableModel : queryResultList) {
                ResourceItemActiveRecord resourceItemActiveRecord = new ResourceItemActiveRecord(parentItemUUID, tempResourceItemTableModel);
                activeRecordObservableList.add(resourceItemActiveRecord);
            }
        }
        // Iterate through the table and build a Model object for each, using the model objects to build active record to then put in the list.

        for (ResourceItemTypeTable resourceItemTypeTable : relatedItemTypeDao) {
            ResourceItemTypeActiveRecord resourceItemTypeActiveRecord = new ResourceItemTypeActiveRecord(resourceItemTypeTable);
            relatedItemTypeObservableList.add(resourceItemTypeActiveRecord);
        }
        teardownDbAccess();
    }


}
