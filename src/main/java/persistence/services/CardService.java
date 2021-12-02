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
import persistence.mappers.ObservableObjectToDTO;
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
        //card.setCreation_timestamp(ZonedDateTime.now().toString());
        //cardDTO.setCreatedOnTimeStamp(ZonedDateTime.parse(card.getCreation_timestamp()));
        //card.setLast_changed_timestamp(ZonedDateTime.now().toString());
        //cardDTO.setLastChangedOnTimeStamp(ZonedDateTime.parse(card.getLast_changed_timestamp()));

        setupDbConnection();

        cardDao = DaoManager.createDao(connectionSource, CardTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        cardTableQueryBuilder = cardDao.queryBuilder();
        cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, UUID.fromString(cardDTO.getParentColumnUUID()));
        long cardCount = cardTableQueryBuilder.countOf();
        int position = (int) (cardCount);
        card.setCard_position(position);

        List<CardTable> cardTableList = cardTableQueryBuilder.query();

        ColumnTable column = columnDao.queryForId(UUID.fromString(cardDTO.getParentColumnUUID()));
        ProjectTable project = projectDao.queryForId(column.getParent_project_uuid());
        project.setLast_changed_timestamp(ZonedDateTime.now().toString());
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                cardDao.create(card);
                //cardDTO.setUuid(card.getID());
                //cardDTO.setPosition(card.getCard_position());
                projectDao.update(project);
                return 1;
            }
        });
        teardownDbConnection();

        for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
            if(project.getID().equals(observableWorkspaceProject.getProjectUUID())) {
                for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                    if(observableColumn.getColumnUUID().equals(UUID.fromString(cardDTO.getParentColumnUUID()))) {
                        shiftSurroundingCardsRight(cardTableList, cardDTO.getPosition(), (cardTableList.size() - card.getCard_position()));
                        updateCardTables(cardTableList, observableColumn.getCards());
                    }
                }
            }
        }
    }

    public void updateCard(CardDTO cardDTO, ObservableCard observableCard) throws SQLException, IOException {
        CardTable cardTable = DTOToTable.mapCardDTOToCardTable(cardDTO);

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
        boolean stillInOriginalColumn = UUID.fromString(newCardDataDTO.getParentColumnUUID()).equals(oldObservableCard.getParentColumnUUID());

        if(stillInOriginalColumn) {
            int newPosition = newCardDataDTO.getPosition();
            int oldPosition = oldObservableCard.positionProperty().getValue();
            if(newPosition != oldPosition) {
                setupDbConnection();

                cardDao = DaoManager.createDao(connectionSource,CardTable.class);
                cardTableQueryBuilder = cardDao.queryBuilder();
                cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, UUID.fromString(newCardDataDTO.getParentColumnUUID()));
                List<CardTable> cardTableList = cardTableQueryBuilder.query();
                teardownDbConnection();

                CardTable movedCard = null;
                for(CardTable cardTable : cardTableList) {
                    if(cardTable.getCard_uuid().equals(UUID.fromString(newCardDataDTO.getUuid()))) {
                        movedCard = cardTable;
                        movedCard.setCard_position(newCardDataDTO.getPosition());
                        cardTableList.remove(cardTable);
                    }
                }
                if(newPosition < oldPosition) {
                    int diffVector = oldPosition - newPosition;
                    shiftSurroundingCardsRight(cardTableList, newPosition, diffVector);
                } else if(newPosition > oldPosition) {
                    int diffVector = newPosition - oldPosition;
                    shiftSurroundingCardsLeft(cardTableList, newPosition, diffVector);
                }
                cardTableList.add(movedCard);
                ObservableList<ObservableCard> observableCardObservableList = FXCollections.observableArrayList();
                for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                    for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                        if(observableColumn.getColumnUUID().equals(UUID.fromString(newCardDataDTO.getParentColumnUUID()))) {
                            observableCardObservableList = observableColumn.getCards();
                        }
                    }
                }
                updateCardTables(cardTableList, observableCardObservableList);
            }
        } else {
            setupDbConnection();

            cardDao = DaoManager.createDao(connectionSource,CardTable.class);
            cardTableQueryBuilder = cardDao.queryBuilder();
            cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, UUID.fromString(newCardDataDTO.getParentColumnUUID()));
            List<CardTable> targetCardTableList = cardTableQueryBuilder.query();
            cardTableQueryBuilder.reset();
            cardTableQueryBuilder.where().eq(CardTable.FOREIGN_KEY_NAME, oldObservableCard.getParentColumnUUID());
            List<CardTable> sourceCardTableList = cardTableQueryBuilder.query();
            teardownDbConnection();


            CardTable movedCardTable = null;
            for(CardTable cardTable : sourceCardTableList) {
                if(cardTable.getCard_uuid().equals(UUID.fromString(newCardDataDTO.getUuid()))) {
                    movedCardTable = cardTable;
                    movedCardTable.setCard_position(newCardDataDTO.getPosition());
                    movedCardTable.setParent_column_uuid(UUID.fromString(newCardDataDTO.getParentColumnUUID()));
                    sourceCardTableList.remove(cardTable);
                    targetCardTableList.add(cardTable);
                }
            }


            int targetDiffVector = targetCardTableList.size() - newCardDataDTO.getPosition();
            shiftSurroundingCardsRight(targetCardTableList, newCardDataDTO.getPosition(), targetDiffVector);
            targetCardTableList.add(movedCardTable);

            int sourceDiffVector = sourceCardTableList.size() - oldObservableCard.positionProperty().getValue();
            shiftSurroundingCardsLeft(sourceCardTableList, sourceCardTableList.size() + 1, sourceDiffVector);

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
                    if(observableColumn.getColumnUUID().equals(UUID.fromString(newCardDataDTO.getParentColumnUUID()))) {
                        targetCardObservableList = observableColumn.getCards();
                    }
                }
            }

            updateCardTables(sourceCardTableList, sourceCardObservableList);
            updateCardTables(targetCardTableList, targetCardObservableList);
        }
    }

    private void shiftSurroundingCardsRight(List<CardTable> cardTableList, int insertPosition, int diffVector) {
        for(int iterator = 0; iterator < cardTableList.size(); iterator ++) {
            CardTable cardTable = cardTableList.get(iterator);
            if(cardTable.getCard_position() >= insertPosition
                    && cardTable.getCard_position() < cardTableList.size()
                    && diffVector > 0) {
                cardTable.setCard_position(cardTable.getCard_position() + 1);
                diffVector -= 1;
            }
        }
    }

    private void shiftSurroundingCardsLeft(List<CardTable> cardTableList, int insertPosition, int diffVector) {
        for(int iterator = cardTableList.size() - 1; iterator >= 0; iterator --) {
            CardTable cardTable = cardTableList.get(iterator);
            if(cardTable.getCard_position() > 0
                    && cardTable.getCard_position() <= insertPosition
                    && diffVector > 0) {
                cardTable.setCard_position(cardTable.getCard_position() - 1);
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
        updateCardTables(cardTableList, observableCardObservableList);
    }

    private void updateCardTables(List<CardTable> cardTableList, ObservableList<ObservableCard> observableCardObservableList) throws SQLException, IOException {
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
            if (observableCardObservableList.size() < cardTableList.size()) {
                CardTable cardTableForAddition = null;
                boolean deltaNotFound = true;
                int outerIterator = 0;
                while(deltaNotFound && outerIterator < cardTableList.size()) {
                    CardTable cardTableIteration = cardTableList.get(outerIterator);
                    int innerIterator = 0;
                    boolean noMatchFound = true;
                    while(noMatchFound && innerIterator < observableCardObservableList.size()) {
                        ObservableCard observableCard = observableCardObservableList.get(innerIterator);
                        if(cardTableIteration.getCard_uuid().equals(observableCard.getCardUUID())) {
                            noMatchFound = false;
                        }
                        innerIterator +=1;
                    }
                    if(noMatchFound) {
                        cardTableForAddition = cardTableIteration;
                        deltaNotFound = false;
                    }
                    outerIterator += 1;
                }
                // TODO Create new card and add to project column card list
                for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                    if(observableWorkspaceProject.getProjectUUID().equals(parentProjectTable.getProject_uuid())) {
                        for(ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                            if(observableColumn.getColumnUUID().equals(cardTableForAddition.getParent_column_uuid())) {
                                CardDTO innerDTO = TableToDTO.mapCardTableToCardDTO(cardTableForAddition);
                                ObservableCard addedObservableCard = new ObservableCard(innerDTO);
                                observableColumn.getCards().add(addedObservableCard);
                            }
                        }
                    }
                }

            } else if (observableCardObservableList.size() > cardTableList.size()) {
                ObservableCard observableCardForRemoval = null;
                boolean deltaNotFound = true;
                int outerIterator = 0;
                while(deltaNotFound && outerIterator < observableCardObservableList.size()) {
                    ObservableCard observableCardIteration = observableCardObservableList.get(outerIterator);
                    int innerIterator = 0;
                    boolean noMatchFound = true;
                    while (noMatchFound && innerIterator < cardTableList.size()) {
                        CardTable cardTable = cardTableList.get(innerIterator);
                        if (observableCardIteration.getCardUUID().equals(cardTable.getCard_uuid())) {
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
                for (CardTable cardTable : cardTableList) {
                    if (observableCard.getCardUUID().equals(cardTable.getCard_uuid())) {
                        observableCard.setParentColumnUUID(cardTable.getParent_column_uuid());
                        observableCard.setCardUUID(cardTable.getCard_uuid());
                        observableCard.setCardTitle(cardTable.getCard_title());
                        observableCard.setCardDescription(cardTable.getCard_description_text());
                        observableCard.setPosition(cardTable.getCard_position());
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
