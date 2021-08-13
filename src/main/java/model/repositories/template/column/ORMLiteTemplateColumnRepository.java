package model.repositories.template.column;

import com.j256.ormlite.stmt.PreparedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.domainobjects.column.TemplateColumn;
import model.domainobjects.project.TemplateProject;
import model.repositories.backends.ormlite.ORMLiteDomainObjectChildRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ORMLiteTemplateColumnRepository extends ORMLiteDomainObjectChildRepository<TemplateColumn, UUID, TemplateProject, UUID> {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    @Override
    public ObservableList<TemplateColumn> getAll() throws SQLException, IOException {
        readAllFromDb();
        return repositoryList;
    }

    @Override
    public TemplateColumn getByID(UUID uuid) throws SQLException, IOException {
        if (repositoryList.size() == 0){
            readAllFromDb();
        }
        for (TemplateColumn templateColumn: repositoryList) {
            if (templateColumn.getColumn_uuid().equals(uuid)) {
                return templateColumn;
            }
        }
        return null;
    }

    @Override
    public ObservableList<TemplateColumn> getAllForParent(TemplateProject templateProject) throws SQLException, IOException {
        return getAllForParentID(templateProject.getProject_uuid());
    }

    @Override
    public ObservableList<TemplateColumn> getAllForParentID(UUID uuid) throws SQLException, IOException {
        ObservableList<TemplateColumn> itemsRelatedToParent;
        setupDbConnection();
        queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq(TemplateColumn.FOREIGN_KEY_NAME, uuid);
        PreparedQuery<TemplateColumn> preparedQuery = queryBuilder.prepare();
        List<TemplateColumn> tempList = dao.query(preparedQuery);
        itemsRelatedToParent = FXCollections.observableArrayList(tempList);
        teardownDbConnection();
        return itemsRelatedToParent;
    }
}
