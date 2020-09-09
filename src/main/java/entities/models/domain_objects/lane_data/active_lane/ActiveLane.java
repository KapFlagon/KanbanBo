package entities.models.domain_objects.lane_data.active_lane;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;


@DatabaseTable(tableName = "active_lane")
public class ActiveLane {

	@DatabaseField(id = true, unique = true, canBeNull = false, useGetSet = true)
	private String active_lane_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true, foreign = true)
	private String parent_board_uuid;
	@DatabaseField(canBeNull = false, useGetSet = true)
	private int active_lane_position;


	// Getters and Setters
	public String getActive_lane_uuid() {
		return active_lane_uuid;
	}

	public void setActive_lane_uuid(String active_lane_uuid) {
		this.active_lane_uuid = active_lane_uuid;
	}

	public String getParent_board_uuid() {
		return parent_board_uuid;
	}

	public void setParent_board_uuid(String parent_board_uuid) {
		this.parent_board_uuid = parent_board_uuid;
	}

	public int getActive_lane_position() {
		return active_lane_position;
	}

	public void setActive_lane_position(int active_lane_position) {
		this.active_lane_position = active_lane_position;
	}
}
