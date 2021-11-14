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
import domain.entities.project.ObservableWorkspaceProject;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<ObservableWorkspaceProject> workspaceProjectsList;

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
    public CardService (Locale locale, ResourceBundle resourceBundle, ObservableList<ObservableWorkspaceProject> workspaceProjectsList) {
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
        cardDTO.setCreatedOnTimeStamp(ZonedDateTime.parse(card.getCreation_timestamp()));
        card.setLast_changed_timestamp(ZonedDateTime.now().toString());
        cardDTO.setLastChangedOnTimeStamp(ZonedDateTime.parse(card.getLast_changed_timestamp()));

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

        for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
            if(project.getID().equals(observableWorkspaceProject.getProjectUUID())) {
                for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                    if(observableColumn.getColumnUUID().equals(cardDTO.getParentColumnUUID())) {
                        ObservableCard newObservableCard = new ObservableCard(cardDTO, FXCollections.observableArrayList());
                        newObservableCard.positionProperty().addListener((observable, oldVal, newVal) -> {
                            // TODO implement function to change position of the Card in its list
                        });
                        observableColumn.getCards().add(newObservableCard);
                        observableColumn.getCards().sort(new Comparator<ObservableCard>() {
                            @Override
                            public int compare(ObservableCard o1, ObservableCard o2) {
                                return o1.positionProperty().getValue().compareTo(o2.positionProperty().getValue());
                            }
                        });
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
        // TODO need to update other card position values if a card is removed from the list...
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
        for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
            if(observableWorkspaceProject.getProjectUUID().equals(column.getParent_project_uuid())){
                for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                    for(Iterator<ObservableCard> observableCardIterator = observableColumn.getCards().listIterator(); observableCardIterator.hasNext();) {
                        UUID tempUUID = observableCardIterator.next().getCardUUID();
                        if(tempUUID.equals(card.getCardUUID())) {
                            observableCardIterator.remove();
                            observableColumn.getCards().sort(new Comparator<ObservableCard>() {
                                @Override
                                public int compare(ObservableCard o1, ObservableCard o2) {
                                    return o1.positionProperty().getValue().compareTo(o2.positionProperty().getValue());
                                }
                            });
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
                    int diffVector = oldPosition - newPosition;
                    shiftSurroundingCardsRight(cardDTOList, newPosition, diffVector);
                } else if(newPosition > oldPosition) {
                    int diffVector = newPosition - oldPosition;
                    shiftSurroundingCardsLeft(cardDTOList, newPosition, diffVector);
                }
                cardDTOList.add(newCardDataDTO);
                ObservableList<ObservableCard> observableCardObservableList = FXCollections.observableArrayList();
                for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                    for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
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

            int targetDiffVector = targetCardDTOList.size() - newCardDataDTO.getPosition();
            shiftSurroundingCardsRight(targetCardDTOList, newCardDataDTO.getPosition(), targetDiffVector);
            targetCardDTOList.add(newCardDataDTO);

            int sourceDiffVector = sourceCardDTOList.size() - oldObservableCard.positionProperty().getValue();
            shiftSurroundingCardsLeft(sourceCardDTOList, sourceCardDTOList.size() + 1, sourceDiffVector);

            ObservableList<ObservableCard> sourceCardObservableList = FXCollections.observableArrayList();
            for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                    if(observableColumn.getColumnUUID().equals(oldObservableCard.getParentColumnUUID())) {
                        sourceCardObservableList = observableColumn.getCards();
                    }
                }
            }

            ObservableList<ObservableCard> targetCardObservableList = FXCollections.observableArrayList();
            for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                    if(observableColumn.getColumnUUID().equals(newCardDataDTO.getParentColumnUUID())) {
                        targetCardObservableList = observableColumn.getCards();
                    }
                }
            }

            updateCards(sourceCardDTOList, sourceCardObservableList);
            updateCards(targetCardDTOList, targetCardObservableList);
        }
    }

    private void shiftSurroundingCardsRight(List<CardDTO> cardDTOList, int insertPosition, int diffVector) {
        for(int iterator = 0; iterator < cardDTOList.size(); iterator ++) {
            CardDTO cardDTO = cardDTOList.get(iterator);
            if(cardDTO.getPosition() >= insertPosition
                    && cardDTO.getPosition() < cardDTOList.size()
                    && diffVector > 0) {
                cardDTO.setPosition(cardDTO.getPosition() + 1);
                diffVector -= 1;
            }
        }
    }

    private void shiftSurroundingCardsLeft(List<CardDTO> cardDTOList, int insertPosition, int diffVector) {
        for(int iterator = cardDTOList.size() - 1; iterator >= 0; iterator --) {
            CardDTO cardDTO = cardDTOList.get(iterator);
            if(cardDTO.getPosition() > 0
                    && cardDTO.getPosition() <= insertPosition
                    && diffVector > 0) {
                cardDTO.setPosition(cardDTO.getPosition() - 1);
                diffVector -= 1;
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
        ColumnTable parentColumnTable = columnDao.queryForId(cardTableList.get(0).getParent_column_uuid());
        ProjectTable parentProjectTable = projectDao.queryForId(parentColumnTable.getParent_project_uuid());
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                result[0] = 0;
                parentColumnTable.setLast_changed_timestamp(ZonedDateTime.now().toString());
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
            // TODO need to remove an observablecard from a list if it is not in the DTO list anymore, and add one if the opposite is true.
            if (observableCardObservableList.size() < cardDTOList.size()) {
                CardDTO cardDTOForAddition = null;
                boolean deltaNotFound = true;
                int outerIterator = 0;
                while(deltaNotFound && outerIterator < cardDTOList.size()) {
                    CardDTO cardDTOIteration = cardDTOList.get(outerIterator);
                    int innerIterator = 0;
                    boolean noMatchFound = true;
                    while(noMatchFound && innerIterator < observableCardObservableList.size()) {
                        ObservableCard observableCard = observableCardObservableList.get(innerIterator);
                        if(cardDTOIteration.getUuid().equals(observableCard.getCardUUID())) {
                            noMatchFound = false;
                        }
                        innerIterator +=1;
                    }
                    if(noMatchFound) {
                        cardDTOForAddition = cardDTOIteration;
                        deltaNotFound = false;
                    }
                    outerIterator += 1;
                }
                // TODO Create new card and add to project column card list
                for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                    if(observableWorkspaceProject.getProjectUUID().equals(parentProjectTable.getProject_uuid())) {
                        for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                            if(observableColumn.getColumnUUID().equals(cardDTOForAddition.getParentColumnUUID())) {
                                ObservableCard addedObservableCard = new ObservableCard(cardDTOForAddition);
                                observableColumn.getCards().add(addedObservableCard);
                            }
                        }
                    }
                }

            } else if (observableCardObservableList.size() > cardDTOList.size()) {
                ObservableCard observableCardForRemoval = null;
                boolean deltaNotFound = true;
                int outerIterator = 0;
                while(deltaNotFound && outerIterator < observableCardObservableList.size()) {
                    ObservableCard observableCardIteration = observableCardObservableList.get(outerIterator);
                    int innerIterator = 0;
                    boolean noMatchFound = true;
                    while (noMatchFound && innerIterator < cardDTOList.size()) {
                        CardDTO cardDTO = cardDTOList.get(innerIterator);
                        if (observableCardIteration.getCardUUID().equals(cardDTO.getUuid())) {
                            noMatchFound = false;
                        }
                        innerIterator += 1;
                    }
                    if (noMatchFound) {
                        observableCardForRemoval = observableCardIteration;
                        deltaNotFound = false;
                    }
                    outerIterator += 1;
                }
                // TODO Remove card from project column card list
                for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                    if(observableWorkspaceProject.getProjectUUID().equals(parentProjectTable.getProject_uuid())) {
                        for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                            if(observableColumn.getColumnUUID().equals(observableCardForRemoval.getParentColumnUUID())) {
                                observableColumn.getCards().remove(observableCardForRemoval);
                            }
                        }
                    }
                }
            }

            for (ObservableCard observableCard : observableCardObservableList) {
                for (CardDTO cardDTO : cardDTOList) {
                    if (observableCard.getCardUUID().equals(cardDTO.getUuid())) {
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
