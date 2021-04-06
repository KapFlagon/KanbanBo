package model.activerecords;

import javafx.beans.property.SimpleStringProperty;
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


    // Variables



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


    // Initialisation methods
    @Override
    protected void initAllProperties() {

    }


    // Other methods


    @Override
    protected void setAllListeners() {

    }

    @Override
    public void createOrUpdateActiveRowInDb() throws SQLException, IOException {

    }
}
