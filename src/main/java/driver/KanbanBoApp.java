package driver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import view.mainstage.MainStagePresenter;
import view.mainstage.MainStageView;

import java.awt.*;
import java.util.Arrays;


public class KanbanBoApp extends Application {

    private int appMinHeight = 400;
    private int appMinWidth = 600;

    // TODO See this strategy for Afterburner bundles and internationalization: https://stackoverflow.com/questions/38082417/set-and-change-resource-bundle-with-afterburner-fx
    // Perform internationalization in a single file.
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        MainStageView mainStageView = new MainStageView();
        MainStagePresenter mainStagePresenter = (MainStagePresenter) mainStageView.getPresenter();
        mainStagePresenter.setMainStage(primaryStage);
        mainStagePresenter.setStageSizes(appMinWidth, appMinHeight);
    }

    public void setStageSizes(Stage primaryStage) {
        primaryStage.setWidth(appMinWidth);
        primaryStage.setMinWidth(appMinWidth);
        primaryStage.setHeight(appMinHeight);
        primaryStage.setMinHeight(appMinHeight);
    }

}