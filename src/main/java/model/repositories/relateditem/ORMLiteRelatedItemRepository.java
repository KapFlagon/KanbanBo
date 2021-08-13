package model.repositories.relateditem;

import com.j256.ormlite.stmt.PreparedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.domainobjects.DomainObjectWithUUID;
import model.domainobjects.relateditems.RelatedItem;
import model.repositories.backends.ormlite.ORMLiteDomainObjectChildRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ORMLiteRelatedItemRepository<V extends DomainObjectWithUUID> extends ORMLiteDomainObjectChildRepository<RelatedItem, UUID, V, UUID> {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods



    // Other methods
    @Override
    public RelatedItem getByID(UUID uuid) throws SQLException, IOException {
        if (repositoryList.size() == 0){
            readAllFromDb();
        }
        for (RelatedItem relatedItem: repositoryList) {
            if (relatedItem.getRelated_item_uuid().equals(uuid)) {
                return relatedItem;
            }
        }
        return null;
    }

    @Override
    public ObservableList<RelatedItem> getAllForParent(V v) throws SQLException, IOException {
        return getAllForParentID(v.get_uuid());
    }

    @Override
    public ObservableList<RelatedItem> getAllForParentID(UUID uuid) throws SQLException, IOException {
        ObservableList<RelatedItem> itemsRelatedToParent;
        setupDbConnection();
        queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq(RelatedItem.FOREIGN_KEY_NAME, uuid);
        PreparedQuery<RelatedItem> preparedQuery = queryBuilder.prepare();
        List<RelatedItem> tempList = dao.query(preparedQuery);
        itemsRelatedToParent = FXCollections.observableArrayList(tempList);
        teardownDbConnection();
        return itemsRelatedToParent;
    }
}
