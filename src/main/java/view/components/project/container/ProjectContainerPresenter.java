package view.components.project.container;

import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableWorkspaceProject;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import utils.view.ScrollPaneFixer;
import view.components.column.container.ColumnContainerPresenter;
import view.components.column.container.ColumnContainerView;
import view.sharedviewcomponents.popups.EditorDataMode;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowPresenter;
import view.sharedviewcomponents.popups.columndetails.ColumnDetailsWindowView;
import view.sharedviewcomponents.popups.finalcolumnselection.FinalColumnSelectionPresenter;
import view.sharedviewcomponents.popups.finalcolumnselection.FinalColumnSelectionView;
import view.sharedviewcomponents.popups.projectdetails.ProjectDetailsWindowPresenter;
import view.sharedviewcomponents.popups.projectdetails.ProjectDetailsWindowView;
import view.sharedviewcomponents.popups.resourceitemdetails.ResourceItemDetailsPresenter;
import view.sharedviewcomponents.popups.resourceitemdetails.ResourceItemDetailsView;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private Button createColumnBtn;
    @FXML
    private Button selectFinalColumnBtn;
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
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private ScrollPane subScrollPane;

    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableWorkspaceProject projectViewModel;
    private ColumnDetailsWindowView columnDetailsWindowView;
    private ColumnDetailsWindowPresenter columnDetailsWindowPresenter;
    private SimpleBooleanProperty forRemoval;


    // Constructors


    // Getters & Setters
    public ObservableWorkspaceProject getProjectViewModel() {
        return projectViewModel;
    }
    public void setProjectViewModel(ObservableWorkspaceProject projectViewModel) throws IOException, SQLException {
        this.projectViewModel = projectViewModel;
        customInit();
    }

    public SimpleBooleanProperty forRemovalProperty() {
        return forRemoval;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtonGraphics();
        relatedItemTitleTableColumn.setCellValueFactory(cellData -> (cellData.getValue().titleProperty()));
        relatedItemTypeTableColumn.setCellValueFactory(cellData -> (cellData.getValue().typeProperty()));
        //relatedItemLinkTableColumn.setCellValueFactory(cellData -> (new Hyperlink(cellData.getValue().getRelatedItemPath())));
        relatedItemLinkTableColumn.setCellValueFactory(cellData -> (cellData.getValue().pathProperty()));
        forRemoval = new SimpleBooleanProperty(false);
        ScrollPaneFixer.fixBlurryScrollPan(mainScrollPane);
        ScrollPaneFixer.fixBlurryScrollPan(subScrollPane);
    }


    public void customInit() throws IOException, SQLException {
        projectTitleLbl.textProperty().bind(projectViewModel.projectTitleProperty());
        // TODO examine binding for the choicebox

        resourceItemTableView.setItems(projectViewModel.getResourceItems());
        // TODO Resume here. Need to devise a strategy to handle populating old data and storing object references properly to remove them from the HBox efficiently later when deleted.
        if(projectViewModel.getColumns().size() < 1) {
            selectFinalColumnBtn.setDisable(true);
        }
        for (ObservableColumn columnViewModel : projectViewModel.getColumns()) {
            generateAndAddColumnView(columnViewModel);
        }
        projectViewModel.getColumns().addListener(new ListChangeListener<ObservableColumn>() {
            @Override
            public void onChanged(Change<? extends ObservableColumn> c) {
                while (c.next()) {
                    ColumnContainerView columnContainerView = new ColumnContainerView();
                    ColumnContainerPresenter columnContainerPresenter = (ColumnContainerPresenter) columnContainerView.getPresenter();
                    columnContainerPresenter.forRemovalProperty().addListener((observable, oldVal, newVal) -> {
                        if(newVal) {
                            columnContainerView.getViewAsync(columnHBox.getChildren()::remove);
                        }
                    });
                    if (c.wasAdded()) {
                        for (ObservableColumn addedObservableColumn : c.getAddedSubList()) {
                            columnContainerPresenter.setColumnViewModel(addedObservableColumn);
                            columnContainerView.getViewAsync(columnHBox.getChildren()::add);
                        }
                        if(projectViewModel.getColumns().size() > 0) {
                            selectFinalColumnBtn.setDisable(false);
                        }
                    }
                    if (c.wasPermutated()) {
                        System.out.println("Permutation detected");
                        columnHBox.getChildren().clear();
                        for(ObservableColumn permutedObservableColumn : projectViewModel.getColumns()) {
                            generateAndAddColumnView(permutedObservableColumn);
                        }
                    }
                    if (c.wasRemoved()) {
                        if(projectViewModel.getColumns().size() < 1) {
                            selectFinalColumnBtn.setDisable(true);
                        }
                    }
                }
            }
        });
    }

    private void initColumnDetailsWindow() {
        columnDetailsWindowView = new ColumnDetailsWindowView();
        columnDetailsWindowPresenter = (ColumnDetailsWindowPresenter) columnDetailsWindowView.getPresenter();
    }

    private void initButtonGraphics() {
        ImageView editProjectDetailsImageView = new ImageView(getClass().getResource("/icons/edit_note/materialicons/black/res/drawable-mdpi/baseline_edit_note_black_18.png").toExternalForm());
        ImageView createColumnImageView = new ImageView(getClass().getResource("/icons/add_circle_outline/materialicons/black/res/drawable-mdpi/baseline_add_circle_outline_black_18.png").toExternalForm());
        ImageView finalColumnSelectionImageView = new ImageView(getClass().getResource("/icons/sports_score/materialicons/black/res/drawable-mdpi/baseline_sports_score_black_18.png").toExternalForm());
        editProjectDetailsBtn.setGraphic(editProjectDetailsImageView);
        createColumnBtn.setGraphic(createColumnImageView);
        selectFinalColumnBtn.setGraphic(finalColumnSelectionImageView);
    }

    // UI event methods
    @FXML private void editProjectDetails() {
        // TODO Add a simple boolean property and switching to change text and behaviour of this button. Switch between "Edit" and "Display" text.
        System.out.println("Edit project details");
        ProjectDetailsWindowView view = new ProjectDetailsWindowView();
        ProjectDetailsWindowPresenter presenter = (ProjectDetailsWindowPresenter) view.getPresenter();
        presenter.setProjectViewModel(projectViewModel);
        presenter.setEditorDataMode(EditorDataMode.EDITING);
        StageUtils.createChildStage("Enter Project Details", view.getView());
        StageUtils.getSubStages().peekLast().initStyle(StageStyle.UNDECORATED);
        StageUtils.showAndWaitOnSubStage();
    }

    @FXML private void saveProjectDetails() {
        // TODO Implement this
    }

    @FXML private void addResourceItem() {
        // TODO Implement this
        ResourceItemDetailsView resourceItemDetailsView = new ResourceItemDetailsView();
        ResourceItemDetailsPresenter resourceItemDetailsPresenter = (ResourceItemDetailsPresenter) resourceItemDetailsView.getPresenter();
        resourceItemDetailsPresenter.setParentUUID(projectViewModel.getProjectUUID());
        resourceItemDetailsPresenter.setRelatedItemTypes(projectViewModel.getResourceItems());
        StageUtils.createChildStage("Add Related Item", resourceItemDetailsView.getView());
        StageUtils.showAndWaitOnSubStage();
    }

    @FXML private void openResourceItem() {
        // TODO implement this
    }

    @FXML private void editResourceItem() {
        // TODO Implement this
    }

    @FXML private void removeProjectResourceItem() {
        // TODO Implement this
    }

    public void addProjectResourceItem() {
        // TODO Implement this
        System.out.println("Adding project resource");
    }

    @FXML private void createColumn() throws IOException, SQLException {
        System.out.println("Create Column...");
        initColumnDetailsWindow();
        columnDetailsWindowPresenter.setParentProjectUUID(projectViewModel.getProjectUUID());
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        // TODO Need to respond and feedback scenario where a final column already exists...
        StageUtils.showAndWaitOnSubStage();
    }

    @FXML private void selectFinalColumn() {
        FinalColumnSelectionView finalColumnSelectionView = new FinalColumnSelectionView();
        FinalColumnSelectionPresenter finalColumnSelectionPresenter = (FinalColumnSelectionPresenter) finalColumnSelectionView.getPresenter();
        finalColumnSelectionPresenter.setAvailableColumns(projectViewModel.getColumns());
        StageUtils.createChildStage("Select Final Column", finalColumnSelectionView.getView());
        StageUtils.showAndWaitOnSubStage();
    }

    // Other methods
    private void generateAndAddColumnView(ObservableColumn observableColumn) {
        ColumnContainerView columnContainerView = new ColumnContainerView();
        ColumnContainerPresenter columnContainerPresenter = (ColumnContainerPresenter) columnContainerView.getPresenter();
        columnContainerPresenter.setColumnViewModel(observableColumn);
        columnContainerPresenter.forRemovalProperty().addListener((observable, oldVal, newVal) -> {
            if(newVal) {
                columnContainerView.getViewAsync(columnHBox.getChildren()::remove);
            }
        });
        columnContainerView.getViewAsync(columnHBox.getChildren()::add);
    }


}