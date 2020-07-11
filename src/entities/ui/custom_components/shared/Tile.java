package entities.ui.custom_components.shared;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Tile extends Group{

	// Variables
	protected String titleText;
	protected Color colour;
	protected int listPosition;


	// Constructors
	public Tile() {
		titleText = ("Dummy Tile");
		colour = Color.rgb(0,0,0,1);
		listPosition = 1;
	}


	public Tile(String newTitleText, Color newColour, int newPosition){
		titleText = newTitleText;
		colour = newColour;
		listPosition = newPosition;
	}


	// Getters and Setters
	public String getTitleText() { return titleText; }
	public void setTitleText(String titleText) { this.titleText = titleText; }
	public Color getColour() { return colour; }
	public void setColour(Color colour) { this.colour = colour; }
	public int getListPosition() { return listPosition; }
	public void setListPosition(int listPosition) { this.listPosition = listPosition; }


	abstract protected void initializeView();

}
