package model.activerecords.project;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.activerecords.AbstractActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.ProjectStatusModel;
import model.domainobjects.column.ColumnModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ProjectStatusActiveRecord extends AbstractActiveRecord {

    // Variables
    private ProjectStatusModel projectStatusModel;
    protected SimpleIntegerProperty statusID;
    protected SimpleStringProperty statusText;

    // Constructors
    protected ProjectStatusActiveRecord() {
        super(ProjectStatusModel.class);
    }
    public ProjectStatusActiveRecord(ProjectStatusModel projectStatusModel) {
        super(ProjectStatusModel.class);
        this.projectStatusModel = projectStatusModel;
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
    public ProjectStatusModel getStatusModel() {
        return projectStatusModel;
    }
    public void setStatusModel(ProjectStatusModel projectStatusModel) {
        this.projectStatusModel = projectStatusModel;
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
        statusID = new SimpleIntegerProperty(projectStatusModel.getStatus_id());
        statusText = new SimpleStringProperty(projectStatusModel.getStatus_text());
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
        dao.createOrUpdate(projectStatusModel);
        this.teardownDbConnection();
    }

    public String getStatusByID(int statusID) throws SQLException, IOException {
        setupDbConnection();
        projectStatusModel = (ProjectStatusModel) dao.queryForId(statusID);
        //QueryBuilder<ProjectStatusModel, Integer> queryBuilder = dao.queryBuilder();
        //queryBuilder.where().eq(ProjectStatusModel.PRIMARY_KEY, statusID);
        //PreparedQuery<ProjectStatusModel> preparedQuery = queryBuilder.prepare();
        //projectStatusModel = (ProjectStatusModel) dao.queryForFirst(preparedQuery);
        teardownDbConnection();
        return projectStatusModel.getStatus_text();
    }

}
