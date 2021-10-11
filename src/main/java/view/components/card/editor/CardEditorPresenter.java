package view.components.card.editor;

import domain.entities.card.ObservableCard;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import utils.view.ScrollPaneFixer;
import view.components.ui.datapanes.card.details.CardDetailsView;
import view.components.ui.datapanes.card.details.CardDetailsPresenter;
import view.components.ui.datapanes.card.resources.CardResourcesPresenter;
import view.components.ui.datapanes.card.resources.CardResourcesView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class CardEditorPresenter implements Initializable {

    // TODO maybe refactor this into a "creation" dialog, separate from the details view?
    // JavaFX injected node variables
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox contentVBox;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;



    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableCard cardViewModel;
    private CardDetailsPresenter cardDetailsPresenter;
    private CardResourcesPresenter cardResourcesPresenter;
    private SimpleBooleanProperty dataValidationErrors;

    // Constructors

    // Getters & Setters

    public void setCardViewModel(ObservableCard cardViewModel) {
        this.cardViewModel = cardViewModel;
        this.cardDetailsPresenter.setCardViewModel(cardViewModel);
        this.cardResourcesPresenter.setCardResourceItemsViewModel(cardViewModel.getResourceItems());

    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScrollPaneFixer.fixBlurryScrollPan(scrollPane);
        CardDetailsView cardDetailsView = new CardDetailsView();
        cardDetailsPresenter = (CardDetailsPresenter) cardDetailsView.getPresenter();
        CardResourcesView cardResourcesView = new CardResourcesView();
        cardResourcesPresenter = (CardResourcesPresenter) cardResourcesView.getPresenter();
        cardDetailsView.getViewAsync(contentVBox.getChildren()::add);
        cardResourcesView.getViewAsync(contentVBox.getChildren()::add);
        dataValidationErrors = new SimpleBooleanProperty(false);
        dataValidationErrors.addListener((changeable, oldValue, newValue) -> {
            if(newValue) {
                saveBtn.setDisable(true);
            } else {
                saveBtn.setDisable(false);
            }
        });
        this.cardDetailsPresenter.dataValidationErrorsProperty().addListener((changeable, oldValue, newValue) -> {
            if(newValue) {
                dataValidationErrors.set(true);
            } else {
                dataValidationErrors.set(false);
            }
        });
    }

    // UI event methods
    @FXML
    void cancel(ActionEvent event) {
        StageUtils.hideSubStage();
        event.consume();
    }

    @FXML
    void save(ActionEvent event) {
        //kanbanBoDataService.updateCard();
        StageUtils.hideSubStage();
        event.consume();
    }

    // Other methods


}