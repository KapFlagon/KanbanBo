package model.activerecords;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.domainobjects.project.AbstractProjectModel;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class ProjectActiveRecord<T extends AbstractProjectModel> extends AbstractActiveRecord{


    // Variables for model objects and DAOs
    protected T projectModel;
    //protected Dao<T, UUID> projectDao;
    // Variables to act as property containers for the model data
    protected SimpleStringProperty projectTitle;
    protected SimpleStringProperty projectDescription;
    protected SimpleStringProperty creationTimestamp;
    protected SimpleStringProperty lastChangedTimestamp;


    // Constructors
    public ProjectActiveRecord(Class<T> modelClassType) {
        super(modelClassType);
    }
    public ProjectActiveRecord(Class<T> modelClassType, T projectModel) {
        super(modelClassType);
        this.projectModel = projectModel;
        this.initAllProperties();
        this.setAllListeners();
    }


    // Getters and Setters
    public T getProjectModel() {
        return projectModel;
    }
    public void setProjectModel(T projectModel) throws IOException, SQLException {
        this.projectModel = projectModel;
        this.initAllProperties();
        this.setAllListeners();
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

    public String getProjectDescription() {
        return projectDescription.get();
    }
    public SimpleStringProperty projectDescriptionProperty() {
        return projectDescription;
    }
    public void setProjectDescription(String projectDescription) {
        this.projectDescription.set(projectDescription);
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
    protected void initAllProperties() {
        this.projectTitle = new SimpleStringProperty(projectModel.getProject_title());
        this.projectDescription = new SimpleStringProperty(projectModel.getProject_description());
        this.creationTimestamp = new SimpleStringProperty(projectModel.getCreation_timestamp().toString());
        this.lastChangedTimestamp = new SimpleStringProperty(projectModel.getLast_changed_timestamp().toString());
    }



    // Other methods
    protected void setAllListeners() {
        setProjectTitleListener();
        setProjectDescriptionListener();
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

    private void setProjectDescriptionListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                projectModel.setProject_description(newValue);
                updateLastChangedTimestamp();
            }
        };
        projectDescription.addListener(changeListener);
    }

    private void setLastChangedTimestampListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    createOrUpdateActiveRowInDb();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        lastChangedTimestamp.addListener(changeListener);
    }

    public void updateLastChangedTimestamp() {
        Date currentTimestamp = new Date();
        projectModel.setLast_changed_timestamp(currentTimestamp);
        setLastChangedTimestamp(currentTimestamp.toString());
    }

    public void updateLastChangedTimestamp(Date currentTimestamp) {
        projectModel.setLast_changed_timestamp(currentTimestamp);
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
        this.setupDbConnection();
        dao.createOrUpdate(projectModel);
        this.teardownDbConnection();
    }

    // TODO make new project building the responsibility of this class




}
