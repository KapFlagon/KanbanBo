package persistence.tables.card;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import persistence.tables.TableObject;

import java.util.UUID;


@DatabaseTable(tableName = "abstract_card")
public abstract class AbstractCardBaseTable implements TableObject<UUID> {

    // Constant for query building using database fields
    public static final String FOREIGN_KEY_NAME = "parent_column_uuid";

    // Variables
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID card_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String card_title;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String card_description_text;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String creation_timestamp;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String last_changed_timestamp;

    // Constructors


    // Getters and Setters
    public UUID getCard_uuid() {
        return card_uuid;
    }
    public void setCard_uuid(UUID card_uuid) {
        this.card_uuid = card_uuid;
    }

    public String getCard_title() {
        return card_title;
    }
    public void setCard_title(String card_title) {
        this.card_title = card_title;
    }

    public String getCard_description_text() {
        return card_description_text;
    }
    public void setCard_description_text(String card_description_text) {
        this.card_description_text = card_description_text;
    }

    public String getCreation_timestamp() {
        return creation_timestamp;
    }
    public void setCreation_timestamp(String creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public String getLast_changed_timestamp() {
        return last_changed_timestamp;
    }
    public void setLast_changed_timestamp(String last_changed_timestamp) {
        this.last_changed_timestamp = last_changed_timestamp;
    }

    // Establishing contract for extending classes so that they have some sort of access for a parent UUID and position.
    // This will allow for behaviour differences in each extending class.
    public abstract UUID getParent_column_uuid();
    public abstract void setParent_column_uuid(UUID parent_column_uuid);

    public abstract int getCard_position();
    public abstract void setCard_position(int card_position);


    // Initialisation methods


    // Other methods
    public UUID getID() {
        return getCard_uuid();
    }
}
