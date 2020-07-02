package entities.user_data;

import entities.board_data.Board;

import java.util.List;

public class Category {
	String name;
	int position;
	List<Board> board_list;

	// Getters and Setters
	public String get_name() {
		return name;
	}
	public void set_name(String new_name) {
		this.name = new_name;
	}

	public int get_position() {
		return position;
	}
	public void set_position(int new_position) {
		this.position = position;
	}

	public List<Board> get_board_list() {
		return board_list;
	}
	public void set_board_list(List<Board> new_board_list) {
		this.board_list = new_board_list;
	}
}
