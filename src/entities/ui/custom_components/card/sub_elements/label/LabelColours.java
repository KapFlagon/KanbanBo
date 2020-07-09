package entities.ui.custom_components.card.sub_elements.label;

import javafx.scene.paint.Color;

public enum LabelColours {
	M_LIMEGREEN (97, 189, 79, 1.0),
	P_YELLOW (242, 214, 0, 1.0),
	V_ORANGE (255, 159, 26, 1.0),
	B_RED (235, 90, 70, 1.0),
	S_VIOLET (195, 119, 224, 1.0),
	S_BLUE (0, 121, 191, 1.0),
	P_CYAN (0, 194, 224, 1.0),
	S_CYAN (81, 232, 152, 1.0),
	VL_PINK (255, 120, 203, 1.0),
	VDD_BLUE (52, 69, 99, 1.0),
	GREYBLUE (179, 186, 197, 1.0),
	NONE (0,0,0,0);

	private Color colour;

	LabelColours(int red, int green, int blue, double alpha){
		colour = Color.rgb(red, green, blue, alpha);
	}

	private Color getColour() {
		return colour;
	}

	// 01. 61BD4F	rgb: (97, 189, 79, 1.0)	Moderate lime green
	// 02. F2D600	rgb: (242, 214, 0, 1.0)	Pure (or mostly pure) yellow
	// 03. FF9F1A	rgb: (255, 159, 26, 1.0)	Vivid orange
	// 04. EB5A46	rgb: (235, 90, 70, 1.0)	Bright red
	// 05. C377E0	rgb: (195, 119, 224, 1.0)	Soft violet
	// 06. 0079BF	rgb: (0, 121, 191, 1.0)	Strong blue
	// 07. 00C2E0	rgb: (0, 194, 224, 1.0)	Pure (or mostly pure) cyan
	// 08. 51E898	rgb: (81, 232, 152, 1.0)	Soft cyan - lime green
	// 09. FF78CB	rgb: (255, 120, 203, 1.0)	Very light pink
	// 10. 344563	rgb: (52, 69, 99, 1.0)	Very dark desaturated blue
	// 11. B3BAC5	rgb: (179, 186, 197, 1.0)	Grayish blue
	// No colour.

}
