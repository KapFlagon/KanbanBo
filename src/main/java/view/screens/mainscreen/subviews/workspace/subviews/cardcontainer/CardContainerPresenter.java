package view.screens.mainscreen.subviews.workspace.subviews.cardcontainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
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
    private BorderPane cardContainer;
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
        initDragAndDropMethods();
    }

    private void initDragAndDropMethods() {
        // https://stackoverflow.com/questions/22424082/drag-and-drop-vbox-element-with-show-snapshot-in-javafx
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/drag-drop.htm#CHDJFJDH
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/paper-doll.htm#CBHFHJID
        //initDragDetection();
        initDragDone();
    }

    private void initDragDetection() {
        cardContainer.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Card drag has started");
                Dragboard dragboard = cardContainer.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString("test data");
                dragboard.setContent(clipboardContent);
                ImageView imageView = new ImageView(cardContainer.snapshot(null, null));
                dragboard.setDragView(imageView.getImage());
                event.consume();
            }
        });
    }

    private void initDragDone() {
        cardContainer.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // Remove data from this container, don't write, and delete the container.
                if (event.getTransferMode() == TransferMode.MOVE) {
                    System.out.println("Card drag is done");
                }
                event.consume();
            }
        });
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

    public void dragDetected() {
        System.out.println("Card drag has started");
        Dragboard dragboard = cardContainer.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("test data");
        dragboard.setContent(clipboardContent);
        ImageView imageView = new ImageView(cardContainer.snapshot(null, null));
        dragboard.setDragView(imageView.getImage());
    }

    public void dragDone() {
        System.out.println("drag done on card " + cardTitle.getText());
    }

    public void dragDropped() {
        System.out.println("drag dropped on card " + cardTitle.getText());
    }

    public void dragEntered() {
        System.out.println("drag entered on card " + cardTitle.getText());
    }

    public void dragExited() {
        System.out.println("drag exited on card " + cardTitle.getText());
    }

    public void dragOver() {
        System.out.println("drag over on card " + cardTitle.getText());
    }

    public void mouseDragEntered() {
        System.out.println("mouse Drag Entered on card " + cardTitle.getText());
    }

    public void mouseDragExited() {
        System.out.println("mouse Drag exited on card " + cardTitle.getText());
    }

    public void mouseDragOver() {
        System.out.println("mouse Drag over on card " + cardTitle.getText());
    }

    public void mouseDragReleased() {
        System.out.println("mouse Drag released on card " + cardTitle.getText());
    }

    public void mouseClicked() {
        //System.out.println("mouse clicked on card " + cardTitle.getText());
    }

    public void mouseDragged() {
        //System.out.println("mouse dragged on card " + cardTitle.getText());
        //dragboard.getDragView();
    }


}