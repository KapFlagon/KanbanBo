package view.screens.mainscreen;

import domain.entities.project.ObservableProject;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import persistence.services.KanbanBoDataService;
import persistence.tables.project.ProjectTable;
import utils.StageUtils;
import view.screens.mainscreen.subviews.manage.ManagePresenter;
import view.screens.mainscreen.subviews.manage.ManageView;
import view.screens.mainscreen.subviews.workspace.WorkspacePresenter;
import view.screens.mainscreen.subviews.workspace.WorkspaceView;
import view.screens.startscreen.StartScreenView;

import javax.inject.Inject;
import java.awt.*;
import java.net.URI;
import java.net.URL;
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
    @Inject
    KanbanBoDataService kanbanBoDataService;

    private ManageView manageView;
    private ManagePresenter managePresenter;
    private WorkspaceView workspaceView;
    private WorkspacePresenter workspacePresenter;

    // Constructors


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        manageView = new ManageView();
        managePresenter = (ManagePresenter) manageView.getPresenter();
        manageTab.setContent(manageView.getView());

        workspaceView = new WorkspaceView();
        workspacePresenter = (WorkspacePresenter) workspaceView.getPresenter();
        workspaceTab.setContent(workspaceView.getView());

        kanbanBoDataService.getWorkspaceProjectsList().addListener(new ListChangeListener<ObservableProject>() {
            @Override
            public void onChanged(Change<? extends ObservableProject> c) {
                while(c.next()) {
                    if(c.wasAdded()) {
                        mainScreenTabPane.getSelectionModel().select(workspaceTab);
                    }
                    if(c.wasRemoved()) {
                        // TODO Do I need this?
                    }
                }
            }
        });
    }


    // UI events
    @FXML private void closeDb() {
        StageUtils.changeMainScene("KanbanBo - Database file selection", new StartScreenView());
    }

    @FXML private void exitProgram() {
        System.out.println("Exiting program...");
        Platform.exit();
        System.exit(0);
    }

    @FXML private void openOnlineSourceCodeRepo() {
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
