package view.sharedviewcomponents;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ScrimRectangle extends Rectangle {


    // Variables
    private Pane parentPane;
    private FadeTransition fadeTransition;
    private SimpleBooleanProperty onDisplay;


    // Constructors
    public ScrimRectangle(Pane parentPane) {
        super(10,10,Color.valueOf("0x00000033"));
        this.setVisible(false);
        this.setDisable(true);
        this.parentPane = parentPane;
        onDisplay = new SimpleBooleanProperty(false);
        onDisplay.addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                this.setDisable(true);
                this.setVisible(false);
            }
        });
        fadeTransition= new FadeTransition(Duration.millis(500), this);
        fadeTransition.setOnFinished(event -> {
            if(onDisplay.getValue()) {
                onDisplay.set(false);
            } else {
                onDisplay.set(true);
            }
        });
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public void showScrim() {
        this.setDisable(false);
        this.setVisible(true);
        this.setHeight(parentPane.getHeight());
        this.setWidth(parentPane.getWidth());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public void hideScrim() {
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }


}
