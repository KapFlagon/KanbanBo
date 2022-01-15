package view.sharedviewcomponents.editor.datapane;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import view.sharedviewcomponents.popups.EditorDataMode;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class DataPanePresenter implements Initializable {

    // JavaFX injected node variables

    // Other variables
    protected EditorDataMode editorDataMode;
    protected SimpleStringProperty dataPaneHeaderText;
    protected SimpleBooleanProperty changesPending;
    protected SimpleBooleanProperty validationErrors;

    // Constructors

    // Getters & Setters
    public void setEditorDataMode(EditorDataMode editorDataMode) {
        this.editorDataMode = editorDataMode;
        initializeDataPaneState();
    }

    public String getDataPaneHeaderText() {
        return dataPaneHeaderText.get();
    }
    public SimpleStringProperty dataPaneHeaderTextProperty() {
        return dataPaneHeaderText;
    }

    public boolean hasChangesPending() {
        return changesPending.get();
    }
    public SimpleBooleanProperty changesPendingProperty() {
        return changesPending;
    }

    public boolean hasValidationErrors() {
        return validationErrors.get();
    }
    public SimpleBooleanProperty validationErrorsProperty() {
        return validationErrors;
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validationErrors = new SimpleBooleanProperty(false);
        changesPending = new SimpleBooleanProperty(false);
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

    // UI event methods

    // Other methods

    protected abstract void prepareViewForCreation();
    protected abstract void prepareViewForDisplay();
    protected abstract void prepareViewForEditing();


}