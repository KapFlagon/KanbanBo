package view.screens.mainscreen.subviews.workspace.subviews.projectcontainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.activerecords.ProjectActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.column.ActiveProjectColumnModel;
import model.domainobjects.project.ActiveProjectModel;
import model.repositories.ActiveColumnListRepository;
import model.repositories.services.ProjectColumnRepositoryService;
import view.screens.mainscreen.subviews.workspace.subviews.columncontainer.ColumnContainerPresenter;
import view.screens.mainscreen.subviews.workspace.subviews.columncontainer.ColumnContainerView;

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

    // Other variables
    //private AbstractProjectModel projectModel;
    private ProjectActiveRecord<ActiveProjectModel> projectActiveRecord;
    private ProjectColumnRepositoryService projectColumnRepositoryService;
    private ActiveColumnListRepository activeColumnListRepository;
    private ObservableList<ProjectColumnActiveRecord> projectColumnsList;


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

    public ProjectActiveRecord<ActiveProjectModel> getProjectActiveRecord() {
        return projectActiveRecord;
    }
    public void setProjectActiveRecord(ProjectActiveRecord<ActiveProjectModel> projectActiveRecord) throws IOException, SQLException {
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
        projectColumnRepositoryService = new ProjectColumnRepositoryService(projectActiveRecord);
        projectColumnsList = projectColumnRepositoryService.getColumnsList();
        for (ProjectColumnActiveRecord<ActiveProjectColumnModel> projectColumnActiveRecord : projectColumnsList) {
            ColumnContainerView ccv = new ColumnContainerView();
            ColumnContainerPresenter ccp = (ColumnContainerPresenter) ccv.getPresenter();
            ccp.setProjectColumnActiveRecord(projectColumnActiveRecord);
            columnHBox.getChildren().add(ccv.getView());
        }
        activeColumnListRepository = projectColumnRepositoryService.getActiveColumnListRepository();
        // TODO get all column records linked to the project, populate the data.
    }

    // UI event methods
    public void createColumn() throws IOException, SQLException {
        System.out.println("Create Column...");
        ColumnContainerView ccv = new ColumnContainerView();
        ColumnContainerPresenter ccp = (ColumnContainerPresenter) ccv.getPresenter();
        // use ccp to set data in the column data.
        ProjectColumnActiveRecord<ActiveProjectColumnModel> pcar = new ProjectColumnActiveRecord<ActiveProjectColumnModel>(ActiveProjectColumnModel.class);
        ActiveProjectColumnModel apcm = new ActiveProjectColumnModel();
        apcm.setParent_project_uuid(projectActiveRecord.getProjectUUID());
        apcm.setColumn_position(projectColumnsList.size() + 1);
        pcar.setProjectColumnModel(apcm);
        pcar.createOrUpdateActiveRowInDb();
        ccp.setProjectColumnActiveRecord(pcar);
        columnHBox.getChildren().add(ccv.getView());
    }

    // Other methods


}