package domain.entities.project;

import domain.entities.column.ObservableColumn;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistence.tables.project.ProjectTable;

public class ObservableProject extends AbstractObservableProjectBase<ProjectTable, ObservableColumn>{


    // Variables
    protected SimpleIntegerProperty statusID;
    protected SimpleStringProperty statusText;
    protected SimpleBooleanProperty hasFinalColumn;


    // Constructors
    public ObservableProject(String title, String description) {
        super(title, description);
        initEditingProperty();
        initHasFinalColumnProperty();
    }


    public ObservableProject(ProjectTable projectDomainObject, String projectStatusText) {
        super(projectDomainObject);
        initStatusTextProperty(projectStatusText);
        initEditingProperty();
        initHasFinalColumnProperty();
    }

    public ObservableProject(ProjectTable projectDomainObject, String projectStatusText, ObservableList<ObservableResourceItem> resourceItems, ObservableList<ObservableColumn> columns) {
        super(projectDomainObject, resourceItems, columns);
        initStatusTextProperty(projectStatusText);
        initEditingProperty();
        initHasFinalColumnProperty(columns);
    }


    // Getters and Setters
    public SimpleIntegerProperty statusIDProperty() {
        return statusID;
    }

    public SimpleStringProperty statusTextProperty() {
        return statusText;
    }




    // Initialisation methods
    @Override
    protected void initAllProperties() {
        super.initAllProperties();
        statusID = new SimpleIntegerProperty();
        statusText = new SimpleStringProperty();
    }

    @Override
    protected void initAllProperties(ProjectTable projectDomainObject) {
        super.initAllProperties(projectDomainObject);
        statusID = new SimpleIntegerProperty(projectDomainObject.getProject_status_id());
    }

    protected void initAllProperties(ProjectTable projectDomainObject, String projectStatusText){
        super.initAllProperties(projectDomainObject);
        statusID = new SimpleIntegerProperty(projectDomainObject.getProject_status_id());
    }

    protected void initStatusTextProperty(String projectStatusText) {
        statusText = new SimpleStringProperty(projectStatusText);
    }

    protected void initEditingProperty() {
        if (statusID.get() == 2 || statusID.get() == 4) {
            editingPermittedProperty().set(false);
        }
    }

    protected void initHasFinalColumnProperty() {
        hasFinalColumn = new SimpleBooleanProperty(false);
    }

    protected void initHasFinalColumnProperty(ObservableList<ObservableColumn> columnList) {
        initHasFinalColumnProperty();
        for(ObservableColumn column : columnList) {
            if (column.isFinalColumn()) {
                hasFinalColumn.set(true);
            }
        }
    }

    @Override
    protected void initPropertyListeners() {
        super.initPropertyListeners();
        statusID.addListener(numberChangeListener);
        // TODO Finish this: Theoretically will update the last changed time

    }

    @Override
    protected void initAllObservableListListeners() {
        super.initAllObservableListListeners();
        columns.addListener(new ListChangeListener<domain.entities.column.ObservableColumn>() {
            @Override
            public void onChanged(Change<? extends domain.entities.column.ObservableColumn> c) {
                // TODO figure out what to update here...

            }
        });
    }

    // Other methods

}
