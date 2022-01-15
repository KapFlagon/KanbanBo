package view.components.test;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ContentHeaderPane extends AnchorPane{


    // Variables
    private Button navigationDrawerExpansionBtn;
    private Label headerLbl;
    private SimpleBooleanProperty navigationPaneExpandedProperty;


    // Constructors
    public ContentHeaderPane() {
        initializeHeader();
    }


    // Getters and Setters
    public boolean getNavigationPaneExpandedProperty() {
        return navigationPaneExpandedProperty.get();
    }
    public SimpleBooleanProperty navigationPaneExpandedPropertyProperty() {
        return navigationPaneExpandedProperty;
    }

    // Initialisation methods
    private void initializeHeader() {
        navigationDrawerExpansionBtn = new Button();
        headerLbl = new Label();
        AnchorPane.setLeftAnchor(navigationDrawerExpansionBtn, 4.0);
        AnchorPane.setLeftAnchor(headerLbl, 100.0);
        this.getChildren().addAll(navigationDrawerExpansionBtn, headerLbl);
    }


    // Other methods


}
