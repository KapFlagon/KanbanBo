package model.domainobjects.column;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "abstract_project_column")
public abstract class AbstractProjectColumnModel extends AbstractColumnModel{

    // Constant for query building using database fields
    public static final String FOREIGN_KEY_NAME = "parent_project_uuid";

    // Variables
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true, columnName = FOREIGN_KEY_NAME)
    private UUID parent_project_uuid;

    // Constructors


    // Getters and Setters
    public UUID getParent_project_uuid() {
        return parent_project_uuid;
    }
    public void setParent_project_uuid(UUID parent_project_uuid) {
        this.parent_project_uuid = parent_project_uuid;
    }

    // Initialisation methods


    // Other methods


}
