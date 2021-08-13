package model.repositories.template.project;

import javafx.collections.ObservableList;
import model.domainobjects.project.Project;
import model.domainobjects.project.TemplateProject;
import model.repositories.backends.ormlite.ORMLiteDomainObjectRepository;


import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class ORMLiteTemplateProjectRepository extends ORMLiteDomainObjectRepository<TemplateProject, UUID> {

    // Variables


    // Constructors
    public ORMLiteTemplateProjectRepository() throws SQLException, IOException {
        super();
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    @Override
    public ObservableList<TemplateProject> getAll() throws SQLException, IOException {
        readAllFromDb();
        return repositoryList;
    }

    @Override
    public TemplateProject getByID(UUID uuid) throws SQLException, IOException {
        if (repositoryList.size() == 0){
            readAllFromDb();
        }
        for (TemplateProject templateProject: repositoryList) {
            if (templateProject.getProject_uuid().equals(uuid)) {
                return templateProject;
            }
        }
        return null;
    }
}
