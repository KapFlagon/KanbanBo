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

        private Builder(String uuid, String parentProjectUUID) {
            super(uuid, parentProjectUUID);
            this.position = 0;
        }

        public static Builder newInstance() {
            return newInstance("", "");
        }

        public static Builder newInstance(String uuid, String parentProjectUUID) {
            return new Builder(uuid, parentProjectUUID);
        }

        public Builder position(int position) {
            this.position = position;
            return this;
        }
    }

}
