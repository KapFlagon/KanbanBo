package model.activerecords.project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.domainobjects.project.ProjectModel;

import java.io.IOException;
import java.sql.SQLException;


public class ProjectActiveRecord extends AbstractProjectActiveRecord {

    // Variables
    protected ProjectModel projectModel;
    protected SimpleStringProperty statusText;
    protected SimpleIntegerProperty statusID;


    // Constructors
    /*public ProjectActiveRecord(Class<T> modelClassType) {
        super(modelClassType);
    }
    public ProjectActiveRecord(Class<T> modelClassType, ProjectModel projectModel) {
        super(modelClassType);
        this.projectModel = projectModel;
        this.initAllProperties();
        this.setAllListeners();
    }*/
    public ProjectActiveRecord() {
        super(ProjectModel.class);
    }
    public ProjectActiveRecord(ProjectModel projectModel) throws SQLException, IOException {
        super(ProjectModel.class, projectModel);
        this.projectModel = projectModel;
        this.initAllProperties();
        updateStatusText();
        this.setAllListeners();
    }


    // Getters and Setters
    public void setProjectModel(ProjectModel projectModel) throws IOException, SQLException {
        super.setAbstractProjectModel(projectModel);
        this.projectModel = projectModel;
        this.initAllProperties();
        updateStatusText();
        this.setAllListeners();
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

    public int getStatusID() {
        return statusID.get();
    }
    public SimpleIntegerProperty statusIDProperty() {
        return statusID;
    }
    public void setStatusID(int statusID) {
        this.statusID.set(statusID);
    }


    // Initialisation methods
    protected void initAllProperties() {
        super.initAllProperties();
        statusText = new SimpleStringProperty();
        statusID = new SimpleIntegerProperty(projectModel.getProject_status());
    }


    // Other methods
    @Override
    protected void setAllListeners() {
        super.setAllListeners();
        setStatusChangeListener();
    }


    private void setStatusChangeListener() {
        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                projectModel.setProject_status((Integer) newValue);
                try {
                    statusText.set(updateStatusText());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateLastChangedTimestamp();
            }
        };
        statusID.addListener(changeListener);
    }

    private String updateStatusText() throws SQLException, IOException {
        String value = "";
        ProjectStatusActiveRecord psar = new ProjectStatusActiveRecord();
        value = psar.getStatusByID(statusID.getValue());
        statusText.set(value);
        return value;
    }


}
