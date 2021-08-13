package model.repositories;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public interface Repository<T, U> {

    // TODO KEEP THIS


    public void add(T t) throws SQLException, IOException;
    public void remove(T t) throws SQLException, IOException;
    public void removeByID(U u) throws SQLException, IOException;
    public T getByID(U u) throws SQLException, IOException;
    public ObservableList<T> getAll() throws SQLException, IOException;

}
