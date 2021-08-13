package model.repositories.backends.ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.repositories.Repository;
import utils.database.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;


public abstract class ORMLiteDomainObjectRepository<T, U> implements Repository<T, U> {

    protected JdbcConnectionSource connectionSource;
    protected Class<T> domainObjectClass;
    protected Class<U> domainObjectIdentifierClass;
    protected Dao<T, U> dao;
    protected ObservableList<T> repositoryList;

    // TODO KEEP THIS

    // Variables


    // Constructors

    public ORMLiteDomainObjectRepository() {
        initializeObservableList();
    }

    //public DomainObjectRepository(Class<T> domainObjectClass, Class<U> domainObjectIdentifierClass) {
        //this.domainObjectClass = domainObjectClass;
        //this.domainObjectIdentifierClass = domainObjectIdentifierClass;
    //}


    // Getters and Setters


    // Initialisation methods
    protected void initializeObservableList() {
        repositoryList = FXCollections.observableArrayList();
    }


    // Other methods
    protected void initConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }

    protected void initDao() throws SQLException {
        dao = DaoManager.createDao(connectionSource, domainObjectClass);
    }


    // Other methods
    protected void setupDbConnection() throws SQLException {
        initConnectionSource();
        initDao();
    }

    protected void teardownDbConnection() throws IOException {
        connectionSource.close();
    }

    @Override
    public ObservableList<T> getAll() throws SQLException, IOException {
        readAllFromDb();
        return repositoryList;
    }


    protected void readAllFromDb() throws SQLException, IOException {
        setupDbConnection();
        if (dao.countOf() != 0) {
            for (T t : dao) {
                repositoryList.add(t);
            }
        }
        teardownDbConnection();
    }

    @Override
    public void add(T t) throws SQLException, IOException {
        setupDbConnection();
        int rowsUpdated = dao.create(t);
        teardownDbConnection();
        if (rowsUpdated > 0) {
            repositoryList.add(t);
        }
    }

    @Override
    public void remove(T t) throws SQLException, IOException {
        setupDbConnection();
        int rowsUpdated =dao.delete(t);
        teardownDbConnection();
        if (rowsUpdated > 0) {
            repositoryList.remove(t);
        }
    }

    @Override
    public void removeByID(U u) throws SQLException, IOException {
        setupDbConnection();
        T t = dao.queryForId(u);
        int rowsUpdated =dao.deleteById(u);
        teardownDbConnection();
        if (rowsUpdated > 0) {
            repositoryList.remove(t);
        }
    }


}
