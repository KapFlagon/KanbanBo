package view.screens.mainscreen;

import domain.entities.project.ObservableWorkspaceProject;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.manage.ManagePresenter;
import view.screens.mainscreen.subviews.manage.ManageView;
import view.screens.mainscreen.subviews.workspace.WorkspacePresenter;
import view.screens.mainscreen.subviews.workspace.WorkspaceView;
import view.screens.startscreen.StartScreenView;

import javax.inject.Inject;
import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainScreenPresenter implements Initializable {

    // JavaFx injected fields
    @FXML
    private TabPane mainScreenTabPane;
    @FXML
    private Tab manageTab;
    //@FXML
    //private Tab templatesTab;
    //@FXML
    //private Tab analyticsTab;
    //@FXML
    //private TabPane analyticsSubTabPane;
    @FXML
    private Tab workspaceTab;
    @FXML
    private MenuItem sourceCodeRepoMenuItem;

    // Other variables and fields
    @Inject
    KanbanBoDataService kanbanBoDataService;

    private ManageView manageView;
    private ManagePresenter managePresenter;
    private WorkspaceView workspaceView;
    private WorkspacePresenter workspacePresenter;
    private SimpleBooleanProperty closingFile;

    // Constructors

    // Getters & Setters
    public SimpleBooleanProperty closingFileProperty() {
        return closingFile;
    }

    // Initialisation methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTabIcons();
        manageView = new ManageView();
        managePresenter = (ManagePresenter) manageView.getPresenter();
        manageTab.setContent(manageView.getView());

        workspaceView = new WorkspaceView();
        workspacePresenter = (WorkspacePresenter) workspaceView.getPresenter();
        workspaceTab.setContent(workspaceView.getView());

        kanbanBoDataService.getWorkspaceProjectsList().addListener((ListChangeListener<ObservableWorkspaceProject>) c -> {
            while(c.next()) {
                if(c.wasAdded()) {
                    mainScreenTabPane.getSelectionModel().select(workspaceTab);
                }
            }
        });
        closingFile = new SimpleBooleanProperty(false);
    }


    // UI events
    @FXML private void closeDb() {
        //StageUtils.changeMainScene("KanbanBo - Database file selection", new StartScreenView());
        closingFile.set(true);
        closingFile.set(false);
    }

    @FXML private void exitProgram() {
        System.out.println("Exiting program...");
        Platform.exit();
        System.exit(0);
    }

    @FXML private void openOnlineSourceCodeRepo() {
        //System.out.println("Redirect to online repository");
        Desktop dt = Desktop.getDesktop();
        try {
            dt.browse(new URI("http://www.github.com/kapflagon/kanbanbo"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    // Other methods
    private void initTabIcons() {
        //TODO need to replace all "getResource()" calls with "getResourceAsStream()" to avoid JAR file issues for reading resources.
        ImageView manageImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/icons/topic/materialicons/black/res/drawable-mdpi/baseline_topic_black_18.png")).toExternalForm());
        ImageView workspaceImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/icons/handyman/materialicons/black/res/drawable-mdpi/baseline_handyman_black_18.png")).toExternalForm());
        //ImageView templatesImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/icons/square_foot/materialicons/black/res/drawable-mdpi/baseline_square_foot_black_18.png")).toExternalForm());
        //ImageView analyticsImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/icons/assessment/materialicons/black/res/drawable-mdpi/baseline_assessment_black_18.png")).toExternalForm());
        manageTab.setGraphic(manageImageView);
        workspaceTab.setGraphic(workspaceImageView);
        //templatesTab.setGraphic(templatesImageView);
        //analyticsTab.setGraphic(analyticsImageView);
    }
}
