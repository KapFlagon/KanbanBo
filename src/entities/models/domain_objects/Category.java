package entities.models.domain_objects;

import entities.models.domain_objects.board_data.Board;

import java.util.List;

//public class Category extends DataObject {
public class Category {

	private String title;
	private int position;

	// Getters and Setters
	public String getTitle() { return title; }
	public void setTitle(String new_title) { this.title = new_title; }
	public int getPosition() { return position; }
	public void setPosition(int new_position) { this.position = position; }

}
