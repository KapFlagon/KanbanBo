package view.screens.mainscreen.subviews.workspace.subviews.columncontainer;

import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableProject;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import persistence.services.KanbanBoDataService;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import utils.StageUtils;
import view.screens.mainscreen.subviews.workspace.subviews.cardcontainer.CardContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.cardcontainer.CardContainerView;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowPresenter;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowView;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowPresenter;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowView;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
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
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableColumn columnViewModel;
    private ColumnDetailsWindowView columnDetailsWindowView;
    private ColumnDetailsWindowPresenter columnDetailsWindowPresenter;
    private CardDetailsWindowView cardDetailsWindowView;
    private CardDetailsWindowPresenter cardDetailsWindowPresenter;
    private SimpleBooleanProperty forRemoval;

    // Constructors

    // Getters & Setters
    public ObservableColumn getColumnViewModel() {
        return columnViewModel;
    }

    public void setColumnViewModel(ObservableColumn columnViewModel) {
        this.columnViewModel = columnViewModel;
        customInit();
    }

    public SimpleBooleanProperty forRemovalProperty() {
        return forRemoval;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        forRemoval = new SimpleBooleanProperty(false);
    }

    public void initDragAndDropMethods() {
        // https://stackoverflow.com/questions/22424082/drag-and-drop-vbox-element-with-show-snapshot-in-javafx
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/drag-drop.htm#CHDJFJDH
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/paper-doll.htm#CBHFHJID
        // From James_D
        // https://stackoverflow.com/questions/22820160/accessing-properties-of-custom-object-from-javafx-draganddrop-clipboard
        // https://community.oracle.com/tech/developers/discussion/2513382/drag-and-drop-objects-with-javafx-properties
    }



    public void customInit() {
        columnTitleLbl.textProperty().bind(columnViewModel.columnTitleProperty());
        columnTitleTextField.textProperty().bind(columnViewModel.columnTitleProperty());
        finalColumnCheckBox.selectedProperty().bind(columnViewModel.finalColumnProperty());
        for (ObservableCard cardViewModel : columnViewModel.getCards()) {
            CardContainerView cardContainerView = new CardContainerView();
            CardContainerPresenter cardContainerPresenter = (CardContainerPresenter) cardContainerView.getPresenter();
            cardContainerPresenter.setCardViewModel(cardViewModel);
            cardContainerPresenter.forRemovalProperty().addListener((observable, oldVal, newVal) -> {
                if(newVal) {
                    cardContainerView.getViewAsync(cardVBox.getChildren()::remove);
                }
            });
            cardContainerView.getViewAsync(cardVBox.getChildren()::add);
        }

        columnViewModel.getCards().addListener(new ListChangeListener<ObservableCard>() {
            @Override
            public void onChanged(Change<? extends ObservableCard> c) {
                while (c.next())
                    if(c.wasAdded()) {
                        for(ObservableCard observableCard : c.getAddedSubList()) {
                            CardContainerView cardContainerView = new CardContainerView();
                            CardContainerPresenter cardContainerPresenter = (CardContainerPresenter) cardContainerView.getPresenter();
                            cardContainerPresenter.setCardViewModel(observableCard);
                            cardContainerPresenter.forRemovalProperty().addListener((observable, oldVal, newVal) -> {
                                if(newVal) {
                                    cardContainerView.getViewAsync(cardVBox.getChildren()::remove);
                                }
                            });
                            cardContainerView.getViewAsync(cardVBox.getChildren()::add);
                        }
                    }
                if(c.wasUpdated()) {
                    // TODO Check if this needs to be implemented...
                    System.out.println("column list was updated");
                }
            }
        });
    }

    private void initColumnDetailsWindow() {
        columnDetailsWindowView = new ColumnDetailsWindowView();
        columnDetailsWindowPresenter = (ColumnDetailsWindowPresenter) columnDetailsWindowView.getPresenter();
        columnDetailsWindowPresenter.setColumnViewModel(columnViewModel);
    }

    private void initCardDetailsWindow() {
        cardDetailsWindowView = new CardDetailsWindowView();
        cardDetailsWindowPresenter = (CardDetailsWindowPresenter) cardDetailsWindowView.getPresenter();
        cardDetailsWindowPresenter.setParentColumnUUID(columnViewModel.getColumnUUID());
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
    }

    @FXML private void createCardFromTemplate() {
        // TODO Implement this
    }

    @FXML private void deleteColumn() throws SQLException, IOException {
        kanbanBoDataService.deleteColumn(columnViewModel);
        forRemovalProperty().set(true);
    }

    // Other methods
    private void showColumnDetailsWindow() {
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        StageUtils.closeSubStage();
    }

}