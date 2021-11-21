package persistence.dto.card;


public abstract class AbstractCardDTO {


    // Variables
    private final String uuid;
    private final String parentColumnUUID;
    private final String title;
    private final String description;
    private final String createdOnTimeStamp;
    private final String lastChangedOnTimeStamp;


    // Constructors

    public AbstractCardDTO(AbstractBuilder abstractBuilder) {
        this.uuid = abstractBuilder.uuid;
        this.parentColumnUUID = abstractBuilder.parentColumnUUID;
        this.title = abstractBuilder.title;
        this.description = abstractBuilder.description;
        this.createdOnTimeStamp = abstractBuilder.createdOnTimeStamp;
        this.lastChangedOnTimeStamp = abstractBuilder.lastChangedOnTimeStamp;
    }


    // Getters and Setters
    public String getUuid() {
        return uuid;
    }

    public String getParentColumnUUID() {
        return parentColumnUUID;
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
        private String parentColumnUUID;
        private String title;
        private String description;
        private String createdOnTimeStamp;
        private String lastChangedOnTimeStamp;


        protected AbstractBuilder(String parentColumnUUID) {
            this.uuid = "";
            this.parentColumnUUID = parentColumnUUID;
            this.title = "";
            this.description = "";
            this.createdOnTimeStamp = "";
            this.lastChangedOnTimeStamp = "";
        }

        public AbstractBuilder uuid(String uuid) {
            this.uuid = uuid;
            return this;
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
