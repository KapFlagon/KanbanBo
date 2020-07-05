package entities.models.user_data;

import entities.models.abstract_classes.DataObject;

import java.util.List;

//public class User extends DataObject {
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
