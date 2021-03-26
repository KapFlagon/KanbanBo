package model.repositories;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javafx.collections.ObservableList;

public interface IRepository {

    public ObservableList getAllItemsAsList() throws SQLException, IOException;
    public <T> void addItem(T t);
    public <T> void deleteItem(T t);
    public <T> void updateItem(T t);
    public <T> T getItem(UUID uuid);
    public <T> boolean itemExists(T t);
    public <T> boolean itemExists(UUID uuid);
}
