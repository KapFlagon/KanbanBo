package persistence.tables.resourceitems;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import persistence.tables.TableObject;

import java.util.UUID;

@DatabaseTable(tableName = "resource_item")
public class ResourceItemTable implements TableObject<UUID> {

    public static final String FOREIGN_KEY_NAME = "parent_item_uuid";
    public static final String TYPE_COLUMN_NAME = "resource_item_type";
    public static final String PATH_COLUMN_NAME = "resource_item_path";

    // Variables
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID resource_item_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String resource_item_title;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int resource_item_type;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String resource_item_path;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String resource_item_description;
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID parent_item_uuid;


    // Constructors
    public ResourceItemTable() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public UUID getResource_item_uuid() {
        return resource_item_uuid;
    }
    public void setResource_item_uuid(UUID resource_item_uuid) {
        this.resource_item_uuid = resource_item_uuid;
    }

    public int getResource_item_type() {
        return resource_item_type;
    }
    public void setResource_item_type(int resource_item_type) {
        this.resource_item_type = resource_item_type;
    }

    public String getResource_item_title() {
        return resource_item_title;
    }
    public void setResource_item_title(String resource_item_title) {
        this.resource_item_title = resource_item_title;
    }

    public String getResource_item_path() {
        return resource_item_path;
    }
    public void setResource_item_path(String resource_item_path) {
        this.resource_item_path = resource_item_path;
    }

    public String getResource_item_description() {
        return resource_item_description;
    }
    public void setResource_item_description(String resource_item_description) {
        this.resource_item_description = resource_item_description;
    }

    public UUID getParent_item_uuid() {
        return parent_item_uuid;
    }
    public void setParent_item_uuid(UUID parent_item_uuid) {
        this.parent_item_uuid = parent_item_uuid;
    }


    // Initialisation methods

    // Other methods
    public UUID getID() {
        return getResource_item_uuid();
    }

}
