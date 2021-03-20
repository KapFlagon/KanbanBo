package model.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DatabaseUtils;

import java.sql.SQLException;

public abstract class AbstractProjectRepository<T> {


    // Variables
    private JdbcConnectionSource connectionSource;
    private Dao dao;
    private ObservableList projectList;

    // Constructors
    public AbstractProjectRepository() {

    }


    // Getters and Setters
    public ObservableList getProjectList() {
        return projectList;
    }
    public void setProjectList(ObservableList projectList) {
        this.projectList = projectList;
    }


    // Initialisation methods
    private void initTempConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }
    protected abstract Dao initProjectDao();
    private void initProjectList() {
        projectList = FXCollections.observableArrayList();
    }

    // Other methods


}
