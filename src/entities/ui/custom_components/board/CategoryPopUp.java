package entities.ui.custom_components.board;

import entities.ui.custom_components.shared.PopUpWindow;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CategoryPopUp extends PopUpWindow {


	private VBox vBox;
	private HBox hBox;
	private TextField inputField;
	private Button confirmButton;
	private Button cancelButton;


	public CategoryPopUp() {
		super("New Category");
		vBox = new VBox();
		hBox = new HBox();
		inputField = new TextField();
		inputField.setPromptText("Enter a category name...");
		confirmButton = new Button("Create category");
		cancelButton = new Button ("Cancel");

		hBox.getChildren().addAll(confirmButton, cancelButton);
		hBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(inputField, hBox);
		initScene(vBox);

	}


	public VBox getvBox() {
		return vBox;
	}
	public void setvBox(VBox vBox) {
		this.vBox = vBox;
	}
	public HBox gethBox() {
		return hBox;
	}
	public void sethBox(HBox hBox) {
		this.hBox = hBox;
	}
	public TextField getInputField() {
		return inputField;
	}
	public void setInputField(TextField inputField) {
		this.inputField = inputField;
	}
	public Button getConfirmButton() {
		return confirmButton;
	}
	public void setConfirmButton(Button confirmButton) {
		this.confirmButton = confirmButton;
	}
	public Button getCancelButton() {
		return cancelButton;
	}
	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

}
