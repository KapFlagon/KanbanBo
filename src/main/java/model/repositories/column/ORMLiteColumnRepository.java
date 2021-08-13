package model.repositories.column;

import com.j256.ormlite.stmt.PreparedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.domainobjects.column.Column;
import model.domainobjects.project.Project;
import model.repositories.backends.ormlite.ORMLiteDomainObjectChildRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ORMLiteColumnRepository extends ORMLiteDomainObjectChildRepository<Column, UUID, Project, UUID> {


    // Variables


    // Constructors
    public ORMLiteColumnRepository() {
        super();
    }

    // Getters and Setters


    // Initialisation methods


    // Other methods

    @Override
    public ObservableList<Column> getAll() throws SQLException, IOException {
        readAllFromDb();
        return repositoryList;
    }

    @Override
    public Column getByID(UUID uuid) throws SQLException, IOException {
        if (repositoryList.size() == 0){
            readAllFromDb();
        }
        for (Column column: repositoryList) {
            if (column.getColumn_uuid().equals(uuid)) {
                return column;
            }
        }
        return null;
    }

    @Override
    public ObservableList<Column> getAllForParent(Project project) throws SQLException, IOException {
        return getAllForParentID(project.getProject_uuid());
    }

    @Override
    public ObservableList<Column> getAllForParentID(UUID uuid) throws SQLException, IOException {
        ObservableList<Column> itemsRelatedToParent;
        setupDbConnection();
        queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq(Column.FOREIGN_KEY_NAME, uuid);
        PreparedQuery<Column> preparedQuery = queryBuilder.prepare();
        List<Column> tempList = dao.query(preparedQuery);
        itemsRelatedToParent = FXCollections.observableArrayList(tempList);
        teardownDbConnection();
        return itemsRelatedToParent;
    }
}
