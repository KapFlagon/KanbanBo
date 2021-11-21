package persistence.dto.column;


public class ColumnDTO extends AbstractColumnDTO{


    // Variables
    private final int position;



    // Constructors
    public ColumnDTO(Builder builder) {
        super(builder);
        this.position = builder.position;
    }


    // Getters and Setters
    public int getPosition() {
        return position;
    }

    // Initialisation methods


    // Other methods

    public static class Builder extends AbstractBuilder {

        private int position;

        private Builder(String parentProjectUUID) {
            super(parentProjectUUID);
            this.position = 0;
        }

        public static Builder newInstance(String parentProjectUUID) {
            return new Builder(parentProjectUUID);
        }

        public Builder uuid(String uuid) {
            super.uuid(uuid);
            return this;
        }

        public Builder title(String title) {
            this.title(title);
            return this;
        }

        public Builder finalColumn(boolean finalColumn) {
            this.finalColumn(finalColumn);
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

        public Builder position(int position) {
            this.position = position;
            return this;
        }
    }

}
