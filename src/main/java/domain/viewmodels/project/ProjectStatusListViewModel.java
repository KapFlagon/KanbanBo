package domain.viewmodels.project;

import javafx.collections.ObservableList;

public class ProjectStatusListViewModel {


    // Variables
    private ObservableList<ProjectStatusViewModel> projectStatusViewModels;
    private ProjectStatusViewModel selectedProjectStatus;


    // Constructors
    public ProjectStatusListViewModel(ObservableList<ProjectStatusViewModel> projectStatusViewModels) {
        this.projectStatusViewModels = projectStatusViewModels;
        selectedProjectStatus = projectStatusViewModels.get(0);
    }

    public ProjectStatusListViewModel(ObservableList<ProjectStatusViewModel> projectStatusViewModels, ProjectStatusViewModel selectedProjectStatus) {
        this.projectStatusViewModels = projectStatusViewModels;
        this.selectedProjectStatus = selectedProjectStatus;
    }


    // Getters and Setters
    public ObservableList<ProjectStatusViewModel> getProjectStatusViewModels() {
        return projectStatusViewModels;
    }

    public ProjectStatusViewModel getSelectedProjectStatus() {
        return selectedProjectStatus;
    }

    public void setSelectedProjectStatus(ProjectStatusViewModel selectedProjectStatus) {
        this.selectedProjectStatus = selectedProjectStatus;
    }


    // Initialisation methods


    // Other methods


}
