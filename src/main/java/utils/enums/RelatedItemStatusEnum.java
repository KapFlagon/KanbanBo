package utils.enums;

public enum RelatedItemStatusEnum {

    AVAILABLE(1, "related_item.status.available"),
    LINK_BROKEN(2, "related_item.status.link_broken");


    // Variables
    private final int id;
    private final String translationKey;


    // Constructors
    RelatedItemStatusEnum(int id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getTranslationKey() {
        return translationKey;
    }
    // Initialisation methods


    // Other methods


}
