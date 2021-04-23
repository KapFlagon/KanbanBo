package utils.enums.statuses;

public enum ProjectStatus {
    ACTIVE("Active"),
    ARCHIVED("Archived"),
    COMPLETED("Completed");

    private final String description;

    ProjectStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}