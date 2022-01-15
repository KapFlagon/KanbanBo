package persistence.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import domain.entities.project.ObservableWorkspaceProject;
import domain.entities.relateditem.ObservableRelatedItem;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import persistence.dto.RelatedItemDTO;
import persistence.mappers.DTOToTable;
import persistence.mappers.ObservableObjectToDTO;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.relateditems.RelatedItemTable;
import persistence.tables.relateditems.RelatedItemTypeTable;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Callable;

public class RelatedItemService extends AbstractService{


    // Variables
    private final Locale locale;
    private final ResourceBundle resourceBundle;

    private ObservableList<ObservableWorkspaceProject> workspaceProjectsList;

    private Dao<RelatedItemTable, UUID> relatedItemDao;
    private Dao<RelatedItemTypeTable, Integer> relatedItemTypeDao;
    private Dao<ProjectTable, UUID> projectDao;
    private Dao<ColumnTable, UUID> columnDao;
    private Dao<CardTable, UUID> cardDao;


    // Constructors
    public RelatedItemService(Locale locale, ResourceBundle resourceBundle, ObservableList<ObservableWorkspaceProject> workspaceProjectsList) {
        this.locale = locale;
        this.resourceBundle = resourceBundle;
        this.workspaceProjectsList = workspaceProjectsList;
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public void createProjectRelatedItem(RelatedItemDTO newRelatedItemDTO) throws SQLException, IOException {
        setupDbConnection();
        relatedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        ProjectTable projectTable = projectDao.queryForId(newRelatedItemDTO.getParentUUID());
        projectTable.setLast_changed_timestamp(ZonedDateTime.now().toString());

        RelatedItemTable relatedItemTable = DTOToTable.mapRelatedItemDTOToRelatedItemTable(newRelatedItemDTO);

        final int[] result = {0};

        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                result[0] += relatedItemDao.create(relatedItemTable);
                result[0] += projectDao.update(projectTable);

                return 1;
            }
        });

        teardownDbConnection();

        if (result[0] > 0) {
            ObservableRelatedItem observableRelatedItem = new ObservableRelatedItem(
                    newRelatedItemDTO.getParentUUID(),
                    relatedItemTable.getRelated_item_uuid(),
                    newRelatedItemDTO.getTitle(),
                    newRelatedItemDTO.getPath(),
                    newRelatedItemDTO.getType(),
                    "");
            for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                if (observableWorkspaceProject.getProjectUUID().equals(observableRelatedItem.getParentItemUUID())) {
                    observableWorkspaceProject.getResourceItems().add(observableRelatedItem);
                }
            }
        }
    }

    public void updateProjectRelatedItem(RelatedItemDTO relatedItemDTO, ObservableRelatedItem observableRelatedItem) throws SQLException, IOException {
        setupDbConnection();
        relatedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        ProjectTable projectTable = projectDao.queryForId(relatedItemDTO.getParentUUID());
        projectTable.setLast_changed_timestamp(ZonedDateTime.now().toString());

        RelatedItemTable relatedItemTable = DTOToTable.mapRelatedItemDTOToRelatedItemTable(relatedItemDTO);

        final int[] result = {0};

        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                result[0] += relatedItemDao.update(relatedItemTable);
                result[0] += projectDao.update(projectTable);

                return 1;
            }
        });

        teardownDbConnection();

        if (result[0] > 0) {
            observableRelatedItem.titleProperty().setValue(relatedItemTable.getRelated_item_title());
            observableRelatedItem.typeProperty().setValue(relatedItemTable.getRelated_item_type());
            observableRelatedItem.pathProperty().setValue(relatedItemTable.getRelated_item_path());
        }
    }

    public void deleteProjectRelatedItem(ObservableRelatedItem observableRelatedItem) throws SQLException, IOException {
        setupDbConnection();
        relatedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);

        ProjectTable projectTable = projectDao.queryForId(observableRelatedItem.getParentItemUUID());
        projectTable.setLast_changed_timestamp(ZonedDateTime.now().toString());

        RelatedItemTable relatedItemTable = new RelatedItemTable();
        relatedItemTable.setRelated_item_uuid(observableRelatedItem.getRelatedItemUUID());
        relatedItemTable.setParent_item_uuid(observableRelatedItem.getParentItemUUID());
        relatedItemTable.setRelated_item_title(observableRelatedItem.titleProperty().getValue());
        relatedItemTable.setRelated_item_type(observableRelatedItem.typeProperty().getValue());
        relatedItemTable.setRelated_item_path(observableRelatedItem.pathProperty().getValue());

        final int[] result = {0};

        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                result[0] += relatedItemDao.delete(relatedItemTable);
                result[0] += projectDao.update(projectTable);

                return 1;
            }
        });

        teardownDbConnection();

        if(result[0] > 0){
            for(ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                if(observableWorkspaceProject.getProjectUUID().equals(relatedItemTable.getParent_item_uuid())){
                    for(Iterator<ObservableRelatedItem> observableRelatedItemIterator = observableWorkspaceProject.getResourceItems().listIterator(); observableRelatedItemIterator.hasNext();) {
                        UUID tempUUID = observableRelatedItemIterator.next().getRelatedItemUUID();
                        if(tempUUID.equals(relatedItemTable.getRelated_item_uuid())) {
                            observableRelatedItemIterator.remove();
                        }
                    }
                }
            }
        }
    }

    public void createCardRelatedItem(RelatedItemDTO newRelatedItemDTO) throws SQLException, IOException {
        setupDbConnection();
        relatedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);

        ZonedDateTime lastChangedTimestamp =ZonedDateTime.now();
        CardTable cardTable = cardDao.queryForId(newRelatedItemDTO.getParentUUID());

        ColumnTable columnTable = columnDao.queryForId(cardTable.getParent_column_uuid());
        ProjectTable projectTable = projectDao.queryForId(columnTable.getParent_project_uuid());

        RelatedItemTable relatedItemTable = DTOToTable.mapRelatedItemDTOToRelatedItemTable(newRelatedItemDTO);

        final int[] result = {0};
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                cardTable.setLast_changed_timestamp(lastChangedTimestamp.toString());
                projectTable.setLast_changed_timestamp(lastChangedTimestamp.toString());

                result[0] += relatedItemDao.create(relatedItemTable);
                result[0] += cardDao.update(cardTable);
                result[0] += projectDao.update(projectTable);

                return null;
            }
        });

        teardownDbConnection();

        if (result[0] > 0) {
            ObservableRelatedItem observableRelatedItem = new ObservableRelatedItem(
                    newRelatedItemDTO.getRelatedItemUUID(),
                    relatedItemTable.getRelated_item_uuid(),
                    newRelatedItemDTO.getTitle(),
                    newRelatedItemDTO.getPath(),
                    newRelatedItemDTO.getType(),
                    "");
            for (ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                if (observableWorkspaceProject.getProjectUUID().equals(projectTable.getProject_uuid())) {
                    for (ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                        if (observableColumn.getColumnUUID().equals(cardTable.getParent_column_uuid())) {
                            for (ObservableCard observableCard : observableColumn.getCards()) {
                                if(observableCard.getCardUUID().equals(observableRelatedItem.getParentItemUUID())) {
                                    observableCard.getResourceItems().add(observableRelatedItem);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void updateCardRelatedItem(RelatedItemDTO relatedItemDTO, ObservableRelatedItem observableRelatedItem) throws SQLException, IOException {
        setupDbConnection();
        relatedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);

        ZonedDateTime lastChangedTimestamp =ZonedDateTime.now();
        CardTable cardTable = cardDao.queryForId(relatedItemDTO.getParentUUID());
        cardTable.setLast_changed_timestamp(lastChangedTimestamp.toString());
        ColumnTable columnTable = columnDao.queryForId(cardTable.getParent_column_uuid());
        ProjectTable projectTable = projectDao.queryForId(columnTable.getParent_project_uuid());
        projectTable.setLast_changed_timestamp(lastChangedTimestamp.toString());

        RelatedItemTable relatedItemTable = DTOToTable.mapRelatedItemDTOToRelatedItemTable(relatedItemDTO);

        final int[] result = {0};
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                result[0] += relatedItemDao.update(relatedItemTable);
                result[0] += cardDao.update(cardTable);
                result[0] += projectDao.update(projectTable);


                return 1;
            }
        });

        teardownDbConnection();
        if (result[0] > 0) {
            observableRelatedItem.titleProperty().setValue(relatedItemTable.getRelated_item_title());
            observableRelatedItem.typeProperty().setValue(relatedItemTable.getRelated_item_type());
            observableRelatedItem.pathProperty().setValue(relatedItemTable.getRelated_item_path());
        }
    }

    public void deleteCardRelatedItem(ObservableRelatedItem observableRelatedItem) throws SQLException, IOException {
        setupDbConnection();
        relatedItemDao = DaoManager.createDao(connectionSource, RelatedItemTable.class);
        projectDao = DaoManager.createDao(connectionSource, ProjectTable.class);
        columnDao = DaoManager.createDao(connectionSource, ColumnTable.class);
        cardDao = DaoManager.createDao(connectionSource, CardTable.class);

        ZonedDateTime lastChangedTimestamp =ZonedDateTime.now();
        CardTable cardTable = cardDao.queryForId(observableRelatedItem.getParentItemUUID());
        cardTable.setLast_changed_timestamp(lastChangedTimestamp.toString());
        ColumnTable columnTable = columnDao.queryForId(cardTable.getParent_column_uuid());
        ProjectTable projectTable = projectDao.queryForId(columnTable.getParent_project_uuid());
        projectTable.setLast_changed_timestamp(lastChangedTimestamp.toString());

        RelatedItemTable relatedItemTable = new RelatedItemTable();
        relatedItemTable.setRelated_item_uuid(observableRelatedItem.getRelatedItemUUID());
        relatedItemTable.setParent_item_uuid(observableRelatedItem.getParentItemUUID());
        relatedItemTable.setRelated_item_title(observableRelatedItem.titleProperty().getValue());
        relatedItemTable.setRelated_item_type(observableRelatedItem.typeProperty().getValue());
        relatedItemTable.setRelated_item_path(observableRelatedItem.pathProperty().getValue());

        final int[] result = {0};
        TransactionManager.callInTransaction(connectionSource, new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                result[0] += relatedItemDao.delete(relatedItemTable);
                result[0] += cardDao.update(cardTable);
                result[0] += projectDao.update(projectTable);
                return 1;
            }
        });
        teardownDbConnection();
        if (result[0] > 0) {
            for (ObservableWorkspaceProject observableWorkspaceProject : workspaceProjectsList) {
                if (observableWorkspaceProject.getProjectUUID().equals(projectTable.getProject_uuid())) {
                    for (ObservableColumn observableColumn : observableWorkspaceProject.getColumns()) {
                        if (observableColumn.getColumnUUID().equals(columnTable.getColumn_uuid())) {
                            for (ObservableCard observableCard : observableColumn.getCards()) {
                                if (observableCard.getCardUUID().equals(cardTable.getCard_uuid())) {
                                    for(Iterator<ObservableRelatedItem> observableRelatedItemIterator = observableCard.getResourceItems().listIterator(); observableRelatedItemIterator.hasNext();) {
                                        UUID tempUUID = observableRelatedItemIterator.next().getRelatedItemUUID();
                                        if(tempUUID.equals(relatedItemTable.getRelated_item_uuid())) {
                                            observableRelatedItemIterator.remove();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public List<RelatedItemTypeTable> getRelatedItemTypeTableAsList() throws SQLException, IOException {
        setupDbConnection();
        relatedItemTypeDao = DaoManager.createDao(connectionSource, RelatedItemTypeTable.class);
        relatedItemTypeDao.setObjectCache(true);
        List<RelatedItemTypeTable> relatedItemTypeList = relatedItemTypeDao.queryForAll();
        teardownDbConnection();
        return relatedItemTypeList;
    }

}
