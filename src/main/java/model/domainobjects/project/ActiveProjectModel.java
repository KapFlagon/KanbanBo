package model.domainobjects.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "active_project")
public class ActiveProjectModel extends AbstractProjectModel {

    // TODO Update the main screen view to just use teh project list for now, and test creating/loading a DB file entry there.
    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.DATE_STRING)
    private Date last_changed_timestamp;


    // Constructors
    public ActiveProjectModel() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }


    // Getters and Setters
    public Date getLast_changed_timestamp() {
        return last_changed_timestamp;
    }
    public void setLast_changed_timestamp(Date last_changed_timestamp) {
        this.last_changed_timestamp = last_changed_timestamp;
    }


    // Initialisation methods


    // Other methods


}
