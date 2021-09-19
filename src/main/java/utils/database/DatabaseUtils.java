package utils.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import persistence.tables.card.CardTable;
import persistence.tables.card.TemplateCardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.column.TemplateColumnTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.project.TemplateProjectTable;
import persistence.tables.resourceitems.ResourceItemTable;
import persistence.tables.resourceitems.ResourceItemTypeTable;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtils {

    // TODO Add logging
    // TODO Create an interface from this, and then make an ORM specific implementation

    // Final variables
    private final static String jdbcSQLitePrefix = "jdbc:sqlite:";

    // Static variables
    private static File activeDatabaseFile;
    private static String connectionURLstring;
    public static final int MAX_TABLES = 10;

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
        Dao<ProjectTable, UUID> projectModelDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        TableUtils.createTable(projectModelDao);
    }

    public static void createProjectTemplateTable(JdbcConnectionSource connectionSource) throws SQLException {
        Dao<TemplateProjectTable, UUID> templateProjectModelDao = DaoManager.createDao(connectionSource, TemplateProjectTable.class);
        TableUtils.createTable(templateProjectModelDao);
    }

    public static void createProjectStatusesTable(JdbcConnectionSource connectionSource) throws SQLException, IOException {
        Dao<ProjectStatusTable, Integer> projectStatusesModelDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);
        TableUtils.createTable(projectStatusesModelDao);
        DefaultDataGenerator ddg = new DefaultDataGenerator();
        ddg.generateDefaultProjectStatuses();
    }

    public static void createColumnTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<ColumnTable, UUID> projectColumnModelDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        TableUtils.createTable(projectColumnModelDao);
    }

    public static void createTemplateColumnTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<TemplateColumnTable, UUID> templateColumnModelDao = DaoManager.createDao(connectionSource, TemplateColumnTable.class);
        TableUtils.createTable(templateColumnModelDao);
    }

    public static void createCardTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<CardTable, UUID> columnCardModelDao = DaoManager.createDao(connectionSource, CardTable.class);
        TableUtils.createTable(columnCardModelDao);
    }

    public static void createTemplateCardTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<TemplateCardTable, UUID> templateCardModelDao = DaoManager.createDao(connectionSource, TemplateCardTable.class);
        TableUtils.createTable(templateCardModelDao);
    }

    public static void createResourceItemTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<ResourceItemTable, UUID> linkedItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        TableUtils.createTable(linkedItemDao);
    }

    public static void createResourceItemTypeTable(JdbcConnectionSource connectionSource) throws SQLException, IOException {
        Dao<ResourceItemTypeTable, Integer> linkedItemDao = DaoManager.createDao(connectionSource, ResourceItemTypeTable.class);
        TableUtils.createTable(linkedItemDao);
        DefaultDataGenerator ddg = new DefaultDataGenerator();
        ddg.generateResourceItemTypeDefaultData();
    }

    public static void createIntermediaryTables(JdbcConnectionSource connectionSource) throws SQLException{

    }
}
