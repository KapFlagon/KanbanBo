package persistence.tables.bridges.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "label_bridge")
public class LabelBridgeTable {


    // Variables
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID parent_uuid;
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID label_uuid;


    // Constructors
    public LabelBridgeTable() {
    }


    // Getters and Setters
    public UUID getParent_uuid() {
        return parent_uuid;
    }
    public void setParent_uuid(UUID parent_uuid) {
        this.parent_uuid = parent_uuid;
    }

    public UUID getLabel_uuid() {
        return label_uuid;
    }
    public void setLabel_uuid(UUID label_uuid) {
        this.label_uuid = label_uuid;
    }


    // Initialisation methods


    // Other methods


}
