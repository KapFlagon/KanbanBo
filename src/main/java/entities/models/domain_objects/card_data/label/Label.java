package entities.models.domain_objects.card_data.label;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "label")
public class Label {

	@DatabaseField(id = true, canBeNull = false, unique = true, useGetSet = true)
	private String label_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String label_title;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String label_base_colour;   // TODO need to examine this further and reconsider how to store this data...
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String label_colourblind_texture;   // TODO need to examine this further and reconsider how to store this data...


	// Getters and Setters
	public String getLabel_uuid() {
		return label_uuid;
	}

	public void setLabel_uuid(String label_uuid) {
		this.label_uuid = label_uuid;
	}

	public String getLabel_title() {
		return label_title;
	}

	public void setLabel_title(String label_title) {
		this.label_title = label_title;
	}

	public String getLabel_base_colour() {
		return label_base_colour;
	}

	public void setLabel_base_colour(String label_base_colour) {
		this.label_base_colour = label_base_colour;
	}

	public String getLabel_colourblind_texture() {
		return label_colourblind_texture;
	}

	public void setLabel_colourblind_texture(String label_colourblind_texture) {
		this.label_colourblind_texture = label_colourblind_texture;
	}
}
