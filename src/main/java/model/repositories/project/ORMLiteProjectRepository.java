package model.repositories.project;

import model.domainobjects.project.Project;
import model.repositories.backends.ormlite.ORMLiteDomainObjectRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class ORMLiteProjectRepository extends ORMLiteDomainObjectRepository<Project, UUID> {


    // Variables


    // Constructors
    public ORMLiteProjectRepository() throws SQLException, IOException {
        super();
        readAllFromDb();
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    @Override
    public Project getByID(UUID uuid) throws SQLException, IOException {
        if (repositoryList.size() == 0){
            readAllFromDb();
        }
        for (Project project: repositoryList) {
            if (project.getProject_uuid().equals(uuid)) {
                return project;
            }
        }
        return null;
    }
}
