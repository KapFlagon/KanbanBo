package domain.viewmodels.resourceitem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import persistence.tables.resourceitems.ResourceItemTable;
import persistence.tables.resourceitems.ResourceItemTypeTable;
import domain.viewmodels.AbstractViewModel;

import java.util.Locale;
import java.util.ResourceBundle;

public class ObservableResourceItemViewModel extends AbstractViewModel<ResourceItemTable> {

    // Variables
    private ResourceItemTypeTable resourceItemTypeTable;
    private SimpleStringProperty resourceItemTitle;
    private SimpleIntegerProperty resourceItemTypeID;
    private SimpleStringProperty resourceItemTypeText;
    private SimpleStringProperty resourceItemPath;
    private SimpleStringProperty resourceItemDescription;


    // Constructors
    public ObservableResourceItemViewModel(ResourceItemTable domainObject, ResourceItemTypeTable resourceItemTypeTable) {
        super(domainObject);
        this.resourceItemTypeTable = resourceItemTypeTable;
    }


    // Getters and Setters


    // Initialisation methods
    @Override
    protected void initAllProperties() {
        resourceItemTitle = new SimpleStringProperty(domainObject.getResource_item_title());
        resourceItemTypeID = new SimpleIntegerProperty(domainObject.getResource_item_type());
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        resourceItemTypeText = new SimpleStringProperty(resourceBundle.getString(resourceItemTypeTable.getResource_item_type_text_key()));
        resourceItemPath = new SimpleStringProperty(domainObject.getResource_item_path());
        resourceItemDescription = new SimpleStringProperty(domainObject.getResource_item_description());
    }

    @Override
    protected void initPropertyListeners() {
        resourceItemTitle.addListener((observable, oldValue, newValue) -> {
            dataChangePending.set(true);
        });
        resourceItemTypeID.addListener((observable, oldValue, newValue) -> {
            dataChangePending.set(true);
        });
        resourceItemPath.addListener((observable, oldValue, newValue) -> {
            dataChangePending.set(true);
        });
        resourceItemDescription.addListener((observable, oldValue, newValue) -> {
            dataChangePending.set(true);
        });
    }


    // Other methods
    @Override
    protected void pushPropertyDataIntoDomainObject() {

    }


}
