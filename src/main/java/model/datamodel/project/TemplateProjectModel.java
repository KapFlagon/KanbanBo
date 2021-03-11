package model.datamodel.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class TemplateProjectModel extends ProjectModel{


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
