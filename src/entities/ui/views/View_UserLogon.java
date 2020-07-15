package entities.ui.views;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class View_UserLogon extends VBox {

	private Label programTitle;
	private Label usernameLabel;
	private TextField usernameTextField;
	private Label passwordLabel;
	private PasswordField passwordField;
	private Button logInButton;
	private Button newUserButton;
	private Button forgottenPasswordButton;
	private CheckBox rememberUserCheckbox;
	private GridPane fieldLayout;


	public View_UserLogon() {
		initProgramTitle();
		initUsernameLabel();
		initUsernameTextField();
		initPasswordLabel();
		initPasswordField();
		initLogInButton();
		initNewUserButton();
		initRememberUserCheckbox();
		initForgottenPasswordButton();
		initFieldLayout();
		initView();
	}


	public Label getUserLabel() {
		return usernameLabel;
	}
	public void setUserLabel(Label userLabel) {
		this.usernameLabel = userLabel;
	}
	public Label getPasswordLabel() {
		return passwordLabel;
	}
	public void setPasswordLabel(Label passwordLabel) {
		this.passwordLabel = passwordLabel;
	}
	public Button getLogInButton() {
		return logInButton;
	}
	public void setLogInButton(Button logInButton) {
		this.logInButton = logInButton;
	}
	public Button getNewUserButton() {
		return newUserButton;
	}
	public void setNewUserButton(Button newUserButton) {
		this.newUserButton = newUserButton;
	}
	public Label getProgramTitle() {
		return programTitle;
	}
	public void setProgramTitle(Label programTitle) {
		this.programTitle = programTitle;
	}
	public TextField getUsernameTextField() {
		return usernameTextField;
	}
	public void setUsernameTextField(TextField usernameTextField) {
		this.usernameTextField = usernameTextField;
	}
	public PasswordField getPasswordField() {
		return passwordField;
	}
	public void setPasswordField(PasswordField passwordField) {
		this.passwordField = passwordField;
	}
	public Button getForgottenPasswordButton() {
		return forgottenPasswordButton;
	}
	public void setForgottenPasswordButton(Button forgottenPasswordButton) {
		this.forgottenPasswordButton = forgottenPasswordButton;
	}
	public GridPane getFieldLayout() {
		return fieldLayout;
	}
	public void setFieldLayout(GridPane fieldLayout) {
		this.fieldLayout = fieldLayout;
	}
	public Label getUsernameLabel() {
		return usernameLabel;
	}
	public void setUsernameLabel(Label usernameLabel) {
		this.usernameLabel = usernameLabel;
	}
	public CheckBox getRememberUserCheckbox() {
		return rememberUserCheckbox;
	}
	public void setRememberUserCheckbox(CheckBox rememberUserCheckbox) {
		this.rememberUserCheckbox = rememberUserCheckbox;
	}


	public void initProgramTitle(){
		programTitle = new Label("KanbanBo");
		programTitle.setStyle("-fx-font: 40px Verdana;");
	}
	public void initUsernameLabel() {
		usernameLabel = new Label("Username: ");
	}
	public void initUsernameTextField() {
		usernameTextField = new TextField();
		usernameTextField.setPromptText("Username");
	}
	public void initPasswordLabel() {
		passwordLabel = new Label("Password: ");
	}
	public void initPasswordField() {
		passwordField = new PasswordField();
		passwordField.setPromptText("Password");
	}
	public void initLogInButton() {
		logInButton = new Button("Log in");
		logInButton.setAlignment(Pos.CENTER);
	}
	public void initNewUserButton() {
		newUserButton = new Button("New User");
		newUserButton.setAlignment(Pos.CENTER);
	}
	public void initRememberUserCheckbox() {
		rememberUserCheckbox = new CheckBox("Remember me");
		rememberUserCheckbox.setAlignment(Pos.CENTER);
	}
	public void initForgottenPasswordButton() {
		forgottenPasswordButton = new Button("Forgot Password");
		forgottenPasswordButton.setAlignment(Pos.CENTER);
	}
	public void initFieldLayout() {
		fieldLayout = new GridPane();
		fieldLayout.add(usernameLabel, 0, 0, 1, 1);
		fieldLayout.add(usernameTextField, 1, 0, 1, 1);
		fieldLayout.add(passwordLabel, 0, 1, 1, 1);
		fieldLayout.add(passwordField, 1, 1, 1, 1);
		fieldLayout.add(logInButton, 0, 2, 1, 1);
		fieldLayout.add(rememberUserCheckbox, 1, 2, 1, 1);
		fieldLayout.add(newUserButton, 0, 3, 1, 1);
		fieldLayout.add(forgottenPasswordButton, 1, 3, 1, 1);
		fieldLayout.setAlignment(Pos.CENTER);
	}
	public void initView() {
		this.setAlignment(Pos.CENTER);
		//this.getChildren().addAll(programTitle, fieldLayout, logInButton, newUserButton, forgottenPasswordButton);
		this.getChildren().addAll(programTitle, fieldLayout);
	}
}
