package view.sharedcomponents.popups.columndetails;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import model.activerecords.project.ProjectActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.column.ColumnModel;
import model.domainobjects.project.ProjectModel;
import utils.StageUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ColumnDetailsWindowPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private TextField titleTextField;
    @FXML
    private Label titleErrorLbl;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    // Variables
    private String noTitleError = "A title must be provided";
    private boolean validTitle;
    private ProjectColumnActiveRecord<ColumnModel> projectColumnActiveRecord;
    private ProjectActiveRecord parentProject;

    // Constructors


    // Getters and Setters
    public TextField getTitleTextField() {
        return titleTextField;
    }
    public void setTitleTextField(TextField titleTextField) {
        this.titleTextField = titleTextField;
    }

    public ProjectColumnActiveRecord<ColumnModel> getProjectColumnActiveRecord() {
        return projectColumnActiveRecord;
    }
    public void setProjectColumnActiveRecord(ProjectColumnActiveRecord<ColumnModel> projectColumnActiveRecord) {
        this.projectColumnActiveRecord = projectColumnActiveRecord;
        titleTextField.setText(projectColumnActiveRecord.getColumnTitle());
    }

    public ProjectActiveRecord getParentProject() {
        return parentProject;
    }
    public void setParentProject(ProjectActiveRecord parentProject) {
        this.parentProject = parentProject;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validTitle = true;
        titleErrorLbl.setText(noTitleError);
        titleErrorLbl.setVisible(false);
        titleErrorLbl.setDisable(true);
        establishTitleTextFieldValidation();
    }


    // UI event methods
    public void saveDetailsChange() throws IOException, SQLException {
        if(validTitle) {
            if(projectColumnActiveRecord == null) {
                projectColumnActiveRecord = new ProjectColumnActiveRecord(ColumnModel.class);
                // TODO make new project building the responsibility of class ProjectActiveRecord
                projectColumnActiveRecord.setProjectColumnModel(buildNewColumnModelInstance());
            } else {
                projectColumnActiveRecord.setColumnTitle(titleTextField.getText());
            }
            StageUtils.hideSubStage();
        } else {
            titleErrorLbl.setVisible(true);
            titleErrorLbl.setDisable(false);
        }
    }


    public void cancelDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }


    // Other methods
    private ColumnModel buildNewColumnModelInstance() {
        // Create blank column instance, and populate with data.
        ColumnModel columnModel = new ColumnModel();
        columnModel.setColumn_title(titleTextField.getText());
        columnModel.setParent_project_uuid(parentProject.getProjectUUID());
        return columnModel;
    }

    private void establishTitleTextFieldValidation() {
        titleTextField.setTextFormatter(new TextFormatter<Object>(change -> {
            change = change.getControlNewText().matches(".{0,50}") ? change : null;
            if (change != null && change.getControlNewText().length() < 1) {
                validTitle = false;
            } else {
                validTitle = true;
            }
            return change;
        }));
    }
}
