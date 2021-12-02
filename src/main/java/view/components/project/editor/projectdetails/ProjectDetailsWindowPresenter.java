package view.components.project.editor.projectdetails;

import domain.viewmodels.project.ProjectStatusListViewModel;
import domain.viewmodels.project.ProjectStatusViewModel;
import persistence.dto.project.ProjectDTO;
import domain.entities.project.ObservableWorkspaceProject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import view.sharedviewcomponents.popups.DetailsWindowPresenter;
import view.sharedviewcomponents.popups.EditorDataMode;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.UUID;

public class ProjectDetailsWindowPresenter extends DetailsWindowPresenter implements Initializable {

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
    private ChoiceBox<ProjectStatusViewModel> statusChoiceBox;
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
    private String noTitleError = "A title must be provided";
    private boolean validTitle;
    private ObservableWorkspaceProject projectViewModel;
    private ProjectStatusListViewModel projectStatusListViewModel;

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
        for(ProjectStatusViewModel projectStatusViewModel : projectStatusListViewModel.getProjectStatusViewModels()) {
            if(projectStatusViewModel.getStatusId() == projectViewModel.statusIDProperty().getValue()) {
                statusChoiceBox.setValue(projectStatusViewModel);
            }
        }
        if(projectViewModel.dueOnDateProperty() != null) {
            dueOnDatePicker.setValue(LocalDate.parse(projectViewModel.dueOnDateProperty().getValue()));
        }
        createdOnTextField.setText(projectViewModel.creationTimestampProperty().getValue());
        lastChangedTextField.setText(projectViewModel.lastChangedTimestampProperty().getValue());
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
        this.editorDataMode = EditorDataMode.CREATION;
        try {
            projectStatusListViewModel = new ProjectStatusListViewModel(kanbanBoDataService.getProjectStatusTableAsList());
            statusChoiceBox.setItems(projectStatusListViewModel.getProjectStatusViewModels());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initTitleValidationFields();
        initializeEditor();
        establishTitleTextFieldValidation();
    }

    private void initTitleValidationFields() {
        validTitle = true;
        titleErrorLbl.setText(noTitleError);
        titleErrorLbl.setVisible(false);
        titleErrorLbl.setDisable(true);
    }


    // Other methods
    public void saveProjectDetailsChange() throws SQLException, IOException, ParseException {
        if(validTitle) {
            ProjectDTO.Builder projectDTOBuilder = ProjectDTO.Builder.newInstance("")
                    .title(projectTitleTextField.getText())
                    .description(projectDescriptionTextArea.getText())
                    .statusId(statusChoiceBox.getSelectionModel().getSelectedItem().getStatusId())
                    .dueOnDate(dueOnDatePicker.getValue().toString());
            if(editorDataMode == EditorDataMode.CREATION) {
                projectDTOBuilder.uuid(UUID.randomUUID().toString())
                        .createdOnTimeStamp(LocalDateTime.now().toString())
                        .lastChangedOnTimeStamp(LocalDateTime.now().toString());
                ProjectDTO newProjectData = new ProjectDTO(projectDTOBuilder);
                kanbanBoDataService.createProject(newProjectData);
            } else if(editorDataMode == EditorDataMode.EDITING) {
                projectDTOBuilder.uuid(projectViewModel.getProjectUUID().toString())
                        .createdOnTimeStamp(projectViewModel.creationTimestampProperty().getValue())
                        .lastChangedOnTimeStamp(LocalDateTime.now().toString());
                ProjectDTO projectDTO = new ProjectDTO(projectDTOBuilder);
                // TODO remove the viewmodel from the call to the service. Move the viewmodel into its own repository/list and use that to perform the view updates.
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

    protected void prepareViewForCreation() {
        saveBtn.setDisable(false);
        projectTitleTextField.setEditable(true);
        projectDescriptionTextArea.setEditable(true);
        popupStateLbl.setVisible(false);
        projectTitleTextField.setDisable(false);
        projectDescriptionTextArea.setDisable(false);
        statusChoiceBox.setValue(projectStatusListViewModel.getSelectedProjectStatus());
        statusChoiceBox.setDisable(true);
        dueOnDatePicker.setDisable(false);
        createdOnTextField.setDisable(true);
        lastChangedTextField.setDisable(true);
    }

    protected void prepareViewForDisplay(){
        saveBtn.setDisable(true);
        projectTitleTextField.setDisable(true);
        projectDescriptionTextArea.setDisable(true);
        popupStateLbl.setVisible(false);
        projectTitleTextField.setDisable(true);
        projectDescriptionTextArea.setDisable(true);
        for(ProjectStatusViewModel projectStatusViewModel : projectStatusListViewModel.getProjectStatusViewModels()) {
            if(projectStatusViewModel.getStatusId() == projectViewModel.statusIDProperty().getValue()) {
                statusChoiceBox.getSelectionModel().select(projectStatusViewModel);
            }
        }
        statusChoiceBox.setDisable(false);
        dueOnDatePicker.setDisable(true);
        createdOnTextField.setDisable(true);
        lastChangedTextField.setDisable(true);
    }

    protected void prepareViewForEditing() {
        saveBtn.setDisable(false);
        projectTitleTextField.setEditable(true);
        projectDescriptionTextArea.setEditable(true);
        popupStateLbl.setVisible(false);
        projectTitleTextField.setDisable(false);
        projectDescriptionTextArea.setDisable(false);
        for(ProjectStatusViewModel projectStatusViewModel : projectStatusListViewModel.getProjectStatusViewModels()) {
            if(projectStatusViewModel.getStatusId() == projectViewModel.statusIDProperty().getValue()) {
                statusChoiceBox.getSelectionModel().select(projectStatusViewModel);
            }
        }
        statusChoiceBox.setDisable(false);
        dueOnDatePicker.setDisable(false);
        createdOnTextField.setDisable(true);
        lastChangedTextField.setDisable(true);
    }

}
