package view.screens.startscreen;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import user.preferences.UserPreferences;
import utils.database.DatabaseUtils;
import utils.FileChooserUtils;
import utils.FileCreationUtils;
import utils.StageUtils;
import view.screens.mainscreen.MainScreenPresenter;
import view.screens.mainscreen.MainScreenView;
import view.screens.startscreen.subviews.recentdbfileitemview.RecentFileEntryPresenter;
import view.screens.startscreen.subviews.recentdbfilesview.RecentFilesListPresenter;
import view.screens.startscreen.subviews.recentdbfilesview.RecentFilesListView;
import view.sharedcomponents.popups.info.DatabaseCreationProgressPresenter;
import view.sharedcomponents.popups.info.DatabaseCreationProgressView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;

public class StartScreenPresenter implements Initializable {

    // FXML injected variables
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button newDbButton;
    @FXML
    private Button browseForDbButton;
    @FXML
    private CheckBox autoLoadCheckBox;
    @FXML
    private MenuItem newDbMenuItem;
    @FXML
    private MenuItem browseForDbMenuItem;
    @FXML
    private CheckMenuItem autoLoadCheckMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem repoMenuItem;

    // Other variables



    // Constructors


    // Getters and Setters
    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public Button getNewDbButton() {
        return newDbButton;
    }

    public void setNewDbButton(Button newDbButton) {
        this.newDbButton = newDbButton;
    }

    public Button getBrowseForDbButton() {
        return browseForDbButton;
    }

    public void setBrowseForDbButton(Button browseForDbButton) {
        this.browseForDbButton = browseForDbButton;
    }

    public MenuItem getNewDbMenuItem() {
        return newDbMenuItem;
    }

    public void setNewDbMenuItem(MenuItem newDbMenuItem) {
        this.newDbMenuItem = newDbMenuItem;
    }

    public MenuItem getBrowseForDbMenuItem() {
        return browseForDbMenuItem;
    }

    public void setBrowseForDbMenuItem(MenuItem browseForDbMenuItem) {
        this.browseForDbMenuItem = browseForDbMenuItem;
    }

    public MenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public void setExitMenuItem(MenuItem exitMenuItem) {
        this.exitMenuItem = exitMenuItem;
    }

    public MenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public void setAboutMenuItem(MenuItem aboutMenuItem) {
        this.aboutMenuItem = aboutMenuItem;
    }

    public MenuItem getRepoMenuItem() {
        return repoMenuItem;
    }

    public void setRepoMenuItem(MenuItem repoMenuItem) {
        this.repoMenuItem = repoMenuItem;
    }


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //StageUtils.getMainStage().setTitle("KanbanBo - Database file selection");
        System.out.println("Start screen loaded");
        autoLoadCheckBox.setSelected(UserPreferences.getSingletonInstance().isOpeningMostRecentAutomatically());
        autoLoadCheckMenuItem.setSelected(UserPreferences.getSingletonInstance().isOpeningMostRecentAutomatically());
        RecentFilesListView rflv = new RecentFilesListView();
        RecentFilesListPresenter rflp = (RecentFilesListPresenter) rflv.getPresenter();
        rflp.itemBeingOpenedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    File selectedRecentFile = rflp.getSelectedPath().toFile();
                    if(selectedRecentFile.exists()) {
                        DatabaseUtils.setActiveDatabaseFile(selectedRecentFile);
                        System.out.println("DatabaseUtils updated to: " + DatabaseUtils.getActiveDatabaseFile().toString());
                        try {
                            UserPreferences.getSingletonInstance().addRecentFilePath(selectedRecentFile.toPath());
                        } catch (BackingStoreException e) {
                            e.printStackTrace();
                        }
                        moveToMainSceneView();
                    } else {
                        // TODO need to account for the scenario where a file gets deleted between the time it is evaluated by the UI and the user clicks it.
                    }
                }
            }
        });
        rflp.setRecentFilePathList(UserPreferences.getSingletonInstance().getRecentFilePaths());
        borderPane.setCenter(rflv.getView());
        BorderPane.setMargin(rflv.getView(), new Insets(5,5,5,5));
    }


    // Other methods
    public void createDbFile() throws IOException, SQLException, BackingStoreException {
        System.out.println("creating");
        File newFile = FileChooserUtils.createFilePopup();
        if(newFile != null) {
            FileCreationUtils.createEmptyDatabaseFile(newFile);
            DatabaseUtils.setActiveDatabaseFile(newFile);
            //DatabaseUtils.initDatabaseTablesInFile();
            DatabaseCreationProgressView progressView= new DatabaseCreationProgressView();
            DatabaseCreationProgressPresenter progressPresenter = (DatabaseCreationProgressPresenter) progressView.getPresenter();
            StageUtils.createChildStage("Database creation", progressView.getView());
            StageUtils.showAndWaitOnSubStage();
            System.out.println("DatabaseUtils updated to: " + DatabaseUtils.getActiveDatabaseFile().toString());
            UserPreferences.getSingletonInstance().addRecentFilePath(newFile.toPath());
            moveToMainSceneView();
        } else {
            // Accounting for scenario where user cancels file creation.
            System.out.println("File creation cancelled");
        }
        // TODO Insert some kind of logging here.
    }


    public void browseForDbFile() throws BackingStoreException {
        System.out.println("browsing for database file");
        File selectedFile = FileChooserUtils.openFilePopup();
        if (selectedFile != null) {
            // Accounting for scenario where user cancels opening file.
            DatabaseUtils.setActiveDatabaseFile(selectedFile);
            System.out.println("DatabaseUtils updated to: " + DatabaseUtils.getActiveDatabaseFile().toString());
            UserPreferences.getSingletonInstance().addRecentFilePath(selectedFile.toPath());
            moveToMainSceneView();
        } else {
            System.out.println("File opening cancelled");
        }
        // TODO Insert some kind of logging for selected file here.
    }


    public void exitApplication() {
        System.out.println("Exiting application");
        Platform.exit();
        System.exit(0);
    }


    public void displayApplicationInfo() {
        System.out.println("Info output");
    }


    public void openOnlineCodeRepo() {
        System.out.println("Redirect to online repository");
        //Desktop dt = Desktop.getDesktop();
        //try {
        //    dt.browse(new URI("http://www.github.com"));
        //} catch (Exception exception) {
        //    exception.printStackTrace();
        //}
    }

    private void moveToMainSceneView() {
        //MainScreenView mainScreenView = new MainScreenView();
        MainScreenView view = new MainScreenView();
        String databaseFileTitle = DatabaseUtils.getActiveDatabaseFile().getName();
        StageUtils.changeMainScene("KanbanBo - Project Database '" + databaseFileTitle + "'", view);
    }

    public void autoLoadCheckMenuItemClicked() throws BackingStoreException {
        updateAutoLoad(autoLoadCheckMenuItem.isSelected());
        autoLoadCheckBox.setSelected(autoLoadCheckMenuItem.isSelected());
    }

    public void autoLoadCheckBoxClicked() throws BackingStoreException {
        updateAutoLoad(autoLoadCheckBox.isSelected());
        autoLoadCheckMenuItem.setSelected(autoLoadCheckBox.isSelected());
    }

    public void updateAutoLoad(boolean value) throws BackingStoreException {
        UserPreferences.getSingletonInstance().setOpeningMostRecentAutomatically(value);
        System.out.println("autoload value: " + value);
    }

}
