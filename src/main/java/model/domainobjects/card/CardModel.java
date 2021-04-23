package model.domainobjects.card;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "card")
public class CardModel extends AbstractCardModel {

    // Variables
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true, columnName = FOREIGN_KEY_NAME)
    private UUID parent_column_uuid;
    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, useGetSet = true)
    private int column_status;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int card_position;

    // Constructors


    // Getters and Setters
    public UUID getParent_column_uuid() {
        return parent_column_uuid;
    }
    public void setParent_column_uuid(UUID parent_column_uuid) {
        this.parent_column_uuid = parent_column_uuid;
    }

    public int getColumn_status() {
        return column_status;
    }
    public void setColumn_status(int column_status) {
        this.column_status = column_status;
    }

    public int getCard_position() {
        return card_position;
    }
    public void setCard_position(int card_position) {
        this.card_position = card_position;
    }

    // Initialisation methods


    // Other methods


}
