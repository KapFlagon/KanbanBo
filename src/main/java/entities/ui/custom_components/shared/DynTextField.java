package entities.ui.custom_components.shared;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class DynTextField extends TextField {

	private Color activeBackgroundColour;
	private Color inactiveBackgroundColour;
	private CornerRadii cornerRadii;
	private Insets backgroundInsets;
	private boolean borderOn;

	public DynTextField() {
		initialize();
	}
	public DynTextField(String text) {
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
		System.out.println("Post set inactive colour: " + inactiveBackgroundColour.toString());
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
	public boolean getBorderOn() {
		return borderOn;
	}
	public void setBorderOn(boolean borderOn) {
		this.borderOn = borderOn;
	}


	private void initialize() {
		this.setPromptText("");
		activeBackgroundColour = Color.rgb(255, 255, 255, 1.0);
		inactiveBackgroundColour = Color.rgb(235, 236, 240, 1.0);
		cornerRadii = new CornerRadii(0);
		backgroundInsets = new Insets(0,0,0,0);
		this.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			focusState(newValue);
		});
		focusState(false);

	}
	private void focusState(boolean value) {
		String tempColour;
		if (value) {
			System.out.println("Focus Gained");
			// Sets background colour for text box.
			this.setBackground(new Background(new BackgroundFill(activeBackgroundColour, cornerRadii, backgroundInsets)));
		}
		else {
			System.out.println("Focus Lost");
			// Sets background colour for text box.
			this.setBackground(new Background(new BackgroundFill(inactiveBackgroundColour, cornerRadii, backgroundInsets)));
		}
	}

}
