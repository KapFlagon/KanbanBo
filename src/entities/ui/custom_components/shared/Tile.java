package entities.ui.custom_components;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Tile extends Group{

	// Variables
	protected Label title;
	protected Rectangle rectangle;
	protected Color colour;
	protected int listPosition;


	// Constructors
	public Tile() {
		initializeTitle("Dummy Tile");
		StackPane.setAlignment(title, Pos.TOP_LEFT);
		colour = new Color(0,0,0,1);
		initializeRect(colour);
		listPosition = 1;
		//initializeView();
	}


	public Tile(String newTitle, Color newColour, int newPosition){
		initializeTitle(newTitle);
		StackPane.setAlignment(title, Pos.TOP_LEFT);
		colour = newColour;
		initializeRect(colour);
		listPosition = newPosition;
		//initializeView();
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


	// Other methods
	protected void initializeRect(Color rectColour) {
		rectangle = new Rectangle();
		rectangle.setWidth(200);
		rectangle.setHeight(100);
		rectangle.setArcWidth(20.0);
		rectangle.setArcHeight(15.0);
		rectangle.setFill(rectColour);
	}

	abstract protected void initializeView();

	protected void initializeTitle(String text) {
		title = new Label(text);
		title.setStyle("-fx-font-weight: bold");
		title.setTextFill(new Color(1,1,1,1));
	}

}
