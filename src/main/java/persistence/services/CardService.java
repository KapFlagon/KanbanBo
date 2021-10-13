package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.QueryBuilder;
import domain.dto.CardDTO;
import domain.dto.ResourceItemDTO;
import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableProject;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.resourceitems.ResourceItemTable;
import persistence.tables.resourceitems.ResourceItemTypeTable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;

public class CardService extends AbstractService{


    // Variables
    private final Locale locale;
    private final ResourceBundle resourceBundle;
    private ObservableList<ObservableProject> projectsList;
    private ObservableList<ObservableProject> workspaceProjectsList;

    private Dao<ProjectTable, UUID> projectDao;
    private Dao<ProjectStatusTable, Integer> projectStatusDao;
    private Dao<ColumnTable, UUID> columnDao;
    private Dao<CardTable, UUID> cardDao;
    private Dao<ResourceItemTable, UUID> resourceItemDao;
    private Dao<ResourceItemTypeTable, Integer> resourceItemTypeDao;

    private QueryBuilder<ColumnTable, UUID> columnTableQueryBuilder;
    private QueryBuilder<CardTable, UUID> cardTableQueryBuilder;
    private QueryBuilder<ResourceItemTable, UUID> resourceItemTableQueryBuilder;
    private QueryBuilder<ResourceItemTypeTable, Integer> resourceItemTypeTableQueryBuilder;

    private DeleteBuilder<ColumnTable, UUID> columnTableDeleteBuilder;
    private DeleteBuilder<CardTable, UUID> cardTableDeleteBuilder;
    private DeleteBuilder<ResourceItemTable, UUID> resourceItemTableDeleteBuilder;



    // Constructors
    public CardService (Locale locale, ResourceBundle resourceBundle, ObservableList<ObservableProject> projectsList, ObservableList<ObservableProject> workspaceProjectsList) {
        this.locale = locale;
        this.resourceBundle = resourceBundle;
        this.projectsList = projectsList;
        this.workspaceProjectsList = workspaceProjectsList;
    }

    // Getters and Setters


    // Initialisation methods


    // Other methods
    public void createCard(UUID parentColumnUUID, String title, String description) throws SQLException, IOException {
        CardTable card = new CardTable();
        card.setParent_column_uuid(parentColumnUUID);
        card.setCard_title(title);
        card.setCard_description_text(description);

        setupDbConnection();

        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        cardTableQueryBuilder = cardDao.queryBuilder();
        cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, parentColumnUUID);
        long cardCount = cardTableQueryBuilder.countOf();
        int position = (int) (cardCount + 1);
        card.setCard_position(position);

