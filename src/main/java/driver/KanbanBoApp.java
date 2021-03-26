package driver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.StageUtils;
import view.screens.startscreen.StartScreenView;

import java.util.UUID;


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
        StartScreenView startScreenView = new StartScreenView();
        currentScene = new Scene(startScreenView.getView());
        primaryStage.setScene(currentScene);
        setStageSizes(primaryStage);
        primaryStage.show();
    }

    public void setStageSizes(Stage primaryStage) {
        primaryStage.setWidth(appMinWidth);
        primaryStage.setMinWidth(appMinWidth);
        primaryStage.setHeight(appMinHeight);
        primaryStage.setMinHeight(appMinHeight);
    }


}