package entities.ui.custom_components.shared;

import entities.ui.custom_components.utils.ImageHelper;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class AvatarSelector extends StackPane {

	private final String defaultAvatar = "src/assets/icons/user_team/ic_perm_identity_black_18dp.png";
	private Stage owner;
	private ImageView avatar;
	private FileChooser fileChooser;


	public AvatarSelector() {
		setAvatar(defaultAvatar);
		initFileChooser();
		initDisplay();
	}

	public AvatarSelector(String imagePath) {
		initImage(imagePath);
		initFileChooser();
	}


	public ImageView getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatarPath) {
		try {
			avatar = ImageHelper.parseImagePath(avatarPath);
		} catch (Exception e) {
			System.out.println("Error using ImageHelper.\n" + e.getMessage());
		}
	}

	public void setOwner(Stage owner) {
		this.owner = owner;
	}


	private void initImage(String imagePath) {
		setAvatar(imagePath);
	}

	private void initFileChooser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Select Avatar file");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", ".jpg", "*.gif"));
	}

	private void initDisplay() {
		this.getChildren().clear();
		this.getChildren().add(avatar);
		this.setMaxSize(20,20);
		this.setPrefSize(20,20);
		this.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}

	public void showFileChooser() {
		File tempFile = fileChooser.showOpenDialog(owner);
		if (tempFile != null) {
			initImage(tempFile.getPath());
			initDisplay();
		}
	}

}
