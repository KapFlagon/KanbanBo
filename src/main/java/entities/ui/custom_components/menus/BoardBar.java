package entities.ui.custom_components.menus;

import entities.ui.custom_components.shared.DynTextField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class BoardBar extends FlowPane {
	private DynTextField boardTitle;
	private Button favouriteButton;
	private final String favOnIconPath = "src/assets/icons/favourite/ic_star_white_18dp.png";;
	private final String favOffIconPath = "src/assets/icons/favourite/ic_star_border_white_18dp.png";
	private ImageView favOnIconImage;
	private ImageView favOffIconImage;

	public BoardBar() {
		
	}
}
