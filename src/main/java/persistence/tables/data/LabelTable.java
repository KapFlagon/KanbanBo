package persistence.tables.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import persistence.tables.TableObject;

@DatabaseTable(tableName = "label")
public class LabelTable implements TableObject<Integer> {

    // Variables
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int label_id;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String label_title;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String label_description;


    // Constructors
    public LabelTable() {
    }

    // Getters and Setters
    public int getLabel_id() {
        return label_id;
    }
    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    public String getLabel_title() {
        return label_title;
    }
    public void setLabel_title(String label_title) {
        this.label_title = label_title;
    }

    public String getLabel_description() {
        return label_description;
    }
    public void setLabel_description(String label_description) {
        this.label_description = label_description;
    }

    @Override
    public Integer getID() {
        return label_id;
    }


    // Initialisation methods


    // Other methods


}
