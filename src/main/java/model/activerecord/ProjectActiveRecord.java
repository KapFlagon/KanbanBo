package model.activerecord;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.domainobjects.project.AbstractProjectModel;
import utils.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ProjectActiveRecord<T extends AbstractProjectModel> {


    // Variables
    protected Class<T> modelClassType;
    protected T t;
    protected Dao<T, UUID> projectDao;
    protected JdbcConnectionSource connectionSource;
    protected SimpleStringProperty projectUUID;
    protected SimpleStringProperty projectTitle;
    protected SimpleStringProperty creationTimestamp;
    protected SimpleStringProperty lastChangedTimestamp;


    // Constructors
    public void AbstractProjectBridge(Class<T> modelClassType, T t) {
        this.modelClassType = modelClassType;
        this.t = t;
        initAllProperties(t);
        setAllListeners();
    }


    // Getters and Setters
    public T getT() {
        return t;
    }
    public void setT(T t) {
        this.t = t;
    }

    public String getProjectUUID() {
        return projectUUID.get();
    }
    public SimpleStringProperty projectUUIDProperty() {
        return projectUUID;
    }
    public void setProjectUUID(String projectUUID) {
        this.projectUUID.set(projectUUID);
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

    private void initAllProperties(T t) {
        this.setProjectUUID(t.getProject_uuid().toString());
        this.setProjectTitle(t.getProject_title().toString());
        this.setCreationTimestamp(t.getCreation_timestamp().toString());
        this.setLastChangedTimestamp(t.getLast_changed_timestamp().toString());
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
                t.setProject_title(newValue);
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
                t.setLast_changed_timestamp(tempDate);
                try {
                    updateDbEntry();
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

    public void updateDbEntry() throws SQLException, IOException {
        setupDbConnection();
        projectDao.update(t);
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
