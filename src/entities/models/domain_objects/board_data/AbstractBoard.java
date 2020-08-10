package entities.models.domain_objects.board_data;

public abstract class AbstractBoard {

	private String board_uuid;
	private String board_title;
	private int board_position;


	// Getters and Setters
	public String getBoard_uuid() {
		return board_uuid;
	}

	public void setBoard_uuid(String board_uuid) {
		this.board_uuid = board_uuid;
	}

	public String getBoard_title() {
		return board_title;
	}

	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}

	public int getBoard_position() {
		return board_position;
	}

	public void setBoard_position(int board_position) {
		this.board_position = board_position;
	}
}
