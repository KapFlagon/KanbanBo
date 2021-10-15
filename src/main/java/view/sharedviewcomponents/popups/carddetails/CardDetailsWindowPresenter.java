package view.sharedviewcomponents.popups.carddetails;

import persistence.dto.card.CardDTO;
import domain.entities.card.ObservableCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;

public class CardDetailsWindowPresenter implements Initializable {


    // JavaFX injected node variables
    @FXML
    private TextField titleTextField;
    @FXML
    private Label titleErrorLbl;
    @FXML
    private TextArea descriptionText;

    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private String noTitleError = "A title must be provided";
    private boolean validTitle;

    private UUID parentColumnUUID;
    private ObservableCard cardViewModel;

    // Constructors

    // Getters & Setters


    public void setParentColumnUUID(UUID parentColumnUUID) {
        this.parentColumnUUID = parentColumnUUID;
    }

    public void setCardViewModel(ObservableCard cardViewModel) {
        this.cardViewModel = cardViewModel;
        this.titleTextField.textProperty().set(cardViewModel.cardTitleProperty().getValue());
        this.descriptionText.textProperty().set(cardViewModel.cardDescriptionProperty().getValue());
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validTitle = true;
        titleErrorLbl.setText(noTitleError);
        titleErrorLbl.setVisible(false);
        titleErrorLbl.setDisable(true);
        establishTitleTextFieldValidation();
    }


    // UI event methods
    @FXML private void saveDetailsChange() throws IOException, SQLException {
        if(validTitle) {
            if(cardViewModel == null) {
                CardDTO cardDTO = new CardDTO();
                cardDTO.setParentColumnUUID(parentColumnUUID);
                cardDTO.setTitle(titleTextField.getText());
                cardDTO.setDescription(descriptionText.getText());
                kanbanBoDataService.createCard(cardDTO);
            } else {
                cardViewModel.setCardTitle(titleTextField.getText());
                cardViewModel.setCardDescription(descriptionText.getText());
            }
            StageUtils.hideSubStage();
        } else {
            titleErrorLbl.setVisible(true);
            titleErrorLbl.setDisable(false);
        }
    }


    @FXML private void cancelDetailsChange() {
        // Close the window, do nothing.
        StageUtils.hideSubStage();
    }

    // Other methods

    private void establishTitleTextFieldValidation() {
        titleTextField.setTextFormatter(new TextFormatter<Object> (change -> {
            change = change.getControlNewText().matches(".{0,50}") ? change : null;
            if (change != null && change.getControlNewText().length() < 1) {
                validTitle = false;
            } else {
                validTitle = true;
            }
            return change;
        }));
    }

}