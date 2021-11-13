package persistence.tables.project;

import persistence.tables.TableObject;

import javax.persistence.*;


@Entity(name = "project_status")
public class ProjectStatusTable implements TableObject<Integer> {

    public static final String PRIMARY_KEY = "project_status_id";
    // Variables
    @Id
    private int project_status_id;
    @Column(nullable = false)
    private String status_text_key;

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

    public String getStatus_text_key() {
        return status_text_key;
    }
    public void setStatus_text_key(String status_text_key) {
        this.status_text_key = status_text_key;
    }


    // Initialisation methods


    // Other methods
    @Override
    public Integer getID() {
        return project_status_id;
    }
}
