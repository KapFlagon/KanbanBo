package view.screens.startscreen.subviews.recentdbfileitemview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class RecentFileEntryPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label titleLabel;
    @FXML
    private Label pathLabel;

    // Other variables
    private Path itemPath;

    // Constructors

    // Getters & Setters
    public Path getItemPath() {
        return itemPath;
    }
    public void setItemPath(Path itemPath) {
        this.itemPath = itemPath;
        this.pathLabel.setText(itemPath.toString());
        this.titleLabel.setText(getFileNameWithoutExtension());
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // UI event methods
    public void itemSelected() {
        System.out.println("Item Selected: " + titleLabel.getText());
        // TODO push this upwards and change the scene/window
    }

    // Other methods
    private String getFileNameWithoutExtension() {
        String fileNamePath = itemPath.getFileName().toString();
        int charIndex = fileNamePath.indexOf('.');
        return fileNamePath.substring(0, charIndex);
    }

    // TODO Handle scenario if file does not actually exist.
    // TODO Give user a chance to remove an entry from the list (and the preferences, etc.).

}