package model.domainobjects.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "project_status")
public class ProjectStatus {

    public static final String PRIMARY_KEY = "status_id";
    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int project_status_id;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String project_status_text;

    // Constructors
    public ProjectStatus() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    // Getters and Setters
    public int getProject_status_id() {
        return project_status_id;
    }
    public void setProject_status_id(int project_status_id) {
        this.project_status_id = project_status_id;
    }

    public String getProject_status_text() {
        return project_status_text;
    }
    public void setProject_status_text(String project_status_text) {
        this.project_status_text = project_status_text;
    }


    // Initialisation methods


    // Other methods


}
