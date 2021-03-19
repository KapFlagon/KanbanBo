package model.datamodel.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class ArchivedProjectModel extends ProjectModel{


    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.DATE_STRING)
    private Date archived_timestamp;

    // Constructors


    // Getters and Setters
    public Date getArchived_timestamp() {
        return archived_timestamp;
    }
    public void setArchived_timestamp(Date archived_timestamp) {
        this.archived_timestamp = archived_timestamp;
    }


    // Initialisation methods


    // Other methods


}
