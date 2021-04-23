package model.domainobjects.column;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "abstract_column")
public abstract class AbstractColumnModel {

    // Constant for query building using database fields
    public static final String FOREIGN_KEY_NAME = "parent_project_uuid";

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

    // Establishing contract for extending classes so that they have some sort of access for a parent UUID and position.
    // This will allow for behaviour differences in each extending class.
    public abstract UUID getParent_project_uuid();
    public abstract void setParent_project_uuid(UUID parent_project_uuid);

    public abstract int getColumn_position();
    public abstract void setColumn_position(int column_position);


    // Initialisation methods


    // Other methods


}
