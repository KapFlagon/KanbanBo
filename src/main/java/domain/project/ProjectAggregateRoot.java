package domain.project;

import exceptions.domain.InvalidTitleException;
import exceptions.domain.InvalidTimestampException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class ProjectAggregateRoot {


    // Variables
    private final UUID id;
    private StringProperty title;
    private StringProperty description;
    private ObjectProperty<ProjectState> projectState;
    private ObjectProperty<LocalDateTime> createdOnTimestamp;
    private ObjectProperty<LocalDateTime> lastChangedTimestamp;
    private ObjectProperty<LocalDateTime> dueDateTimestamp;

    private ObservableList<String> resourcesList;
    private ObservableList<String> tasksList;


    // Constructors
    public ProjectAggregateRoot() {
        this(UUID.randomUUID());
    }

    public ProjectAggregateRoot(UUID id) {
        this.id = id;
        this.title = new SimpleStringProperty();
        this.projectState = new SimpleObjectProperty<>(ProjectState.ACTIVE);
        LocalDateTime now = LocalDateTime.now();
        this.createdOnTimestamp = new SimpleObjectProperty<>(now);
        this.lastChangedTimestamp = new SimpleObjectProperty<>(now);
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) throws InvalidTitleException {
        if (title != null && !title.equalsIgnoreCase("") && !title.equalsIgnoreCase(" ")) {
            this.title.set(title);
        } else {
            throw new InvalidTitleException();
        }
    }

    public Optional<StringProperty> descriptionProperty() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        if(descriptionProperty().isPresent()) {
            this.description.set(description);
        } else {
            this.description = new SimpleStringProperty(description);
        }
    }

    public ObjectProperty<ProjectState> projectStateProperty() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState.set(projectState);
    }

    public ObjectProperty<LocalDateTime> createdOnTimestampProperty() {
        return createdOnTimestamp;
    }

    public void setCreatedOnTimestamp(LocalDateTime createdOnTimestamp) {
        this.createdOnTimestamp.set(createdOnTimestamp);
    }

    public ObjectProperty<LocalDateTime> lastChangedTimestampProperty() {
        return lastChangedTimestamp;
    }

    public void setLastChangedTimestamp(LocalDateTime lastChangedTimestamp) throws InvalidTimestampException {
        if (lastChangedTimestamp.isEqual(createdOnTimestamp.get()) || lastChangedTimestamp.isAfter(createdOnTimestamp.get())) {
            this.lastChangedTimestamp.set(lastChangedTimestamp);
        } else {
            throw new InvalidTimestampException();
        }
    }

    public Optional<ObjectProperty<LocalDateTime>> dueDateTimestampProperty() {
        return Optional.ofNullable(dueDateTimestamp);
    }

    public void setDueDateTimestamp(LocalDateTime newDueDateTimestamp) throws InvalidTimestampException {
        if (newDueDateTimestamp.isAfter(createdOnTimestamp.get())) {
            if (this.dueDateTimestamp != null) {
                this.dueDateTimestamp.set(newDueDateTimestamp);
            } else {
                this.dueDateTimestamp = new SimpleObjectProperty<>(newDueDateTimestamp);
            }
        } else {
            throw new InvalidTimestampException();
        }
    }



    // Initialisation methods

    // Other methods
    public void updateLastChangedTimestamp() {
        if (lastChangedTimestamp != null) {
            Duration duration = Duration.between(createdOnTimestamp.get(), LocalDateTime.now());
            lastChangedTimestamp.get().plus(duration);
        } else {
            lastChangedTimestamp = new SimpleObjectProperty<>(LocalDateTime.now());
        }
    }

}
