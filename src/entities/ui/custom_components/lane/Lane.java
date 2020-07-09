package entities.ui.custom_components.lane;

import entities.models.card_data.Card;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class Lane extends Group {

	private int position;

	// stackPane as top level
	// Rectangle background and borderPane added to the stackPane
	private StackPane stackPane;

	// borderPane layouts- topHbox, centreVbox, bottomHbox
	private BorderPane borderPane;

	private HBox topHbox;
	private TextArea title;
	private Button menuButton;

	private VBox centreVbox;
	private List<Card> cards;
	// Created Cards go here
	// Clicking on "Create a card" should add a text area here.

	private HBox bottomHbox;
	private Button addCardButton;
	private Button cardFromTemplateButton;


	public Lane() {
		initializeVariables();

		topHbox = new HBox();
		title = new TextArea();
		title.setPromptText("Add a Lane");
		title.setPrefRowCount(1);
		title.setWrapText(true);
		title.setOnMouseClicked(evnt -> {
			System.out.println("title clicked");
		});
		//title.setBackground(new Background(new BackgroundFill(Color.rgb(235, 236, 240, 1.0), new CornerRadii(12.0), new Insets(5,5,5,5))));
		try{
			String menuHpath = "src/assets/icons/menu_h/ic_more_horiz_black_18dp.png";
			InputStream inputStream = new FileInputStream(menuHpath);
			Image image = new Image(inputStream);
			ImageView imageView = new ImageView(image);
			menuButton.setGraphic(imageView);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

		topHbox.getChildren().addAll(title, menuButton);

		centreVbox.getChildren().add(new TextArea());

		try{
			String addIconPath = "src/assets/icons/add/ic_add_box_black_18dp.png";
			InputStream inputStream = new FileInputStream(addIconPath);
			Image image = new Image(inputStream);
			ImageView imageView = new ImageView(image);
			addCardButton.setGraphic(imageView);
			addCardButton.setText("Add a Card");
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

		try{
			String templateIconPath = "src/assets/icons/add/ic_add_circle_outline_white_18dp.png";
			InputStream inputStream = new FileInputStream(templateIconPath);
			Image image = new Image(inputStream);
			ImageView imageView = new ImageView(image);
			cardFromTemplateButton.setGraphic(imageView);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

		bottomHbox.getChildren().addAll(addCardButton, cardFromTemplateButton);

		borderPane.setMinWidth(280);
		this.minWidth(280);
		this.prefWidth(280);
		this.maxWidth(280);
		borderPane.setMaxWidth(280);
		borderPane.setTop(topHbox);
		borderPane.setCenter(centreVbox);
		borderPane.setBottom(bottomHbox);

		this.getChildren().add(borderPane);
	}

	public void initializeVariables() {
		position = 1;
		stackPane = new StackPane();
		stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(235, 236, 240, 1.0), new CornerRadii(7.0), new Insets(1,1,1,1))));

		borderPane = new BorderPane();
		borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(235, 236, 240, 1.0), new CornerRadii(7.0), new Insets(1,1,1,1))));

		topHbox = new HBox();
		title = new TextArea();
		menuButton = new Button();

		centreVbox = new VBox();
		// Need a dynamic container for Cards
		//cards = new List<>;

		bottomHbox = new HBox();
		addCardButton = new Button();
		cardFromTemplateButton = new Button();
	}

}
