package view.screens.mainscreen.subviews.manage.projectsmanagerview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import view.screens.mainscreen.subviews.manage.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListPresenter;
import view.screens.mainscreen.subviews.manage.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectsManagerPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private TabPane projectManagementTabPane;
    @FXML
    private Tab activeProjectsTab;
    @FXML
    private Tab archivedProjectsTab;
    @FXML
    private Tab completeProjectsTab;

    // Other variables
    private ActiveProjectsListView activeProjectsListView;
    private ActiveProjectsListPresenter activeProjectsListPresenter;

    // Constructors


    // Getters and Setters
    public TabPane getProjectManagementTabPane() {
        return projectManagementTabPane;
    }
    public void setProjectManagementTabPane(TabPane projectManagementTabPane) {
        this.projectManagementTabPane = projectManagementTabPane;
    }

    public Tab getActiveProjectsTab() {
        return activeProjectsTab;
    }
    public void setActiveProjectsTab(Tab activeProjectsTab) {
        this.activeProjectsTab = activeProjectsTab;
    }

    public Tab getArchivedProjectsTab() {
        return archivedProjectsTab;
    }
    public void setArchivedProjectsTab(Tab archivedProjectsTab) {
        this.archivedProjectsTab = archivedProjectsTab;
    }

    public Tab getCompleteProjectsTab() {
        return completeProjectsTab;
    }
    public void setCompleteProjectsTab(Tab completeProjectsTab) {
        this.completeProjectsTab = completeProjectsTab;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeProjectsListView = new ActiveProjectsListView();
        activeProjectsListPresenter = (ActiveProjectsListPresenter) activeProjectsListView.getPresenter();
        activeProjectsTab.setContent(activeProjectsListView.getView());
    }



}
