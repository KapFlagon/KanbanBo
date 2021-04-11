package model.domainobjects.card;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "active_column_card")
public class ActiveColumnCardModel extends AbstractColumnCardModel{

    // Constant for query building using database fields
    public static final String FOREIGN_KEY_NAME = "parent_column_uuid";

    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int card_position;
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true, columnName = FOREIGN_KEY_NAME)
    private UUID parent_column_uuid;

    // Constructors


    // Getters and Setters
    public int getCard_position() {
        return card_position;
    }
    public void setCard_position(int card_position) {
        this.card_position = card_position;
    }

    public UUID getParent_column_uuid() {
        return parent_column_uuid;
    }
    public void setParent_column_uuid(UUID parent_column_uuid) {
        this.parent_column_uuid = parent_column_uuid;
    }

    // Initialisation methods


    // Other methods


}
