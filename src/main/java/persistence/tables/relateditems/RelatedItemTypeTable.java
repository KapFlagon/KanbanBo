package persistence.tables.relateditems;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import persistence.tables.TableObject;

@DatabaseTable(tableName = "related_item_type")
public class RelatedItemTypeTable implements TableObject<Integer> {

    public static final String TYPE_KEY_NAME = "related_item_type_id";

    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int related_item_type_id;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String related_item_type_text_key;

    // Constructors

    public RelatedItemTypeTable() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public int getRelated_item_type_id() {
        return related_item_type_id;
    }
    public void setRelated_item_type_id(int related_item_type_id) {
        this.related_item_type_id = related_item_type_id;
    }

    public String getRelated_item_type_text_key() {
        return related_item_type_text_key;
    }
    public void setRelated_item_type_text_key(String related_item_type_text_key) {
        this.related_item_type_text_key = related_item_type_text_key;
    }


    // Initialisation methods


    // Other methods


    @Override
    public Integer getID() {
        return getRelated_item_type_id();
    }
}
