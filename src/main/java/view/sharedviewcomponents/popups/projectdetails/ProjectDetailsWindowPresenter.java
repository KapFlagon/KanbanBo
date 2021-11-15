package view.sharedviewcomponents.popups.projectdetails;

import persistence.dto.project.ProjectDTO;
import domain.entities.project.ObservableWorkspaceProject;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import view.sharedviewcomponents.popups.EditorDataMode;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class ProjectDetailsWindowPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private Label popupStateLbl;
    @FXML
    private TextField projectTitleTextField;
    @FXML
    private Label titleErrorLbl;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private ComboBox statusComboBox;
    @FXML
    private DatePicker dueOnDatePicker;
    @FXML
    private TextField createdOnTextField;
    @FXML
    private TextField lastChangedTextField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    // Variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private EditorDataMode editorDataMode;
    private String noTitleError = "A title must be provided";
    private boolean validTitle;
    private ObservableWorkspaceProject projectViewModel;

    // Constructors


    // Getters and Setters
    public TextField getProjectTitleTextField() {
        return projectTitleTextField;
    }

    public TextArea getProjectDescriptionTextArea() {
        return projectDescriptionTextArea;
    }

    public ObservableWorkspaceProject getProjectViewModel() {
        return projectViewModel;
    }
    public void setProjectViewModel(ObservableWorkspaceProject projectViewModel) {
        this.projectViewModel = projectViewModel;
        projectTitleTextField.textProperty().set(projectViewModel.projectTitleProperty().getValue());
        projectDescriptionTextArea.textProperty().set(projectViewModel.projectDescriptionProperty().getValue());
        statusComboBox.setValue(projectViewModel.statusTextProperty().getValue());
        //dueOnDatePicker.setValue(projectViewModel.);
        createdOnTextField.setText(projectViewModel.creationTimestampProperty().getValue());
        lastChangedTextField.setText(projectViewModel.lastChangedTimestampProperty().getValue());
    }

    public void setEditorDataMode(EditorDataMode editorDataMode) {
        this.editorDataMode = editorDataMode;
        lateInit();
    }

    public double[] getDisplayDimensions() {
        double[] dimensions = new double[6];
        dimensions[0] = 440;
        dimensions[1] = 560;
        dimensions[2] = 440;
        dimensions[3] = 560;
        dimensions[4] = -1;
        dimensions[5] = -1;
        return dimensions;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editorDataMode = EditorDataMode.DISPLAY;
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
        switch (editorDataMode) {
            case CREATION:
                prepareViewForProjectCreation();
                break;
            case DISPLAY:
                prepareViewForProjectDisplay();
                break;
            case EDITING:
                prepareViewForProjectEditing();
                break;
        }
    }


    // Other methods
    public void saveProjectDetailsChange() throws SQLException, IOException, ParseException {
        if(validTitle) {
            if(projectViewModel == null) {
                ProjectDTO newProjectData = new ProjectDTO();
                newProjectData.setTitle(projectTitleTextField.getText());
                newProjectData.setDescription(projectDescriptionTextArea.getText());
                kanbanBoDataService.createProject(newProjectData);
            } else {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setTitle(getProjectTitleTextField().getText());
                projectDTO.setDescription(getProjectDescriptionTextArea().getText());
                kanbanBoDataService.updateProject(projectDTO, projectViewModel);
            }
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

    private void prepareViewForProjectCreation() {
        saveBtn.setDisable(false);
        projectTitleTextField.setEditable(true);
        projectDescriptionTextArea.setEditable(true);
        popupStateLbl.setVisible(false);
        projectTitleTextField.setDisable(false);
        projectDescriptionTextArea.setDisable(false);
        statusComboBox.setDisable(true);
        dueOnDatePicker.setDisable(false);
        createdOnTextField.setDisable(true);
        lastChangedTextField.setDisable(true);
    }

    private void prepareViewForProjectDisplay(){
        saveBtn.setDisable(true);
        projectTitleTextField.setDisable(true);
        projectDescriptionTextArea.setDisable(true);
        popupStateLbl.setVisible(false);
        projectTitleTextField.setDisable(true);
        projectDescriptionTextArea.setDisable(true);
        statusComboBox.setDisable(false);
        dueOnDatePicker.setDisable(true);
        createdOnTextField.setDisable(true);
        lastChangedTextField.setDisable(true);
    }

    private void prepareViewForProjectEditing() {
        saveBtn.setDisable(false);
        projectTitleTextField.setEditable(true);
        projectDescriptionTextArea.setEditable(true);
        popupStateLbl.setVisible(false);
        projectTitleTextField.setDisable(false);
        projectDescriptionTextArea.setDisable(false);
        statusComboBox.setDisable(false);
        dueOnDatePicker.setDisable(false);
        createdOnTextField.setDisable(true);
        lastChangedTextField.setDisable(true);
    }

}
