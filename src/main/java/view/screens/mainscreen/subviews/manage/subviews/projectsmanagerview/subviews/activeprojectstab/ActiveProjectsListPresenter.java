package view.screens.mainscreen.subviews.manage.subviews.projectsmanagerview.subviews.activeprojectstab;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import model.activerecords.ProjectActiveRecord;
import model.domainobjects.project.ProjectModel;


public class ActiveProjectsListPresenter implements Initializable {

    // Injected JavaFX field variables
    @FXML
    private TableView activeProjectListTableView;
    @FXML
    private TableColumn<ProjectActiveRecord, String> projectTitleTableCol;
    @FXML
    private TableColumn<ProjectActiveRecord, String> creationDateTableCol;
    @FXML
    private TableColumn<ProjectActiveRecord, String> lastChangedDateTableCol;


    // Variables
    private ObservableList<ProjectActiveRecord> activeProjectList;
    private TableView.TableViewSelectionModel<ProjectActiveRecord> selectionModel;


    // Constructors


    // Getters and Setters
    public TableView getActiveProjectListTableView() {
        return activeProjectListTableView;
    }
    public void setActiveProjectListTableView(TableView activeProjectListTableView) {
        this.activeProjectListTableView = activeProjectListTableView;
    }

    public TableColumn getProjectTitleTableCol() {
        return projectTitleTableCol;
    }
    public void setProjectTitleTableCol(TableColumn projectTitleTableCol) {
        this.projectTitleTableCol = projectTitleTableCol;
    }

    public TableColumn getCreationDateTableCol() {
        return creationDateTableCol;
    }
    public void setCreationDateTableCol(TableColumn creationDateTableCol) {
        this.creationDateTableCol = creationDateTableCol;
    }

    public TableColumn getLastChangedDateTableCol() {
        return lastChangedDateTableCol;
    }
    public void setLastChangedDateTableCol(TableColumn lastChangedDateTableCol) {
        this.lastChangedDateTableCol = lastChangedDateTableCol;
    }

    public ObservableList<ProjectActiveRecord> getActiveProjectList() {
        return activeProjectList;
    }
    public void setActiveProjectList(ObservableList<ProjectActiveRecord> activeProjectList) {
        this.activeProjectList = activeProjectList;
        activeProjectListTableView.setItems(activeProjectList);
    }


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        initSelectionModel();
        initTableColumns();
    }

    public void initTableView() {
        activeProjectListTableView.setPlaceholder(new Label ("No active projects in database..."));
    }

    public void initSelectionModel() {
        selectionModel = activeProjectListTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
    }

    public void initTableColumns() {
        projectTitleTableCol.setCellValueFactory(cellData -> (cellData.getValue().projectTitleProperty()));
        creationDateTableCol.setCellValueFactory(cellData -> (cellData.getValue().creationTimestampProperty()));
        lastChangedDateTableCol.setCellValueFactory(cellData -> (cellData.getValue().lastChangedTimestampProperty()));
    }

    // UI Event methods


    // Other methods
    public ProjectActiveRecord<ProjectModel> getSelectedRow() {
        return selectionModel.getSelectedItem();
    }

}
