package entities.model.user_data;

import java.util.List;

public class User {

	private String firstName;
	private String lastName;
	private List<Category> categoryList;

	// Getters and setters
	public String getFirstName() { return firstName; }
	public void setFirstName(String new_name) { this.firstName = new_name; }
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String new_name) {
		this.lastName = new_name;
	}
	public List<Category> getCategoryList() { return categoryList; }
	public void setCategoryList(List<Category> categoryList) { this.categoryList = categoryList; }
}
