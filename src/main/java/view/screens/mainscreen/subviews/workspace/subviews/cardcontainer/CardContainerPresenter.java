package view.screens.mainscreen.subviews.workspace.subviews.cardcontainer;

import domain.entities.card.ObservableCard;
import javafx.beans.property.SimpleBooleanProperty;
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
import persistence.services.KanbanBoDataService;
import persistence.tables.card.CardTable;
import utils.StageUtils;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowPresenter;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowView;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private VBox cardContainer;
    @FXML
    private Label cardTitleLbl;    //TODO examine bug where card title is not being updated after saving a change.
    @FXML
    private Button editCardBtn;
    @FXML
    private Button copyCardBtn;
    @FXML
    private Button moveCardBtn;
    @FXML
    private Button deleteCardBtn;
    @FXML
    private TextField cardTitleTextField;
    @FXML
    private TextArea cardDescriptionTextArea;

    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableCard cardViewModel;
    private CardDetailsWindowView cardDetailsWindowView;
    private CardDetailsWindowPresenter cardDetailsWindowPresenter;
    private SimpleBooleanProperty forRemoval;
    private VBox parentVBox;

    // Constructors


    // Getters & Setters
    public ObservableCard getCardViewModel() {
        return cardViewModel;
    }
    public void setCardViewModel(ObservableCard cardViewModel) {
        this.cardViewModel = cardViewModel;
        this.cardTitleLbl.textProperty().bind(cardViewModel.cardTitleProperty());
        this.cardTitleTextField.textProperty().bind(cardViewModel.cardTitleProperty());
        this.cardDescriptionTextArea.textProperty().bind(cardViewModel.cardDescriptionProperty());
    }

    public SimpleBooleanProperty forRemovalProperty() {
        return forRemoval;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtonGraphics();
        forRemoval = new SimpleBooleanProperty(false);
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

    private void initButtonGraphics() {
        ImageView editCardDetailsImageView = new ImageView(getClass().getResource("/icons/edit_note/materialicons/black/res/drawable-hdpi/baseline_edit_note_black_18.png").toExternalForm());
        ImageView copyCardImageView = new ImageView(getClass().getResource("/icons/content_copy/materialicons/black/res/drawable-hdpi/baseline_content_copy_black_18.png").toExternalForm());
        ImageView moveCardImageView = new ImageView(getClass().getResource("/icons/chevron_right/materialiconsoutlined/black/res/drawable-hdpi/outline_chevron_right_black_18.png").toExternalForm());
        ImageView deleteCardImageView = new ImageView(getClass().getResource("/icons/remove_circle_outline/materialicons/black/res/drawable-hdpi/baseline_remove_circle_outline_black_18.png").toExternalForm());

        editCardBtn.setGraphic(editCardDetailsImageView);
        copyCardBtn.setGraphic(copyCardImageView);
        moveCardBtn.setGraphic(moveCardImageView);
        deleteCardBtn.setGraphic(deleteCardImageView);
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

    @FXML
    private void deleteCard() throws SQLException, IOException {
        kanbanBoDataService.deleteCard(cardViewModel);
        forRemovalProperty().set(true);
    }

    // Other methods
    private void showCardDetailsWindow() {
        StageUtils.createChildStage("Enter Column Details", cardDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        StageUtils.closeSubStage();

    }

}