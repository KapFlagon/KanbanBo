package view.sharedviewcomponents.popups.info;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import utils.StageUtils;
import utils.database.DatabaseCreator;

import java.net.URL;
import java.util.ResourceBundle;


public class DatabaseCreationProgressPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressIndicator progressWheel;
    @FXML
    private Label generatedContentLbl;

    // Other variables
    private SimpleBooleanProperty completed;

    // Constructors

    // Getters & Setters
    /*public void setProgressProperty(SimpleDoubleProperty progressProperty) {
        this.progressProperty = progressProperty;
    }
     */

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        completed = new SimpleBooleanProperty(false);
        //completed.addListener();
        DatabaseCreator dbCreator = new DatabaseCreator();
        dbCreator.getDatabaseCreationTask().setOnSucceeded(workerStateEvent -> {
            System.out.println("\tprogress succeeded");
            StageUtils.closeSubStage();
        });
        dbCreator.getDatabaseCreationTask().setOnRunning(workerStateEvent -> {System.out.println("\trunning");});
        dbCreator.getDatabaseCreationTask().setOnScheduled(workerStateEvent -> {System.out.println("\tscheduled");});
        dbCreator.getDatabaseCreationTask().setOnFailed(workerStateEvent -> {System.out.println("\tfailed");});
        dbCreator.getDatabaseCreationTask().setOnCancelled(workerStateEvent -> {System.out.println("\tcancelled");});
        progressBar.progressProperty().bind(dbCreator.getDatabaseCreationTask().progressProperty());
        progressWheel.progressProperty().bind(dbCreator.getDatabaseCreationTask().progressProperty());
        generatedContentLbl.textProperty().bind(dbCreator.getDatabaseCreationTask().messageProperty());
        dbCreator.startDbCreation();

    }

    // UI event methods

    // Other methods


}