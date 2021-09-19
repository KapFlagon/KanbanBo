package userpreferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class UserPreferences {


    // Variables
    private static final UserPreferences SINGLETON = new UserPreferences();
    private final static int MAX_PATHS = 5;
    private final String KEY_openingMostRecentAutomatically = "openingMostRecentAutomatically";
    private final String KEY_recentFilePaths = "recent_item_";
    private Preferences preferences;
    private boolean openingMostRecentAutomatically;
    private ObservableList<Path> recentFilePaths;


    // Constructors
    private UserPreferences() {
        Preferences root = Preferences.userRoot();
        preferences = root.node("KanbanBo");
        openingMostRecentAutomatically = false;
        recentFilePaths = FXCollections.observableArrayList();
        loadAllPreferences();
    }


    // Getters and Setters
    public static UserPreferences getSingletonInstance() {
        return SINGLETON;
    }

    public boolean isOpeningMostRecentAutomatically() {
        return openingMostRecentAutomatically;
    }

    public void setOpeningMostRecentAutomatically(boolean openingMostRecentAutomatically) throws BackingStoreException {
        this.openingMostRecentAutomatically = openingMostRecentAutomatically;
        saveAutoLoadValue();
    }

    public ObservableList<Path> getRecentFilePaths() {
        return recentFilePaths;
    }
    public Path getMostRecentPath() {
        if (recentFilePaths.size() > 0) {
            return recentFilePaths.get(0);
        } else {
            return null;
        }
    }


    // Initialisation methods


    // Other methods
    public void addRecentFilePath(Path newRecentFilePath) throws BackingStoreException {
        recentFilePaths.remove(newRecentFilePath);
        recentFilePaths.add(0, newRecentFilePath);
        trimRecentFilePaths();
        saveRecentFilePaths();
    }

    private void trimRecentFilePaths() {
        if (recentFilePaths.size() > MAX_PATHS) {
            recentFilePaths.remove(recentFilePaths.size() - 1);
        }
    }

    public void saveAllPreferences() throws BackingStoreException {
        saveAutoLoadValue();
        saveRecentFilePaths();
    }

    public void saveAutoLoadValue() throws BackingStoreException {
        preferences.putBoolean(KEY_openingMostRecentAutomatically, isOpeningMostRecentAutomatically());
        preferences.flush();
    }

    private void saveRecentFilePaths() throws BackingStoreException {
        for (int pathIterator = 0; pathIterator < recentFilePaths.size(); pathIterator++) {
            String key = KEY_recentFilePaths+pathIterator;
            String path = recentFilePaths.get(pathIterator).toString();
            preferences.put(key, path);
        }
        preferences.flush();
    }

    public void loadAllPreferences() {
        loadAutoLoadValue();
        loadRecentFilePaths();
    }

    private void loadAutoLoadValue() {
        openingMostRecentAutomatically = preferences.getBoolean(KEY_openingMostRecentAutomatically, false);
    }

    private void loadRecentFilePaths() {
        for (int pathKeyIterator = 0; pathKeyIterator < MAX_PATHS; pathKeyIterator++) {
            String key = KEY_recentFilePaths + pathKeyIterator;
            String pathStringValue = preferences.get(key, "");
            if (!pathStringValue.equals("")) {
                //System.out.println("Path is not empty");
                //URI uri = URI.create(pathStringValue);
                Path path = Path.of(pathStringValue);
                recentFilePaths.add(path);
            } else {
                //System.out.println("Path is empty");
            }
        }
    }

    public void removeRecentFilePath(Path path){
        String foundPositionKey = getPathPositionInPreferences(path);
        if (!foundPositionKey.equals("")) {
            preferences.remove(foundPositionKey);
        }
    }

    private String getPathPositionInPreferences(Path path) {
        String foundPositionKey = "";
        for (int pathKeyIterator = 0; pathKeyIterator < MAX_PATHS; pathKeyIterator++) {
            String key = KEY_recentFilePaths + pathKeyIterator;
            String pathStringValue = preferences.get(key, "");
            if (pathStringValue.equals(path.toString())) {
                foundPositionKey = key;
            }
        }
        return foundPositionKey;
    }

}
