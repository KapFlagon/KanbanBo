package view.sharedcomponents.popups.info;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import java.net.URL;
import java.util.ResourceBundle;


public class DatabaseCreationProgressPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressIndicator progressWheel;

    // Other variables
    private SimpleDoubleProperty progressProperty;

    // Constructors

    // Getters & Setters
    public void setProgressProperty(SimpleDoubleProperty progressProperty) {
        this.progressProperty = progressProperty;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // UI event methods

    // Other methods
    public void setProgressValue(double progress) {
        if(progress < 1) {
            progressBar.setProgress(progress);
            progressWheel.setProgress(progress);
        }
    }

}