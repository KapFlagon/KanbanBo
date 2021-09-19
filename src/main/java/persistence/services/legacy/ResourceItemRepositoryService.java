package persistence.services.legacy;

import javafx.collections.ObservableList;
import domain.activerecords.ResourceItemActiveRecord;
import persistence.tables.TableObject;
import persistence.repositories.legacy.ResourceItemRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class ResourceItemRepositoryService {


    // Variables
    private ResourceItemRepository resourceItemRepository;


    // Constructors
    public ResourceItemRepositoryService(TableObject tableObject) throws SQLException, IOException {
        //this(tableObject.getID());
    }

    public ResourceItemRepositoryService(UUID parentUUID) throws SQLException, IOException {
        this.resourceItemRepository = new ResourceItemRepository(parentUUID);
        this.resourceItemRepository.readFromDb();
    }


    // Getters and Setters

    public ResourceItemRepository getRelatedItemRepository() {
        return resourceItemRepository;
    }
    public void setRelatedItemRepository(ResourceItemRepository resourceItemRepository) {
        this.resourceItemRepository = resourceItemRepository;
    }

    public ObservableList<ResourceItemActiveRecord> getRelatedItemsList() {
        return resourceItemRepository.getActiveRecordObservableList();
    }


    // Initialisation methods


    // Other methods


}
