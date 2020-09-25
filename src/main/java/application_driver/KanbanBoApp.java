package application_driver;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.ProgramDirectoryHelper;

public class KanbanBoApp extends Application {

	PrimaryController primaryController;
	ModelManager modelManager;
	ViewManager_B viewManager_B;
	ViewManager viewManager;

	@Override
	public void start(Stage primaryStage) throws Exception{
		ProgramDirectoryHelper programDirectoryHelper = new ProgramDirectoryHelper();
		primaryStage.setTitle("KanBanBo");
		viewManager_B = new ViewManager_B();
		primaryStage.setScene(viewManager_B.getCurrentScene());
		primaryStage.setWidth(600);
		primaryStage.setHeight(400);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
