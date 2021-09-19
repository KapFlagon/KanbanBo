package domain.viewmodels.resourceitem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import persistence.tables.resourceitems.ResourceItemTypeTable;
import domain.viewmodels.AbstractViewModel;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceItemTypeViewModel extends AbstractViewModel<ResourceItemTypeTable> {


    // Variables
    private SimpleIntegerProperty resourceTypeId;
    private SimpleStringProperty resourceTypePropertiesKey;
    private SimpleStringProperty resourceTypeText;


    // Constructors
    public ResourceItemTypeViewModel(ResourceItemTypeTable domainObject) {
        super(domainObject);
    }


    // Getters and Setters


    // Initialisation methods

    @Override
    protected void initAllProperties() {
        resourceTypeId = new SimpleIntegerProperty(domainObject.getResource_item_type_id());
        resourceTypePropertiesKey = new SimpleStringProperty(domainObject.getResource_item_type_text_key());
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        resourceTypeText = new SimpleStringProperty(resourceBundle.getString(resourceTypePropertiesKey.getValue()));
    }

    @Override
    protected void initPropertyListeners() {
        // Not required here...
    }



    // Other methods
    @Override
    protected void pushPropertyDataIntoDomainObject() {
        // Not required here...
    }


}
