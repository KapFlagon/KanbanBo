package view.screens.startscreen;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import userpreferences.UserPreferences;
import utils.database.DatabaseUtils;
import utils.FileChooserUtils;
import utils.FileCreationUtils;
import utils.StageUtils;
import view.animation.ScrimFadeTransitions;
import view.screens.mainscreen.MainScreenView;
import view.screens.startscreen.subviews.recentdbfilesview.RecentFilesListPresenter;
import view.screens.startscreen.subviews.recentdbfilesview.RecentFilesListView;
import view.sharedviewcomponents.popups.info.DatabaseCreationProgressPresenter;
import view.sharedviewcomponents.popups.info.DatabaseCreationProgressView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
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

    // Other variables
    private ScrimFadeTransitions scrimFadeTransitions;



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


    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //StageUtils.getMainStage().setTitle("KanbanBo - Database file selection");
        initButtonImages();
        System.out.println("Start screen loaded");
        autoLoadCheckBox.setSelected(UserPreferences.getSingletonInstance().isOpeningMostRecentAutomatically());

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
        rflp.itemBeingDeletedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    File fileForDeletion = rflp.getSelectedPath().toFile();
                    boolean deletionResult = deleteDbFile(fileForDeletion);
                    if(deletionResult) {
                        rflp.getRecentFilePathList().remove(rflp.getSelectedPath());
                        UserPreferences.getSingletonInstance().removeRecentFilePath(rflp.getSelectedPath());
                    }
                }
            }
        });
        rflp.setRecentFilePathList(UserPreferences.getSingletonInstance().getRecentFilePaths());
        borderPane.setCenter(rflv.getView());
        //BorderPane.setMargin(rflv.getView(), new Insets(5,5,5,5));

        scrimFadeTransitions = new ScrimFadeTransitions(Duration.millis(350), borderPane, 0.4, 1.0);
    }

    private void initButtonImages() {
        ImageView newDbImageView = new ImageView(getClass().getResource("/icons/add/materialicons/black/res/drawable-mdpi/baseline_add_black_18.png").toExternalForm());
        ImageView searchDbImageView = new ImageView(getClass().getResource("/icons/search/materialicons/black/res/drawable-mdpi/baseline_search_black_18.png").toExternalForm());
        newDbButton.setGraphic(newDbImageView);
        browseForDbButton.setGraphic(searchDbImageView);
    }


    // Other methods
    @FXML
    private void createDbFile() throws IOException, SQLException, BackingStoreException {
        System.out.println("creating");

        scrimFadeTransitions.fadeOut();
        File newFile = FileChooserUtils.createFilePopup();
        if(newFile != null) {
            createFile(newFile);
            openFile(newFile);
        } else {
            // Accounting for scenario where user cancels file creation.
            System.out.println("File creation cancelled");
        }
        scrimFadeTransitions.fadeIn();
        // TODO Insert some kind of logging here.
    }

    @FXML
    private boolean deleteDbFile(File fileForDeletion) {
        // TODO Perform logging here
        System.out.println("Deleting file: " + fileForDeletion.getName());
        return fileForDeletion.delete();
    }


    @FXML
    private void browseForDbFile() throws BackingStoreException {
        System.out.println("browsing for database file");
        scrimFadeTransitions.fadeOut();
        File selectedFile = FileChooserUtils.openFilePopup();
        if (selectedFile != null && isValidDbFile(selectedFile.toString())) {
            // Accounting for scenario where user cancels opening file.
            openFile(selectedFile);
        } else {
            System.out.println("File opening cancelled");
        }
        scrimFadeTransitions.fadeIn();
        // TODO Insert some kind of logging for selected file here.
    }


    @FXML
    private void exitApplication() {
        System.out.println("Exiting application");
        Platform.exit();
        System.exit(0);
    }


    @FXML
    private void displayApplicationInfo() {
        System.out.println("Info output");
    }


    @FXML
    private void openOnlineSourceCodeRepo(ActionEvent event) {
        //System.out.println("Redirect to online repository");
        Desktop dt = Desktop.getDesktop();
        try {
            dt.browse(new URI("http://www.github.com/kapflagon/kanbanbo"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        event.consume();
    }

    private void moveToMainSceneView() {
        //MainScreenView mainScreenView = new MainScreenView();
        MainScreenView view = new MainScreenView();
        String databaseFileTitle = DatabaseUtils.getActiveDatabaseFile().getName();
        StageUtils.changeMainScene("KanbanBo - Project Database '" + databaseFileTitle + "'", view);
    }

    @FXML
    private void autoLoadCheckBoxClicked() throws BackingStoreException {
        updateAutoLoad(autoLoadCheckBox.isSelected());
    }

    @FXML
    void onDragoOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
    }

    @FXML
    void onDragDropped(DragEvent event) throws BackingStoreException {
        Dragboard dragboard = event.getDragboard();
        if(event.getDragboard().hasFiles()) {
            File selectedFile = event.getDragboard().getFiles().get(0);
            if (isValidDbFile(selectedFile.toString())) {
                // Accounting for scenario where user cancels opening file.
                dragboard.clear();
                event.consume();
                openFile(selectedFile);
            }
        }
    }

    public void updateAutoLoad(boolean value) throws BackingStoreException {
        UserPreferences.getSingletonInstance().setOpeningMostRecentAutomatically(value);
        System.out.println("autoload value: " + value);
    }

    private boolean isValidDbFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (extension.equals("kbbdb")) {
            return true;
        } else {
            return false;
        }
    }

    private void createFile(File newFile) throws IOException, BackingStoreException {
        FileCreationUtils.createEmptyDatabaseFile(newFile);
        DatabaseUtils.setActiveDatabaseFile(newFile);
        DatabaseCreationProgressView progressView= new DatabaseCreationProgressView();
        DatabaseCreationProgressPresenter progressPresenter = (DatabaseCreationProgressPresenter) progressView.getPresenter();
        StageUtils.createChildStage("Database creation", progressView.getView());
        StageUtils.showAndWaitOnSubStage();
        openFile(newFile);
    }

    private void openFile(File fileToOpen) throws BackingStoreException {
        if (isValidDbFile(fileToOpen.toString())) {
            // Accounting for scenario where user cancels opening file.
            DatabaseUtils.setActiveDatabaseFile(fileToOpen);
            System.out.println("DatabaseUtils updated to: " + DatabaseUtils.getActiveDatabaseFile().toString());
            UserPreferences.getSingletonInstance().addRecentFilePath(fileToOpen.toPath());
            moveToMainSceneView();
        }
    }

}
