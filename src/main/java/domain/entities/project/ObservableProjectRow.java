package domain.entities.project;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import persistence.dto.project.ProjectDTO;
import java.util.UUID;


public class ObservableProjectRow{


    // Variables
    protected UUID projectUUID;
    protected SimpleStringProperty projectTitle;
    protected SimpleStringProperty projectDescription;
    protected SimpleStringProperty creationTimestamp;
    protected SimpleStringProperty lastChangedTimestamp;
    protected SimpleBooleanProperty hasFinalColumn;
    protected SimpleIntegerProperty statusID;
    protected SimpleStringProperty statusText;
    protected SimpleStringProperty dueOnDate;


    // Constructors
    public ObservableProjectRow(ProjectDTO projectDTO, String projectStatusText) {
        projectUUID = projectDTO.getUuid();
        initAllProperties(projectDTO, projectStatusText);
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

    public SimpleIntegerProperty statusIDProperty() {
        return statusID;
    }

    public SimpleStringProperty statusTextProperty() {
        return statusText;
    }


    // Initialisation methods
    protected void initAllProperties() {
        this.projectTitle = new SimpleStringProperty();
        this.projectDescription = new SimpleStringProperty();
        this.creationTimestamp = new SimpleStringProperty();
        this.lastChangedTimestamp = new SimpleStringProperty();
        this.hasFinalColumn = new SimpleBooleanProperty();
        this.statusID = new SimpleIntegerProperty();
        this.statusText = new SimpleStringProperty();
        this.dueOnDate = new SimpleStringProperty();
    }


    protected void initAllProperties(ProjectDTO projectDTO, String projectStatusText){
        this.projectTitle = new SimpleStringProperty(projectDTO.getTitle());
        this.projectDescription = new SimpleStringProperty(projectDTO.getDescription());
        this.creationTimestamp = new SimpleStringProperty(projectDTO.getCreatedOnTimeStamp().toString());
        this.lastChangedTimestamp = new SimpleStringProperty(projectDTO.getLastChangedOnTimeStamp().toString());
        this.hasFinalColumn = new SimpleBooleanProperty(false);
        this.statusID = new SimpleIntegerProperty(projectDTO.getStatus());
        this.statusText = new SimpleStringProperty(projectStatusText);
        if(projectDTO.getDueOnDate() != null) {
            this.dueOnDate = new SimpleStringProperty(projectDTO.getDueOnDate().toString());
        }
    }


    // Other methods


}
