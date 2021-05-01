package driver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.preferences.UserPreferences;
import utils.StageUtils;
import utils.database.DatabaseUtils;
import view.screens.mainscreen.MainScreenView;
import view.screens.startscreen.StartScreenView;

import java.io.File;
import java.nio.file.Path;


public class KanbanBoApp extends Application {

    private int appMinHeight = 400;
    private int appMinWidth = 600;
    private Scene currentScene;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        StageUtils.setMainStage(primaryStage);
        setStageSizes(primaryStage);
        //StartScreenView startScreenView = new StartScreenView();
        //currentScene = new Scene(startScreenView.getView());
        determineScene();
        //primaryStage.setScene(currentScene);
        //primaryStage.show();
    }

    public void setStageSizes(Stage primaryStage) {
        primaryStage.setWidth(appMinWidth);
        primaryStage.setMinWidth(appMinWidth);
        primaryStage.setHeight(appMinHeight);
        primaryStage.setMinHeight(appMinHeight);
    }

    public void determineScene () {
        boolean autoLoadOn = UserPreferences.getSingletonInstance().isOpeningMostRecentAutomatically();
        Path mostRecentPath = UserPreferences.getSingletonInstance().getMostRecentPath();
        if (mostRecentPath != null) {
            File mostRecentFile = mostRecentPath.toFile();
            if(autoLoadOn && mostRecentFile.exists()) {
                // Get latest path from preferences, check if it is valid, update to main screen
                DatabaseUtils.setActiveDatabaseFile(mostRecentFile);
                MainScreenView mainScreenView = new MainScreenView();
                //StageUtils.changeMainScene("KanbanBo - Project manager", view);
                StageUtils.changeMainScene("KanbanBo - Project manager", mainScreenView);
                //currentScene = new Scene((mainScreenView.getView()));
            } else {
                StartScreenView startScreenView = new StartScreenView();
                StageUtils.changeMainScene("KanbanBo - Database file selection", startScreenView);
            }
        } else {
            StartScreenView startScreenView = new StartScreenView();
            StageUtils.changeMainScene("KanbanBo - Database file selection", startScreenView);
            //currentScene = new Scene(startScreenView.getView());
        }
    }

}