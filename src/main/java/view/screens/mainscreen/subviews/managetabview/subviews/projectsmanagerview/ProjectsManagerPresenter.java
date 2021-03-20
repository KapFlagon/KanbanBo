package view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.domainobjects.project.ActiveProjectModel;
import model.repository.ProjectListRepository;
import utils.StageUtils;
import view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListPresenter;
import view.screens.mainscreen.subviews.managetabview.subviews.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListView;
import view.sharedcomponents.inputwindows.projectdetails.ProjectDetailsWindowPresenter;
import view.sharedcomponents.inputwindows.projectdetails.ProjectDetailsWindowView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private ProjectListRepository<ActiveProjectModel> activeProjectsList;

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
        activeProjectsList = new ProjectListRepository<ActiveProjectModel>(ActiveProjectModel.class);
        try {
            activeProjectsList.getAllItemsAsList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        activeProjectsListView = new ActiveProjectsListView();
        activeProjectsListPresenter = (ActiveProjectsListPresenter) activeProjectsListView.getPresenter();
        activeProjectsListPresenter.setActiveProjectList(activeProjectsList.getModelList());
        activeProjectsTab.setContent(activeProjectsListView.getView());
    }


    // UI Events
    public void createNewProject() {
        System.out.println("Creating a new project");
        showCreateProjectWindow();
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
        System.out.println("Deleting a project");
        PopupControl popupControl = new PopupControl();
        // TODO verify selection and deletion in a pop-up.
    }

    public void tabSelectionChanged() {
        AvailableTabs selectedTab = determineSelectedTab();
        updateButtonDisplay(selectedTab);
    }

    // Other methods
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

    private void showCreateProjectWindow() {
        ProjectDetailsWindowView projectDetailsWindowView = new ProjectDetailsWindowView();
        ProjectDetailsWindowPresenter projectDetailsWindowPresenter = (ProjectDetailsWindowPresenter) projectDetailsWindowView.getPresenter();
        StageUtils.createChildStage("Enter Project Details", projectDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ActiveProjectModel tempActiveProjectModel = projectDetailsWindowPresenter.getSelectedActiveProjectModel();
        if (tempActiveProjectModel != null) {
            activeProjectsList.addItem(tempActiveProjectModel);
        }
        StageUtils.closeSubStage();
    }


}
