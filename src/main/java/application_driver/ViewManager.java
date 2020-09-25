package application_driver;

import entities.ui.views.I_View;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {

	private Stage stage;
	private Scene currentScene;
	private PROGRAM_STATE program_state;
	private I_View currentView;

	public ViewManager() {

	}

	// TODO Need to build a window with Menu bar as a container for all screens after start-up screen.

	// Getters and Setters
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Scene getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(Scene currentScene) {
		this.currentScene = currentScene;
	}

	public PROGRAM_STATE getProgram_state() {
		return program_state;
	}

	public void setProgram_state(PROGRAM_STATE program_state) {
		this.program_state = program_state;
	}
}
