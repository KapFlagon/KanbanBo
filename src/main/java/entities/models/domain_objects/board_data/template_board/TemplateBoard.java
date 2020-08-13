package entities.models.domain_objects.board_data.template_board;

public class TemplateBoard {

	private String template_board_uuid;
	private String template_board_title;
	private int template_board_position;


	// Getters and Setters

	public String getTemplate_board_uuid() {
		return template_board_uuid;
	}

	public void setTemplate_board_uuid(String template_board_uuid) {
		this.template_board_uuid = template_board_uuid;
	}

	public String getTemplate_board_title() {
		return template_board_title;
	}

	public void setTemplate_board_title(String template_board_title) {
		this.template_board_title = template_board_title;
	}

	public int getTemplate_board_position() {
		return template_board_position;
	}

	public void setTemplate_board_position(int template_board_position) {
		this.template_board_position = template_board_position;
	}
}
