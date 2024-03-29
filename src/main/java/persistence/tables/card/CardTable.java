package persistence.tables.card;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "card")
public class CardTable extends AbstractCardBaseTable {

    public static final String POSITION_KEY_NAME = "card_position";

    // Variables
    @DatabaseField(canBeNull = false, dataType = DataType.UUID, useGetSet = true, columnName = FOREIGN_KEY_NAME)
    private UUID parent_column_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int card_position;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String due_on_date;

    // Constructors


    // Getters and Setters
    public UUID getParent_column_uuid() {
        return parent_column_uuid;
    }
    public void setParent_column_uuid(UUID parent_column_uuid) {
        this.parent_column_uuid = parent_column_uuid;
    }

    public int getCard_position() {
        return card_position;
    }
    public void setCard_position(int card_position) {
        this.card_position = card_position;
    }

    public String getDue_on_date() {
        return due_on_date;
    }
    public void setDue_on_date(String due_on_date) {
        this.due_on_date = due_on_date;
    }

    // Initialisation methods


    // Other methods


}
