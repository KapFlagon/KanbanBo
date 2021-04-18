package utils;

import com.airhacks.afterburner.views.FXMLView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayDeque;

public class StageUtils {

    // Variables
    private static Stage mainStage;         // Static reference to main stage to allow pop-ups.
    private static ArrayDeque<Stage> subStages;  // Queue of all sub-stages to be processed in order


    // Getters and Setters
    public static Stage getMainStage() {
        return mainStage;
    }
    public static void setMainStage(Stage newMainStage) {
        newMainStage.setOnCloseRequest(event -> {
            System.out.println("Exiting program...");
            Platform.exit();
            System.exit(0);
        });
        StageUtils.mainStage = newMainStage;
    }

    public static ArrayDeque<Stage> getSubStages() {
        return subStages;
    }
    public static void setSubStages(ArrayDeque<Stage> subStages) {
        StageUtils.subStages = subStages;
    }

    // Other methods
    public static void changeMainScene(String newTitle, FXMLView newView) {
        generateNewStage();
        Scene newViewScene = new Scene(newView.getView());
        StageUtils.getMainStage().setTitle(newTitle);
        StageUtils.getMainStage().setScene(newViewScene);
        StageUtils.getMainStage().show();
    }

    private static void generateNewStage() {
        getMainStage().hide();
        Stage newStage = new Stage();
        setMainStage(newStage);
    }

    public static void addSubStageToDeque(Stage subStage) {
        StageUtils.getSubStages().addLast(subStage);
    }

    public static void removeSubStageFromDeque(Stage subStage) {
        StageUtils.getSubStages().remove(subStage);
    }

    public static void removeLastSubStageFromDeque() {
        StageUtils.getSubStages().removeLast();
    }

    public static void createChildStage(String title, Parent parent) {
        if (StageUtils.getSubStages() == null) {
            StageUtils.setSubStages(new ArrayDeque<Stage>());
        }
        Stage childStage = new Stage();
        childStage.setTitle(title);
        childStage.initOwner(StageUtils.getMainStage());
        childStage.initModality(Modality.APPLICATION_MODAL);
        Scene internalScene = new Scene(parent);
        childStage.setScene(internalScene);
        StageUtils.addSubStageToDeque(childStage);
    }

    public static void showSubStage() {
        StageUtils.getSubStages().peekLast().show();
    }

    public static void showAndWaitOnSubStage() {
        StageUtils.getSubStages().peekLast().showAndWait();
    }

    public static void closeSubStage() {
        StageUtils.getSubStages().peekLast().close();
        StageUtils.removeLastSubStageFromDeque();
    }

    public static void hideSubStage() {
        StageUtils.getSubStages().peekLast().hide();
    }

}