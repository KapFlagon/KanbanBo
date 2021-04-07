package model.domainobjects.card;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "active_column_card")
public class ActiveColumnCardModel extends AbstractColumnCardModel{


    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int card_position;
    @DatabaseField(canBeNull = false, useGetSet = true)
    private UUID parent_column;

    // Constructors


    // Getters and Setters
    public int getCard_position() {
        return card_position;
    }
    public void setCard_position(int card_position) {
        this.card_position = card_position;
    }

    public UUID getParent_column() {
        return parent_column;
    }
    public void setParent_column(UUID parent_column) {
        this.parent_column = parent_column;
    }

    // Initialisation methods


    // Other methods


}
