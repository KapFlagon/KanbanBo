package entities.models.domain_objects.lane_data;

public class ArchivedLane {

	private String archived_lane_uuid;
	private String board_uuid;
	private String archived_lane_title;
	private String archived_lane_position;


	// Getters and Setters
	public String getArchived_lane_uuid() {
		return archived_lane_uuid;
	}

	public void setArchived_lane_uuid(String archived_lane_uuid) {
		this.archived_lane_uuid = archived_lane_uuid;
	}

	public String getBoard_uuid() {
		return board_uuid;
	}

	public void setBoard_uuid(String board_uuid) {
		this.board_uuid = board_uuid;
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
