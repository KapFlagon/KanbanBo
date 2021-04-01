package utils.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.domainobjects.card.ArchivedCardModel;
import model.domainobjects.card.ActiveColumnCardModel;
import model.domainobjects.card.TemplateCardModel;
import model.domainobjects.column.ArchivedColumnModel;
import model.domainobjects.column.ActiveProjectColumnModel;
import model.domainobjects.column.TemplateColumnModel;
import model.domainobjects.project.ActiveProjectModel;
import model.domainobjects.project.ArchivedProjectModel;
import model.domainobjects.project.CompletedProjectModel;
import model.domainobjects.project.TemplateProjectModel;
import model.domainobjects.subitems.LinkedItem;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtils {

    // TODO Add logging

    // Final variables
    private final static String jdbcSQLitePrefix = "jdbc:sqlite:";

    // Static variables
    private static File activeDatabaseFile;
    private static String connectionURLstring;

    // Constructors


    // Getters and Setters
    public static File getActiveDatabaseFile() {
        return activeDatabaseFile;
    }
    public static void setActiveDatabaseFile(File activeDatabaseFile) {
        DatabaseUtils.activeDatabaseFile = activeDatabaseFile;
        DatabaseUtils.buildConnectionURLstring();
    }

    public static String getConnectionURLstring() {
        return connectionURLstring;
    }
    public static void setConnectionURLstring(String connectionURLstring) {
        DatabaseUtils.connectionURLstring = connectionURLstring;
    }

    // Initialisation methods

    // Other methods
    public static JdbcConnectionSource getConnectionSource() throws SQLException {
        return new JdbcConnectionSource(DatabaseUtils.getConnectionURLstring());
    }

    private static void buildConnectionURLstring() {
        String builtConnectionString = DatabaseUtils.jdbcSQLitePrefix + DatabaseUtils.getActiveDatabaseFile().toString();
        DatabaseUtils.setConnectionURLstring(builtConnectionString);
    }

    public static void initDatabaseTablesInFile() throws SQLException, IOException {
        // establish connection
        JdbcConnectionSource connectionSource = DatabaseUtils.getConnectionSource();

        // TODO need to add some sort of progress bar to indicate work is being done here.
        createProjectTables(connectionSource);
        createColumnTables(connectionSource);
        createCardTables(connectionSource);
        createSubItemTables(connectionSource);
        createIntermediaryTables(connectionSource);

        // Close the connection for the data source.
        connectionSource.close();
    }

    private static void createProjectTables(JdbcConnectionSource connectionSource) throws SQLException {
        Dao<ActiveProjectModel, UUID> activeProjectModelDao = DaoManager.createDao(connectionSource, ActiveProjectModel.class);
        Dao<ArchivedProjectModel, UUID> archivedProjectModelDao = DaoManager.createDao(connectionSource, ArchivedProjectModel.class);
        Dao<CompletedProjectModel, UUID> completedProjectModelDao = DaoManager.createDao(connectionSource, CompletedProjectModel.class);
        Dao<TemplateProjectModel, UUID> templateProjectModelDao = DaoManager.createDao(connectionSource, TemplateProjectModel.class);

        TableUtils.createTable(activeProjectModelDao);
        TableUtils.createTable(archivedProjectModelDao);
        TableUtils.createTable(completedProjectModelDao);
        TableUtils.createTable(templateProjectModelDao);
    }

    private static void createColumnTables(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<ActiveProjectColumnModel, UUID> projectColumnModelDao = DaoManager.createDao(connectionSource, ActiveProjectColumnModel.class);
        Dao<ArchivedColumnModel, UUID> archivedColumnModelDao = DaoManager.createDao(connectionSource, ArchivedColumnModel.class);
        Dao<TemplateColumnModel, UUID> templateColumnModelDao = DaoManager.createDao(connectionSource, TemplateColumnModel.class);

        TableUtils.createTable(projectColumnModelDao);
        TableUtils.createTable(archivedColumnModelDao);
        TableUtils.createTable(templateColumnModelDao);
    }

    private static void createCardTables(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<ActiveColumnCardModel, UUID> columnCardModelDao = DaoManager.createDao(connectionSource, ActiveColumnCardModel.class);
        Dao<ArchivedCardModel, UUID> archivedCardModelDao = DaoManager.createDao(connectionSource, ArchivedCardModel.class);
        Dao<TemplateCardModel, UUID> templateCardModelDao = DaoManager.createDao(connectionSource, TemplateCardModel.class);

        TableUtils.createTable(columnCardModelDao);
        TableUtils.createTable(archivedCardModelDao);
        TableUtils.createTable(templateCardModelDao);
    }

    private static void createSubItemTables(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<LinkedItem, UUID> linkedItemDao = DaoManager.createDao(connectionSource, LinkedItem.class);
        TableUtils.createTable(linkedItemDao);
    }

    private static void createIntermediaryTables(JdbcConnectionSource connectionSource) throws SQLException{

    }
}
