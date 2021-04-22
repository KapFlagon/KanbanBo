package model.repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import java.sql.SQLException;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.domainobjects.project.ProjectModel;
import model.domainobjects.project.TemplateProjectModel;
import utils.database.DatabaseUtils;

public class ProjectDataRepository {//implements IRepository{

    private JdbcConnectionSource connectionSource;
    private Dao<ProjectModel, UUID> activeProjectModelDao;
    private ObservableList<ProjectModel> activeProjectsList;
    private ObservableList<ProjectModel> archivedProjectsList;
    private ObservableList<ProjectModel> completedProjectsList;
    private ObservableList<TemplateProjectModel> templateProjectsList;

    // Variables


    // Constructors
    public ProjectDataRepository() {
        initLists();
    }
    public ProjectDataRepository(ObservableList<ProjectModel> activeProjectsList, ObservableList<ProjectModel> archivedProjectsList, ObservableList<ProjectModel> completedProjectsList, ObservableList<TemplateProjectModel> templateProjectsList) {
        setActiveProjectsList(activeProjectsList);
        setArchivedProjectsList(archivedProjectsList);
        setCompletedProjectsList(completedProjectsList);
        setTemplateProjectsList(templateProjectsList);
    }


    // Getters and Setters
    public ObservableList<ProjectModel> getActiveProjectsList() {
        return activeProjectsList;
    }
    public void setActiveProjectsList(ObservableList<ProjectModel> activeProjectsList) {
        this.activeProjectsList = activeProjectsList;
    }

    public ObservableList<ProjectModel> getArchivedProjectsList() {
        return archivedProjectsList;
    }
    public void setArchivedProjectsList(ObservableList<ProjectModel> archivedProjectsList) {
        this.archivedProjectsList = archivedProjectsList;
    }

    public ObservableList<ProjectModel> getCompletedProjectsList() {
        return completedProjectsList;
    }
    public void setCompletedProjectsList(ObservableList<ProjectModel> completedProjectsList) {
        this.completedProjectsList = completedProjectsList;
    }

    public ObservableList<TemplateProjectModel> getTemplateProjectsList() {
        return templateProjectsList;
    }
    public void setTemplateProjectsList(ObservableList<TemplateProjectModel> templateProjectsList) {
        this.templateProjectsList = templateProjectsList;
    }


    // Initialisation methods
    private void initLists() {
        activeProjectsList = FXCollections.observableArrayList();
        archivedProjectsList = FXCollections.observableArrayList();
        completedProjectsList = FXCollections.observableArrayList();
    }

    private void initTempConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }

    // Other methods
    //@Override
    public ObservableList getAllInList() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
        activeProjectModelDao = DaoManager.createDao(connectionSource, ProjectModel.class);
        long rowCount = activeProjectModelDao.countOf();
        if (rowCount > 0) {
            for (ProjectModel projectModel : activeProjectModelDao) {
                activeProjectsList.add(projectModel);
            }
        }
        return activeProjectsList;
    }

    //@Override
    public ObservableList getAllChildLists() {
        return null;
    }
}
