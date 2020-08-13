package entities.ui.custom_components.card.sub_elements.label;

import javafx.scene.paint.Color;

public abstract class CardLabel {

	private String title;
	private Color colour;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Color getColour() {
		return colour;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
}