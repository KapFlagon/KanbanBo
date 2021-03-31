package view.screens.mainscreen.subviews.workspace;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.activerecords.ProjectActiveRecord;
import model.domainobjects.project.ActiveProjectModel;
import model.repositories.ProjectRepositoryService;
import view.screens.mainscreen.subviews.workspace.subviews.projectcontainer.ProjectContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.projectcontainer.ProjectContainerView;

import java.net.URL;
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

    }

    public void customInit() {
        projectRepositoryService.getOpenedActiveProjects().addListener(new ListChangeListener<ProjectActiveRecord<ActiveProjectModel>>() {
            @Override
            public void onChanged(Change<? extends ProjectActiveRecord<ActiveProjectModel>> c) {
                c.next();
                if (c.wasAdded()) {
                    System.out.println("Change detected");
                    Tab tab = new Tab();
                    ProjectContainerView pcv = new ProjectContainerView();
                    ProjectContainerPresenter pcp = (ProjectContainerPresenter) pcv.getPresenter();
                    for (ProjectActiveRecord par : c.getAddedSubList()) {
                        pcp.setActiveRecord(par);
                        tab.setContent(pcv.getView());
                        workspaceTabPane.getTabs().add(tab);
                        tab.setText("Project '" + par.getProjectTitle() + "'");
                        tab.setClosable(true);
                    }
                }
            }
        });
    }

    // UI event methods


    // Other methods


}