package utils;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class FileChooserUtils {

    // Initialisation methods
    private static FileChooser initFileChooser(String title) {
        FileChooser freshFileChooser = new FileChooser();
        freshFileChooser.setTitle(title);
        freshFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("KanbanBo Database Files", "*.kbbdb"));
        return freshFileChooser;
    }

    // Other methods
    public static File createFilePopup() {
        FileChooser fileChooser = initFileChooser("Create Database File");
        return fileChooser.showSaveDialog(StageUtils.getMainStage());
    }

    public static File openFilePopup() {
        FileChooser fileChooser = initFileChooser("Open Database File");
        return fileChooser.showOpenDialog(StageUtils.getMainStage());
    }

}
