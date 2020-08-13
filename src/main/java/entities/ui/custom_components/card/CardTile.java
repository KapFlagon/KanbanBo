package entities.ui.custom_components.card;

import entities.ui.custom_components.shared.DynTextArea;
import entities.ui.custom_components.shared.Tile;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CardTile extends Tile {

	private VBox outerVbox;
	private DynTextArea titleDynTextArea;
	private Color activeBackgroundColour;
	private Color inactiveBackgroundColour;


	public CardTile() {
		//setColour(new Color(1,1,1,1));
		outerVbox = new VBox();
		outerVbox.setSpacing(5);
		outerVbox.setPadding(new Insets(2,2,2,2));
		titleDynTextArea = new DynTextArea();
		titleDynTextArea.setPromptText("Enter a Card Name...");
		titleDynTextArea.setMaxSize(200,200);
		setInactiveBackgroundColour(Color.rgb(244,245,247,1.0));
		outerVbox.getChildren().add(titleDynTextArea);
		this.getChildren().add(outerVbox);
	}

	public void setActiveBackgroundColour(Color activeBackgroundColour) {
		this.activeBackgroundColour = activeBackgroundColour;
		this.titleDynTextArea.setInactiveBackgroundColour(activeBackgroundColour);
	}
	/*public Color getActiveBackgroundColour() {
		return activeBackgroundColour;
	}*/
	public void setInactiveBackgroundColour(Color inactiveBackgroundColour) {
		this.inactiveBackgroundColour = inactiveBackgroundColour;
		this.titleDynTextArea.setInactiveBackgroundColour(inactiveBackgroundColour);
	}
	/*public Color getInactiveBackgroundColour() {
		return inactiveBackgroundColour;
	}*/

	@Override
	protected void initializeView() {

	}
}
