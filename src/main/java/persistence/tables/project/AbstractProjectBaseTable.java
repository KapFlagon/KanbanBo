package persistence.tables.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import persistence.tables.TableObject;

import java.util.Date;
import java.util.UUID;

@DatabaseTable(tableName = "abstract_project")
public abstract class AbstractProjectBaseTable implements TableObject<UUID> {


    // Variables
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID project_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String project_title;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String project_description;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.DATE_STRING)
    private Date creation_timestamp;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.DATE_STRING)
    private Date last_changed_timestamp;


    // Constructors
    public AbstractProjectBaseTable() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    // Getters and Setters
    public UUID getProject_uuid() {
        return project_uuid;
    }
    public void setProject_uuid(UUID project_uuid) {
        this.project_uuid = project_uuid;
    }

    public String getProject_title() {
        return project_title;
    }
    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getProject_description() {
        return project_description;
    }
    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public Date getCreation_timestamp() {
        return creation_timestamp;
    }
    public void setCreation_timestamp(Date creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public Date getLast_changed_timestamp() {
        return last_changed_timestamp;
    }
    public void setLast_changed_timestamp(Date last_changed_timestamp) {
        this.last_changed_timestamp = last_changed_timestamp;
    }

    // Initialisation methods


    // Other methods
    public UUID getID() {
        return getProject_uuid();
    }
}
