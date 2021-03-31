package view.screens.mainscreen.subviews.workspace.subviews.projectcontainer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.activerecords.ProjectActiveRecord;
import model.domainobjects.project.ActiveProjectModel;
import view.screens.mainscreen.subviews.workspace.subviews.columncontainer.ColumnContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.columncontainer.ColumnContainerView;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label projectTitleLbl;
    @FXML
    private Button createColumnBtn;
    @FXML
    private HBox columnHBox;

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
        projectTitleLbl.setText(activeRecord.getProjectTitle());
    }

    // UI event methods
    public void createColumn() {
        System.out.println("Create Column...");
        ColumnContainerView ccv = new ColumnContainerView();
        ColumnContainerPresenter ccp = (ColumnContainerPresenter) ccv.getPresenter();
        // use ccp to set data in the column data.
        columnHBox.getChildren().add(ccv.getView());
    }

    // Other methods


}