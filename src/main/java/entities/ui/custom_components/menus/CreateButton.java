package entities.ui.custom_components.menus;

import entities.ui.custom_components.utils.ImageHelper;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class CreateButton extends MenuButton {
	private ImageView iconImage;
	private final String iconPath = "src/assets/icons/add/ic_add_circle_outline_black_18dp.png";
	private MenuItem board;
	//private MenuItem team;
	private MenuItem category;


	public CreateButton() {
		try {
			iconImage = ImageHelper.parseImagePath(iconPath);
		} catch (Exception e) {
			System.out.println("Error using ImageHelper, Create: " + e.getStackTrace());
		}

		initMenuItems();
		initDisplay();
	}

	private void initMenuItems() {
		board = new MenuItem("New Board");
		//team = new MenuItem("Team");
		category = new MenuItem("New Category");
	}

	private void initDisplay() {
		this.setGraphic(iconImage);
		this.getItems().add(board);
		//this.getItems().add(team);
		this.getItems().add(category);
	}
}
