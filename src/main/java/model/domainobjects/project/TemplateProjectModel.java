package model.domainobjects.project;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "template_project")
public class TemplateProjectModel extends AbstractProjectModel {


    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true)
    private String original_project_title;


    // Constructors


    // Getters and Setters
    public String getOriginal_project_title() {
        return original_project_title;
    }
    public void setOriginal_project_title(String original_project_title) {
        this.original_project_title = original_project_title;
    }


    // Initialisation methods


    // Other methods


}
