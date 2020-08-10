package entities.models.domain_objects.card_data;

import java.sql.Blob;

public class Attachment {

	private String attachment_uuid;
	private String parent_card_uuid;
	private String attachment_title;
	private Blob attachment_data;


	// Getters and Setters
	public String getAttachment_uuid() {
		return attachment_uuid;
	}

	public void setAttachment_uuid(String attachment_uuid) {
		this.attachment_uuid = attachment_uuid;
	}

	public String getParent_card_uuid() {
		return parent_card_uuid;
	}

	public void setParent_card_uuid(String parent_card_uuid) {
		this.parent_card_uuid = parent_card_uuid;
	}

	public String getAttachment_title() {
		return attachment_title;
	}

	public void setAttachment_title(String attachment_title) {
		this.attachment_title = attachment_title;
	}

	public Blob getAttachment_data() {
		return attachment_data;
	}

	public void setAttachment_data(Blob attachment_data) {
		this.attachment_data = attachment_data;
	}
}
