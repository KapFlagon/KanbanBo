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
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String due_on_date;

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

    public String getDue_on_date() {
        return due_on_date;
    }
    public void setDue_on_date(String due_on_date) {
        this.due_on_date = due_on_date;
    }

    // Initialisation methods


    // Other methods


}