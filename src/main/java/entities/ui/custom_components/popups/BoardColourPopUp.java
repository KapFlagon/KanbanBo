package entities.ui.custom_components.popups;

import entities.ui.custom_components.board.BoardColours;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class BoardColourPopUp {

	private FlowPane layout;
	private ArrayList<StackPane> rectUnits;
	private Image selectedIconImage;
	private ImageView selectedIconImageView;

	public BoardColourPopUp() {
		layout = new FlowPane();
		layout.setHgap(3);
		layout.setVgap(3);

		initializeRectangles();
	}

	public void initializeRectangles() {
		for (BoardColours bc : BoardColours.values()) {
			// Using a stack to future proof selection icon
			StackPane tempStack = new StackPane();
			Rectangle rect = new Rectangle();
			rect.setArcHeight(2);
			rect.setArcWidth(2);
			rect.setFill(bc.getColour());
			tempStack.getChildren().add(rect);
			/*tempStack.setOnMouseClicked(event -> {
				bc.getColour();
			});*/
			layout.getChildren().add(tempStack);
		}
	}
}
