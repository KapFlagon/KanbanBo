package model.domainobjects.card;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import model.domainobjects.DomainObjectWithUUID;

import java.util.UUID;


@DatabaseTable(tableName = "abstract_card")
public abstract class AbstractCard implements DomainObjectWithUUID {

    // Constant for query building using database fields
    public static final String FOREIGN_KEY_NAME = "parent_column_uuid";

    // Variables
    @DatabaseField(generatedId = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID card_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String card_title;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String card_description_text;

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

    // Establishing contract for extending classes so that they have some sort of access for a parent UUID and position.
    // This will allow for behaviour differences in each extending class.
    public abstract UUID getParent_column_uuid();
    public abstract void setParent_column_uuid(UUID parent_column_uuid);

    public abstract int getCard_position();
    public abstract void setCard_position(int card_position);


    // Initialisation methods


    // Other methods


    @Override
    public UUID get_uuid() {
        return getCard_uuid();
    }
}
