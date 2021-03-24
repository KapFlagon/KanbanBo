package view.screens.mainscreen.subviews.manage.subviews.projectsmanagerview.subviews.activeprojectstab;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import model.domainobjects.project.ActiveProjectModel;

public class ActiveProjectsListPresenter implements Initializable {

    // Injected JavaFX field variables
    @FXML
    private TableView activeProjectListTableView;
    @FXML
    private TableColumn<ActiveProjectModel, String> projectTitleTableCol;
    @FXML
    private TableColumn<ActiveProjectModel, String> creationDateTableCol;
    @FXML
    private TableColumn<ActiveProjectModel, String> lastChangedDateTableCol;

    // Variables
    private ObservableList<ActiveProjectModel> activeProjectList;
    private TableView.TableViewSelectionModel<ActiveProjectModel> selectionModel;

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

    public ObservableList<ActiveProjectModel> getActiveProjectList() {
        return activeProjectList;
    }
    public void setActiveProjectList(ObservableList<ActiveProjectModel> activeProjectList) {
        this.activeProjectList = activeProjectList;
        activeProjectListTableView.setItems(activeProjectList);
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeProjectListTableView.setPlaceholder(new Label ("No active projects in database..."));
        selectionModel = activeProjectListTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        projectTitleTableCol.setCellValueFactory(new PropertyValueFactory<ActiveProjectModel, String>("project_title"));
        creationDateTableCol.setCellValueFactory(new PropertyValueFactory<ActiveProjectModel, String>("creation_timestamp"));
        lastChangedDateTableCol.setCellValueFactory(new PropertyValueFactory<ActiveProjectModel, String>("last_changed_timestamp"));
    }

    // UI Event methods


    // Other methods
    public ActiveProjectModel getSelectedRow() {
        return selectionModel.getSelectedItem();
    }

}
