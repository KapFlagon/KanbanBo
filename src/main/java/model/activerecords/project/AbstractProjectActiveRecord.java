package model.activerecords.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.activerecords.AbstractActiveRecord;
import model.domainobjects.project.AbstractProjectModel;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractProjectActiveRecord<T extends AbstractProjectModel> extends AbstractActiveRecord {


    // Variables
    protected T abstractProjectModel;
    protected SimpleStringProperty projectTitle;
    protected SimpleStringProperty projectDescription;
    protected SimpleStringProperty creationTimestamp;
    protected SimpleStringProperty lastChangedTimestamp;


    // Constructors
    protected AbstractProjectActiveRecord(Class domainModelClassType) {
        super(domainModelClassType);
    }
    public AbstractProjectActiveRecord(Class domainModelClassType, T abstractProjectModel) {
        super(domainModelClassType);
        setAbstractProjectModel(abstractProjectModel);
    }

    // Getters and Setters
    public T getAbstractProjectModel() {
        return abstractProjectModel;
    }
    public void setAbstractProjectModel(T abstractProjectModel) {
        this.abstractProjectModel = abstractProjectModel;
        //this.initAllProperties();
        //this.setAllListeners();
        //createOrUpdateActiveRowInDb();
    }

    public UUID getProjectUUID() {
        return abstractProjectModel.getProject_uuid();
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
    @Override
    protected void initAllProperties() {
        this.projectTitle = new SimpleStringProperty(abstractProjectModel.getProject_title());
        this.projectDescription = new SimpleStringProperty(abstractProjectModel.getProject_description());
        this.creationTimestamp = new SimpleStringProperty(abstractProjectModel.getCreation_timestamp().toString());
        this.lastChangedTimestamp = new SimpleStringProperty(abstractProjectModel.getLast_changed_timestamp().toString());
    }

    // Other methods
    @Override
    protected void setAllListeners() {
        setProjectTitleListener();
        setProjectDescriptionListener();
        setLastChangedTimestampListener();
    }

    private void setProjectTitleListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                abstractProjectModel.setProject_title(newValue);
                updateLastChangedTimestamp();
            }
        };
        projectTitle.addListener(changeListener);
    }

    private void setProjectDescriptionListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                abstractProjectModel.setProject_description(newValue);
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
        abstractProjectModel.setLast_changed_timestamp(currentTimestamp);
        setLastChangedTimestamp(currentTimestamp.toString());
    }

    public void updateLastChangedTimestamp(Date currentTimestamp) {
        abstractProjectModel.setLast_changed_timestamp(currentTimestamp);
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

    @Override
    public void createOrUpdateActiveRowInDb() throws SQLException, IOException {
        this.setupDbConnection();
        dao.createOrUpdate(abstractProjectModel);
        this.teardownDbConnection();
    }

}
