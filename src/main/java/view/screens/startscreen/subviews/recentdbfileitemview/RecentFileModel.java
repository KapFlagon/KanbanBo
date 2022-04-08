package view.screens.startscreen.subviews.recentdbfileitemview;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class RecentFileModel {


    // Variables
    private Path path;
    private final SimpleStringProperty fileName;
    private final SimpleStringProperty pathString;
    private final SimpleStringProperty createdOnDate;
    private final SimpleStringProperty lastChangedDate;
    private final SimpleBooleanProperty fileExistsInPath;


    // Constructors
    private RecentFileModel() {
        this.pathString = new SimpleStringProperty();
        this.fileName = new SimpleStringProperty();
        this.createdOnDate = new SimpleStringProperty();
        this.lastChangedDate = new SimpleStringProperty();
        this.fileExistsInPath = new SimpleBooleanProperty();
    }

    public RecentFileModel(Path path) throws IOException {
        this();
        setPath(path);
    }


    // Getters and Setters
    public void setPath(Path path) throws IOException {
        this.path = path;
        this.pathString.set(path.toString());
        this.fileName.set(calculateFileNameWithoutExtension(path));
        this.createdOnDate.set(calculateCreationDateAsString(path));
        this.lastChangedDate.set(calculateLastChangedDateAsString(path));
        this.fileExistsInPath.set(validatePathAsFile(path));
    }

    public String getPathString() {
        return pathString.get();
    }
    public SimpleStringProperty pathStringProperty() {
        return pathString;
    }

    public void setPathString(String pathString) {
        this.pathString.set(pathString);
    }

    public boolean getFileExistsInPath() {
        return fileExistsInPath.get();
    }

    public SimpleBooleanProperty fileExistsInPathProperty() {
        return fileExistsInPath;
    }

    public void setFileExistsInPath(boolean fileExistsInPath) {
        this.fileExistsInPath.set(fileExistsInPath);
    }

    public String getFileName() {
        return fileName.get();
    }
    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getCreatedOnDate() {
        return createdOnDate.get();
    }
    public SimpleStringProperty createdOnDateProperty() {
        return createdOnDate;
    }

    public void setCreatedOnDate(String createdOnDate) {
        this.createdOnDate.set(createdOnDate);
    }

    public String getLastChangedDate() {
        return lastChangedDate.get();
    }
    public SimpleStringProperty lastChangedDateProperty() {
        return lastChangedDate;
    }

    public void setLastChangedDate(String lastChangedDate) {
        this.lastChangedDate.set(lastChangedDate);
    }


    // Initialisation methods


    // Other methods
    private String calculateFileNameWithoutExtension(Path pathToParse) {
        String fileNamePathString = pathToParse.getFileName().toString();
        int charIndex = fileNamePathString.indexOf('.');
        return fileNamePathString.substring(0, charIndex);
    }

    private String calculateCreationDateAsString(Path pathToParse) throws IOException {
        BasicFileAttributes fileAttributes = Files.readAttributes(pathToParse, BasicFileAttributes.class);
        return fileAttributes.creationTime().toString();
    }

    private String calculateLastChangedDateAsString(Path pathToParse) throws IOException {
        BasicFileAttributes fileAttributes = Files.readAttributes(pathToParse, BasicFileAttributes.class);
        return fileAttributes.lastModifiedTime().toString();
    }

    private boolean validatePathAsFile(Path pathToParse){
        File tempFile = pathToParse.toFile();
        return tempFile.exists();
    }

}
