package entities.models.domain_objects;

public class ProjectFolder {

	private String project_folder_uuid;
	private String parent_item_uuid;
	private String folder_title;
	private String folder_description;
	private String folder_path;


	// Getters and Setters
	public String getProject_folder_uuid() {
		return project_folder_uuid;
	}

	public void setProject_folder_uuid(String project_folder_uuid) {
		this.project_folder_uuid = project_folder_uuid;
	}

	public String getParent_item_uuid() {
		return parent_item_uuid;
	}

	public void setParent_item_uuid(String parent_item_uuid) {
		this.parent_item_uuid = parent_item_uuid;
	}

	public String getFolder_title() {
		return folder_title;
	}

	public void setFolder_title(String folder_title) {
		this.folder_title = folder_title;
	}

	public String getFolder_description() {
		return folder_description;
	}

	public void setFolder_description(String folder_description) {
		this.folder_description = folder_description;
	}

	public String getFolder_path() {
		return folder_path;
	}

	public void setFolder_path(String folder_path) {
		this.folder_path = folder_path;
	}
}
