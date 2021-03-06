package view.screens.mainscreen;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.activerecords.project.ProjectActiveRecord;
import model.domainobjects.project.ProjectModel;
import model.repositories.services.ProjectRepositoryService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.manage.subviews.projectsmanagerview.ProjectsManagerPresenter;
import view.screens.mainscreen.subviews.manage.subviews.projectsmanagerview.ProjectsManagerView;
import view.screens.mainscreen.subviews.workspace.WorkspacePresenter;
import view.screens.mainscreen.subviews.workspace.WorkspaceView;
import view.screens.startscreen.StartScreenView;

import java.io.IOException;
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

    // Other variables and fields
    private ProjectRepositoryService projectRepositoryService;
    private ProjectsManagerView projectsManagerView;
    private ProjectsManagerPresenter projectsManagerPresenter;
    private WorkspaceView workspaceView;
    private WorkspacePresenter workspacePresenter;

    // Constructors


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //StageUtils.getMainStage().setTitle("KanbanBo - Database file selection");
        try {
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

        projectsManagerView = new ProjectsManagerView();
        projectsManagerPresenter = (ProjectsManagerPresenter) projectsManagerView.getPresenter();
        projectsManagerPresenter.setProjectRepositoryService(projectRepositoryService);
        //manageProjectsSubTab.setContent(projectsManagerView.getView());
        manageTab.setContent(projectsManagerView.getView());

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


    // Other methods
}
