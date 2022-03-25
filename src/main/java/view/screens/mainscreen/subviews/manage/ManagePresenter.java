package view.screens.mainscreen.subviews.manage;

import domain.entities.project.ObservableWorkspaceProject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import view.components.project.table.body.ProjectsTableBodyPresenter;
import view.components.project.table.body.ProjectsTableBodyView;
import view.sharedviewcomponents.popups.EditorDataMode;
import view.sharedviewcomponents.popups.confirmationdialog.ConfirmationDialogPresenter;
import view.sharedviewcomponents.popups.confirmationdialog.ConfirmationDialogView;
import view.components.project.editor.projectdetails.ProjectDetailsWindowPresenter;
import view.components.project.editor.projectdetails.ProjectDetailsWindowView;

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
    private ProjectsTableBodyView projectsTableBodyView;
    private ProjectsTableBodyPresenter projectsTableBodyPresenter;
    private ProjectDetailsWindowView projectDetailsWindowView;
    private ProjectDetailsWindowPresenter projectDetailsWindowPresenter;

    // Constructors


    // Getters and Setters


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtonImages();
        projectsTableBodyView = new ProjectsTableBodyView();
        projectsTableBodyPresenter = (ProjectsTableBodyPresenter) projectsTableBodyView.getPresenter();
        projectsTableBodyPresenter.setProjectTableViewModel(kanbanBoDataService.getProjectsList());
        mainContainer.setCenter(projectsTableBodyView.getView());
    }

    private void initProjectDetailsUI() {
        projectDetailsWindowView = new ProjectDetailsWindowView();
        projectDetailsWindowPresenter = (ProjectDetailsWindowPresenter) projectDetailsWindowView.getPresenter();
    }

    private void initButtonImages() {
        ImageView newProjectImageView = new ImageView(getClass().getResource("/icons/create_new_folder/materialicons/black/res/drawable-mdpi/baseline_create_new_folder_black_18.png").toExternalForm());
        ImageView openProjectImageView = new ImageView(getClass().getResource("/icons/open_in_new/materialicons/black/res/drawable-mdpi/baseline_open_in_new_black_18.png").toExternalForm());
        ImageView accessProjectImageView = new ImageView(getClass().getResource("/icons/edit_note/materialicons/black/res/drawable-mdpi/baseline_edit_note_black_18.png").toExternalForm());
        ImageView deleteProjectImageView = new ImageView(getClass().getResource("/icons/delete/materialicons/black/res/drawable-mdpi/baseline_delete_black_18.png").toExternalForm());
        ImageView duplicateProjectImageView = new ImageView(getClass().getResource("/icons/content_copy/materialicons/black/res/drawable-mdpi/baseline_content_copy_black_18.png").toExternalForm());
        newProjectBtn.setGraphic(newProjectImageView);
        openProjectBtn.setGraphic(openProjectImageView);
        accessProjectDetailsBtn.setGraphic(accessProjectImageView);
        deleteProjectBtn.setGraphic(deleteProjectImageView);
        duplicateProjectBtn.setGraphic(duplicateProjectImageView);
    }


    // UI Events
    @FXML private void createNewProject() throws SQLException, IOException {
        System.out.println("Creating a new project");
        initProjectDetailsUI();
        projectDetailsWindowPresenter.setEditorDataMode(EditorDataMode.CREATION);
        showProjectDetailsWindow();
        // TODO maybe insert error handling here to show error to user.
    }

    @FXML private void openSelectedProject() throws SQLException, IOException {
        System.out.println("Trying to open project in workspace");
        ObservableWorkspaceProject selectedProjectRow = projectsTableBodyPresenter.getSelectedRow();

        if (selectedProjectRow != null) {
            System.out.println("Project selected is not null");
            kanbanBoDataService.openProjectInWorkspace(selectedProjectRow.getProjectUUID());
        }
    }


    @FXML private void accessProjectDetails() throws SQLException, IOException {
        System.out.println("Trying to open project in manager for editing.");
        ObservableWorkspaceProject observableWorkspaceProject = projectsTableBodyPresenter.getSelectedRow();
        if (observableWorkspaceProject != null) {
            System.out.println("Project selected is not null");
            initProjectDetailsUI();
            projectDetailsWindowPresenter.setProjectViewModel(observableWorkspaceProject);
            // TODO Need to read project status, and determine "Editing" or "Display" mode based on this...
            projectDetailsWindowPresenter.setEditorDataMode(EditorDataMode.EDITING);
            showProjectDetailsWindow();
        } else {
            System.out.println("selected project was found to be null");
        }
    }



    public void copySelectedProject() {
        // TODO Add option to deep duplicate a project, including all of its contents, but with new UUID values.
    }

    public void deleteSelectedProject() {
        System.out.println("Deleting a project");
        ObservableWorkspaceProject selectedProjectRow = projectsTableBodyPresenter.getSelectedRow();
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
            //StageUtils.createChildStage("Confirm deletion", confirmationDialogView.getView(), confirmationDialogPresenter.getDisplayDimensions());
            StageUtils.createChildStage("Confirm deletion", confirmationDialogView.getView());
            StageUtils.showAndWaitOnSubStage();
        } else {
            System.out.println("selected project was found to be null");
        }
    }

    private void showProjectDetailsWindow() throws SQLException, IOException {
        //StageUtils.createChildStage("Enter Project Details", projectDetailsWindowView.getView(), projectDetailsWindowPresenter.getDisplayDimensions());
        StageUtils.createChildStage("Enter Project Details", projectDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        StageUtils.closeSubStage();
    }


}
