package view.sharedviewcomponents.popups.resourceitemdetails;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import domain.activerecords.ResourceItemActiveRecord;
import domain.activerecords.ResourceItemTypeActiveRecord;
import persistence.tables.resourceitems.ResourceItemTable;
import utils.StageUtils;
import view.screens.mainscreen.subviews.manage.subviews.projectstable.ProjectsTablePresenter;
import view.screens.mainscreen.subviews.manage.subviews.projectstable.ProjectsTableView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class ResourceItemDetailsPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private TextField itemTitleTextField;
    @FXML
    private ChoiceBox itemTypeChoiceBox;
    @FXML
    private TextField itemPathTextField;
    @FXML
    private Button pathInputBtn;
    @FXML
    private TextArea itemDescriptionTextArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;


    // Other variables
    private UUID parentUUID;
    private ResourceItemActiveRecord resourceItemActiveRecord;
    private ObservableList<ResourceItemTypeActiveRecord> relatedItemTypes;

    // Constructors

    // Getters & Setters
    public UUID getParentUUID() {
        return parentUUID;
    }
    public void setParentUUID(UUID parentUUID) {
        this.parentUUID = parentUUID;
        resourceItemActiveRecord = new ResourceItemActiveRecord(parentUUID, new ResourceItemTable());
        resourceItemActiveRecord.setParentItemUUID(parentUUID);
    }

    public ResourceItemActiveRecord getRelatedItemActiveRecord() {
        return resourceItemActiveRecord;
    }
    public void setRelatedItemActiveRecord(ResourceItemActiveRecord resourceItemActiveRecord) {
        this.resourceItemActiveRecord = resourceItemActiveRecord;
        this.parentUUID = resourceItemActiveRecord.getParentItemUUID();
        itemTitleTextField.textProperty().bind(resourceItemActiveRecord.relatedItemTitleProperty());
        itemDescriptionTextArea.textProperty().bind(resourceItemActiveRecord.relatedItemDescriptionProperty());
        itemPathTextField.textProperty().bind(getRelatedItemActiveRecord().relatedItemPathProperty());
    }

    public ObservableList<ResourceItemTypeActiveRecord> getRelatedItemTypes() {
        return relatedItemTypes;
    }
    public void setRelatedItemTypes(ObservableList<ResourceItemTypeActiveRecord> relatedItemTypes) {
        this.relatedItemTypes = relatedItemTypes;
        initChoiceBox();
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Determine strategy for populating data in choicebox and handling selections.
        //initChoiceBox();
    }

    private void initChoiceBox() {
        itemTypeChoiceBox.setItems(relatedItemTypes);
    }

    // UI event methods
    @FXML private void inputPath() {
        // Needs to be dependent on the value of the item type choice box, to show a different selection/input UI.

        int selectedItemType = itemTypeChoiceBox.getSelectionModel().getSelectedIndex();

        switch(selectedItemType) {
            case 0:
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(StageUtils.getSubStages().getLast());
                if(selectedDirectory != null && selectedDirectory.exists()){
                    itemPathTextField.setText(selectedDirectory.toString());
                }
                StageUtils.showAndWaitOnSubStage();
                break;
            case 1:
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(StageUtils.getSubStages().getLast());
                if(selectedFile != null && selectedFile.exists()) {
                    itemPathTextField.setText(selectedFile.toString());
                }
                StageUtils.showAndWaitOnSubStage();
                break;
            case 2:
                // TODO Implement this
                System.out.println("URL thing");
                TextInputDialog textInputDialog = new TextInputDialog("Enter a URL");
                textInputDialog.setTitle("Enter a URL");
                textInputDialog.showAndWait().filter(response -> !response.isEmpty()).ifPresent((response) -> {
                    System.out.println(textInputDialog.getEditor().getText());
                    itemPathTextField.setText(textInputDialog.getEditor().getText());
                });
                // Some kind of minimal pop-up with TextField for an internet URL.
                break;
            case 3:
                // TODO Implement this
                VBox vbox = new VBox();
                HBox hbox = new HBox();
                Button selectBtn = new Button("Select");
                Button createBtn = new Button("Create");
                hbox.getChildren().addAll(selectBtn, createBtn);
                vbox.getChildren().add(hbox);
                ProjectsTableView projectsTableView = new ProjectsTableView();
                ProjectsTablePresenter projectsTablePresenter = (ProjectsTablePresenter) projectsTableView.getPresenter();
                //projectsTablePresenter.setActiveProjectList();
                vbox.getChildren().add(projectsTableView.getView());
                StageUtils.createChildStage("Select a child project", vbox);
                StageUtils.showAndWaitOnSubStage();
                // Some kind of pop-up for selecting a project from a table list (excluding the current project), or to create a new project with this owner as a parent item.
                break;
            case 4:
                // TODO Implement this
                // Should not be available for selection/editing...
                // Can be removed though...
                break;
            default:
                System.out.println("Specify a related item type");
                break;
        }
    }

    @FXML private void saveRelatedItemDetailsChange() {
        // TODO Finish implementing this method

        resourceItemActiveRecord.setRelatedItemTitle(itemTitleTextField.getText());
        // TODO Implement this
        // relatedItemActiveRecord.setRelatedItemType();
        resourceItemActiveRecord.setRelatedItemPath(itemPathTextField.getText());
        resourceItemActiveRecord.setRelatedItemDescription(itemDescriptionTextArea.getText());
        StageUtils.hideSubStage();
    }

    @FXML private void cancelRelatedItemsDetailsChange() {
        StageUtils.hideSubStage();
    }

    // Other methods


}