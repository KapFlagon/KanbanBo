package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import domain.entities.project.ObservableProject;
import javafx.collections.ObservableList;
import persistence.tables.project.ProjectTable;
import persistence.tables.project.ProjectStatusTable;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProjectManagementService extends AbstractService{


    // TODO Make this the service used by the "manage" tab.
    // Variables
    private ObservableList<ObservableProject> projectDomainObjectsList;
    private Dao<ProjectTable, UUID> projectDao;
    private Dao<ProjectStatusTable, Integer> projectStatusDao;



    // Constructors
    public ProjectManagementService(ObservableList<ObservableProject> projectDomainObjectsList) {
        this.projectDomainObjectsList = projectDomainObjectsList;
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public ObservableList<ObservableProject> getProjectsList() throws SQLException, IOException {
        projectDomainObjectsList.clear();
        List<ProjectTable> projects = getProjectsTableAsList();
        List<ProjectStatusTable> projectStatuses = getProjectStatusTableAsList();
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        for(ProjectTable projectEntry : projects) {
            int statusId = projectEntry.getProject_status_id();
            String localisedStatusText = "";
            for(ProjectStatusTable projectStatus : projectStatuses) {
                if (projectStatus.getProject_status_id() == statusId) {
                    String statusTextKey = projectStatus.getProject_status_text_key();
                    localisedStatusText = resourceBundle.getString(statusTextKey);
                }
            }
            ObservableProject projectDomainObject = new ObservableProject(projectEntry, localisedStatusText);
            projectDomainObject.dataChangePendingProperty().addListener(change -> {
                try {
                    updateProject(projectDomainObject);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            projectDomainObjectsList.add(projectDomainObject);
        }
        /*projectDomainObjectsList.addListener(new ListChangeListener<ObservableProject>() {
            @Override
            public void onChanged(Change<? extends ObservableProject> c) {

            }
        });*/
        return projectDomainObjectsList;
    }


    private List<ProjectTable> getProjectsTableAsList() throws SQLException, IOException {
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectDao.setObjectCache(true);
        List<ProjectTable> projectsList = projectDao.queryForAll();
        teardownDbConnection();
        return projectsList;
    }

    private List<ProjectStatusTable> getProjectStatusTableAsList() throws SQLException, IOException {
        setupDbConnection();
        projectStatusDao = DaoManager.createDao(connectionSource, ProjectStatusTable.class);
        projectStatusDao.setObjectCache(true);
        List<ProjectStatusTable> projectStatusList = projectStatusDao.queryForAll();
        teardownDbConnection();
        return projectStatusList;
    }

    public void createNewProject(ObservableProject projectDomainObject) throws ParseException, SQLException, IOException {
        ProjectTable projectTable = prepareProjectForCommit(projectDomainObject);
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectDao.create(projectTable);
        teardownDbConnection();
        // TODO respond to a failure...
    }

    public void updateProject(ObservableProject projectDomainObject) throws ParseException, SQLException, IOException {
        ProjectTable projectTableData = prepareProjectForCommit(projectDomainObject);
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectDao.update(projectTableData);
        teardownDbConnection();
        // TODO respond to a failure...
    }

    public void deleteProject(ObservableProject projectDomainObject) throws ParseException, SQLException, IOException {
        ProjectTable projectTableData = prepareProjectForCommit(projectDomainObject);
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        projectDao.deleteById(projectTableData.getID());
        teardownDbConnection();
        // TODO respond to a failure...
    }

    private Date stringToDate(String dateTimeValue) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        Date tempDate = null;
        tempDate = dateFormat.parse(dateTimeValue);
        return tempDate;
    }

    private ProjectTable prepareProjectForCommit(ObservableProject projectDomainObject) throws ParseException {
        ProjectTable projectTableData = new ProjectTable();
        projectTableData.setProject_uuid(projectDomainObject.getProjectUUID());
        projectTableData.setCreation_timestamp(stringToDate(projectDomainObject.creationTimestampProperty().getValue()));
        projectTableData.setProject_title(projectDomainObject.projectTitleProperty().getValue());
        projectTableData.setProject_description(projectDomainObject.projectDescriptionProperty().getValue());
        projectTableData.setProject_status_id(projectDomainObject.statusIDProperty().getValue());
        projectTableData.setLast_changed_timestamp(new Date());
        return projectTableData;
    }

}
