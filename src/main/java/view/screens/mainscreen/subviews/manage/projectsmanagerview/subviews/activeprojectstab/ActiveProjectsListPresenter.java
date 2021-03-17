package view.screens.mainscreen.subviews.manage.projectsmanagerview.subviews.activeprojectstab;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import utils.DatabaseUtils;
import utils.StageUtils;
import view.sharedcomponents.inputwindows.projectdetails.ProjectDetailsWindowPresenter;
import view.sharedcomponents.inputwindows.projectdetails.ProjectDetailsWindowView;
import model.datamodel.project.ActiveProjectModel;

public class ActiveProjectsListPresenter implements Initializable {

    // Injected JavaFX field variables
    @FXML
    private Button newProjectButton;
    // TODO Move all controls into level above the tabs for the different table views, then get tab, get table, get selected item, and perform action on that data.
    @FXML Button editProjectButton;
    @FXML
    private Button archiveProjectButton;
    @FXML
    private Button completeProjectButton;
    @FXML
    private Button deleteProjectButton;
    @FXML
    private Button copyProjectAsActiveProjectButton;
    @FXML
    private Button copyProjectToTemplateButton;
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
    private Dao<ActiveProjectModel, UUID> activeProjectModelDao;
    private TableView.TableViewSelectionModel<ActiveProjectModel> selectionModel;

    // Constructors


    // Getters and Setters
    public Button getNewProjectButton() {
        return newProjectButton;
    }
    public void setNewProjectButton(Button newProjectButton) {
        this.newProjectButton = newProjectButton;
    }

    public Button getArchiveProjectButton() {
        return archiveProjectButton;
    }
    public void setArchiveProjectButton(Button archiveProjectButton) {
        this.archiveProjectButton = archiveProjectButton;
    }

    public Button getCompleteProjectButton() {
        return completeProjectButton;
    }
    public void setCompleteProjectButton(Button completeProjectButton) {
        this.completeProjectButton = completeProjectButton;
    }

    public Button getDeleteProjectButton() {
        return deleteProjectButton;
    }
    public void setDeleteProjectButton(Button deleteProjectButton) {
        this.deleteProjectButton = deleteProjectButton;
    }

    public Button getCopyProjectToTemplateButton() {
        return copyProjectToTemplateButton;
    }
    public void setCopyProjectToTemplateButton(Button copyProjectToTemplateButton) {
        this.copyProjectToTemplateButton = copyProjectToTemplateButton;
    }

    public Button getCopyProjectAsActiveProjectButton() {
        return copyProjectAsActiveProjectButton;
    }
    public void setCopyProjectAsActiveProjectButton(Button copyProjectAsActiveProjectButton) {
        this.copyProjectAsActiveProjectButton = copyProjectAsActiveProjectButton;
    }

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

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeProjectList = FXCollections.observableArrayList();
        activeProjectListTableView.setPlaceholder(new Label ("No active projects in database..."));
        selectionModel = activeProjectListTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        projectTitleTableCol.setCellValueFactory(new PropertyValueFactory<ActiveProjectModel, String>("project_title"));
        creationDateTableCol.setCellValueFactory(new PropertyValueFactory<ActiveProjectModel, String>("creation_timestamp"));
        lastChangedDateTableCol.setCellValueFactory(new PropertyValueFactory<ActiveProjectModel, String>("last_changed_timestamp"));

        try {
            readAllActiveProjectsFromDatabaseToObservableList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        activeProjectListTableView.setItems(activeProjectList);
    }

    // Other methods
    public void createNewProject() throws SQLException, IOException {
        System.out.println("Creating a new project");
        showCreateProjectWindow();
    }

    public void editSelectedProject() {
        // TODO get selected table row
        System.out.println("Editing selected project");
    }

    public void archiveProject() {
        System.out.println("Archiving a project");
    }

    public void completeProject() {
        System.out.println("Completing a project");
    }

    public void copyProjectToActiveProject() {

    }

    public void copyProjectToTemplate() {

    }

    public void deleteProject() {
        System.out.println("Deleting a project");
        PopupControl popupControl = new PopupControl();
        // TODO verify selection and deletion in a pop-up.
    }

    private void showCreateProjectWindow() {
        ProjectDetailsWindowView projectDetailsWindowView = new ProjectDetailsWindowView();
        ProjectDetailsWindowPresenter projectDetailsWindowPresenter = (ProjectDetailsWindowPresenter) projectDetailsWindowView.getPresenter();
        StageUtils.createChildStage("Enter Project Details", projectDetailsWindowView.getView());
        // TODO Get the project data and push it to the observable list once the input pop-up closes.
        StageUtils.showAndWaitOnSubStage();
        ActiveProjectModel tempActiveProjectModel = projectDetailsWindowPresenter.getSelectedActiveProjectModel();
        if (tempActiveProjectModel != null) {
            activeProjectList.add(tempActiveProjectModel);
        }
        StageUtils.closeSubStage();
    }

    private void readAllActiveProjectsFromDatabaseToObservableList() throws SQLException {
        JdbcConnectionSource connectionSource = DatabaseUtils.getConnectionSource();
        activeProjectModelDao = DaoManager.createDao(connectionSource, ActiveProjectModel.class);
        long rowCount = activeProjectModelDao.countOf();
        if (rowCount > 0) {
            for (ActiveProjectModel activeProjectModel : activeProjectModelDao) {
                activeProjectList.add(activeProjectModel);
            }
        }
    }

}
