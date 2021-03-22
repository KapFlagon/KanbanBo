package view.screens.mainscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview.ProjectsManagerPresenter;
import view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview.ProjectsManagerView;
import view.screens.mainscreen.subviews.workspace.ProjectWorkspacePresenter;
import view.screens.mainscreen.subviews.workspace.ProjectWorkspaceView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenPresenter implements Initializable {

    // JavaFx injected fields
    @FXML
    private Tab manageProjectsSubTab;
    @FXML
    private Tab dashboardTab;
    @FXML
    private TabPane dashboardSubTabPane;
    @FXML
    private Tab workspaceTab;

    // Other variables and fields
    private ProjectsManagerView projectsManagerView;
    private ProjectsManagerPresenter projectsManagerPresenter;
    private ProjectWorkspaceView projectWorkspaceView;
    private ProjectWorkspacePresenter projectWorkspacePresenter;

    // Constructors


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        projectsManagerView = new ProjectsManagerView();
        projectsManagerPresenter = (ProjectsManagerPresenter) projectsManagerView.getPresenter();
        manageProjectsSubTab.setContent(projectsManagerView.getView());

        projectWorkspaceView = new ProjectWorkspaceView();
        projectWorkspacePresenter = (ProjectWorkspacePresenter) projectWorkspaceView.getPresenter();
        workspaceTab.setContent(projectWorkspaceView.getView());
    }


    // Other methods
}
