package view.components.project.editor;

import domain.entities.project.ObservableProjectRow;
import domain.entities.project.ObservableWorkspaceProject;
import domain.viewmodels.project.ProjectStatusListViewModel;
import javafx.fxml.Initializable;
import view.components.project.editor.datapanes.ProjectBasicInfoPresenter;
import view.components.project.editor.datapanes.ProjectBasicInfoView;
import view.sharedviewcomponents.editor.EditorPresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectEditorPresenter extends EditorPresenter {

    // JavaFX injected node variables

    // Other variables
    private ObservableWorkspaceProject projectViewModel;
    private ProjectStatusListViewModel projectStatusListViewModel;

    private ProjectBasicInfoView projectBasicInfoView;
    private ProjectBasicInfoPresenter projectBasicInfoPresenter;

    // Constructors

    // Getters & Setters
    public void setProjectViewModel(ObservableWorkspaceProject observableWorkspaceProject) {
        this.projectViewModel = observableWorkspaceProject;
        projectBasicInfoPresenter.setProjectViewModel(projectViewModel);
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        projectBasicInfoView = new ProjectBasicInfoView();
        projectBasicInfoPresenter = (ProjectBasicInfoPresenter) projectBasicInfoView.getPresenter();
        addDataPane(projectBasicInfoView);
    }

    // UI event methods

    // Other methods


}