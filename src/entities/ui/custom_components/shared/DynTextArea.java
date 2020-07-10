package entities.ui.custom_components.shared;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class DynTextArea extends TextArea {

	private Color activeBackgroundColour;
	private Color inactiveBackgroundColour;
	private CornerRadii cornerRadii;
	private Insets backgroundInsets;

	public DynTextArea() {
		initialize();
	}
	public DynTextArea(String text) {
		initialize();
		this.setText(text);
	}


	public Color getActiveBackgroundColour() {
		return activeBackgroundColour;
	}
	public void setActiveBackgroundColour(Color activeBackgroundColour) {
		this.activeBackgroundColour = activeBackgroundColour;
	}
	public Color getInactiveBackgroundColour() {
		return inactiveBackgroundColour;
	}
	public void setInactiveBackgroundColour(Color inactiveBackgroundColour) {
		this.inactiveBackgroundColour = inactiveBackgroundColour;
	}
	public CornerRadii getCornerRadii() {
		return cornerRadii;
	}
	public void setCornerRadii(CornerRadii cornerRadii) {
		this.cornerRadii = cornerRadii;
	}
	public void setCornerRadii(int radii) {
		this.cornerRadii = new CornerRadii(radii);
	}
	public Insets getBackgroundInsets() {
		return backgroundInsets;
	}
	public void setBackgroundInsets(Insets backgroundInsets) {
		this.backgroundInsets = backgroundInsets;
	}
	public void setBackgroundInsets(int v1, int v2, int v3, int v4) {
		this.backgroundInsets = new Insets(v1, v2, v3, v4);
	}


	private void initialize() {
		this.setWrapText(true);
		this.setPrefRowCount(1);
		this.setPromptText("");
		activeBackgroundColour = Color.rgb(235, 236, 240, 1.0);
		//inactiveBackgroundColour = Color.rgb(235, 236, 240, 0.0);
		cornerRadii = new CornerRadii(0);
		backgroundInsets = new Insets(0,0,0,0);
		this.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			focusState(newValue);
		});


	}
	private void focusState(boolean value) {
		if (value) {
			System.out.println("Focus Gained");
			//this.setBackground(new Background(new BackgroundFill(activeBackgroundColour, cornerRadii, backgroundInsets)));
			//this.setStyle("-fx-background-color: rgba(235, 236, 240, 1.0)");
			Region content = (Region) this.lookup(".content");
			content.setStyle("-fx-background-color: rgba(118,240,76,1.0)");
		}
		else {
			System.out.println("Focus Lost");
			//this.setBackground(new Background(new BackgroundFill(inactiveBackgroundColour, cornerRadii, backgroundInsets)));
			//this.setStyle("-fx-background-color: rgba(235, 236, 240, 0.0)");
			//this.setStyle("-fx-background-color: rgba(138,171,240,1.0)");
			Region content = (Region) this.lookup(".content");
			//content.setStyle("-fx-background-color: transparent");
			//content.setStyle("-fx-background-color: rgba(138,171,240,1.0)");

			System.out.println("-fx-background-color: rgba(" + inactiveBackgroundColour.getRed() + ", " + inactiveBackgroundColour.getGreen() + ", " + inactiveBackgroundColour.getBlue() + ", 1.0)");
			content.setStyle("-fx-background-color: rgba(" + inactiveBackgroundColour.getRed() + ", " + inactiveBackgroundColour.getGreen() + ", " + inactiveBackgroundColour.getBlue() + ", 1.0)");
		}
	}

}
