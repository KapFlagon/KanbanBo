package view.screens.mainscreen.subviews.workspace;

import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.activerecords.project.ProjectActiveRecord;
import model.domainobjects.project.ProjectModel;
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
    private BorderPane borderPane;
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
        emptyWorkspaceLbl = new Label("No Projects opened in workspace...");
        emptyWorkspaceLbl.setPadding(new Insets(5,5,5,5));
        BorderPane.setMargin(emptyWorkspaceLbl, new Insets(5,5,5,5));
        tabPaneSelectionModel = workspaceTabPane.getSelectionModel();
    }

    public void customInit() {
        projectRepositoryService.getOpenedActiveProjects().addListener(new ListChangeListener<ProjectActiveRecord>() {
            @Override
            public void onChanged(Change<? extends ProjectActiveRecord> c) {
                c.next();
                if (c.wasAdded()) {
                    System.out.println("Change detected, new project opened");
                    Tab tab = new Tab();
                    ProjectContainerView pcv = new ProjectContainerView();
                    ProjectContainerPresenter pcp = (ProjectContainerPresenter) pcv.getPresenter();
                    for (ProjectActiveRecord par1 : c.getAddedSubList()) {
                        try {
                            pcp.setProjectActiveRecord(par1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        tab.setContent(pcv.getView());
                        workspaceTabPane.getTabs().add(tab);
                        tab.setText("Project '" + par1.getProjectTitle() + "'");
                        tab.setClosable(true);
                        updatePlaceholderLabel();
                        // TODO refactor this into another method or something. 
                        tab.setOnClosed(new EventHandler<Event>() {
                            @Override
                            public void handle(Event event) {
                                System.out.println("workspace project tab is being closed");
                                int index = -1;
                                for (int innerIterator = 0; innerIterator < (c.getAddedSubList().size()); innerIterator++) {
                                    ProjectActiveRecord par2 = c.getAddedSubList().get(innerIterator);
                                    if (par1.getProjectUUID().equals(par2.getProjectUUID())) {
                                        index = innerIterator;
                                    }
                                }
                                if (index != -1) {
                                    projectRepositoryService.getOpenedActiveProjects().remove(index);
                                    System.out.println("workspace project tab is finally closed");
                                    updatePlaceholderLabel();
                                }
                            }
                        });
                        tabPaneSelectionModel.select(tab);
                    }
                }
            }
        });
        updatePlaceholderLabel();
    }

    // UI event methods


    // Other methods
    private void updatePlaceholderLabel() {
        if (projectRepositoryService.getOpenedActiveProjects().size() < 1) {
            borderPane.setTop(emptyWorkspaceLbl);
            //emptyWorkspaceLbl.setVisible(true);
        } else {
            borderPane.setTop(null);
            //emptyWorkspaceLbl.setVisible(false);
        }
    }

}