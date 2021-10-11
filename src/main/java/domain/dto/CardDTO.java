package domain.dto;

import java.util.List;
import java.util.UUID;

public class CardDTO {


    // Variables
    private UUID uuid;
    private UUID parentColumnUUID;
    private String title;
    private String description;
    private int position;
    private List<ResourceItemDTO> resourcesList;


    // Constructors
    public CardDTO(UUID uuid, UUID parentColumnUUID, String title, String description, int position, List<ResourceItemDTO> resourcesList) {
        this.uuid = uuid;
        this.parentColumnUUID = parentColumnUUID;
        this.title = title;
        this.description = description;
        this.position = position;
        this.resourcesList = resourcesList;
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

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public List<ResourceItemDTO> getResourcesList() {
        return resourcesList;
    }
    public void setResourcesList(List<ResourceItemDTO> resourcesList) {
        this.resourcesList = resourcesList;
    }


    // Initialisation methods


    // Other methods


}
