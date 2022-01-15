package view.sharedviewcomponents.creationprocedures.relateditemdetails;

import domain.entities.project.ObservableWorkspaceProject;
import domain.entities.relateditem.ObservableRelatedItem;
import domain.viewmodels.relateditem.RelatedItemTypeListViewModel;
import domain.viewmodels.relateditem.RelatedItemTypeViewModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import persistence.dto.RelatedItemDTO;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import utils.enums.RelatedItemParentTypeEnum;
import utils.enums.RelatedItemTypeEnum;
import view.components.project.table.relatedprojectselection.RelatedProjectSelectionPresenter;
import view.components.project.table.relatedprojectselection.RelatedProjectSelectionView;
import view.sharedviewcomponents.editor.datapane.DataPanePresenter;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RelatedItemDetailsPresenter extends DataPanePresenter {

    // JavaFX injected node variables
    @FXML
    private TextField titleTextField;
    @FXML
    private Label titleErrorLbl;
    @FXML
    private ChoiceBox<RelatedItemTypeViewModel> typeChoiceBox;
    @FXML
    private Label typeErrorLbl;
    @FXML
    private Button inputPathBtn;
    @FXML
    private TextField pathTextField;
    @FXML
    private Label pathErrorLbl;
    @FXML
    private TextField createdOnTextField;
    @FXML
    private TextField lastChangedTextField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private UUID parentUUID;
    private RelatedItemParentTypeEnum relatedItemParentType;
    private Optional<ObservableRelatedItem> relatedItem;
    private RelatedItemTypeListViewModel relatedItemTypeListViewModel;
    private SimpleBooleanProperty titleValidationPasses;
    private SimpleBooleanProperty typeValidationPasses;
    private SimpleBooleanProperty pathValidationPasses;
    private List<SimpleBooleanProperty> validationStates;
    private SimpleBooleanProperty titleChangePending;
    private SimpleBooleanProperty typeChangePending;
    private SimpleBooleanProperty pathChangePending;
    private List<SimpleBooleanProperty> pendingChangeStates;

    // Constructors

    // Getters & Setters
    public void setParentUUID(UUID parentUUID) {
        this.parentUUID = parentUUID;
    }

    public void setRelatedItemParentType(RelatedItemParentTypeEnum relatedItemParentType) {
        this.relatedItemParentType = relatedItemParentType;
        ObservableList<RelatedItemTypeViewModel> filteredTypeObservableList;
        List<RelatedItemTypeViewModel> filteredTypeList = null;
        switch(relatedItemParentType) {
            case PROJECT:
                // TODO Revisit this. May need to filter differently and then "disable" options in the ChoiceBox list etc.
                filteredTypeList = relatedItemTypeListViewModel.getRelatedItemTypesViewModels()
                        .stream()
                        .filter(item -> item.getRelatedItemTypeId() < RelatedItemTypeEnum.CHILD_PROJECT.getId())
                        .collect(Collectors.toList());
                break;
            case CARD:
                filteredTypeList = relatedItemTypeListViewModel.getRelatedItemTypesViewModels()
                        .stream()
                        .filter(item -> item.getRelatedItemTypeId() < RelatedItemTypeEnum.PARENT_CARD.getId())
                        .collect(Collectors.toList());
                break;
        }
        filteredTypeObservableList = FXCollections.observableArrayList(filteredTypeList);
        typeChoiceBox.setItems(filteredTypeObservableList);
    }

    public void setRelatedItem(ObservableRelatedItem relatedItem) {
        this.relatedItem = Optional.of(relatedItem);
        this.parentUUID = relatedItem.getParentItemUUID();
        if (this.relatedItem.isPresent()) {
            titleTextField.setText(this.relatedItem.get().titleProperty().getValue());
            titleErrorLbl.setVisible(false);
            for(RelatedItemTypeViewModel relatedItemTypeViewModel : typeChoiceBox.getItems()) {
                if(this.relatedItem.get().typeProperty().getValue() == relatedItemTypeViewModel.getRelatedItemTypeId()) {
                    typeChoiceBox.getSelectionModel().select(relatedItemTypeViewModel);
                }
            }
            typeErrorLbl.setVisible(false);;
            pathTextField.setText(this.relatedItem.get().pathProperty().getValue());
            pathErrorLbl.setVisible(false);
            //createdOnTextField.setText(relatedItem.pro);
            //lastChangedTextField.setDisable(true);
        }
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        relatedItem = Optional.empty();
        try {
            relatedItemTypeListViewModel = new RelatedItemTypeListViewModel(kanbanBoDataService.getRelatedItemTypeTableAsList());
            typeChoiceBox.setItems(relatedItemTypeListViewModel.getRelatedItemTypesViewModels());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        validationStates = new ArrayList<>();
        titleValidationPasses = new SimpleBooleanProperty(true);
        validationStates.add(titleValidationPasses);
        typeValidationPasses = new SimpleBooleanProperty(true);
        validationStates.add(typeValidationPasses);
        pathValidationPasses = new SimpleBooleanProperty(true);
        validationStates.add(pathValidationPasses);
        titleValidationPasses.addListener(createErrorValidationChangeListener());
        typeValidationPasses.addListener(createErrorValidationChangeListener());
        pathValidationPasses.addListener(createErrorValidationChangeListener());
        validationErrors.addListener(((observable, oldValue, newValue) -> {
            if(newValue) {
                saveBtn.setDisable(true);
            } else {
                saveBtn.setDisable(false);
            }
        }));
        // TODO Changes pending next...
        pendingChangeStates = new ArrayList<>();
        titleChangePending = new SimpleBooleanProperty(false);
        pendingChangeStates.add(titleChangePending);
        typeChangePending = new SimpleBooleanProperty(false);
        pendingChangeStates.add(typeChangePending);
        pathChangePending = new SimpleBooleanProperty(false);
        pendingChangeStates.add(pathChangePending);
        titleChangePending.addListener(createChangesPendingChangeListener());
        typeChangePending.addListener(createChangesPendingChangeListener());
        pathChangePending.addListener(createChangesPendingChangeListener());


        titleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            relatedItem.ifPresent(item -> {
                if (newValue.equals(relatedItem.get().titleProperty().getValue())) {
                    titleChangePending.set(false);
                    return;
                }
            });
            if(newValue.length() < 1) {
                titleValidationPasses.set(false);
            } else {
                titleValidationPasses.set(true);
                titleChangePending.set(true);
            }
        });

        // TODO fix this
        /*typeChoiceBox.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldViewModel, newViewModel) -> {
            if(oldViewModel.getRelatedItemTypeId() != newViewModel.getRelatedItemTypeId()) {
                pathTextField.setText("");
            }
        }));*/
        // TODO Implement validation for the other fields etc.

    }

    // UI event methods
    @FXML
    private void inputPath(ActionEvent actionEvent) throws SQLException, IOException {
        RelatedItemTypeViewModel relatedItemTypeViewModel = typeChoiceBox.getSelectionModel().getSelectedItem();
        RelatedItemTypeEnum itemType = RelatedItemTypeEnum.getTypeFromId(relatedItemTypeViewModel.getRelatedItemTypeId());
        switch(itemType) {
            case DIRECTORY:
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(StageUtils.getSubStages().getLast());
                System.out.println(selectedDirectory.getAbsolutePath());
                pathTextField.setText(selectedDirectory.getAbsolutePath());
                break;
            case FILE:
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(StageUtils.getSubStages().getLast());
                System.out.println(selectedFile.getAbsolutePath());
                pathTextField.setText(selectedFile.getAbsolutePath());
                break;
            case URL_PATH:
                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setTitle("input a web URL");
                textInputDialog.showAndWait();
                String inputPathString = textInputDialog.getEditor().getText();
                System.out.println("URL: " + inputPathString);
                pathTextField.setText(inputPathString);
                break;
            case CHILD_PROJECT:
                // TODO Wrap this project table in a view with buttons to select project, or create new project
                RelatedProjectSelectionView relatedProjectSelectionView = new RelatedProjectSelectionView();
                RelatedProjectSelectionPresenter relatedProjectSelectionPresenter = (RelatedProjectSelectionPresenter) relatedProjectSelectionView.getPresenter();
                relatedProjectSelectionPresenter.setProjectsList(kanbanBoDataService.getRelatedItemProjectSubList(parentUUID));
                StageUtils.createChildStage("test project view", relatedProjectSelectionView.getView());
                StageUtils.showAndWaitOnSubStage();
                ObservableWorkspaceProject observableWorkspaceProject = relatedProjectSelectionPresenter.getSelectedProject();
                pathTextField.setText(observableWorkspaceProject.getProjectUUID().toString());
                titleTextField.setText(observableWorkspaceProject.projectTitleProperty().getValue());
                break;
        }
        actionEvent.consume();
    }

    @FXML
    private void save(ActionEvent actionEvent) throws SQLException, IOException {
                RelatedItemDTO.Builder relatedItemBuilder = new RelatedItemDTO.Builder(parentUUID.toString())
                .title(titleTextField.getText())
                .type(typeChoiceBox.getSelectionModel().getSelectedItem().getRelatedItemTypeId())
                .path(pathTextField.getText());
                relatedItem.ifPresent(item -> {
                    relatedItemBuilder.relatedItemUUID(item.getRelatedItemUUID().toString());
                });
        RelatedItemDTO relatedItemDTO = null;
        switch(editorDataMode) {
            case CREATION:
                relatedItemBuilder.relatedItemUUID(UUID.randomUUID().toString());
                relatedItemDTO = new RelatedItemDTO(relatedItemBuilder);
                if(relatedItemParentType == RelatedItemParentTypeEnum.PROJECT) {
                    relatedItemBuilder.relatedItemUUID(UUID.randomUUID().toString());
                    kanbanBoDataService.createProjectRelatedItem(relatedItemDTO);
                } else {
                    kanbanBoDataService.createCardRelatedItem(relatedItemDTO);
                }
                break;
            case EDITING:
                relatedItemDTO = new RelatedItemDTO(relatedItemBuilder);

                if(relatedItemParentType == RelatedItemParentTypeEnum.PROJECT) {
                    kanbanBoDataService.updateProjectRelatedItem(relatedItemDTO, this.relatedItem.get());
                } else {
                    kanbanBoDataService.updateCardRelatedItem(relatedItemDTO, this.relatedItem.get());
                }
                break;
        }
        StageUtils.closeSubStage();
        actionEvent.consume();
    }

    @FXML
    private void cancel(ActionEvent actionEvent) {
        StageUtils.hideSubStage();
        actionEvent.consume();
    }

    // Other methods
    @Override
    protected void prepareViewForCreation() {
        titleTextField.setDisable(false);
        titleErrorLbl.setDisable(false);
        typeChoiceBox.setDisable(false);
        typeChoiceBox.setValue(relatedItemTypeListViewModel.getSelectedRelatedItem());
        typeErrorLbl.setDisable(false);
        inputPathBtn.setDisable(false);
        pathTextField.setDisable(false);
        pathErrorLbl.setDisable(false);
        createdOnTextField.setDisable(true);
        lastChangedTextField.setDisable(true);
        saveBtn.setDisable(false);
    }

    @Override
    protected void prepareViewForDisplay() {
        titleTextField.setDisable(true);
        titleErrorLbl.setDisable(true);
        typeChoiceBox.setDisable(true);
        typeErrorLbl.setDisable(true);
        inputPathBtn.setDisable(true);
        pathTextField.setDisable(true);
        pathErrorLbl.setDisable(true);
        createdOnTextField.setDisable(true);
        lastChangedTextField.setDisable(true);
        saveBtn.setDisable(true);
    }

    @Override
    protected void prepareViewForEditing() {
        prepareViewForCreation();
    }

    private ChangeListener<Boolean> createErrorValidationChangeListener() {
        return (observable, oldValue, newValue) -> {
            if(newValue) {
                boolean noErrors = true;
                for (SimpleBooleanProperty validationState : validationStates) {
                    if(!validationState.getValue()) {
                        noErrors = false;
                    }
                }
                if(!noErrors) {
                    validationErrors.set(true);
                }
            } else {
                validationErrors.set(false);
            }
        };
    }

    private ChangeListener<Boolean> createChangesPendingChangeListener() {
        return (observable, oldValue, newValue) -> {
            if(newValue) {
                boolean noChanges = true;
                for (SimpleBooleanProperty pendingChangeState : pendingChangeStates) {
                    if(pendingChangeState.getValue()) {
                        noChanges = false;
                    }
                }
                if(!noChanges) {
                    changesPending.set(true);
                }
            } else {
                changesPending.set(false);
            }
        };
    }
}