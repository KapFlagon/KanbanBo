package model.domainobjects.project;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "archived_project")
public class ArchivedProjectModel extends AbstractProjectModel {


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
