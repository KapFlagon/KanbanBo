package model.repositories.relateditem;

import model.domainobjects.relateditems.RelatedItemType;
import model.repositories.backends.ormlite.ORMLiteDomainObjectRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ORMLiteRelatedItemTypeRepository extends ORMLiteDomainObjectRepository<RelatedItemType, Integer> {


    // Variables


    // Constructors

    public ORMLiteRelatedItemTypeRepository() throws SQLException, IOException {
        super();
        readAllFromDb();
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    @Override
    public RelatedItemType getByID(Integer id) throws SQLException, IOException {
        for (RelatedItemType relatedItemType: repositoryList) {
            if (id.equals(relatedItemType.getRelated_item_type_id())) {
                return relatedItemType;
            }
        }
        return null;
    }
}