        ColumnTable column = columnDao.queryForId(parentColumnUUID);
        ProjectTable project = projectDao.queryForId(column.getParent_project_uuid());
        project.setLast_changed_timestamp(getOffsetNowTime());
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                cardDao.create(card);
                projectDao.update(project);
                return null;
            }
        });
        teardownDbConnection();

        for(ObservableProject observableProject : workspaceProjectsList) {
            if(project.getID().equals(observableProject.getProjectUUID())) {
                for(ObservableColumn observableColumn : observableProject.getColumns()) {
                    if(observableColumn.getColumnUUID().equals(parentColumnUUID)) {
                        ObservableCard newObservableCard = new ObservableCard(card, FXCollections.observableArrayList());
                        newObservableCard.positionProperty().addListener((observable, oldVal, newVal) -> {
                            // TODO implement function to change position of the Card in its list
                        });
                        observableColumn.getCards().add(newObservableCard);
                    }
                }
            }
        }
    }

    public void updateCard(CardDTO newCardData, ObservableCard observableCard) throws SQLException, IOException {
        CardTable cardTable = new CardTable();
        cardTable.setCard_uuid(newCardData.getUuid());
        cardTable.setParent_column_uuid(newCardData.getParentColumnUUID());
        cardTable.setCard_title(newCardData.getTitle());
        cardTable.setCard_description_text(newCardData.getDescription());
        cardTable.setCard_position(newCardData.getPosition());

        ArrayList<ResourceItemTable> newResourceData = new ArrayList<>();
        for(ResourceItemDTO resourceItemDTO : newCardData.getResourcesList()) {
            ResourceItemTable resourceItemTable = new ResourceItemTable();
            resourceItemTable.setResource_item_uuid(resourceItemDTO.getUuid());
            resourceItemTable.setParent_item_uuid(resourceItemDTO.getParentUUID());
            resourceItemTable.setResource_item_title(resourceItemDTO.getTitle());
            resourceItemTable.setResource_item_type(resourceItemDTO.getType());
            resourceItemTable.setResource_item_path(resourceItemDTO.getPath());
        }

        setupDbConnection();
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        ArrayList<ResourceItemTable> resourcesForDeletion = new ArrayList<>();

        for(ObservableResourceItem observableResourceItem : observableCard.getResourceItems()) {
            boolean resourceMarkedForDeletion = true;
            for(ResourceItemTable resourceItemTable : newResourceData) {
                if(observableResourceItem.getResourceItemUUID().equals(resourceItemTable.getResource_item_uuid())) {
                    resourceMarkedForDeletion = false;
                }
            }
            if (resourceMarkedForDeletion) {
                ResourceItemTable resourceItemTableForDeletion = new ResourceItemTable();
                resourceItemTableForDeletion.setResource_item_uuid(observableResourceItem.getResourceItemUUID());
                resourceItemTableForDeletion.setParent_item_uuid(observableResourceItem.getParentItemUUID());
            }
        }

        ColumnTable column = columnDao.queryForId(cardTable.getParent_column_uuid());
        ProjectTable project = projectDao.queryForId(column.getParent_project_uuid());
        project.setLast_changed_timestamp(getOffsetNowTime());
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                cardDao.update(cardTable);
                for(ResourceItemTable resourceItemTable : newResourceData) {
                    resourceItemDao.createOrUpdate(resourceItemTable);
                }
                for(ResourceItemTable resourceItemTable : resourcesForDeletion) {
                    resourceItemDao.delete(resourceItemTable);
                }
                projectDao.update(project);
                return null;
            }
        });
        observableCard.setCardTitle(newCardData.getTitle());
        observableCard.setCardDescription(newCardData.getDescription());
        observableCard.setPosition(newCardData.getPosition());
        //observableCard.setResourceItems(newCardData.getResourcesList());
        // TODO need to figure out how to update the resource items...
        teardownDbConnection();
    }

    public void deleteCard(ObservableCard card) throws SQLException, IOException {
        setupDbConnection();
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        resourceItemDao = DaoManager.createDao(connectionSource, ResourceItemTable.class);
        resourceItemTableDeleteBuilder = resourceItemDao.deleteBuilder();
        ColumnTable column = columnDao.queryForId(card.getParentColumnUUID());
        ProjectTable project = projectDao.queryForId(column.getParent_project_uuid());
        project.setLast_changed_timestamp(getOffsetNowTime());

        List<PreparedDelete<ResourceItemTable>> resourceItemPreparedDeleteList = new ArrayList<PreparedDelete<ResourceItemTable>>();

        for (ObservableResourceItem observableResourceItem : card.getResourceItems()) {
            resourceItemTableDeleteBuilder.reset();
            resourceItemTableDeleteBuilder.where().eq(ResourceItemTable.FOREIGN_KEY_NAME, card.getCardUUID());
            resourceItemPreparedDeleteList.add(resourceItemTableDeleteBuilder.prepare());
        }
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for(PreparedDelete<ResourceItemTable> resourceItemPreparedDelete : resourceItemPreparedDeleteList) {
                    resourceItemDao.delete(resourceItemPreparedDelete);
                }
                cardDao.deleteById(card.getCardUUID());
                projectDao.update(project);
                return null;
            }
        });
        teardownDbConnection();
        for(ObservableProject observableProject : workspaceProjectsList) {
            if(observableProject.getProjectUUID().equals(column.getParent_project_uuid())){
                for(ObservableColumn observableColumn : observableProject.getColumns()) {
                    for(Iterator<ObservableCard> observableCardIterator = observableColumn.getCards().listIterator(); observableCardIterator.hasNext();) {
                        UUID tempUUID = observableCardIterator.next().getCardUUID();
                        if(tempUUID.equals(card.getCardUUID())) {
                            observableCardIterator.remove();
                        }
                    }

                }
            }
        }
    }

    public void copyCard(ObservableCard observableCard) {
        // TODO Implement this
    }



}
