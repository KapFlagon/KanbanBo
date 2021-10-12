package utils.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import persistence.tables.data.LabelTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.resourceitems.ResourceItemTypeTable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultDataGenerator {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public void generateDefaultTemplateProject() {
        // Create template project row
            // Name
            // Description
        // Create template columns (3)
            // 1. To Do
                // Column name
                // Column position
                // Column parent
            // 2. Doing
                // Column name
                // Column position
                // Column parent
            // 3. Done
                // Column name
                // Column position
                // Column parent
        // Create template cards (1) ?
            // Card Title
            // Card Description
            // Card parent
            // Card Position
        // Create default statuses
            // 1. Active
            // 2. Archived
            // 3. Completed
    }

    public void generateDefaultTemplateColumns(UUID parentProjectUUID) {

    }

    public void generateDefaultTemplateCard(UUID parentColumnUUID) {

    }

    public void generateDefaultProjectStatuses() throws SQLException, IOException {
        JdbcConnectionSource connectionSource = DatabaseUtils.getConnectionSource();
        Dao<ProjectStatusTable, Integer> statusModelDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);
        ProjectStatusTable projectStatusTable;
        String[] projectStatusDescriptionResourceBundleKeys = {
                "project.status.description.active",
                "project.status.description.completed",
                "project.status.description.hiatus",
                "project.status.description.cancelled"
        };
        for (int iterator = 0; iterator < projectStatusDescriptionResourceBundleKeys.length; iterator++) {
            projectStatusTable = new ProjectStatusTable();
            //projectStatusTable.setProject_status_id(iterator + 1);
            projectStatusTable.setProject_status_text_key(projectStatusDescriptionResourceBundleKeys[iterator]);
            statusModelDao.createOrUpdate(projectStatusTable);
        }
        connectionSource.close();
    }

    public void generateResourceItemTypeDefaultData() throws SQLException, IOException {
        JdbcConnectionSource connectionSource = DatabaseUtils.getConnectionSource();
        Dao<ResourceItemTypeTable, Integer> statusModelDao = DaoManager.createDao(connectionSource, ResourceItemTypeTable.class);
        ResourceItemTypeTable resourceItemTypeTable;
        String[] resourceItemDescriptionResourceBundleKeys = {
                "resource_item.type.description.directory",
                "resource_item.type.description.file",
                "resource_item.type.description.url",
                "resource_item.type.description.child_project",
                "resource_item.type.description.parent_card"
        };
        for (int iterator = 0; iterator < resourceItemDescriptionResourceBundleKeys.length; iterator++) {
            resourceItemTypeTable = new ResourceItemTypeTable();
            //resourceItemTypeTable.setResource_item_type_id(iterator + 1);
            resourceItemTypeTable.setResource_item_type_text_key(resourceItemDescriptionResourceBundleKeys[iterator]);
            statusModelDao.createOrUpdate(resourceItemTypeTable);
        }
        connectionSource.close();
    }

    public void generateLabelDefaultData() throws SQLException, IOException {
        JdbcConnectionSource connectionSource = DatabaseUtils.getConnectionSource();
        Dao<LabelTable, Integer> labelTableDao = DaoManager.createDao(connectionSource, LabelTable.class);
        LabelTable labelTable = new LabelTable();
        labelTable.setLabel_title("Planning");
        labelTable.setLabel_description("Planning and preparation");
        labelTableDao.create(labelTable);
        labelTable = new LabelTable();
        labelTable.setLabel_title("Design");
        labelTable.setLabel_description("Design and interface");
        labelTableDao.create(labelTable);
        labelTable = new LabelTable();
        labelTable.setLabel_title("Implementation");
        labelTable.setLabel_description("Implementation and execution");
        labelTableDao.create(labelTable);
        labelTable = new LabelTable();
        labelTable.setLabel_title("Requires discussion");
        labelTable.setLabel_description("Requires further discussion before proceeding");
        labelTableDao.create(labelTable);
        labelTable = new LabelTable();
        labelTable.setLabel_title("Bug");
        labelTable.setLabel_description("Bug or programming error that requires attention");
        labelTableDao.create(labelTable);
        labelTable = new LabelTable();
        labelTable.setLabel_title("No go");
        labelTable.setLabel_description("Won't be addressed further");
        labelTableDao.create(labelTable);
        labelTable = new LabelTable();
        labelTable.setLabel_title("Enhancement");
        labelTable.setLabel_description("Enhancement or additional feature");
        labelTableDao.create(labelTable);
        connectionSource.close();
    }



}
