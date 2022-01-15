package persistence.repositories.observables;

import com.j256.ormlite.dao.DaoManager;
import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import domain.entities.project.AbstractObservableProjectBase;
import domain.entities.project.ObservableWorkspaceProject;
import domain.entities.relateditem.ObservableRelatedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.dto.RelatedItemDTO;
import persistence.dto.card.CardDTO;
import persistence.dto.column.ColumnDTO;
import persistence.dto.project.ProjectDTO;
import persistence.mappers.TableToDTO;
import persistence.tables.card.CardTable;
import persistence.tables.column.ColumnTable;
import persistence.tables.project.ProjectTable;
import persistence.tables.relateditems.RelatedItemTable;
import persistence.tables.relateditems.RelatedItemTypeTable;

import java.util.List;

public class WorkspaceProjectRepository {


    // Variables
    private final ObservableList<ObservableWorkspaceProject> workspaceProjectsList;


    // Constructors
    public WorkspaceProjectRepository() {
        workspaceProjectsList = FXCollections.observableArrayList();
    }


    // Getters and Setters
    public ObservableList<ObservableWorkspaceProject> getWorkspaceProjectsList() {
        return workspaceProjectsList;
    }


    // Initialisation methods


    // Other methods
    public void createObservableWorkspaceProject(ProjectDTO projectDTO, List<ColumnDTO> projectColumnsDTOList, List<RelatedItemDTO> projectRelatedItemDTOList) {
        ObservableWorkspaceProject.ObservableWorkspaceProjectBuilder projectBuilder = ObservableWorkspaceProject.ObservableWorkspaceProjectBuilder.newInstance()
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .statusID(projectDTO.getStatusId())
                .statusText("")
                .dueOnDate(projectDTO.getDueOnDate());

        ObservableWorkspaceProject workspaceProject;
        ObservableList<ObservableRelatedItem> projectResourceItems = FXCollections.observableArrayList();
        ObservableList<ObservableColumn> observableColumnsList = FXCollections.observableArrayList();

        for(RelatedItemDTO projectIndividualRelatedItem : projectRelatedItemDTOList) {
            //relatedItemTypeTableQueryBuilder.reset();
            //relatedItemTypeTableQueryBuilder.where().eq(RelatedItemTypeTable.TYPE_KEY_NAME, projectResourceTableItem.getRelated_item_type());
            //RelatedItemTypeTable relatedItemTypeTable = relatedItemTypeTableQueryBuilder.queryForFirst();
            //String typeText = resourceBundle.getString(relatedItemTypeTable.getRelated_item_type_text_key());

            /*ObservableRelatedItem observableRelatedItem = new ObservableRelatedItem(projectResourceTableItem, typeText);
            projectResourceItems.add(observableRelatedItem);*/
        }

        /*for(ColumnDTO projectColumnDTO: projectColumnsDTOList) {
            cardTableQueryBuilder.reset();

            ObservableList<ObservableCard> observableCardsList = FXCollections.observableArrayList();
            for (CardTable cardTable : columnCardsTableList) {
                ObservableList<ObservableRelatedItem> cardObservableRelatedItemList = FXCollections.observableArrayList();
                relatedItemTableQueryBuilder.reset();
                relatedItemTableQueryBuilder.where().eq(RelatedItemTable.FOREIGN_KEY_NAME, cardTable.getID());
                List<RelatedItemTable> cardResourceItemsList = relatedItemTableQueryBuilder.query();
                for(RelatedItemTable relatedItemTableItem : cardResourceItemsList) {
                    relatedItemTypeTableQueryBuilder.reset();
                    relatedItemTypeTableQueryBuilder.where().eq(RelatedItemTypeTable.TYPE_KEY_NAME, relatedItemTableItem.getRelated_item_type());
                    RelatedItemTypeTable relatedItemTypeTable = relatedItemTypeTableQueryBuilder.queryForFirst();
                    String typeText = resourceBundle.getString(relatedItemTypeTable.getRelated_item_type_text_key());
                    ObservableRelatedItem cardObservableRelatedItem = new ObservableRelatedItem(relatedItemTableItem, typeText);
                    cardObservableRelatedItemList.add(cardObservableRelatedItem);
                }

                CardDTO cardDTO = TableToDTO.mapCardTableToCardDTO(cardTable);
                ObservableCard observableCard = new ObservableCard(cardDTO);
                observableCard.setResourceItems(cardObservableRelatedItemList);
                observableCard.positionProperty().addListener((observable, oldVal, newVal) -> {
                    // TODO implement function to change position of the Card in its list
                });
                observableCardsList.add(observableCard);
            }
            ColumnDTO columnDTO = TableToDTO.mapColumnTableToColumnDTO(columnTableItem);
            ObservableColumn observableColumn = new ObservableColumn(columnDTO, observableCardsList);
            observableColumn.columnPositionProperty().addListener((observable, oldVal, newVal) -> {
                // TODO implement function to change position of the Column in its list
            });
            observableColumnsList.add(observableColumn);
        }

        observableWorkspaceProject.setColumns(observableColumnsList);
        observableWorkspaceProject.setResourceItems(projectResourceItems);*/
    }


}
