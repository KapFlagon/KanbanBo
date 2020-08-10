package entities.models.domain_objects.board_data;

import entities.models.domain_objects.card_data.Card;
import entities.models.domain_objects.card_data.Lane;

import java.util.List;

public class ArchivedBoard {

	private String archived_board_uuid;
	private String archived_board_title;
	private int archived_board_position;


	// Getters and Setters
	public String getArchived_board_uuid() {
		return archived_board_uuid;
	}

	public void setArchived_board_uuid(String archived_board_uuid) {
		this.archived_board_uuid = archived_board_uuid;
	}

	public String getArchived_board_title() {
		return archived_board_title;
	}

	public void setArchived_board_title(String archived_board_title) {
		this.archived_board_title = archived_board_title;
	}

	public int getArchived_board_position() {
		return archived_board_position;
	}

	public void setArchived_board_position(int archived_board_position) {
		this.archived_board_position = archived_board_position;
	}
}
