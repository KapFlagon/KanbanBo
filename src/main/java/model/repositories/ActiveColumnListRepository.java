package model.repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.activerecords.ProjectActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.column.ActiveProjectColumnModel;
import model.domainobjects.project.ActiveProjectModel;
import utils.database.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ActiveColumnListRepository {


    // Variables
    protected JdbcConnectionSource connectionSource;
    protected Dao<ActiveProjectColumnModel, UUID> modelDao;
    protected ProjectActiveRecord<ActiveProjectModel> projectActiveRecord;
    protected ObservableList<ProjectColumnActiveRecord> activeRecordObservableList;


    // Constructors
    public ActiveColumnListRepository() {
        initActiveRecordObservableList();
    }
    public ActiveColumnListRepository(ProjectActiveRecord<ActiveProjectModel> projectActiveRecord) {
        initActiveRecordObservableList();
        this.projectActiveRecord = projectActiveRecord;
    }

    // Getters and Setters


    public ObservableList<ProjectColumnActiveRecord> getActiveRecordObservableList() {
        return activeRecordObservableList;
    }

    // Initialisation methods
    protected void initTempConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }
    protected void initProjectDao() throws SQLException {
        modelDao = DaoManager.createDao(connectionSource, ActiveProjectColumnModel.class);
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
        QueryBuilder<ActiveProjectColumnModel, UUID> queryBuilder = modelDao.queryBuilder();
        queryBuilder.where().eq(ActiveProjectColumnModel.FOREIGN_KEY_NAME, projectActiveRecord.getProjectUUID());
        PreparedQuery<ActiveProjectColumnModel> preparedQuery = queryBuilder.prepare();
        List<ActiveProjectColumnModel> queryResultList = modelDao.query(preparedQuery);
        if ((queryResultList != null) && (queryResultList.size() != 0)) {
            for (ActiveProjectColumnModel tempActiveProjectColumnModel : queryResultList) {
                ProjectColumnActiveRecord<ActiveProjectColumnModel> projectColumnActiveRecord = new ProjectColumnActiveRecord<ActiveProjectColumnModel>(ActiveProjectColumnModel.class, tempActiveProjectColumnModel, projectActiveRecord);
                activeRecordObservableList.add(projectColumnActiveRecord);
            }
        }
        // Iterate through the table and build a Model object for each, using the model objects to build active record to then put in the list.
        teardownDbAccess();
    }

}
