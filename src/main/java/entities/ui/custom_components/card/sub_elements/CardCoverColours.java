package entities.ui.custom_components.card.sub_elements;

import javafx.scene.paint.Color;

public enum CardCoverColours {
	SD_LIMEGREEN (123, 200, 108, 1.0),
	B_YELLOW (245, 221, 41, 1.0),
	L_ORANGE (255, 175, 63, 1.0),
	S_RED (239, 117, 100, 1.0),
	VS_VIOLET (205, 141, 229, 1.0),
	M_BLUE (91, 164, 207, 1.0),
	B_CYAN (41, 204, 229, 1.0),
	S_CYAN (109, 236, 169, 1.0),
	VL_PINK (255, 142, 212, 1.0),
	VD_BLUE (23, 43, 77, 1.0);

	private Color colour;

	CardCoverColours(int red, int green, int blue, double alpha){
		colour = Color.rgb(red, green, blue, alpha);
	}

	private Color getColour() {
		return colour;
	}

	// 01. 7BC86C	rgb: (123, 200, 108, 1.0)	Slightly desaturated lime green
	// 02. F5DD29	rgb: (245, 221, 41, 1.0)	Bright yellow
	// 03. FFAF3F	rgb: (255, 175, 63, 1.0)	Light orange
	// 04. EF7564	rgb: (239, 117, 100, 1.0)	Soft red
	// 05. CD8DE5	rgb: (205, 141, 229, 1.0)	Very soft violet
	// 06. 5BA4CF	rgb: (91, 164, 207, 1.0)	Moderate blue
	// 07. 29CCE5	rgb: (41, 204, 229, 1.0)	Bright cyan
	// 08. 6DECA9	rgb: (109, 236, 169, 1.0)	Soft cyan - lime green
	// 09. FF8ED4	rgb: (255, 142, 212, 1.0)	Very light pink
	// 10. 172B4D	rgb: (23, 43, 77, 1.0)	Very dark blue
}
