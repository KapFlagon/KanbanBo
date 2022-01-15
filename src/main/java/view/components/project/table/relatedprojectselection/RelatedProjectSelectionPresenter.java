package view.components.project.table.relatedprojectselection;

import domain.entities.project.ObservableWorkspaceProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import view.components.project.editor.projectdetails.ProjectDetailsWindowPresenter;
import view.components.project.editor.projectdetails.ProjectDetailsWindowView;
import view.components.project.table.body.ProjectsTableBodyPresenter;
import view.components.project.table.body.ProjectsTableBodyView;
import view.sharedviewcomponents.popups.EditorDataMode;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;

public class RelatedProjectSelectionPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private BorderPane mainContainer;
    @FXML
    private Button createAndSelectProjectBtn;
    @FXML
    private Button selectProjectBtn;
    @FXML
    private Button duplicateProjectBtn;


    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ProjectsTableBodyView projectsTableBodyView;
    private ProjectsTableBodyPresenter projectsTableBodyPresenter;
    private ProjectDetailsWindowView projectDetailsWindowView;
    private ProjectDetailsWindowPresenter projectDetailsWindowPresenter;
    private ObservableList<ObservableWorkspaceProject> projectsList;
    private UUID selectedProjectUUID;

    // Constructors


    // Getters and Setters
    /*public UUID selectedProjectUUID() {
        return selectedProjectUUID;
    }*/
    public ObservableWorkspaceProject getSelectedProject() {
        return projectsTableBodyPresenter.getSelectedRow();
    }

    public void setProjectsList(ObservableList<ObservableWorkspaceProject> projectsList) {
        this.projectsList = projectsList;
        projectsTableBodyPresenter.setProjectTableViewModel(projectsList);
    }

    // TODO Need to return either a UUID or some other reference to the selected project BACK to the selection screen.
    // TODO On project creation/copy, the new copy needs to be written to DB, then passed BACK to the selection screen.
    // TODO After data is pushed back to the selection screen, it writes an entry for the parent card

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtonImages();
        projectsTableBodyView = new ProjectsTableBodyView();
        projectsTableBodyPresenter = (ProjectsTableBodyPresenter) projectsTableBodyView.getPresenter();
        //projectsTableBodyPresenter.setProjectTableViewModel(kanbanBoDataService.getProjectsList());
        mainContainer.setCenter(projectsTableBodyView.getView());
        projectsList = FXCollections.observableArrayList();
    }

    private void initProjectDetailsUI() {
        projectDetailsWindowView = new ProjectDetailsWindowView();
        projectDetailsWindowPresenter = (ProjectDetailsWindowPresenter) projectDetailsWindowView.getPresenter();
    }

    private void initButtonImages() {
        ImageView newProjectImageView = new ImageView(getClass().getResource("/icons/create_new_folder/materialicons/black/res/drawable-mdpi/baseline_create_new_folder_black_18.png").toExternalForm());
        ImageView openProjectImageView = new ImageView(getClass().getResource("/icons/open_in_new/materialicons/black/res/drawable-mdpi/baseline_open_in_new_black_18.png").toExternalForm());
        ImageView duplicateProjectImageView = new ImageView(getClass().getResource("/icons/content_copy/materialicons/black/res/drawable-mdpi/baseline_content_copy_black_18.png").toExternalForm());
        createAndSelectProjectBtn.setGraphic(newProjectImageView);
        selectProjectBtn.setGraphic(openProjectImageView);
        duplicateProjectBtn.setGraphic(duplicateProjectImageView);
    }


    // UI Events
    @FXML private void selectProject() throws SQLException, IOException {
        System.out.println("Trying to open project in workspace");
        selectedProjectUUID = projectsTableBodyPresenter.getSelectedRow().getProjectUUID();
        StageUtils.closeSubStage();
    }

    @FXML private void createAndSelectProject() throws SQLException, IOException {
        System.out.println("Creating a new project");
        initProjectDetailsUI();
        projectDetailsWindowPresenter.setEditorDataMode(EditorDataMode.CREATION);
        showProjectDetailsWindow();
        selectedProjectUUID = projectDetailsWindowPresenter.getProjectViewModel().getProjectUUID();
        for(ObservableWorkspaceProject observableWorkspaceProject : projectsList) {
            boolean projectNotIdentified = true;
            while(projectNotIdentified) {
                if (observableWorkspaceProject.getProjectUUID().equals(selectedProjectUUID)) {
                    projectsTableBodyPresenter.setSelectedRow(observableWorkspaceProject);
                    projectNotIdentified = false;
                }
            }
        }
        // TODO maybe insert error handling here to show error to user.
        StageUtils.closeSubStage();
    }



    public void copySelectedProject() {
        // TODO Add option to deep duplicate a project, including all of its contents, but with new UUID values.
        /*for(ObservableWorkspaceProject observableWorkspaceProject : projectsList) {
            boolean projectNotIdentified = true;
            while(projectNotIdentified) {
                if (observableWorkspaceProject.getProjectUUID().equals(selectedProjectUUID)) {
                    projectsTableBodyPresenter.setSelectedRow(observableWorkspaceProject);
                    projectNotIdentified = false;
                }
            }
        }*/
        StageUtils.closeSubStage();
    }



    private void showProjectDetailsWindow() throws SQLException, IOException {
        StageUtils.createChildStage("Enter Project Details", projectDetailsWindowView.getView(), projectDetailsWindowPresenter.getDisplayDimensions());
        StageUtils.showAndWaitOnSubStage();
        StageUtils.closeSubStage();
    }


}
