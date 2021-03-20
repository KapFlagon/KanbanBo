package view.screens.mainscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview.ProjectsManagerPresenter;
import view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview.ProjectsManagerView;

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
    private TabPane manageSubTabPane;
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
    @FXML
    private TabPane workspaceSubTabPane;

    // Other variables and fields
    private ProjectsManagerView projectsManagerView;
    private ProjectsManagerPresenter projectsManagerPresenter;

    // Constructors


    // Getters and Setters
    //public TabPane getMainScreenTabPane() {
    //    return mainScreenTabPane;
    //}
    //public void setMainScreenTabPane(TabPane mainScreenTabPane) {
    //    this.mainScreenTabPane = mainScreenTabPane;
    //}

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        projectsManagerView = new ProjectsManagerView();
        projectsManagerPresenter = (ProjectsManagerPresenter) projectsManagerView.getPresenter();
        manageProjectsSubTab.setContent(projectsManagerView.getView());

        //ActiveProjectsListView test1 = new ActiveProjectsListView();
        //ActiveProjectsListPresenter test2 = (ActiveProjectsListPresenter) test1.getPresenter();
        //manageTab.setContent(test1.getView());
    }


    // Other methods
}
