package entities.ui.custom_components.lane;

import entities.ui.custom_components.card.CardTile;
import entities.ui.custom_components.shared.DynTextArea;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Lane extends Group {

	private int position;
	private Color backgroundColour;

	// stackPane as top level
	// Rectangle background and borderPane added to the stackPane
	private StackPane stackPane;

	// borderPane layouts- topHbox, centreVbox, bottomHbox
	private BorderPane borderPane;

	private HBox topHbox;
	private DynTextArea title;
	private Button menuButton;

	private VBox centreVbox;
	private ArrayList<CardTile> cardArrayList;
	// Created Cards go here
	// Clicking on "Create a card" should add a text area here.

	private HBox bottomHbox;
	private Button addCardButton;
	private Button cardFromTemplateButton;


	public Lane() {
		initializeVariables();

		title.setPromptText("Add a Lane");
		title.setPrefRowCount(1);
		title.setWrapText(true);
		title.setPrefHeight(10);

		try{
			String menuHpath = "src/assets/icons/menu_h/ic_more_horiz_black_18dp.png";
			InputStream inputStream = new FileInputStream(menuHpath);
			Image image = new Image(inputStream, 20, 20, true, false);
			ImageView imageView = new ImageView(image);
			menuButton.setGraphic(imageView);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

		topHbox.getChildren().addAll(title, menuButton);

		//centreVbox.getChildren().add(new TextArea());

		try{
			String addIconPath = "src/assets/icons/add/ic_add_box_black_18dp.png";
			InputStream inputStream = new FileInputStream(addIconPath);
			Image image = new Image(inputStream, 20, 20, true, false);
			ImageView imageView = new ImageView(image);
			addCardButton.setGraphic(imageView);
			addCardButton.setText("Add a Card");
			addCardButton.setOnMouseClicked(event -> {
				System.out.println("Clicked 'add card' button.");
				updateCards();
			});
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

		try{
			String templateIconPath = "src/assets/icons/add/ic_add_circle_outline_white_18dp.png";
			InputStream inputStream = new FileInputStream(templateIconPath);
			Image image = new Image(inputStream, 20, 20, true, false);
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
		stackPane.getChildren().add(borderPane);
		stackPane.setPrefSize(400,400);

		this.getChildren().add(borderPane);
	}

	public Color getBackgroundColour() {
		return backgroundColour;
	}
	public void setBackgroundColour(Color newColour) {
		this.backgroundColour = newColour;
	}
	public void setBackgroundColour(int red, int green, int blue, double alpha) {
		this.backgroundColour = Color.rgb(red, green, blue, alpha);
	}

	public void initializeVariables() {
		position = 1;
		setBackgroundColour(235, 236, 240, 1.0);
		stackPane = new StackPane();
		//stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(235, 236, 240, 1.0), new CornerRadii(7.0), new Insets(2,2,2,2))));

		borderPane = new BorderPane();
		borderPane.setBackground(new Background(new BackgroundFill(backgroundColour, new CornerRadii(7.0), new Insets(0,0,0,0))));

		topHbox = new HBox();
		title = new DynTextArea();
		title.setInactiveBackgroundColour(getBackgroundColour());
		menuButton = new Button();

		centreVbox = new VBox();
		// Need a dynamic container for Cards
		//cards = new List<>;
		cardArrayList = new ArrayList<CardTile>();

		bottomHbox = new HBox();
		addCardButton = new Button();
		cardFromTemplateButton = new Button();
	}

	public void updateCards() {
		cardArrayList.add(new CardTile());
		centreVbox = new VBox();
		for (CardTile ct : cardArrayList) {
			centreVbox.getChildren().add(ct);
		}
		borderPane.setCenter(centreVbox);
	}

}
