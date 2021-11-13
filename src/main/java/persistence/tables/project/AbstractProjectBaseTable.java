package persistence.tables.project;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;

import persistence.tables.TableObject;

import java.util.UUID;

@Entity(name = "abstract_project")
public abstract class AbstractProjectBaseTable implements TableObject<UUID> {


    // Variables
    @Id
    private UUID project_uuid;
    @Column(nullable = false)
    private String title;
    @Column(nullable = true)
    private String description;
    @Column(nullable = false)
    private String creation_timestamp;
    @Column(nullable = false)
    private String last_changed_timestamp;


    // Constructors
    public AbstractProjectBaseTable() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    // Getters and Setters
    public UUID getProject_uuid() {
        return project_uuid;
    }
    public void setProject_uuid(UUID project_uuid) {
        this.project_uuid = project_uuid;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreation_timestamp() {
        return creation_timestamp;
    }
    public void setCreation_timestamp(String creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public String getLast_changed_timestamp() {
        return last_changed_timestamp;
    }
    public void setLast_changed_timestamp(String last_changed_timestamp) {
        this.last_changed_timestamp = last_changed_timestamp;
    }

    // Initialisation methods


    // Other methods
    public UUID getID() {
        return getProject_uuid();
    }
}
