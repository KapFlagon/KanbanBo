package model.domainobjects.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "project_statuses")
public class ProjectStatuses {

    // TODO need to push data to DB during file creation, as well as create the table

    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int status_id;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String status_text;

    // Constructors
    public ProjectStatuses() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    // Getters and Setters


    // Initialisation methods


    // Other methods


}
