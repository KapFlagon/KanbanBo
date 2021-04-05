package view.sharedcomponents.popups.columndetails;

import com.j256.ormlite.dao.Dao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.activerecords.ProjectActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.column.ActiveProjectColumnModel;
import model.domainobjects.project.ActiveProjectModel;
import utils.StageUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

public class ColumnDetailsWindowPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private TextField titleTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    // Variables
    private ProjectColumnActiveRecord<ActiveProjectColumnModel> projectColumnActiveRecord;
    private ProjectActiveRecord<ActiveProjectModel> parentProject;

    // Constructors


    // Getters and Setters
    public TextField getTitleTextField() {
        return titleTextField;
    }
    public void setTitleTextField(TextField titleTextField) {
        this.titleTextField = titleTextField;
    }

    public ProjectColumnActiveRecord<ActiveProjectColumnModel> getProjectColumnActiveRecord() {
        return projectColumnActiveRecord;
    }
    public void setProjectColumnActiveRecord(ProjectColumnActiveRecord<ActiveProjectColumnModel> projectColumnActiveRecord) {
        this.projectColumnActiveRecord = projectColumnActiveRecord;
        titleTextField.setText(projectColumnActiveRecord.getColumnTitle());
    }

    public ProjectActiveRecord<ActiveProjectModel> getParentProject() {
        return parentProject;
    }
    public void setParentProject(ProjectActiveRecord<ActiveProjectModel> parentProject) {
        this.parentProject = parentProject;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    // UI event methods
    public void saveDetailsChange() throws IOException, SQLException {
        if(projectColumnActiveRecord == null) {
            projectColumnActiveRecord = new ProjectColumnActiveRecord(ActiveProjectColumnModel.class);
            // TODO make new project building the responsibility of class ProjectActiveRecord
            projectColumnActiveRecord.setProjectColumnModel(buildNewColumnModelInstance());
        } else {
            projectColumnActiveRecord.setColumnTitle(titleTextField.getText());
        }
        StageUtils.hideSubStage();
    }


    public void cancelDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }


    // Other methods
    private ActiveProjectColumnModel buildNewColumnModelInstance() {
        // Create blank column instance, and populate with data.
        ActiveProjectColumnModel activeProjectColumnModel = new ActiveProjectColumnModel();
        activeProjectColumnModel.setColumn_title(titleTextField.getText());
        activeProjectColumnModel.setParent_project_uuid(parentProject.getProjectUUID());
        return activeProjectColumnModel;
    }

}
