package entities.ui.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class StartScreen_View extends BorderPane implements I_View{

	private VBox centerBox;
	private Stage ownerStage;
	private Label titleLabel;
	private Label userActionLabel;
	private Button createFileBtn;
	private Button openFileBtn;
	private Label recentLabel;
	private Label noRecentLabel;
	private ArrayList<Button> recentItemButtons;
	private FileChooser fileChooser;

	public StartScreen_View(Stage ownerStage) {
		initViewView(ownerStage);

		initNoRecentLabel();
		centerBox.getChildren().add(noRecentLabel);
	}

	public StartScreen_View(Stage ownerStage, String[] recentItemsNames) {
		initViewView(ownerStage);

		parseRecentItems(recentItemsNames);
		for (Button button : recentItemButtons) {
			centerBox.getChildren().add(button);
		}
	}

	public Label getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}

	public Button getCreateFileBtn() {
		return createFileBtn;
	}

	public void setCreateFileBtn(Button createFileBtn) {
		this.createFileBtn = createFileBtn;
	}

	public Button getOpenFileBtn() {
		return openFileBtn;
	}

	public void setOpenFileBtn(Button openFileBtn) {
		this.openFileBtn = openFileBtn;
	}

	public Label getRecentLabel() {
		return recentLabel;
	}

	public void setRecentLabel(Label recentLabel) {
		this.recentLabel = recentLabel;
	}

	public ArrayList<Button> getRecentItemButtons() {
		return recentItemButtons;
	}

	public void setRecentItemButtons(ArrayList<Button> recentItemButtons) {
		this.recentItemButtons = recentItemButtons;
	}

	private void initViewView(Stage ownerStage) {
		this.ownerStage = ownerStage;
		initFileChooser();
		initTitleLabel();
		initUserActionLabel();
		initCreateFileBtn();
		initOpenFileBtn();
		initRecentLabel();
		initCenterBox();
		updateBaseDisplay();
	}

	private void initCenterBox() {
		centerBox = new VBox();
		centerBox.setSpacing(3);
		centerBox.setAlignment(Pos.CENTER);
	}

	private void initTitleLabel() {
		titleLabel = new Label("KanbanBo");
		titleLabel.setStyle("-fx-font: 40px Verdana;");
		titleLabel.setAlignment(Pos.CENTER);
	}

	private void initUserActionLabel() {
		userActionLabel = new Label("Select an action:");
	}

	private void initCreateFileBtn() {
		createFileBtn = new Button("Create new database");
		createFileBtn.setOnAction(event -> {
			File newfile = fileChooser.showSaveDialog(ownerStage);
			// TODO Create empty db file with provided data.
			// TODO Prompt User to input their name
			System.out.println("Create a file");
		});
	}

	private void initOpenFileBtn() {
		openFileBtn = new Button("Open file");
		openFileBtn.setOnAction(event -> {
			File file = fileChooser.showOpenDialog(ownerStage);
			if (file != null) {
				// TODO validate file, then open file
				System.out.println("Valid file");
			}
		});
	}

	private void initRecentLabel() {
		recentLabel = new Label("Recent files:");
	}

	private void initNoRecentLabel() {
		noRecentLabel = new Label("No recent items");
	}

	private void parseRecentItems(String[] recentItemsNames) {
		recentItemButtons = new ArrayList<Button>();
		for (String itemName : recentItemsNames) {
			System.out.println(itemName);
			Button temp = new Button(itemName);
			temp.setOnAction(event -> {
				// TODO Open file, search db based on file name.
				System.out.println("File " + itemName + " selected");
			});
			recentItemButtons.add(temp);
		}
	}

	private void initFileChooser() {
		fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DB files (*.db)", "*.db");
		fileChooser.getExtensionFilters().add(extensionFilter);
	}

	public void updateBaseDisplay() {
		this.setTop(titleLabel);
		BorderPane.setAlignment(titleLabel, Pos.CENTER);
		centerBox.getChildren().add(userActionLabel);
		centerBox.getChildren().add(createFileBtn);
		centerBox.getChildren().add(openFileBtn);
		centerBox.getChildren().add(recentLabel);
		this.setCenter(centerBox);
	}
	
}
