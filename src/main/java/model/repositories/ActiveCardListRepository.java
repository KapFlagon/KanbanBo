package model.repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.activerecords.ColumnCardActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.card.CardModel;
import model.domainobjects.column.ColumnModel;
import utils.database.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ActiveCardListRepository {


    // Variables
    protected JdbcConnectionSource connectionSource;
    protected Dao<CardModel, UUID> modelDao;
    protected ProjectColumnActiveRecord<ColumnModel> columnActiveRecord;
    protected ObservableList<ColumnCardActiveRecord> activeRecordObservableList;

    // Constructors
    public ActiveCardListRepository() {
        initActiveRecordObservableList();
    }
    public ActiveCardListRepository(ProjectColumnActiveRecord<ColumnModel> columnActiveRecord) {
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
        modelDao = DaoManager.createDao(connectionSource, CardModel.class);
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
        QueryBuilder<CardModel, UUID> queryBuilder = modelDao.queryBuilder();
        queryBuilder.where().eq(CardModel.FOREIGN_KEY_NAME, columnActiveRecord.getColumnUUID());
        PreparedQuery<CardModel> preparedQuery = queryBuilder.prepare();
        List<CardModel> queryResultList = modelDao.query(preparedQuery);
        if ((queryResultList != null) && (queryResultList.size() != 0)) {
            for (CardModel tempCardModel : queryResultList) {
                ColumnCardActiveRecord<CardModel> columnCardActiveRecord = new ColumnCardActiveRecord<CardModel>(CardModel.class, tempCardModel, columnActiveRecord);
                activeRecordObservableList.add(columnCardActiveRecord);
            }
        }
        // Iterate through the table and build a Model object for each, using the model objects to build active record to then put in the list.
        teardownDbAccess();
    }

}
