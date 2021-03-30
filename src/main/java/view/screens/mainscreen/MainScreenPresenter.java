package view.screens.mainscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.repositories.ProjectRepositoryService;
import view.screens.mainscreen.subviews.manage.subviews.projectsmanagerview.ProjectsManagerPresenter;
import view.screens.mainscreen.subviews.manage.subviews.projectsmanagerview.ProjectsManagerView;
import view.screens.mainscreen.subviews.workspace.WorkspacePresenter;
import view.screens.mainscreen.subviews.workspace.WorkspaceView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainScreenPresenter implements Initializable {

    // JavaFx injected fields
    @FXML
    private TabPane mainScreenTabPane;
    @FXML
    private Tab manageProjectsSubTab;
    @FXML
    private Tab manageTemplatesSubTab;
    @FXML
    private Tab dashboardTab;
    @FXML
    private TabPane dashboardSubTabPane;
    @FXML
    private Tab workspaceTab;

    // Other variables and fields
    private ProjectRepositoryService projectRepositoryService;
    private ProjectsManagerView projectsManagerView;
    private ProjectsManagerPresenter projectsManagerPresenter;
    private WorkspaceView workspaceView;
    private WorkspacePresenter workspacePresenter;

    // Constructors


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            projectRepositoryService = new ProjectRepositoryService();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        projectsManagerView = new ProjectsManagerView();
        projectsManagerPresenter = (ProjectsManagerPresenter) projectsManagerView.getPresenter();
        projectsManagerPresenter.setProjectRepositoryService(projectRepositoryService);
        manageProjectsSubTab.setContent(projectsManagerView.getView());

        workspaceView = new WorkspaceView();
        workspacePresenter = (WorkspacePresenter) workspaceView.getPresenter();
        workspacePresenter.setProjectRepositoryService(projectRepositoryService);
        workspaceTab.setContent(workspaceView.getView());
    }


    // Other methods
}
