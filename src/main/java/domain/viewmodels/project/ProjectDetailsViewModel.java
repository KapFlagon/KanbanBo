package domain.viewmodels.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import domain.entities.project.ObservableProject;

public class ProjectDetailsViewModel {


    // Variables
    private ObservableProject project;
    private ProjectStatusListViewModel projectStatusListViewModel;



    // Constructors

    public ProjectDetailsViewModel(ObservableProject project, ProjectStatusListViewModel projectStatusListViewModel) {
        this.project = project;
        this.projectStatusListViewModel = projectStatusListViewModel;
        //this.projectStatusListViewModel.setSelectedProjectStatus(project.);
    }


    // Getters and Setters
    public ProjectStatusListViewModel getProjectStatusListViewModel() {
        return projectStatusListViewModel;
    }


    // Initialisation methods


    // Other methods
    public void bindToProjectTitleProperty(StringProperty propertyToBind) {
        propertyToBind.bind(project.projectTitleProperty());
    }

    public void bindToProjectDescriptionProperty(StringProperty propertyToBind) {
        propertyToBind.bind(project.projectDescriptionProperty());
    }

    public void bindToProjectCreationTimestampProperty(StringProperty propertyToBind) {
        propertyToBind.bind(project.creationTimestampProperty());
    }

    public void bindToProjectLastChangedTimestampProperty(StringProperty propertyToBind) {
        propertyToBind.bind(project.lastChangedTimestampProperty());
    }

    public void bindToProjectStatusIdProperty(IntegerProperty propertyToBind) {
        propertyToBind.bind(project.statusIDProperty());
    }

    public void bindToProjectStatusTextProperty(StringProperty propertyToBind) {
        propertyToBind.bind(project.statusTextProperty());
    }

}
