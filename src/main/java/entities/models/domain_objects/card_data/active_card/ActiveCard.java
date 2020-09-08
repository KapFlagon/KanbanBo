package entities.models.domain_objects.card_data.active_card;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "active_card")
public class ActiveCard {

	@DatabaseField(id = true, canBeNull = false, unique = true, useGetSet = true)
	private String category_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true, foreign = true)
	private String active_card_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true, foreign = true)
	private String parent_lane_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private int active_card_position;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String active_card_title;


	// Getters and Setters
	public String getActive_card_uuid() {
		return active_card_uuid;
	}

	public void setActive_card_uuid(String active_card_uuid) {
		this.active_card_uuid = active_card_uuid;
	}

	public String getParent_lane_uuid() {
		return parent_lane_uuid;
	}

	public void setParent_lane_uuid(String parent_lane_uuid) {
		this.parent_lane_uuid = parent_lane_uuid;
	}

	public int getActive_card_position() {
		return active_card_position;
	}

	public void setActive_card_position(int active_card_position) {
		this.active_card_position = active_card_position;
	}

	public String getActive_card_title() {
		return active_card_title;
	}

	public void setActive_card_title(String active_card_title) {
		this.active_card_title = active_card_title;
	}
}
