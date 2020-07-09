package entities.ui.custom_components.board;

import javafx.scene.paint.Color;

import java.util.Random;

public enum BoardColours {

	S_BLUE (0, 121, 191, 1.0),
	S_ORANGE (210, 144, 52, 1.0),
	DM_GREEN (81, 152, 57, 1.0),
	D_RED (176, 70, 50, 1.0),
	MDD_VIOLET (137, 96, 158, 1.0),
	M_PINK (205, 90, 145, 1.0),
	M_CYAN (75, 191, 107, 1.0),
	S_CYAN (0, 174, 204, 1.0),
	D_GREYBLUE (111, 119, 123, 1.0);

	private Color colour;

	BoardColours(int red, int green, int blue, double alpha){
		colour = Color.rgb(red, green, blue, alpha);
	}

	public Color getColour() {
		return colour;
	}

	public static Color getRandomColour() {
		Random random = new Random();
		BoardColours bc = values()[random.nextInt(values().length)];
		return bc.getColour();
	}

	//1. 0079BF		rgb: (0, 121, 191, 1.0)	Strong blue
	//2. D29034		rgb: (210, 144, 52, 1.0) Strong orange
	//3. 519839		rgb: (81, 152, 57, 1.0) Dark moderate green
	//4. B04632		rgb: (176, 70, 50, 1.0) Dark red
	//5. 89609E		rgb: (137, 96, 158, 1.0) Mostly desaturated dark violet
	//6. CD5A91		rgb: (205, 90, 145, 1.0) Moderate Pink
	//7. 4BBF6B		rgb: (75, 191, 107, 1.0) Moderate cyan - lime green
	//8. 00AECC		rgb: (0, 174, 204, 1.0)	Strong cyan
	//9. 6F777B		rgb: (111, 119, 123, 1.0) Dark grayish blue

}
