package view.components.column.container;

import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import utils.view.ScrollPaneFixer;
import view.components.card.basictile.CardBasicTilePresenter;
import view.components.card.basictile.CardBasicTileView;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowPresenter;
import view.sharedviewcomponents.popups.carddetails.CardDetailsWindowView;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowPresenter;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowView;

import javax.inject.Inject;
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
    private Button createCardBtn;
    @FXML
    private Button copyColumnBtn;
    @FXML
    private Button moveColumnBtn;
    @FXML
    private Button deleteColumnBtn;
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
    @FXML
    private ScrollPane scrollPane;


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
        initButtonGraphics();
        forRemoval = new SimpleBooleanProperty(false);
        ScrollPaneFixer.fixBlurryScrollPan(scrollPane);
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
        //columnTitleTextField.textProperty().bind(columnViewModel.columnTitleProperty());
        //finalColumnCheckBox.selectedProperty().bind(columnViewModel.finalColumnProperty());
        for (ObservableCard cardViewModel : columnViewModel.getCards()) {
            /*
            CardContainerView cardContainerView = new CardContainerView();
            CardContainerPresenter cardContainerPresenter = (CardContainerPresenter) cardContainerView.getPresenter();
            cardContainerPresenter.setCardViewModel(cardViewModel);
            cardContainerPresenter.forRemovalProperty().addListener((observable, oldVal, newVal) -> {
                if(newVal) {
                    cardContainerView.getViewAsync(cardVBox.getChildren()::remove);
                }
            });
            cardContainerView.getViewAsync(cardVBox.getChildren()::add);
             */

            CardBasicTileView cardBasicTileView = new CardBasicTileView();
            CardBasicTilePresenter cardBasicTilePresenter = (CardBasicTilePresenter) cardBasicTileView.getPresenter();
            cardBasicTilePresenter.setCardViewModel(cardViewModel);
            cardBasicTilePresenter.forRemovalProperty().addListener((observable, oldVal, newVal) -> {
                if(newVal) {
                    cardBasicTileView.getViewAsync(cardVBox.getChildren()::remove);
                }
            });
            cardBasicTileView.getViewAsync(cardVBox.getChildren()::add);
        }

        columnViewModel.getCards().addListener(new ListChangeListener<ObservableCard>() {
            @Override
            public void onChanged(Change<? extends ObservableCard> c) {
                while (c.next())
                    if(c.wasAdded()) {
                        for(ObservableCard observableCard : c.getAddedSubList()) {
                            /*
                            CardContainerView cardContainerView = new CardContainerView();
                            CardContainerPresenter cardContainerPresenter = (CardContainerPresenter) cardContainerView.getPresenter();
                            cardContainerPresenter.setCardViewModel(observableCard);
                            cardContainerPresenter.forRemovalProperty().addListener((observable, oldVal, newVal) -> {
                                if(newVal) {
                                    cardContainerView.getViewAsync(cardVBox.getChildren()::remove);
                                }
                            });
                            cardContainerView.getViewAsync(cardVBox.getChildren()::add);
                            */
                            CardBasicTileView cardBasicTileView = new CardBasicTileView();
                            CardBasicTilePresenter cardBasicTilePresenter = (CardBasicTilePresenter) cardBasicTileView.getPresenter();
                            cardBasicTilePresenter.setCardViewModel(observableCard);
                            cardBasicTilePresenter.forRemovalProperty().addListener((observable, oldVal, newVal) -> {
                                if(newVal) {
                                    cardBasicTileView.getViewAsync(cardVBox.getChildren()::remove);
                                }
                            });
                            cardBasicTileView.getViewAsync(cardVBox.getChildren()::add);
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

    private void initButtonGraphics() {
        ImageView editColumnDetailsImageView = new ImageView(getClass().getResource("/icons/edit_note/materialicons/black/res/drawable-mdpi/baseline_edit_note_black_18.png").toExternalForm());
        ImageView addCardImageView = new ImageView(getClass().getResource("/icons/add_circle_outline/materialicons/black/res/drawable-mdpi/baseline_add_circle_outline_black_18.png").toExternalForm());
        ImageView copyColumnImageView = new ImageView(getClass().getResource("/icons/content_copy/materialicons/black/res/drawable-mdpi/baseline_content_copy_black_18.png").toExternalForm());
        ImageView moveColumnImageView = new ImageView(getClass().getResource("/icons/chevron_right/materialicons/black/res/drawable-mdpi/baseline_chevron_right_black_18.png").toExternalForm());
        ImageView deleteColumnImageView = new ImageView(getClass().getResource("/icons/remove_circle_outline/materialicons/black/res/drawable-mdpi/baseline_remove_circle_outline_black_18.png").toExternalForm());

        editColumnDetailsBtn.setGraphic(editColumnDetailsImageView);
        createCardBtn.setGraphic(addCardImageView);
        copyColumnBtn.setGraphic(copyColumnImageView);
        moveColumnBtn.setGraphic(moveColumnImageView);
        deleteColumnBtn.setGraphic(deleteColumnImageView);
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
        System.out.println("Delete Column");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure that you want to delete the column?");
        alert.setTitle("Confirm column deletion");
        alert.showAndWait().filter(response -> response == ButtonType.OK)
                .ifPresent((response) -> {
                    try {
                        kanbanBoDataService.deleteColumn(columnViewModel);
                        forRemovalProperty().set(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // TODO Need to cause some kind of display if this error is encountered
                    } catch (IOException e) {
                        e.printStackTrace();
                        // TODO Need to cause some kind of display if this error is encountered
                    }
                });
    }

    // Other methods
    private void showColumnDetailsWindow() {
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        StageUtils.closeSubStage();
    }

}