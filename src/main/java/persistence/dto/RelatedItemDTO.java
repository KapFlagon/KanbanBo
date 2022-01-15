package persistence.dto;

import persistence.dto.project.AbstractProjectDTO;
import persistence.dto.project.ProjectDTO;

import java.util.UUID;

public class RelatedItemDTO {


    // Variables
    private final UUID relatedItemUUID;
    private final UUID parentUUID;
    private final String title;
    private final int type;
    private final String path;


    // Constructors
    public RelatedItemDTO(Builder builder) {
        this.relatedItemUUID = UUID.fromString(builder.relatedItemUUID);
        this.parentUUID = UUID.fromString(builder.parentItemUUID);
        this.title = builder.title;
        this.type = builder.type;
        this.path = builder.path;
    }


    // Getters and Setters
    public UUID getRelatedItemUUID() {
        return relatedItemUUID;
    }

    public UUID getParentUUID() {
        return parentUUID;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public String getPath() {
        return path;
    }


    // Initialisation methods


    // Other methods


    public static class Builder {

        private String relatedItemUUID;
        private String parentItemUUID;
        private String title;
        private int type;
        private String path;

        public Builder(String parentItemUUID) {
            this.relatedItemUUID = "";
            this.parentItemUUID = parentItemUUID;
            this.title = "";
            this.type = 1;
            this.path = "";
        }

        public static Builder newInstance(String uuid) {
            return new Builder(uuid);
        }

        public Builder relatedItemUUID(String relatedItemUUID) {
            this.relatedItemUUID = relatedItemUUID;
            return this;
        }

        public Builder parentItemUUID(String parentItemUUID) {
            this.parentItemUUID = parentItemUUID;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }
    }

}
