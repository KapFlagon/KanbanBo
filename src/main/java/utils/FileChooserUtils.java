package utils;

import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserUtils {
    //private static FileChooser fileChooser;

    // Initialisation methods
    private static FileChooser initFileChooser(String title) {
        FileChooser freshFileChooser = new FileChooser();
        freshFileChooser.setTitle(title);
        freshFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return freshFileChooser;
    }

    // Other methods
    public static File createFilePopup() {
        FileChooser fileChooser = initFileChooser("Create Database File");
        return fileChooser.showSaveDialog(MainStageUtils.getMainStage());
    }

    public static File openFilePopup() {
        FileChooser fileChooser = initFileChooser("Open Database File");
        return fileChooser.showOpenDialog(MainStageUtils.getMainStage());
    }

}
