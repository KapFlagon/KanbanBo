package entities.models.domain_objects.card_data.card_due_date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "card_due_date")
public class CardDueDate {

	@DatabaseField(id = true, canBeNull = false, unique = true, useGetSet = true, foreign = true)
	private String parent_card_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String target_date;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String target_time;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private boolean isCompleted;


	// Getters and Setters
	public String getParent_card_uuid() {
		return parent_card_uuid;
	}

	public void setParent_card_uuid(String parent_card_uuid) {
		this.parent_card_uuid = parent_card_uuid;
	}

	public String getTarget_date() {
		return target_date;
	}

	public void setTarget_date(String target_date) {
		this.target_date = target_date;
	}

	public String getTarget_time() {
		return target_time;
	}

	public void setTarget_time(String target_time) {
		this.target_time = target_time;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean completed) {
		isCompleted = completed;
	}
}
