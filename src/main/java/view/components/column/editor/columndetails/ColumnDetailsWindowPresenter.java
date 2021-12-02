package view.components.column.editor.columndetails;

import persistence.dto.column.ColumnDTO;
import domain.entities.column.ObservableColumn;
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
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.UUID;

public class ColumnDetailsWindowPresenter extends DetailsWindowPresenter implements Initializable {

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

            ColumnDTO.Builder columnDTOBuilder = ColumnDTO.Builder.newInstance(parentProjectUUID.toString())
                        .title(titleTextField.getText())
                        .finalColumn(finalColumnCheckBox.isSelected());
            if(editorDataMode == EditorDataMode.CREATION) {
                columnDTOBuilder.uuid(UUID.randomUUID().toString())
                        .createdOnTimeStamp(ZonedDateTime.now().toString())
                        .lastChangedOnTimeStamp(ZonedDateTime.now().toString());
                ColumnDTO columnDTO = new ColumnDTO(columnDTOBuilder);
                kanbanBoDataService.createColumn(columnDTO);
            } else if (editorDataMode == EditorDataMode.EDITING){
                columnDTOBuilder.uuid(columnViewModel.getColumnUUID().toString())
                        .createdOnTimeStamp(columnViewModel.creationTimestampProperty().getValue())
                        .lastChangedOnTimeStamp(ZonedDateTime.now().toString());
                ColumnDTO columnDTO = new ColumnDTO(columnDTOBuilder);
                kanbanBoDataService.updateColumn(columnDTO, columnViewModel);
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


    protected void prepareViewForCreation() {

    }

    protected void prepareViewForDisplay() {

    }

    protected void prepareViewForEditing() {

    }
}
