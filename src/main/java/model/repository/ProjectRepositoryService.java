package model.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.domainobjects.project.*;

import java.io.IOException;
import java.sql.SQLException;

public class ProjectRepositoryService {


    // Variables
    private ProjectListRepository<ActiveProjectModel> activeProjectsRepository;
    private ProjectListRepository<ArchivedProjectModel> archivedProjectsRepository;
    private ProjectListRepository<CompletedProjectModel> completedProjectsRepository;
    private ProjectListRepository<TemplateProjectModel> templateProjectsRepository;
    private ObservableList<AbstractProjectModel> openedProjects;

    // Constructors
    public ProjectRepositoryService() throws IOException, SQLException {
        initAllData();
    }


    // Getters and Setters
    public ProjectListRepository<ActiveProjectModel> getActiveProjectsRepository() {
        return activeProjectsRepository;
    }

    public ProjectListRepository<ArchivedProjectModel> getArchivedProjectsRepository() {
        return archivedProjectsRepository;
    }

    public ProjectListRepository<CompletedProjectModel> getCompletedProjectsRepository() {
        return completedProjectsRepository;
    }

    public ProjectListRepository<TemplateProjectModel> getTemplateProjectsRepository() {
        return templateProjectsRepository;
    }

    public ObservableList<AbstractProjectModel> getOpenedProjects() {
        return openedProjects;
    }


    // Initialisation methods
    public void initAllData() throws IOException, SQLException {
        initActiveProjectsRepository();
        initArchivedProjectsRepository();
        initCompletedProjectsRepository();
        initTemplateProjectsRepository();
        initOpenedProjects();
    }

    public void initActiveProjectsRepository() throws IOException, SQLException {
        activeProjectsRepository = new ProjectListRepository<ActiveProjectModel>(ActiveProjectModel.class);
        activeProjectsRepository.getAllItemsAsList();
    }

    public void initArchivedProjectsRepository() throws IOException, SQLException {
        archivedProjectsRepository = new ProjectListRepository<ArchivedProjectModel>(ArchivedProjectModel.class);
        archivedProjectsRepository.getAllItemsAsList();
    }

    public void initCompletedProjectsRepository() throws IOException, SQLException {
        completedProjectsRepository = new ProjectListRepository<CompletedProjectModel>(CompletedProjectModel.class);
        completedProjectsRepository.getAllItemsAsList();
    }

    public void initTemplateProjectsRepository() throws IOException, SQLException {
        templateProjectsRepository = new ProjectListRepository<TemplateProjectModel>(TemplateProjectModel.class);
        templateProjectsRepository.getAllItemsAsList();
    }

    public void initOpenedProjects() {
        openedProjects = FXCollections.observableArrayList();
    }

    // Other methods


}