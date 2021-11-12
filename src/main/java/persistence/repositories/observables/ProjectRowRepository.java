package persistence.repositories.observables;

import domain.entities.project.ObservableProjectRow;
import javafx.collections.ObservableList;

import java.util.UUID;

public class ProjectRowRepository {


    // Variables
    private ObservableList<ObservableProjectRow> projectRowsList;

    // Constructors
    public ProjectRowRepository(ObservableList<ObservableProjectRow> observableProjectRows) {
        this.projectRowsList = observableProjectRows;
    }


    // Getters and Setters
    public ObservableList<ObservableProjectRow> getObservableProjectRows() {
        return projectRowsList;
    }

    public void setObservableProjectRows(ObservableList<ObservableProjectRow> observableProjectRows) {
        this.projectRowsList = observableProjectRows;
    }


    // Initialisation methods


    // Other methods
    public void addProjectRow(ObservableProjectRow newObservableProjectRow) {
        projectRowsList.add(newObservableProjectRow);
    }

    public void removeProjectRow(ObservableProjectRow observableProjectRow) {
        projectRowsList.remove(observableProjectRow);
    }

    public void removeProjectRow(UUID uuid) {
        boolean removalPending = true;
        int iterator = 0;
        while(removalPending && iterator < projectRowsList.size()) {
            if(projectRowsList.get(iterator).getProjectUUID().equals(uuid)) {
                projectRowsList.remove(projectRowsList.get(iterator));
                removalPending = false;
            }
            iterator += 1;
        }
    }

}
