package entities.ui.custom_components.utils;

import javafx.scene.paint.Color;

public class ColourHelper {

	// User "Kröw" solution from stackoverflow
	// https://stackoverflow.com/questions/17925318/how-to-get-hex-web-string-from-javafx-colorpicker-color/56733608#56733608

	private static String format(double val) {
		String intVal = Integer.toHexString((int) Math.round(val * 255));
		return intVal.length() == 1 ? "0" + intVal : intVal;
	}

	public static String rgba_toHexString(Color value) {
		return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
				.toUpperCase();
	}

	public static String rgb_toHexString(Color value) {
		return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()))
				.toUpperCase();
	}

}
