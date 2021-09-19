package domain.activerecords.project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import domain.activerecords.AbstractActiveRecord;
import persistence.tables.project.ProjectStatusTable;

import java.io.IOException;
import java.sql.SQLException;

public class ProjectStatusActiveRecord extends AbstractActiveRecord {

    // Variables
    private ProjectStatusTable projectStatusTable;
    protected SimpleIntegerProperty statusID;
    protected SimpleStringProperty statusText;

    // Constructors
    protected ProjectStatusActiveRecord() {
        super(ProjectStatusTable.class);
    }
    public ProjectStatusActiveRecord(ProjectStatusTable projectStatusTable) {
        super(ProjectStatusTable.class);
        this.projectStatusTable = projectStatusTable;
    }
    /*protected ProjectStatusActiveRecord(Class domainModelClassType) {
        super(domainModelClassType);
    }*/
    /*public ProjectStatusActiveRecord(Class domainModelClassType, ProjectStatusModel projectStatusModel) {
        super(domainModelClassType);
        initAllProperties();
        setAllListeners();
    }*/

    // Getters and Setters
    public ProjectStatusTable getStatusModel() {
        return projectStatusTable;
    }
    public void setStatusModel(ProjectStatusTable projectStatusTable) {
        this.projectStatusTable = projectStatusTable;
    }

    public int getStatusID() {
        return statusID.get();
    }
    public SimpleIntegerProperty statusIDProperty() {
        return statusID;
    }
    public void setStatusID(int statusID) {
        this.statusID.set(statusID);
    }

    public String getStatusText() {
        return statusText.get();
    }
    public SimpleStringProperty statusTextProperty() {
        return statusText;
    }
    public void setStatusText(String statusText) {
        this.statusText.set(statusText);
    }

    // Initialisation methods
    @Override
    protected void initAllProperties() {
        statusID = new SimpleIntegerProperty(projectStatusTable.getProject_status_id());
        statusText = new SimpleStringProperty(projectStatusTable.getProject_status_text_key());
    }

    // Other methods
    @Override
    protected void setAllListeners() {
        setStatusIDChangeListener();
        setStatusTextChangeListener();
        }

    private void setStatusIDChangeListener() {

    }

    private void setStatusTextChangeListener() {

    }

    @Override
    public void createOrUpdateActiveRowInDb() throws SQLException, IOException {
        this.setupDbConnection();
        dao.createOrUpdate(projectStatusTable);
        this.teardownDbConnection();
    }

    public String getStatusByID(int statusID) throws SQLException, IOException {
        setupDbConnection();
        projectStatusTable = (ProjectStatusTable) dao.queryForId(statusID);
        //QueryBuilder<ProjectStatusModel, Integer> queryBuilder = dao.queryBuilder();
        //queryBuilder.where().eq(ProjectStatusModel.PRIMARY_KEY, statusID);
        //PreparedQuery<ProjectStatusModel> preparedQuery = queryBuilder.prepare();
        //projectStatusModel = (ProjectStatusModel) dao.queryForFirst(preparedQuery);
        teardownDbConnection();
        return projectStatusTable.getProject_status_text_key();
    }

}
