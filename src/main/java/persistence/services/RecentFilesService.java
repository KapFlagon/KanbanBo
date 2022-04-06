package persistence.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.repositories.RecentFilesRepository;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.List;
import java.util.prefs.BackingStoreException;

public class RecentFilesService {


    // Variables
    @Inject
    private RecentFilesRepository recentFilesRepository;
    private final ObservableList<Path> recentFilePaths;


    // Constructors
    public RecentFilesService() {
        this.recentFilePaths = FXCollections.observableArrayList();
    }


    // Getters and Setters
    public List<Path> getRecentFilePaths() {
        return recentFilesRepository.loadRecentFilePaths();
    }


    // Initialisation methods


    // Other methods
    private void trimToMaxSize() {
        int diff = recentFilePaths.size() - RecentFilesRepository.MAX_SAVED_PATHS;
        while(diff > 0) {
            recentFilePaths.remove((recentFilePaths.size() - 1));
            diff--;
        }
    }

    public void addRecentFilePath(Path newRecentFilePath) throws BackingStoreException {
        recentFilePaths.remove(newRecentFilePath);
        recentFilePaths.add(0, newRecentFilePath);
        trimToMaxSize();
        recentFilesRepository.saveRecentFilePaths(recentFilePaths);
    }

    public void removeRecentFilePath(Path recentFilePathToRemove) throws BackingStoreException {
        recentFilePaths.remove(recentFilePathToRemove);
        recentFilesRepository.saveRecentFilePaths(recentFilePaths);
    }

    public void clearRecentFilePaths() throws BackingStoreException {
        recentFilePaths.clear();
        recentFilesRepository.saveRecentFilePaths(recentFilePaths);
    }

}
