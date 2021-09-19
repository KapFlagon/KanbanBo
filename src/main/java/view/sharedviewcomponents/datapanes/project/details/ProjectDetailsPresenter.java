package view.sharedviewcomponents.datapanes.project.details;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import domain.activerecords.project.ProjectActiveRecord;
import domain.viewmodels.project.ProjectDetailsViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectDetailsPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Button editProjectDetailsBtn;
    @FXML
    private Button cancelChangeBtn;
    @FXML
    private Button saveChangeBtn;
    @FXML
    private Text unsavedChangesTxt;
    @FXML
    private TextField projectTitleTextField;
    @FXML
    private TextField projectStatusTextField;
    @FXML
    private ChoiceBox projectStatusChoiceBox;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private TextField projectCreationDateTextField;
    @FXML
    private TextField projectLastChangedDateTextField;


    // Other variables
    //private ProjectDetailsViewModel projectDetailsViewModel;
    private ProjectActiveRecord projectActiveRecord;
    private ProjectDetailsViewModel projectDetailsViewModel;
    private SimpleBooleanProperty changesPending;

    // Constructors

    // Getters & Setters
    public void setProjectActiveRecord(ProjectActiveRecord projectActiveRecord) {
        this.projectActiveRecord = projectActiveRecord;
        initializeTexts();
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeStateProperties();
        initializeListeners();
        disableEditing();
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
        projectDetailsViewModel.bindToProjectTitleProperty(projectTitleTextField.textProperty());
        projectDetailsViewModel.bindToProjectDescriptionProperty(projectDescriptionTextArea.textProperty());
        projectDetailsViewModel.bindToProjectCreationTimestampProperty(projectCreationDateTextField.textProperty());
        projectDetailsViewModel.bindToProjectLastChangedTimestampProperty(projectLastChangedDateTextField.textProperty());
        projectDetailsViewModel.bindToProjectStatusTextProperty(projectStatusTextField.textProperty());
        projectStatusChoiceBox.getItems().addAll(projectDetailsViewModel.getProjectStatusListViewModel());
    }


    // UI event methods
    @FXML private void editProjectDetails() {
        enableEditing();
    }
    @FXML private void cancelChanges() {
        if(changesPending.getValue()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Pending changes have not been saved!");
            alert.setContentText("Are you sure?");
        }
        changesPending.set(false);
        disableEditing();
    }
    @FXML private void saveChanges() {
        // TODO Save the changes somehow, via a service maybe?

        changesPending.set(false);
        disableEditing();
    }


    // Other methods
    private void disableEditing() {
        unsavedChangesTxt.setVisible(false);
        projectTitleTextField.setEditable(false);
        projectStatusTextField.setVisible(true);
        projectStatusTextField.setDisable(false);
        projectStatusChoiceBox.setVisible(false);
        projectStatusChoiceBox.setDisable(true);
        projectDescriptionTextArea.setEditable(false);
        editProjectDetailsBtn.setVisible(true);
        editProjectDetailsBtn.setDisable(false);
        cancelChangeBtn.setVisible(false);
        cancelChangeBtn.setDisable(true);
        saveChangeBtn.setVisible(false);
        saveChangeBtn.setDisable(true);
    }

    private void enableEditing() {
        projectTitleTextField.setEditable(true);
        projectStatusTextField.setVisible(false);
        projectStatusTextField.setDisable(true);
        projectStatusChoiceBox.setVisible(true);
        projectStatusChoiceBox.setDisable(false);
        projectDescriptionTextArea.setEditable(true);
        editProjectDetailsBtn.setVisible(false);
        editProjectDetailsBtn.setDisable(true);
        cancelChangeBtn.setVisible(true);
        cancelChangeBtn.setDisable(false);
        saveChangeBtn.setVisible(true);
        saveChangeBtn.setDisable(false);
    }

}