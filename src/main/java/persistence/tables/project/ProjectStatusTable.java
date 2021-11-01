package persistence.tables.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import persistence.tables.TableObject;


@DatabaseTable(tableName = "project_status")
public class ProjectStatusTable implements TableObject<Integer> {

    public static final String PRIMARY_KEY = "status_id";
    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int project_status_id;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String project_status_text_key;

    // Constructors
    public ProjectStatusTable() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    // Getters and Setters
    public int getProject_status_id() {
        return project_status_id;
    }
    public void setProject_status_id(int project_status_id) {
        this.project_status_id = project_status_id;
    }

    public String getProject_status_text_key() {
        return project_status_text_key;
    }
    public void setProject_status_text_key(String project_status_text_key) {
        this.project_status_text_key = project_status_text_key;
    }


    // Initialisation methods


    // Other methods
    @Override
    public Integer getID() {
        return project_status_id;
    }
}
