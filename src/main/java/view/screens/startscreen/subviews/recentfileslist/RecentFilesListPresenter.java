package view.screens.startscreen.subviews.recentfileslist;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import utils.images.BufferedImageSVGTranscoder;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utils.view.ScrollPaneFixer;
import view.screens.startscreen.subviews.recentfileentry.RecentFileEntryPresenter;
import view.screens.startscreen.subviews.recentfileentry.RecentFileEntryView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RecentFilesListPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private VBox promptVbox;
    @FXML
    private VBox recentFilesVBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Button createBtn;
    @FXML
    private Button openBtn;

    // Other variables
    private ObservableList<Path> recentFilePathList;
    private Path selectedPath;
    private SimpleBooleanProperty itemBeingOpened;
    private SimpleBooleanProperty itemBeingRemoved;
    private SimpleBooleanProperty itemBeingDeleted;
    private SimpleBooleanProperty itemBeingLocated;
    private SimpleBooleanProperty modalDialogShowing;

    // Constructors

    // Getters & Setters
    public ObservableList<Path> getRecentFilePathList() {
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

    public SimpleBooleanProperty itemBeingRemovedProperty() {
        return itemBeingRemoved;
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

    public SimpleBooleanProperty itemBeingLocatedProperty() {
        return itemBeingLocated;
    }

    public SimpleBooleanProperty modalDialogShowingProperty() {
        return modalDialogShowing;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemBeingOpened = new SimpleBooleanProperty(false);
        itemBeingRemoved = new SimpleBooleanProperty(false);
        itemBeingDeleted = new SimpleBooleanProperty(false);
        itemBeingLocated = new SimpleBooleanProperty(false);
        modalDialogShowing = new SimpleBooleanProperty(false);
        ScrollPaneFixer.fixBlurryScrollPan(scrollPane);
        try {
            parseSvgFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        initButtonImages();
    }

    // UI event methods

    // Other methods
    private void createFileEntriesInView() {
        recentFilesVBox.getChildren().clear();
        if (recentFilePathList.size() > 0) {
            promptVbox.setVisible(false);
            for (Path pathEntry : recentFilePathList) {
                RecentFileEntryView view = new RecentFileEntryView();
                RecentFileEntryPresenter presenter = (RecentFileEntryPresenter) view.getPresenter();
                presenter.setItemPath(pathEntry);
                presenter.beingDeletedProperty().addListener((observable, oldValue, newValue) -> {
                    // TODO Add logging here
                    if (newValue) {
                        selectedPath = presenter.getItemPath();
                        setItemBeingDeleted(true);
                        recentFilePathList.remove(presenter.getItemPath());
                    }
                });
                presenter.beingRemovedProperty().addListener((observable, oldValue, newValue) -> {
                    // TODO Add logging here
                    if (newValue) {
                        recentFilePathList.remove(presenter.getItemPath());
                    }
                });
                presenter.beingSelectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        selectedPath = presenter.getItemPath();
                        setItemBeingOpened(true);
                    }
                });
                presenter.beingLocatedProperty().addListener(((observable, oldValue, newValue) -> {
                    if (newValue) {
                        selectedPath = presenter.getItemPath();
                        itemBeingLocated.set(true);
                    }
                }));
                recentFilesVBox.getChildren().add(view.getView());
                recentFilePathList.addListener((ListChangeListener<Path>) c -> {
                    while(c.next()) {
                        if(c.wasRemoved()){
                            for(Path path : c.getRemoved()) {
                                if (path.equals(presenter.getItemPath())) {
                                    Platform.runLater(() -> recentFilesVBox.getChildren().remove(view.getView()));
                                }
                            }
                        }
                    }
                    if (recentFilePathList.size() < 1) {
                        recentFilesVBox.setVisible(false);
                        promptVbox.setVisible(true);
                    } else {
                        recentFilesVBox.setVisible(true);
                        promptVbox.setVisible(false);
                    }
                });
                modalDialogShowing.bind(presenter.modalDialogShowingProperty());
            }
        } else {
            promptVbox.setVisible(true);
        }
    }

    public void parseSvgFile() throws URISyntaxException, MalformedURLException {
        BufferedImageSVGTranscoder transcoder = new BufferedImageSVGTranscoder();
        URI uri = getClass().getResource("/svgs/illustrations/undraw_blank_canvas_re_2hwy.svg").toURI();
        InputStream inputStream = getClass().getResourceAsStream("/svgs/illustrations/undraw_blank_canvas_re_2hwy.svg");
        Map<String, String> colourMap = new HashMap<>();
        colourMap.put("baseColour", "#6C63FF");
        colourMap.put("newColour", "#5CD6C8");
        // TODO this may still not work from JAR...
        transcoder.parseSvgFileWithNewColours(imageView, uri.toURL(), inputStream, colourMap);
    }

    private void initButtonImages() {
        //TODO need to replace all "getResource()" calls with "getResourceAsStream()" to avoid JAR file issues for reading resources.
        ImageView newDbImageView = new ImageView(getClass().getResource("/icons/add/materialicons/black/res/drawable-mdpi/baseline_add_black_18.png").toExternalForm());
        ImageView searchDbImageView = new ImageView(getClass().getResource("/icons/search/materialicons/black/res/drawable-mdpi/baseline_search_black_18.png").toExternalForm());
        createBtn.setGraphic(newDbImageView);
        openBtn.setGraphic(searchDbImageView);
    }

    public void setCreateBtnAction(EventHandler<ActionEvent> action) {
        createBtn.setOnAction(action);
    }

    public void setOpenBtnAction(EventHandler<ActionEvent> action) {
        openBtn.setOnAction(action);
    }

}