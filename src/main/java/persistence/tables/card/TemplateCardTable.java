package persistence.tables.card;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "template_card")
public class TemplateCardTable extends AbstractCardBaseTable {


    // Variables
    @DatabaseField(canBeNull = true, dataType = DataType.UUID, useGetSet = true, columnName = FOREIGN_KEY_NAME)
    private UUID parent_column_uuid;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.INTEGER)
    private int card_position;

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

    // Initialisation methods


    // Other methods


}
