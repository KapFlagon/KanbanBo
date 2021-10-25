package persistence.dto.project;

import java.time.ZonedDateTime;
import java.util.UUID;

public abstract class AbstractProjectDTO {


    // Variables
    private UUID uuid;
    private String title;
    private String description;
    private ZonedDateTime createdOnTimeStamp;
    private ZonedDateTime lastChangedOnTimeStamp;

    // Constructors
    public AbstractProjectDTO() {
        this.uuid = null;
        this.title = "";
        this.description = "";
        this.createdOnTimeStamp = null;
        this.lastChangedOnTimeStamp = null;
    }

    public AbstractProjectDTO(UUID uuid, String title, String description, ZonedDateTime createdOnTimeStamp, ZonedDateTime lastChangedOnTimeStamp) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.createdOnTimeStamp = createdOnTimeStamp;
        this.lastChangedOnTimeStamp = lastChangedOnTimeStamp;
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

    public ZonedDateTime getCreatedOnTimeStamp() {
        return createdOnTimeStamp;
    }
    public void setCreatedOnTimeStamp(ZonedDateTime createdOnTimeStamp) {
        this.createdOnTimeStamp = createdOnTimeStamp;
    }

    public ZonedDateTime getLastChangedOnTimeStamp() {
        return lastChangedOnTimeStamp;
    }
    public void setLastChangedOnTimeStamp(ZonedDateTime lastChangedOnTimeStamp) {
        this.lastChangedOnTimeStamp = lastChangedOnTimeStamp;
    }


    // Initialisation methods


    // Other methods


}
