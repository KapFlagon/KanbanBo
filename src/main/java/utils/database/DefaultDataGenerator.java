package utils.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import model.domainobjects.ProjectStatusModel;

import java.io.IOException;
import java.sql.SQLException;
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
        Dao<ProjectStatusModel, Integer> statusModelDao = DaoManager.createDao(connectionSource, ProjectStatusModel.class);
        ProjectStatusModel projectStatusModel = new ProjectStatusModel();
        for (int iterator = 1; iterator <= 3; iterator++) {
            switch (iterator){
                case 1:
                    projectStatusModel.setStatus_id(1);
                    projectStatusModel.setStatus_text("Active");
                    break;
                case 2:
                    projectStatusModel.setStatus_id(2);
                    projectStatusModel.setStatus_text("Archived");
                    break;
                case 3:
                    projectStatusModel.setStatus_id(3);
                    projectStatusModel.setStatus_text("Completed");
                    break;
            }
            statusModelDao.createOrUpdate(projectStatusModel);
        }
        connectionSource.close();
    }



}
