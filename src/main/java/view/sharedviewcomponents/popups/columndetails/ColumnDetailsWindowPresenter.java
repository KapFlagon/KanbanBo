package view.sharedviewcomponents.popups.columndetails;

import persistence.dto.column.ColumnDTO;
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
import java.text.ParseException;
import java.time.ZonedDateTime;
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
        this.titleTextField.textProperty().set(columnViewModel.columnTitleProperty().getValue());
        this.finalColumnCheckBox.selectedProperty().set(columnViewModel.isFinalColumn());
        this.parentProjectUUID = columnViewModel.getParentProjectUUID();
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
    @FXML private void saveDetailsChange() throws IOException, SQLException, ParseException {
        if(validTitle) {
            ColumnDTO columnDTO = new ColumnDTO();
            columnDTO.setParentProjectUUID(parentProjectUUID);
            columnDTO.setTitle(titleTextField.getText());
            columnDTO.setFinalColumn(finalColumnCheckBox.isSelected());
            if(columnViewModel == null) {
                kanbanBoDataService.createColumn(columnDTO);
            } else {
                columnDTO.setUuid(columnViewModel.getColumnUUID());
                columnDTO.setCreatedOnTimeStamp(ZonedDateTime.parse(columnViewModel.creationTimestampProperty().getValue()));
                columnDTO.setLastChangedOnTimeStamp(ZonedDateTime.parse(columnViewModel.lastChangedTimestampProperty().getValue()));
                kanbanBoDataService.updateColumn(columnDTO, columnViewModel);
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
