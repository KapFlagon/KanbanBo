package view.screens.mainscreen.subviews.workspace.subviews.columncontainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.activerecords.ColumnCardActiveRecord;
import model.activerecords.ProjectActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.card.ActiveColumnCardModel;
import model.domainobjects.column.ActiveProjectColumnModel;
import model.repositories.services.ProjectColumnRepositoryService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.workspace.subviews.cardcontainer.CardContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.cardcontainer.CardContainerView;
import view.sharedcomponents.popups.carddetails.CardDetailsWindowPresenter;
import view.sharedcomponents.popups.carddetails.CardDetailsWindowView;
import view.sharedcomponents.popups.columndetails.ColumnDetailsWindowPresenter;
import view.sharedcomponents.popups.columndetails.ColumnDetailsWindowView;

import java.net.URL;
import java.util.ResourceBundle;

public class ColumnContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private VBox cardVBox;
    @FXML
    private Label columnTitleLbl;
    @FXML
    private Text cardCountTxt;
    @FXML
    private Button addCardBtn;


    // Other variables
    private ProjectColumnActiveRecord<ActiveProjectColumnModel> projectColumnActiveRecord;
    private ObservableList cardsList;
    private ColumnDetailsWindowView columnDetailsWindowView;
    private ColumnDetailsWindowPresenter columnDetailsWindowPresenter;
    private CardDetailsWindowView cardDetailsWindowView;
    private CardDetailsWindowPresenter cardDetailsWindowPresenter;

    // Constructors

    // Getters & Setters

    public ProjectColumnActiveRecord<ActiveProjectColumnModel> getProjectColumnActiveRecord() {
        return projectColumnActiveRecord;
    }
    public void setProjectColumnActiveRecord(ProjectColumnActiveRecord<ActiveProjectColumnModel> projectColumnActiveRecord) {
        this.projectColumnActiveRecord = projectColumnActiveRecord;
        this.columnTitleLbl.setText(projectColumnActiveRecord.getColumnTitle());
        this.columnTitleLbl.textProperty().bind(projectColumnActiveRecord.columnTitleProperty());
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardsList = FXCollections.observableArrayList();
    }

    public void customInit() {
        columnTitleLbl.setText(projectColumnActiveRecord.getColumnTitle());
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
    public void addCard() {
        System.out.println("Adding card");
        initCardDetailsWindow();
        StageUtils.createChildStage("Enter Card Details", cardDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ColumnCardActiveRecord<ActiveColumnCardModel> ccar = cardDetailsWindowPresenter.getColumnCardActiveRecord();
        if(ccar != null) {
            ccar.getColumnCardModel().setParent_column(projectColumnActiveRecord.getProjectColumnModel().getColumn_uuid());
            ccar.getColumnCardModel().setCard_position(cardsList.size() + 1);
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
        showProjectDetailsWindow();
    }

    // Other methods
    private void showProjectDetailsWindow() {
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ProjectColumnActiveRecord tempProjectColumnActiveRecord = columnDetailsWindowPresenter.getProjectColumnActiveRecord();
        StageUtils.closeSubStage();
    }

}