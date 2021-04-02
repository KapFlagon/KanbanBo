package view.screens.mainscreen.subviews.workspace;

import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.activerecords.ProjectActiveRecord;
import model.domainobjects.project.ActiveProjectModel;
import model.repositories.services.ProjectRepositoryService;
import view.screens.mainscreen.subviews.workspace.subviews.projectcontainer.ProjectContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.projectcontainer.ProjectContainerView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WorkspacePresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private TabPane workspaceTabPane;
    @FXML
    private Label emptyWorkspaceLbl;

    // Other variables
    private enum ProjectType {ACTIVE, ARCHIVED, COMPLETED, TEMPLATE}
    private ProjectRepositoryService projectRepositoryService;
    private SelectionModel tabPaneSelectionModel;

    // Constructors

    // Getters & Setters
    public ProjectRepositoryService getProjectRepositoryService() {
        return projectRepositoryService;
    }
    public void setProjectRepositoryService(ProjectRepositoryService projectRepositoryService) {
        this.projectRepositoryService = projectRepositoryService;
        customInit();
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPaneSelectionModel = workspaceTabPane.getSelectionModel();
    }

    public void customInit() {
        projectRepositoryService.getOpenedActiveProjects().addListener(new ListChangeListener<ProjectActiveRecord<ActiveProjectModel>>() {
            @Override
            public void onChanged(Change<? extends ProjectActiveRecord<ActiveProjectModel>> c) {
                c.next();
                if (c.wasAdded()) {
                    System.out.println("Change detected, new project opened");
                    Tab tab = new Tab();
                    ProjectContainerView pcv = new ProjectContainerView();
                    ProjectContainerPresenter pcp = (ProjectContainerPresenter) pcv.getPresenter();
                    for (ProjectActiveRecord par : c.getAddedSubList()) {
                        try {
                            pcp.setProjectActiveRecord(par);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        tab.setContent(pcv.getView());
                        workspaceTabPane.getTabs().add(tab);
                        tab.setText("Project '" + par.getProjectTitle() + "'");
                        tab.setClosable(true);
                        tab.setOnClosed(new EventHandler<Event>() {
                            @Override
                            public void handle(Event event) {
                                // TODO remove entry from the displayed projects list, need to examine this for bugs
                                System.out.println("workspace project tab is being closed");
                                projectRepositoryService.getOpenedActiveProjects().remove(c);
                            }
                        });
                        tabPaneSelectionModel.select(tab);
                    }
                }
            }
        });
    }

    // UI event methods


    // Other methods


}