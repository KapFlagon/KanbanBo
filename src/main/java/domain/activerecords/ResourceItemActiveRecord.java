package domain.activerecords;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import persistence.tables.resourceitems.ResourceItemTable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;


public class ResourceItemActiveRecord extends AbstractActiveRecord implements FindableByUUID{


    // Variables
    //private DomainObjectWithUUID parent;
    private UUID parentItemUUID;
    private ResourceItemTable resourceItemTable;

    private SimpleStringProperty relatedItemTitle;
    private SimpleIntegerProperty relatedItemType;
    private SimpleStringProperty relatedItemPath;
    private SimpleStringProperty relatedItemDescription;


    // Constructors
    protected ResourceItemActiveRecord() {
        super(ResourceItemTable.class);
    }

    public ResourceItemActiveRecord(UUID parentItemUUID, ResourceItemTable resourceItemTable) {
        super(ResourceItemTable.class);
        this.parentItemUUID = parentItemUUID;
        this.resourceItemTable = resourceItemTable;
        initAllProperties();
        setAllListeners();
    }

    // Getters and Setters
    public UUID getParentItemUUID() {
        return parentItemUUID;
    }
    public void setParentItemUUID(UUID parentItemUUID) {
        this.parentItemUUID = parentItemUUID;
        this.resourceItemTable.setParent_item_uuid(parentItemUUID);
        //createOrUpdateActiveRowInDb();
    }

    public ResourceItemTable getRelatedItem() {
        return resourceItemTable;
    }
    public void setRelatedItem(ResourceItemTable resourceItemTable) {
        this.resourceItemTable = resourceItemTable;
        initAllProperties();
        setAllListeners();
    }

    public String getRelatedItemTitle() {
        return relatedItemTitle.get();
    }
    public SimpleStringProperty relatedItemTitleProperty() {
        return relatedItemTitle;
    }
    public void setRelatedItemTitle(String relatedItemTitle) {
        this.relatedItemTitle.set(relatedItemTitle);
    }

    public int getRelatedItemType() {
        return relatedItemType.get();
    }
    public SimpleIntegerProperty relatedItemTypeProperty() {
        return relatedItemType;
    }
    public void setRelatedItemType(int relatedItemType) {
        this.relatedItemType.set(relatedItemType);
    }

    public String getRelatedItemPath() {
        return relatedItemPath.get();
    }
    public SimpleStringProperty relatedItemPathProperty() {
        return relatedItemPath;
    }
    public void setRelatedItemPath(String relatedItemPath) {
        this.relatedItemPath.set(relatedItemPath);
    }

    public String getRelatedItemDescription() {
        return relatedItemDescription.get();
    }
    public SimpleStringProperty relatedItemDescriptionProperty() {
        return relatedItemDescription;
    }
    public void setRelatedItemDescription(String relatedItemDescription) {
        this.relatedItemDescription.set(relatedItemDescription);
    }

    // Initialisation methods
    @Override
    protected void initAllProperties() {
        relatedItemTitle = new SimpleStringProperty(resourceItemTable.getResource_item_title());
        relatedItemType = new SimpleIntegerProperty(resourceItemTable.getResource_item_type());
        relatedItemPath = new SimpleStringProperty(resourceItemTable.getResource_item_path());
        relatedItemDescription = new SimpleStringProperty(resourceItemTable.getResource_item_description());
    }


    // Other methods
    @Override
    protected void setAllListeners() {
        setTitleListener();
        setTypeListener();
        setPathListener();
        setDescriptionListener();
    }

    @Override
    public void createOrUpdateActiveRowInDb() throws SQLException, IOException {
        this.setupDbConnection();
        dao.createOrUpdate(resourceItemTable);
        this.teardownDbConnection();
    }

    private void setTitleListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                resourceItemTable.setResource_item_title(newValue);
                try {
                    createOrUpdateActiveRowInDb();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //parentProjectActiveRecord.updateLastChangedTimestamp();
            }
        };
        relatedItemTitle.addListener(changeListener);
    }

    private void setTypeListener() {
        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resourceItemTable.setResource_item_type((Integer) newValue);
                try {
                    createOrUpdateActiveRowInDb();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //parentProjectActiveRecord.updateLastChangedTimestamp();
            }
        };
        relatedItemType.addListener(changeListener);
    }

    private void setPathListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                resourceItemTable.setResource_item_path(newValue);
                try {
                    createOrUpdateActiveRowInDb();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //parentProjectActiveRecord.updateLastChangedTimestamp();
            }
        };
        relatedItemPath.addListener(changeListener);
    }

    private void setDescriptionListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                resourceItemTable.setResource_item_description(newValue);
                try {
                    createOrUpdateActiveRowInDb();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //parentProjectActiveRecord.updateLastChangedTimestamp();
            }
        };
        relatedItemDescription.addListener(changeListener);
    }

    @Override
    public UUID getUUID() {
        return resourceItemTable.getID();
    }
}
