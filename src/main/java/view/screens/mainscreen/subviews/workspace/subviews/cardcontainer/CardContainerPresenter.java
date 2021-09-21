package view.screens.mainscreen.subviews.workspace.subviews.cardcontainer;

import domain.entities.card.ObservableCard;
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
    private ObservableCard cardViewModel;
    private CardDetailsWindowView cardDetailsWindowView;
    private CardDetailsWindowPresenter cardDetailsWindowPresenter;
    private VBox parentVBox;

    // Constructors

    // Getters & Setters
    public void setCardViewModel(ObservableCard cardViewModel) {
        this.cardViewModel = cardViewModel;
        this.cardTitleLbl.textProperty().bind(cardViewModel.cardTitleProperty());
        this.cardTitleTextField.textProperty().bind(cardViewModel.cardTitleProperty());
        this.cardDescriptionTextArea.textProperty().bind(cardViewModel.cardDescriptionProperty());
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
    }


    private void initCardDetailsWindow() {
        cardDetailsWindowView = new CardDetailsWindowView();
        cardDetailsWindowPresenter = (CardDetailsWindowPresenter) cardDetailsWindowView.getPresenter();
        cardDetailsWindowPresenter.setCardViewModel(cardViewModel);
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
        StageUtils.closeSubStage();

    }

}