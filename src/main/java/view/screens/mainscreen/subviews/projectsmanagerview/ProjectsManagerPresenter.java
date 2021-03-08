package view.screens.mainscreen.subviews.projectsmanagerview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectsManagerPresenter implements Initializable {

    // JavaFX injected fields
    @FXML
    private Button newProjectButton;
    @FXML
    private Button openProjectButton;
    @FXML
    private Button deleteProjectButton;
    @FXML
    private ScrollPane mainContentArea;


    // Constructors


    // Getters and Setters
    public Button getNewProjectButton() {
        return newProjectButton;
    }
    public void setNewProjectButton(Button newProjectButton) {
        this.newProjectButton = newProjectButton;
    }

    public Button getOpenProjectButton() {
        return openProjectButton;
    }
    public void setOpenProjectButton(Button openProjectButton) {
        this.openProjectButton = openProjectButton;
    }

    public Button getDeleteProjectButton() {
        return deleteProjectButton;
    }
    public void setDeleteProjectButton(Button deleteProjectButton) {
        this.deleteProjectButton = deleteProjectButton;
    }

    public ScrollPane getMainContentArea() {
        return mainContentArea;
    }
    public void setMainContentArea(ScrollPane mainContentArea) {
        this.mainContentArea = mainContentArea;
    }


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    // Other methods
    public void createNewProject() {
        System.out.println("Creating new Project");
    }


    public void  openSelectedProject() {
        System.out.println("Opening selected Project");
    }


    public void deleteSelectedProject() {
        System.out.println("Deleting selected Project");
    }



}
