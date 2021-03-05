package view.screens.startscreen;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import utils.FileChooserUtils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenPresenter implements Initializable {

    // Variables
    @FXML
    Button newDbButton;

    @FXML
    Button openDbButton;

    @FXML
    MenuItem newDbMenuItem;

    @FXML
    MenuItem openDbMenuItem;

    @FXML
    MenuItem exitMenuItem;

    @FXML
    MenuItem aboutMenuItem;

    @FXML
    MenuItem repoMenuItem;

    // Constructors


    // Getters and Setters



    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    // Other methods
    public void createDbFile() {
        System.out.println("creating");
        File createdFile = FileChooserUtils.createFilePopup();
        // TODO Insert some kind of logging for creating file here.
    }


    public void openDbFile() {
        System.out.println("opening");
        File selectedFile = FileChooserUtils.openFilePopup();
        // TODO Insert some kind of logging for selected file here.
    }


    public void exitApplication() {
        System.out.println("exiting");
        Platform.exit();
        System.exit(0);
    }


    public void displayApplicationInfo() {
        System.out.println("Info");
    }


    public void openOnlineCodeRepo() {
        System.out.println("repo");
    }

}
