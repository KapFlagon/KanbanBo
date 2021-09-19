package persistence.tables.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "project")
public class ProjectTable extends AbstractProjectBaseTable {

    // TODO Either use JPA annotations, or use DatabaseFieldConfig for even more decoupling
    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int project_status_id;

    // Constructors
    public ProjectTable() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public int getProject_status_id() {
        return project_status_id;
    }
    public void setProject_status_id(int project_status_id) {
        this.project_status_id = project_status_id;
    }


    // Initialisation methods


    // Other methods


}
