package persistence.repositories.legacy;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.activerecords.ColumnCardActiveRecord;
import domain.activerecords.ProjectColumnActiveRecord;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import utils.database.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ActiveCardListRepository {


    // Variables
    protected JdbcConnectionSource connectionSource;
    protected Dao<CardTable, UUID> modelDao;
    protected ProjectColumnActiveRecord<ColumnTable> columnActiveRecord;
    protected ObservableList<ColumnCardActiveRecord> activeRecordObservableList;

    // Constructors
    public ActiveCardListRepository() {
        initActiveRecordObservableList();
    }
    public ActiveCardListRepository(ProjectColumnActiveRecord<ColumnTable> columnActiveRecord) {
        initActiveRecordObservableList();
        this.columnActiveRecord = columnActiveRecord;
    }

    // Getters and Setters
    public ObservableList<ColumnCardActiveRecord> getActiveRecordObservableList() {
        return activeRecordObservableList;
    }

    // Initialisation methods
    protected void initTempConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }
    protected void initProjectDao() throws SQLException {
        modelDao = DaoManager.createDao(connectionSource, CardTable.class);
    }
    protected void initActiveRecordObservableList() {
        activeRecordObservableList = FXCollections.observableArrayList();
    }

    // Other methods
    protected void setupDbAccess() throws SQLException {
        initTempConnectionSource();
        initProjectDao();
    }

    protected void teardownDbAccess() throws IOException {
        connectionSource.close();
    }

    public void readFromDb() throws IOException, SQLException {
        setupDbAccess();
        QueryBuilder<CardTable, UUID> queryBuilder = modelDao.queryBuilder();
        queryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, columnActiveRecord.getColumnUUID());
        PreparedQuery<CardTable> preparedQuery = queryBuilder.prepare();
        List<CardTable> queryResultList = modelDao.query(preparedQuery);
        if ((queryResultList != null) && (queryResultList.size() != 0)) {
            for (CardTable tempCardModel : queryResultList) {
                ColumnCardActiveRecord<CardTable> columnCardActiveRecord = new ColumnCardActiveRecord<CardTable>(CardTable.class, tempCardModel, columnActiveRecord);
                activeRecordObservableList.add(columnCardActiveRecord);
            }
        }
        // Iterate through the table and build a Model object for each, using the model objects to build active record to then put in the list.
        teardownDbAccess();
    }

}
