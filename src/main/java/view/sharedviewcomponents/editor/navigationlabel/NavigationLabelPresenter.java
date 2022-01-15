package view.sharedviewcomponents.editor.navigationlabel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class NavigationLabelPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label headerLbl;
    @FXML
    private Label pendingChangesLbl;
    @FXML
    private Label hasErrorsLbl;


    // Other variables
    private SimpleBooleanProperty selected;
    private EventHandler<MouseEvent> mouseClickedEventEventHandler;

    // Constructors

    // Getters & Setters

    public Label getHeaderLbl() {
        return headerLbl;
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

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selected = new SimpleBooleanProperty(false);
    }

    // UI event methods
    @FXML
    private void clicked(MouseEvent mouseEvent) {
        mouseClickedEventEventHandler.handle(mouseEvent);
        selected.set(true);
        mouseEvent.consume();
    }

    // Other methods



}