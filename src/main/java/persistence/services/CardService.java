package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.QueryBuilder;
import persistence.dto.card.CardDTO;
import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableProject;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.dto.column.ColumnDTO;
import persistence.mappers.DTOToTable;
import persistence.mappers.TableToDTO;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectStatusTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.resourceitems.ResourceItemTable;
import persistence.tables.resourceitems.ResourceItemTypeTable;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Callable;

public class CardService extends AbstractService{


    // Variables
    private final Locale locale;
    private final ResourceBundle resourceBundle;
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
    public CardService (Locale locale, ResourceBundle resourceBundle, ObservableList<ObservableProject> workspaceProjectsList) {
        this.locale = locale;
        this.resourceBundle = resourceBundle;
        this.workspaceProjectsList = workspaceProjectsList;
    }

    // Getters and Setters


    // Initialisation methods


    // Other methods
    public void createCard(CardDTO cardDTO) throws SQLException, IOException {
        CardTable card = DTOToTable.mapCardDTOToCardTable(cardDTO);
        card.setCreation_timestamp(ZonedDateTime.now().toString());
        card.setLast_changed_timestamp(ZonedDateTime.now().toString());

        setupDbConnection();

        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        cardTableQueryBuilder = cardDao.queryBuilder();
        cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, cardDTO.getParentColumnUUID());
        long cardCount = cardTableQueryBuilder.countOf();
        int position = (int) (cardCount);
        card.setCard_position(position);

