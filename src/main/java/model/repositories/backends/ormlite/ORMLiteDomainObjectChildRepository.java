package model.repositories.backends.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import javafx.collections.ObservableList;
import model.repositories.ChildRepository;

import java.io.IOException;
import java.sql.SQLException;


public abstract class ORMLiteDomainObjectChildRepository<T, U, V, W> extends ORMLiteDomainObjectRepository<T, U> implements ChildRepository<T, U, V, W> {


    // Variables
    protected QueryBuilder<T, U> queryBuilder;

    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public ObservableList<T> getAll() throws SQLException, IOException {
        readAllFromDb();
        return repositoryList;
    }

}
