package entities.ui.custom;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MiniBoardTile extends Group{

	// Variables
	private Label title;
	private Rectangle rectangle;
	private Color colour;
	private int listPosition;
	private boolean favourite;
	private StackPane stackPane;
	private Image favourited;
	private Image pre_favourited;


	// Constructors
	public MiniBoardTile() {
		title = new Label("Dummy Mini Board");
		StackPane.setAlignment(title, Pos.TOP_LEFT);
		colour = new Color(0,0,0,0);
		initializeRect(colour);
		listPosition = 1;
		favourite = false;
		initializeView();
	}


	public MiniBoardTile(String newTitle, Color newColour, int newPosition, boolean newFavourite){
		title = new Label(newTitle);
		StackPane.setAlignment(title, Pos.TOP_LEFT);
		colour = newColour;
		initializeRect(colour);
		listPosition = newPosition;
		favourite = newFavourite;
		initializeView();
	}


	// Getters and Setters
	public Label getTitle() { return title; }
	public void setTitle(Label title) { this.title = title; }
	public Rectangle getRectangle() { return rectangle; }
	public void setRectangle(Rectangle rectangle) { this.rectangle = rectangle; }
	public Color getColour() { return colour; }
	public void setColour(Color colour) { this.colour = colour; }
	public int getListPosition() { return listPosition; }
	public void setListPosition(int listPosition) { this.listPosition = listPosition; }
	public boolean isFavourite() { return favourite; }
	public void setFavourite(boolean favourite) { this.favourite = favourite; }
	public StackPane getStackPane() { return stackPane; }
	public void setStackPane(StackPane new_stackPane) { this.stackPane = new_stackPane; }


	// Other methods
	private void initializeRect(Color rectColour) {
		rectangle = new Rectangle();
		rectangle.setWidth(200);
		rectangle.setHeight(100);
		rectangle.setFill(rectColour);
	}

	private void initializeView() {
		stackPane = new StackPane();
		stackPane.getChildren().addAll(rectangle, title);
		this.getChildren().add(stackPane);
	}

}
