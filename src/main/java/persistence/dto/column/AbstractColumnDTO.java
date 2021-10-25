package persistence.dto.column;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

public abstract class AbstractColumnDTO {


    // Variables
    private UUID uuid;
    private UUID parentProjectUUID;
    private String title;
    private boolean finalColumn;
    private ZonedDateTime createdOnTimeStamp;
    private ZonedDateTime lastChangedOnTimeStamp;


    // Constructors
    public AbstractColumnDTO() {
        this.uuid = null;
        this.parentProjectUUID = null;
        this.title = "";
        this.finalColumn = false;
    }

    public AbstractColumnDTO(UUID uuid, UUID parentProjectUUID, String title, boolean finalColumn) {
        this.uuid = uuid;
        this.parentProjectUUID = parentProjectUUID;
        this.title = title;
        this.finalColumn = finalColumn;
    }

    // Getters and Setters
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getParentProjectUUID() {
        return parentProjectUUID;
    }
    public void setParentProjectUUID(UUID parentProjectUUID) {
        this.parentProjectUUID = parentProjectUUID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFinalColumn() {
        return finalColumn;
    }
    public void setFinalColumn(boolean finalColumn) {
        this.finalColumn = finalColumn;
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
