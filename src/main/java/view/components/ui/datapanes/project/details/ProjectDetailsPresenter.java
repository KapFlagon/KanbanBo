package view.components.ui.datapanes.project.details;

import domain.entities.project.ObservableWorkspaceProject;
import domain.viewmodels.project.ProjectStatusListViewModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import domain.viewmodels.project.ProjectDetailsViewModel;
import persistence.services.KanbanBoDataService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectDetailsPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Text unsavedChangesTxt;
    @FXML
    private TextField projectTitleTextField;
    @FXML
    private Label titleValidationLbl;
    @FXML
    private TextField projectStatusTextField;
    @FXML
    private ChoiceBox projectStatusChoiceBox;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private DatePicker dueOnDatePicker;
    @FXML
    private Label dueOnDateValidationLbl;
    @FXML
    private TextField projectCreationDateTextField;
    @FXML
    private TextField projectLastChangedDateTextField;


    // Other variables
    //private ProjectDetailsViewModel projectDetailsViewModel;
    private KanbanBoDataService kanbanBoDataService;
    private ObservableWorkspaceProject observableWorkspaceProject;
    private ProjectStatusListViewModel projectStatusListViewModel;
    private ProjectDetailsViewModel projectDetailsViewModel;
    private SimpleBooleanProperty changesPending;

    // Constructors

    // Getters & Setters
    public void setObservableProject(ObservableWorkspaceProject observableWorkspaceProject) {
        this.observableWorkspaceProject = observableWorkspaceProject;
        initializeTexts();
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeStateProperties();
        initializeListeners();
    }

    private void initializeStateProperties() {
        changesPending = new SimpleBooleanProperty(false);
    }

    private void initializeListeners() {
        projectTitleTextField.textProperty().addListener((change) -> {
            changesPending.setValue(true);
        });
        projectStatusChoiceBox.selectionModelProperty().addListener((change) -> {
            changesPending.setValue(true);
        });
        projectDescriptionTextArea.textProperty().addListener((change) -> {
            changesPending.setValue(true);
        });
        changesPending.addListener((observable, oldVal, newVal) -> {
            if(newVal) {
                unsavedChangesTxt.setVisible(true);
            }
        });
    }

    private void initializeTexts() {
        projectTitleTextField.textProperty().set(observableWorkspaceProject.projectTitleProperty().getValue());
        projectDescriptionTextArea.textProperty().set(observableWorkspaceProject.projectDescriptionProperty().getValue());
        projectCreationDateTextField.textProperty().set(observableWorkspaceProject.creationTimestampProperty().getValue());
        projectLastChangedDateTextField.textProperty().set(observableWorkspaceProject.lastChangedTimestampProperty().getValue());
        projectStatusTextField.textProperty().set(observableWorkspaceProject.statusTextProperty().getValue());
        projectStatusChoiceBox.getItems().addAll(projectDetailsViewModel.getProjectStatusListViewModel());
    }


    // UI event methods
    @FXML private void saveChanges() {
        // TODO Save the changes somehow, via a service maybe?
        //kanbanBoDataService.updateProject();
    }

    @FXML private void cancelChanges() {
        if(changesPending.getValue()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to discard unsaved changed Project Details data?");
            alert.setTitle("Discard unsaved changes?");
            alert.setHeaderText("Pending changes have not been saved!");
            alert.showAndWait().filter(response -> response == ButtonType.OK)
                    .ifPresent((response) ->{

                    });
        }
    }




    // Other methods

}