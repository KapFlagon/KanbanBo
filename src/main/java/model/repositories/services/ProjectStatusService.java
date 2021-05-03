package model.repositories.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.collections.ObservableList;
import model.activerecords.project.ProjectActiveRecord;
import model.activerecords.project.ProjectStatusActiveRecord;
import model.domainobjects.ProjectStatusModel;
import model.domainobjects.project.ProjectModel;

import java.util.UUID;

public class ProjectStatusService {


    // Variables
    protected JdbcConnectionSource connectionSource;
    protected Dao<ProjectStatusModel, UUID> projectStatusModelDao;

    // Constructors


    // Getters and Setters
    public ProjectStatusActiveRecord test() {
        return null;
    }


    // Initialisation methods


    // Other methods


}
