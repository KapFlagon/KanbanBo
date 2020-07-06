package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    Controller controller;
    ModelManager modelManager;
    ViewManager viewManager;
    /*
    Button button;
    StackPane layout;
    Scene scene;

     */

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("KanBanBo");
        viewManager = new ViewManager();
        primaryStage.setScene(viewManager.getCurrentScene());
        primaryStage.show();
        /*
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 300, 275));
        button = new Button();
        button.setText("Click");
        button.setOnAction(e -> {
            System.out.println("yum");
            System.out.println("ee");
        });

        layout = new StackPane();
        layout.getChildren().add(button);

        scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
         */
    }


    public static void main(String[] args) {
        launch(args);
    }

    /*
    public void handle(ActionEvent event) {
        System.out.println("yum");
        newButton();
    }
    public void newButton() {
        Button tempButton = new Button();
        tempButton.setText("Click");
        layout.getChildren().add(tempButton);

    }
     */
}
