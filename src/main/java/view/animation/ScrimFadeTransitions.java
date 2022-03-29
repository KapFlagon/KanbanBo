package view.animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScrimFadeTransitions {


    // Variables
    private FadeTransition fadeTransition;
    private double lowValue;
    private double highValue;


    // Constructors
    public ScrimFadeTransitions(Duration duration, Node node) {
        this(duration, node, 0.0, 1.0);
    }

    public ScrimFadeTransitions(Duration duration, Node node, double lowValue, double highValue) {
        this.fadeTransition = new FadeTransition(duration, node);
        this.lowValue = lowValue;
        this.highValue = highValue;
    }

    // Getters and Setters
    public void setLowValue(double lowValue) {
        this.lowValue = lowValue;
    }

    public void setHighValue(double highValue) {
        this.highValue = highValue;
    }

    public void setDuration(Duration duration) {
        this.fadeTransition.setDuration(duration);
    }

    public void setNode(Node node) {
        this.fadeTransition.setNode(node);
    }


    // Initialisation methods


    // Other methods
    public void fadeIn() {
        fadeTransition.setFromValue(lowValue);
        fadeTransition.setToValue(highValue);
        fadeTransition.play();
    }

    public void fadeOut() {
        fadeTransition.setFromValue(highValue);
        fadeTransition.setToValue(lowValue);
        fadeTransition.play();
    }


}
