package model.domainobjects.subitems;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "resource_item")
public class ResourceItem {


    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID resource_item_uuid;
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID parent_item_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String resource_item_title;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String resource_item_type;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String resource_item_location;


    // Constructors
    public ResourceItem() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public UUID getResource_item_uuid() {
        return resource_item_uuid;
    }
    public void setResource_item_uuid(UUID resource_item_uuid) {
        this.resource_item_uuid = resource_item_uuid;
    }

    public UUID getParent_item_uuid() {
        return parent_item_uuid;
    }
    public void setParent_item_uuid(UUID parent_item_uuid) {
        this.parent_item_uuid = parent_item_uuid;
    }

    public String getResource_item_title() {
        return resource_item_title;
    }
    public void setResource_item_title(String resource_item_title) {
        this.resource_item_title = resource_item_title;
    }

    public String getResource_item_type() {
        return resource_item_type;
    }
    public void setResource_item_type(String resource_item_type) {
        this.resource_item_type = resource_item_type;
    }

    public String getResource_item_location() {
        return resource_item_location;
    }
    public void setResource_item_location(String resource_item_location) {
        this.resource_item_location = resource_item_location;
    }


    // Initialisation methods


    // Other methods


}
