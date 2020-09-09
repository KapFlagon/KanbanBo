package entities.models.domain_objects.card_data.checklist;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "checklist")
public class Checklist {

	@DatabaseField(id = true, canBeNull = false, unique = true, useGetSet = true)
	private String checklist_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private int checklist_position;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String checklist_title;


	// Getters and Setters
	public String getChecklist_uuid() {
		return checklist_uuid;
	}

	public void setChecklist_uuid(String checklist_uuid) {
		this.checklist_uuid = checklist_uuid;
	}

	public int getChecklist_position() {
		return checklist_position;
	}

	public void setChecklist_position(int checklist_position) {
		this.checklist_position = checklist_position;
	}

	public String getChecklist_title() {
		return checklist_title;
	}

	public void setChecklist_title(String checklist_title) {
		this.checklist_title = checklist_title;
	}
}
