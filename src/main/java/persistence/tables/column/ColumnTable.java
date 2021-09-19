package persistence.tables.column;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;


@DatabaseTable(tableName = "column")
public class ColumnTable extends AbstractColumnBaseTable {

    public static final String FINAL_FLAG_NAME = "final_column";
    public static final String POSITION_KEY_NAME = "column_position";


    // Variables
    // Same fields as Column, but cannot be null in database.
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true, columnName = FOREIGN_KEY_NAME)
    private UUID parent_project_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int column_position;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.BOOLEAN)
    private boolean final_column;

    // Constructors
    public ColumnTable() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public UUID getParent_project_uuid() {
        return parent_project_uuid;
    }
    public void setParent_project_uuid(UUID parent_project_uuid) {
        this.parent_project_uuid = parent_project_uuid;
    }

    public int getColumn_position() {
        return column_position;
    }
    public void setColumn_position(int column_position) {
        this.column_position = column_position;
    }

    public boolean isFinal_column() {
        return final_column;
    }
    public void setFinal_column(boolean final_column) {
        this.final_column = final_column;
    }

    // Initialisation methods


    // Other methods


}
