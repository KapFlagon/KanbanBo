package entities.ui.custom_components.popups;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class NewUserPopUpWindow extends PopUpWindow{

	private VBox fieldContainer;
	private Label header;
	private HBox firstNameHBox;
	private HBox lastNameHBox;
	private Label firstNameLabel;
	private Label lastNameLabel;
	private TextField firstNameField;
	private TextField lastNameField;
	private Button submitDataBtn;


	public NewUserPopUpWindow() {
		super("Specify User", 300, 300);
		initFieldContainer();
		initHeader();
		initFirstNameHBox();
		initFirstNameLabel();
		initFirstNameField();
		updateChildrenFirstNameHBox();
		initLastNameHBox();
		initLastNameLabel();
		initLastNameField();
		updateChildrenLastNameHBox();
		initSubmitDataBtn();
		updateFieldContainerChildren();
		initPopUpScene(fieldContainer);
	}


	// Getters and Setters
	public VBox getFieldContainer() {
		return fieldContainer;
	}

	public void setFieldContainer(VBox fieldContainer) {
		this.fieldContainer = fieldContainer;
	}

	public Label getHeader() {
		return header;
	}

	public void setHeader(Label header) {
		this.header = header;
	}

	public HBox getFirstNameHBox() {
		return firstNameHBox;
	}

	public void setFirstNameHBox(HBox firstNameHBox) {
		this.firstNameHBox = firstNameHBox;
	}

	public HBox getLastNameHBox() {
		return lastNameHBox;
	}

	public void setLastNameHBox(HBox lastNameHBox) {
		this.lastNameHBox = lastNameHBox;
	}

	public Label getFirstNameLabel() {
		return firstNameLabel;
	}

	public void setFirstNameLabel(Label firstNameLabel) {
		this.firstNameLabel = firstNameLabel;
	}

	public TextField getFirstNameField() {
		return firstNameField;
	}

	public void setFirstNameField(TextField firstNameField) {
		this.firstNameField = firstNameField;
	}

	public Label getLastNameLabel() {
		return lastNameLabel;
	}

	public void setLastNameLabel(Label lastNameLabel) {
		this.lastNameLabel = lastNameLabel;
	}

	public TextField getLastNameField() {
		return lastNameField;
	}

	public void setLastNameField(TextField lastNameField) {
		this.lastNameField = lastNameField;
	}

	public Button getSubmitDataBtn() {
		return submitDataBtn;
	}

	public void setSubmitDataBtn(Button submitDataBtn) {
		this.submitDataBtn = submitDataBtn;
	}


	// Initialisation methods
	private void initFieldContainer() {
		fieldContainer = new VBox();
		fieldContainer.setAlignment(Pos.CENTER);
		fieldContainer.setSpacing(3);
	}

	private void initHeader() {
		header = new Label("Enter user details:");
	}

	private void initFirstNameHBox() {
		firstNameHBox = new HBox();
		firstNameHBox.setSpacing(3);
		firstNameHBox.setAlignment(Pos.CENTER);
	}

	private void initFirstNameLabel() {
		firstNameLabel = new Label("First name: ");
	}

	private void initFirstNameField() {
		firstNameField = new TextField();
		firstNameField.setPromptText("First Name");
	}

	private void updateChildrenFirstNameHBox() {
		firstNameHBox.getChildren().add(firstNameLabel);
		firstNameHBox.getChildren().add(firstNameField);
	}

	private void initLastNameHBox() {
		lastNameHBox = new HBox();
		lastNameHBox.setSpacing(3);
		lastNameHBox.setAlignment(Pos.CENTER);
	}

	private void initLastNameLabel() {
		lastNameLabel = new Label("Last Name: ");
	}

	private void initLastNameField() {
		lastNameField = new TextField();
		lastNameField.setPromptText("Last Name");
	}

	private void updateChildrenLastNameHBox() {
		lastNameHBox.getChildren().add(lastNameLabel);
		lastNameHBox.getChildren().add(lastNameField);
	}

	private void initSubmitDataBtn() {
		submitDataBtn = new Button("Submit");
		submitDataBtn.setOnAction(event -> {
			throw new RuntimeException("No action specified for \"Submit\" button");
		});
	}


	// Other methods
	public void updateFieldContainerChildren() {
		fieldContainer.getChildren().add(header);
		fieldContainer.getChildren().add(firstNameHBox);
		fieldContainer.getChildren().add(lastNameHBox);
		fieldContainer.getChildren().add(submitDataBtn);
	}

}
