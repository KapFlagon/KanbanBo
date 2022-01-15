package utils.enums;

public enum ProjectStatusEnum {

    ACTIVE (1, "project.status.active"),
    COMPLETED (2,"project.status.completed"),
    HIATUS (3, "project.status.hiatus"),
    CANCELLED (4, "project.status.cancelled");

    private final int id;
    private final String translationKey;

    ProjectStatusEnum(int id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
    }

    public int getId() {
        return id;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}
