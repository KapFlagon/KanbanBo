package entities.models.domain_objects.category;

//public class Category extends DataObject {
public class Category {

	private String category_uuid;
	private String category_title;
	private int category_position;

	// Getters and Setters
	public String getCategory_uuid() {
		return category_uuid;
	}

	public void setCategory_uuid(String category_uuid) {
		this.category_uuid = category_uuid;
	}

	public String getCategory_title() {
		return category_title;
	}

	public void setCategory_title(String category_title) {
		this.category_title = category_title;
	}

	public int getCategory_position() {
		return category_position;
	}

	public void setCategory_position(int category_position) {
		this.category_position = category_position;
	}
}
