package model.domainobjects.column;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "project_column")
public class ActiveProjectColumnModel extends AbstractProjectColumnModel{


    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int column_position;

    // Constructors
    public ActiveProjectColumnModel() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public int getColumn_position() {
        return column_position;
    }
    public void setColumn_position(int column_position) {
        this.column_position = column_position;
    }

    // Initialisation methods


    // Other methods


}
