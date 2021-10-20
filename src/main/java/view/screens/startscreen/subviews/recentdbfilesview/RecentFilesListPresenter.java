package view.screens.startscreen.subviews.recentdbfilesview;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import userpreferences.UserPreferences;
import utils.view.ScrollPaneFixer;
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
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label noDatabasesFileLbl;

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
        ScrollPaneFixer.fixBlurryScrollPan(scrollPane);
    }

    // UI event methods

    // Other methods
    private void createFileEntriesInView() {
        if (recentFilePathList.size() > 0) {
            for (Path pathEntry : recentFilePathList) {
                RecentFileEntryView view = new RecentFileEntryView();
                RecentFileEntryPresenter presenter = (RecentFileEntryPresenter) view.getPresenter();
                presenter.setItemPath(pathEntry);
                presenter.beingDeletedProperty().addListener((observable, oldValue, newValue) -> {
                    // TODO Add logging here
                    if (newValue) {
                        selectedPath = presenter.getItemPath();
                        setItemBeingDeleted(true);
                        setItemBeingDeleted(false);
                    }
                });
                presenter.beingRemovedProperty().addListener((observable, oldValue, newValue) -> {
                    // TODO Add logging here
                    System.out.println("change detected in boolean");
                    if (newValue) {
                        UserPreferences.getSingletonInstance().removeRecentFilePath(presenter.getItemPath());
                        recentFilePathList.remove(presenter.getItemPath());
                    }
                });
                presenter.beingSelectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        selectedPath = presenter.getItemPath();
                        setItemBeingOpened(true);
                    }
                });
                recentFilesVBox.getChildren().add(view.getView());
                recentFilesVBox.getChildren().remove(noDatabasesFileLbl);
                recentFilePathList.addListener((ListChangeListener<Path>) c -> {
                    while(c.next()) {
                        if(c.wasRemoved()){
                            for(Path path : c.getRemoved()) {
                                if (path.equals(presenter.getItemPath())) {
                                    // https://stackoverflow.com/questions/38760878/javafx-endless-exception-in-thread-javafx-application-thread-java-lang-nullp
                                    Platform.runLater(() -> recentFilesVBox.getChildren().remove(view.getView()));
                                    recentFilesVBox.getChildren().add(noDatabasesFileLbl);
                                    // TODO fix bug where deleting files does not properly remove them from the view on reload.
                                }
                            }
                        }
                    }
                });
            }
        }
    }

}