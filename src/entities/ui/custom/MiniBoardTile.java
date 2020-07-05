package entities.ui.custom;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MiniBoardTile{

	// Variables
	private Label title;
	private Rectangle rectangle;
	private Color colour;
	private int listPosition;
	private boolean favourite;
	private StackPane stackPane;


	// Constructors
	public MiniBoardTile() {
		title = new Label("Dummy Mini Board");
		colour = new Color(0,0,0,0);
		initializeRect(colour);
		listPosition = 1;
		stackPane = new StackPane();
		stackPane.getChildren().addAll(rectangle, title);
	}


	public MiniBoardTile(String newTitle, Color newColour, int newPosition, boolean newFavourite){
		title = new Label(newTitle);
		colour = newColour;
		initializeRect(colour);
		listPosition = newPosition;
		favourite = newFavourite;
		stackPane = new StackPane();
		stackPane.getChildren().addAll(rectangle, title);
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
}
