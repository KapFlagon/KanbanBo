package view.components.ui.datapanes.card.resources;

import domain.entities.relateditem.ObservableRelatedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CardResourcesPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private TableView<ObservableRelatedItem> resourceTableView;
    @FXML
    private TableColumn<ObservableRelatedItem, String> resourceTitleTableColumn;
    @FXML
    private TableColumn<ObservableRelatedItem, String> resourceTypeTableColumn;
    @FXML
    private TableColumn<ObservableRelatedItem, String> resourcePathTableColumn;


    // Other variables
    private ObservableList<ObservableRelatedItem> cardResourceItemsViewModel;

    // Constructors

    // Getters & Setters
    public ObservableList<ObservableRelatedItem> getCardResourceItemsViewModel() {
        return cardResourceItemsViewModel;
    }

    public void setCardResourceItemsViewModel(ObservableList<ObservableRelatedItem> cardResourceItemsViewModel) {
        this.cardResourceItemsViewModel = FXCollections.observableArrayList(cardResourceItemsViewModel);
        resourceTableView.setItems(this.cardResourceItemsViewModel);
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardResourceItemsViewModel = FXCollections.observableArrayList();
        resourceTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        resourceTitleTableColumn.setCellValueFactory(cellData -> (cellData.getValue().titleProperty()));
        //resourceTypeTableColumn.setCellValueFactory(cellData -> (cellData.getValue().typeTextProperty()));
        resourcePathTableColumn.setCellValueFactory(cellData -> (cellData.getValue().pathProperty()));
    }

    // UI event methods
    @FXML
    void addResource(ActionEvent event) {
        // TODO pop-up to enter resource data
        event.consume();
    }

    @FXML
    void deleteResource(ActionEvent event) {
        ObservableRelatedItem selectedItem = resourceTableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            cardResourceItemsViewModel.remove(selectedItem);
        }
        event.consume();
    }

    @FXML
    void editResourceDetails(ActionEvent event) {
        // TODO pop-up to edit resource data
        event.consume();
    }

    // Other methods


}