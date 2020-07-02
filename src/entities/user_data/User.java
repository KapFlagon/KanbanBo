package entities.user_data;

public class User {

	private String first_name;
	private String last_name;

	// Getters and setters
	public String get_first_name() {
		return first_name;
	}
	public void set_first_name(String new_name) {
		this.first_name = new_name;
	}

	public String get_last_name() {
		return last_name;
	}
	public void set_last_name(String new_name) {
		this.last_name = new_name;
	}
}
