package persistence.dto.card;


public class CardDTO extends AbstractCardDTO{


    // Variables
    private final int position;
    private final String dueOnDate;


    // Constructors
     public CardDTO(Builder builder) {
        super(builder);
        this.position = builder.position;
        this.dueOnDate = builder.dueOnDate;
    }



    // Getters and Setters
    public int getPosition() {
        return position;
    }

    public String getDueOnDate() {
        return dueOnDate;
    }

    // Initialisation methods


    // Other methods

    public static class Builder extends AbstractBuilder{

        private int position;
        private String dueOnDate;


        public Builder(String parentColumnUUID) {
            super(parentColumnUUID);
            this.position = 0;
            this.dueOnDate = "";
        }

        public static Builder newInstance(String parentColumnUUID) {
            return new Builder(parentColumnUUID);
        }

        public Builder uuid(String uuid) {
            super.uuid(uuid);
            return this;
        }

        public Builder title(String title) {
            super.title(title);
            return this;
        }

        public Builder description(String description) {
            super.description(description);
            return this;
        }

        public Builder createdOnTimeStamp(String createdOnTimeStamp) {
            super.createdOnTimeStamp(createdOnTimeStamp);
            return this;
        }

        public Builder lastChangedOnTimeStamp(String lastChangedOnTimeStamp) {
            super.lastChangedOnTimeStamp(lastChangedOnTimeStamp);
            return this;
        }

        public Builder position(int position) {
            this.position = position;
            return this;
        }

        public Builder dueOnDate(String dueOnDate) {
            this.dueOnDate = dueOnDate;
            return this;
        }
    }


}
