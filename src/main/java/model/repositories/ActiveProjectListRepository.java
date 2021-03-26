package model.repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.activerecords.ProjectActiveRecord;
import model.domainobjects.project.ActiveProjectModel;
import utils.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class ActiveProjectListRepository {


    // Variables
    protected JdbcConnectionSource connectionSource;
    protected Dao<ActiveProjectModel, UUID> modelDao;
    protected ObservableList<ProjectActiveRecord> activeRecordObservableList;


    // Constructors
    public ActiveProjectListRepository() {
        initActiveRecordObservableList();
    }

    // Getters and Setters
    public ObservableList<ProjectActiveRecord> getActiveRecordObservableList() {
        return activeRecordObservableList;
    }
    public void setActiveRecordObservableList(ObservableList<ProjectActiveRecord> activeRecordObservableList) {
        this.activeRecordObservableList = activeRecordObservableList;
    }

    // Initialisation methods
    protected void initTempConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }
    protected void initProjectDao() throws SQLException {
        modelDao = DaoManager.createDao(connectionSource, ActiveProjectModel.class);
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
        long count = modelDao.countOf();
        if (count != 0) {
            for (ActiveProjectModel tempModel : modelDao) {
                ProjectActiveRecord<ActiveProjectModel> activeRecord = new ProjectActiveRecord<ActiveProjectModel>(ActiveProjectModel.class, tempModel);
                activeRecordObservableList.add(activeRecord);
            }
        }
        // Iterate through the table and build a Model object for each, using the model objects to build active record to then put in the list.
        teardownDbAccess();
    }

}
