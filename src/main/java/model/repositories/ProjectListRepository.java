package model.repositories;

import javafx.collections.ObservableList;
import model.domainobjects.project.AbstractProjectModel;

public class ProjectListRepository<T extends AbstractProjectModel> {//extends AbstractModelRepository{


    // Constructors
    public ProjectListRepository(Class<T> modelClassType) {
       // super(modelClassType);
    }
    public ProjectListRepository(Class<T> modelClassType, ObservableList<T> projectList) {
        //super(modelClassType, projectList);
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public <T extends AbstractProjectModel> void addItem(T t) {
       // activeRecordObservableList.add(t);
    }

    public <T extends AbstractProjectModel> void removeItem(T t) {
        //activeRecordObservableList.remove(t);
    }


}
