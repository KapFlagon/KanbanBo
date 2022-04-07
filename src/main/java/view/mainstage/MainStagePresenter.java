package view.mainstage;

import com.airhacks.afterburner.views.FXMLView;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import persistence.services.RecentFilesService;
import persistence.services.UserPreferencesService;
import preferences.KanbanBoPreferences;
import utils.database.DatabaseUtils;
import view.screens.mainscreen.MainScreenPresenter;
import view.screens.mainscreen.MainScreenView;
import view.screens.startscreen.StartScreenPresenter;
import view.screens.startscreen.StartScreenView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;

public class MainStagePresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Stage mainStage;
    @FXML
    private Button myButton;

    // Other variables
    @Inject
    KanbanBoPreferences kanbanBoPreferences;
    @Inject
    UserPreferencesService userPreferencesService;
    @Inject
    RecentFilesService recentFilesService;
    //private ResourceBundle resourceBundle;
    private String startScreenTitle;
    private String mainScreenTitleBase;


    // Constructors

    // Getters & Setters
    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
        determineScene();
    }

    /*public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }*/

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startScreenTitle = resources.getString("start_screen_title");
        mainScreenTitleBase = resources.getString("main_screen_title_base");
    }

    @PostConstruct
    private void postConstructTasks() {

    }

    // UI event methods

    // Other methods
    public void setStageSizes(int minWidth, int minHeight) {
        mainStage.setMinWidth(minWidth);
        mainStage.setMinHeight(minHeight);
        mainStage.setWidth(minWidth);
        mainStage.setHeight(minHeight);
    }

    private void determineScene() {
        boolean autoLoadEnabled = userPreferencesService.getAutoOpenMostRecentFileValue();

        if (recentFilesService.getRecentFilePaths().size() > 0) {
            Path mostRecentPath = recentFilesService.getRecentFilePaths().get(0);
            File mostRecentFile = mostRecentPath.toFile();
            if(autoLoadEnabled && mostRecentFile.exists()) {
                navigateToMainScreen(mostRecentFile);
            } else {
                navigateToStartScreen();
            }
        } else {
            navigateToStartScreen();
        }
    }

    private Scene generateScene(FXMLView newView) {
        Scene newViewScene = new Scene(newView.getView());
        newViewScene.setFill(Color.BLACK);
        return newViewScene;
    }

    private void navigateToStartScreen() {
        StartScreenView startScreenView = new StartScreenView();
        StartScreenPresenter startScreenPresenter = (StartScreenPresenter) startScreenView.getPresenter();
        startScreenPresenter.fileSelectedProperty().addListener(generatePathSelectedChangeListener(startScreenPresenter.getSelectedPath()));
        Scene scene = generateScene(startScreenView);
        updateMainStageWithScene(startScreenTitle, scene);
    }

    private void navigateToMainScreen(File mostRecentFile) {
        DatabaseUtils.setActiveDatabaseFile(mostRecentFile);
        MainScreenView mainScreenView = new MainScreenView();
        MainScreenPresenter mainScreenPresenter = (MainScreenPresenter) mainScreenView.getPresenter();
        mainScreenPresenter.closingFileProperty().addListener(generateFileClosedChangeListener());
        Scene scene = generateScene(mainScreenView);
        String titleWithFileName = mainScreenTitleBase + mostRecentFile.getName();
        updateMainStageWithScene(titleWithFileName, scene);
    }

    private void updateMainStageWithScene(String stageTitle, Scene scene) {
        mainStage.hide();
        mainStage.setTitle(stageTitle);
        mainStage.setScene(scene);
        //mainStage.setMinHeight(500);
        //mainStage.setHeight(500);
        //mainStage.setMinWidth(700);
        //mainStage.setWidth(700);
        mainStage.show();
    }

    private ChangeListener<Boolean> generatePathSelectedChangeListener(Path selectedPath) {
        return (observable, oldValue, newValue) -> {
            if(newValue) {
                try {
                    recentFilesService.addRecentFilePath(selectedPath);
                    File selectedFile = selectedPath.toFile();
                    navigateToMainScreen(selectedFile);
                } catch (BackingStoreException e) {
                    //TODO Respond with pop-up here...
                    e.printStackTrace();
                }
            }
        };
    }

    private ChangeListener<Boolean> generateFileClosedChangeListener() {
        return ((observable, oldValue, newValue) -> {
            if(newValue) {
                navigateToStartScreen();
            }
        });
    }


}