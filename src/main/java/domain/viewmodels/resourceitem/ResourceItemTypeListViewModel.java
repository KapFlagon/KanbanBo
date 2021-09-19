package domain.viewmodels.resourceitem;

import javafx.collections.ObservableList;
import persistence.tables.resourceitems.ResourceItemTypeTable;
import domain.viewmodels.AbstractViewModel;

public class ResourceItemTypeListViewModel extends AbstractViewModel<ResourceItemTypeTable> {


    // Variables
    private ObservableList<ResourceItemTypeViewModel> resourceItemTypes;


    // Constructors
    public ResourceItemTypeListViewModel(ResourceItemTypeTable selectedObject, ObservableList<ResourceItemTypeViewModel> resourceItemTypes) {
        super(selectedObject);
        this.resourceItemTypes = resourceItemTypes;
    }

    // Getters and Setters


    // Initialisation methods

    @Override
    protected void initAllProperties() {

    }

    @Override
    protected void initPropertyListeners() {

    }



    // Other methods
    @Override
    protected void pushPropertyDataIntoDomainObject() {

    }


}
