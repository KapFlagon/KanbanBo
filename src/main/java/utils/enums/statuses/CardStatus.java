package utils.enums.statuses;

public enum CardStatus {
    ACTIVE("Active"),
    ARCHIVED("Archived");

    private final String description;

    CardStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}