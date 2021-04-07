package view.sharedcomponents.popups.carddetails;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.activerecords.ColumnCardActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.card.ActiveColumnCardModel;
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
    private ProjectColumnActiveRecord<ActiveProjectColumnModel> parentColumnActiveRecord;
    private ColumnCardActiveRecord<ActiveColumnCardModel> columnCardActiveRecord;

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

    }

    // UI event methods
    public void saveDetailsChange() throws IOException, SQLException {
        if(columnCardActiveRecord == null) {
            columnCardActiveRecord = new ColumnCardActiveRecord<>(ActiveColumnCardModel.class);
            // TODO make new project building the responsibility of class ProjectActiveRecord
            columnCardActiveRecord.setColumnCardModel(buildNewCardModelInstance());
        } else {
            columnCardActiveRecord.setCardTitle(titleTextField.getText());
            columnCardActiveRecord.setCardDescription(descriptionText.getText());
        }
        StageUtils.hideSubStage();
    }


    public void cancelDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }

    // Other methods
    private ActiveColumnCardModel buildNewCardModelInstance() {
        // Create blank column instance, and populate with data.
        ActiveColumnCardModel activeColumnCardModel = new ActiveColumnCardModel();
        activeColumnCardModel.setCard_title(titleTextField.getText());
        activeColumnCardModel.setCard_description_text(descriptionText.getText());
        activeColumnCardModel.setParent_column(parentColumnActiveRecord.getProjectColumnModel().getColumn_uuid());
        return activeColumnCardModel;
    }

}