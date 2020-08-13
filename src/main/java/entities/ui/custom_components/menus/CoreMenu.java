package entities.ui.custom_components.menus;

import javafx.scene.layout.FlowPane;

public class CoreMenu extends FlowPane {

	private HomeButton homeBtn;
	private CreateButton createBtn;
	private UserIconButton userIconBtn;

	public CoreMenu() {
		initDimensions();
		initButtons();
		updateDisplay();
	}

	public CoreMenu(String userInitials) {
		initDimensions();
		initButtons();
		updateUserInitials(userInitials);
		updateDisplay();
	}

	public void updateUserInitials(String initials) {
		this.userIconBtn.setUserInitialsString(initials);
		this.userIconBtn.updateDisplay();
		updateDisplay();
	}


	private void initButtons() {
		homeBtn = new HomeButton();
		createBtn = new CreateButton();
		userIconBtn = new UserIconButton("");
	}

	public void updateDisplay() {
		this.getChildren().clear();
		this.getChildren().add(homeBtn);
		this.getChildren().add(createBtn);
		this.getChildren().add(userIconBtn);
	}

	private void initDimensions() {
		this.setHgap(5);
		//this.setHeight(10);
	}
	
}
