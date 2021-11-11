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
import view.sharedviewcomponents.popups.DetailsPopupInitialDataMode;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
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
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private DetailsPopupInitialDataMode initialDataMode;
    private SimpleBooleanProperty editing;
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
