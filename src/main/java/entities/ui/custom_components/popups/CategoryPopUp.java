package entities.ui.custom_components.popups;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CategoryPopUp extends PopUpWindow {


	private VBox layoutVBox;
	private HBox buttonHBox;
	private TextField inputField;
	private Button confirmBtn;
	private Button cancelBtn;


	public CategoryPopUp() {
		super("New Category");
		initLayoutVBox();
		initButtonHBox();
		initInputField();
		initConfirmBtn();
		initCancelBtn();
		updateButtonHBoxChildren();
		updateLayoutVBoxChildren();
		initPopUpScene(layoutVBox);
	}

	// Getters and Setters
	public VBox setLayoutVBox() {
		return layoutVBox;
	}

	public void setLayoutVBox(VBox layoutVBox) {
		this.layoutVBox = layoutVBox;
	}

	public HBox getButtonHBox() {
		return buttonHBox;
	}

	public void setButtonHBox(HBox buttonHBox) {
		this.buttonHBox = buttonHBox;
	}

	public TextField getInputField() {
		return inputField;
	}

	public void setInputField(TextField inputField) {
		this.inputField = inputField;
	}

	public Button getConfirmBtn() {
		return confirmBtn;
	}

	public void setConfirmBtn(Button confirmBtn) {
		this.confirmBtn = confirmBtn;
	}

	public Button getCancelBtn() {
		return cancelBtn;
	}

	public void setCancelBtn(Button cancelBtn) {
		this.cancelBtn = cancelBtn;
	}


	// Initialisation methods
	private void initLayoutVBox() {
		layoutVBox = new VBox();
	}

	private void initButtonHBox() {
		buttonHBox = new HBox();
		buttonHBox.setAlignment(Pos.CENTER);
	}

	private void initInputField() {
		inputField = new TextField();
		inputField.setPromptText("Enter a category name...");
	}

	private void initConfirmBtn() {
		confirmBtn = new Button("Create category");
		confirmBtn.setOnAction(event -> {
			throw new RuntimeException("No action specified for \"Confirm\" button");
		});
	}

	private void initCancelBtn() {
		cancelBtn = new Button ("Cancel");
	}


	// Other methods
	private void updateButtonHBoxChildren() {
		buttonHBox.getChildren().addAll(confirmBtn, cancelBtn);
	}

	private void updateLayoutVBoxChildren() {
		layoutVBox.getChildren().addAll(inputField, buttonHBox);
	}

}
