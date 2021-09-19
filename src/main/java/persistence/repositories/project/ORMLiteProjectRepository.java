package persistence.repositories.project;

import persistence.tables.project.ProjectTable;
import persistence.repositories.backends.ormlite.ORMLiteDomainObjectRepository;

import java.util.UUID;

public class ORMLiteProjectRepository extends ORMLiteDomainObjectRepository<ProjectTable, UUID> {


    // Variables


    // Constructors
    public ORMLiteProjectRepository() {
        super();
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    @Override
    protected ProjectTable getObjectFromListById(UUID uuid) {
        for (ProjectTable project: repositoryList) {
            if (project.getProject_uuid().equals(uuid)) {
                return project;
            }
        }
        return null;
    }
}
