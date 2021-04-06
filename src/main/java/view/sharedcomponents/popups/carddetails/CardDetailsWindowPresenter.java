package view.sharedcomponents.popups.carddetails;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.activerecords.ColumnCardActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.column.ActiveProjectColumnModel;
import utils.StageUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardDetailsWindowPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionText;

    // Other variables
    private ProjectColumnActiveRecord parentColumnActiveRecord;
    private ColumnCardActiveRecord columnCardActiveRecord;

    // Constructors

    // Getters & Setters
    public ProjectColumnActiveRecord getParentColumnActiveRecord() {
        return parentColumnActiveRecord;
    }
    public void setParentColumnActiveRecord(ProjectColumnActiveRecord parentColumnActiveRecord) {
        this.parentColumnActiveRecord = parentColumnActiveRecord;
    }

    public ColumnCardActiveRecord getColumnCardActiveRecord() {
        return columnCardActiveRecord;
    }
    public void setColumnCardActiveRecord(ColumnCardActiveRecord columnCardActiveRecord) {
        this.columnCardActiveRecord = columnCardActiveRecord;

    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // UI event methods
    public void saveDetailsChange() throws IOException, SQLException {
        /*
        if(projectColumnActiveRecord == null) {
            projectColumnActiveRecord = new ProjectColumnActiveRecord(ActiveProjectColumnModel.class);
            // TODO make new project building the responsibility of class ProjectActiveRecord
            projectColumnActiveRecord.setProjectColumnModel(buildNewColumnModelInstance());
        } else {
            projectColumnActiveRecord.setColumnTitle(titleTextField.getText());
        }
        StageUtils.hideSubStage();
         */
    }


    public void cancelDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }

    // Other methods
    /*
    private ActiveProjectColumnModel buildNewColumnModelInstance() {
        // Create blank column instance, and populate with data.
        ActiveProjectColumnModel activeProjectColumnModel = new ActiveProjectColumnModel();
        activeProjectColumnModel.setColumn_title(titleTextField.getText());
        activeProjectColumnModel.setParent_project_uuid(parentProject.getProjectUUID());
        return activeProjectColumnModel;
    }
     */

}