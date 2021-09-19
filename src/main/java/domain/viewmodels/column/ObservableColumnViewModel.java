package domain.viewmodels.column;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistence.tables.column.ColumnTable;
import domain.viewmodels.card.CardViewModel;

public class ObservableColumnViewModel extends AbstractColumnBaseViewModel<ColumnTable> {


    // Variables
    private SimpleBooleanProperty finalColumn;
    private ObservableList<CardViewModel> cards;

    // Constructors

    public ObservableColumnViewModel(ColumnTable column, ObservableList<CardViewModel> cards) {
        super(column);
        this.cards = cards;
    }


    // Getters and Setters


    // Initialisation methods

    @Override
    protected void initAllProperties() {
        super.initAllProperties();
    }

    @Override
    protected void initPropertyListeners() {
        super.initPropertyListeners();
        finalColumn.addListener((observable, oldValue, newValue) -> {
            dataChangePending.set(true);
        });
        cards.addListener(new ListChangeListener<CardViewModel>() {
            @Override
            public void onChanged(Change<? extends CardViewModel> c) {
                dataChangePending.set(true);
            }
        });
    }


    // Other methods


    @Override
    protected void pushPropertyDataIntoDomainObject() {
        super.pushPropertyDataIntoDomainObject();
        domainObject.setFinal_column(finalColumn.getValue());
    }
}
