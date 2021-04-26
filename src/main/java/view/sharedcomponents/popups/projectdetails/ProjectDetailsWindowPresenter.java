package view.sharedcomponents.popups.projectdetails;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.activerecords.ProjectActiveRecord;
import model.domainobjects.project.ProjectModel;
import utils.StageUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ProjectDetailsWindowPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private TextField projectTitleTextField;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    // Variables
    private ProjectModel selectedProjectModel;
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

    public ProjectModel getSelectedActiveProjectModel() {
        return selectedProjectModel;
    }
    public void setSelectedActiveProjectModel(ProjectModel selectedProjectModel) {
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

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    // Other methods
    public void saveProjectDetailsChange() throws SQLException, IOException {
        if(projectActiveRecord == null) {
            projectActiveRecord = new ProjectActiveRecord(ProjectModel.class);
            // TODO make new project building the responsibility of class ProjectActiveRecord
            projectActiveRecord.setProjectModel(buildNewProjectModelInstance());
        } else {
            projectActiveRecord.setProjectTitle(getProjectTitleTextField().getText());
            projectActiveRecord.setProjectDescription(getProjectDescriptionTextArea().getText());
        }
        StageUtils.hideSubStage();
    }

    /*
    public void createProject(Dao<ActiveProjectModel, UUID> dao) throws SQLException {
        selectedActiveProjectModel = buildNewProjectModelInstance();
        // Use the DAO to create the table entry.
        dao.create(selectedActiveProjectModel);
    }

    public void updateProject(Dao<ActiveProjectModel, UUID> dao) throws SQLException {
        updateSelectedActiveProjectModel();
        dao.update(selectedActiveProjectModel);
    }
*/

    public void cancelProjectDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }


    private ProjectModel buildNewProjectModelInstance() {
        // Create blank project instance, and populate with data.
        ProjectModel newProjectModel = new ProjectModel();
        newProjectModel.setProject_title(getProjectTitleTextField().getText());
        newProjectModel.setProject_description(getProjectDescriptionTextArea().getText());
        newProjectModel.setCreation_timestamp(new Date());
        newProjectModel.setLast_changed_timestamp(new Date());
        return newProjectModel;
    }

    /*
    private void updateSelectedActiveProjectModel() {
        selectedActiveProjectModel.setProject_title(getProjectTitleTextField().getText());
        selectedActiveProjectModel.setLast_changed_timestamp(new Date());
    }
*/
}
