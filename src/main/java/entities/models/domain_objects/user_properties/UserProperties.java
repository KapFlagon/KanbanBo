package entities.models;

import javafx.scene.text.Font;

import java.util.ArrayList;

public class UserProperties {

	private boolean colourblindModeOn;
	private boolean loadMostRecentFile;
	private ArrayList<String> recentItemPaths;


	public void PropertiesModel() {
		initColourblindModeOn();
		initRecentItemPaths();
	}


	// Getters and Setters
	public boolean isColourblindModeOn() {
		return colourblindModeOn;
	}

	public void setColourblindModeOn(boolean colourblindModeOn) {
		this.colourblindModeOn = colourblindModeOn;
	}

	public boolean canLoadMostRecentFile() {
		return loadMostRecentFile;
	}

	public void setLoadMostRecentFile(boolean loadMostRecentFile) {
		this.loadMostRecentFile = loadMostRecentFile;
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

	public void initRecentItemPaths() {
		recentItemPaths = new ArrayList<String>();
	}


	// Other methods
	
}
