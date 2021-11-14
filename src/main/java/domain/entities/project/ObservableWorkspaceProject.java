package domain.entities.project;

import persistence.dto.project.ProjectDTO;
import domain.entities.column.ObservableColumn;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.UUID;

public class ObservableWorkspaceProject extends AbstractObservableProjectBase<ProjectDTO, ObservableColumn>{


    // Variables
    protected SimpleIntegerProperty statusID;
    protected SimpleStringProperty statusText;
    protected SimpleStringProperty dueOnDate;


    // Constructors
    protected ObservableWorkspaceProject(ObservableWorkspaceProjectBuilder observableWorkspaceProjectBuilder) {
        super(observableWorkspaceProjectBuilder);
        this.statusID = new SimpleIntegerProperty(observableWorkspaceProjectBuilder.statusIDValue);
        this.statusText = new SimpleStringProperty(observableWorkspaceProjectBuilder.statusTextValue);
        this.dueOnDate = new SimpleStringProperty(observableWorkspaceProjectBuilder.dueOnDateValue);
    }

    public ObservableWorkspaceProject(ProjectDTO projectDTO, String projectStatusText) {
        super(projectDTO);
        statusText = new SimpleStringProperty(projectStatusText);
        initHasFinalColumnProperty(columns);
        dueOnDate = new SimpleStringProperty();
    }

    public ObservableWorkspaceProject(ProjectDTO projectDTO, ObservableList<ObservableResourceItem> resourceItems, ObservableList<ObservableColumn> columns, String projectStatusText) {
        super(projectDTO, resourceItems, columns);
        statusText = new SimpleStringProperty(projectStatusText);
        initHasFinalColumnProperty(columns);
        dueOnDate = new SimpleStringProperty();
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

    protected void initAllProperties(ProjectDTO projectDTO, String projectStatusText){
        super.initAllProperties(projectDTO);
        statusID = new SimpleIntegerProperty(projectDTO.getStatus());
        statusID.addListener((observable, oldValue, newValue) -> updateEditingProperty());
        statusText = new SimpleStringProperty(projectStatusText);
    }

    protected void initHasFinalColumnProperty(ObservableList<ObservableColumn> columnList) {
        hasFinalColumn = new SimpleBooleanProperty(false);
        updateHasFinalColumnProperty(columnList);
        hasFinalColumn.addListener((changeable, oldValue, newValue) -> updateHasFinalColumnProperty(columnList));
    }


    // Other methods
    private void updateEditingProperty() {
        if (statusID.get() == 2 || statusID.get() == 4) {
            editingPermittedProperty().set(false);
        }
    }

    private void updateHasFinalColumnProperty(List<ObservableColumn> columnList) {
        for(ObservableColumn column : columnList) {
            if (column.isFinalColumn()) {
                hasFinalColumn.set(true);
            }
        }
    }

    public static class ObservableWorkspaceProjectBuilder extends AbstractProjectBuilder<ObservableColumn>{
        private int statusIDValue;
        private String statusTextValue;
        private String dueOnDateValue;

        private ObservableWorkspaceProjectBuilder() {
            super();
            this.statusIDValue = 1;
            this.statusTextValue = "";
            this.dueOnDateValue = "";
        }

        public static ObservableWorkspaceProjectBuilder newInstance() {
            return new ObservableWorkspaceProjectBuilder();
        }

        public ObservableWorkspaceProjectBuilder statusID(int statusID) {
            this.statusIDValue = statusID;
            return this;
        }

        public ObservableWorkspaceProjectBuilder statusText(String statusText) {
            this.statusTextValue = statusText;
            return this;
        }

        public ObservableWorkspaceProjectBuilder dueOnDate(String dueOnDate) {
            this.dueOnDateValue = dueOnDate;
            return this;
        }
    }

}
