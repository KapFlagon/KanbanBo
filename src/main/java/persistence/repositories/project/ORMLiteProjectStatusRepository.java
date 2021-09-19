package persistence.repositories.project;

import persistence.tables.project.ProjectStatusTable;
import persistence.repositories.backends.ormlite.ORMLiteDomainObjectRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ORMLiteProjectStatusRepository extends ORMLiteDomainObjectRepository<ProjectStatusTable, Integer> {


    // Variables


    // Constructors
    public ORMLiteProjectStatusRepository() throws SQLException, IOException {
        super();
        readAllFromDb();
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    @Override
    protected ProjectStatusTable getObjectFromListById(Integer integer) {
        for (ProjectStatusTable projectStatusTable : repositoryList) {
            if (projectStatusTable.getProject_status_id() == integer) {
                return projectStatusTable;
            }
        }
        return null;
    }
}
