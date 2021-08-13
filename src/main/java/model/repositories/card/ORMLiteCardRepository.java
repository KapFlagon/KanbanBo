package model.repositories.card;

import com.j256.ormlite.stmt.PreparedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.domainobjects.card.Card;
import model.domainobjects.column.Column;
import model.repositories.backends.ormlite.ORMLiteDomainObjectChildRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ORMLiteCardRepository extends ORMLiteDomainObjectChildRepository<Card, UUID, Column, UUID> {


    // Variables


    // Constructors
    public ORMLiteCardRepository() throws SQLException, IOException {
        super();
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    @Override
    public ObservableList<Card> getAll() throws SQLException, IOException {
        readAllFromDb();
        return repositoryList;
    }

    @Override
    public Card getByID(UUID uuid) throws SQLException, IOException {
        if (repositoryList.size() == 0){
            readAllFromDb();
        }
        for (Card card: repositoryList) {
            if (card.getCard_uuid().equals(uuid)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public ObservableList<Card> getAllForParent(Column column) throws SQLException, IOException {
        return getAllForParentID(column.getColumn_uuid());
    }

    @Override
    public ObservableList<Card> getAllForParentID(UUID uuid) throws SQLException, IOException {
        ObservableList<Card> itemsRelatedToParent;
        setupDbConnection();
        queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq(Card.FOREIGN_KEY_NAME, uuid);
        PreparedQuery<Card> preparedQuery = queryBuilder.prepare();
        List<Card> tempList = dao.query(preparedQuery);
        itemsRelatedToParent = FXCollections.observableArrayList(tempList);
        teardownDbConnection();
        return itemsRelatedToParent;
    }
}
