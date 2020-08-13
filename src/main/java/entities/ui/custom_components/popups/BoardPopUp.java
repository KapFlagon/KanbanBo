package entities.ui.custom_components.popups;

import entities.ui.custom_components.shared.DynTextField;
import entities.ui.custom_components.popups.PopUpWindow;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class BoardPopUp extends PopUpWindow {


	private VBox outerVbox;  // maybe use a borderpane instead



	private DynTextField boardTitleField;
	private Rectangle background;
	private StackPane stackPane;
	private HBox buttonsHbox;
	private Button createButton;
	private Button createAndViewButton;
	private Button createFromTemplateButton;



	public BoardPopUp() {
		super("New Board");

		getPopupWindow().setWidth(400);
		getPopupWindow().setHeight(300);

		outerVbox = new VBox();
		stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER);
		buttonsHbox = new HBox();

		boardTitleField = new DynTextField();
		boardTitleField.setPromptText("Enter a board title...");
		boardTitleField.setMaxWidth(100);
		StackPane.setAlignment(boardTitleField, Pos.TOP_LEFT);

		background = new Rectangle();
		background.setArcWidth(2);
		background.setArcHeight(2);
		background.setHeight(100);
		background.setWidth(200);
		//background.setFill(BoardColours.getRandomColour());
		StackPane.setAlignment(background, Pos.TOP_LEFT);

		//stackPane.getChildren().addAll(background, textLabel, boardTitleField);
		stackPane.getChildren().addAll(background, boardTitleField);

		createButton = new Button("Create");
		createAndViewButton = new Button ("Create and View");

		buttonsHbox.setAlignment(Pos.CENTER);
		buttonsHbox.getChildren().addAll(createButton, createAndViewButton);

		outerVbox.setAlignment(Pos.CENTER);
		outerVbox.getChildren().addAll(stackPane, buttonsHbox);

		initPopUpScene(outerVbox);
	}


	public DynTextField getBoardTitleField() {
		return boardTitleField;
	}
	public void setBoardTitleField(DynTextField boardTitleField) {
		this.boardTitleField = boardTitleField;
	}
	public Rectangle getBackground() {
		return background;
	}
	public void setBackground(Rectangle background) {
		this.background = background;
	}


}
