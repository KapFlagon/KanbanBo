package view.screens.mainscreen.subviews.manage.projectsmanagerview.subviews.activeprojectlistentry;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ActiveProjectListEntryPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private Label projectListEntryTitle;
    @FXML
    private Label projectListEntryCreatedOn;
    @FXML
    private Label projectListEntryLastChanged;

    // Variables


    // Constructors


    // Getters and Setters
    public Label getProjectListEntryTitle() {
        return projectListEntryTitle;
    }
    public void setProjectListEntryTitle(Label projectListEntryTitle) {
        this.projectListEntryTitle = projectListEntryTitle;
    }

    public Label getProjectListEntryCreatedOn() {
        return projectListEntryCreatedOn;
    }
    public void setProjectListEntryCreatedOn(Label projectListEntryCreatedOn) {
        this.projectListEntryCreatedOn = projectListEntryCreatedOn;
    }

    public Label getProjectListEntryLastChanged() {
        return projectListEntryLastChanged;
    }
    public void setProjectListEntryLastChanged(Label projectListEntryLastChanged) {
        this.projectListEntryLastChanged = projectListEntryLastChanged;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    // Other methods


}
