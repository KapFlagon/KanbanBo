package model.repositories.template.card;

import com.j256.ormlite.stmt.PreparedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.domainobjects.card.TemplateCard;
import model.domainobjects.column.TemplateColumn;
import model.repositories.backends.ormlite.ORMLiteDomainObjectChildRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ORMLiteTemplateCardRepository extends ORMLiteDomainObjectChildRepository<TemplateCard, UUID, TemplateColumn, UUID> {


    // Variables


    // Constructors
    public ORMLiteTemplateCardRepository() {
        super();
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods

    @Override
    public ObservableList<TemplateCard> getAll() throws SQLException, IOException {
        readAllFromDb();
        return repositoryList;
    }

    @Override
    public TemplateCard getByID(UUID uuid) throws SQLException, IOException {
        if (repositoryList.size() == 0){
            readAllFromDb();
        }
        for (TemplateCard templateCard: repositoryList) {
            if (templateCard.getCard_uuid().equals(uuid)) {
                return templateCard;
            }
        }
        return null;
    }

    @Override
    public ObservableList<TemplateCard> getAllForParent(TemplateColumn templateColumn) throws SQLException, IOException {
        return getAllForParentID(templateColumn.getColumn_uuid());
    }

    @Override
    public ObservableList<TemplateCard> getAllForParentID(UUID uuid) throws SQLException, IOException {
        ObservableList<TemplateCard> itemsRelatedToParent;
        setupDbConnection();
        queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq(TemplateCard.FOREIGN_KEY_NAME, uuid);
        PreparedQuery<TemplateCard> preparedQuery = queryBuilder.prepare();
        List<TemplateCard> tempList = dao.query(preparedQuery);
        itemsRelatedToParent = FXCollections.observableArrayList(tempList);
        teardownDbConnection();
        return itemsRelatedToParent;
    }
}
