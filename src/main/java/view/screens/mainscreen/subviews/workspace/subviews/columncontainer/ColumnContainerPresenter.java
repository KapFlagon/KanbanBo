package view.screens.mainscreen.subviews.workspace.subviews.columncontainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import domain.activerecords.ColumnCardActiveRecord;
import domain.activerecords.ProjectColumnActiveRecord;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.repositories.legacy.ActiveCardListRepository;
import persistence.services.legacy.ColumnCardRepositoryService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.workspace.subviews.cardcontainer.CardContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.cardcontainer.CardContainerView;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowPresenter;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowView;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowPresenter;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ColumnContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label columnTitleLbl;
    @FXML
    private Button editColumnDetailsBtn;
    @FXML
    private Button saveColumnDetailsBtn;
    @FXML
    private TextField columnTitleTextField;
    @FXML
    private CheckBox finalColumnCheckBox;
    @FXML
    private BorderPane columnBorderPane;
    @FXML
    private VBox cardVBox;
    @FXML
    private Text cardCountTxt;


    // Other variables
    private ProjectColumnActiveRecord<ColumnTable> projectColumnActiveRecord;
    private ObservableList<ColumnCardActiveRecord> cardsList;
    private ColumnDetailsWindowView columnDetailsWindowView;
    private ColumnDetailsWindowPresenter columnDetailsWindowPresenter;
    private CardDetailsWindowView cardDetailsWindowView;
    private CardDetailsWindowPresenter cardDetailsWindowPresenter;
    private ColumnCardRepositoryService columnCardRepositoryService;
    private ActiveCardListRepository activeCardListRepository;
    private HBox parentHBox;

    // Constructors

    // Getters & Setters
    public BorderPane getColumnBorderPane() {
        return columnBorderPane;
    }

    public ProjectColumnActiveRecord<ColumnTable> getProjectColumnActiveRecord() {
        return projectColumnActiveRecord;
    }
    public void setProjectColumnActiveRecord(ProjectColumnActiveRecord<ColumnTable> projectColumnActiveRecord) throws IOException, SQLException {
        this.projectColumnActiveRecord = projectColumnActiveRecord;
        this.columnTitleLbl.setText(projectColumnActiveRecord.getColumnTitle());
        //this.columnTitleLbl.textProperty().bind(projectColumnActiveRecord.columnTitleProperty());
        customInit();
    }

    public void setParentHBox(HBox parentHBox) {
        this.parentHBox = parentHBox;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardsList = FXCollections.observableArrayList();
        initDragAndDropMethods();
    }

    public void initDragAndDropMethods() {
        // https://stackoverflow.com/questions/22424082/drag-and-drop-vbox-element-with-show-snapshot-in-javafx
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/drag-drop.htm#CHDJFJDH
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/paper-doll.htm#CBHFHJID
        // From James_D
        // https://stackoverflow.com/questions/22820160/accessing-properties-of-custom-object-from-javafx-draganddrop-clipboard
        // https://community.oracle.com/tech/developers/discussion/2513382/drag-and-drop-objects-with-javafx-properties
        initDragDetection();
        initDragDone();
        initDragCardOver();
        initCardDragEntered();
        initCardDragExited();
        initCardDragDropped();
    }

    private void initDragDetection() {
        columnBorderPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Column drag has started");
                Dragboard dragboard = columnBorderPane.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent clipboardContent = new ClipboardContent();
                ImageView imageView = new ImageView(columnBorderPane.snapshot(null, null));
                clipboardContent.putImage(imageView.getImage());
                //clipboardContent.putString("test data");
                dragboard.setContent(clipboardContent);
                //ImageView imageView = new ImageView(columnContainer.snapshot(null, null));
                //dragboard.setDragView(imageView.getImage());
                event.consume();
            }
        });
    }

    private void initDragDone() {
        columnBorderPane.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // Remove data from this container, don't write, and delete the container.
                if (event.getTransferMode() == TransferMode.MOVE) {
                    System.out.println("Column drag is done");
                }
                event.consume();
            }
        });
    }

    private void initDragCardOver() {
        cardVBox.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("Card dragged over column VBox");
                event.acceptTransferModes(TransferMode.MOVE);
                event.consume();
            }
        });
    }

    public void initCardDragEntered() {
        cardVBox.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("Card drag entered column VBox");
                cardVBox.setStyle("-fx-background-color: blue");
                event.consume();
            }
        });
    }

    public void initCardDragExited() {
        cardVBox.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("Card drag exited column VBox");
                cardVBox.setStyle("-fx-background-color: white");
                event.consume();
            }
        });
    }

    public void initCardDragDropped() {
        cardVBox.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("Card dropped column VBox");
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    public void customInit() throws IOException, SQLException {
        columnCardRepositoryService = new ColumnCardRepositoryService(projectColumnActiveRecord);
        columnTitleLbl.textProperty().bind(projectColumnActiveRecord.columnTitleProperty());
        columnTitleTextField.textProperty().bind(projectColumnActiveRecord.columnTitleProperty());
        finalColumnCheckBox.selectedProperty().bind(projectColumnActiveRecord.finalColumnProperty());
        cardsList = columnCardRepositoryService.getCardsList();
        for (ColumnCardActiveRecord<CardTable> columnCardActiveRecord : cardsList) {
            CardContainerView ccv = new CardContainerView();
            CardContainerPresenter ccp = (CardContainerPresenter) ccv.getPresenter();
            ccp.setColumnCardActiveRecord(columnCardActiveRecord);
            ccp.setParentVBox(cardVBox);
            cardVBox.getChildren().add(ccv.getView());
        }
        activeCardListRepository = columnCardRepositoryService.getActiveCardListRepository();
    }

    private void initColumnDetailsWindow() {
        columnDetailsWindowView = new ColumnDetailsWindowView();
        columnDetailsWindowPresenter = (ColumnDetailsWindowPresenter) columnDetailsWindowView.getPresenter();
        columnDetailsWindowPresenter.setProjectColumnActiveRecord(projectColumnActiveRecord);
    }

    private void initCardDetailsWindow() {
        cardDetailsWindowView = new CardDetailsWindowView();
        cardDetailsWindowPresenter = (CardDetailsWindowPresenter) cardDetailsWindowView.getPresenter();
        cardDetailsWindowPresenter.setParentColumnActiveRecord(projectColumnActiveRecord);
    }


    // UI event methods
    @FXML private void editColumnDetails() {
        // TODO update this to include a simple boolean property and update the state of the buttons etc.
        System.out.println("Renaming Column");
        initColumnDetailsWindow();
        showColumnDetailsWindow();
    }

    @FXML private void saveColumnDetails() {
        // TODO Implement this
    }

    @FXML private void createCard() throws IOException, SQLException {
        System.out.println("Adding card");
        initCardDetailsWindow();
        StageUtils.createChildStage("Enter Card Details", cardDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ColumnCardActiveRecord<CardTable> ccar = cardDetailsWindowPresenter.getColumnCardActiveRecord();
        if(ccar != null) {
            ccar.setParentColumnActiveRecord(projectColumnActiveRecord);
            //ccar.getColumnCardModel().setParent_column(projectColumnActiveRecord.getProjectColumnModel().getColumn_uuid());
            //ccar.getColumnCardModel().setCard_position(cardsList.size() + 1);
            cardsList.add(ccar);
            CardContainerView ccv = new CardContainerView();
            CardContainerPresenter ccp = (CardContainerPresenter) ccv.getPresenter();
            // use ccp to set data in the column data.
            ccp.setColumnCardActiveRecord(ccar);
            ccp.setParentVBox(cardVBox);
            cardVBox.getChildren().add(ccv.getView());
        }
    }

    @FXML private void createCardFromTemplate() {
        // TODO Implement this
    }

    // Other methods
    private void showColumnDetailsWindow() {
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ProjectColumnActiveRecord tempProjectColumnActiveRecord = columnDetailsWindowPresenter.getProjectColumnActiveRecord();
        StageUtils.closeSubStage();
    }

    private void addCardPreview(BorderPane draggedBorderPane) {
        ImageView imageView = new ImageView(draggedBorderPane.snapshot(null, null));
        imageView.setManaged(false);
        imageView.setMouseTransparent(true);
        cardVBox.getChildren().add(imageView);
        cardVBox.setUserData(imageView);
        cardVBox.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.relocate(event.getX(), event.getY());
            }
        });
    }

    private void removeCardPreview() {
        cardVBox.setOnMouseDragged(null);
        cardVBox.getChildren().remove(cardVBox.getUserData());
        cardVBox.setUserData(null);
    }

}