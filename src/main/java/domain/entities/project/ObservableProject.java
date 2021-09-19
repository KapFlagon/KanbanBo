package domain.entities.project;

import domain.entities.column.ObservableColumn;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistence.tables.project.ProjectTable;

import java.util.Date;

public class ObservableProject extends AbstractObservableProjectBase<ProjectTable, domain.entities.column.ObservableColumn>{


    // Variables
    protected SimpleIntegerProperty statusID;
    protected SimpleStringProperty statusText;


    // Constructors
    public ObservableProject(String title, String description) {
        super(title, description);
    }


    public ObservableProject(ProjectTable projectDomainObject, String projectStatusText) {
        super(projectDomainObject);
        initProjectStatusProperty(projectDomainObject, projectStatusText);
    }

    public ObservableProject(ProjectTable projectDomainObject, String projectStatusText, ObservableList<ObservableResourceItem> resourceItems, ObservableList<domain.entities.column.ObservableColumn> columns) {
        super(projectDomainObject, resourceItems, columns);
        initProjectStatusProperty(projectDomainObject, projectStatusText);
    }


    // Getters and Setters
    public SimpleIntegerProperty statusIDProperty() {
        return statusID;
    }

    public SimpleStringProperty statusTextProperty() {
        return statusText;
    }


    // Initialisation methods
    protected void initAllProperties() {
        super.initAllProperties();
        statusID = new SimpleIntegerProperty();
        statusText = new SimpleStringProperty();
    }

    protected void initProjectStatusProperty(ProjectTable projectTableObject, String projectStatusText) {
        super.initAllProperties(projectTableObject);
        statusID = new SimpleIntegerProperty(projectTableObject.getProject_status_id());
        if (statusID.get() == 2 || statusID.get() == 4) {
            editingPermittedProperty().set(false);
        }
        statusText = new SimpleStringProperty(projectStatusText);
    }

    @Override
    protected void initPropertyListeners() {
        super.initPropertyListeners();
        statusID.addListener(numberChangeListener);
        // TODO Finish this: Theoretically will update the last changed time
        columns.addListener(new ListChangeListener<domain.entities.column.ObservableColumn>() {
            @Override
            public void onChanged(Change<? extends domain.entities.column.ObservableColumn> c) {
                lastChangedTimestamp.set(new Date().toString());
            }
        });
    }


    // Other methods

}
