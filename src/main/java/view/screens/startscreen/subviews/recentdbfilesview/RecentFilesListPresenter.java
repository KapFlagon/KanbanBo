package view.screens.startscreen.subviews.recentdbfilesview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import user.preferences.UserPreferences;
import view.screens.startscreen.subviews.recentdbfileitemview.RecentFileEntryPresenter;
import view.screens.startscreen.subviews.recentdbfileitemview.RecentFileEntryView;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class RecentFilesListPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private VBox recentFilesVBox;

    // Other variables
    private List<Path> recentFilePathList;

    // Constructors

    // Getters & Setters
    public List<Path> getRecentFilePathList() {
        return recentFilePathList;
    }
    public void setRecentFilePathList(List<Path> recentFilePathList) {
        this.recentFilePathList = recentFilePathList;
        createFileEntriesInView();
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // UI event methods

    // Other methods
    private void createFileEntriesInView() {
        if (recentFilePathList.size() > 0) {
            for (Path pathEntry : recentFilePathList) {
                RecentFileEntryView view = new RecentFileEntryView();
                RecentFileEntryPresenter presenter = (RecentFileEntryPresenter) view.getPresenter();
                presenter.setItemPath(pathEntry);
                recentFilesVBox.getChildren().add(view.getView());
            }
        } else {
            Label label = new Label("No recent database files found");
            recentFilesVBox.getChildren().add(label);
        }
    }

    // TODO if item is removed, push change to the UI

}