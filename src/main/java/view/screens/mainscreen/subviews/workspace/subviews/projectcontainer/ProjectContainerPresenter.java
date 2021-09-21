package view.screens.mainscreen.subviews.workspace.subviews.projectcontainer;

import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableProject;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import persistence.services.KanbanBoDataService;
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

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
    private TableView<ObservableResourceItem> resourceItemTableView;
    @FXML
    private TableColumn<ObservableResourceItem, String> relatedItemTitleTableColumn;
    @FXML
    private TableColumn<ObservableResourceItem, Number> relatedItemTypeTableColumn;
    @FXML
    private TableColumn<ObservableResourceItem, String> relatedItemLinkTableColumn;
    @FXML
    private HBox columnHBox;

    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableProject projectViewModel;
    private ColumnDetailsWindowView columnDetailsWindowView;
    private ColumnDetailsWindowPresenter columnDetailsWindowPresenter;


    // Constructors


    // Getters & Setters
    public ObservableProject getProjectViewModel() {
        return projectViewModel;
    }
    public void setProjectViewModel(ObservableProject projectViewModel) throws IOException, SQLException {
        this.projectViewModel = projectViewModel;
        customInit();
    }



    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        relatedItemTitleTableColumn.setCellValueFactory(cellData -> (cellData.getValue().titleProperty()));
        relatedItemTypeTableColumn.setCellValueFactory(cellData -> (cellData.getValue().typeProperty()));
        //relatedItemLinkTableColumn.setCellValueFactory(cellData -> (new Hyperlink(cellData.getValue().getRelatedItemPath())));
        relatedItemLinkTableColumn.setCellValueFactory(cellData -> (cellData.getValue().pathProperty()));
    }


    public void customInit() throws IOException, SQLException {
        projectTitleLbl.textProperty().bind(projectViewModel.projectTitleProperty());
        projectTitleTextField.textProperty().bind(projectViewModel.projectTitleProperty());
        projectStatusTextField.textProperty().bind(projectViewModel.statusTextProperty());
        // TODO examine binding for the choicebox
        projectDescriptionTextArea.textProperty().bind(projectViewModel.projectDescriptionProperty());
        projectCreationDateTextField.textProperty().bind(projectViewModel.creationTimestampProperty());
        projectLastChangedDateTextField.textProperty().bind(projectViewModel.lastChangedTimestampProperty());

        resourceItemTableView.setItems(projectViewModel.getResourceItems());

        for (ObservableColumn columnViewModel : projectViewModel.getColumns()) {
            ColumnContainerView columnContainerView = new ColumnContainerView();
            ColumnContainerPresenter columnContainerPresenter = (ColumnContainerPresenter) columnContainerView.getPresenter();
            columnContainerPresenter.setColumnViewModel(columnViewModel);
            columnHBox.getChildren().add(columnContainerView.getView());
        }
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
        });
    }

    private void initColumnDetailsWindow() {
        columnDetailsWindowView = new ColumnDetailsWindowView();
        columnDetailsWindowPresenter = (ColumnDetailsWindowPresenter) columnDetailsWindowView.getPresenter();
        columnDetailsWindowPresenter.setParentProjectUUID(projectViewModel.getProjectUUID());
    }

    // UI event methods
    @FXML private void editProjectDetails() {
        // TODO Add a simple boolean property and switching to change text and behaviour of this button. Switch between "Edit" and "Display" text.
        System.out.println("Edit project details");
        ProjectDetailsWindowView view = new ProjectDetailsWindowView();
        ProjectDetailsWindowPresenter presenter = (ProjectDetailsWindowPresenter) view.getPresenter();
        presenter.setProjectViewModel(projectViewModel);
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
        resourceItemDetailsPresenter.setParentUUID(projectViewModel.getProjectUUID());
        resourceItemDetailsPresenter.setRelatedItemTypes(projectViewModel.getResourceItems());
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
        // TODO Implement this
        System.out.println("Adding project resource");
    }

    @FXML private void createColumn() throws IOException, SQLException {
        System.out.println("Create Column...");
        initColumnDetailsWindow();
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        /*
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

         */
    }
    // Other methods


}