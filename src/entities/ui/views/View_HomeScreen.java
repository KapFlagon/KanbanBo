package entities.ui.views;

import entities.ui.custom_components.board.BoardTile;
import entities.ui.custom_components.board.CategoryContainer;
import entities.ui.custom_components.board.CategoryPopUp;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Random;

public class View_HomeScreen {

	// Variables
	private Scene scene;
	private BorderPane borderPane;
	private ScrollPane centerScrollPane;
	
	private ArrayList<CategoryContainer> categoriesList;
	private CategoryContainer favouritesCategory;
	private Button createCategoryButton;
	private VBox innerVbox;

	// Constructors
	public View_HomeScreen() {
		borderPane = new BorderPane();
		innerVbox = new VBox();
		//innerVbox.setPrefSize(800, 600);

		centerScrollPane = new ScrollPane();
		centerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		centerScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		//centerScrollPane.setPrefViewportHeight(500);
		//centerScrollPane.setPrefViewportWidth(500);

		categoriesList = new ArrayList<CategoryContainer>();

		favouritesCategory = new CategoryContainer("Favourites");
		favouritesCategory.getCreateBoardButton().setDisable(true);
		favouritesCategory.getCreateBoardButton().setOpacity(0);

		createCategoryButton = new Button("Create Category");
		createCategoryButton.setOnAction(event -> {
			CategoryPopUp catPop = new CategoryPopUp();
			catPop.getConfirmButton().setOnAction(butEvent -> {
				String tempText = catPop.getInputField().getText();
				CategoryContainer tempC = new CategoryContainer(tempText);
				categoriesList.add(tempC);
				updateDisplay();
				catPop.getPopupWindow().close();
			});
			catPop.getCancelButton().setOnAction(butEvent -> {
				catPop.getPopupWindow().close();
			});
			catPop.getPopupWindow().showAndWait();
		});

		borderPane.setTop(createCategoryButton);
		updateDisplay();
		scene = new Scene(borderPane);

		//dummyScreen();

	}

	private void dummyScreen() {
		BorderPane borderPane;
		ScrollPane centerScrollPane;
		Button button_L_activeBoards;
		Button button_L_templateBoards;
		Button button_T_home;
		Button button_T_user;
		Button button_T_boardsShortcut;
		VBox vBox_left;
		HBox hBox_Top;
		VBox vBox_center;
		HBox hBox_category;
		button_T_home = new Button ("H");
		button_T_boardsShortcut = new Button ("B");
		button_T_user = new Button ("User");

		button_L_activeBoards = new Button("Boards");
		button_L_templateBoards = new Button("Templates");

		hBox_Top = new HBox();
		hBox_Top.getChildren().addAll(button_T_home, button_T_boardsShortcut, button_T_user);

		vBox_left = new VBox();
		vBox_left.getChildren().addAll(button_L_activeBoards, button_L_templateBoards);

		centerScrollPane = new ScrollPane();
		centerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		centerScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		centerScrollPane.setPrefViewportHeight(500);
		centerScrollPane.setPrefViewportWidth(500);
		vBox_center = new VBox();
		for (int i = 0; i < 10; i++) {
			Label catLabel = new Label("Category " + Integer.toString(i + 1));
			HBox temp_hBox_category = new HBox();
			temp_hBox_category.setPadding(new Insets(10,10,10,10));
			temp_hBox_category.setSpacing(15);;
			ScrollPane tempScrollPane = new ScrollPane();
			tempScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
			tempScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			tempScrollPane.setPrefViewportHeight(110);
			tempScrollPane.setPrefViewportWidth(500);
			for (int j = 0; j < 3; j++) {
				int temp_int = j + 1;
				String tempTitle = "Board " + Integer.toString(temp_int);
				Random random = new Random();
				BoardTile temp_BoardTile = new BoardTile();
				temp_hBox_category.setPadding(new Insets(10,10,10,10));
				temp_hBox_category.setSpacing(15);
				//temp_hBox_category.getChildren().add(temp_miniBoardTile.getGroup());
				temp_hBox_category.getChildren().add(temp_BoardTile);
			}
			tempScrollPane.setContent(temp_hBox_category);
			vBox_center.getChildren().addAll(catLabel, tempScrollPane);
		}
		hBox_category = new HBox();
		centerScrollPane.setContent(vBox_center);

		borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10,10,10,10));
		borderPane.setTop(hBox_Top);
		borderPane.setLeft(vBox_left);
		//borderPane.setCenter(vBox_center);
		borderPane.setCenter(centerScrollPane);

		scene = new Scene(borderPane);
	}

	public Scene getScene() { return scene; }
	public void setScene(Scene scene) { this.scene = scene; }

	public void updateDisplay() {
		innerVbox.getChildren().clear();

		innerVbox.getChildren().add(favouritesCategory);
		for (CategoryContainer cc : categoriesList) {
			innerVbox.getChildren().add(cc);
		}

		centerScrollPane.setContent(innerVbox);
		borderPane.setCenter(centerScrollPane);
	}
}
