package entities.models.domain_objects.card_data;

public class CardFacts {

	private String parent_card_uuid;
	private String card_description;


	// Getters and Setters
	public String getParent_card_uuid() {
		return parent_card_uuid;
	}

	public void setParent_card_uuid(String parent_card_uuid) {
		this.parent_card_uuid = parent_card_uuid;
	}

	public String getCard_description() {
		return card_description;
	}

	public void setCard_description(String card_description) {
		this.card_description = card_description;
	}
}
