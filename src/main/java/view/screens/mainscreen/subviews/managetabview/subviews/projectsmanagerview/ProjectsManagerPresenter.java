package view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListPresenter;
import view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectsManagerPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private Button newProjectBtn;
    @FXML
    private Button openProjectBtn;
    @FXML
    private Button editProjectBtn;
    @FXML
    private Button archiveProjectBtn;
    @FXML
    private Button completeProjectBtn;
    @FXML
    private Button convertToTemplateBtn;
    @FXML
    private Button deleteProjectBtn;
    @FXML
    private TabPane projectManagementTabPane;
    @FXML
    private Tab activeProjectsTab;
    @FXML
    private Tab archivedProjectsTab;
    @FXML
    private Tab completeProjectsTab;

    // Other variables
    private enum AvailableTabs {NONE, ACTIVE, ARCHIVED, COMPLETED}
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


    // Other methods
    public void createNewProject() {

    }

    public void openSelectedProject() {

    }

    public void editProjectDetails() {

    }

    public void archiveSelectedProject() {

    }

    public void completeSelectedProject() {

    }

    public void convertSelectedProjectToTemplate() {

    }

    public void deleteSelectedProject() {

    }

    public void tabSelectionChanged() {
        AvailableTabs selectedTab = determineSelectedTab();
        updateButtonDisplay(selectedTab);
    }

    private AvailableTabs determineSelectedTab() {
        Tab selectedTab = projectManagementTabPane.getSelectionModel().getSelectedItem();
        String tabTitle = selectedTab.getText();
        switch (tabTitle) {
            case "Active Projects":
                return AvailableTabs.ACTIVE;
            case "Archived Projects":
                return AvailableTabs.ARCHIVED;
            case "Completed Projects":
                return AvailableTabs.COMPLETED;
            default:
                return AvailableTabs.NONE;
        }
    }

    private void updateButtonDisplay(AvailableTabs selectedTab) {
        switch (selectedTab) {
            case ACTIVE:
                newProjectBtn.setDisable(false);
                editProjectBtn.setDisable(false);
                deleteProjectBtn.setDisable(false);
                break;
            case ARCHIVED:
                newProjectBtn.setDisable(true);
                editProjectBtn.setDisable(true);
                deleteProjectBtn.setDisable(false);
                break;
            case COMPLETED:
                newProjectBtn.setDisable(true);
                editProjectBtn.setDisable(true);
                deleteProjectBtn.setDisable(true);
                break;
            default:
                break;
        }
    }


}
