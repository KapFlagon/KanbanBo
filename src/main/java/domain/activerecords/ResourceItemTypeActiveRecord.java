package domain.activerecords;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import persistence.tables.resourceitems.ResourceItemTypeTable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceItemTypeActiveRecord extends AbstractActiveRecord{


    // Variables
    private ResourceItemTypeTable resourceItemTypeTable;

    private SimpleIntegerProperty id;
    private SimpleStringProperty propertiesKey;
    private SimpleStringProperty typeText;


    // Constructors
    public ResourceItemTypeActiveRecord() {
        super(ResourceItemTypeTable.class);
    }

    public ResourceItemTypeActiveRecord(ResourceItemTypeTable resourceItemTypeTable) {
        super(ResourceItemTypeTable.class);
        this.resourceItemTypeTable = resourceItemTypeTable;
        initAllProperties();
        setAllListeners();
    }

    // Getters and Setters
    public int getId() {
        return id.get();
    }
    public SimpleIntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }

    public String getTypeText() {
        return typeText.get();
    }
    public SimpleStringProperty typeTextProperty() {
        return typeText;
    }
    public void setTypeText(String typeText) {
        this.typeText.set(typeText);
    }



    // Initialisation methods
    @Override
    protected void initAllProperties() {
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        id = new SimpleIntegerProperty(resourceItemTypeTable.getResource_item_type_id());
        propertiesKey = new SimpleStringProperty(resourceItemTypeTable.getResource_item_type_text_key());
        typeText = new SimpleStringProperty(resourceBundle.getString(propertiesKey.getValue()));
    }


    // Other methods
    @Override
    protected void setAllListeners() {

    }

    @Override
    public void createOrUpdateActiveRowInDb() throws SQLException, IOException {
        this.setupDbConnection();
        dao.createOrUpdate(resourceItemTypeTable);
        this.teardownDbConnection();
    }

    @Override
    public String toString() {
        return typeText.getValue();
    }
}
