package view.components.test;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class NavigationLabel extends VBox {

    // Variables
    private Label titleLbl;
    private Label pendingChangesLbl;
    private Label hasErrorsLbl;
    private SimpleBooleanProperty selected;
    private EventHandler<MouseEvent> mouseClickedEventEventHandler;


    // Constructors
    public NavigationLabel() {
        initialize();
    }

    public NavigationLabel(double spacing) {
        super(spacing);
        initialize();
    }

    public NavigationLabel(Node... children) {
        super(children);
        initialize();
    }

    public NavigationLabel(double spacing, Node... children) {
        super(spacing, children);
        initialize();
    }


    // Getters and Setters
    public Label getTitleLbl() {
        return titleLbl;
    }

    public Label getPendingChangesLbl() {
        return pendingChangesLbl;
    }

    public Label getHasErrorsLbl() {
        return hasErrorsLbl;
    }

    public boolean isSelected() {
        return selected.get();
    }
    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setMouseClickedEventEventHandler(EventHandler<MouseEvent> mouseClickedEventEventHandler) {
        this.mouseClickedEventEventHandler = mouseClickedEventEventHandler;
    }


    // Initialisation methods
    public void initialize() {
        titleLbl = new Label();
        pendingChangesLbl = new Label();
        hasErrorsLbl = new Label();
        selected = new SimpleBooleanProperty(false);
        this.setOnMouseClicked(this::clicked);
    }

    // Other methods
    private void clicked(MouseEvent mouseEvent) {
        mouseClickedEventEventHandler.handle(mouseEvent);
        selected.set(true);
        mouseEvent.consume();
    }


}
