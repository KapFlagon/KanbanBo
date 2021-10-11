package view.components.ui.datapanes.resourceitemstable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceItemsTablePresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Button openSelectedItemBtn;
    @FXML
    private Button addNewItemBtn;
    @FXML
    private Button editSelectedItemBtn;
    @FXML
    private Button removeSelectedItemBtn;
    @FXML
    private TableView itemTableView;
    @FXML
    private TableColumn itemTitleTableColumn;
    @FXML
    private TableColumn itemTypeTableColumn;
    @FXML
    private TableColumn itemLinkTableColumn;


    // Other variables
    private SelectionModel<TableRow> tableRowSelectionModel;

    // Constructors

    // Getters & Setters

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableRowSelectionModel = itemTableView.getSelectionModel();
        // TODO implement this to enable/disable buttons
        // tableRowSelectionModel.selectedItemProperty().addListener();
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        itemTableView.setPlaceholder(new Label(resourceBundle.getString("table.placeholder.resource_items")));
    }

    // UI event methods
    @FXML private void openSelectedItem() {

    }

    @FXML private void addNewItem() {

    }

    @FXML private void editSelectedItem() {

    }

    @FXML private void removeSelectedItem() {

    }

    // Other methods


}