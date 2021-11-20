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


        public Builder(String uuid, String parentColumnUUID) {
            super(uuid, parentColumnUUID);
            this.position = 0;
            this.dueOnDate = "";
        }

        public static Builder newInstance() {
            return newInstance("","");
        }

        public static Builder newInstance(String uuid, String parentColumnUUID) {
            return new Builder(uuid, parentColumnUUID);
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
