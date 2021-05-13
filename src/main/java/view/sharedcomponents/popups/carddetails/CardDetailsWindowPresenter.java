package view.sharedcomponents.popups.carddetails;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import model.activerecords.ColumnCardActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.card.CardModel;
import model.domainobjects.column.ColumnModel;
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
    private Label titleErrorLbl;
    @FXML
    private TextArea descriptionText;

    // Other variables
    private String noTitleError = "A title must be provided";
    private boolean validTitle;
    private ProjectColumnActiveRecord<ColumnModel> parentColumnActiveRecord;
    private ColumnCardActiveRecord<CardModel> columnCardActiveRecord;

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
        this.titleTextField.setText(columnCardActiveRecord.getCardTitle());
        this.descriptionText.setText(columnCardActiveRecord.getCardDescription());
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validTitle = true;
        titleErrorLbl.setText(noTitleError);
        titleErrorLbl.setVisible(false);
        titleErrorLbl.setDisable(true);
        establishTitleTextFieldValidation();
    }

    // UI event methods
    public void saveDetailsChange() throws IOException, SQLException {
        if(validTitle) {
            if(columnCardActiveRecord == null) {
                columnCardActiveRecord = new ColumnCardActiveRecord<>(CardModel.class);
                // TODO make new project building the responsibility of class ProjectActiveRecord
                columnCardActiveRecord.setColumnCardModel(buildNewCardModelInstance());
            } else {
                columnCardActiveRecord.setCardTitle(titleTextField.getText());
                columnCardActiveRecord.setCardDescription(descriptionText.getText());
            }
            StageUtils.hideSubStage();
        } else {
            titleErrorLbl.setVisible(true);
            titleErrorLbl.setDisable(false);
        }
    }


    public void cancelDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }

    // Other methods
    private CardModel buildNewCardModelInstance() {
        // Create blank column instance, and populate with data.
        CardModel cardModel = new CardModel();
        cardModel.setCard_title(titleTextField.getText());
        cardModel.setCard_description_text(descriptionText.getText());
        cardModel.setParent_column_uuid(parentColumnActiveRecord.getProjectColumnModel().getColumn_uuid());
        return cardModel;
    }

    private void establishTitleTextFieldValidation() {
        titleTextField.setTextFormatter(new TextFormatter<Object> (change -> {
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