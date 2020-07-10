package entities.ui.custom_components.card;

import entities.ui.custom_components.shared.DynTextArea;
import entities.ui.custom_components.shared.Tile;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CardTile extends Tile {

	private VBox outerVbox;
	private DynTextArea dynTextAreaTitle;


	public CardTile() {
		//setColour(new Color(1,1,1,1));
		outerVbox = new VBox();
		outerVbox.setSpacing(5);
		outerVbox.setPadding(new Insets(2,2,2,2));
		dynTextAreaTitle = new DynTextArea();
		dynTextAreaTitle.setPromptText("Enter a Card Name...");
		dynTextAreaTitle.setMaxSize(200,200);
		outerVbox.getChildren().add(dynTextAreaTitle);
		this.getChildren().add(outerVbox);
	}

	@Override
	protected void initializeView() {

	}
}
