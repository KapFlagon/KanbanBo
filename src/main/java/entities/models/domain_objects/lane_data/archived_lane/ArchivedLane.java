package entities.models.domain_objects.lane_data.archived_lane;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "archived_lane")
public class ArchivedLane {

	@DatabaseField(id = true, unique = true, canBeNull = false, useGetSet = true)
	private String archived_lane_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true, foreign = true)
	private String parent_board_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String archived_lane_title;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String archived_lane_position;


	// Getters and Setters
	public String getArchived_lane_uuid() {
		return archived_lane_uuid;
	}

	public void setArchived_lane_uuid(String archived_lane_uuid) {
		this.archived_lane_uuid = archived_lane_uuid;
	}

	public String getParent_board_uuid() {
		return parent_board_uuid;
	}

	public void setParent_board_uuid(String parent_board_uuid) {
		this.parent_board_uuid = parent_board_uuid;
	}

	public String getArchived_lane_title() {
		return archived_lane_title;
	}

	public void setArchived_lane_title(String archived_lane_title) {
		this.archived_lane_title = archived_lane_title;
	}

	public String getArchived_lane_position() {
		return archived_lane_position;
	}

	public void setArchived_lane_position(String archived_lane_position) {
		this.archived_lane_position = archived_lane_position;
	}
}
