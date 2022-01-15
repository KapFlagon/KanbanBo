package persistence.services;

import com.j256.ormlite.dao.Dao;
import domain.entities.project.ObservableWorkspaceProject;
import javafx.collections.ObservableList;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.relateditems.RelatedItemTable;

import java.util.UUID;

public class WorkspaceService extends AbstractService{

    // TODO Make this the service to manage data for the "workspace" tab, specifically binding ViewModels for each entire project data.
    // Variables
    private Dao<ProjectTable, UUID> projectDao;
    private Dao<ProjectStatusTable, Integer> projectStatusDao;
    private Dao<ColumnTable, UUID> columnTableDao;
    private Dao<CardTable, UUID> cardTableDao;
    private Dao<RelatedItemTable, UUID> resourceItemDao;

    private ObservableList<ObservableWorkspaceProject> openedProjects;


    // Constructors



    // Getters and Setters


    // Initialisation methods


    // Other methods
    // TODO Design the "ViewModel" class for a project in the workspace view, taking into account its related item.
    // TODO Design the "ViewModel" class for a column inside a project in the workspace.
    // TODO Design the "ViewModel" class for a card inside a column in the workspace, taking into account its related item.
    // TODO needs a method that builds a ViewModel object for the project data and related items.
    // TODO needs a method that builds a list of ViewModel objects for the columns.
    // TODO needs a method that builds a list of ViewModel objects for the cards, and their related items.


    public void createColumn() {

    }

    public void updateColumn() {

    }

    public void deleteColumn() {
        // TODO Also delete all child cards within the column
        // TODO delete all child resourceItems for the child cards
    }

    public void deleteColumns(ObservableList columnList) {

    }

    public void createCard() {

    }

    public void updateCard() {

    }

    public void deleteCard() {
        // TODO delete all child resourceItems for the card
    }

    public void deleteCards(ObservableList cardList) {

    }

    public void createProjectResourceItem() {

    }

    public void updateProjectResourceItem() {

    }

    public void deleteProjectResourceItem() {

    }

    public void deleteProjectResourceItems(ObservableList projectResourceItemsList) {

    }

    public void createCardResourceItem() {

    }

    public void updateCardResourceItem() {

    }

    public void deleteCardResourceItem() {

    }

    public void deleteCardResourceItems(ObservableList resourceItemsList) {

    }
}
