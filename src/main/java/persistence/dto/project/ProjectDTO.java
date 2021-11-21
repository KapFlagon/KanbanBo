package persistence.dto.project;


public class ProjectDTO extends AbstractProjectDTO{


    // Variables
    private final int statusId;
    private final String dueOnDate;


    // Constructors
    public ProjectDTO(Builder builder) {
        super(builder);
        this.statusId = builder.statusId;
        this.dueOnDate = builder.dueOnDate;
    }


    // Getters and Setters
    public int getStatusId() {
        return statusId;
    }

    public String getDueOnDate() {
        return dueOnDate;
    }


    // Initialisation methods


    // Other methods

    public static class Builder extends AbstractBuilder {

        private int statusId;
        private String dueOnDate;

        public Builder(String uuid) {
            super(uuid);
            this.statusId = 1;
            this.dueOnDate = "";
        }

        public static Builder newInstance() {
            return newInstance("");
        }

        public static Builder newInstance(String uuid) {
            return new Builder(uuid);
        }

        public Builder uuid(String uuid) {
            super.uuid(uuid);
            return this;
        }

        public Builder title(String title) {
            this.title(title);
            return this;
        }

        public Builder description(String description) {
            this.description(description);
            return this;
        }

        public Builder createdOnTimeStamp(String createdOnTimeStamp) {
            this.createdOnTimeStamp(createdOnTimeStamp);
            return this;
        }

        public Builder lastChangedOnTimeStamp(String lastChangedOnTimeStamp) {
            this.lastChangedOnTimeStamp(lastChangedOnTimeStamp);
            return this;
        }

        public Builder statusId(int statusId) {
            this.statusId = statusId;
            return this;
        }

        public Builder dueOnDate(String dueOnDate) {
            this.dueOnDate = dueOnDate;
            return this;
        }
    }

}
