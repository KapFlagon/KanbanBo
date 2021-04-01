package model.domainobjects.column;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "abstract_column")
public abstract class AbstractColumnModel {


    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID column_uuid;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String column_title;

    // Constructors
    public AbstractColumnModel() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public UUID getColumn_uuid() {
        return column_uuid;
    }
    public void setColumn_uuid(UUID column_uuid) {
        this.column_uuid = column_uuid;
    }

    public String getColumn_title() {
        return column_title;
    }
    public void setColumn_title(String column_title) {
        this.column_title = column_title;
    }


    // Initialisation methods


    // Other methods


}
