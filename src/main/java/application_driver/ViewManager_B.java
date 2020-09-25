package application_driver;

import entities.ui.DragDummy;
import entities.ui.custom_components.board.BoardColours;
import entities.ui.custom_components.board.BoardTile;
import entities.ui.custom_components.card.CardTile;
import entities.ui.custom_components.lane.Lane;
import entities.ui.custom_components.menus.CoreMenu;
import entities.ui.custom_components.menus.CreateButton;
import entities.ui.custom_components.menus.HomeButton;
import entities.ui.custom_components.menus.UserIconButton;
import entities.ui.custom_components.popups.BoardPopUp;
import entities.ui.views.HomeScreen_View;
import entities.ui.views.StartScreen_View;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ViewManager_B {

	private Stage stage;
	private Scene currentScene;
	private PROGRAM_STATE program_state;

	public ViewManager_B() {
		currentScene = testView_HomeScreen();
	}

	public Stage getStage() { return stage; }
	public void setStage(Stage newStage) { this.stage = newStage; }
	public Scene getCurrentScene() { return currentScene; }
	//public void setCurrentScene(Scene currentScene) { this.currentScene = currentScene; }

	public void changeScene(Scene newScene) {
		this.currentScene = newScene;
	}

	public Scene testUIobject_boardTile() {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefViewportHeight(300);
		VBox vbox = new VBox();
		for (BoardColours bcs : BoardColours.values()) {
			BoardTile b = new BoardTile("Dummy title", bcs.getColour(), 1, false);
			vbox.getChildren().add(b);
		}
		scrollPane.setContent(vbox);
		Scene a = new Scene(scrollPane);

		BoardTile bt = new BoardTile();
		VBox vb = new VBox(bt);
		Scene scene = new Scene(vb);
		return scene;
	}

	public Scene testView_HomeScreen() {
		HomeScreen_View c = new HomeScreen_View();
		return c.getScene();
	}

	public Scene testUIobject_lane() {
		HBox hbox = new HBox();
		for (int i = 0; i < 3; i++) {
			Lane lane = new Lane();
			hbox.getChildren().add(lane);
		}
		StackPane sp = new StackPane(hbox);
		sp.setPrefSize(600,600);
		sp.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 60, 1.0), new CornerRadii(7.0), new Insets(1,1,1,1))));
		Scene a = new Scene(sp);
		a.setFill(new Color(1,0,0,1));
		return a;
	}

	public Scene testUIobject_cardTile() {
		VBox vbox = new VBox();
		CardTile ct1 = new CardTile();
		//ct1.setInactiveBackgroundColour(Color.rgb(134, 200, 100, 1.0));
		vbox.getChildren().add(ct1);
		CardTile ct2 = new CardTile();
		//ct2.setInactiveBackgroundColour(Color.rgb(134, 200, 100, 1.0));
		vbox.getChildren().add(ct2);
		Scene a = new Scene(vbox);
		a.setFill(new Color(1,0,0,1));
		return a;
	}

	public void testUIobject_cardTile_Dummy() {
		DragDummy d = new DragDummy(stage);
	}
	
	public Scene testUIobject_boardPopUp() {
		BoardPopUp bpu = new BoardPopUp();
		bpu.getPopupWindow().showAndWait();
		return new Scene(new HBox());
	}

	public Scene testUIobject_UserIcon() {
		UserIconButton uib = new UserIconButton("JG");
		Scene a = new Scene(uib);
		return a;
	}
	public Scene testUIobject_HomeButton() {
		HomeButton hb = new HomeButton();
		Scene a = new Scene(hb);
		return a;
	}
	public Scene testUIobject_CreateButton() {
		CreateButton cb = new CreateButton();
		Scene a = new Scene(cb);
		return a;
	}
	public Scene testUIobject_CoreMenu() {
		CoreMenu cm = new CoreMenu("JG");
		VBox vb = new VBox(cm);
		Scene a = new Scene(vb);
		return a;
	}
	public Scene test_View_StartScreen() {
		StartScreen_View vss_01 = new StartScreen_View(getStage());
		String[] testArray = {"db_01", "db_02"};
		StartScreen_View vss_02 = new StartScreen_View(getStage(), testArray);
		Scene scene = new Scene(vss_02);
		return scene;
	}

}
