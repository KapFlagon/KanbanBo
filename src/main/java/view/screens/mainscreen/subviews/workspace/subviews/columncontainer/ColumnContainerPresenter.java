package view.screens.mainscreen.subviews.workspace.subviews.columncontainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.activerecords.ColumnCardActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.card.CardModel;
import model.domainobjects.column.ColumnModel;
import model.repositories.ActiveCardListRepository;
import model.repositories.services.ColumnCardRepositoryService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.workspace.subviews.cardcontainer.CardContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.cardcontainer.CardContainerView;
import view.sharedcomponents.popups.carddetails.CardDetailsWindowPresenter;
import view.sharedcomponents.popups.carddetails.CardDetailsWindowView;
import view.sharedcomponents.popups.columndetails.ColumnDetailsWindowPresenter;
import view.sharedcomponents.popups.columndetails.ColumnDetailsWindowView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ColumnContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private BorderPane columnContainer;
    @FXML
    private VBox cardVBox;
    @FXML
    private Label columnTitleLbl;
    @FXML
    private Text cardCountTxt;
    @FXML
    private Button addCardBtn;


    // Other variables
    private ProjectColumnActiveRecord<ColumnModel> projectColumnActiveRecord;
    private ObservableList<ColumnCardActiveRecord> cardsList;
    private ColumnDetailsWindowView columnDetailsWindowView;
    private ColumnDetailsWindowPresenter columnDetailsWindowPresenter;
    private CardDetailsWindowView cardDetailsWindowView;
    private CardDetailsWindowPresenter cardDetailsWindowPresenter;
    private ColumnCardRepositoryService columnCardRepositoryService;
    private ActiveCardListRepository activeCardListRepository;

    // Constructors

    // Getters & Setters

    public ProjectColumnActiveRecord<ColumnModel> getProjectColumnActiveRecord() {
        return projectColumnActiveRecord;
    }
    public void setProjectColumnActiveRecord(ProjectColumnActiveRecord<ColumnModel> projectColumnActiveRecord) throws IOException, SQLException {
        this.projectColumnActiveRecord = projectColumnActiveRecord;
        this.columnTitleLbl.setText(projectColumnActiveRecord.getColumnTitle());
        //this.columnTitleLbl.textProperty().bind(projectColumnActiveRecord.columnTitleProperty());
        customInit();
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
        initDragDetection();
        initDragDone();
        initDragCardOver();
        initCardDragEntered();
        initCardDragExited();
        initCardDragDropped();
    }

    private void initDragDetection() {
        columnContainer.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Column drag has started");
                Dragboard dragboard = columnContainer.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString("test data");
                dragboard.setContent(clipboardContent);
                ImageView imageView = new ImageView(columnContainer.snapshot(null, null));
                dragboard.setDragView(imageView.getImage());
                event.consume();
            }
        });
    }

    private void initDragDone() {
        columnContainer.setOnDragDone(new EventHandler<DragEvent>() {
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
        columnTitleLbl.setText(projectColumnActiveRecord.getColumnTitle());
        columnCardRepositoryService = new ColumnCardRepositoryService(projectColumnActiveRecord);
        cardsList = columnCardRepositoryService.getCardsList();
        for (ColumnCardActiveRecord<CardModel> columnCardActiveRecord : cardsList) {
            CardContainerView ccv = new CardContainerView();
            CardContainerPresenter ccp = (CardContainerPresenter) ccv.getPresenter();
            ccp.setColumnCardActiveRecord(columnCardActiveRecord);
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
    public void addCard() throws IOException, SQLException {
        System.out.println("Adding card");
        initCardDetailsWindow();
        StageUtils.createChildStage("Enter Card Details", cardDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ColumnCardActiveRecord<CardModel> ccar = cardDetailsWindowPresenter.getColumnCardActiveRecord();
        if(ccar != null) {
            ccar.setParentColumnActiveRecord(projectColumnActiveRecord);
            //ccar.getColumnCardModel().setParent_column(projectColumnActiveRecord.getProjectColumnModel().getColumn_uuid());
            //ccar.getColumnCardModel().setCard_position(cardsList.size() + 1);
            cardsList.add(ccar);
            CardContainerView ccv = new CardContainerView();
            CardContainerPresenter ccp = (CardContainerPresenter) ccv.getPresenter();
            // use ccp to set data in the column data.
            ccp.setColumnCardActiveRecord(ccar);
            cardVBox.getChildren().add(ccv.getView());
        }
    }

    public void renameColumn() {
        System.out.println("Renaming Column");
        initColumnDetailsWindow();
        showColumnDetailsWindow();
    }

    // Other methods
    private void showColumnDetailsWindow() {
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ProjectColumnActiveRecord tempProjectColumnActiveRecord = columnDetailsWindowPresenter.getProjectColumnActiveRecord();
        StageUtils.closeSubStage();
    }

}