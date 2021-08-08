package view.sharedcomponents.popups.confirmationdialog;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationDialogPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private BorderPane contentBorderPane;
    @FXML
    private Label promptTextLbl;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;


    // Other variables

    // Constructors

    // Getters & Setters
    public void setPromptText(String newPromptText) {
        promptTextLbl.setText(newPromptText);
    }

    public double[] getDimensions() {
        double[] dimensions = new double[6];
        dimensions[0] = contentBorderPane.getMinHeight();
        dimensions[1] = contentBorderPane.getMinWidth();
        dimensions[2] = contentBorderPane.getPrefHeight();
        dimensions[3] = contentBorderPane.getPrefWidth();
        dimensions[4] = contentBorderPane.getMaxWidth();
        dimensions[5] = contentBorderPane.getMaxWidth();
        return dimensions;
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // UI event methods


    // Other methods
    public void setConfirmAction(EventHandler<ActionEvent> eventValue){
        confirmBtn.setOnAction(eventValue);
    }

    public void setCancelAction(EventHandler<ActionEvent> eventValue){
        cancelBtn.setOnAction(eventValue);
    }


}