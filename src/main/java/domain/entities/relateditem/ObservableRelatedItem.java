package domain.entities.relateditem;

import domain.entities.AbstractObservableEntity;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import persistence.tables.relateditems.RelatedItemTable;

import java.util.UUID;

public class ObservableRelatedItem extends AbstractObservableEntity<RelatedItemTable> {


    // Variables
    private UUID relatedItemUUID;
    private UUID parentItemUUID;
    private SimpleStringProperty title;
    private SimpleIntegerProperty type;
    private SimpleStringProperty path;


    // Constructors
    public ObservableRelatedItem(RelatedItemTable relatedItemTable, String typeText) {
        super();
        this.relatedItemUUID = relatedItemTable.getRelated_item_uuid();
        this.parentItemUUID = relatedItemTable.getParent_item_uuid();
        initAllProperties(relatedItemTable, typeText);
    }

    public ObservableRelatedItem(UUID parentItemUUID, UUID relatedItem, String title, String path, int type, String typeText) {
        super();
        this.relatedItemUUID = relatedItem;
        this.parentItemUUID = parentItemUUID;
        initAllProperties(title, path, type, typeText);
    }


    // Getters and Setters
    public UUID getRelatedItemUUID() {
        return relatedItemUUID;
    }

    public UUID getParentItemUUID() {
        return parentItemUUID;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public SimpleIntegerProperty typeProperty() {
        return type;
    }


    // Initialisation methods
    protected void initAllProperties(String title, String path, int type, String typeText) {
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleIntegerProperty(type);
        this.path = new SimpleStringProperty(path);
    }

    protected void initAllProperties(RelatedItemTable relatedItemTable, String typeText) {
        this.title = new SimpleStringProperty(relatedItemTable.getRelated_item_title());
        this.type = new SimpleIntegerProperty(relatedItemTable.getRelated_item_type());
        this.path = new SimpleStringProperty(relatedItemTable.getRelated_item_path());
    }


    // Other methods


}
