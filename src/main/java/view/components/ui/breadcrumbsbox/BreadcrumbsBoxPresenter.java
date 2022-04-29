package view.components.ui.breadcrumbsbox;

import javafx.application.Platform;
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
import utils.view.ScrollPaneFixer;

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
    private ObservableList<Crumb> crumbs;

    // Constructors

    // Getters & Setters

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScrollPaneFixer.fixBlurryScrollPan(scrollPane);
        separatorCharacter = Character.toString(resources.getString("separator.character").charAt(0));
        crumbs = FXCollections.observableArrayList();
        Bindings.bindContent(hBox.getChildren(), crumbs);
        crumbs.addListener((ListChangeListener<Crumb>) c -> {
            if (c.next()) {
                for (Crumb crumb : c.getAddedSubList()) {
                    int position = crumbs.indexOf(crumb);
                    while (position > 0) {
                        crumbs.get(position - 1).displayCrumbAsLink();
                        position--;
                    }
                }
            }
        });
    }

    // UI event methods


    // Other methods
    public void addCrumb(Hyperlink hyperlink) {
        // TODO need to add some kind of boolean property that enables hiding/showing the hyperlink/label depending on the position in the list.
        boolean crumbAlreadyExists = false;
        for(Crumb crumb : crumbs) {
            if(crumb.getLink().equals(hyperlink)) {
                crumbAlreadyExists = true;
            }
        }
        if(!crumbAlreadyExists) {
            crumbs.add(new Crumb(hyperlink, separatorCharacter));
            if(crumbs.size() > 0) {
                for(Crumb crumb : crumbs) {
                    if (crumbs.indexOf(crumb) < (crumbs.size() - 1)) {
                        crumb.showSeparator();
                    }
                }
            }
            hyperlink.visitedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue) {
                    hyperlink.setVisited(false);
                }
                for (Crumb crumb : crumbs) {
                    if(crumb.getLink().equals(hyperlink)) {
                        crumb.displayCrumbAsLabel();
                        int position = crumbs.indexOf(crumb);
                        crumbs.get(position).hideSeparator();
                        Platform.runLater(() -> crumbs.remove((position + 1), crumbs.size()));
                    }
                }
            });
        }
    }


        private class Crumb extends HBox{

        private StackPane stack;
        private Hyperlink link;
        private Label linkLbl;
        private Label separatorLbl;

        public Crumb(Hyperlink link, String separatorCharacter) {
            stack = new StackPane();
            this.link = link;
            this.linkLbl = new Label(link.getText());
            this.link.getStyleClass().add("footer");
            linkLbl.getStyleClass().add("footer");
            separatorLbl = new Label(separatorCharacter);
            separatorLbl.setMaxHeight(Double.MAX_VALUE);
            separatorLbl.setMaxWidth(Double.MAX_VALUE);
            separatorLbl.getStyleClass().add("footer");
            hideSeparator();
            stack.getChildren().add(link);
            stack.getChildren().add(linkLbl);
            this.getChildren().add(stack);
            this.getChildren().add(separatorLbl);
            displayCrumbAsLabel();
        }

        public Hyperlink getLink() {
            return link;
        }

        public void displayCrumbAsLink() {
            this.link.setVisible(true);
            this.link.setDisable(false);
            this.linkLbl.setVisible(false);
            this.linkLbl.setDisable(true);
        }

        public void displayCrumbAsLabel() {
            this.link.setVisible(false);
            this.link.setDisable(true);
            this.linkLbl.setVisible(true);
            this.linkLbl.setDisable(false);
        }

        public void hideSeparator() {
            separatorLbl.setVisible(false);
            separatorLbl.setDisable(true);
        }

        public void showSeparator() {
            separatorLbl.setVisible(true);
            separatorLbl.setDisable(false);
        }
    }


}