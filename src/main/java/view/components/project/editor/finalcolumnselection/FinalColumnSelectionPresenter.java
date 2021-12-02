package view.components.project.editor.finalcolumnselection;

import domain.entities.column.ObservableColumn;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import persistence.dto.column.ColumnDTO;
import persistence.services.KanbanBoDataService;
import utils.StageUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
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
            ColumnDTO.Builder columnDTOBuilder = ColumnDTO.Builder.newInstance(observableColumn.getParentProjectUUID().toString())
                    .uuid(observableColumn.getColumnUUID().toString())
                    .title(observableColumn.columnTitleProperty().getValue())
                    .createdOnTimeStamp(observableColumn.creationTimestampProperty().getValue())
                    .lastChangedOnTimeStamp(observableColumn.lastChangedTimestampProperty().getValue())
                    .position(observableColumn.columnPositionProperty().getValue());

            if(observableColumn.getColumnUUID().equals(columnChoiceBox.getSelectionModel().getSelectedItem().getColumnUUID())) {
                columnDTOBuilder.finalColumn(true);
            } else {
                columnDTOBuilder.finalColumn(false);
            }
            ColumnDTO columnDTO = new ColumnDTO(columnDTOBuilder);
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