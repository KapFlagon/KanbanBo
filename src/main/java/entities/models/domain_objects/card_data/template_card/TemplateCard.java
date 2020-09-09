package entities.models.domain_objects.card_data.template_card;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "template_card")
public class TemplateCard {

	@DatabaseField(id = true, canBeNull = false, unique = true, useGetSet = true)
	private String template_card_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String template_card_title;


	// Getters and Setters
	public String getTemplate_card_uuid() {
		return template_card_uuid;
	}

	public void setTemplate_card_uuid(String template_card_uuid) {
		this.template_card_uuid = template_card_uuid;
	}

	public String getTemplate_card_title() {
		return template_card_title;
	}

	public void setTemplate_card_title(String template_card_title) {
		this.template_card_title = template_card_title;
	}
}
