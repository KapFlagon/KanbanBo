package entities.ui.custom_components.menus;

import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UserIconButton extends MenuButton {


	private StackPane stackPane;
	private Circle circle;
	private String userInitialsString;
	private Label initialsLabel;
	private final Color bgColour = Color.web("DFE1E6");
	private MenuItem profile;
	private MenuItem activityLog;
	private MenuItem settings;
	private MenuItem logoff;


	public UserIconButton(String userInitials) {
		setUserInitialsString(userInitials);
		initCircle();
		initStackPane();
		initMenuItems();
		initDisplay();
	}


	public void setUserInitialsString(String userInitials) {
		this.userInitialsString = userInitials;
		initLabel();
	}


	private void initLabel() {
		initialsLabel = new Label(userInitialsString);
		initialsLabel.setStyle("-fx-font-weight: Bold");
	}

	private void initCircle() {
		circle = new Circle();
		circle.setRadius(18.0f);
		circle.setFill(bgColour);
	}

	private void initStackPane() {
		stackPane = new StackPane();
	}

	private void initMenuItems() {
		profile = new MenuItem("Profile");
		activityLog = new MenuItem("Activity Log");
		settings = new MenuItem("Settings");
		logoff = new MenuItem("Log Off");
	}

	private void initDisplay() {
		stackPane.getChildren().add(circle);
		stackPane.getChildren().add(initialsLabel);
		this.setGraphic(stackPane);
		this.getItems().add(profile);
		this.getItems().add(activityLog);
		this.getItems().add(settings);
		this.getItems().add(logoff);
	}



}
