package model.domainobjects.relateditems;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import model.domainobjects.DomainObjectWithUUID;

import java.util.UUID;

@DatabaseTable(tableName = "related_item")
public class RelatedItem implements DomainObjectWithUUID {

    public static final String FOREIGN_KEY_NAME = "parent_item_uuid";

    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID related_item_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int related_item_type;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String related_item_title;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String related_item_path;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String related_item_description;
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID parent_item_uuid;


    // Constructors
    public RelatedItem() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public UUID getRelated_item_uuid() {
        return related_item_uuid;
    }
    public void setRelated_item_uuid(UUID related_item_uuid) {
        this.related_item_uuid = related_item_uuid;
    }

    public int getRelated_item_type() {
        return related_item_type;
    }
    public void setRelated_item_type(int related_item_type) {
        this.related_item_type = related_item_type;
    }

    public String getRelated_item_title() {
        return related_item_title;
    }
    public void setRelated_item_title(String related_item_title) {
        this.related_item_title = related_item_title;
    }

    public String getRelated_item_path() {
        return related_item_path;
    }
    public void setRelated_item_path(String related_item_path) {
        this.related_item_path = related_item_path;
    }

    public String getRelated_item_description() {
        return related_item_description;
    }
    public void setRelated_item_description(String related_item_description) {
        this.related_item_description = related_item_description;
    }

    public UUID getParent_item_uuid() {
        return parent_item_uuid;
    }
    public void setParent_item_uuid(UUID parent_item_uuid) {
        this.parent_item_uuid = parent_item_uuid;
    }


    // Initialisation methods

    // Other methods
    @Override
    public UUID get_uuid() {
        return getRelated_item_uuid();
    }

}
