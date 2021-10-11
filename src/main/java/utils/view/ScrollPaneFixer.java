package utils.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class ScrollPaneFixer {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public static void fixBlurryScrollPan(ScrollPane scrollPane) {
        scrollPane.skinProperty().addListener(((observable, oldValue, newValue) -> {
            setEmbeddedStackPaneCache(scrollPane);
        }));
    }

    private static void setEmbeddedStackPaneCache(ScrollPane scrollPane) {
        StackPane stackPane = (StackPane) scrollPane.lookup("ScrollPane .viewport");
        stackPane.setCache(false);
    }

}
