package entities.ui.custom_components.card;

import entities.ui.custom_components.shared.TempTile;

public class TempTile_Card extends TempTile {

	public TempTile_Card() {
		getTextArea().setPromptText("Enter a title for this Card...");
		getAddButton().setText("Add Card");
	}
}
