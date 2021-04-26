package view.screens.mainscreen.subviews.workspace.subviews.projectcontainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import model.activerecords.ProjectActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.column.ColumnModel;
import model.domainobjects.project.ProjectModel;
import model.repositories.ActiveColumnListRepository;
import model.repositories.services.ProjectColumnRepositoryService;
import utils.StageUtils;
import view.screens.mainscreen.subviews.workspace.subviews.columncontainer.ColumnContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.columncontainer.ColumnContainerView;
import view.sharedcomponents.popups.columndetails.ColumnDetailsWindowPresenter;
import view.sharedcomponents.popups.columndetails.ColumnDetailsWindowView;
import view.sharedcomponents.popups.projectdetails.ProjectDetailsWindowPresenter;
import view.sharedcomponents.popups.projectdetails.ProjectDetailsWindowView;
import view.sharedcomponents.popups.projectnotesinput.ProjectNotesInputPresenter;
import view.sharedcomponents.popups.projectnotesinput.ProjectNotesInputView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectContainerPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label projectTitleLbl;
    @FXML
    private Button createColumnBtn;
    @FXML
    private HBox columnHBox;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private VBox resourcesVBox;

    // Other variables
    //private AbstractProjectModel projectModel;
    private ProjectActiveRecord<ProjectModel> projectActiveRecord;
    private ProjectColumnRepositoryService projectColumnRepositoryService;
    private ActiveColumnListRepository activeColumnListRepository;
    private ObservableList<ProjectColumnActiveRecord> projectColumnsList;
    private ColumnDetailsWindowView columnDetailsWindowView;
    private ColumnDetailsWindowPresenter columnDetailsWindowPresenter;


    // Constructors

    // Getters & Setters
    /*public AbstractProjectModel getProjectModel() {
        return projectModel;
    }
    public void setProjectModel(AbstractProjectModel projectModel) {
        this.projectModel = projectModel;
        doTheThings();
    }
     */

    public ProjectActiveRecord<ProjectModel> getProjectActiveRecord() {
        return projectActiveRecord;
    }
    public void setProjectActiveRecord(ProjectActiveRecord<ProjectModel> projectActiveRecord) throws IOException, SQLException {
        this.projectActiveRecord = projectActiveRecord;
        customInit();
    }

    public ObservableList<ProjectColumnActiveRecord> getProjectColumnsList() {
        return projectColumnsList;
    }
    public void setProjectColumnsList(ObservableList<ProjectColumnActiveRecord> projectColumnsList) {
        this.projectColumnsList = projectColumnsList;
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectColumnsList = FXCollections.observableArrayList();
    }

    public void customInit() throws IOException, SQLException {
        projectTitleLbl.setText(projectActiveRecord.getProjectTitle());
        projectDescriptionTextArea.setText(projectActiveRecord.getProjectDescription());
        projectColumnRepositoryService = new ProjectColumnRepositoryService(projectActiveRecord);
        projectColumnsList = projectColumnRepositoryService.getColumnsList();
        for (ProjectColumnActiveRecord<ColumnModel> projectColumnActiveRecord : projectColumnsList) {
            ColumnContainerView ccv = new ColumnContainerView();
            ColumnContainerPresenter ccp = (ColumnContainerPresenter) ccv.getPresenter();
            ccp.setProjectColumnActiveRecord(projectColumnActiveRecord);
            columnHBox.getChildren().add(ccv.getView());
        }
        activeColumnListRepository = projectColumnRepositoryService.getActiveColumnListRepository();
        // TODO get all column records linked to the project, populate the data.
    }

    private void initColumnDetailsWindow() {
        columnDetailsWindowView = new ColumnDetailsWindowView();
        columnDetailsWindowPresenter = (ColumnDetailsWindowPresenter) columnDetailsWindowView.getPresenter();
        columnDetailsWindowPresenter.setParentProject(projectActiveRecord);
    }

    // UI event methods
    public void editProjectDetails() {
        System.out.println("Edit project details");
        ProjectDetailsWindowView view = new ProjectDetailsWindowView();
        ProjectDetailsWindowPresenter presenter = (ProjectDetailsWindowPresenter) view.getPresenter();
        presenter.setProjectActiveRecord(projectActiveRecord);
        StageUtils.createChildStage("Enter Project Details", view.getView());
        StageUtils.getSubStages().peekLast().initStyle(StageStyle.UNDECORATED);
        StageUtils.showAndWaitOnSubStage();
        projectTitleLbl.setText(presenter.getProjectTitleTextField().getText());
        projectDescriptionTextArea.setText(presenter.getProjectDescriptionTextArea().getText());
    }

    public void addProjectResource() {
        System.out.println("Adding project resource");
    }

    public void createColumn() throws IOException, SQLException {
        System.out.println("Create Column...");
        initColumnDetailsWindow();
        StageUtils.createChildStage("Enter Column Details", columnDetailsWindowView.getView());
        StageUtils.showAndWaitOnSubStage();
        ProjectColumnActiveRecord<ColumnModel> pcar = columnDetailsWindowPresenter.getProjectColumnActiveRecord();
        if(pcar != null) {
            pcar.setParentProjectActiveRecord(projectActiveRecord);
            pcar.getProjectColumnModel().setColumn_position(projectColumnsList.size() + 1);
            projectColumnsList.add(pcar);
            ColumnContainerView ccv = new ColumnContainerView();
            ColumnContainerPresenter ccp = (ColumnContainerPresenter) ccv.getPresenter();
            // use ccp to set data in the column data.
            ccp.setProjectColumnActiveRecord(pcar);
            columnHBox.getChildren().add(ccv.getView());
        }
    }

    // Other methods


}