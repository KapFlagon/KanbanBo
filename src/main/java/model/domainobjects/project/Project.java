package model.domainobjects.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "project")
public class Project extends AbstractProject {

    // TODO Either use JPA annotations, or use DatabaseFieldConfig for even more decoupling
    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int project_status;

    // Constructors
    public Project() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public int getProject_status() {
        return project_status;
    }
    public void setProject_status(int project_status) {
        this.project_status = project_status;
    }


    // Initialisation methods


    // Other methods


}
