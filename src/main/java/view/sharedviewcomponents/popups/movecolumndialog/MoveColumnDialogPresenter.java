package view.sharedviewcomponents.popups.movecolumndialog;

import domain.entities.column.ObservableColumn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import persistence.dto.column.ColumnDTO;
import persistence.mappers.ObservableObjectToDTO;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MoveColumnDialogPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label columnTitleLbl;
    @FXML
    private ChoiceBox<Integer> columnPositionChoiceBox;

    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableColumn columnToMove;

    // Constructors

    // Getters & Setters
    public void setColumnToMove(ObservableColumn columnToMove) throws SQLException, IOException {
        this.columnToMove = columnToMove;
        columnTitleLbl.setText(columnToMove.columnTitleProperty().getValue());
        int size = kanbanBoDataService.getRelatedColumns(columnToMove.getColumnUUID()).size();
        for(int iterator = 0; iterator < size; iterator++) {
            columnPositionChoiceBox.getItems().add(iterator + 1);
        }
        // Using Integer.valueOf here to avoid the select method interpreting the value as an index.
        columnPositionChoiceBox.getSelectionModel().select(Integer.valueOf(this.columnToMove.columnPositionProperty().getValue() + 1));
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        ColumnDTO columnDTO = ObservableObjectToDTO.mapObservableColumnToColumnDTO(columnToMove);
        columnDTO.setPosition(columnPositionChoiceBox.getSelectionModel().getSelectedItem() - 1);
        try {
            kanbanBoDataService.moveColumn(columnDTO, columnToMove);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StageUtils.hideSubStage();
    }

    // Other methods



}