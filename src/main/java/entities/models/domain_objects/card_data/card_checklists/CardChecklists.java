package entities.models.domain_objects.card_data.card_checklists;

public class CardChecklists {

	private String parent_card_uuid;
	private String linked_checklist_uuid;


	// Getters and Setters
	public String getParent_card_uuid() {
		return parent_card_uuid;
	}

	public void setParent_card_uuid(String parent_card_uuid) {
		this.parent_card_uuid = parent_card_uuid;
	}

	public String getLinked_checklist_uuid() {
		return linked_checklist_uuid;
	}

	public void setLinked_checklist_uuid(String linked_checklist_uuid) {
		this.linked_checklist_uuid = linked_checklist_uuid;
	}
}
