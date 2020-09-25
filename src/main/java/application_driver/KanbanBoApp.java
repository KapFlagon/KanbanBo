package application_driver;

import javafx.application.Application;
import javafx.stage.Stage;


public class KanbanBoApp extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("KanBanBo");
		primaryStage.setWidth(600);
		primaryStage.setHeight(400);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}