package view.screens.mainscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import view.screens.mainscreen.subviews.manage.projectsmanagerview.ProjectsManagerPresenter;
import view.screens.mainscreen.subviews.manage.projectsmanagerview.ProjectsManagerView;
import view.screens.mainscreen.subviews.manage.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListPresenter;
import view.screens.mainscreen.subviews.manage.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenPresenter implements Initializable {

    // JavaFx injected fields
    @FXML
    private TabPane mainScreenTabPane;
    // TODO Add toolbar for expected functions, like "home" (Projects manager tab).
    @FXML
    private Tab manageTab;
    @FXML
    private Tab dashboardTab;
    @FXML
    private Tab workspaceTab;

    // Other variables and fields
    private ProjectsManagerView projectsManagerView;
    private ProjectsManagerPresenter projectsManagerPresenter;

    // Constructors


    // Getters and Setters
    public TabPane getMainScreenTabPane() {
        return mainScreenTabPane;
    }
    public void setMainScreenTabPane(TabPane mainScreenTabPane) {
        this.mainScreenTabPane = mainScreenTabPane;
    }


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectsManagerView = new ProjectsManagerView();
        projectsManagerPresenter = (ProjectsManagerPresenter) projectsManagerView.getPresenter();
        manageTab.setContent(projectsManagerView.getView());

        //ActiveProjectsListView test1 = new ActiveProjectsListView();
        //ActiveProjectsListPresenter test2 = (ActiveProjectsListPresenter) test1.getPresenter();
        //manageTab.setContent(test1.getView());
    }


    // Other methods
}
