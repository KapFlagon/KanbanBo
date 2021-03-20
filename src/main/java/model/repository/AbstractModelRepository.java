package model.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public abstract class AbstractModelRepository<T>{

    // Variables
    protected Class<T> modelClassType;
    protected JdbcConnectionSource connectionSource;
    protected Dao<T, UUID> modelDao;
    protected ObservableList<T> modelList;


    // Constructors
    public AbstractModelRepository(Class<T> modelClassType) {
        this.modelClassType = modelClassType;
        initModelList();
    }
    public AbstractModelRepository(Class<T> modelClassType, ObservableList<T> modelList) {
        this.modelClassType = modelClassType;
        setModelList(modelList);
    }


    // Getters and Setters
    public ObservableList<T> getModelList() {
        return modelList;
    }
    public void setModelList(ObservableList<T> modelList) {
        this.modelList = modelList;
    }


    // Initialisation methods
    protected void initTempConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }
    protected void initModelList() {
        modelList = FXCollections.observableArrayList();
    }
    protected void initProjectDao() throws SQLException {
        modelDao = DaoManager.createDao(connectionSource, modelClassType);
    }


    // Other methods
    protected void setupDbAccess() throws SQLException {
        initTempConnectionSource();
        initProjectDao();
    }

    /**
     * Must be called after any Db access to free resources.
     * @throws IOException
     */
    protected void teardownDbAccess() throws IOException {
        connectionSource.close();
    }


    public ObservableList getAllItemsAsList() throws SQLException, IOException {
        setupDbAccess();
        if (!modelList.isEmpty()) {
            modelList.clear();
            modelList.removeAll();
        }
        if (modelDao.countOf() > 0) {
            for (T t : modelDao) {
                modelList.add(t);
            }
        }
        teardownDbAccess();
        return modelList;
    }

}
