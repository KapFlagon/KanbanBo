package persistence.services;

import persistence.repositories.UserPreferencesRepository;

import javax.inject.Inject;
import java.util.prefs.BackingStoreException;

public class UserPreferencesService {


    // Variables
    @Inject
    private UserPreferencesRepository userPreferencesRepository;



    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public void setAutoOpenMostRecentFileValue(boolean newValue) throws BackingStoreException {
        userPreferencesRepository.setAutoOpeningMostRecentFile(newValue);
    }


    public boolean getAutoOpenMostRecentFileValue() {
        return userPreferencesRepository.isAutoOpeningMostRecentFile();
    }
}
