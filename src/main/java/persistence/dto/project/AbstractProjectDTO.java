package persistence.dto.project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public abstract class AbstractProjectDTO {


    // Variables
    private UUID uuid;
    private String title;
    private String description;
    private LocalDateTime createdOnDate;
    private LocalDateTime lastChangedOnDate;

    // Constructors
    public AbstractProjectDTO() {
        this.uuid = null;
        this.title = "";
        this.description = "";
        this.createdOnDate = null;
        this.lastChangedOnDate = null;
    }

    public AbstractProjectDTO(UUID uuid, String title, String description, LocalDateTime createdOnDate, LocalDateTime lastChangedOnDate) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.createdOnDate = createdOnDate;
        this.lastChangedOnDate = lastChangedOnDate;
    }

    // Getters and Setters
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public LocalDateTime getCreatedOnDate() {
        return createdOnDate;
    }
    public void setCreatedOnDate(LocalDateTime createdOnDate) {
        this.createdOnDate = createdOnDate;
    }

    public LocalDateTime getLastChangedOnDate() {
        return lastChangedOnDate;
    }
    public void setLastChangedOnDate(LocalDateTime lastChangedOnDate) {
        this.lastChangedOnDate = lastChangedOnDate;
    }


    // Initialisation methods


    // Other methods


}
