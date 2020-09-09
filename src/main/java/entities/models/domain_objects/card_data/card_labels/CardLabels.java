package entities.models.domain_objects.card_data.card_labels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "card_labels")
public class CardLabels {

	@DatabaseField(canBeNull = false, useGetSet = true, foreign = true)
	private String linked_label_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true, foreign = true)
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
