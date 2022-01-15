package domain.viewmodels.relateditem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import persistence.tables.relateditems.RelatedItemTypeTable;

import java.util.Locale;
import java.util.ResourceBundle;

public class RelatedItemTypeViewModel {


    // Variables
    private RelatedItemTypeTable relatedItemTypeTable;
    private SimpleIntegerProperty relatedItemTypeId;
    private SimpleStringProperty relatedItemTypeText;


    // Constructors
    public RelatedItemTypeViewModel(RelatedItemTypeTable relatedItemTypeTable) {
        this.relatedItemTypeTable = relatedItemTypeTable;
        initializeProperties();
    }


    // Getters and Setters
    public int getRelatedItemTypeId() {
        return relatedItemTypeId.get();
    }
    public SimpleIntegerProperty relatedItemTypeIdProperty() {
        return relatedItemTypeId;
    }

    public String getRelatedItemTypeText() {
        return relatedItemTypeText.get();
    }
    public SimpleStringProperty relatedItemTypeTextProperty() {
        return relatedItemTypeText;
    }


    // Initialisation methods
    protected void initializeProperties() {
        relatedItemTypeId = new SimpleIntegerProperty(relatedItemTypeTable.getRelated_item_type_id());
        String localizedText = getLocalisedText(relatedItemTypeTable.getRelated_item_type_text_key());
        relatedItemTypeText = new SimpleStringProperty(localizedText);
    }



    // Other methods
    private String getLocalisedText(String resourceBundleKey) {
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        return resourceBundle.getString(resourceBundleKey);
    }

    @Override
    public String toString() {
        return relatedItemTypeText.getValue();
    }
}
