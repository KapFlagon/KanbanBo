package model.repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.database.DatabaseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public abstract class AbstractModelRepository<T1, T2>{

    // Variables
    protected Class<T1> modelClassType;
    protected Class<T2> activeRecordClassType;
    protected JdbcConnectionSource connectionSource;
    protected Dao<T1, UUID> modelDao;
    protected ObservableList<T2> activeRecordObservableList;


    // Constructors
    public AbstractModelRepository(Class<T1> modelClassType, Class<T2> activeRecordClassType) {
        this.modelClassType = modelClassType;
        this.activeRecordClassType = activeRecordClassType;

    }
    public AbstractModelRepository(Class<T1> modelClassType, Class<T2> activeRecordClassType, ObservableList<T1> modelList) {
        this.modelClassType = modelClassType;
        this.activeRecordClassType = activeRecordClassType;

    }


    // Getters and Setters
    public ObservableList<T2> getActiveRecordObservableList() {
        return activeRecordObservableList;
    }
    public void setActiveRecordObservableList(ObservableList<T2> activeRecordObservableList) {
        this.activeRecordObservableList = activeRecordObservableList;
    }


    // Initialisation methods
    protected void initActiveRecordObservableList() {
        activeRecordObservableList = FXCollections.observableArrayList();
    }
    protected void initTempConnectionSource() throws SQLException {
        connectionSource = DatabaseUtils.getConnectionSource();
    }
    protected void initProjectDao() throws SQLException {
        modelDao = DaoManager.createDao(connectionSource, modelClassType);
    }


    // Other methods
    protected void setupDbAccess() throws SQLException {
        initTempConnectionSource();
        initProjectDao();
    }

    /**
     * Must be called after any Db access to free resources.
     * @throws IOException
     */
    protected void teardownDbAccess() throws IOException {
        connectionSource.close();
    }


    public ObservableList getAllItemsAsList() throws SQLException, IOException {
        setupDbAccess();
        if (!activeRecordObservableList.isEmpty()) {
            activeRecordObservableList.clear();
            activeRecordObservableList.removeAll();
        }
        if (modelDao.countOf() > 0) {
            for (T1 t1 : modelDao) {
                //T2 t2 = new T2(t1);
                //activeRecordObservableList.add();
            }
        }
        teardownDbAccess();
        return activeRecordObservableList;
    }

    /*
    public void test() {
        activeRecordObservableList = FXCollections.observableArrayList(
                new Callback<T1, Observable[]>() {
                    @Override
                    public Observable[] call(T1 param) {
                        // TODO pick up from here
                        return new Observable[]{
                                param.nameProperty(),
                                param.descriptionProperty()
                        };
                    }
                }
        );

        fruit.addListener(new ListChangeListener<Fruit>() {
            @Override
            public void onChanged(Change<? extends Fruit> c) {
                while (c.next()) {
                    if (c.wasPermutated()) {
                        for (int i = c.getFrom(); i < c.getTo(); ++i) {
                            System.out.println("Permuted: " + i + " " + fruit.get(i));
                        }
                    } else if (c.wasUpdated()) {
                        for (int i = c.getFrom(); i < c.getTo(); ++i) {
                            System.out.println("Updated: " + i + " " + fruit.get(i));
                        }
                    } else {
                        for (Fruit removedItem : c.getRemoved()) {
                            System.out.println("Removed: " + removedItem);
                        }
                        for (Fruit addedItem : c.getAddedSubList()) {
                            System.out.println("Added: " + addedItem);
                        }
                    }
                }
            }
        });
    }

     */

}
