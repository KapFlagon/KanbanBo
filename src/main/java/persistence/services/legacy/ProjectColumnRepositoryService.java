package persistence.services.legacy;

import javafx.collections.ObservableList;
import domain.activerecords.project.ProjectActiveRecord;
import domain.activerecords.ProjectColumnActiveRecord;
import persistence.repositories.legacy.ActiveColumnListRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ProjectColumnRepositoryService {


    // Variables
    private ActiveColumnListRepository activeColumnListRepository;

    // Constructors
    public ProjectColumnRepositoryService(ProjectActiveRecord projectActiveRecord) throws IOException, SQLException {
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
