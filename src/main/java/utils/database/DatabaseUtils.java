package utils.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.domainobjects.card.CardModel;
import model.domainobjects.card.TemplateCardModel;
import model.domainobjects.column.ColumnModel;
import model.domainobjects.column.TemplateColumnModel;
import model.domainobjects.project.ProjectModel;
import model.domainobjects.StatusModel;
import model.domainobjects.project.TemplateProjectModel;
import model.domainobjects.subitems.ResourceItem;

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
    public static final int MAX_TABLES = 9;

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
        //createProjectTables(connectionSource);
        //createColumnTables(connectionSource);
        //createCardTables(connectionSource);
        //createSubItemTables(connectionSource);
        //createIntermediaryTables(connectionSource);

        // Close the connection for the data source.
        connectionSource.close();
    }

    public static void createProjectTable(JdbcConnectionSource connectionSource) throws SQLException {
        Dao<ProjectModel, UUID> projectModelDao = DaoManager.createDao(connectionSource, ProjectModel.class);
        TableUtils.createTable(projectModelDao);
    }

    public static void createProjectTemplateTable(JdbcConnectionSource connectionSource) throws SQLException {
        Dao<TemplateProjectModel, UUID> templateProjectModelDao = DaoManager.createDao(connectionSource, TemplateProjectModel.class);
        TableUtils.createTable(templateProjectModelDao);
    }

    public static void createProjectStatusesTable(JdbcConnectionSource connectionSource) throws SQLException {
        Dao<StatusModel, Integer> projectStatusesModelDao = DaoManager.createDao(connectionSource, StatusModel.class);
        TableUtils.createTable(projectStatusesModelDao);
    }

    public static void createColumnTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<ColumnModel, UUID> projectColumnModelDao = DaoManager.createDao(connectionSource, ColumnModel.class);
        TableUtils.createTable(projectColumnModelDao);
    }

    public static void createTemplateColumnTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<TemplateColumnModel, UUID> templateColumnModelDao = DaoManager.createDao(connectionSource, TemplateColumnModel.class);
        TableUtils.createTable(templateColumnModelDao);
    }

    public static void createCardTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<CardModel, UUID> columnCardModelDao = DaoManager.createDao(connectionSource, CardModel.class);
        TableUtils.createTable(columnCardModelDao);
    }

    public static void createTemplateCardTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<TemplateCardModel, UUID> templateCardModelDao = DaoManager.createDao(connectionSource, TemplateCardModel.class);
        TableUtils.createTable(templateCardModelDao);
    }

    public static void createResourceItemTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<ResourceItem, UUID> linkedItemDao = DaoManager.createDao(connectionSource, ResourceItem.class);
        TableUtils.createTable(linkedItemDao);
    }

    public static void createIntermediaryTables(JdbcConnectionSource connectionSource) throws SQLException{

    }
}
