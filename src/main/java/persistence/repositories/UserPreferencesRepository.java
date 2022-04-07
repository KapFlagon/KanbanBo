package persistence.repositories;

import preferences.KanbanBoPreferences;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class UserPreferencesRepository {


    // Variables
    @Inject
    KanbanBoPreferences kanbanBoPreferences;
    private Preferences userPreferences;
    private final String KEY_openingMostRecentFileAutomatically = "auto_opening_most_recent_file";
    private boolean autoOpeningMostRecentFile;



    // Constructors
    //public UserPreferencesRepository(KanbanBoPreferences kanbanBoPreferences) {
    public UserPreferencesRepository() {
        //this.kanbanBoPreferences = kanbanBoPreferences;
        autoOpeningMostRecentFile = false;
    }

    @PostConstruct
    private void postConstructTasks() {
        userPreferences = kanbanBoPreferences.kanbanboPreferencesChildNode("user_preferences");
        loadAllPreferences();
    }


    // Getters and Setters
    public boolean isAutoOpeningMostRecentFile() {
        return autoOpeningMostRecentFile;
    }

    public void setAutoOpeningMostRecentFile(boolean autoOpeningMostRecentFile) throws BackingStoreException {
        this.autoOpeningMostRecentFile = autoOpeningMostRecentFile;
        saveAutoLoadValue();
    }


    // Initialisation methods


    // Other methods
    public void saveAllPreferences() throws BackingStoreException {
        saveAutoLoadValue();
    }

    public void saveAutoLoadValue() throws BackingStoreException {
        userPreferences.putBoolean(KEY_openingMostRecentFileAutomatically, isAutoOpeningMostRecentFile());
        userPreferences.flush();
    }


    public void loadAllPreferences() {
        loadAutoLoadValue();
    }

    private void loadAutoLoadValue() {
        autoOpeningMostRecentFile = userPreferences.getBoolean(KEY_openingMostRecentFileAutomatically, false);
    }

}
