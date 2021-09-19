package view.screens.mainscreen.subviews.manage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import domain.activerecords.project.ProjectActiveRecord;
import persistence.repositories.legacy.ActiveProjectListRepository;
import persistence.services.legacy.ProjectRepositoryService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.manage.subviews.projectstable.ProjectsTablePresenter;
import view.screens.mainscreen.subviews.manage.subviews.projectstable.ProjectsTableView;
import view.sharedviewcomponents.DetailsPopupInitialDataMode;
import view.sharedviewcomponents.popups.confirmationdialog.ConfirmationDialogPresenter;
import view.sharedviewcomponents.popups.confirmationdialog.ConfirmationDialogView;
import view.sharedviewcomponents.popups.projectdetails.ProjectDetailsWindowPresenter;
import view.sharedviewcomponents.popups.projectdetails.ProjectDetailsWindowView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagePresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private BorderPane mainContainer;
    @FXML
    private Button newProjectBtn;
    @FXML
    private Button openProjectBtn;
    @FXML
    private Button accessProjectDetailsBtn;
    @FXML
    private Button duplicateProjectBtn;
    @FXML
    private Button deleteProjectBtn;


    // Other variables
    private ProjectsTableView projectsTableView;
    private ProjectsTablePresenter projectsTablePresenter;
    //private ProjectListRepository<ActiveProjectModel> activeProjectsList;
    private ActiveProjectListRepository activeProjectListRepository;
    private ProjectRepositoryService projectRepositoryService;
    private ProjectDetailsWindowView projectDetailsWindowView;
    private ProjectDetailsWindowPresenter projectDetailsWindowPresenter;

    // Constructors


    // Getters and Setters
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
        //activeProjectsList = projectRepositoryService.getActiveProjectListRepository();
        activeProjectListRepository = projectRepositoryService.getActiveProjectListRepository();
        projectsTableView = new ProjectsTableView();
        projectsTablePresenter = (ProjectsTablePresenter) projectsTableView.getPresenter();
        //activeProjectsListPresenter.setActiveProjectList(activeProjectsList.getActiveRecordObservableList());
        projectsTablePresenter.setActiveProjectList(activeProjectListRepository.getActiveRecordObservableList());
        //activeProjectsTab.setContent(activeProjectsListView.getView());
        mainContainer.setCenter(projectsTableView.getView());
    }

    private void initProjectDetailsUI() {
        projectDetailsWindowView = new ProjectDetailsWindowView();
        projectDetailsWindowPresenter = (ProjectDetailsWindowPresenter) projectDetailsWindowView.getPresenter();
    }


    // UI Events
    public void createNewProject() {
        System.out.println("Creating a new project");
        initProjectDetailsUI();
        projectDetailsWindowPresenter.setInitialDataMode(DetailsPopupInitialDataMode.CREATE);
        showProjectDetailsWindow();
    }

    public void openSelectedProject() {
        /*
        AvailableTabs selectedParentTab = determineSelectedTab();
        switch (selectedParentTab) {
            case ACTIVE:
                System.out.println("Active project parent tab selected");
                System.out.println("selected item: \n" + activeProjectsListPresenter.getSelectedRow().toString() + "\n" + activeProjectsListPresenter.getSelectedRow().getProjectUUID().toString() + "\t" + activeProjectsListPresenter.getSelectedRow().getProjectTitle());
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
         */
        System.out.println("Trying to open project in workspace");
        ProjectActiveRecord activeRecord = projectsTablePresenter.getSelectedRow();
        if (activeRecord != null) {
            System.out.println("Project selected is not null");
            // TODO check if the project is already open in the tab list.
            if (projectRepositoryService.getOpenedActiveProjects().size() == 0) {
                System.out.println("No projects opened yet in workspace");
                projectRepositoryService.getOpenedActiveProjects().add(activeRecord);
            } else {
                for (ProjectActiveRecord innerActiveRecord: projectRepositoryService.getOpenedActiveProjects()) {
                    if (activeRecord.getProjectUUID() != innerActiveRecord.getProjectUUID()) {
                        projectRepositoryService.getOpenedActiveProjects().add(activeRecord);
                        System.out.println("Project opened");
                    } else {
                        // TODO Logging here
                        System.out.println("Project already open");
                    }
                }
            }

        }
    }

    public void accessProjectDetails() {
        System.out.println("Trying to open project in manager for editing.");
        ProjectActiveRecord activeRecord = projectsTablePresenter.getSelectedRow();
        if (activeRecord != null) {
            System.out.println("Project selected is not null");
            initProjectDetailsUI();
            projectDetailsWindowPresenter.setProjectActiveRecord(activeRecord);
            projectDetailsWindowPresenter.setInitialDataMode(DetailsPopupInitialDataMode.DISPLAY);
            showProjectDetailsWindow();
        } else {
            System.out.println("selected project was found to be null");
        }
    }



    public void duplicateSelectedProject() {
        // TODO Add option to deep duplicate a project, including all of its contents, but with new UUID values.
    }

    public void deleteSelectedProject() {
        System.out.println("Deleting a project");
        ProjectActiveRecord activeRecord = projectsTablePresenter.getSelectedRow();
        if (activeRecord != null) {
            System.out.println("Project selected is not null");
            ConfirmationDialogView confirmationDialogView = new ConfirmationDialogView();
            ConfirmationDialogPresenter confirmationDialogPresenter = (ConfirmationDialogPresenter) confirmationDialogView.getPresenter();
            confirmationDialogPresenter.setPromptText("Are you sure that you want to delete the selected project?");
            confirmationDialogPresenter.setConfirmAction(actionEvent ->  {
                try {
                    activeRecord.deleteActiveRowInDb();
                    activeProjectListRepository.getActiveRecordObservableList().remove(activeRecord);
                } catch (SQLException throwables) {
                    System.out.println("Could not delete project entry");
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StageUtils.closeSubStage();
            });
            confirmationDialogPresenter.setCancelAction(actionEvent ->  {
                StageUtils.closeSubStage();
            });
            StageUtils.createChildStage("Confirm deletion", confirmationDialogView.getView(), confirmationDialogPresenter.getDisplayDimensions());
            StageUtils.showAndWaitOnSubStage();
        } else {
            System.out.println("selected project was found to be null");
        }
    }

    private void showProjectDetailsWindow() {
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
        StageUtils.createChildStage("Enter Project Details", projectDetailsWindowView.getView(), projectDetailsWindowPresenter.getDisplayDimensions());
        StageUtils.showAndWaitOnSubStage();
        //ActiveProjectModel tempActiveProjectModel = projectDetailsWindowPresenter.getSelectedActiveProjectModel();
        ProjectActiveRecord tempProjectActiveRecord = projectDetailsWindowPresenter.getProjectActiveRecord();
        /*
        if (tempActiveProjectModel != null) {
            activeProjectListRepository.addItem(tempActiveProjectModel);
        }
        */
        if(tempProjectActiveRecord != null) {
            boolean toBeCreated = true;
            for (ProjectActiveRecord par : activeProjectListRepository.getActiveRecordObservableList()) {
                if (par.getProjectUUID().equals(tempProjectActiveRecord.getProjectUUID())) {
                    toBeCreated = false;
                }
            }
            if (toBeCreated) {
                activeProjectListRepository.getActiveRecordObservableList().add(tempProjectActiveRecord);
            }
        }
        StageUtils.closeSubStage();
    }


}
