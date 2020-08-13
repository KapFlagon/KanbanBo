package entities.models.domain_objects.lane_data;

public class ActiveLane {

	private String active_lane_uuid;
	private String parent_board_uuid;
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
