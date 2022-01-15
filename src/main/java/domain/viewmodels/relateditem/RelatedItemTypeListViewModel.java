package domain.viewmodels.relateditem;

import domain.viewmodels.project.ProjectStatusViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.tables.relateditems.RelatedItemTypeTable;

import java.util.List;

public class RelatedItemTypeListViewModel{


    // Variables
    private ObservableList<RelatedItemTypeViewModel> relatedItemTypesViewModels;
    private RelatedItemTypeViewModel selectedRelatedItem;


    // Constructors
    public RelatedItemTypeListViewModel(ObservableList<RelatedItemTypeViewModel> relatedItemTypesViewModels) {
        this.relatedItemTypesViewModels = relatedItemTypesViewModels;
        selectedRelatedItem = relatedItemTypesViewModels.get(0);
    }

    public RelatedItemTypeListViewModel(ObservableList<RelatedItemTypeViewModel> relatedItemTypesViewModels, RelatedItemTypeViewModel selectedRelatedItem) {
        this.relatedItemTypesViewModels = relatedItemTypesViewModels;
        this.selectedRelatedItem = selectedRelatedItem;
    }

    public RelatedItemTypeListViewModel(List<RelatedItemTypeTable> relatedItemTypeTables) {
        this.relatedItemTypesViewModels = FXCollections.observableArrayList();
        for(RelatedItemTypeTable relatedItemTypeTable : relatedItemTypeTables) {
            relatedItemTypesViewModels.add(new RelatedItemTypeViewModel(relatedItemTypeTable));
        }
        this.selectedRelatedItem = relatedItemTypesViewModels.get(0);
    }


    // Getters and Setters
    public ObservableList<RelatedItemTypeViewModel> getRelatedItemTypesViewModels() {
        return relatedItemTypesViewModels;
    }

    public RelatedItemTypeViewModel getSelectedRelatedItem() {
        return selectedRelatedItem;
    }


    // Initialisation methods




    // Other methods



}
