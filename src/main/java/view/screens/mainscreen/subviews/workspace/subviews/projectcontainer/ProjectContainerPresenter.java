package view.screens.mainscreen.subviews.workspace.subviews.projectview;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.activerecords.ProjectActiveRecord;
import model.domainobjects.project.AbstractProjectModel;
import model.domainobjects.project.ActiveProjectModel;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label titleText;
    @FXML
    private Label createDate;
    @FXML
    private Label lastChanged;

    // Other variables
    //private AbstractProjectModel projectModel;
    private ProjectActiveRecord<ActiveProjectModel> activeRecord;


    // Constructors

    // Getters & Setters
    /*public AbstractProjectModel getProjectModel() {
        return projectModel;
    }
    public void setProjectModel(AbstractProjectModel projectModel) {
        this.projectModel = projectModel;
        doTheThings();
    }
     */

    public ProjectActiveRecord<ActiveProjectModel> getActiveRecord() {
        return activeRecord;
    }
    public void setActiveRecord(ProjectActiveRecord<ActiveProjectModel> activeRecord) {
        this.activeRecord = activeRecord;
        customInit();
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void customInit() {
        titleText.setText(activeRecord.getProjectTitle());
        createDate.setText(activeRecord.getCreationTimestamp());
        lastChanged.setText(activeRecord.getLastChangedTimestamp());
    }

    // UI event methods

    // Other methods
    private void doTheThings() {

    }

}