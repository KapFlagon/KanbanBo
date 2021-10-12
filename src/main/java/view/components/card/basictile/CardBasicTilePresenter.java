package view.components.card.basictile;

import domain.entities.card.ObservableCard;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import view.components.card.editor.CardEditorPresenter;
import view.components.card.editor.CardEditorView;
import view.components.ui.datapanes.card.details.CardDetailsView;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardBasicTilePresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private VBox rootContainer;

    @FXML
    private Label cardTypeLbl;

    @FXML
    private Label cardTitleLbl;

    @FXML
    private MenuButton cardMenuBtn;

    @FXML
    private MenuItem cardDetailsBtn;

    @FXML
    private MenuItem copyCardBtn;

    @FXML
    private MenuItem moveCardBtn;

    @FXML
    private MenuItem deleteCardBtn;

    @FXML
    private FlowPane tagFlowPane;

    @FXML
    private Label dueDateLbl;

    @FXML
    private HBox checklistHBox;

    @FXML
    private Label completedChecklistItemsLbl;

    @FXML
    private Label allChecklistItemsLbl;

    @FXML
    private ProgressBar checklistProgressBar;

    @FXML
    private Label descriptionLbl;

    @FXML
    private HBox resourcesHBox;

    @FXML
    private Label resourcesLbl;

    @FXML
    private MenuButton resourcesMenuBtn;


    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableCard cardViewModel;
    private SimpleBooleanProperty forRemoval;

    // Constructors

    // Getters & Setters
    public void setCardViewModel(ObservableCard cardViewModel) {
        this.cardViewModel = cardViewModel;
        lateViewInitialization();
    }

    public SimpleBooleanProperty forRemovalProperty() {
        return forRemoval;
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtonGraphics();
        initDataGraphics();
        forRemoval = new SimpleBooleanProperty(false);
    }

    private void lateViewInitialization() {
        cardTitleLbl.textProperty().bind(cardViewModel.cardTitleProperty());
        tagFlowPane.setVisible(false);
        dueDateLbl.setVisible(false);
        checklistHBox.setVisible(false);
        if(cardViewModel.cardDescriptionProperty().getValue().trim().isEmpty() || cardViewModel.cardDescriptionProperty().getValue().trim().isBlank()) {
            descriptionLbl.setVisible(false);
        }
        if(cardViewModel.getResourceItems().size() == 0) {
            resourcesMenuBtn.setDisable(true);
            resourcesLbl.setText("0");
        } else{
            resourcesMenuBtn.setDisable(false);
            resourcesLbl.setText(String.valueOf(cardViewModel.getResourceItems().size()));
            for(ObservableResourceItem resourceItem : cardViewModel.getResourceItems()) {
                MenuItem resourceMenuItem = new MenuItem(resourceItem.typeTextProperty().getValue());
                resourceMenuItem.setOnAction(event -> {
                    switch(resourceItem.typeProperty().get()) {
                        // TODO Designate "run" behaviour for button click, based on resource type
                        default: System.out.println("run the thing");
                    }
                });
                resourcesMenuBtn.getItems().add(resourceMenuItem);
            }
        }
    }

    private void initButtonGraphics() {
        ImageView editCardDetailsImageView = new ImageView(getClass().getResource("/icons/edit_note/materialicons/black/res/drawable-mdpi/baseline_edit_note_black_18.png").toExternalForm());
        ImageView copyCardImageView = new ImageView(getClass().getResource("/icons/content_copy/materialicons/black/res/drawable-mdpi/baseline_content_copy_black_18.png").toExternalForm());
        ImageView moveCardImageView = new ImageView(getClass().getResource("/icons/chevron_right/materialicons/black/res/drawable-mdpi/baseline_chevron_right_black_18.png").toExternalForm());
        ImageView deleteCardImageView = new ImageView(getClass().getResource("/icons/remove_circle_outline/materialicons/black/res/drawable-mdpi/baseline_remove_circle_outline_black_18.png").toExternalForm());

        cardDetailsBtn.setGraphic(editCardDetailsImageView);
        copyCardBtn.setGraphic(copyCardImageView);
        moveCardBtn.setGraphic(moveCardImageView);
        deleteCardBtn.setGraphic(deleteCardImageView);
    }

    private void initDataGraphics() {
        ImageView dueDateImageView = new ImageView(getClass().getResource("/icons/event/materialicons/black/res/drawable-mdpi/baseline_event_black_18.png").toExternalForm());
        dueDateLbl.setGraphic(dueDateImageView);

        ImageView checkListImageView = new ImageView(getClass().getResource("/icons/checklist/materialicons/black/res/drawable-mdpi/baseline_checklist_black_18.png").toExternalForm());
        completedChecklistItemsLbl.setGraphic(checkListImageView);

        ImageView descriptionImageView = new ImageView(getClass().getResource("/icons/article/materialicons/black/res/drawable-mdpi/baseline_article_black_18.png").toExternalForm());
        descriptionLbl.setGraphic(descriptionImageView);
        descriptionLbl.setText("");

        ImageView resourcesImageView = new ImageView(getClass().getResource("/icons/link/materialicons/black/res/drawable-mdpi/baseline_link_black_18.png").toExternalForm());
        resourcesLbl.setGraphic(resourcesImageView);
    }

    // UI event methods
    @FXML
    void copyCard(ActionEvent event) {
        // TODO Implement this
        System.out.println("Copy card");
    }

    @FXML
    void deleteCard(ActionEvent event) throws SQLException, IOException {
        System.out.println("Delete Card");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure that you want to delete the card?");
        alert.setTitle("Confirm card deletion");
        alert.showAndWait().filter(response -> response == ButtonType.OK)
                .ifPresent((response) -> {
                    try {
                        kanbanBoDataService.deleteCard(cardViewModel);
                        forRemovalProperty().set(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // TODO Need to cause some kind of display if this error is encountered
                    } catch (IOException e) {
                        e.printStackTrace();
                        // TODO Need to cause some kind of display if this error is encountered
                    }
                });
        event.consume();
    }

    @FXML
    void moveCard(ActionEvent event) {
        // TODO Implement this
        System.out.println("Move Card dialog");
    }

    @FXML
    void showCardDetailsPopup(ActionEvent event) {
        // TODO Implement this
        System.out.println("Show card details");
        CardEditorView cardEditorView = new CardEditorView();
        CardEditorPresenter cardEditorPresenter = (CardEditorPresenter) cardEditorView.getPresenter();
        cardEditorPresenter.setCardViewModel(cardViewModel);
        StageUtils.createChildStage("Card details", cardEditorView.getView());
        StageUtils.showAndWaitOnSubStage();
        StageUtils.closeSubStage();
    }

    // Other methods



}