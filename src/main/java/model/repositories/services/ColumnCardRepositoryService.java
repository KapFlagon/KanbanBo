package model.repositories.services;

import javafx.collections.ObservableList;
import model.activerecords.ColumnCardActiveRecord;
import model.activerecords.ProjectColumnActiveRecord;
import model.domainobjects.column.ColumnModel;
import model.repositories.ActiveCardListRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ColumnCardRepositoryService {


    // Variables
    private ActiveCardListRepository activeCardListRepository;


    // Constructors
    public ColumnCardRepositoryService(ProjectColumnActiveRecord<ColumnModel> projectColumnActiveRecord) throws IOException, SQLException {
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
