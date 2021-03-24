package view.screens.mainscreen.subviews.manage.subviews.projectsmanagerview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.domainobjects.project.ActiveProjectModel;
import model.repository.ProjectListRepository;
import model.repository.ProjectRepositoryService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.manage.subviews.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListPresenter;
import view.screens.mainscreen.subviews.manage.subviews.projectsmanagerview.subviews.activeprojectstab.ActiveProjectsListView;
import view.sharedcomponents.inputwindows.projectdetails.ProjectDetailsWindowPresenter;
import view.sharedcomponents.inputwindows.projectdetails.ProjectDetailsWindowView;

import javax.inject.Inject;
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
    private Button unarchiveProjectBtn;
    @FXML
    private Button completeProjectBtn;
    @FXML
    private Button duplicateProjectBtn;
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
    private ProjectRepositoryService projectRepositoryService;

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

    public ProjectRepositoryService getProjectRepositoryService() {
        return projectRepositoryService;
    }
    public void setProjectRepositoryService(ProjectRepositoryService projectRepositoryService) {
        this.projectRepositoryService = projectRepositoryService;
        customInit();
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void customInit() {
        activeProjectsList = projectRepositoryService.getActiveProjectsRepository();
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
        AvailableTabs selectedParentTab = determineSelectedTab();
        switch (selectedParentTab) {
            case ACTIVE:
                System.out.println("Active project parent tab selected");
                System.out.println("selected item: \n" + activeProjectsListPresenter.getSelectedRow().toString() + "\n" + activeProjectsListPresenter.getSelectedRow().getProject_uuid().toString() + "\t" + activeProjectsListPresenter.getSelectedRow().getProject_title());
                break;
            case ARCHIVED:
                System.out.println("Archived project parent tab selected");
                break;
            case COMPLETED:
                System.out.println("Completed project parent tab selected");
                break;
            case NONE:
                System.out.println("No parent tab selected");
                break;
            default:
                System.out.println("default");
                break;
        }
    }

    public void editProjectDetails() {

    }

    public void archiveSelectedProject() {

    }

    public void unarchiveSelectedProject() {

    }

    public void completeSelectedProject() {

    }

    public void duplicateSelectedProject() {

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
                openProjectBtn.setDisable(false);
                editProjectBtn.setDisable(false);
                archiveProjectBtn.setDisable(false);
                unarchiveProjectBtn.setDisable(true);
                completeProjectBtn.setDisable(false);
                duplicateProjectBtn.setDisable(false);
                convertToTemplateBtn.setDisable(false);
                deleteProjectBtn.setDisable(false);
                break;
            case ARCHIVED:
                newProjectBtn.setDisable(false);
                openProjectBtn.setDisable(false);
                editProjectBtn.setDisable(true);
                archiveProjectBtn.setDisable(true);
                unarchiveProjectBtn.setDisable(false);
                completeProjectBtn.setDisable(false);
                duplicateProjectBtn.setDisable(false);
                convertToTemplateBtn.setDisable(false);
                deleteProjectBtn.setDisable(false);
                break;
            case COMPLETED:
                newProjectBtn.setDisable(false);
                openProjectBtn.setDisable(false);
                editProjectBtn.setDisable(true);
                archiveProjectBtn.setDisable(true);
                unarchiveProjectBtn.setDisable(true);
                completeProjectBtn.setDisable(true);
                duplicateProjectBtn.setDisable(false);
                convertToTemplateBtn.setDisable(false);
                deleteProjectBtn.setDisable(false);
                break;
            default:
                break;
        }
    }

    private void showCreateProjectWindow() {
        // TODO 24.03.2021 start here to redesign how data is moved to the project repository service
        /*
        User clicks button to create/edit.
        If create:
            New project data gathered.
            New project data pushed to Db.
            New project object returned to the active project repository
                Last two steps might be together, based on the project repository coding.
            Tables are updated based on observable list

        If edit:
            Old project data is saved in a temporary object
            New project data is saved in a temporary object.
            Both objects sent to the repository
            Old object found, updates are made to DB and to object in the observable list
            Changes are pushed outward from that observable list update.
         */
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
