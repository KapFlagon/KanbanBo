package entities.models.domain_objects.board_data.archived_board;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "archived_board")
public class ArchivedBoard {

	@DatabaseField(id = true, canBeNull = false, unique = true, useGetSet = true)
	private String archived_board_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String archived_board_title;
	@DatabaseField(canBeNull = false, useGetSet = true)
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
