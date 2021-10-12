package domain.entities.project;

import domain.entities.column.AbstractObservableColumnBase;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistence.tables.project.AbstractProjectBaseTable;
import domain.entities.AbstractObservableEntity;

import java.util.UUID;

public class AbstractObservableProjectBase<T extends AbstractProjectBaseTable, U extends AbstractObservableColumnBase> extends AbstractObservableEntity<T> {


    // Variables
    protected UUID projectUUID;
    protected SimpleStringProperty projectTitle;
    protected SimpleStringProperty projectDescription;
    protected SimpleStringProperty creationTimestamp;
    protected SimpleStringProperty lastChangedTimestamp;
    //protected SimpleStringProperty dueOnDate; // TODO RESUME HERE
    protected ObservableList<ObservableResourceItem> resourceItems;
    protected ObservableList<U> columns;


    // Constructors
    public AbstractObservableProjectBase(String title, String description) {
        super();
        projectUUID = UUID.randomUUID();
        initAllProperties(title, description);
        initAllObservableLists();
    }


    public AbstractObservableProjectBase(T projectDomainObject) {
        super();
        projectUUID = projectDomainObject.getProject_uuid();
        initAllProperties(projectDomainObject);
        initAllObservableLists();
    }

    public AbstractObservableProjectBase(T projectDomainObject, ObservableList<ObservableResourceItem> resourceItems, ObservableList<U> columnsList) {
        super();
        projectUUID = projectDomainObject.getProject_uuid();
        initAllProperties(projectDomainObject);
        initAllObservableLists(resourceItems, columnsList);
    }

    // Getters and Setters
    public UUID getProjectUUID() {
        return projectUUID;
    }

    public SimpleStringProperty projectTitleProperty() {
        return projectTitle;
    }

    public SimpleStringProperty projectDescriptionProperty() {
        return projectDescription;
    }

    public SimpleStringProperty creationTimestampProperty() {
        return creationTimestamp;
    }

    public SimpleStringProperty lastChangedTimestampProperty() {
        return lastChangedTimestamp;
    }

    public ObservableList<ObservableResourceItem> getResourceItems() {
        return resourceItems;
    }
    public void setResourceItems(ObservableList<ObservableResourceItem> resourceItems) {
        this.resourceItems = resourceItems;
    }

    public ObservableList<U> getColumns() {
        return columns;
    }
    public void setColumns(ObservableList<U> columns) {
        this.columns = columns;
    }

    // Initialisation methods
    protected void initAllProperties() {
        this.projectTitle = new SimpleStringProperty();
        this.projectDescription = new SimpleStringProperty();
        this.creationTimestamp = new SimpleStringProperty();
        this.lastChangedTimestamp = new SimpleStringProperty();
    }

    protected void initAllProperties(String title, String description) {
        this.projectTitle = new SimpleStringProperty(title);
        this.projectDescription = new SimpleStringProperty(description);
        this.creationTimestamp = new SimpleStringProperty();
        this.lastChangedTimestamp = new SimpleStringProperty();
    }

    protected void initAllProperties(T projectDomainObject) {
        this.projectTitle = new SimpleStringProperty(projectDomainObject.getProject_title());
        this.projectDescription = new SimpleStringProperty(projectDomainObject.getProject_description());
        this.creationTimestamp = new SimpleStringProperty(projectDomainObject.getCreation_timestamp().toString());
        this.lastChangedTimestamp = new SimpleStringProperty(projectDomainObject.getLast_changed_timestamp().toString());
    }

    protected void initAllObservableLists() {
        this.resourceItems = FXCollections.observableArrayList();
        this.columns = FXCollections.observableArrayList();
    }

    protected void initAllObservableLists(ObservableList<ObservableResourceItem> resourceItems, ObservableList<U> columnsList) {
        this.resourceItems = resourceItems;
        this.columns = columnsList;
    }


    // Other methods



}
