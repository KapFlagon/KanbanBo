package entities.ui.custom_components.shared;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.InputStream;

public abstract class TempTile {

	private TextArea textArea;
	private Button addButton;
	private Button cancelButton;

	public TempTile() {
		initializeAddButtonDisplay();
		initializeCancelButtonIcon();
	}

	public TextArea getTextArea() {
		return textArea;
	}
	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}
	public Button getAddButton() {
		return addButton;
	}
	public void setAddButton(Button addButton) {
		this.addButton = addButton;
	}
	public Button getCancelButton() {
		return cancelButton;
	}
	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void initializeCancelButtonIcon() {
		try{
			String templateIconPath = "src/assets/icons/cancel/ic_clear_black_24dp.png";
			InputStream inputStream = new FileInputStream(templateIconPath);
			Image image = new Image(inputStream, 20, 20, true, false);
			ImageView imageView = new ImageView(image);
			cancelButton.setGraphic(imageView);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}

	public void initializeAddButtonDisplay() {
		/*
		Success button (save, add, etc.) colour:
		5AAC44  rgb: (90, 172, 68, 1.0) Dark moderate lime green.
		 */
		Color backgroundColour = Color.rgb(90, 172, 68, 1.0);
		CornerRadii cornerRadii = new CornerRadii(2);
		Insets insets = new Insets(1,1,1,1);
		addButton.setBackground(new Background(new BackgroundFill(backgroundColour, cornerRadii, insets)));
	}

}
