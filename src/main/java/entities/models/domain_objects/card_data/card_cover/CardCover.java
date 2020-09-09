package entities.models.domain_objects.card_data.card_cover;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Blob;

@DatabaseTable(tableName = "card_cover")
public class CardCover {

	@DatabaseField(id = true, canBeNull = false, useGetSet = true, foreign = true)
	private String parent_card_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String cover_title;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private Blob cover_data;


	// Getters and Setters
	public String getParent_card_uuid() {
		return parent_card_uuid;
	}

	public void setParent_card_uuid(String parent_card_uuid) {
		this.parent_card_uuid = parent_card_uuid;
	}

	public String getCover_title() {
		return cover_title;
	}

	public void setCover_title(String cover_title) {
		this.cover_title = cover_title;
	}

	public Blob getCover_data() {
		return cover_data;
	}

	public void setCover_data(Blob cover_data) {
		this.cover_data = cover_data;
	}
}
