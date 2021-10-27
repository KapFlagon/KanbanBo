package view.sharedviewcomponents.popups.finalcolumnselection;

import domain.entities.column.ObservableColumn;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import persistence.dto.column.ColumnDTO;
import persistence.mappers.ObservableObjectToDTO;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FinalColumnSelectionPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label finalColumnLbl;
    @FXML
    private ChoiceBox<ObservableColumn> columnChoiceBox;

    // Other variables
    @Inject
    KanbanBoDataService kanbanBoDataService;
    private ObservableList<ObservableColumn> availableColumns;

    // Constructors

    // Getters & Setters


    public void setAvailableColumns(ObservableList<ObservableColumn> availableColumns) {
        this.availableColumns = availableColumns;
        columnChoiceBox.getItems().addAll(availableColumns);
        for (ObservableColumn observableColumn : availableColumns) {
            if(observableColumn.isFinalColumn()) {
                columnChoiceBox.getSelectionModel().select(observableColumn);
            }
        }
        setUpChoiceBoxStringConverter();
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
        System.out.println("saving, selected: " + columnChoiceBox.getSelectionModel().getSelectedItem().columnTitleProperty());
        List<ColumnDTO> columnDTOList = new ArrayList<>();
        for(ObservableColumn observableColumn : availableColumns) {
            ColumnDTO columnDTO = ObservableObjectToDTO.mapColumnTableToColumnDTO(observableColumn);
            if(observableColumn.getColumnUUID().equals(columnChoiceBox.getSelectionModel().getSelectedItem().getColumnUUID())) {
                columnDTO.setFinalColumn(true);
            } else {
                columnDTO.setFinalColumn(false);
            }
            columnDTOList.add(columnDTO);
        }
        try {
            kanbanBoDataService.updateColumns(columnDTOList, availableColumns);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        event.consume();
        StageUtils.hideSubStage();
    }

    // Other methods
    private void setUpChoiceBoxStringConverter() {
        columnChoiceBox.setConverter(new StringConverter<ObservableColumn>() {
            @Override
            public String toString(ObservableColumn object) {
                if(object != null) {
                    return object.columnTitleProperty().get();
                } else {return "";}
            }

            @Override
            public ObservableColumn fromString(String string) {
                return null;
            }
        });
    }

}