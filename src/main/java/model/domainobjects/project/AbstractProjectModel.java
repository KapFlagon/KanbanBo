package model.domainobjects.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import model.domainobjects.BasicUniqueObjectModel;

import java.util.Date;
import java.util.UUID;

@DatabaseTable(tableName = "project")
public abstract class AbstractProjectModel {


    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID project_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String project_title;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.DATE_STRING)
    private Date creation_timestamp;


    // Constructors
    public AbstractProjectModel() {
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

    public Date getCreation_timestamp() {
        return creation_timestamp;
    }
    public void setCreation_timestamp(Date creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }


    // Initialisation methods


    // Other methods

}
