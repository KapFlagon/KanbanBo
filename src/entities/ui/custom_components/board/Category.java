package entities.ui.custom_components.board;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Category extends VBox {

	private ArrayList<BoardTile> boardsArrayList;

	public Category() {
		boardsArrayList = new ArrayList<BoardTile>();
	}
}
