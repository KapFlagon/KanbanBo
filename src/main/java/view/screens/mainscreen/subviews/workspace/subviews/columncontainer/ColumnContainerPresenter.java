package view.screens.mainscreen.subviews.workspace.subviews.columncontainer;

import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableProject;
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

    // Constructors

    // Getters & Setters
    public ObservableColumn getColumnViewModel() {
        return columnViewModel;
    }

    public void setColumnViewModel(ObservableColumn columnViewModel) {
        this.columnViewModel = columnViewModel;
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initDragAndDropMethods() {
        // https://stackoverflow.com/questions/22424082/drag-and-drop-vbox-element-with-show-snapshot-in-javafx
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/drag-drop.htm#CHDJFJDH
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/paper-doll.htm#CBHFHJID
        // From James_D
        // https://stackoverflow.com/questions/22820160/accessing-properties-of-custom-object-from-javafx-draganddrop-clipboard
        // https://community.oracle.com/tech/developers/discussion/2513382/drag-and-drop-objects-with-javafx-properties
    }



    public void customInit() throws IOException, SQLException {
        columnTitleLbl.textProperty().bind(columnViewModel.columnTitleProperty());
        columnTitleTextField.textProperty().bind(columnViewModel.columnTitleProperty());
        finalColumnCheckBox.selectedProperty().bind(columnViewModel.finalColumnProperty());
        for (ObservableCard cardViewModel : columnViewModel.getCards()) {
            CardContainerView cardContainerView = new CardContainerView();
            CardContainerPresenter cardContainerPresenter = (CardContainerPresenter) cardContainerView.getPresenter();
            cardContainerPresenter.setCardViewModel(cardViewModel);
            cardVBox.getChildren().add(cardContainerView.getView());
        }
        // TODO RESUME HERE
        /*
        projectViewModel.getColumns().addListener(new ListChangeListener<ObservableColumn>() {
            @Override
            public void onChanged(Change<? extends ObservableColumn> c) {
                while (c.next())
                    if(c.wasAdded()) {
                        for(ObservableColumn observableColumn : c.getAddedSubList()) {
                            ColumnContainerView columnContainerView = new ColumnContainerView();
                            ColumnContainerPresenter columnContainerPresenter = (ColumnContainerPresenter) columnContainerView.getPresenter();
                            columnContainerPresenter.setColumnViewModel(observableColumn);
                            columnHBox.getChildren().add(columnContainerView.getView());
                        }
                    }
                if(c.wasRemoved()) {
                    for(ObservableColumn observableColumn : c.getRemoved()) {
                        List thing = columnHBox.getChildren();
                        for(Object nodeObject : thing) {
                            if(nodeObject.getClass() == ColumnContainerView.class) {
                                ColumnContainerPresenter columnContainerPresenter = (ColumnContainerPresenter) ((ColumnContainerView) nodeObject).getPresenter();
                                if(columnContainerPresenter.getColumnViewModel().getColumnUUID().equals(observableColumn.getColumnUUID())) {
                                    columnHBox.getChildren().remove(nodeObject);
                                }
                            }
                        }
                    }
                }
                if(c.wasUpdated()) {
                    // TODO Check if this needs to be implemented...
                    System.out.println("column list was updated");
                }
            }
        });*/
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

    // Other methods
    private void showColumnDetailsWindow() {
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        StageUtils.closeSubStage();
    }

}