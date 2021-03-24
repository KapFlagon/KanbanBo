package view.screens.mainscreen.subviews.workspace.subviews.projectview;

import javafx.fxml.Initializable;
import model.domainobjects.project.AbstractProjectModel;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectContainerPresenter implements Initializable {

    // JavaFX injected node variables

    // Other variables
    private AbstractProjectModel projectModel;


    // Constructors

    // Getters & Setters
    public AbstractProjectModel getProjectModel() {
        return projectModel;
    }
    public void setProjectModel(AbstractProjectModel projectModel) {
        this.projectModel = projectModel;
        doTheThings();
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // UI event methods

    // Other methods
    private void doTheThings() {

    }

}