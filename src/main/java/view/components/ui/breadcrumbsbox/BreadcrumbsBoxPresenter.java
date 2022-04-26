package view.components.ui.breadcrumbsbox;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.*;

public class BreadcrumbsBoxPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox hBox;
    private Label endLabel;


    // Other variables
    private String separatorCharacter;
    private ObservableList<Hyperlink> crumbs;
    private ObservableList<StackPane> visuals;

    // Constructors

    // Getters & Setters

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        separatorCharacter = Character.toString(resources.getString("separator.character").charAt(0));
        crumbs = FXCollections.observableArrayList();
        visuals = FXCollections.observableArrayList();
        Bindings.bindContent(hBox.getChildren(), visuals);
        crumbs.addListener((ListChangeListener<Hyperlink>) c -> {
            if(c.next()) {
                if(c.wasAdded()) {
                    for(Hyperlink link : c.getAddedSubList()) {
                        createNewCrumb(link);
                    }
                } else if (c.wasRemoved()) {
                    for(Hyperlink link : c.getRemoved()) {
                        System.out.println(crumbs.indexOf(link));
                        int removalStart = crumbs.indexOf(link);
                        int removalEnd = (crumbs.size() - 1);
                        crumbs.remove(removalStart, removalEnd);
                        visuals.remove(removalStart, removalEnd);
                    }
                } else if (c.wasPermutated()) {

                }
            }
        });
    }

    // UI event methods


    // Other methods
    public void addHyperlink(Hyperlink hyperlink) {
        // TODO need to add some kind of boolean property that enables hiding/showing the hyperlink/label depending on the position in the list.
        crumbs.add(hyperlink);
        hyperlink.onActionProperty().addListener(f -> {
            crumbs.remove(hyperlink);
        });
    }

    private void createNewCrumb(Hyperlink hyperlink) {
        HBox crumbItem = new HBox();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(hyperlink);
        Label label = new Label(hyperlink.getText());
        stackPane.getChildren().add(label);
        crumbItem.getChildren().add(stackPane);
        crumbItem.getChildren().add(new Label(separatorCharacter));
    }


}