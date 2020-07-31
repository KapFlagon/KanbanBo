package entities.models.user_data;

import entities.models.abstract_classes.DataObject;
import entities.models.board_data.Board;

import java.util.List;

//public class Category extends DataObject {
public class Category {

	private String title;
	private int position;
	private List<Board> boardList;

	// Getters and Setters
	public String getTitle() { return title; }
	public void setTitle(String new_title) { this.title = new_title; }
	public int getPosition() { return position; }
	public void setPosition(int new_position) { this.position = position; }
	public List<Board> getBoardList() {
		return boardList;
	}
	public void setBoardList(List<Board> new_boardList) {
		this.boardList = new_boardList;
	}

}
