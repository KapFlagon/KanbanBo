package model.domainobjects.card;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "column_card")
public class ColumnCardModel extends AbstractColumnCardModel{


    // Variables
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int card_position;

    // Constructors


    // Getters and Setters
    public int getCard_position() {
        return card_position;
    }
    public void setCard_position(int card_position) {
        this.card_position = card_position;
    }


    // Initialisation methods


    // Other methods


}
