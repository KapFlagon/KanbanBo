package view.screens.mainscreen;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import domain.activerecords.project.ProjectActiveRecord;
import persistence.tables.project.ProjectTable;
import persistence.repositories.project.ORMLiteProjectRepository;
import persistence.repositories.project.ORMLiteProjectStatusRepository;
import persistence.services.latest.ProjectManagementService;
import persistence.services.legacy.ProjectRepositoryService;
import persistence.services.latest.WorkspaceService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.manage.ManagePresenter;
import view.screens.mainscreen.subviews.manage.ManageView;
import view.screens.mainscreen.subviews.workspace.WorkspacePresenter;
import view.screens.mainscreen.subviews.workspace.WorkspaceView;
import view.screens.startscreen.StartScreenView;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainScreenPresenter implements Initializable {

    // JavaFx injected fields
    @FXML
    private TabPane mainScreenTabPane;
    @FXML
    private Tab manageTab;
    @FXML
    private Tab manageProjectsSubTab;
    @FXML
    private Tab manageTemplatesSubTab;
    @FXML
    private Tab templatesTab;
    @FXML
    private Tab analyticsTab;
    @FXML
    private TabPane analyticsSubTabPane;
    @FXML
    private Tab workspaceTab;
    @FXML
    private MenuItem sourceCodeRepoMenuItem;

    // Other variables and fields
    private ProjectManagementService projectManagementService;
    private WorkspaceService workspaceService;
    private ProjectRepositoryService projectRepositoryService;

    private ManageView manageView;
    private ManagePresenter managePresenter;
    private WorkspaceView workspaceView;
    private WorkspacePresenter workspacePresenter;

    private ObservableList<ProjectTable> openedProjects;

    // Constructors


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            workspaceService = new WorkspaceService();
            projectRepositoryService = new ProjectRepositoryService();
            projectRepositoryService.getOpenedActiveProjects().addListener(new ListChangeListener<ProjectActiveRecord>() {
                @Override
                public void onChanged(Change<? extends ProjectActiveRecord> c) {
                    c.next();
                    if (c.wasAdded()) {
                        System.out.println("Change detected in main screen");
                        mainScreenTabPane.getSelectionModel().select(workspaceTab);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        manageView = new ManageView();
        managePresenter = (ManagePresenter) manageView.getPresenter();
        managePresenter.setProjectRepositoryService(projectRepositoryService);
        //manageProjectsSubTab.setContent(projectsManagerView.getView());
        manageTab.setContent(manageView.getView());

        workspaceView = new WorkspaceView();
        workspacePresenter = (WorkspacePresenter) workspaceView.getPresenter();
        workspacePresenter.setProjectRepositoryService(projectRepositoryService);
        workspaceTab.setContent(workspaceView.getView());
    }


    // UI events
    public void closeDb() {
        StageUtils.changeMainScene("KanbanBo - Database file selection", new StartScreenView());
    }

    public void exitProgram() {
        System.out.println("Exiting program...");
        Platform.exit();
        System.exit(0);
    }

    public void openOnlineSourceCodeRepo() {
        //System.out.println("Redirect to online repository");
        Desktop dt = Desktop.getDesktop();
        try {
            dt.browse(new URI("http://www.github.com/kapflagon/kanbanbo"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    // Other methods
}