        ColumnTable column = columnDao.queryForId(cardDTO.getParentColumnUUID());
        ProjectTable project = projectDao.queryForId(column.getParent_project_uuid());
        project.setLast_changed_timestamp(ZonedDateTime.now().toString());
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                cardDao.create(card);
                cardDTO.setUuid(card.getID());
                projectDao.update(project);
                return 1;
            }
        });
        teardownDbConnection();

        for(ObservableProject observableProject : workspaceProjectsList) {
            if(project.getID().equals(observableProject.getProjectUUID())) {
                for(ObservableColumn observableColumn : observableProject.getColumns()) {
                    if(observableColumn.getColumnUUID().equals(cardDTO.getParentColumnUUID())) {
                        ObservableCard newObservableCard = new ObservableCard(cardDTO, FXCollections.observableArrayList());
                        newObservableCard.positionProperty().addListener((observable, oldVal, newVal) -> {
                            // TODO implement function to change position of the Card in its list
                        });
                        observableColumn.getCards().add(newObservableCard);
                    }
                }
            }
        }
    }

    public void updateCard(CardDTO cardDTO, ObservableCard observableCard) throws SQLException, IOException {
        CardTable cardTable = new CardTable();
        cardTable.setCard_uuid(cardDTO.getUuid());
        cardTable.setParent_column_uuid(cardDTO.getParentColumnUUID());
        cardTable.setCard_title(cardDTO.getTitle());
        cardTable.setCard_description_text(cardDTO.getDescription());
        cardTable.setCard_position(cardDTO.getPosition());

        ArrayList<ResourceItemTable> newResourceData = new ArrayList<>();
        /*for(ResourceItemDTO resourceItemDTO : cardDTO.getResourcesList()) {
            ResourceItemTable resourceItemTable = new ResourceItemTable();
            resourceItemTable.setResource_item_uuid(resourceItemDTO.getUuid());
            resourceItemTable.setParent_item_uuid(resourceItemDTO.getParentUUID());
            resourceItemTable.setResource_item_title(resourceItemDTO.getTitle());
            resourceItemTable.setResource_item_type(resourceItemDTO.getType());
            resourceItemTable.setResource_item_path(resourceItemDTO.getPath());
        }*/

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
        project.setLast_changed_timestamp(ZonedDateTime.now().toString());
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
                return 1;
            }
        });
        observableCard.setCardTitle(cardDTO.getTitle());
        observableCard.setCardDescription(cardDTO.getDescription());
        observableCard.setPosition(cardDTO.getPosition());
        //observableCard.setResourceItems(cardDTO.getResourcesList());
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
        project.setLast_changed_timestamp(ZonedDateTime.now().toString());

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
                return 1;
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

    public void moveCard(CardDTO newCardDataDTO, ObservableCard oldObservableCard) throws SQLException, IOException {
        boolean stillInOriginalColumn = newCardDataDTO.getParentColumnUUID().equals(oldObservableCard.getParentColumnUUID());

        if(stillInOriginalColumn) {
            int newPosition = newCardDataDTO.getPosition();
            int oldPosition = oldObservableCard.positionProperty().getValue();
            if(newPosition != oldPosition) {
                setupDbConnection();

                cardDao = DaoManager.createDao(connectionSource,CardTable.class);
                cardTableQueryBuilder = cardDao.queryBuilder();
                cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, newCardDataDTO.getParentColumnUUID());
                List<CardTable> cardTableList = cardTableQueryBuilder.query();
                teardownDbConnection();

                List<CardDTO> cardDTOList = new ArrayList<>();
                for(CardTable cardTable : cardTableList) {
                    CardDTO cardDTO = TableToDTO.mapCardTableToColumnDTO(cardTable);
                    if(!cardTable.getCard_uuid().equals(newCardDataDTO.getUuid())) {
                        cardDTOList.add(cardDTO);
                    }
                }
                if(newPosition < oldPosition) {
                    shiftSurroundingCardsRight(cardDTOList, newPosition);
                } else if(newPosition > oldPosition) {
                    shiftSurroundingCardsLeft(cardDTOList, newPosition);
                }
                cardDTOList.add(newCardDataDTO);
                ObservableList<ObservableCard> observableCardObservableList = FXCollections.observableArrayList();
                for(ObservableProject observableProject : workspaceProjectsList) {
                    for(ObservableColumn observableColumn : observableProject.getColumns()) {
                        if(observableColumn.getColumnUUID().equals(newCardDataDTO.getParentColumnUUID())) {
                            observableCardObservableList = observableColumn.getCards();
                        }
                    }
                }
                updateCards(cardDTOList, observableCardObservableList);
            }
        } else {
            setupDbConnection();

            cardDao = DaoManager.createDao(connectionSource,CardTable.class);
            cardTableQueryBuilder = cardDao.queryBuilder();
            cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, newCardDataDTO.getParentColumnUUID());
            List<CardTable> targetCardTableList = cardTableQueryBuilder.query();
            cardTableQueryBuilder.reset();
            cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, oldObservableCard.getParentColumnUUID());
            List<CardTable> sourceCardTableList = cardTableQueryBuilder.query();
            teardownDbConnection();

            List<CardDTO> targetCardDTOList = new ArrayList<>();
            List<CardDTO> sourceCardDTOList = new ArrayList<>();
            for(CardTable cardTable : sourceCardTableList) {
                CardDTO cardDTO = TableToDTO.mapCardTableToColumnDTO(cardTable);
                if(!cardTable.getCard_uuid().equals(newCardDataDTO.getUuid())) {
                    sourceCardDTOList.add(cardDTO);
                }
            }

            for(CardTable cardTable : targetCardTableList) {
                CardDTO cardDTO = TableToDTO.mapCardTableToColumnDTO(cardTable);
                targetCardDTOList.add(cardDTO);
            }

            shiftSurroundingCardsRight(targetCardDTOList, newCardDataDTO.getPosition());
            targetCardDTOList.add(newCardDataDTO);
            shiftSurroundingCardsLeft(sourceCardDTOList, oldObservableCard.positionProperty().getValue());

            ObservableList<ObservableCard> sourceCardObservableList = FXCollections.observableArrayList();
            for(ObservableProject observableProject : workspaceProjectsList) {
                for(ObservableColumn observableColumn : observableProject.getColumns()) {
                    if(observableColumn.getColumnUUID().equals(newCardDataDTO.getParentColumnUUID())) {
                        sourceCardObservableList = observableColumn.getCards();
                    }
                }
            }

            ObservableList<ObservableCard> targetCardObservableList = FXCollections.observableArrayList();
            for(ObservableProject observableProject : workspaceProjectsList) {
                for(ObservableColumn observableColumn : observableProject.getColumns()) {
                    if(observableColumn.getColumnUUID().equals(newCardDataDTO.getParentColumnUUID())) {
                        targetCardObservableList = observableColumn.getCards();
                    }
                }
            }

            updateCards(sourceCardDTOList, sourceCardObservableList);
            updateCards(targetCardDTOList, targetCardObservableList);
        }
    }

    private void shiftSurroundingCardsRight(List<CardDTO> cardDTOList, int insertPosition) {
        for(CardDTO cardDTO : cardDTOList) {
            if(cardDTO.getPosition() >= insertPosition) {
                cardDTO.setPosition(cardDTO.getPosition() + 1);
            }
        }
    }

    private void shiftSurroundingCardsLeft(List<CardDTO> cardDTOList, int insertPosition) {
        for(CardDTO cardDTO : cardDTOList) {
            if(cardDTO.getPosition() <= insertPosition) {
                cardDTO.setPosition(cardDTO.getPosition() - 1);
            }
        }
    }

    private void updateCards(List<CardDTO> cardDTOList, ObservableList<ObservableCard> observableCardObservableList) throws SQLException, IOException {
        List<CardTable> cardTableList = new ArrayList<>();
        for(CardDTO cardDTO : cardDTOList) {
            CardTable cardTable = DTOToTable.mapCardDTOToCardTable(cardDTO);
            cardTableList.add(cardTable);
        }
        setupDbConnection();
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        final int[] result = new int[1];
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                result[0] = 0;
                ColumnTable parentColumnTable = columnDao.queryForId(cardTableList.get(0).getParent_column_uuid());
                parentColumnTable.setLast_changed_timestamp(ZonedDateTime.now().toString());

                ProjectTable parentProjectTable = projectDao.queryForId(parentColumnTable.getParent_project_uuid());
                parentProjectTable.setLast_changed_timestamp(ZonedDateTime.now().toString());

                for(CardTable cardTable : cardTableList) {
                    result[0] += cardDao.update(cardTable);
                }
                result[0] += columnDao.update(parentColumnTable);
                result[0] += projectDao.update(parentProjectTable);
                System.out.println("Cards, Column, and project updated successfully");
                return 1;
            }
        });
        teardownDbConnection();

        if (result[0] > 1) {
            for(ObservableCard observableCard : observableCardObservableList) {
                for(CardDTO cardDTO : cardDTOList) {
                    if(observableCard.getCardUUID().equals(cardDTO.getUuid())) {
                        observableCard.setParentColumnUUID(cardDTO.getParentColumnUUID());
                        observableCard.setCardUUID(cardDTO.getUuid());
                        observableCard.setCardTitle(cardDTO.getTitle());
                        observableCard.setCardDescription(cardDTO.getDescription());
                        observableCard.setPosition(cardDTO.getPosition());
                        // TODO observableCard.setResourceItems(cardDTO.);
                    }
                }
            }
            observableCardObservableList.sort(new Comparator<ObservableCard>() {
                @Override
                public int compare(ObservableCard o1, ObservableCard o2) {
                    return o1.positionProperty().getValue().compareTo(o2.positionProperty().getValue());
                }
            });
        }
    }



}
