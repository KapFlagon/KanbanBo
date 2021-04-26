package model.domainobjects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "status")
public class StatusModel {

    // TODO need to push data to DB during file creation, as well as create the table

    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int status_id;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String status_text;

    // Constructors
    public StatusModel() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    // Getters and Setters
    public int getStatus_id() {
        return status_id;
    }
    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus_text() {
        return status_text;
    }
    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }


    // Initialisation methods


    // Other methods


}
