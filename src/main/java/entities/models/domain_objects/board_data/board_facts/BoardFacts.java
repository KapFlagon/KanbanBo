package entities.models.domain_objects.board_data.board_facts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "board_facts")
public class BoardFacts {


	@DatabaseField(id = true, canBeNull = false, unique = true, useGetSet = true)
	private String board_facts_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true, foreign = true)
	private String parent_board_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String board_description;


	// Getters and Setters
	public String getBoard_facts_uuid() {
		return board_facts_uuid;
	}

	public void setBoard_facts_uuid(String board_facts_uuid) {
		this.board_facts_uuid = board_facts_uuid;
	}

	public String getParent_board_uuid() {
		return parent_board_uuid;
	}

	public void setParent_board_uuid(String parent_board_uuid) {
		this.parent_board_uuid = parent_board_uuid;
	}

	public String getBoard_description() {
		return board_description;
	}

	public void setBoard_description(String board_description) {
		this.board_description = board_description;
	}
}
