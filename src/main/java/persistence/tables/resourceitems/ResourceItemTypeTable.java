package persistence.tables.resourceitems;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import persistence.tables.TableObject;

@DatabaseTable(tableName = "resource_item_type")
public class ResourceItemTypeTable implements TableObject<Integer> {

    public static final String TYPE_KEY_NAME = "resource_item_type";

    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int resource_item_type_id;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String resource_item_type_text_key;

    // Constructors

    public ResourceItemTypeTable() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public int getResource_item_type_id() {
        return resource_item_type_id;
    }
    public void setResource_item_type_id(int resource_item_type_id) {
        this.resource_item_type_id = resource_item_type_id;
    }

    public String getResource_item_type_text_key() {
        return resource_item_type_text_key;
    }
    public void setResource_item_type_text_key(String resource_item_type_text_key) {
        this.resource_item_type_text_key = resource_item_type_text_key;
    }


    // Initialisation methods


    // Other methods


    @Override
    public Integer getID() {
        return getResource_item_type_id();
    }
}
