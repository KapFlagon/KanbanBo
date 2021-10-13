package persistence.services;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import utils.database.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public abstract class AbstractService {


    // Variables
    protected JdbcConnectionSource connectionSource;
    protected TransactionManager transactionManager;


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    private void initConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }

    protected void setupDbConnection() throws SQLException {
        initConnectionSource();
    }

    protected void teardownDbConnection() throws IOException {
        connectionSource.close();
    }

    protected void setupTransactionManager(ConnectionSource connectionSource) {

    }

    protected String getOffsetNowTime() {
        return OffsetDateTime.now().toString();
    }

    protected String formatUTCforLocale() {
        return null;
    }

}
