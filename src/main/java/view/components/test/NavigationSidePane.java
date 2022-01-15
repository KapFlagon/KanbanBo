package view.components.test;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class NavigationSidePane extends VBox {


    // Variables
    private SimpleBooleanProperty paneExpanded;
    private ObservableList<NavigationLabel> navigationLabelViewLists;


    // Constructors
    public NavigationSidePane() {
        initialize();
    }

    public NavigationSidePane(double spacing) {
        super(spacing);
        initialize();
    }

    public NavigationSidePane(Node... children) {
        super(children);
        initialize();
    }

    public NavigationSidePane(double spacing, Node... children) {
        super(spacing, children);
        initialize();
    }


    // Getters and Setters


    // Initialisation methods
    private void initialize() {
        paneExpanded = new SimpleBooleanProperty(true);
        navigationLabelViewLists = FXCollections.observableArrayList();
    }


    // Other methods
    public void addNavigationLabelView(NavigationLabel navigationLabelView) {
        this.getChildren().add(navigationLabelView);
    }


}
