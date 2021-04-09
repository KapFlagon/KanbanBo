package user.preferences;

import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
    private List<Path> recentFilePaths;


    // Constructors
    private UserPreferences() {
        Preferences root = Preferences.userRoot();
        preferences = root.node("KanbanBo");
        openingMostRecentAutomatically = false;
        recentFilePaths = new ArrayList<Path>();
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

    public List<Path> getRecentFilePaths() {
        return recentFilePaths;
    }
    public Path getMostRecentPath() {
        return recentFilePaths.get(0);
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
                System.out.println("Path is not empty");
                //URI uri = URI.create(pathStringValue);
                Path path = Path.of(pathStringValue);
                recentFilePaths.add(path);
            } else {
                System.out.println("Path is empty");
            }
        }
    }

}
