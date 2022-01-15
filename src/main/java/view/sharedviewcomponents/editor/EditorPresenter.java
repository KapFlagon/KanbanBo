package view.sharedviewcomponents.editor;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;
import utils.view.ScrollPaneFixer;
import view.sharedviewcomponents.editor.datapane.DataPanePresenter;
import view.sharedviewcomponents.editor.datapane.DataPaneView;
import view.sharedviewcomponents.editor.navigationlabel.NavigationLabelPresenter;
import view.sharedviewcomponents.editor.navigationlabel.NavigationLabelView;
import view.sharedviewcomponents.popups.EditorDataMode;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;

public abstract class EditorPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    protected ScrollPane contentScrollPane;
    @FXML
    protected VBox navigationLabelsVBox;
    @FXML
    protected Button saveBtn;
    @FXML
    protected Button cancelBtn;



    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    protected EditorDataMode editorDataMode;
    protected SimpleBooleanProperty dataErrors;
    protected SimpleBooleanProperty changesPending;
    protected NavigationLabelView selectedNavigationLabelView;
    protected List<NavigationLabelView> navigationLabelViewList;
    protected Map<NavigationLabelView, DataPaneView> navLabelDataPaneMap;


    // Constructors


    // Getters & Setters
    public void setEditorDataMode(EditorDataMode editorDataMode) {
        this.editorDataMode = editorDataMode;
        initializeEditorState();
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScrollPaneFixer.fixBlurryScrollPan(contentScrollPane);
        dataErrors = new SimpleBooleanProperty(false);
        changesPending = new SimpleBooleanProperty(false);
        navigationLabelViewList = new ArrayList<>();
        navLabelDataPaneMap = new HashMap<>();

        dataErrors.addListener((changeable, oldValue, newValue) -> {
            if(newValue) {
                saveBtn.setDisable(true);
            } else {
                saveBtn.setDisable(false);
            }
        });
    }

    private void initializeEditorState() {
        for(DataPaneView dataPaneView : navLabelDataPaneMap.values()) {
            DataPanePresenter dataPanePresenter = (DataPanePresenter) dataPaneView.getPresenter();
            dataPanePresenter.setEditorDataMode(editorDataMode);
        }
    }

    protected void lateInitialization() {

    }

    // UI event methods
    @FXML
    protected void cancel(ActionEvent event) {
        if(changesPending.getValue()) {
            // TODO Use confirmation dialog to inform user that changes are pending, and ask if they are sure they want to discard them
        }
        StageUtils.hideSubStage();
        event.consume();
    }

    @FXML
    protected void save(ActionEvent event) {
        StageUtils.hideSubStage();
        event.consume();
    }

    // Other methods
    protected void addDataPane(DataPaneView dataPaneView) {
        NavigationLabelView navigationLabelView = buildNavigationLabel(dataPaneView);
        NavigationLabelPresenter navigationLabelPresenter = (NavigationLabelPresenter) navigationLabelView.getPresenter();
        navigationLabelPresenter.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                selectedNavigationLabelView = navigationLabelView;
                for(ListIterator<NavigationLabelView> iterator = navigationLabelViewList.listIterator(); iterator.hasNext();) {
                    NavigationLabelView iteratorNavLabelView = iterator.next();
                    if(!iteratorNavLabelView.equals(selectedNavigationLabelView)) {
                        NavigationLabelPresenter iteratorNavLabelPresenter = (NavigationLabelPresenter) iteratorNavLabelView.getPresenter();
                        iteratorNavLabelPresenter.selectedProperty().set(false);
                    }
                }
                DataPaneView paneForContent = navLabelDataPaneMap.get(navigationLabelView);
                contentScrollPane.setContent(paneForContent.getView());
            }
        });
        navigationLabelViewList.add(navigationLabelView);
        navLabelDataPaneMap.put(navigationLabelView, dataPaneView);
    }

    private NavigationLabelView buildNavigationLabel(DataPaneView dataPaneView) {
        NavigationLabelView navigationLabelView = new NavigationLabelView();
        NavigationLabelPresenter navigationLabelPresenter = (NavigationLabelPresenter) navigationLabelView.getPresenter();
        DataPanePresenter dataPanePresenter = (DataPanePresenter) dataPaneView.getPresenter();
        navigationLabelPresenter.getHeaderLbl().setText(dataPanePresenter.getDataPaneHeaderText());
        navigationLabelPresenter.getPendingChangesLbl().visibleProperty().bind(dataPanePresenter.changesPendingProperty());
        navigationLabelPresenter.getHasErrorsLbl().visibleProperty().bind(dataPanePresenter.validationErrorsProperty());
        dataPanePresenter.validationErrorsProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue) {
                dataErrors.set(true);
            } else {
                boolean noRemainingErrors = true;
                for(DataPaneView iteratedDataPaneView : navLabelDataPaneMap.values()) {
                    DataPanePresenter iteratedDataPanePresenter = (DataPanePresenter) iteratedDataPaneView.getPresenter();
                    if(iteratedDataPanePresenter.hasValidationErrors()) {
                        noRemainingErrors = false;
                    }
                }
                if(noRemainingErrors) {
                    dataErrors.set(false);
                }
            }
        }));
        dataPanePresenter.changesPendingProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                changesPending.set(true);
            } else {
                boolean noPendingChanges = true;
                for(DataPaneView iteratedDataPaneView : navLabelDataPaneMap.values()) {
                    DataPanePresenter iteratedDataPanePresenter = (DataPanePresenter) iteratedDataPaneView.getPresenter();
                    if(iteratedDataPanePresenter.hasChangesPending()) {
                        noPendingChanges = false;
                    }
                }
                if(noPendingChanges) {
                    changesPending.set(false);
                }
            }
        });

        return navigationLabelView;
    }

}