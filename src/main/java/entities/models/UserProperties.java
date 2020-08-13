package entities.models;

import javafx.scene.text.Font;

import java.util.ArrayList;

public class UserProperties {

	private boolean colourblindModeOn;
	private boolean loadMostRecentFile;
	private String font;
	private float fontSize;
	private ArrayList<String> recentItemPaths;


	public void PropertiesModel() {
		initColourblindModeOn();
		initFont();
		initFontSize();
		initRecentItemPaths();
	}


	// Getters and Setters
	public boolean isColourblindModeOn() {
		return colourblindModeOn;
	}

	public void setColourblindModeOn(boolean colourblindModeOn) {
		this.colourblindModeOn = colourblindModeOn;
	}

	public boolean isLoadMostRecentFile() {
		return loadMostRecentFile;
	}

	public void setLoadMostRecentFile(boolean loadMostRecentFile) {
		this.loadMostRecentFile = loadMostRecentFile;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public ArrayList<String> getRecentItemPaths() {
		return recentItemPaths;
	}

	public void setRecentItemPaths(ArrayList<String> recentItemPaths) {
		this.recentItemPaths = recentItemPaths;
	}


	// Initialiser methods
	public void initColourblindModeOn() {
		colourblindModeOn = false;
	}

	public void initFont() {
		font = "default";
	}

	public void initFontSize() {
		fontSize = 2.5f;
	}

	public void initRecentItemPaths() {
		recentItemPaths = new ArrayList<String>();
	}


	// Other methods
	
}
