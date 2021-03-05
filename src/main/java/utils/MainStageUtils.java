package utils;

import javafx.stage.Stage;

public class MainStageUtils {

    // Static reference to main stage to allow pop-ups.
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage newMainStage) {
        MainStageUtils.mainStage = newMainStage;
    }

}
