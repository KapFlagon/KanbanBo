package entities.ui.views;

import entities.ui.custom_components.shared.AvatarSelector;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javax.swing.text.html.ImageView;

public class View_NewUser extends VBox {

	private Label viewTitle;
	private GridPane fieldLayout;
	private Label usernameLabel;
	private TextField usernameField;
	private Label passwordLabel;
	private PasswordField passwordField;
	private Label fNameLabel;
	private TextField fNamelField;
	private Label lNameLabel;
	private TextField lNameField;
	private Label emailLabel;
	private TextField emailField;
	private Label avatarLabel;
	private AvatarSelector avatarSelector;
	private ImageView avatarImageView;
	private Button createUserButton;


	public View_NewUser() {
		initViewTitle();
		initUsernameLabel();
		initUsernameField();
		initPasswordLabel();
		initPasswordField();
		initFnameLabel();
		initFnameField();
		initLnameLabel();
		initLnameField();
		initEmailLabel();
		initEmailField();
		initAvatarLabel();
		initAvatarSelector();
		initCreateUserButton();
		initFieldLayout();
		initView();
	}


	// All constructors
	public Label getViewTitle() {
		return viewTitle;
	}
	public void setViewTitle(Label viewTitle) {
		this.viewTitle = viewTitle;
	}
	public GridPane getLayout() {
		return fieldLayout;
	}
	public void setFieldLayout(GridPane layout) {
		this.fieldLayout = layout;
	}
	public Label getUsernameLabel() {
		return usernameLabel;
	}
	public void setUsernameLabel(Label usernameLabel) {
		this.usernameLabel = usernameLabel;
	}
	public TextField getUsernameField() {
		return usernameField;
	}
	public void setUsernameField(TextField usernameField) {
		this.usernameField = usernameField;
	}
	public Label getfNameLabel() {
		return fNameLabel;
	}
	public void setfNameLabel(Label fNameLabel) {
		this.fNameLabel = fNameLabel;
	}
	public TextField getfNamelField() {
		return fNamelField;
	}
	public void setfNamelField(TextField fNamelField) {
		this.fNamelField = fNamelField;
	}
	public Label getlNameLabel() {
		return lNameLabel;
	}
	public void setlNameLabel(Label lNameLabel) {
		this.lNameLabel = lNameLabel;
	}
	public TextField getlNameField() {
		return lNameField;
	}
	public void setlNameField(TextField lNameField) {
		this.lNameField = lNameField;
	}
	public Label getEmailLabel() {
		return emailLabel;
	}
	public void setEmailLabel(Label emailLabel) {
		this.emailLabel = emailLabel;
	}
	public TextField getEmailField() {
		return emailField;
	}
	public void setEmailField(TextField emailField) {
		this.emailField = emailField;
	}
	public Label getAvatarLabel() {
		return avatarLabel;
	}
	public void setAvatarLabel(Label avatarLabel) {
		this.avatarLabel = avatarLabel;
	}
	public AvatarSelector getAvatarSelector() {
		return avatarSelector;
	}
	public void setAvatarSelector(AvatarSelector avatarSelector) {
		this.avatarSelector = avatarSelector;
	}
	public ImageView getAvatarImageView() {
		return avatarImageView;
	}
	public void setAvatarImageView(ImageView avatarImageView) {
		this.avatarImageView = avatarImageView;
	}
	public Button getCreateUserButton() {
		return createUserButton;
	}
	public void setCreateUserButton(Button createUserButton) {
		this.createUserButton = createUserButton;
	}
	public Label getPasswordLabel() {
		return passwordLabel;
	}
	public void setPasswordLabel(Label passwordLabel) {
		this.passwordLabel = passwordLabel;
	}
	public PasswordField getPasswordField() {
		return passwordField;
	}
	public void setPasswordField(PasswordField passwordField) {
		this.passwordField = passwordField;
	}

	private void initViewTitle() {
		viewTitle = new Label("Register New User");
		viewTitle.setStyle("-fx-font: 40px Verdana;");
	}

	private void initUsernameLabel() {
		usernameLabel = new Label("Username: ");
	}

	private void initUsernameField() {
		usernameField = new TextField();
		usernameField.setPromptText("Username");
	}

	private void initPasswordLabel() {
		passwordLabel = new Label("Password: ");
	}

	private void initPasswordField() {
		passwordField = new PasswordField();
		passwordField.setPromptText("Password");
	}

	private void initFnameLabel() {
		fNameLabel = new Label("First Name: ");
	}

	private void initFnameField() {
		fNamelField = new TextField();
		fNamelField.setPromptText("First Name");
	}

	private void initLnameLabel() {
		lNameLabel = new Label("Last Name: ");
	}

	private void initLnameField() {
		lNameField = new TextField();
		lNameField.setPromptText("Last Name");
	}

	private void initEmailLabel() {
		emailLabel = new Label("Email: ");
	}

	private void initEmailField() {
		emailField = new TextField();
		emailField.setPromptText("EMail address");
	}

	private void initAvatarLabel() {
		avatarLabel = new Label("Upload Avatar");
	}

	private void initCreateUserButton() {
		createUserButton = new Button("Create User");
	}

	private void initFieldLayout() {
		fieldLayout = new GridPane();
		fieldLayout.add(usernameLabel,0,0,1,1);
		fieldLayout.add(usernameField,1,0,1,1);
		fieldLayout.add(passwordLabel,0,1,1,1);
		fieldLayout.add(passwordField,1,1,1,1);
		fieldLayout.add(fNameLabel,0,2,1,1);
		fieldLayout.add(fNamelField,1,2,1,1);
		fieldLayout.add(lNameLabel,0,3,1,1);
		fieldLayout.add(lNameField,1,3,1,1);
		fieldLayout.add(emailLabel,0,4,1,1);
		fieldLayout.add(emailField,1,4,1,1);
		fieldLayout.add(avatarLabel,0,5,1,1);
		fieldLayout.add(avatarSelector,1,5,1,1);
		fieldLayout.setAlignment(Pos.CENTER);
	}
	private void initAvatarSelector() {
		avatarSelector = new AvatarSelector();
	}

	private void initView() {
		this.getChildren().addAll(viewTitle, fieldLayout, createUserButton);
		this.setAlignment(Pos.CENTER);
	}
}
