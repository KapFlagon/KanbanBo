package persistence.repositories;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public interface ChildRepository<T, U, V, W> extends Repository<T, U>, ChangeablePosition<T, U> {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public ObservableList<T> getAllForParent(V v) throws SQLException, IOException;
    public ObservableList<T> getAllForParentID(W w) throws SQLException, IOException;

}
