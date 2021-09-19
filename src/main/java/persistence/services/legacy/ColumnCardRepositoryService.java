package persistence.services.legacy;

import javafx.collections.ObservableList;
import domain.activerecords.ColumnCardActiveRecord;
import domain.activerecords.ProjectColumnActiveRecord;
import persistence.tables.column.ColumnTable;
import persistence.repositories.legacy.ActiveCardListRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ColumnCardRepositoryService {


    // Variables
    private ActiveCardListRepository activeCardListRepository;


    // Constructors
    public ColumnCardRepositoryService(ProjectColumnActiveRecord<ColumnTable> projectColumnActiveRecord) throws IOException, SQLException {
        activeCardListRepository = new ActiveCardListRepository(projectColumnActiveRecord);
        activeCardListRepository.readFromDb();
        //initAllData();
    }


    // Getters and Setters
    public ActiveCardListRepository getActiveCardListRepository() {
        return activeCardListRepository;
    }
    public void setActiveCardListRepository(ActiveCardListRepository activeCardListRepository) {
        this.activeCardListRepository = activeCardListRepository;
    }

    public ObservableList<ColumnCardActiveRecord> getCardsList() {
        return activeCardListRepository.getActiveRecordObservableList();
    }

    // Initialisation methods


    // Other methods


}
