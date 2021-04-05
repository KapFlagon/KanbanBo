package view.screens.mainscreen.subviews.workspace.subviews.columncontainer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.activerecords.ProjectActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.column.ActiveProjectColumnModel;
import model.repositories.services.ProjectColumnRepositoryService;
import utils.StageUtils;
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
    private ObservableList cards;
    private ColumnDetailsWindowView columnDetailsWindowView;
    private ColumnDetailsWindowPresenter columnDetailsWindowPresenter;

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

    }

    public void customInit() {
        columnTitleLbl.setText(projectColumnActiveRecord.getColumnTitle());
    }

    private void initColumnDetailsWindow() {
        columnDetailsWindowView = new ColumnDetailsWindowView();
        columnDetailsWindowPresenter = (ColumnDetailsWindowPresenter) columnDetailsWindowView.getPresenter();
        columnDetailsWindowPresenter.setProjectColumnActiveRecord(projectColumnActiveRecord);
    }


    // UI event methods
    public void addCard() {
        System.out.println("Adding card");
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