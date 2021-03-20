package model.domainobjects.subitems;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "linked_item")
public class LinkedItem {


    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID linked_item_uuid;
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID parent_item_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String linked_item_title;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String linked_item_type;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String linked_item_location;


    // Constructors
    public LinkedItem() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public UUID getLinked_item_uuid() {
        return linked_item_uuid;
    }
    public void setLinked_item_uuid(UUID linked_item_uuid) {
        this.linked_item_uuid = linked_item_uuid;
    }

    public UUID getParent_item_uuid() {
        return parent_item_uuid;
    }
    public void setParent_item_uuid(UUID parent_item_uuid) {
        this.parent_item_uuid = parent_item_uuid;
    }

    public String getLinked_item_title() {
        return linked_item_title;
    }
    public void setLinked_item_title(String linked_item_title) {
        this.linked_item_title = linked_item_title;
    }

    public String getLinked_item_type() {
        return linked_item_type;
    }
    public void setLinked_item_type(String linked_item_type) {
        this.linked_item_type = linked_item_type;
    }

    public String getLinked_item_location() {
        return linked_item_location;
    }
    public void setLinked_item_location(String linked_item_location) {
        this.linked_item_location = linked_item_location;
    }


    // Initialisation methods


    // Other methods


}
