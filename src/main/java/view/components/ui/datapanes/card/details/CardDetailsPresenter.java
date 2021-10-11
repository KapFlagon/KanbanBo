package view.components.ui.datapanes.card.details;

import domain.entities.card.ObservableCard;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CardDetailsPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Accordion rootItem;
    @FXML
    private VBox contentVBox;
    @FXML
    private TextField cardTitleTextField;
    @FXML
    private Label titleValidationLbl;
    @FXML
    private TextArea cardDescriptionTextArea;
    @FXML
    private DatePicker dueOnDatePicker;
    @FXML
    private Label dueOnDateValidationLbl;
    @FXML
    private TextField createdOnTextField;
    @FXML
    private TextField lastChangedOnTextField;


    // Other variables
    private ObservableCard cardViewModel;
    private SimpleBooleanProperty dataValidationErrors;

    // Constructors

    // Getters & Setters
    public void setCardViewModel(ObservableCard cardViewModel) {
        this.cardViewModel = cardViewModel;
        lateInitialization();
    }

    public String getCardTitleText() {
        return cardTitleTextField.getText();
    }

    public String getCardDescriptionText() {
        return cardDescriptionTextArea.getText();
    }

    public LocalDate getDueOnDate() {
        return dueOnDatePicker.getValue();
    }

    public SimpleBooleanProperty dataValidationErrorsProperty() {
        return dataValidationErrors;
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //dueOnDatePicker.minWidthProperty().bind(contentVBox.widthProperty());
        dataValidationErrors = new SimpleBooleanProperty(false);
        establishTitleTextFieldValidation();
        establishDueOnDatePickerValidation();
    }

    private void lateInitialization() {
        cardTitleTextField.setText(cardViewModel.cardTitleProperty().getValue());
        cardDescriptionTextArea.setText(cardViewModel.cardDescriptionProperty().getValue());
        // TODO update card viewmodel to include this data.
        //dueOnDatePicker.setValue(LocalDate.parse(cardViewModel.dueDateProperty().getValue()));
        //createdOnTextField.setText(cardViewModel.creationDateProperty.getValue());
        //lastChangedOnTextField.setText(cardViewModel.lastChangedDateProperty.getValue());
    }

    // UI event methods

    // Other methods
    private void establishTitleTextFieldValidation() {
        cardTitleTextField.setTextFormatter(new TextFormatter<Object>(change -> {
            change = change.getControlNewText().matches(".{0,50}") ? change : null;
            if (change != null && change.getControlNewText().length() < 1) {
                dataValidationErrors.set(true);
                titleValidationLbl.setVisible(true);
            } else {
                dataValidationErrors.set(false);
                titleValidationLbl.setVisible(false);
            }
            return change;
        }));
    }

    private void establishDueOnDatePickerValidation() {
        // TODO Implement this
    }

}