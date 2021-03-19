package model.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import java.sql.SQLException;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.domainobjects.project.ActiveProjectModel;
import model.domainobjects.project.ArchivedProjectModel;
import model.domainobjects.project.CompletedProjectModel;
import model.domainobjects.project.TemplateProjectModel;
import utils.DatabaseUtils;

public class ProjectRepository implements IRepository{

    private JdbcConnectionSource connectionSource;
    private Dao<ActiveProjectModel, UUID> activeProjectModelDao;
    private Dao<ArchivedProjectModel, UUID> archivedProjectModelDao;
    private Dao<CompletedProjectModel, UUID> completedProjectModelDao;
    private ObservableList<ActiveProjectModel> activeProjectsList;
    private ObservableList<ArchivedProjectModel> archivedProjectsList;
    private ObservableList<CompletedProjectModel> completedProjectsList;
    private ObservableList<TemplateProjectModel> templateProjectsList;

    // Variables


    // Constructors
    public ProjectRepository() {
        initLists();
    }
    public ProjectRepository(ObservableList<ActiveProjectModel> activeProjectsList, ObservableList<ArchivedProjectModel> archivedProjectsList, ObservableList<CompletedProjectModel> completedProjectsList, ObservableList<TemplateProjectModel> templateProjectsList) {
        setActiveProjectsList(activeProjectsList);
        setArchivedProjectsList(archivedProjectsList);
        setCompletedProjectsList(completedProjectsList);
        setTemplateProjectsList(templateProjectsList);
    }


    // Getters and Setters
    public ObservableList<ActiveProjectModel> getActiveProjectsList() {
        return activeProjectsList;
    }
    public void setActiveProjectsList(ObservableList<ActiveProjectModel> activeProjectsList) {
        this.activeProjectsList = activeProjectsList;
    }

    public ObservableList<ArchivedProjectModel> getArchivedProjectsList() {
        return archivedProjectsList;
    }
    public void setArchivedProjectsList(ObservableList<ArchivedProjectModel> archivedProjectsList) {
        this.archivedProjectsList = archivedProjectsList;
    }

    public ObservableList<CompletedProjectModel> getCompletedProjectsList() {
        return completedProjectsList;
    }
    public void setCompletedProjectsList(ObservableList<CompletedProjectModel> completedProjectsList) {
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
    @Override
    public ObservableList getAllInList() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
        activeProjectModelDao = DaoManager.createDao(connectionSource, ActiveProjectModel.class);
        long rowCount = activeProjectModelDao.countOf();
        if (rowCount > 0) {
            for (ActiveProjectModel activeProjectModel : activeProjectModelDao) {
                activeProjectsList.add(activeProjectModel);
            }
        }
        return activeProjectsList;
    }

    @Override
    public ObservableList getAllChildLists() {
        return null;
    }
}
