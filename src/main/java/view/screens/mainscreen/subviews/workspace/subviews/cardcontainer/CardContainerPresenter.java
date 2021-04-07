package view.screens.mainscreen.subviews.workspace.subviews.cardcontainer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.activerecords.ColumnCardActiveRecord;
import model.domainobjects.card.ActiveColumnCardModel;

import java.net.URL;
import java.util.ResourceBundle;

public class CardContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label cardTitle;
    @FXML
    private TextArea cardDescription;

    // Other variables
    private ColumnCardActiveRecord<ActiveColumnCardModel> columnCardActiveRecord;

    // Constructors

    // Getters & Setters
    public Label getCardTitle() {
        return cardTitle;
    }
    public void setCardTitle(Label cardTitle) {
        this.cardTitle = cardTitle;
    }

    public TextArea getCardDescription() {
        return cardDescription;
    }
    public void setCardDescription(TextArea cardDescription) {
        this.cardDescription = cardDescription;
    }

    public ColumnCardActiveRecord<ActiveColumnCardModel> getColumnCardActiveRecord() {
        return columnCardActiveRecord;
    }
    public void setColumnCardActiveRecord(ColumnCardActiveRecord<ActiveColumnCardModel> columnCardActiveRecord) {
        this.columnCardActiveRecord = columnCardActiveRecord;
        this.cardTitle.setText(columnCardActiveRecord.getCardTitle());
        this.cardDescription.setText(columnCardActiveRecord.getCardDescription());
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // UI event methods

    // Other methods


}