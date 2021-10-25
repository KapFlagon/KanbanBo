package persistence.tables.column;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import persistence.tables.TableObject;

import java.util.UUID;

@DatabaseTable(tableName = "abstract_column")
public abstract class AbstractColumnBaseTable implements TableObject<UUID> {

    // Constant for query building using database fields
    public static final String FOREIGN_KEY_NAME = "parent_project_uuid";

    // Variables
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID column_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String column_title;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String creation_timestamp;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String last_changed_timestamp;

    // Constructors
    public AbstractColumnBaseTable() {
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

    public String getCreation_timestamp() {
        return creation_timestamp;
    }
    public void setCreation_timestamp(String creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public String getLast_changed_timestamp() {
        return last_changed_timestamp;
    }
    public void setLast_changed_timestamp(String last_changed_timestamp) {
        this.last_changed_timestamp = last_changed_timestamp;
    }

    // Establishing contract for extending classes so that they have some sort of access for a parent UUID and position.
    // This will allow for behaviour differences in each extending class.
    public abstract UUID getParent_project_uuid();
    public abstract void setParent_project_uuid(UUID parent_project_uuid);


    // Initialisation methods


    // Other methods
    public UUID getID() {
        return getColumn_uuid();
    }

}
