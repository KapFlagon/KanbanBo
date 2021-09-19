package utils;

public enum ResourceItemTypeEnum {

    DIRECTORY (1),
    FILE (2),
    URL_PATH (3),
    CHILD_PROJECT (4),
    PARENT_CARD (5);

    private final int id;

    ResourceItemTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
