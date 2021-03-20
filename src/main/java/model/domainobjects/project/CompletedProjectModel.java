package model.domainobjects.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "completed_project")
public class CompletedProjectModel extends AbstractProjectModel {


    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.DATE_STRING)
    private Date completed_timestamp;

    // Constructors


    // Getters and Setters
    public Date getCompleted_timestamp() {
        return completed_timestamp;
    }
    public void setCompleted_timestamp(Date completed_timestamp) {
        this.completed_timestamp = completed_timestamp;
    }


    // Initialisation methods


    // Other methods


}
