package view.sharedviewcomponents.popups.movecarddialog;

import domain.entities.card.ObservableCard;
import domain.entities.column.ObservableColumn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import persistence.dto.card.CardDTO;
import persistence.mappers.ObservableObjectToDTO;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MoveCardDialogPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label cardTitleLbl;
    @FXML
    private ChoiceBox<ObservableColumn> columnChoiceBox;
    @FXML
    private ChoiceBox<Integer> cardPositionChoiceBox;


    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableCard cardToMove;

    // Constructors

    // Getters & Setters


    public void setCardToMove(ObservableCard cardToMove) {
        this.cardToMove = cardToMove;
        cardTitleLbl.setText(cardToMove.cardTitleProperty().getValue());
        columnChoiceBox.setItems(kanbanBoDataService.getRelatedColumns(cardToMove.getParentColumnUUID()));
        for(ObservableColumn observableColumn : columnChoiceBox.getItems()) {
            if(observableColumn.getColumnUUID().equals(cardToMove.getParentColumnUUID())) {
                columnChoiceBox.getSelectionModel().select(observableColumn);
            }
        }
    }

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnChoiceBox.setConverter(new StringConverter<ObservableColumn>() {
            @Override
            public String toString(ObservableColumn object) {
                String colPositionAndName = (object.columnPositionProperty().getValue() + 1)  + ": " + object.columnTitleProperty().getValue();
                return colPositionAndName;
            }

            @Override
            public ObservableColumn fromString(String string) {
                return null;
            }
        });

        columnChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateCardPositionChoiceBox();
        });
    }

    // UI event methods
    @FXML
    private void cancelSelection(ActionEvent event) {
        System.out.println("cancel");
        StageUtils.hideSubStage();
        event.consume();
    }

    @FXML
    private void saveSelection(ActionEvent event) {
        CardDTO cardDTO = ObservableObjectToDTO.mapObservableCardToCardDTO(cardToMove);
        cardDTO.setParentColumnUUID(columnChoiceBox.getSelectionModel().getSelectedItem().getColumnUUID());
        cardDTO.setPosition(cardPositionChoiceBox.getSelectionModel().getSelectedItem() - 1);
        try {
            kanbanBoDataService.moveCard(cardDTO, cardToMove);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StageUtils.hideSubStage();
        event.consume();
    }

    // Other methods
    private void updateCardPositionChoiceBox() {
        cardPositionChoiceBox.getItems().clear();
        for(int iterator = 0; iterator < columnChoiceBox.getSelectionModel().getSelectedItem().getCards().size(); iterator++) {
            cardPositionChoiceBox.getItems().add(iterator + 1);
        }
        if(!columnChoiceBox.getSelectionModel().getSelectedItem().getColumnUUID().equals(cardToMove.getParentColumnUUID())){
            cardPositionChoiceBox.getItems().add(columnChoiceBox.getSelectionModel().getSelectedItem().getCards().size() + 1);
        } else {
            // Using Integer.valueOf here to avoid the select method interpreting the value as an index.
            cardPositionChoiceBox.getSelectionModel().select(Integer.valueOf(cardToMove.positionProperty().getValue() + 1));
        }
    }


}