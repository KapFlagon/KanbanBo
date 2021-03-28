package view.sharedcomponents.popups.projectdetails;

import com.j256.ormlite.dao.Dao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.activerecords.ProjectActiveRecord;
import model.domainobjects.project.ActiveProjectModel;
import utils.StageUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

public class ProjectDetailsWindowPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private TextField projectTitleTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    // Variables
    private ActiveProjectModel selectedActiveProjectModel;
    private ProjectActiveRecord projectActiveRecord;

    // Constructors


    // Getters and Setters
    public TextField getProjectTitleTextField() {
        return projectTitleTextField;
    }
    public void setProjectTitleTextField(TextField projectTitleTextField) {
        this.projectTitleTextField = projectTitleTextField;
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

    public ActiveProjectModel getSelectedActiveProjectModel() {
        return selectedActiveProjectModel;
    }
    public void setSelectedActiveProjectModel(ActiveProjectModel selectedActiveProjectModel) {
        this.selectedActiveProjectModel = selectedActiveProjectModel;
    }

    public ProjectActiveRecord getProjectActiveRecord() {
        return projectActiveRecord;
    }
    public void setProjectActiveRecord(ProjectActiveRecord projectActiveRecord) {
        this.projectActiveRecord = projectActiveRecord;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    // Other methods
    public void saveProjectDetailsChange() throws SQLException, IOException {
        /*
        // Establish connection
        JdbcConnectionSource connectionSource = DatabaseUtils.getConnectionSource();

        // Build Data Access Objects (DAOs) for the persisted class.
        Dao<ActiveProjectModel, UUID> activeProjectModelDao = DaoManager.createDao(connectionSource, ActiveProjectModel.class);

        // Check if current active project instance is blank/new/null.
        if (getSelectedActiveProjectModel() == null) {
            createProject(activeProjectModelDao);
        } else {
            updateProject(activeProjectModelDao);
        }
        // Close the connection for the data source.
        connectionSource.close();

         */

        if(projectActiveRecord == null) {
            projectActiveRecord = new ProjectActiveRecord(ActiveProjectModel.class);
            projectActiveRecord.setProjectModel(buildNewProjectModelInstance());
        } else {
            projectActiveRecord.setProjectTitle(getProjectTitleTextField().getText());
        }
        StageUtils.hideSubStage();
    }

    public void createProject(Dao<ActiveProjectModel, UUID> dao) throws SQLException {
        selectedActiveProjectModel = buildNewProjectModelInstance();
        // Use the DAO to create the table entry.
        dao.create(selectedActiveProjectModel);
    }

    public void updateProject(Dao<ActiveProjectModel, UUID> dao) throws SQLException {
        updateSelectedActiveProjectModel();
        dao.update(selectedActiveProjectModel);
    }


    public void cancelProjectDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }


    private ActiveProjectModel buildNewProjectModelInstance() {
        // Create blank project instance, and populate with data.
        ActiveProjectModel newActiveProjectModel = new ActiveProjectModel();
        newActiveProjectModel.setProject_title(getProjectTitleTextField().getText());
        newActiveProjectModel.setCreation_timestamp(new Date());
        newActiveProjectModel.setLast_changed_timestamp(new Date());
        return newActiveProjectModel;
    }

    private void updateSelectedActiveProjectModel() {
        selectedActiveProjectModel.setProject_title(getProjectTitleTextField().getText());
        selectedActiveProjectModel.setLast_changed_timestamp(new Date());
    }

}
