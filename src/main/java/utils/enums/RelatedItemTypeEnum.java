package utils.enums;

public enum RelatedItemTypeEnum {

    DIRECTORY (1,"related_item.type.directory"),
    FILE (2, "related_item.type.file"),
    URL_PATH (3, "related_item.type.path_or_url"),
    CHILD_PROJECT (4, "related_item.type.child_project"),
    PARENT_CARD (5, "related_item.type.parent_card");

    private final int id;
    private final String translationKey;

    RelatedItemTypeEnum(int id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
    }

    public int getId() {
        return id;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public static RelatedItemTypeEnum getTypeFromId(int id) {
        switch(id) {
            case 1:
                return RelatedItemTypeEnum.DIRECTORY;
            case 2:
                return RelatedItemTypeEnum.FILE;
            case 3:
                return RelatedItemTypeEnum.URL_PATH;
            case 4:
                return RelatedItemTypeEnum.CHILD_PROJECT;
            case 5:
                return RelatedItemTypeEnum.PARENT_CARD;
            default:
                return RelatedItemTypeEnum.URL_PATH;
        }
    }
}
