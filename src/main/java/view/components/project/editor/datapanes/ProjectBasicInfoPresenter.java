package view.components.project.editor.datapanes;

import domain.entities.project.ObservableWorkspaceProject;
import domain.viewmodels.project.ProjectStatusListViewModel;
import domain.viewmodels.project.ProjectStatusViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import persistence.dto.project.ProjectDTO;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import view.sharedviewcomponents.editor.datapane.DataPanePresenter;
import view.sharedviewcomponents.popups.EditorDataMode;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.UUID;

public class ProjectBasicInfoPresenter extends DataPanePresenter {

    // JavaFX injected node variables
    @FXML
    private TextField projectTitleTextField;
    @FXML
    private Label titleErrorLbl;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private ChoiceBox<ProjectStatusViewModel> statusChoiceBox;

    // Other variables
    KanbanBoDataService kanbanBoDataService;
    private String noTitleError = "A title must be provided";
    private boolean validTitle;
    private ObservableWorkspaceProject projectViewModel;
    private ProjectStatusListViewModel projectStatusListViewModel;

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
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location,resources);
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
        initializeDataPaneState();
        establishTitleTextFieldValidation();
    }

    private void initTitleValidationFields() {
        validTitle = true;
        titleErrorLbl.setText(noTitleError);
        titleErrorLbl.setVisible(false);
        titleErrorLbl.setDisable(true);
    }


    // Other methods
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
        projectTitleTextField.setEditable(true);
        projectDescriptionTextArea.setEditable(true);
        projectTitleTextField.setDisable(false);
        projectDescriptionTextArea.setDisable(false);
        statusChoiceBox.setValue(projectStatusListViewModel.getSelectedProjectStatus());
        statusChoiceBox.setDisable(true);
    }

    protected void prepareViewForDisplay(){
        projectTitleTextField.setDisable(true);
        projectDescriptionTextArea.setDisable(true);
        projectTitleTextField.setDisable(true);
        projectDescriptionTextArea.setDisable(true);
        for(ProjectStatusViewModel projectStatusViewModel : projectStatusListViewModel.getProjectStatusViewModels()) {
            if(projectStatusViewModel.getStatusId() == projectViewModel.statusIDProperty().getValue()) {
                statusChoiceBox.getSelectionModel().select(projectStatusViewModel);
            }
        }
        statusChoiceBox.setDisable(false);
    }

    protected void prepareViewForEditing() {
        projectTitleTextField.setEditable(true);
        projectDescriptionTextArea.setEditable(true);
        projectTitleTextField.setDisable(false);
        projectDescriptionTextArea.setDisable(false);
        for(ProjectStatusViewModel projectStatusViewModel : projectStatusListViewModel.getProjectStatusViewModels()) {
            if(projectStatusViewModel.getStatusId() == projectViewModel.statusIDProperty().getValue()) {
                statusChoiceBox.getSelectionModel().select(projectStatusViewModel);
            }
        }
        statusChoiceBox.setDisable(false);
    }


}