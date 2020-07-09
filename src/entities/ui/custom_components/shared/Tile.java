package entities.ui.custom_components.shared;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Tile extends Group{

	// Variables
	protected Label title;
	protected Color colour;
	protected int listPosition;


	// Constructors
	public Tile() {
		initializeTitle("Dummy Tile");
		colour = Color.rgb(0,0,0,1);
		listPosition = 1;
	}


	public Tile(String newTitle, Color newColour, int newPosition){
		initializeTitle(newTitle);
		colour = newColour;
		listPosition = newPosition;
	}


	// Getters and Setters
	public Label getTitle() { return title; }
	public void setTitle(Label title) { this.title = title; }
	public Color getColour() { return colour; }
	public void setColour(Color colour) { this.colour = colour; }
	public int getListPosition() { return listPosition; }
	public void setListPosition(int listPosition) { this.listPosition = listPosition; }


	abstract protected void initializeView();

	protected void initializeTitle(String text) {
		title = new Label(text);
		StackPane.setAlignment(title, Pos.TOP_LEFT);
		title.setStyle("-fx-font-weight: bold");
		title.setPadding(new Insets(3,3,3,3));
		title.setTextFill(Color.rgb(255,255,255,1.0));
	}

}
