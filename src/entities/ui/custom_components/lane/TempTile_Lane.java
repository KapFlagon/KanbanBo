package entities.ui.custom_components.lane;

import entities.ui.custom_components.shared.TempTile;

public class TempTile_Lane extends TempTile {

	public TempTile_Lane() {
		getTextArea().setPromptText("Enter a title for this Lane...");
		getAddButton().setText("Add Lane");
	}

}
