package persistence.repositories;

import java.io.IOException;
import java.sql.SQLException;

public interface ChangeablePosition<T, U> {


    // Variables


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public void add(int newPosition, T t) throws SQLException, IOException;
    public void changePosition(int newPosition, T t) throws SQLException, IOException;
    // TODO implement this in the repository classes.

}
