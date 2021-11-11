package view.screens.mainscreen.subviews.manage.subviews.projectstable;

import domain.entities.project.ObservableWorkspaceProject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.SelectionMode;
import persistence.services.KanbanBoDataService;
import utils.view.ScrollPaneFixer;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;


public class ProjectsTablePresenter implements Initializable {

    // Injected JavaFX field variables
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TableView<ObservableWorkspaceProject> activeProjectListTableView;
    @FXML
    private TableColumn<ObservableWorkspaceProject, String> projectTitleTableCol;
    @FXML
    private TableColumn<ObservableWorkspaceProject, String> projectStatusTableCol;
    @FXML
    private TableColumn<ObservableWorkspaceProject, String> creationDateTableCol;
    @FXML
    private TableColumn<ObservableWorkspaceProject, String> lastChangedDateTableCol;


    // Variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableList<ObservableWorkspaceProject> projectTableViewModel;
    private TableView.TableViewSelectionModel<ObservableWorkspaceProject> selectionModel;


    // Constructors


    // Getters and Setters


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        initSelectionModel();
        initTableColumns();
        projectTableViewModel = kanbanBoDataService.getProjectsList();
        activeProjectListTableView.setItems(projectTableViewModel);
        ScrollPaneFixer.fixBlurryScrollPan(scrollPane);
    }

    public void initTableView() {
        //activeProjectListTableView.setPlaceholder(new Label ("No projects in database..."));
    }

    public void initSelectionModel() {
        selectionModel = activeProjectListTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
    }

    public void initTableColumns() {
        projectTitleTableCol.setCellValueFactory(cellData -> (cellData.getValue().projectTitleProperty()));
        projectStatusTableCol.setCellValueFactory(cellData -> (cellData.getValue().statusTextProperty()));
        creationDateTableCol.setCellValueFactory(cellData -> (cellData.getValue().creationTimestampProperty()));
        lastChangedDateTableCol.setCellValueFactory(cellData -> (cellData.getValue().lastChangedTimestampProperty()));
    }

    // UI Event methods


    // Other methods
    public ObservableWorkspaceProject getSelectedRow() {
        return selectionModel.getSelectedItem();
    }

}
