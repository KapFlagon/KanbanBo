package entities.models.domain_objects.board_data.active_board;

public class ActiveBoard {

	private String active_board_uuid;
	private String category_uuid;
	private String active_board_title;
	private int active_board_position;
	private boolean active_board_favourite;


	// Getters and Setters
	public String getActive_board_uuid() {
		return active_board_uuid;
	}

	public void setActive_board_uuid(String active_board_uuid) {
		this.active_board_uuid = active_board_uuid;
	}

	public String getCategory_uuid() {
		return category_uuid;
	}

	public void setCategory_uuid(String category_uuid) {
		this.category_uuid = category_uuid;
	}

	public String getActive_board_title() {
		return active_board_title;
	}

	public void setActive_board_title(String active_board_title) {
		this.active_board_title = active_board_title;
	}

	public int getActive_board_position() {
		return active_board_position;
	}

	public void setActive_board_position(int active_board_position) {
		this.active_board_position = active_board_position;
	}

	public boolean isActive_board_favourite() {
		return active_board_favourite;
	}

	public void setActive_board_favourite(boolean active_board_favourite) {
		this.active_board_favourite = active_board_favourite;
	}
}
