package view.sharedviewcomponents.popups.columndetails;

import domain.entities.column.ObservableColumn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;

public class ColumnDetailsWindowPresenter implements Initializable {

    // JavaFX injected field variables
    @FXML
    private TextField titleTextField;
    @FXML
    private Label titleErrorLbl;
    @FXML
    private CheckBox finalColumnCheckBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    // Variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private String noTitleError = "A title must be provided";
    private boolean validTitle;

    private UUID parentProjectUUID;
    private ObservableColumn columnViewModel;

    // Constructors


    // Getters and Setters
    public void setParentProjectUUID(UUID parentProjectUUID) {
        this.parentProjectUUID = parentProjectUUID;
    }

    public void setColumnViewModel(ObservableColumn columnViewModel) {
        this.columnViewModel = columnViewModel;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validTitle = true;
        titleErrorLbl.setText(noTitleError);
        titleErrorLbl.setVisible(false);
        titleErrorLbl.setDisable(true);
        establishTitleTextFieldValidation();
    }


    // UI event methods
    @FXML private void saveDetailsChange() throws IOException, SQLException {
        if(validTitle) {
            if(columnViewModel == null) {
                kanbanBoDataService.createColumn(parentProjectUUID, titleTextField.getText(), finalColumnCheckBox.isSelected());
            } else {
                columnViewModel.columnTitleProperty().set(titleTextField.getText());
                columnViewModel.finalColumnProperty().set(finalColumnCheckBox.isSelected());
                // TODO figure out how to manage position...
                // columnViewModel.columnPositionProperty().set(position?);
            }
            StageUtils.hideSubStage();
        } else {
            titleErrorLbl.setVisible(true);
            titleErrorLbl.setDisable(false);
        }
    }


    @FXML private void cancelDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }


    // Other methods

    private void establishTitleTextFieldValidation() {
        titleTextField.setTextFormatter(new TextFormatter<Object>(change -> {
            change = change.getControlNewText().matches(".{0,50}") ? change : null;
            if (change != null && change.getControlNewText().length() < 1) {
                validTitle = false;
            } else {
                validTitle = true;
            }
            return change;
        }));
    }
}
