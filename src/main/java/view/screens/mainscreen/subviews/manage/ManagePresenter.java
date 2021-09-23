package view.screens.mainscreen.subviews.manage;

import domain.entities.project.ObservableProject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.manage.subviews.projectstable.ProjectsTablePresenter;
import view.screens.mainscreen.subviews.manage.subviews.projectstable.ProjectsTableView;
import view.sharedviewcomponents.DetailsPopupInitialDataMode;
import view.sharedviewcomponents.popups.confirmationdialog.ConfirmationDialogPresenter;
import view.sharedviewcomponents.popups.confirmationdialog.ConfirmationDialogView;
import view.sharedviewcomponents.popups.projectdetails.ProjectDetailsWindowPresenter;
import view.sharedviewcomponents.popups.projectdetails.ProjectDetailsWindowView;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
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
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ProjectsTableView projectsTableView;
    private ProjectsTablePresenter projectsTablePresenter;
    private ProjectDetailsWindowView projectDetailsWindowView;
    private ProjectDetailsWindowPresenter projectDetailsWindowPresenter;

    // Constructors


    // Getters and Setters


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectsTableView = new ProjectsTableView();
        projectsTablePresenter = (ProjectsTablePresenter) projectsTableView.getPresenter();
        mainContainer.setCenter(projectsTableView.getView());
    }

    private void initProjectDetailsUI() {
        projectDetailsWindowView = new ProjectDetailsWindowView();
        projectDetailsWindowPresenter = (ProjectDetailsWindowPresenter) projectDetailsWindowView.getPresenter();
    }


    // UI Events
    @FXML private void createNewProject() throws SQLException, IOException {
        System.out.println("Creating a new project");
        initProjectDetailsUI();
        projectDetailsWindowPresenter.setInitialDataMode(DetailsPopupInitialDataMode.CREATE);
        showProjectDetailsWindow();
        // TODO maybe insert error handling here to show error to user.
    }

    @FXML private void openSelectedProject() {
        System.out.println("Trying to open project in workspace");
        ObservableProject selectedProjectRow = projectsTablePresenter.getSelectedRow();

        if (selectedProjectRow != null) {
            System.out.println("Project selected is not null");
            // TODO check if the project is already open in the tab list.
            if (kanbanBoDataService.getWorkspaceProjectsList().size() == 0) {
                System.out.println("No projects opened yet in workspace");
                kanbanBoDataService.getWorkspaceProjectsList().add(selectedProjectRow);
            } else {
                boolean projectCanBeFreshlyOpened = true;
                for (ObservableProject observableProject: kanbanBoDataService.getWorkspaceProjectsList()) {
                    if (selectedProjectRow.getProjectUUID().equals(observableProject.getProjectUUID())) {
                        projectCanBeFreshlyOpened = false;
                    }
                }
                if(projectCanBeFreshlyOpened) {
                    kanbanBoDataService.getWorkspaceProjectsList().add(selectedProjectRow);
                    System.out.println("Project opened");
                } else {
                    // TODO Need some mechanism here to open the project...
                    System.out.println("Project already open");

                }
            }
        }
    }


    @FXML private void accessProjectDetails() throws SQLException, IOException {

        System.out.println("Trying to open project in manager for editing.");
        ObservableProject observableProject = projectsTablePresenter.getSelectedRow();
        if (observableProject != null) {
            System.out.println("Project selected is not null");
            initProjectDetailsUI();
            projectDetailsWindowPresenter.setProjectViewModel(observableProject);
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
        ObservableProject selectedProjectRow = projectsTablePresenter.getSelectedRow();
        if (selectedProjectRow != null) {
            System.out.println("Project selected is not null");
            ConfirmationDialogView confirmationDialogView = new ConfirmationDialogView();
            ConfirmationDialogPresenter confirmationDialogPresenter = (ConfirmationDialogPresenter) confirmationDialogView.getPresenter();
            confirmationDialogPresenter.setPromptText("Are you sure that you want to delete the selected project?");
            confirmationDialogPresenter.setConfirmAction(actionEvent ->  {
                try {
                    kanbanBoDataService.deleteProject(selectedProjectRow);
                } catch (SQLException throwables) {
                    System.out.println("Could not delete project entry");
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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

    private void showProjectDetailsWindow() throws SQLException, IOException {
        StageUtils.createChildStage("Enter Project Details", projectDetailsWindowView.getView(), projectDetailsWindowPresenter.getDisplayDimensions());
        StageUtils.showAndWaitOnSubStage();
        StageUtils.closeSubStage();
    }


}
