package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.domainobjects.project.AbstractProjectModel;
import view.screens.mainscreen.subviews.workspace.subviews.projectview.ProjectContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.projectview.ProjectContainerView;

public class ProjectWorkspaceController {

    // JavaFX variables
    private TabPane mainScreenTabPane;
    private TabPane workspaceTabPane;      // Need to inject dependency for the workspacesubtabpane

    // Variables
    private ObservableList<AbstractProjectModel> projectDisplayList;


    // Constructors
    public ProjectWorkspaceController() {
        initProjectDisplayList();
    }


    // Getters and Setters
    public ObservableList getProjectDisplayList() {
        return projectDisplayList;
    }
    public void setProjectDisplayList(ObservableList projectDisplayList) {
        this.projectDisplayList = projectDisplayList;
    }

    public TabPane getMainScreenTabPane() {
        return mainScreenTabPane;
    }
    public void setMainScreenTabPane(TabPane mainScreenTabPane) {
        this.mainScreenTabPane = mainScreenTabPane;
    }

    public TabPane getWorkspaceTabPane() {
        return workspaceTabPane;
    }
    public void setWorkspaceTabPane(TabPane workspaceTabPane) {
        this.workspaceTabPane = workspaceTabPane;
    }

    // Initialisation methods
    private void initProjectDisplayList() {
        projectDisplayList = FXCollections.observableArrayList();
    }


    // Other methods
    private <T extends AbstractProjectModel> boolean isProjectAlreadyOpen(T t) {
        boolean uuidFound = false;
        for (AbstractProjectModel projectModel : projectDisplayList) {
            if (projectModel.getProject_uuid().equals(t.getProject_uuid())) {
                uuidFound = true;
            }
        }
        return uuidFound;
    }

    private <T extends AbstractProjectModel> void replaceItemWithLatest(T t) {
        for (AbstractProjectModel projectModel : projectDisplayList) {
            if (projectModel.getProject_uuid().equals(t.getProject_uuid())) {
                int itemIndex = projectDisplayList.indexOf(projectModel);
                // workspaceTabPane.getTabs().remove(projectModel); ? This might be wrong
                projectDisplayList.remove(itemIndex);
                projectDisplayList.add(t);
            }
        }
    }

    public <T extends AbstractProjectModel> void openProject(T t) {
        if (isProjectAlreadyOpen(t)) {
            // direct to the tab
            // Update if necessary
            replaceItemWithLatest(t);
        } else {
            // open the new tab and add to the list
            projectDisplayList.add(t);
            // Create a new tab
            // Create a project presenter and view, then add to the tab
            workspaceTabPane.getTabs();
        }

    }

    public <T extends AbstractProjectModel> void check(T t) {
        ProjectContainerView projectContainerView = new ProjectContainerView();
        ProjectContainerPresenter projectContainerPresenter = (ProjectContainerPresenter) projectContainerView.getPresenter();
        //projectContainerPresenter.setProjectModel(t);
        Tab tab = new Tab();
        tab.setContent(projectContainerView.getView());
    }

    private void openProjectInWorkspaceTab(Tab tab) {
        mainScreenTabPane.getSelectionModel().selectLast();     // Select "workspace" tab, stupidly
        workspaceTabPane.getTabs().add(tab);
        workspaceTabPane.getSelectionModel().select(tab);
    }
}
