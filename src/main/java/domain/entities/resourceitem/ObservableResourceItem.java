package domain.entities.resourceitem;

import domain.entities.AbstractObservableEntity;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import persistence.tables.resourceitems.ResourceItemTable;
import persistence.tables.resourceitems.ResourceItemTypeTable;

import javax.swing.event.ChangeEvent;
import java.util.UUID;

public class ObservableResourceItem extends AbstractObservableEntity<ResourceItemTable> {


    // Variables
    private UUID resourceItemUUID;
    private UUID parentItemUUID;
    private SimpleStringProperty title;
    private SimpleStringProperty description;
    private SimpleStringProperty path;
    private SimpleIntegerProperty type;
    private SimpleStringProperty typeText;


    // Constructors
    public ObservableResourceItem(ResourceItemTable resourceItemTable, String typeText) {
        super();
        this.resourceItemUUID = resourceItemTable.getResource_item_uuid();
        this.parentItemUUID = resourceItemTable.getParent_item_uuid();
        initAllProperties(resourceItemTable, typeText);
        initPropertyListeners();
    }

    public ObservableResourceItem(UUID parentItemUUID, String title, String description, String path, int type, String typeText) {
        super();
        this.resourceItemUUID = UUID.randomUUID();
        this.parentItemUUID = parentItemUUID;
        initAllProperties(title, description, path, type, typeText);
        initPropertyListeners();
    }


    // Getters and Setters
    public UUID getResourceItemUUID() {
        return resourceItemUUID;
    }

    public UUID getParentItemUUID() {
        return parentItemUUID;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public SimpleIntegerProperty typeProperty() {
        return type;
    }

    public SimpleStringProperty typeTextProperty() {
        return typeText;
    }


    // Initialisation methods
    protected void initAllProperties(String title, String description, String path, int type, String typeText) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.path = new SimpleStringProperty(path);
        this.type = new SimpleIntegerProperty(type);
        this.typeText = new SimpleStringProperty(typeText);
    }

    protected void initAllProperties(ResourceItemTable resourceItemTable, String typeText) {
        this.title = new SimpleStringProperty(resourceItemTable.getResource_item_title());
        this.description = new SimpleStringProperty(resourceItemTable.getResource_item_description());
        this.path = new SimpleStringProperty(resourceItemTable.getResource_item_path());
        this.type = new SimpleIntegerProperty(resourceItemTable.getResource_item_type());
        this.typeText = new SimpleStringProperty(typeText);
    }

    protected void initPropertyListeners() {
        this.title.addListener(stringChangeListener);
        this.description.addListener(stringChangeListener);
        this.path.addListener(stringChangeListener);
        this.type.addListener(numberChangeListener);
    }


    // Other methods


}
