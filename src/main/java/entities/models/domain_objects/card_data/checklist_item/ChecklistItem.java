package entities.models.domain_objects.card_data.checklist_item;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "checklist_item")
public class ChecklistItem {

	@DatabaseField(id = true, canBeNull = false, unique = true, useGetSet = true, foreign = true)
	private String checklist_item_uuid;
	@DatabaseField(canBeNull = false, unique = true, useGetSet = true, foreign = true)
	private String parent_checklist_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private int item_position;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String item_description;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private boolean item_completed;


	// Getters and Setters
	public String getChecklist_item_uuid() {
		return checklist_item_uuid;
	}

	public void setChecklist_item_uuid(String checklist_item_uuid) {
		this.checklist_item_uuid = checklist_item_uuid;
	}

	public String getParent_checklist_uuid() {
		return parent_checklist_uuid;
	}

	public void setParent_checklist_uuid(String parent_checklist_uuid) {
		this.parent_checklist_uuid = parent_checklist_uuid;
	}

	public int getItem_position() {
		return item_position;
	}

	public void setItem_position(int item_position) {
		this.item_position = item_position;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	public boolean isItem_completed() {
		return item_completed;
	}

	public void setItem_completed(boolean item_completed) {
		this.item_completed = item_completed;
	}
}
