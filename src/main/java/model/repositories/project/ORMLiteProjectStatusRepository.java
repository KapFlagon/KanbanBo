package model.repositories.project;

import model.domainobjects.project.ProjectStatus;
import model.repositories.backends.ormlite.ORMLiteDomainObjectRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ORMLiteProjectStatusRepository extends ORMLiteDomainObjectRepository<ProjectStatus, Integer> {


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
    public ProjectStatus getByID(Integer id) throws SQLException, IOException {
        for (ProjectStatus projectStatus: repositoryList) {
            if (id.equals(projectStatus.getProject_status_id())) {
                return projectStatus;
            }
        }
        return null;
    }
}
