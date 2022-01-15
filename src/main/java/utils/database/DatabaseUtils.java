package utils.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import persistence.tables.bridges.data.LabelBridgeTable;
import persistence.tables.card.CardTable;
import persistence.tables.card.TemplateCardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.column.TemplateColumnTable;
import persistence.tables.data.ChecklistItemTable;
import persistence.tables.data.LabelTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.project.TemplateProjectTable;
import persistence.tables.relateditems.RelatedItemTable;
import persistence.tables.relateditems.RelatedItemTypeTable;

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
    public static final int MAX_TABLES = 14;

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
    }

    public static void createDefaultProjectStatuses(JdbcConnectionSource connectionSource) throws SQLException, IOException {
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
        Dao<RelatedItemTable, UUID> linkedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        TableUtils.createTable(linkedItemDao);
    }

    public static void createResourceItemTypeTable(JdbcConnectionSource connectionSource) throws SQLException, IOException {
        Dao<RelatedItemTypeTable, Integer> resourceItemDao = DaoManager.createDao(connectionSource, RelatedItemTypeTable.class);
        TableUtils.createTable(resourceItemDao);
    }

    public static void createDefaultResourceItemTypeData(JdbcConnectionSource connectionSource) throws SQLException, IOException {
        DefaultDataGenerator ddg = new DefaultDataGenerator();
        ddg.generateResourceItemTypeDefaultData();
    }

    public static void createLabelTable(JdbcConnectionSource connectionSource) throws SQLException{
        Dao<LabelTable, Integer> labelTableDao = DaoManager.createDao(connectionSource, LabelTable.class);
        TableUtils.createTable(labelTableDao);
    }

    public static void createDefaultLabelData(JdbcConnectionSource connectionSource) throws SQLException, IOException {
        DefaultDataGenerator ddg = new DefaultDataGenerator();
        ddg.generateLabelDefaultData();
    }

    public static void createLabelBridgeTable(JdbcConnectionSource connectionSource) throws SQLException {
        Dao<LabelBridgeTable, UUID> labelBridgeTableDao = DaoManager.createDao(connectionSource, LabelBridgeTable.class);
        TableUtils.createTable(labelBridgeTableDao);
    }

    public static void createChecklistItemTable(JdbcConnectionSource connectionSource) throws SQLException {
        Dao<ChecklistItemTable, UUID> checklistItemTableDao = DaoManager.createDao(connectionSource, ChecklistItemTable.class);
        TableUtils.createTable(checklistItemTableDao);
    }
}
