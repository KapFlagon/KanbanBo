package model.activerecords;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.domainobjects.column.AbstractProjectColumnModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;


public class ProjectColumnActiveRecord<T extends AbstractProjectColumnModel> extends AbstractActiveRecord{


    // Variables for model objects and DAOs
    protected ProjectActiveRecord parentProjectActiveRecord;
    protected T projectColumnModel;

    // Variables to act as property containers for the model data
    protected SimpleStringProperty columnTitle;

    // Constructors
    public ProjectColumnActiveRecord(Class<T> projectColumnModelClassType) {
        super(projectColumnModelClassType);
    }
    public ProjectColumnActiveRecord(Class<T> projectColumnModelClassType, T projectColumnModel, ProjectActiveRecord parentProjectActiveRecord) {
        super(projectColumnModelClassType);
        this.projectColumnModel = projectColumnModel;
        this.parentProjectActiveRecord = parentProjectActiveRecord;
        initAllProperties();
        setAllListeners();
    }


    // Getters and Setters
    public ProjectActiveRecord getParentProjectActiveRecord() {
        return parentProjectActiveRecord;
    }
    public void setParentProjectActiveRecord(ProjectActiveRecord parentProjectActiveRecord) throws IOException, SQLException {
        this.parentProjectActiveRecord = parentProjectActiveRecord;
        this.projectColumnModel.setParent_project_uuid(parentProjectActiveRecord.getProjectUUID());
        createOrUpdateActiveRowInDb();
    }

    public T getProjectColumnModel() {
        return projectColumnModel;
    }
    public void setProjectColumnModel(T projectColumnModel) {
        this.projectColumnModel = projectColumnModel;
        initAllProperties();
        setAllListeners();
    }

    public String getColumnTitle() {
        return columnTitle.get();
    }
    public SimpleStringProperty columnTitleProperty() {
        return columnTitle;
    }
    public void setColumnTitle(String columnTitle) {
        this.columnTitle.set(columnTitle);
    }

    public UUID getColumnUUID() {
        return projectColumnModel.getColumn_uuid();
    }


    // Initialisation methods
    @Override
    protected void initAllProperties() {
        this.columnTitle = new SimpleStringProperty(projectColumnModel.getColumn_title());
    }


    // Other methods
    @Override
    protected void setAllListeners() {
        setColumnTitleListener();
    }

    private void setColumnTitleListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                projectColumnModel.setColumn_title(newValue);
                try {
                    createOrUpdateActiveRowInDb();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                parentProjectActiveRecord.updateLastChangedTimestamp();
            }
        };
        columnTitle.addListener(changeListener);
    }

    @Override
    public void createOrUpdateActiveRowInDb() throws SQLException, IOException {
        this.setupDbConnection();
        dao.createOrUpdate(projectColumnModel);
        this.teardownDbConnection();
    }
}
