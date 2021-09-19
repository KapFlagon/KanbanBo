package view.screens.mainscreen.subviews.workspace.subviews.cardcontainer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import domain.activerecords.ColumnCardActiveRecord;
import persistence.tables.card.CardTable;
import utils.StageUtils;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowPresenter;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowView;

import java.net.URL;
import java.util.ResourceBundle;

public class CardContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private VBox cardContainer;
    @FXML
    private Label cardTitleLbl;    //TODO examine bug where card title is not being updated after saving a change.
    @FXML
    private Button editCardDetailsBtn;
    @FXML
    private Button saveCardDetailsBtn;
    @FXML
    private TextField cardTitleTextField;
    @FXML
    private TextArea cardDescriptionTextArea;

    // Other variables
    private ColumnCardActiveRecord<CardTable> columnCardActiveRecord;
    private CardDetailsWindowView cardDetailsWindowView;
    private CardDetailsWindowPresenter cardDetailsWindowPresenter;
    private VBox parentVBox;

    // Constructors

    // Getters & Setters
    public ColumnCardActiveRecord<CardTable> getColumnCardActiveRecord() {
        return columnCardActiveRecord;
    }
    public void setColumnCardActiveRecord(ColumnCardActiveRecord<CardTable> columnCardActiveRecord) {
        this.columnCardActiveRecord = columnCardActiveRecord;
        this.cardTitleLbl.textProperty().bind(columnCardActiveRecord.cardTitleProperty());
        this.cardTitleTextField.textProperty().bind(columnCardActiveRecord.cardTitleProperty());
        this.cardDescriptionTextArea.textProperty().bind(columnCardActiveRecord.cardDescriptionProperty());
    }

    public void setParentVBox(VBox parentVBox) {
        this.parentVBox = parentVBox;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initDragAndDropMethods();
    }

    private void initDragAndDropMethods() {
        // https://stackoverflow.com/questions/22424082/drag-and-drop-vbox-element-with-show-snapshot-in-javafx
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/drag-drop.htm#CHDJFJDH
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/paper-doll.htm#CBHFHJID
        // From James_D
        // https://stackoverflow.com/questions/22820160/accessing-properties-of-custom-object-from-javafx-draganddrop-clipboard
        // https://community.oracle.com/tech/developers/discussion/2513382/drag-and-drop-objects-with-javafx-properties
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
                clipboardContent.putString("Card");
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
    @FXML private void editCardDetails() {
        // TODO Reimplement this
        System.out.println("Renaming Column");
        initCardDetailsWindow();
        showCardDetailsWindow();
    }

    @FXML private void saveCardDetails() {
        // TODO Implement this
    }

    @FXML private void addRelatedItem() {
        // TODO Implement this
    }

    @FXML private void editRelatedItem() {
        // TODO Implement this
    }

    @FXML private void removeRelatedItem() {
        // TODO Implement this
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




}