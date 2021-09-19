package domain.viewmodels.card;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import persistence.tables.card.CardTable;
import domain.viewmodels.resourceitem.ObservableResourceItemViewModel;

public class CardViewModel extends AbstractCardBaseViewModel<CardTable>{


    // Variables
    private SimpleIntegerProperty cardPosition;
    private ObservableList<ObservableResourceItemViewModel> resourceItems;


    // Constructors
    public CardViewModel(CardTable domainObject){
        super(domainObject);
    }


    // Getters and Setters


    // Initialisation methods

    @Override
    protected void initAllProperties() {
        super.initAllProperties();
        cardPosition = new SimpleIntegerProperty(domainObject.getCard_position());
    }

    @Override
    protected void initPropertyListeners() {
        super.initPropertyListeners();
        cardPosition.addListener((observable, oldValue, newValue) -> {
            dataChangePending.set(true);
        });
        resourceItems.addListener(new ListChangeListener<ObservableResourceItemViewModel>() {
            @Override
            public void onChanged(Change<? extends ObservableResourceItemViewModel> c) {
                dataChangePending.set(true);
            }
        });
    }


    // Other methods
    @Override
    protected void pushPropertyDataIntoDomainObject() {
        super.pushPropertyDataIntoDomainObject();
        this.domainObject.setCard_position(cardPosition.getValue());
    }

}
