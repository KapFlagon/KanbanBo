package entities.ui.custom_components.board;

import entities.ui.custom_components.popups.BoardPopUp;
import entities.ui.custom_components.shared.DynTextField;
import entities.ui.custom_components.shared.I_DynamicUI;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CategoryContainer extends VBox implements I_DynamicUI {

	private String titleString;
	private DynTextField titleTextField;
	private Button createBoardButton;
	private ArrayList<BoardTile> boardsArrayList;
	private ScrollPane listScrollPane;
	private HBox listHbox;
	private HBox headerHbox;
	private int listPosition;

	public CategoryContainer() {
		titleString = "Name your Category...";
		initialize();
	}

	public CategoryContainer(String containerTitle) {
		titleString = containerTitle;
		initialize();
	}

	public void setTitleString(String newTitle) {
		this.titleString = newTitle;
		this.titleTextField.setText(titleString);
	}
	public String getTitleString() {
		return titleString;
	}
	public void setTitleTextField(DynTextField newTitleTextField) {
		this.titleTextField = newTitleTextField;
		this.titleString = titleTextField.getText();
	}
	public DynTextField getTitleTextField() {
		return titleTextField;
	}
	public void setCreateBoardButton(Button newCreateButton) {
		this.createBoardButton = newCreateButton;
	}
	public Button getCreateBoardButton() {
		return createBoardButton;
	}

	public void initialize() {
		//this.setPrefSize(600,200);

		titleTextField = new DynTextField(titleString);
		//titleTextField.setEditable(false);
		//titleTextField.setDisable(true);
		boardsArrayList = new ArrayList<BoardTile>();

		createBoardButton = new Button("Create Board");
		createBoardButton.setOnAction(event -> {
			//addBoard();
			BoardPopUp bpu = new BoardPopUp();
			Color colour = BoardColours.getRandomColour();
			bpu.getBackground().setFill(colour);
			bpu.getBoardTitleField().setInactiveBackgroundColour(colour);
			bpu.getPopupWindow().showAndWait();
		});

		listScrollPane = new ScrollPane();
		listScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		listScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		//listScrollPane.setPrefViewportHeight(210);
		//listScrollPane.setPrefViewportWidth(600);

		headerHbox = new HBox();
		headerHbox.getChildren().add(titleTextField);
		headerHbox.getChildren().add(createBoardButton);

		listHbox = new HBox();

		updateDisplay();
	}

	public void addBoard() {
		BoardTile bt = new BoardTile();
		boardsArrayList.add(bt);
		System.out.println("Board added");
		updateDisplay();
	}

	public boolean updateDisplay() {
		this.getChildren().clear();
		this.getChildren().add(headerHbox);

		try {
			listHbox.getChildren().clear();
			for (BoardTile bt : boardsArrayList) {
				listHbox.getChildren().add(bt);
				System.out.println("inside boardtile loop");
			}
			listScrollPane.setContent(listHbox);
			this.getChildren().add(listScrollPane);
			System.out.println("Category container Display updated");
			return true;
		} catch (Exception e) {
			System.out.println("Exception encountered: " + e.getMessage() + "; Cause: " + e.getCause());
			return false;
		}
	}
}
