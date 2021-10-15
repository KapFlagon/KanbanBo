package persistence.dto;

import java.util.UUID;

public class ResourceItemDTO {


    // Variables
    private UUID uuid;
    private UUID parentUUID;
    private String title;
    private int type;
    private String path;


    // Constructors
    public ResourceItemDTO(UUID uuid, UUID parentUUID, String title, int type, String path) {
        this.uuid = uuid;
        this.parentUUID = parentUUID;
        this.title = title;
        this.type = type;
        this.path = path;
    }


    // Getters and Setters
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getParentUUID() {
        return parentUUID;
    }
    public void setParentUUID(UUID parentUUID) {
        this.parentUUID = parentUUID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }


    // Initialisation methods


    // Other methods


}
