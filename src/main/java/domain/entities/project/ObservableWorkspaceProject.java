package domain.entities.project;

import persistence.dto.project.ProjectDTO;
import domain.entities.column.ObservableColumn;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.List;

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
        statusID = new SimpleIntegerProperty(projectDTO.getStatusId());
        statusText = new SimpleStringProperty(projectStatusText);
        initHasFinalColumnProperty(columns);
        if(projectDTO.getDueOnDate() != null) {
            dueOnDate = new SimpleStringProperty(projectDTO.getDueOnDate().toString());
        }
    }

    public ObservableWorkspaceProject(ProjectDTO projectDTO, ObservableList<ObservableResourceItem> resourceItems, ObservableList<ObservableColumn> columns, String projectStatusText) {
        super(projectDTO, resourceItems, columns);
        statusID = new SimpleIntegerProperty(projectDTO.getStatusId());
        statusText = new SimpleStringProperty(projectStatusText);
        initHasFinalColumnProperty(columns);
        dueOnDate = new SimpleStringProperty(projectDTO.getDueOnDate().toString());
    }


    // Getters and Setters
    public SimpleIntegerProperty statusIDProperty() {
        return statusID;
    }

    public SimpleStringProperty statusTextProperty() {
        return statusText;
    }

    public SimpleStringProperty dueOnDateProperty() {
        return dueOnDate;
    }


    // Initialisation methods
    @Override
    protected void initAllProperties() {
        super.initAllProperties();
        statusID = new SimpleIntegerProperty();
        statusText = new SimpleStringProperty();
        dueOnDate = new SimpleStringProperty();
    }

    protected void initAllProperties(ProjectDTO projectDTO, String projectStatusText){
        super.initAllProperties(projectDTO);
        statusID = new SimpleIntegerProperty(projectDTO.getStatusId());
        statusID.addListener((observable, oldValue, newValue) -> updateEditingProperty());
        statusText = new SimpleStringProperty(projectStatusText);
        dueOnDate = new SimpleStringProperty();
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
