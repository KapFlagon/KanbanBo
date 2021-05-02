package model.activerecords.project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.domainobjects.project.ProjectModel;

import java.io.IOException;
import java.sql.SQLException;


public class ProjectActiveRecord<T extends ProjectModel> extends AbstractProjectActiveRecord {

    // Variables
    protected ProjectModel projectModel;
    protected SimpleIntegerProperty status;


    // Constructors
    public ProjectActiveRecord(Class<T> modelClassType) {
        super(modelClassType);
    }
    public ProjectActiveRecord(Class<T> modelClassType, ProjectModel projectModel) {
        super(modelClassType);
        this.projectModel = projectModel;
        this.initAllProperties();
        this.setAllListeners();
    }


    // Getters and Setters
    public void setProjectModel(ProjectModel projectModel) throws IOException, SQLException {
        this.projectModel = projectModel;
        super.setAbstractProjectModel(projectModel);
    }

    public int getStatus() {
        return status.get();
    }
    public SimpleIntegerProperty statusProperty() {
        return status;
    }
    public void setStatus(int status) {
        this.status.set(status);
    }


    // Initialisation methods
    protected void initAllProperties() {
        super.initAllProperties();
        status = new SimpleIntegerProperty(projectModel.getProject_status());
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
                updateLastChangedTimestamp();
            }
        };
        status.addListener(changeListener);
    }

    // TODO make new project building the responsibility of this class




}
