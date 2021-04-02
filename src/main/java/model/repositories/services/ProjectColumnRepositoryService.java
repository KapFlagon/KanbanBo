package model.repositories.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.activerecords.ProjectActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.project.ActiveProjectModel;
import model.repositories.ActiveColumnListRepository;
import model.repositories.ActiveProjectListRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ProjectColumnRepositoryService {


    // Variables
    private ActiveColumnListRepository activeColumnListRepository;

    // Constructors
    public ProjectColumnRepositoryService(ProjectActiveRecord<ActiveProjectModel> projectActiveRecord) throws IOException, SQLException {
        activeColumnListRepository = new ActiveColumnListRepository(projectActiveRecord);
        activeColumnListRepository.readFromDb();
        //initAllData();
    }


    // Getters and Setters
    public ActiveColumnListRepository getActiveColumnListRepository() {
        return activeColumnListRepository;
    }

    public void setActiveColumnListRepository(ActiveColumnListRepository activeColumnListRepository) {
        this.activeColumnListRepository = activeColumnListRepository;
    }

    public ObservableList<ProjectColumnActiveRecord> getColumnsList() {
        return activeColumnListRepository.getActiveRecordObservableList();
    }


    // Initialisation methods

    // Other methods


}
