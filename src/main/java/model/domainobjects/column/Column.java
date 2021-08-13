package model.domainobjects.column;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;


@DatabaseTable(tableName = "column")
public class Column extends AbstractColumn {


    // Variables
    // Same fields as Column, but cannot be null in database.
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true, columnName = FOREIGN_KEY_NAME)
    private UUID parent_project_uuid;
    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int column_status;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int column_position;

    // Constructors
    public Column() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public UUID getParent_project_uuid() {
        return parent_project_uuid;
    }
    public void setParent_project_uuid(UUID parent_project_uuid) {
        this.parent_project_uuid = parent_project_uuid;
    }

    public int getColumn_status() {
        return column_status;
    }
    public void setColumn_status(int column_status) {
        this.column_status = column_status;
    }

    public int getColumn_position() {
        return column_position;
    }
    public void setColumn_position(int column_position) {
        this.column_position = column_position;
    }

    // Initialisation methods


    // Other methods


}
