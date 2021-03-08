package utils;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageUtils {

    // Static reference to main stage to allow pop-ups.
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage newMainStage) {
        StageUtils.mainStage = newMainStage;
    }

    public static void changeScene(String newTitle, FXMLView newView) {
        Scene newViewScene = new Scene(newView.getView());
        getMainStage().setTitle(newTitle);
        getMainStage().setScene(newViewScene);
    }

}