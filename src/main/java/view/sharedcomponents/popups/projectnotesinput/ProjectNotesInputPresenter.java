package view.sharedcomponents.popups.projectnotesinput;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import utils.StageUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectNotesInputPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private TextArea projectNotesTextArea;

    // Other variables
    private String projectNotesContent;

    // Constructors

    // Getters & Setters
    public String getProjectNotesContent() {
        return projectNotesContent;
    }
    public void setProjectNotesContent(String projectNotesContent) {
        this.projectNotesContent = projectNotesContent;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // UI event methods
    public void updateProjectNotes() {
        System.out.println("Updating project notes");
        // Write to DB
        setProjectNotesContent(projectNotesTextArea.getText());
        StageUtils.hideSubStage();
    }

    public void cancel() {
        System.out.println("Project note update cancelled");
        StageUtils.hideSubStage();
    }

    // Other methods


}