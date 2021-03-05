package driver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.MainStageUtils;
import view.screens.startscreen.StartScreenView;


public class KanbanBoApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        MainStageUtils.setMainStage(primaryStage);
        primaryStage.setTitle("KanBanBo");
        StartScreenView startScreen = new StartScreenView();
        Scene startScene = new Scene(startScreen.getView());
        primaryStage.setScene(startScene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.show();
    }


}