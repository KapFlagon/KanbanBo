package entities.ui.custom_components.menus;

import entities.ui.custom_components.utils.ImageHelper;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class HomeButton extends Button {

	private ImageView homeImage;
	private final String iconPath = "src\\assets\\icons\\home\\ic_home_black_18dp.png";

	public HomeButton() {
		try {
			homeImage = ImageHelper.parseImagePath(iconPath);
		} catch (Exception e) {
			System.out.println("Error using ImageHelper, HomeButton: " + e.getStackTrace());
		}
		this.setGraphic(homeImage);
	}
}
