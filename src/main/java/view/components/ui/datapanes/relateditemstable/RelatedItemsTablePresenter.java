package view.components.ui.datapanes.relateditemstable;

import domain.entities.relateditem.ObservableRelatedItem;
import domain.viewmodels.relateditem.RelatedItemTypeViewModel;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Callback;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import utils.enums.RelatedItemParentTypeEnum;
import utils.enums.RelatedItemTypeEnum;
import view.sharedviewcomponents.creationprocedures.relateditemdetails.RelatedItemDetailsPresenter;
import view.sharedviewcomponents.creationprocedures.relateditemdetails.RelatedItemDetailsView;
import view.sharedviewcomponents.popups.EditorDataMode;

import javax.inject.Inject;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

public class RelatedItemsTablePresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Button addNewItemBtn;
    @FXML
    private Button editSelectedItemBtn;
    @FXML
    private Button removeSelectedItemBtn;
    @FXML
    private Button openSelectedItemBtn;
    @FXML
    private TableView<ObservableRelatedItem> relatedItemTableView;
    @FXML
    private TableColumn<ObservableRelatedItem, String> relatedItemTitleTableColumn;
    @FXML
    private TableColumn<ObservableRelatedItem, String> relatedItemTypeTableColumn;
    @FXML
    private TableColumn<ObservableRelatedItem, String> relatedItemPathTableColumn;


    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableList<ObservableRelatedItem> resourceItemList;
    private UUID parentUUID;
    private RelatedItemParentTypeEnum relatedItemParentType;

    // Constructors

    // Getters & Setters
    public void setResourceItemList(ObservableList<ObservableRelatedItem> resourceItemList) {
        this.resourceItemList = resourceItemList;
        relatedItemTableView.setItems(resourceItemList);
    }

    public void setParentUUID(UUID parentUUID) {
        this.parentUUID = parentUUID;
    }

    public void setRelatedItemParentType(RelatedItemParentTypeEnum relatedItemParentType) {
        this.relatedItemParentType = relatedItemParentType;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO implement this to enable/disable buttons
        resourceItemList = FXCollections.observableArrayList();
        String placeholderText = getLocalisedTypeText("table.placeholder.related_items");
        relatedItemTableView.setPlaceholder(new Label(placeholderText));
        relatedItemTitleTableColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        relatedItemTypeTableColumn.setCellValueFactory(cellData -> {
            TableCell<ObservableRelatedItem, String> tableCell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if(!empty) {

                    }
                }
            };
            int typeID = cellData.getValue().typeProperty().getValue();
            SimpleStringProperty typeTextProperty = new SimpleStringProperty();
            for(RelatedItemTypeEnum relatedItemTypeEnum : RelatedItemTypeEnum.values()) {
                if(relatedItemTypeEnum.getId() == typeID) {
                    String text = getLocalisedTypeText(relatedItemTypeEnum.getTranslationKey());
                    typeTextProperty.set(text);
                }
            }
            return typeTextProperty;
        });
        relatedItemPathTableColumn.setCellValueFactory(cell -> cell.getValue().pathProperty());

    }

    private void initializeListListener() {

    }

    // UI event methods
    @FXML
    private void openSelectedItem() {
        // TODO
        Optional<ObservableRelatedItem> selectedItem = Optional.of(relatedItemTableView.getSelectionModel().getSelectedItem());
        selectedItem.ifPresent(p -> {
            RelatedItemTypeEnum itemType = RelatedItemTypeEnum.getTypeFromId(selectedItem.get().typeProperty().getValue());
            String pathData = selectedItem.get().pathProperty().getValue();
            switch(itemType) {
                case DIRECTORY -> {
                    try {
                        openDirectory(pathData);
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Error opening directory. Check the path\n" + e.getLocalizedMessage());
                        alert.showAndWait();
                        e.printStackTrace();
                    }
                }
                case FILE -> {
                    try {
                        openFile(pathData);
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Error opening file\n" + e.getLocalizedMessage());
                        alert.showAndWait();
                        e.printStackTrace();
                    }
                }
                case URL_PATH -> {
                    try{
                        openURL(pathData);
                    } catch (URISyntaxException uriSyntaxException) {
                        Alert uriSyntaxAlert = new Alert(Alert.AlertType.ERROR);
                        uriSyntaxAlert.setContentText("Syntax error with the URL. Check the path\n" + uriSyntaxException.getLocalizedMessage());
                        uriSyntaxAlert.showAndWait();
                        uriSyntaxException.printStackTrace();
                    } catch (IOException e) {
                        Alert ioExceptionAlert = new Alert(Alert.AlertType.ERROR);
                        ioExceptionAlert.setContentText("Error opening website. Check the path\n" + e.getLocalizedMessage());
                        ioExceptionAlert.showAndWait();
                        e.printStackTrace();
                    }
                }
                case CHILD_PROJECT -> {
                    StageUtils.closeSubStage();
                    try {
                        kanbanBoDataService.openProjectInWorkspace(UUID.fromString(pathData));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case PARENT_CARD -> {
                    StageUtils.closeSubStage();
                    try {
                        kanbanBoDataService.openCardParentProjectInWorkspace(selectedItem.get().getParentItemUUID());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @FXML
    private void addNewItem() {
        RelatedItemDetailsView relatedItemDetailsView = new RelatedItemDetailsView();
        RelatedItemDetailsPresenter relatedItemDetailsPresenter = (RelatedItemDetailsPresenter) relatedItemDetailsView.getPresenter();
        relatedItemDetailsPresenter.setParentUUID(parentUUID);
        relatedItemDetailsPresenter.setEditorDataMode(EditorDataMode.CREATION);
        relatedItemDetailsPresenter.setRelatedItemParentType(relatedItemParentType);
        StageUtils.createChildStage("Add Related Item", relatedItemDetailsView.getView());
        StageUtils.showAndWaitOnSubStage();
    }

    @FXML
    private void editSelectedItem() {
        if(relatedItemTableView.getSelectionModel().getSelectedItem() != null) {
            RelatedItemDetailsView relatedItemDetailsView = new RelatedItemDetailsView();
            RelatedItemDetailsPresenter relatedItemDetailsPresenter = (RelatedItemDetailsPresenter) relatedItemDetailsView.getPresenter();
            relatedItemDetailsPresenter.setEditorDataMode(EditorDataMode.EDITING);
            relatedItemDetailsPresenter.setRelatedItemParentType(relatedItemParentType);
            relatedItemDetailsPresenter.setRelatedItem(relatedItemTableView.getSelectionModel().getSelectedItem());
            StageUtils.createChildStage("Edit Related Item", relatedItemDetailsView.getView());
            StageUtils.showAndWaitOnSubStage();
        }
    }

    @FXML
    private void removeSelectedItem() {
        // TODO
        if(relatedItemTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Remove Related Item?");
            alert.setContentText("Remove the Related Item?");
            alert.showAndWait().filter(response -> response == ButtonType.OK)
                    .ifPresent((response) -> {
                        ObservableRelatedItem itemForDeletion = this.relatedItemTableView.getSelectionModel().getSelectedItem();
                        switch (relatedItemParentType) {
                            case PROJECT -> {
                                try {
                                    kanbanBoDataService.deleteProjectRelatedItem(itemForDeletion);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            case CARD -> {
                                try {
                                    kanbanBoDataService.deleteCardRelatedItem(itemForDeletion);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }

    // Other methods
    private String getLocalisedTypeText(String resourceBundleKey) {
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        return resourceBundle.getString(resourceBundleKey);
    }

    private String assessPath(ObservableRelatedItem observableRelatedItem) {
        String pathStatus = "";

        return pathStatus;
    }

    private void openDirectory(String directoryPathString) throws IOException {
        File file = new File(directoryPathString);
        Desktop.getDesktop().open(file);
    }

    private void openFile(String filePathString) throws IOException {
        File file = new File(filePathString);
        Desktop desktop = Desktop.getDesktop();
        if(desktop.isSupported(Desktop.Action.OPEN)) {
            System.out.println("Opening is supported");
            desktop.open(file);
        }
    }

    private void openURL(String urlPath) throws URISyntaxException, IOException {
        URI uri = new URI(urlPath);
        Desktop.getDesktop().browse(uri);
    }

    private void openChildProject(String projectUUIDString) throws SQLException, IOException {
        UUID projectUUID = UUID.fromString(projectUUIDString);
        // TODO Implement search of projects, and opening it in the workspace.
        kanbanBoDataService.openProjectInWorkspace(projectUUID);
    }

    private void openParentCardProject(String parentCardUUIDString) {
        UUID parentCardUUID = UUID.fromString(parentCardUUIDString);
        // TODO Implement search of projects, backwards based on parent card UUID.
    }

}