package view.screens.mainscreen.subviews.workspace;

import domain.entities.project.ObservableProject;
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
import persistence.services.KanbanBoDataService;
import view.components.project.container.ProjectContainerPresenter;
import view.components.project.container.ProjectContainerView;

import javax.inject.Inject;
import javafx.scene.image.ImageView;
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
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableList<ObservableProject> openedProjectsViewModel;
    private SelectionModel tabPaneSelectionModel;

    // Constructors

    // Getters & Setters


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.openedProjectsViewModel = kanbanBoDataService.getWorkspaceProjectsList();
        tabPaneSelectionModel = workspaceTabPane.getSelectionModel();
        openedProjectsViewModel.addListener(new ListChangeListener<ObservableProject>() {
            @Override
            public void onChanged(Change<? extends ObservableProject> c) {
                Tab tab = new Tab();
                ImageView projectImageView = new ImageView(getClass().getResource("/icons/topic/materialiconsoutlined/black/res/drawable-hdpi/outline_topic_black_18.png").toExternalForm());
                tab.setGraphic(projectImageView);
                while(c.next()) {
                    if (c.wasAdded()) {
                        System.out.println("Change detected, new project opened");
                        ProjectContainerView projectContainerView = new ProjectContainerView();
                        ProjectContainerPresenter projectContainerPresenter = (ProjectContainerPresenter) projectContainerView.getPresenter();
                        for (ObservableProject observableProject : c.getAddedSubList()) {
                            try {
                                projectContainerPresenter.setProjectViewModel(observableProject);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            tab.setContent(projectContainerView.getView());
                            // TODO Investigate use of getViewAsync here and further down the chain
                            workspaceTabPane.getTabs().add(tab);
                            tab.textProperty().bind(observableProject.projectTitleProperty());
                            //tab.setText("Project '" + observableProject.getProjectTitle() + "'");
                            tab.setClosable(true);
                            updatePlaceholderLabel();
                            // TODO refactor this into another method or something.
                            tab.setOnClosed(new EventHandler<Event>() {
                                @Override
                                public void handle(Event event) {
                                    System.out.println("workspace project tab is being closed");
                                    int index = -1;
                                    for (int innerIterator = 0; innerIterator < (c.getAddedSubList().size()); innerIterator++) {
                                        ObservableProject par2 = c.getAddedSubList().get(innerIterator);
                                        if (observableProject.getProjectUUID().equals(par2.getProjectUUID())) {
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
                    if(c.wasRemoved()) {
                        // TODO need to resume here to ensure a project can be only opened once.
                        workspaceTabPane.getTabs().remove(tab);
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