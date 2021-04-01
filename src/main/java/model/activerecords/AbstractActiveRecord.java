package model.activerecords;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import utils.database.DatabaseUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;


public abstract class AbstractActiveRecord<T> {


    // Variables
    protected JdbcConnectionSource connectionSource;
    protected Class<T> modelClassType;
    protected Dao<T, UUID> dao;

    // Constructors
    protected AbstractActiveRecord(Class<T> modelClassType) {
        this.modelClassType = modelClassType;
    }

    // Getters and Setters


    // Initialisation methods
    protected void initConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }

    protected abstract void initDao() throws SQLException;

    protected abstract void initAllProperties();


    // Other methods
    protected abstract void setAllListeners();

    public abstract void createOrUpdateActiveRowInDb() throws SQLException, IOException;

    protected void setupDbConnection() throws SQLException {
        initConnectionSource();
        initDao();
    }

    protected void teardownDbConnection() throws IOException {
        connectionSource.close();
    }

}
