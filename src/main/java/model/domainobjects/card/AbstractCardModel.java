package model.domainobjects.card;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;


@DatabaseTable(tableName = "abstract_card")
public abstract class AbstractCardModel {


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


    // Initialisation methods


    // Other methods


}
