package model.domainobjects.relateditems;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class RelatedItemType {


    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int related_item_type_id;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String related_item_type_title;

    // Constructors

    public RelatedItemType() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public int getRelated_item_type_id() {
        return related_item_type_id;
    }
    public void setRelated_item_type_id(int related_item_type_id) {
        this.related_item_type_id = related_item_type_id;
    }

    public String getRelated_item_type_title() {
        return related_item_type_title;
    }
    public void setRelated_item_type_title(String related_item_type_title) {
        this.related_item_type_title = related_item_type_title;
    }


    // Initialisation methods


    // Other methods


}
