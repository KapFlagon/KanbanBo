package entities.models.utils;

import entities.models.domain_objects.user_properties.UserProperties;
import utils.FileAndDirectoryHelper;
import utils.ProgramDirectoryHelper;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

public class PropertiesFileHelper {


	// Variables
	private Properties defaultPropertiesObject;
	private Properties propertiesObject;
	private Path propertiesFilePath;


	// Constructors
	public PropertiesFileHelper() throws IOException, URISyntaxException {
		initDefaultPropertiesObject();
		initPropertiesObject();
		initPropertiesFilePath();
		loadSavedProperties();
	}


	// Getters and Setters
	public String getPropertyValue(String key, String defaultValue) {
		return propertiesObject.getProperty(key, defaultValue);
	}

	public void setAndSavePropertyValue(String key, String value) throws IOException {
		propertiesObject.setProperty(key, value);
		storeProperties();
	}

	public String getSelectedSkinProperty() {
		return propertiesObject.getProperty("selectedSkin");
	}

	public boolean getIsColourblindModeOnProperty() {
		return Boolean.valueOf(propertiesObject.getProperty("isColourblindMode"));
	}

	public boolean willLoadMostRecentFileProperty() {
		return Boolean.valueOf(propertiesObject.getProperty("willLoadMostRecentFile"));
	}

	public ArrayList<Path> getRecentItemPathsProperties() {
		ArrayList<Path> recentFilePaths = new ArrayList<Path>();
		for (int iterator = 5; iterator >= 1; iterator--) {
			String propertyName = "recentItemPath_0" + (iterator);
			String propertyValue = propertiesObject.getProperty(propertyName);
			if(!propertyValue.equals("null")) {
				Path tempPath = Paths.get(propertyValue);
				recentFilePaths.add(tempPath);
			}
		}
		return recentFilePaths;
	}


	// Initialisation methods
	public void initDefaultPropertiesObject() {
		defaultPropertiesObject = new Properties();
		UserProperties defaultUserProperties = new UserProperties();
		defaultPropertiesObject.setProperty("selectedSkin", defaultUserProperties.getSelectedSkin());
		defaultPropertiesObject.setProperty("isColourblindModeOn", String.valueOf(defaultUserProperties.getIsColourblindModeOn()));
		defaultPropertiesObject.setProperty("willLoadMostRecentFile", String.valueOf(defaultUserProperties.getWillLoadMostRecentFile()));
		ArrayList<Path> recentFilePaths = defaultUserProperties.getRecentItemPaths();
		for (int iterator = 1; iterator <= recentFilePaths.size(); iterator++) {
			String propertyName = "recentItemPath_0" + (iterator);
			defaultPropertiesObject.setProperty(propertyName, "null");
		}
	}

	private void initPropertiesObject() {
		propertiesObject = new Properties(defaultPropertiesObject);
	}

	public void initPropertiesFilePath() throws URISyntaxException {
		propertiesFilePath = ProgramDirectoryHelper.parsePropertiesPath();
	}

	// Other methods
	private void loadSavedProperties() throws IOException {
		if (doesPropertiesFileExist()) {
			loadProperties();
		} else {
			createInitialPropertiesFile();
		}
	}

	private boolean doesPropertiesFileExist() {
		return FileAndDirectoryHelper.fileExists(propertiesFilePath);
	}

	private void createInitialPropertiesFile() throws IOException {
		FileAndDirectoryHelper.createFile(propertiesFilePath);
	}

	public void loadProperties() throws IOException {
		InputStream inputStream = new FileInputStream(propertiesFilePath.toString());
		propertiesObject.load(inputStream);
	}

	public void storeProperties() throws IOException {
		OutputStream outputStream = new FileOutputStream(propertiesFilePath.toString());
		propertiesObject.store(outputStream, null);
	}



}
