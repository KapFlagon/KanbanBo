package view.screens.startscreen.subviews.recentfileentry;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;

public class RecentFileEntryPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox itemVBox;
    @FXML
    private Label titleLabel;
    @FXML
    private Label pathLabel;
    @FXML
    private Label pathStatusLbl;
    @FXML
    private MenuItem openItemMenuItem;
    @FXML
    private MenuItem removeItemMenuItem;
    @FXML
    private MenuItem deleteFileMenuItem;
    @FXML
    private MenuItem findMissingFileBtn;

    // Other variables
    private Path itemPath;
    private SimpleBooleanProperty fileExists;
    private SimpleBooleanProperty beingDeleted;
    private SimpleBooleanProperty beingRemoved;
    private SimpleBooleanProperty beingSelected;
    private SimpleBooleanProperty beingLocated;
    private SimpleBooleanProperty modalDialogShowing;

    // Constructors

    // Getters & Setters
    public Path getItemPath() {
        return itemPath;
    }
    public void setItemPath(Path itemPath) {
        this.itemPath = itemPath;
        validatePathAsFile();
        this.pathLabel.setText(itemPath.toString());
        this.pathLabel.setTooltip((new Tooltip(itemPath.toString())));
        this.titleLabel.setText(getFileNameWithoutExtension());
        this.titleLabel.setTooltip(new Tooltip(getFileNameWithoutExtension()));
    }

    public boolean isBeingDeleted() {
        return beingDeleted.get();
    }
    public SimpleBooleanProperty beingDeletedProperty() {
        return beingDeleted;
    }
    public void setBeingDeleted(boolean beingDeleted) {
        this.beingDeleted.set(beingDeleted);
    }

    public boolean isBeingRemoved() {
        return beingRemoved.get();
    }
    public SimpleBooleanProperty beingRemovedProperty() {
        return beingRemoved;
    }
    public void setBeingRemoved(boolean beingRemoved) {
        this.beingRemoved.set(beingRemoved);
    }

    public boolean isBeingSelected() {
        return beingSelected.get();
    }
    public SimpleBooleanProperty beingSelectedProperty() {
        return beingSelected;
    }
    public void setBeingSelected(boolean beingSelected) {
        this.beingSelected.set(beingSelected);
    }

    public SimpleBooleanProperty beingLocatedProperty() {
        return beingLocated;
    }

    public SimpleBooleanProperty modalDialogShowingProperty() {
        return modalDialogShowing;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        beingDeleted = new SimpleBooleanProperty(false);
        beingRemoved = new SimpleBooleanProperty(false);
        beingSelected = new SimpleBooleanProperty(false);
        beingLocated = new SimpleBooleanProperty(false);
        fileExists = new SimpleBooleanProperty(false);
        modalDialogShowing = new SimpleBooleanProperty(false);
        pathStatusLbl.visibleProperty().bind(fileExists.not());
        pathStatusLbl.disableProperty().bind(fileExists);
        openItemMenuItem.visibleProperty().bind(fileExists);
        openItemMenuItem.disableProperty().bind(fileExists.not());
        deleteFileMenuItem.visibleProperty().bind(fileExists);
        deleteFileMenuItem.disableProperty().bind(fileExists.not());
        findMissingFileBtn.visibleProperty().bind(fileExists.not());
        findMissingFileBtn.disableProperty().bind(fileExists);
        this.anchorPane.getStyleClass().remove("recent-item");
        this.anchorPane.getStyleClass().add("recent-item-missing");
        fileExists.addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                this.anchorPane.getStyleClass().remove("recent-item-missing");
                this.anchorPane.getStyleClass().add("recent-item");
            } else {
                this.anchorPane.getStyleClass().remove("recent-item");
                this.anchorPane.getStyleClass().add("recent-item-missing");
            }
        });
    }

    // UI event methods
    @FXML private void itemSelected() throws BackingStoreException {
        validatePathAsFile();
        if(fileExists.get()) {
            setBeingSelected(true);
        } else {
            // TODO maybe some logging here for error, or an alert.
        }
    }

    @FXML private void openItem() throws BackingStoreException {
        itemSelected();
    }

    @FXML private void removeItem() {
        setBeingRemoved(true);
    }

    @FXML private void deleteFile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the Database file?");
        alert.setTitle("Confirm database Deletion");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Delete");
        alert.setHeaderText("Delete Database?");
        modalDialogShowing.set(true);
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent((response)
                -> {
            System.out.println("Deleting the Database file");
            setBeingDeleted(true);
        });
        modalDialogShowing.set(false);
    }

    @FXML private void findFile() {
        beingLocated.set(true);
    }

    // Other methods

    private void validatePathAsFile(){
        File tempFile = itemPath.toFile();
        if(tempFile.exists()) {
            fileExists.set(true);
        } else {
            fileExists.set(false);
        }
    }

    private String getFileNameWithoutExtension() {
        String fileNamePath = itemPath.getFileName().toString();
        int charIndex = fileNamePath.indexOf('.');
        return fileNamePath.substring(0, charIndex);
    }

}