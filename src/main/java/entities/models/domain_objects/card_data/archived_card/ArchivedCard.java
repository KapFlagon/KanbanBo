package entities.models.domain_objects.card_data.archived_card;

public class ArchivedCard {

	private String archived_card_uuid;
	private String parent_lane_uuid;
	private String archived_card_title;


	// Getters and Setters
	public String getArchived_card_uuid() {
		return archived_card_uuid;
	}

	public void setArchived_card_uuid(String archived_card_uuid) {
		this.archived_card_uuid = archived_card_uuid;
	}

	public String getParent_lane_uuid() {
		return parent_lane_uuid;
	}

	public void setParent_lane_uuid(String parent_lane_uuid) {
		this.parent_lane_uuid = parent_lane_uuid;
	}

	public String getArchived_card_title() {
		return archived_card_title;
	}

	public void setArchived_card_title(String archived_card_title) {
		this.archived_card_title = archived_card_title;
	}
}
