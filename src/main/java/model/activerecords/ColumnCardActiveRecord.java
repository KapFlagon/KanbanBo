package model.activerecords;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.domainobjects.card.AbstractColumnCardModel;
import model.domainobjects.column.AbstractProjectColumnModel;

import java.io.IOException;
import java.sql.SQLException;

public class ColumnCardActiveRecord <T extends AbstractColumnCardModel> extends AbstractActiveRecord{

    // Variables for model objects and DAOs
    protected ProjectColumnActiveRecord parentColumnActiveRecord;
    protected T columnCardModel;


    // Variables to act as property containers for the model data
    protected SimpleStringProperty columnTitle;
    protected SimpleStringProperty columnDescription;


    // Constructors
    public ColumnCardActiveRecord(Class<T> columnCardModelClassCard) {
        super(columnCardModelClassCard);
    }

    public ColumnCardActiveRecord(Class<T> columnCardModelClassCard, T columnCardModel, ProjectColumnActiveRecord parentColumnActiveRecord) {
        super(columnCardModelClassCard);
        this.columnCardModel = columnCardModel;
        this.parentColumnActiveRecord = parentColumnActiveRecord;
        initAllProperties();
        setAllListeners();
    }


    // Getters and Setters
    public String getColumnTitle() {
        return columnTitle.get();
    }
    public SimpleStringProperty columnTitleProperty() {
        return columnTitle;
    }
    public void setColumnTitle(String columnTitle) {
        this.columnTitle.set(columnTitle);
    }

    public String getColumnDescription() {
        return columnDescription.get();
    }
    public SimpleStringProperty columnDescriptionProperty() {
        return columnDescription;
    }
    public void setColumnDescription(String columnDescription) {
        this.columnDescription.set(columnDescription);
    }

    
    // Initialisation methods
    @Override
    protected void initAllProperties() {
        this.columnTitle = new SimpleStringProperty(columnCardModel.getCard_title());
        this.columnDescription = new SimpleStringProperty(columnCardModel.getCard_description_text());
    }


    // Other methods
    @Override
    protected void setAllListeners() {
        setCardTitleListener();
        setCardDescriptionListener();
    }

    private void setCardTitleListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                columnCardModel.setCard_title(newValue);
                try {
                    createOrUpdateActiveRowInDb();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                parentColumnActiveRecord.getParentProjectActiveRecord().updateLastChangedTimestamp();
            }
        };
        columnTitle.addListener(changeListener);
    }

    private void setCardDescriptionListener() {
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                columnCardModel.setCard_description_text(newValue);
                try {
                    createOrUpdateActiveRowInDb();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                parentColumnActiveRecord.getParentProjectActiveRecord().updateLastChangedTimestamp();
            }
        };
        columnDescription.addListener(changeListener);
    }

    @Override
    public void createOrUpdateActiveRowInDb() throws SQLException, IOException {
        this.setupDbConnection();
        dao.createOrUpdate(columnCardModel);
        this.teardownDbConnection();
    }
}
