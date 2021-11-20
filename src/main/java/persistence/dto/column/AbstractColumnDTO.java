package persistence.dto.column;


public abstract class AbstractColumnDTO {


    // Variables
    private final String uuid;
    private final String parentProjectUUID;
    private final String title;
    private final boolean finalColumn;
    private final String createdOnTimeStamp;
    private final String lastChangedOnTimeStamp;


    // Constructors
    public AbstractColumnDTO(AbstractBuilder abstractBuilder) {
        this.uuid = abstractBuilder.uuid;
        this.parentProjectUUID = abstractBuilder.parentProjectUUID;
        this.title = abstractBuilder.title;
        this.finalColumn = abstractBuilder.finalColumn;
        this.createdOnTimeStamp = abstractBuilder.createdOnTimeStamp;
        this.lastChangedOnTimeStamp = abstractBuilder.lastChangedOnTimeStamp;
    }


    // Getters and Setters
    public String getUuid() {
        return uuid;
    }

    public String getParentProjectUUID() {
        return parentProjectUUID;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFinalColumn() {
        return finalColumn;
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
        private String parentProjectUUID;
        private String title;
        private boolean finalColumn;
        private String createdOnTimeStamp;
        private String lastChangedOnTimeStamp;


        protected AbstractBuilder(String uuid, String parentProjectUUID) {
            this.uuid = uuid;
            this.parentProjectUUID = parentProjectUUID;
            this.title = "";
            this.finalColumn = false;
            this.createdOnTimeStamp = "";
            this.lastChangedOnTimeStamp = "";
        }

        public AbstractBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AbstractBuilder finalColumn(boolean finalColumn) {
            this.finalColumn = finalColumn;
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
