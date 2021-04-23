package utils.enums.statuses;

public enum ColumnStatus {
    ACTIVE("Active"),
    ARCHIVED("Archived");

    private final String description;

    ColumnStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
