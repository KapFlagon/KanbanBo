package entities.models.domain_objects.card_data;

public class CardLabels {

	private String linked_label_uuid;
	private String linked_card_uuid;


	// Getters and Setters
	public String getLinked_label_uuid() {
		return linked_label_uuid;
	}

	public void setLinked_label_uuid(String linked_label_uuid) {
		this.linked_label_uuid = linked_label_uuid;
	}

	public String getLinked_card_uuid() {
		return linked_card_uuid;
	}

	public void setLinked_card_uuid(String linked_card_uuid) {
		this.linked_card_uuid = linked_card_uuid;
	}
}
