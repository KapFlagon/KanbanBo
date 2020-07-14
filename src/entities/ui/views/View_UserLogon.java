package entities.ui.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class View_UserLogon extends VBox {

	private Label programTitle;
	private Label usernameLabel;
	private TextField usernameTextField;
	private Label passwordLabel;
	private PasswordField passwordField;
	private Button logInButton;
	private Button newUserButton;
	private Button forgottenPasswordButton;
	private GridPane fieldLayout;


	public View_UserLogon() {
		programTitle = new Label("KanbanBo");
		usernameLabel = new Label("Username: ");
		usernameTextField = new TextField();
		usernameTextField.setPromptText("Username");

		passwordLabel = new Label("Password: ");
		passwordField = new PasswordField();
		passwordField.setPromptText("Password");

		logInButton = new Button("Log in");
		logInButton.setAlignment(Pos.CENTER);
		newUserButton = new Button("New User");
		newUserButton.setAlignment(Pos.CENTER);
		forgottenPasswordButton = new Button("Forgotten Password");
		forgottenPasswordButton.setAlignment(Pos.CENTER);
		fieldLayout = new GridPane();
		fieldLayout.add(usernameLabel,0,0,1,1);
		fieldLayout.add(usernameTextField,1,0,1,1);
		fieldLayout.add(passwordLabel,0,1,1,1);
		fieldLayout.add(passwordField,1,1,1,1);
		fieldLayout.setAlignment(Pos.CENTER);

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(programTitle, fieldLayout, logInButton, newUserButton, forgottenPasswordButton);
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
}
