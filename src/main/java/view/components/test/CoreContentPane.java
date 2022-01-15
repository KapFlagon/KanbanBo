package view.components.test;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;


import java.util.Iterator;
import java.util.Map;

public abstract class CoreContentPane extends BorderPane {


    // Variables
    protected ContentHeaderPane contentHeaderPane;
    protected NavigationSidePane navigationSidePane;
    protected ScrollPane centreScrollPane;
    protected NavigationLabel selectedNavigationLabel;
    protected Map<NavigationLabel, DataPane> navLabelDataPaneMap;


    // Constructors
    public CoreContentPane() {
        initializeStructure();
    }

    public CoreContentPane(Node center) {
        super(center);
        initializeStructure();
    }

    public CoreContentPane(Node center, Node top, Node right, Node bottom, Node left) {
        super(center, top, right, bottom, left);
        initializeStructure();
    }


    // Getters and Setters


    // Initialisation methods
    private void initializeStructure() {
        initializeContentHeaderPane();
        initializeNavigationDrawer();
        initializeBaseCentreContent();
    }

    private void initializeContentHeaderPane() {
        contentHeaderPane = new ContentHeaderPane();
        this.setTop(contentHeaderPane);
    }

    private void initializeNavigationDrawer() {
        navigationSidePane = new NavigationSidePane();
        this.setLeft(navigationSidePane);
    }

    private void initializeBaseCentreContent() {
        centreScrollPane = new ScrollPane();
        this.setCenter(centreScrollPane);
    }


    // Other methods
    protected void addDataPane(DataPane dataPane) {
        NavigationLabel navigationLabel = createNavigationLabel(dataPane);
        navigationLabel.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                selectedNavigationLabel = navigationLabel;
                for(Iterator<NavigationLabel> navigationLabelIterator = navLabelDataPaneMap.keySet().iterator(); navigationLabelIterator.hasNext();) {
                    NavigationLabel iteratedNavigationLabel = navigationLabelIterator.next();
                    if(iteratedNavigationLabel.equals(selectedNavigationLabel)) {
                        // Do nothing
                    } else {
                        iteratedNavigationLabel.selectedProperty().set(false);
                        DataPane iteratorDataPane = navLabelDataPaneMap.get(iteratedNavigationLabel);
                        iteratorDataPane.onDisplayProperty().set(false);
                    }
                }
                dataPane.onDisplayProperty().set(true);
                centreScrollPane.setContent(dataPane);
            }
        });
        navigationSidePane.addNavigationLabelView(navigationLabel);
        navLabelDataPaneMap.put(navigationLabel, dataPane);
    }


    protected NavigationLabel createNavigationLabel(DataPane dataPane) {
        NavigationLabel navigationLabel = new NavigationLabel();
        navigationLabel.getHasErrorsLbl().visibleProperty().set(false);
        navigationLabel.getPendingChangesLbl().visibleProperty().set(false);
        return navigationLabel;
    }



}
