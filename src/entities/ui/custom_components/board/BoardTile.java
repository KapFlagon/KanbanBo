package entities.ui.custom;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;

public class BoardTile extends Tile {

	// Variables
	private boolean favourite;
	private StackPane stackPane;
	private Group favouriteGroup;
	private Image favouriteIcon;
	private FileInputStream inputStream;


	// Constructors
	public BoardTile() {
		initializeTitle("Dummy Mini Board");
		StackPane.setAlignment(title, Pos.TOP_LEFT);
		setColour(new Color(0.5, 0, 0, 1));
		initializeRect(colour);
		listPosition = 1;
		setFavourite(false);
		initializeIcons();
		initializeView();
	}


	public BoardTile(String newTitle, Color newColour, int newPosition, boolean newFavourite) {
		initializeTitle(newTitle);
		StackPane.setAlignment(title, Pos.TOP_LEFT);
		colour = newColour;
		initializeRect(colour);
		listPosition = newPosition;
		setFavourite(newFavourite);
		initializeIcons();
		initializeView();
	}


	// Getters and Setters
	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public StackPane getStackPane() {
		return stackPane;
	}

	public void setStackPane(StackPane new_stackPane) {
		this.stackPane = new_stackPane;
	}


	// Other methods
	@Override
	protected void initializeView() {
		stackPane = new StackPane();
		stackPane.getChildren().addAll(rectangle, title, favouriteGroup);
		this.getChildren().add(stackPane);
	}

	protected void initializeIcons() {
		favouriteGroup = new Group();
		ImageView imageView;
		StackPane.setAlignment(favouriteGroup, Pos.BOTTOM_RIGHT);
		String path;
		if (favourite) {
			path = "src/assets/icons/ic_star_black_18dp.png";
		} else {
			path = "src/assets/icons/ic_star_border_black_18dp.png";
		}
		try {
			inputStream = new FileInputStream(path);
			favouriteIcon = new Image(inputStream);
			imageView = new ImageView(favouriteIcon);
			imageView.setPickOnBounds(true);
			if (favourite) {
				imageView.setOpacity(1);
			} else {
				imageView.setOpacity(0);
			}

			imageView.setOnMouseEntered(evnt -> {
				if (!favourite) {
					imageView.setOpacity(1);
				}
			});
			imageView.setOnMouseExited(evnt -> {
				if (!favourite) {
					imageView.setOpacity(0);
				}
			});
			imageView.setOnMouseClicked(evnt -> {
				System.out.println("clicked image. pre-click state: " + favourite);
				if (favourite) {
					setFavourite(false);
				} else {
					setFavourite(true);
				}
				stackPane.getChildren().removeAll();
				initializeIcons();
				initializeView();
			});
			favouriteGroup.getChildren().add(imageView);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

	}

}