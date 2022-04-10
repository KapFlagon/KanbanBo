package persistence.repositories;

import javafx.collections.FXCollections;
import preferences.KanbanBoPreferences;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.nio.file.Path;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class RecentFilesRepository {


    // Variables
    @Inject
    KanbanBoPreferences kanbanBoPreferences;
    private Preferences recentItemsPreferences;
    public static final int MAX_SAVED_PATHS = 5;
    private final String KEY_recentFilePaths = "recent_item_";


    // Constructors
    public RecentFilesRepository() {

    }

    @PostConstruct
    private void postConstructTasks() {
        recentItemsPreferences = kanbanBoPreferences.kanbanboPreferencesChildNode("recent_files");
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public void saveRecentFilePaths(List<Path> recentFilePaths) throws BackingStoreException {
        recentItemsPreferences.clear();
        for (int pathIterator = 0; pathIterator < recentFilePaths.size(); pathIterator++) {
            String key = KEY_recentFilePaths+pathIterator;
            String path = recentFilePaths.get(pathIterator).toString();
            recentItemsPreferences.put(key, path);
        }
        recentItemsPreferences.flush();
    }

    public List<Path> loadRecentFilePaths() {
        List<Path> recentFilePaths = FXCollections.observableArrayList();
        for (int pathKeyIterator = 0; pathKeyIterator < MAX_SAVED_PATHS; pathKeyIterator++) {
            String key = KEY_recentFilePaths + pathKeyIterator;
            String pathStringValue = recentItemsPreferences.get(key, "");
            if (!pathStringValue.equals("")) {
                //System.out.println("Path is not empty");
                //URI uri = URI.create(pathStringValue);
                Path path = Path.of(pathStringValue);
                recentFilePaths.add(path);
            } else {
                //System.out.println("Path is empty");
            }
        }
        return recentFilePaths;
    }

}
