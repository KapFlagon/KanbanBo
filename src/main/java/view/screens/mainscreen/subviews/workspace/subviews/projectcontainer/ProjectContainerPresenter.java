package view.screens.mainscreen.subviews.workspace.subviews.projectcontainer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import domain.activerecords.ResourceItemActiveRecord;
import domain.activerecords.project.ProjectActiveRecord;
import domain.activerecords.ProjectColumnActiveRecord;
import persistence.tables.column.ColumnTable;
import persistence.repositories.legacy.ActiveColumnListRepository;
import persistence.services.legacy.ProjectColumnRepositoryService;
import persistence.services.legacy.ResourceItemRepositoryService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.workspace.subviews.columncontainer.ColumnContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.columncontainer.ColumnContainerView;
import view.sharedviewcomponents.DetailsPopupInitialDataMode;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowPresenter;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowView;
import view.sharedviewcomponents.popups.projectdetails.ProjectDetailsWindowPresenter;
import view.sharedviewcomponents.popups.projectdetails.ProjectDetailsWindowView;
import view.sharedviewcomponents.popups.resourceitemdetails.ResourceItemDetailsPresenter;
import view.sharedviewcomponents.popups.resourceitemdetails.ResourceItemDetailsView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProjectContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label projectTitleLbl;
    @FXML
    private Button editProjectDetailsBtn;
    @FXML
    private Button saveProjectDetailsBtn;
    @FXML
    private TextField projectTitleTextField;
    @FXML
    private TextField projectStatusTextField; // TODO Used to display the text in a copyable way, but replaced by choicebox in "edit" mode.
    @FXML
    private ChoiceBox projectStatusChoiceBox; // TODO Used to select status in "edit" mode, but replaced by non-editable TextField in "display" mode.
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private TextField projectCreationDateTextField;
    @FXML
    private TextField projectLastChangedDateTextField;
    @FXML
    private TableView relatedItemTable;
    @FXML
    private TableColumn<ResourceItemActiveRecord, String> relatedItemTitleTableColumn;
    @FXML
    private TableColumn<ResourceItemActiveRecord, Number> relatedItemTypeTableColumn;
    @FXML
    private TableColumn<ResourceItemActiveRecord, String> relatedItemLinkTableColumn;
    @FXML
    private HBox columnHBox;

    // Other variables
    //private AbstractProjectModel projectModel;
    private ProjectActiveRecord projectActiveRecord;
    private ProjectColumnRepositoryService projectColumnRepositoryService;
    private ActiveColumnListRepository activeColumnListRepository;
    private ResourceItemRepositoryService resourceItemRepositoryService;
    private ObservableList<ProjectColumnActiveRecord> projectColumnsList;
    private ObservableList<ResourceItemActiveRecord> relatedItemsList;
    private ColumnDetailsWindowView columnDetailsWindowView;
    private ColumnDetailsWindowPresenter columnDetailsWindowPresenter;


    // Constructors

    // Getters & Setters
    /*public AbstractProjectModel getProjectModel() {
        return projectModel;
    }
    public void setProjectModel(AbstractProjectModel projectModel) {
        this.projectModel = projectModel;
        doTheThings();
    }
     */

    public ProjectActiveRecord getProjectActiveRecord() {
        return projectActiveRecord;
    }
    public void setProjectActiveRecord(ProjectActiveRecord projectActiveRecord) throws IOException, SQLException {
        this.projectActiveRecord = projectActiveRecord;
        customInit();
    }

    public ObservableList<ProjectColumnActiveRecord> getProjectColumnsList() {
        return projectColumnsList;
    }
    public void setProjectColumnsList(ObservableList<ProjectColumnActiveRecord> projectColumnsList) {
        this.projectColumnsList = projectColumnsList;
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectColumnsList = FXCollections.observableArrayList();
        relatedItemsList = FXCollections.observableArrayList();
        relatedItemTitleTableColumn.setCellValueFactory(cellData -> (cellData.getValue().relatedItemTitleProperty()));
        relatedItemTypeTableColumn.setCellValueFactory(cellData -> (cellData.getValue().relatedItemTypeProperty()));
        //relatedItemLinkTableColumn.setCellValueFactory(cellData -> (new Hyperlink(cellData.getValue().getRelatedItemPath())));
        relatedItemLinkTableColumn.setCellValueFactory(cellData -> (cellData.getValue().relatedItemPathProperty()));
        //initDragAndDropMethods();
    }

    public void initDragAndDropMethods() {
        // https://stackoverflow.com/questions/22424082/drag-and-drop-vbox-element-with-show-snapshot-in-javafx
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/drag-drop.htm#CHDJFJDH
        // https://docs.oracle.com/javase/8/javafx/events-tutorial/paper-doll.htm#CBHFHJID
        // From James_D
        // https://stackoverflow.com/questions/22820160/accessing-properties-of-custom-object-from-javafx-draganddrop-clipboard
        // https://community.oracle.com/tech/developers/discussion/2513382/drag-and-drop-objects-with-javafx-properties

        //initDragColumnOver();
        //initColumnDragEntered();
        //initColumnDragExited();
        //initColumnDragDropped();
    }

    public void customInit() throws IOException, SQLException {
        projectTitleLbl.textProperty().bind(projectActiveRecord.projectTitleProperty());
        projectTitleTextField.textProperty().bind(projectActiveRecord.projectTitleProperty());
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        SimpleStringProperty statusProperty = new SimpleStringProperty(resourceBundle.getString(projectActiveRecord.statusTextProperty().getValue()));
        projectStatusTextField.textProperty().bind(statusProperty);
        // TODO examine binding for the choicebox
        projectDescriptionTextArea.textProperty().bind(projectActiveRecord.projectDescriptionProperty());
        projectCreationDateTextField.textProperty().bind(projectActiveRecord.creationTimestampProperty());
        projectLastChangedDateTextField.textProperty().bind(projectActiveRecord.lastChangedTimestampProperty());

        resourceItemRepositoryService = new ResourceItemRepositoryService(projectActiveRecord.getProjectUUID());
        relatedItemsList = resourceItemRepositoryService.getRelatedItemsList();
        for (ResourceItemActiveRecord resourceItemActiveRecord : relatedItemsList) {
            // TODO fill table here
        }
        projectColumnRepositoryService = new ProjectColumnRepositoryService(projectActiveRecord);
        projectColumnsList = projectColumnRepositoryService.getColumnsList();
        for (ProjectColumnActiveRecord<ColumnTable> projectColumnActiveRecord : projectColumnsList) {
            ColumnContainerView ccv = new ColumnContainerView();
            ColumnContainerPresenter ccp = (ColumnContainerPresenter) ccv.getPresenter();
            ccp.setProjectColumnActiveRecord(projectColumnActiveRecord);
            columnHBox.getChildren().add(ccv.getView());
        }
        //activeColumnListRepository = projectColumnRepositoryService.getActiveColumnListRepository();
        // TODO get all column records linked to the project, populate the data.
    }

    private void initColumnDetailsWindow() {
        columnDetailsWindowView = new ColumnDetailsWindowView();
        columnDetailsWindowPresenter = (ColumnDetailsWindowPresenter) columnDetailsWindowView.getPresenter();
        columnDetailsWindowPresenter.setParentProject(projectActiveRecord);
    }

    // UI event methods
    @FXML private void editProjectDetails() {
        // TODO Add a simple boolean property and switching to change text and behaviour of this button. Switch between "Edit" and "Display" text.
        System.out.println("Edit project details");
        ProjectDetailsWindowView view = new ProjectDetailsWindowView();
        ProjectDetailsWindowPresenter presenter = (ProjectDetailsWindowPresenter) view.getPresenter();
        presenter.setProjectActiveRecord(projectActiveRecord);
        presenter.setInitialDataMode(DetailsPopupInitialDataMode.EDIT);
        StageUtils.createChildStage("Enter Project Details", view.getView());
        StageUtils.getSubStages().peekLast().initStyle(StageStyle.UNDECORATED);
        StageUtils.showAndWaitOnSubStage();
    }

    @FXML private void saveProjectDetails() {
        // TODO Implement this
    }

    @FXML private void addRelatedItem() {
        // TODO Implement this
        ResourceItemDetailsView resourceItemDetailsView = new ResourceItemDetailsView();
        ResourceItemDetailsPresenter resourceItemDetailsPresenter = (ResourceItemDetailsPresenter) resourceItemDetailsView.getPresenter();
        resourceItemDetailsPresenter.setParentUUID(projectActiveRecord.getUUID());
        resourceItemDetailsPresenter.setRelatedItemTypes(resourceItemRepositoryService.getRelatedItemRepository().getRelatedItemTypeObservableList());
        StageUtils.createChildStage("Add Related Item", resourceItemDetailsView.getView());
        StageUtils.showAndWaitOnSubStage();
    }

    @FXML private void editRelatedItem() {
        // TODO Implement this
    }

    @FXML private void removeRelatedItem() {
        // TODO Implement this
    }

    public void addProjectResource() {
        System.out.println("Adding project resource");
    }

    @FXML private void createColumn() throws IOException, SQLException {
        System.out.println("Create Column...");
        initColumnDetailsWindow();
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ProjectColumnActiveRecord<ColumnTable> pcar = columnDetailsWindowPresenter.getProjectColumnActiveRecord();
        if(pcar != null) {
            pcar.setParentProjectActiveRecord(projectActiveRecord);
            pcar.getProjectColumnModel().setColumn_position(projectColumnsList.size() + 1);
            projectColumnsList.add(pcar);
            ColumnContainerView ccv = new ColumnContainerView();
            ColumnContainerPresenter ccp = (ColumnContainerPresenter) ccv.getPresenter();
            // use ccp to set data in the column data.
            ccp.setProjectColumnActiveRecord(pcar);
            columnHBox.getChildren().add(ccv.getView());
        }
    }
    // Other methods


}