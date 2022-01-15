package view.components.test;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import view.sharedviewcomponents.popups.EditorDataMode;

public abstract class EditorContentPane extends CoreContentPane {


    // Variables
    protected EditorDataMode editorDataMode;
    protected Button submitBtn;
    protected Button cancelBtn;
    protected SimpleBooleanProperty dataErrors;
    protected SimpleBooleanProperty changesPending;


    // Constructors
    public EditorContentPane() {
        super();
        initializeButtons();
    }

    public EditorContentPane(Node center) {
        super(center);
        initializeButtons();
    }

    public EditorContentPane(Node center, Node top, Node right, Node bottom, Node left) {
        super(center, top, right, bottom, left);
        initializeButtons();
    }


    // Getters and Setters
    public Button getSubmitBtn() {
        return submitBtn;
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }


    // Initialisation methods
    private void initializeButtons() {
        ButtonBar buttonBar = new ButtonBar();
        submitBtn = new Button();
        cancelBtn = new Button();
        ButtonBar.setButtonData(submitBtn, ButtonBar.ButtonData.OK_DONE);
        ButtonBar.setButtonData(cancelBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
        this.setBottom(buttonBar);
    }


    // Other methods
    @Override
    protected void addDataPane(DataPane dataPane) {
        super.addDataPane(dataPane);
    }

    @Override
    protected NavigationLabel createNavigationLabel(DataPane dataPane) {
        return super.createNavigationLabel(dataPane);
    }

}
