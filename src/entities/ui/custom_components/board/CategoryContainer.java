package entities.ui.custom_components.board;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class CategoryContainer extends HBox { // TODO May need to use a different extension here, as There will need to be a VBOX with title and button, then an HBOX with boards.

	private String titleString;
	private TextField titleTextField;
	private Button createBoardButton;
	private ArrayList<BoardTile> boardsArrayList;

	public CategoryContainer() {
		titleString = "Name your Category...";
		titleTextField = new TextField(titleString);
		boardsArrayList = new ArrayList<BoardTile>();
		createBoardButton = new Button("Create Board");
	}

	public CategoryContainer(String containerTitle) {

	}

	public void setTitleString(String newTitle) {
		this.titleString = newTitle;
		this.titleTextField.setText(titleString);
	}
	public String getTitleString() {
		return titleString;
	}
	public void setTitleTextField(TextField newTitleTextField) {
		this.titleTextField = newTitleTextField;
		this.titleString = titleTextField.getText();
	}
	public TextField getTitleTextField() {
		return titleTextField;
	}
	public void setCreateBoardButton(Button newCreateButton) {
		this.createBoardButton = newCreateButton;
	}
	public Button getCreateBoardButton() {
		return createBoardButton;
	}
}
