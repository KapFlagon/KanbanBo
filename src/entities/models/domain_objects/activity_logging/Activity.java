package entities.models.domain_objects.activity_logging;

public class Activity {

	private String activity_uuid;
	private String parent_item_uuid;
	private String activity_entry_date;
	private String activity_entry_time;
	private String activity_log_text;


	// Getters and Setters
	public String getActivity_uuid() {
		return activity_uuid;
	}

	public void setActivity_uuid(String activity_uuid) {
		this.activity_uuid = activity_uuid;
	}

	public String getParent_item_uuid() {
		return parent_item_uuid;
	}

	public void setParent_item_uuid(String parent_item_uuid) {
		this.parent_item_uuid = parent_item_uuid;
	}

	public String getActivity_entry_date() {
		return activity_entry_date;
	}

	public void setActivity_entry_date(String activity_entry_date) {
		this.activity_entry_date = activity_entry_date;
	}

	public String getActivity_entry_time() {
		return activity_entry_time;
	}

	public void setActivity_entry_time(String activity_entry_time) {
		this.activity_entry_time = activity_entry_time;
	}

	public String getActivity_log_text() {
		return activity_log_text;
	}

	public void setActivity_log_text(String activity_log_text) {
		this.activity_log_text = activity_log_text;
	}
}
