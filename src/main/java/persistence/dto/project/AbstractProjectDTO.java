package persistence.dto.project;

public abstract class AbstractProjectDTO {


    // Variables
    protected final String uuid;
    protected final String title;
    protected final String description;
    protected final String createdOnTimeStamp;
    protected final String lastChangedOnTimeStamp;

    // Constructors
    public AbstractProjectDTO() {
        this.uuid = "";
        this.title = "";
        this.description = "";
        this.createdOnTimeStamp = "";
        this.lastChangedOnTimeStamp = "";
    }

    public AbstractProjectDTO(AbstractBuilder abstractBuilder) {
        this.uuid = abstractBuilder.uuid;
        this.title = abstractBuilder.title;
        this.description = abstractBuilder.description;
        this.createdOnTimeStamp = abstractBuilder.createdOnTimeStamp;
        this.lastChangedOnTimeStamp = abstractBuilder.lastChangedOnTimeStamp;
    }

    public AbstractProjectDTO(String uuid, String title, String description, String createdOnTimeStamp, String lastChangedOnTimeStamp) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.createdOnTimeStamp = createdOnTimeStamp;
        this.lastChangedOnTimeStamp = lastChangedOnTimeStamp;
    }

    // Getters and Setters
    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedOnTimeStamp() {
        return createdOnTimeStamp;
    }

    public String getLastChangedOnTimeStamp() {
        return lastChangedOnTimeStamp;
    }


    // Initialisation methods


    // Other methods

    protected static class AbstractBuilder {

        private String uuid;
        private String title;
        private String description;
        private String createdOnTimeStamp;
        private String lastChangedOnTimeStamp;


        public AbstractBuilder(String uuid) {
            this.uuid = uuid;
            this.title = "";
            this.description = "";
            this.createdOnTimeStamp = "";
            this.lastChangedOnTimeStamp = "";
        }

        public AbstractBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AbstractBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AbstractBuilder createdOnTimeStamp(String createdOnTimeStamp) {
            this.createdOnTimeStamp = createdOnTimeStamp;
            return this;
        }

        public AbstractBuilder lastChangedOnTimeStamp(String lastChangedOnTimeStamp) {
            this.lastChangedOnTimeStamp = lastChangedOnTimeStamp;
            return this;
        }
    }


}
