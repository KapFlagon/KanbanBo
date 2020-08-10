package entities.models.domain_objects.board_data;

public class BoardFacts {

	private String board_facts_uuid;
	private String board_uuid;
	private String board_description;


	// Getters and Setters
	public String getBoard_facts_uuid() {
		return board_facts_uuid;
	}

	public void setBoard_facts_uuid(String board_facts_uuid) {
		this.board_facts_uuid = board_facts_uuid;
	}

	public String getBoard_uuid() {
		return board_uuid;
	}

	public void setBoard_uuid(String board_uuid) {
		this.board_uuid = board_uuid;
	}

	public String getBoard_description() {
		return board_description;
	}

	public void setBoard_description(String board_description) {
		this.board_description = board_description;
	}
}
