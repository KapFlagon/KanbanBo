package view.screens.mainscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import view.screens.mainscreen.subviews.projectsmanagerview.ProjectsManagerPresenter;
import view.screens.mainscreen.subviews.projectsmanagerview.ProjectsManagerView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenPresenter implements Initializable {

    // JavaFx injected fields
    @FXML
    private TabPane mainScreenTabPane;
    // TODO Add toolbar for expected functions, like "home" (Projects manager tab).

    // Constructors


    // Getters and Setters
    public TabPane getMainScreenTabPane() {
        return mainScreenTabPane;
    }
    public void setMainScreenTabPane(TabPane mainScreenTabPane) {
        this.mainScreenTabPane = mainScreenTabPane;
    }


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tab tab = new Tab();
        ProjectsManagerView pmv = new ProjectsManagerView();
        ProjectsManagerPresenter pmp = (ProjectsManagerPresenter) pmv.getPresenter();
        tab.setContent(pmv.getView());
        tab.setText("Manage Projects");
        tab.setClosable(true);
        getMainScreenTabPane().getTabs().add(tab);
    }


    // Other methods
}
