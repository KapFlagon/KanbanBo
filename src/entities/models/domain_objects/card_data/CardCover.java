package entities.models.domain_objects.card_data;

import java.sql.Blob;

public class CardCover {

	private String parent_card_uuid;
	private String cover_title;
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
