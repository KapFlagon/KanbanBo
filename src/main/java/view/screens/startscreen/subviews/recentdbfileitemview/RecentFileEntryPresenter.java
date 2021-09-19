package view.screens.startscreen.subviews.recentdbfileitemview;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;

public class RecentFileEntryPresenter implements Initializable {

    // JavaFX injected node variables
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

    // Other variables
    private Path itemPath;
    private boolean fileExists;
    private SimpleBooleanProperty beingDeleted;
    private SimpleBooleanProperty beingRemoved;
    private SimpleBooleanProperty beingSelected;

    // Constructors

    // Getters & Setters
    public Path getItemPath() {
        return itemPath;
    }
    public void setItemPath(Path itemPath) {
        this.itemPath = itemPath;
        validatePathAsFile();
        if (fileExists) {
            pathStatusLbl.setText("File exists");
        } else {
            pathStatusLbl.setText("File does not exist!");
            openItemMenuItem.setVisible(false);
            openItemMenuItem.setDisable(true);
            deleteFileMenuItem.setVisible(false);
            deleteFileMenuItem.setDisable(true);
        }
        this.pathLabel.setText(itemPath.toString());
        this.titleLabel.setText(getFileNameWithoutExtension());
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

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        beingDeleted = new SimpleBooleanProperty(false);
        beingRemoved = new SimpleBooleanProperty(false);
        beingSelected = new SimpleBooleanProperty(false);
    }

    // UI event methods
    @FXML private void itemSelected() throws BackingStoreException {
        String validationState;
        if(fileExists) {
            validationState = "and file exists";
            setBeingSelected(true);
        } else {
            validationState = "and file doesn't exist";
        }
        System.out.println("Item Selected: " + titleLabel.getText() + " , " + validationState);
    }

    @FXML private void openItem() throws BackingStoreException {
        System.out.println("Opening the selected item");
        itemSelected();
    }

    @FXML private void removeItem() {
        System.out.println("Removing the selected item from the list");
        setBeingRemoved(true);
    }

    @FXML private void deleteFile() {
        System.out.println("Deleting the file entirely");
        setBeingDeleted(true);
    }

    // Other methods
    private void validatePathAsFile(){
        File tempFile = itemPath.toFile();
        if(tempFile.exists()) {
            fileExists = true;
        } else {
            fileExists = false;
        }
    }

    private String getFileNameWithoutExtension() {
        String fileNamePath = itemPath.getFileName().toString();
        int charIndex = fileNamePath.indexOf('.');
        return fileNamePath.substring(0, charIndex);
    }

}