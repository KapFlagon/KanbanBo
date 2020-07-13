package entities.ui.custom_components.shared;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class PopUpWindow {


	private Stage popupWindow;
	private Scene popupScene;


	public PopUpWindow(String title) {
		popupWindow = new Stage();
		popupWindow.initStyle(StageStyle.UTILITY);
		popupWindow.initModality(Modality.APPLICATION_MODAL);
		popupWindow.setTitle(title);
		popupWindow.setWidth(200);
		popupWindow.setHeight(90);
	}


	public Scene getPopupScene() {
		return popupScene;
	}
	public void setPopupScene(Scene popupScene) {
		this.popupScene = popupScene;
	}
	public Stage getPopupWindow() {
		return popupWindow;
	}
	public void setPopupWindow(Stage popupWindow) {
		this.popupWindow = popupWindow;
	}


	public void initScene(Parent root) {
		popupScene = new Scene(root);
		popupWindow.setScene(popupScene);
		//popupWindow.showAndWait();
	}

}
