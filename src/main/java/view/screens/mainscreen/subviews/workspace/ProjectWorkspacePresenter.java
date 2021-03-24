package view.screens.mainscreen.subviews.workspace;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import model.repository.ProjectRepositoryService;
import utils.ProjectWorkspaceController;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectWorkspacePresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private TabPane workspaceTabPane;

    // Other variables
    private enum ProjectType {ACTIVE, ARCHIVED, COMPLETED, TEMPLATE}
    private ProjectRepositoryService projectRepositoryService;

    // Constructors

    // Getters & Setters
    public ProjectRepositoryService getProjectRepositoryService() {
        return projectRepositoryService;
    }
    public void setProjectRepositoryService(ProjectRepositoryService projectRepositoryService) {
        this.projectRepositoryService = projectRepositoryService;
        customInit();
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void customInit() {

    }

    // UI event methods


    // Other methods


}