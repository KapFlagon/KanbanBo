package view.sharedviewcomponents.popups;

public abstract class DetailsWindowPresenter {


    // Variables
    protected EditorDataMode editorDataMode;

    // Constructors


    // Getters and Setters
    public void setEditorDataMode(EditorDataMode editorDataMode) {
        this.editorDataMode = editorDataMode;
        initializeEditor();
    }


    // Initialisation methods
    protected void initializeEditor() {
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
