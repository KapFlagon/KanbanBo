package persistence.tables.project;

import persistence.tables.column.ColumnTable;
import persistence.tables.resourceitems.ResourceItemTable;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "project")
public class ProjectTable extends AbstractProjectBaseTable {

    // TODO Either use JPA annotations, or use DatabaseFieldConfig for even more decoupling
    // Variables
    @OneToOne
    @JoinColumn(name = ProjectStatusTable.PRIMARY_KEY)
    private ProjectStatusTable projectStatus;
    @Column(nullable = false)
    private String due_on_date;
    @OneToMany
    @JoinColumn(name = "resource_items")
    private Collection<ResourceItemTable> projectResources;
    @OneToMany
    @JoinColumn(name = ColumnTable.FOREIGN_KEY_NAME)
    private Collection<ColumnTable> columns;

    // Constructors
    public ProjectTable() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public ProjectStatusTable getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatusTable projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getDue_on_date() {
        return due_on_date;
    }

    public void setDue_on_date(String due_on_date) {
        this.due_on_date = due_on_date;
    }

    public Collection<ResourceItemTable> getProjectResources() {
        return projectResources;
    }

    public void setProjectResources(Collection<ResourceItemTable> projectResources) {
        this.projectResources = projectResources;
    }

    public Collection<ColumnTable> getColumns() {
        return columns;
    }

    public void setColumns(Collection<ColumnTable> columns) {
        this.columns = columns;
    }


    // Initialisation methods


    // Other methods


}
