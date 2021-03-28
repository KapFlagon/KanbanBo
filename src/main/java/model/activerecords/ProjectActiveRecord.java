package model.activerecords;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.domainobjects.project.AbstractProjectModel;
import utils.database.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ProjectActiveRecord<T extends AbstractProjectModel> {


    // Variables
    protected Class<T> modelClassType;
    protected T projectModel;
    protected Dao<T, UUID> projectDao;
    // TODO decide whether or not to use a SimpleStringProperty to bridge the UUID.
    protected JdbcConnectionSource connectionSource;
    protected SimpleStringProperty projectTitle;
    protected SimpleStringProperty creationTimestamp;
    protected SimpleStringProperty lastChangedTimestamp;


    // Constructors
    public ProjectActiveRecord(Class<T> modelClassType) {
        this.modelClassType = modelClassType;
    }
    public ProjectActiveRecord(Class<T> modelClassType, T projectModel) {
        this.modelClassType = modelClassType;
        this.projectModel = projectModel;
        initAllProperties();
        setAllListeners();
    }


    // Getters and Setters
    public T getProjectModel() {
        return projectModel;
    }
    public void setProjectModel(T projectModel) throws IOException, SQLException {
        this.projectModel = projectModel;
        initAllProperties();
        setAllListeners();
        createOrUpdateActiveRowInDb();
        // TODO lazily create UUID (when table entry is made), or actively create it here when detecting a new object?
    }

    public UUID getProjectUUID() {
        return projectModel.getProject_uuid();
    }

    public String getProjectTitle() {
        return projectTitle.get();
    }
    public SimpleStringProperty projectTitleProperty() {
        return projectTitle;
    }
    public void setProjectTitle(String projectTitle) {
        this.projectTitle.set(projectTitle);
    }

    public String getCreationTimestamp() {
        return creationTimestamp.get();
    }
    public SimpleStringProperty creationTimestampProperty() {
        return creationTimestamp;
    }
    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp.set(creationTimestamp);
    }

    public String getLastChangedTimestamp() {
        return lastChangedTimestamp.get();
    }
    public SimpleStringProperty lastChangedTimestampProperty() {
        return lastChangedTimestamp;
    }
    public void setLastChangedTimestamp(String lastChangedTimestamp) {
        this.lastChangedTimestamp.set(lastChangedTimestamp);
    }

    // Initialisation methods
    private void initConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }
    private void initDao() throws SQLException {
        projectDao = DaoManager.createDao(connectionSource, modelClassType);
    }

    private void initAllProperties() {
        //this.projectUUID = new SimpleStringProperty(projectModel.getProject_uuid().toString());
        this.projectTitle = new SimpleStringProperty(projectModel.getProject_title());
        this.creationTimestamp = new SimpleStringProperty(projectModel.getCreation_timestamp().toString());
        this.lastChangedTimestamp = new SimpleStringProperty(projectModel.getLast_changed_timestamp().toString());
    }



    // Other methods
    private void setAllListeners() {
        setProjectTitleListener();
        setLastChangedTimestampListener();
    }

    private void setProjectTitleListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                projectModel.setProject_title(newValue);
                updateLastChangedTimestamp();
            }
        };
        projectTitle.addListener(changeListener);
    }

    private void setLastChangedTimestampListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Date tempDate = stringToDate(newValue);
                projectModel.setLast_changed_timestamp(tempDate);
                try {
                    createOrUpdateActiveRowInDb();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        creationTimestamp.addListener(changeListener);
    }

    public void updateLastChangedTimestamp() {
        Date currentTimestamp = new Date();
        setLastChangedTimestamp(currentTimestamp.toString());
    }

    public void updateLastChangedTimestamp(Date currentTimestamp) {
        setLastChangedTimestamp(currentTimestamp.toString());
    }

    private Date stringToDate(String dateTimeValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        Date tempDate = null;
        try {
            tempDate = dateFormat.parse(dateTimeValue);
        } catch (ParseException e) {
            // TODO proper error handling/display to user
            e.printStackTrace();
        }
        return tempDate;
    }

    public void createOrUpdateActiveRowInDb() throws SQLException, IOException {
        setupDbConnection();
        projectDao.createOrUpdate(projectModel);
        teardownDbConnection();
    }

    private void setupDbConnection() throws SQLException {
        initConnectionSource();
        initDao();
    }

    private void teardownDbConnection() throws IOException {
        connectionSource.close();
    }



}
