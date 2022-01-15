package view.components.test;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;
import view.sharedviewcomponents.popups.EditorDataMode;

public abstract class DataPane extends VBox {


    // Variables
    protected EditorDataMode editorDataMode;
    protected SimpleStringProperty dataPaneHeaderText;
    protected SimpleBooleanProperty changesPending;
    protected SimpleBooleanProperty validationErrors;
    protected SimpleBooleanProperty onDisplay;


    // Constructors


    // Getters and Setters
    public boolean isOnDisplay() {
        return onDisplay.get();
    }
    public SimpleBooleanProperty onDisplayProperty() {
        return onDisplay;
    }


    // Initialisation methods
    private void initialize() {
        editorDataMode = EditorDataMode.CREATION;
        dataPaneHeaderText = new SimpleStringProperty();
        changesPending = new SimpleBooleanProperty(false);
        validationErrors = new SimpleBooleanProperty(false);
        onDisplay = new SimpleBooleanProperty(false);
    }


    protected void initializeDataPaneState() {
        switch (editorDataMode) {
            case CREATION:
                prepareViewForCreation();
                break;
            case DISPLAY:
                prepareViewForDisplay();
                break;
            case EDITING:
                prepareViewForEditing();
                break;
        }
    }


    // Other methods
    protected abstract void prepareViewForCreation();
    protected abstract void prepareViewForDisplay();
    protected abstract void prepareViewForEditing();


}
