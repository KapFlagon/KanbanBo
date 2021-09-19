package view.screens.mainscreen;

import javafx.collections.ObservableList;

public class MainScreenViewModel {


    // Variables
    private ObservableList projectsInManagementTable;
    private ObservableList projectsOpenInWorkspace;


    // Constructors


    // Getters and Setters
    public ObservableList getProjectsInManagementTable() {
        return projectsInManagementTable;
    }
    public void setProjectsInManagementTable(ObservableList projectsInManagementTable) {
        this.projectsInManagementTable = projectsInManagementTable;
    }

    public ObservableList getProjectsOpenInWorkspace() {
        return projectsOpenInWorkspace;
    }
    public void setProjectsOpenInWorkspace(ObservableList projectsOpenInWorkspace) {
        this.projectsOpenInWorkspace = projectsOpenInWorkspace;
    }


    // Initialisation methods


    // Other methods


}
