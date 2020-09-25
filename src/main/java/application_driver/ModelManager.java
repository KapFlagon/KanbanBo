package application_driver;

import entities.models.domain_objects.user_properties.UserProperties;
import entities.models.utils.PropertiesFileHelper;
import entities.models.views.I_ViewModel;

import java.io.IOException;
import java.net.URISyntaxException;

public class ModelManager {

	private UserProperties userProperties;
	private PropertiesFileHelper propertiesFileHelper;
	private I_ViewModel currentModel;
	private PROGRAM_STATE program_state;


	public void ModelManager(PROGRAM_STATE program_state) throws IOException, URISyntaxException {
		this.program_state = program_state;
		initUserProperties();
		initPropertiesFileHelper();
		readAllUserPropertiesFromFile();
	}

	public void initUserProperties() {
		userProperties = new UserProperties();
	}

	public void initPropertiesFileHelper() throws IOException, URISyntaxException {
		propertiesFileHelper = new PropertiesFileHelper();
	}

	public void readAllUserPropertiesFromFile() throws IOException {
		userProperties.setSelectedSkin(propertiesFileHelper.getSelectedSkinProperty());
		userProperties.setColourblindModeOn(propertiesFileHelper.getIsColourblindModeOnProperty());
		userProperties.setWillLoadMostRecentFile(propertiesFileHelper.getWillLoadMostRecentFileProperty());
		userProperties.setRecentItemPaths(propertiesFileHelper.getRecentItemPathsProperties());
	}


	public void pushPropertiesToFile() throws IOException {
		propertiesFileHelper.setAndSaveSelectedSkinProperty(userProperties.getSelectedSkin());
		propertiesFileHelper.setAndSavesColourblindModeOnProperty(userProperties.getIsColourblindModeOn());
		propertiesFileHelper.setAndSaveWillLoadMostRecentFileProperty(userProperties.getWillLoadMostRecentFile());
		propertiesFileHelper.setAndSaveRecentItemPathsProperties(userProperties.getRecentItemPaths());
	}

}
