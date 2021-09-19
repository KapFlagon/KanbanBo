package view.screens.mainscreen.subviews.workspace;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import domain.activerecords.project.ProjectActiveRecord;
import persistence.services.legacy.ProjectRepositoryService;
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
    private ObservableList<ProjectActiveRecord> openedProjectsViewModel;
    private ProjectRepositoryService projectRepositoryService;
    private SelectionModel tabPaneSelectionModel;

    // Constructors

    // Getters & Setters
    public void setProjectRepositoryService(ProjectRepositoryService projectRepositoryService) {
        this.projectRepositoryService = projectRepositoryService;
        openedProjectsViewModel = projectRepositoryService.getOpenedActiveProjects();
        customInit();
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPaneSelectionModel = workspaceTabPane.getSelectionModel();
    }

    public void customInit() {
        openedProjectsViewModel.addListener(new ListChangeListener<ProjectActiveRecord>() {
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
                        // TODO Investigate use of getViewAsync here and further down the chain
                        //Pane testPane = new Pane();
                        //pcv.getViewAsync(testPane.getChildren()::add);
                        //tab.setContent(testPane);
                        workspaceTabPane.getTabs().add(tab);
                        tab.textProperty().bind(Bindings.concat("Project: ").concat(par1.projectTitleProperty()));
                        //tab.setText("Project '" + par1.getProjectTitle() + "'");
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
                                    openedProjectsViewModel.remove(index);
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
        if (openedProjectsViewModel.size() < 1) {
            emptyWorkspaceLbl.setDisable(false);
            emptyWorkspaceLbl.setVisible(true);
        } else {
            emptyWorkspaceLbl.setDisable(true);
            emptyWorkspaceLbl.setVisible(false);
        }
    }

}