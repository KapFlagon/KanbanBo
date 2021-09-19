package utils;

public enum ProjectStatusEnum {

    ACTIVE (1),
    COMPLETED (2),
    HIATUS (3),
    CANCELLED (4);

    private int id;

    ProjectStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
