package view.screens.mainscreen.subviews.manage.projectsmanagerview.subviews.activeprojectstab;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.datamodel.project.ActiveProjectModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ActiveProjectsListPresenter implements Initializable {

    // Injected JavaFX field variables
    @FXML
    private Button newProjectButton;
    @FXML
    private Button archiveProjectButton;
    @FXML
    private Button completeProjectButton;
    @FXML
    private Button deleteProjectButton;
    @FXML
    private VBox activeProjectListVBox;

    // Variables
    private List<ActiveProjectModel> projectList;

    // Constructors


    // Getters and Setters
    public Button getNewProjectButton() {
        return newProjectButton;
    }
    public void setNewProjectButton(Button newProjectButton) {
        this.newProjectButton = newProjectButton;
    }

    public Button getArchiveProjectButton() {
        return archiveProjectButton;
    }
    public void setArchiveProjectButton(Button archiveProjectButton) {
        this.archiveProjectButton = archiveProjectButton;
    }

    public Button getCompleteProjectButton() {
        return completeProjectButton;
    }
    public void setCompleteProjectButton(Button completeProjectButton) {
        this.completeProjectButton = completeProjectButton;
    }

    public Button getDeleteProjectButton() {
        return deleteProjectButton;
    }
    public void setDeleteProjectButton(Button deleteProjectButton) {
        this.deleteProjectButton = deleteProjectButton;
    }

    public VBox getActiveProjectListVBox() {
        return activeProjectListVBox;
    }
    public void setActiveProjectListVBox(VBox activeProjectListVBox) {
        this.activeProjectListVBox = activeProjectListVBox;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeProjectListVBox.getChildren().add(new Label("No projects"));

    }

    // Other methods
    public void createNewProject() {
        System.out.println("Creating a new project");
    }
    public void archiveProject() {
        System.out.println("Archiving a project");
    }
    public void completeProject() {
        System.out.println("Completing a project");
    }

    public void deleteProject() {
        System.out.println("Deleting a project");
    }

}
