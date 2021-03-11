package view.screens.mainscreen.subviews.projectsmanagerview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectsManagerPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private TabPane projectManagementTabPane;

    // Other variables

    // Constructors


    // Getters and Setters
    public TabPane getProjectManagementTabPane() {
        return projectManagementTabPane;
    }
    public void setProjectManagementTabPane(TabPane projectManagementTabPane) {
        this.projectManagementTabPane = projectManagementTabPane;
    }


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
