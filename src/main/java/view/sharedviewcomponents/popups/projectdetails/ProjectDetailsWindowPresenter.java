package view.sharedviewcomponents.popups.projectdetails;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import domain.activerecords.project.ProjectActiveRecord;
import persistence.tables.project.ProjectTable;
import utils.StageUtils;
import view.sharedviewcomponents.DetailsPopupInitialDataMode;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ProjectDetailsWindowPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private Button editBtn;
    @FXML
    private TextField projectTitleTextField;
    @FXML
    private Label titleErrorLbl;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    // Variables
    private DetailsPopupInitialDataMode initialDataMode;
    private SimpleBooleanProperty editing;
    private String noTitleError = "A title must be provided";
    private boolean validTitle;
    private ProjectTable selectedProjectModel;
    private ProjectActiveRecord projectActiveRecord;

    // Constructors


    // Getters and Setters
    public TextField getProjectTitleTextField() {
        return projectTitleTextField;
    }
    public void setProjectTitleTextField(TextField projectTitleTextField) {
        this.projectTitleTextField = projectTitleTextField;
    }

    public TextArea getProjectDescriptionTextArea() {
        return projectDescriptionTextArea;
    }
    public void setProjectDescriptionTextArea(TextArea projectDescriptionTextArea) {
        this.projectDescriptionTextArea = projectDescriptionTextArea;
    }

    public Button getSaveButton() {
        return saveButton;
    }
    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }
    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public ProjectTable getSelectedActiveProjectModel() {
        return selectedProjectModel;
    }
    public void setSelectedActiveProjectModel(ProjectTable selectedProjectModel) {
        this.selectedProjectModel = selectedProjectModel;
    }

    public ProjectActiveRecord getProjectActiveRecord() {
        return projectActiveRecord;
    }
    public void setProjectActiveRecord(ProjectActiveRecord projectActiveRecord) {
        this.projectActiveRecord = projectActiveRecord;
        projectTitleTextField.setText(projectActiveRecord.getProjectTitle());
        projectDescriptionTextArea.setText(projectActiveRecord.getProjectDescription());
    }

    public void setInitialDataMode(DetailsPopupInitialDataMode initialDataMode) {
        this.initialDataMode = initialDataMode;
        lateInit();
    }

    public double[] getDisplayDimensions() {
        double[] dimensions = new double[6];
        dimensions[0] = 300;
        dimensions[1] = 400;
        dimensions[2] = 300;
        dimensions[3] = 400;
        dimensions[4] = -1;
        dimensions[5] = -1;
        return dimensions;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialDataMode = DetailsPopupInitialDataMode.DISPLAY;
        editing = new SimpleBooleanProperty(true);
        updateEditingChangeListener();
        initTitleValidationFields();
        lateInit();
        establishTitleTextFieldValidation();
    }

    private void initTitleValidationFields() {
        validTitle = true;
        titleErrorLbl.setText(noTitleError);
        titleErrorLbl.setVisible(false);
        titleErrorLbl.setDisable(true);
    }

    private void lateInit() {
        switch (initialDataMode) {
            case CREATE:
                prepareViewForProjectCreation();
                break;
            case DISPLAY:
                prepareViewForProjectDisplay();
                break;
            case EDIT:
                prepareViewForProjectEditing();
                break;
        }
    }


    // Other methods
    public void saveProjectDetailsChange() throws SQLException, IOException {
        if(validTitle) {
            if(projectActiveRecord == null) {
                projectActiveRecord = new ProjectActiveRecord();
                // TODO make new project building the responsibility of class ProjectActiveRecord
                projectActiveRecord.setProjectModel(buildNewProjectModelInstance());
            } else {
                projectActiveRecord.setProjectTitle(getProjectTitleTextField().getText());
                projectActiveRecord.setProjectDescription(getProjectDescriptionTextArea().getText());
          }
            projectActiveRecord.createOrUpdateActiveRowInDb();
            StageUtils.hideSubStage();
        } else {
            titleErrorLbl.setVisible(true);
            titleErrorLbl.setDisable(false);
        }
    }


    public void cancelProjectDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }


    private ProjectTable buildNewProjectModelInstance() {
        // Create blank project instance, and populate with data.
        ProjectTable newProjectModel = new ProjectTable();
        newProjectModel.setProject_title(getProjectTitleTextField().getText());
        newProjectModel.setProject_description(getProjectDescriptionTextArea().getText());
        newProjectModel.setCreation_timestamp(new Date());
        newProjectModel.setLast_changed_timestamp(new Date());
        newProjectModel.setProject_status_id(1);
        return newProjectModel;
    }

    private void establishTitleTextFieldValidation() {
        projectTitleTextField.setTextFormatter(new TextFormatter<Object>(change -> {
            change = change.getControlNewText().matches(".{0,50}") ? change : null;
            if (change != null && change.getControlNewText().length() < 1) {
                validTitle = false;
            } else {
                validTitle = true;
            }
            return change;
        }));
    }

    public void toggleEditMode() {
        if (editing.get()) {
            editing.setValue(false);
        } else {
            editing.setValue(true);
        }
    }

    private void prepareViewForProjectCreation() {
        editBtn.setDisable(true);
        editBtn.setVisible(false);
        saveButton.setDisable(false);
        editing.setValue(true);
    }

    private void prepareViewForProjectDisplay(){
        editBtn.setDisable(false);
        editBtn.setVisible(true);
        editing.setValue(false);
    }

    private void prepareViewForProjectEditing() {
        editBtn.setDisable(false);
        editBtn.setVisible(true);
        editing.setValue(true);
    }

    private void updateEditingChangeListener() {
        editing.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                // TODO Need to examine the css file further to ensure that readonly fields are properly displayed to user
                if (newValue) {
                    editBtn.setText("Display");
                    saveButton.setDisable(false);
                    cancelButton.setText("Cancel");
                    projectTitleTextField.setEditable(true);
                    projectDescriptionTextArea.setEditable(true);
                } else {
                    editBtn.setText("Edit");
                    saveButton.setDisable(true);
                    cancelButton.setText("Close");
                    projectTitleTextField.setEditable(false);
                    projectDescriptionTextArea.setEditable(false);
                }
            }
        });

    }
}
