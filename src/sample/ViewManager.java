package sample;

import entities.ui.views.View_HomeScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {

	private Stage stage;
	private Scene currentScene;

	public ViewManager() {
		View_HomeScreen homeScreen = new View_HomeScreen();

		currentScene = homeScreen.getScene();
		//stage = new Stage(currentScene);
		//stage.show();
	}

	public Stage getStage() { return stage; }
	public void setStage(Stage newStage) { this.stage = newStage; }
	public Scene getCurrentScene() { return currentScene; }
	//public void setCurrentScene(Scene currentScene) { this.currentScene = currentScene; }

	public void changeScene(Scene newScene) {
		this.currentScene = newScene;
	}

}
