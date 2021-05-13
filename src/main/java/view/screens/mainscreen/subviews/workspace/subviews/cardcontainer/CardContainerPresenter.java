package view.screens.mainscreen.subviews.workspace.subviews.cardcontainer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.activerecords.ColumnCardActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.card.CardModel;
import utils.StageUtils;
import view.sharedcomponents.popups.carddetails.CardDetailsWindowPresenter;
import view.sharedcomponents.popups.carddetails.CardDetailsWindowView;
import view.sharedcomponents.popups.columndetails.ColumnDetailsWindowPresenter;
import view.sharedcomponents.popups.columndetails.ColumnDetailsWindowView;

import java.net.URL;
import java.util.ResourceBundle;

public class CardContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label cardTitle;    //TODO examine bug where card title is not being updated after saving a change.
    @FXML
    private TextArea cardDescription;

    // Other variables
    private ColumnCardActiveRecord<CardModel> columnCardActiveRecord;
    private CardDetailsWindowView cardDetailsWindowView;
    private CardDetailsWindowPresenter cardDetailsWindowPresenter;

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

    public ColumnCardActiveRecord<CardModel> getColumnCardActiveRecord() {
        return columnCardActiveRecord;
    }
    public void setColumnCardActiveRecord(ColumnCardActiveRecord<CardModel> columnCardActiveRecord) {
        this.columnCardActiveRecord = columnCardActiveRecord;
        this.cardTitle.setText(columnCardActiveRecord.getCardTitle());
        this.cardDescription.setText(columnCardActiveRecord.getCardDescription());
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initCardDetailsWindow() {
        cardDetailsWindowView = new CardDetailsWindowView();
        cardDetailsWindowPresenter = (CardDetailsWindowPresenter) cardDetailsWindowView.getPresenter();
        cardDetailsWindowPresenter.setColumnCardActiveRecord(columnCardActiveRecord);
    }

    // UI event methods
    public void editCard() {
        System.out.println("Renaming Column");
        initCardDetailsWindow();
        showCardDetailsWindow();
    }

    // Other methods
    private void showCardDetailsWindow() {
        StageUtils.createChildStage("Enter Column Details", cardDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ColumnCardActiveRecord tempColumnCardActiveRecord = cardDetailsWindowPresenter.getColumnCardActiveRecord();
        StageUtils.closeSubStage();
    }

}