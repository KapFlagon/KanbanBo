package persistence.dto.card;

import java.util.ArrayList;
import java.util.UUID;

public abstract class AbstractCardDTO {


    // Variables
    private UUID uuid;
    private UUID parentColumnUUID;
    private String title;
    private String description;


    // Constructors
    public AbstractCardDTO() {
        this.uuid = null;
        this.parentColumnUUID = null;
        this.title = "";
        this.description = "";
    }

    public AbstractCardDTO(UUID uuid, UUID parentColumnUUID, String title, String description) {
        this.uuid = uuid;
        this.parentColumnUUID = parentColumnUUID;
        this.title = title;
        this.description = description;
    }


    // Getters and Setters
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getParentColumnUUID() {
        return parentColumnUUID;
    }
    public void setParentColumnUUID(UUID parentColumnUUID) {
        this.parentColumnUUID = parentColumnUUID;
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


    // Initialisation methods


    // Other methods


}
