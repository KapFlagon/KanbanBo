package view.screens.mainscreen.subviews.workspace.subviews.columncontainer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ColumnContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private VBox cardVBox;
    @FXML
    private Label columnTitleLbl;
    @FXML
    private Text cardCountTxt;
    @FXML
    private Button addCardBtn;


    // Other variables
    private ObservableList cards;

    // Constructors

    // Getters & Setters

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // UI event methods
    public void addCard() {
        System.out.println("Adding card");
    }

    // Other methods


}