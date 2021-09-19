package view.screens.startscreen.subviews.recentdbfilesview;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import userpreferences.UserPreferences;
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
    private ObservableList<Path> recentFilePathList;
    private Path selectedPath;
    private SimpleBooleanProperty itemBeingOpened;
    private SimpleBooleanProperty itemBeingDeleted;

    // Constructors

    // Getters & Setters
    public List<Path> getRecentFilePathList() {
        return recentFilePathList;
    }
    public void setRecentFilePathList(ObservableList<Path> recentFilePathList) {
        this.recentFilePathList = recentFilePathList;
        createFileEntriesInView();
    }

    public Path getSelectedPath() {
        return selectedPath;
    }
    public void setSelectedPath(Path selectedPath) {
        this.selectedPath = selectedPath;
    }

    public boolean isItemBeingOpened() {
        return itemBeingOpened.get();
    }
    public SimpleBooleanProperty itemBeingOpenedProperty() {
        return itemBeingOpened;
    }
    public void setItemBeingOpened(boolean itemBeingOpened) {
        this.itemBeingOpened.set(itemBeingOpened);
    }

    public boolean isItemBeingDeleted() {
        return itemBeingDeleted.get();
    }
    public SimpleBooleanProperty itemBeingDeletedProperty() {
        return itemBeingDeleted;
    }
    public void setItemBeingDeleted(boolean itemBeingDeleted) {
        this.itemBeingDeleted.set(itemBeingDeleted);
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemBeingOpened = new SimpleBooleanProperty(false);
        itemBeingDeleted = new SimpleBooleanProperty(false);
    }

    // UI event methods

    // Other methods
    private void createFileEntriesInView() {
        if (recentFilePathList.size() > 0) {
            for (Path pathEntry : recentFilePathList) {
                RecentFileEntryView view = new RecentFileEntryView();
                RecentFileEntryPresenter presenter = (RecentFileEntryPresenter) view.getPresenter();
                presenter.setItemPath(pathEntry);
                presenter.beingDeletedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        // TODO Add logging here
                        if (newValue) {
                            selectedPath = presenter.getItemPath();
                            setItemBeingDeleted(true);
                        }
                    }
                });
                presenter.beingRemovedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        // TODO Add logging here
                        System.out.println("change detected in boolean");
                        if (newValue) {
                            UserPreferences.getSingletonInstance().removeRecentFilePath(presenter.getItemPath());
                            recentFilePathList.remove(presenter.getItemPath());
                            Platform.runLater(new Runnable() {
                                // https://stackoverflow.com/questions/38760878/javafx-endless-exception-in-thread-javafx-application-thread-java-lang-nullp
                                public void run() {
                                    recentFilesVBox.getChildren().remove(view.getView());
                                }
                            });
                        }
                    }
                });
                presenter.beingSelectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            selectedPath = presenter.getItemPath();
                            setItemBeingOpened(true);
                        }
                    }
                });
                recentFilesVBox.getChildren().add(view.getView());
            }
        } else {
            Label label = new Label("No recent database files found");
            recentFilesVBox.getChildren().add(label);
        }
    }

}