package model.activerecords;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.domainobjects.card.AbstractColumnCardModel;
import model.domainobjects.card.ActiveColumnCardModel;
import model.domainobjects.column.ActiveProjectColumnModel;

import java.io.IOException;
import java.sql.SQLException;

public class ColumnCardActiveRecord <T extends ActiveColumnCardModel> extends AbstractActiveRecord{

    // Variables for model objects and DAOs
    protected ProjectColumnActiveRecord parentColumnActiveRecord;
    protected T columnCardModel;


    // Variables to act as property containers for the model data
    protected SimpleStringProperty cardTitle;
    protected SimpleStringProperty cardDescription;
    protected SimpleIntegerProperty cardPosition;


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
    public ProjectColumnActiveRecord getParentColumnActiveRecord() {
        return parentColumnActiveRecord;
    }
    public void setParentColumnActiveRecord(ProjectColumnActiveRecord<ActiveProjectColumnModel> parentColumnActiveRecord) throws IOException, SQLException {
        this.parentColumnActiveRecord = parentColumnActiveRecord;
        this.columnCardModel.setParent_column_uuid(parentColumnActiveRecord.getProjectColumnModel().getColumn_uuid());
        createOrUpdateActiveRowInDb();
    }

    public T getColumnCardModel() {
        return columnCardModel;
    }
    public void setColumnCardModel(T columnCardModel) {
        this.columnCardModel = columnCardModel;
        initAllProperties();
        setAllListeners();
    }

    public String getCardTitle() {
        return cardTitle.get();
    }
    public SimpleStringProperty cardTitleProperty() {
        return cardTitle;
    }
    public void setCardTitle(String cardTitle) {
        this.cardTitle.set(cardTitle);
    }

    public String getCardDescription() {
        return cardDescription.get();
    }
    public SimpleStringProperty cardDescriptionProperty() {
        return cardDescription;
    }
    public void setCardDescription(String cardDescription) {
        this.cardDescription.set(cardDescription);
    }

    public int getCardPosition() {
        return cardPosition.get();
    }
    public SimpleIntegerProperty cardPositionProperty() {
        return cardPosition;
    }
    public void setCardPosition(int cardPosition) {
        this.cardPosition.set(cardPosition);
    }

    // Initialisation methods
    @Override
    protected void initAllProperties() {
        this.cardTitle = new SimpleStringProperty(columnCardModel.getCard_title());
        this.cardDescription = new SimpleStringProperty(columnCardModel.getCard_description_text());
        this.cardPosition = new SimpleIntegerProperty(columnCardModel.getCard_position());
    }


    // Other methods
    @Override
    protected void setAllListeners() {
        setCardTitleListener();
        setCardDescriptionListener();
        //setCardPositionListener();
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
        cardTitle.addListener(changeListener);
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
        cardDescription.addListener(changeListener);
    }

    private void setCardPositionListener() {
        ChangeListener<Integer> changeListener = new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                columnCardModel.setCard_position(newValue);
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
        cardPosition.addListener((InvalidationListener) changeListener);
    }

    @Override
    public void createOrUpdateActiveRowInDb() throws SQLException, IOException {
        this.setupDbConnection();
        dao.createOrUpdate(columnCardModel);
        this.teardownDbConnection();
    }
}
