package utils;

import com.airhacks.afterburner.views.FXMLView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayDeque;
import java.util.Vector;

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
        //generateNewStage();
        Scene newViewScene = new Scene(newView.getView());
        newViewScene.setFill(Color.BLACK);
        getMainStage().hide();
        getMainStage().setTitle(newTitle);
        getMainStage().setScene(newViewScene);
        getMainStage().setMinHeight(500);
        getMainStage().setHeight(500);
        getMainStage().setMinWidth(700);
        getMainStage().setWidth(700);
        getMainStage().show();
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

    public static void createChildStage(String title, Parent childStageContent) {
        double[] dummyDimensions = {-1,-1,-1,-1,-1,-1};
        StageUtils.createChildStage(title, childStageContent, dummyDimensions);
    }


    private static void createChildStage(String title, Parent childStageContent, double[] displayDimensions) {
        if (StageUtils.getSubStages() == null) {
            StageUtils.setSubStages(new ArrayDeque<Stage>());
        }
        Stage childStage = new Stage();
        if (displayDimensions[0] > 0) {
            childStage.setMinHeight(displayDimensions[0]);
        }
        if (displayDimensions[1] > 0) {
            childStage.setMinWidth(displayDimensions[1]);
        }
        if (displayDimensions[2] > 0) {
            childStage.setHeight(displayDimensions[2]);
        }
        if (displayDimensions[3] > 0) {
            childStage.setWidth(displayDimensions[3]);
        }
        if (displayDimensions[4] > 0) {
            childStage.setMaxWidth(displayDimensions[4]);
        }
        if (displayDimensions[5] > 0) {
            childStage.setMaxWidth(displayDimensions[5]);
        }
        childStage.setTitle(title);
        childStage.initOwner(StageUtils.getMainStage());
        childStage.initModality(Modality.APPLICATION_MODAL);
        Scene internalScene = new Scene(childStageContent);
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