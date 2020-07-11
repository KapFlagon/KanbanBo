package entities.ui.custom_components.board;

import entities.ui.custom_components.shared.Tile;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;

public class BoardTile extends Tile {

	// Variables
	private Label titleLabel;
	private boolean favourite;
	private StackPane stackPane;
	private Group favouriteGroup;
	private Image favouriteIcon;
	private FileInputStream inputStream;


	// Constructors
	public BoardTile() {
		stackPane = new StackPane();
		stackPane.setPrefSize(200,100);
		initializeTitleLabel("Dummy Mini Board");
		setColour(BoardColours.getRandomColour());
		listPosition = 1;
		setFavourite(false);
		initializeIcons();
		initializeView();
	}


	public BoardTile(String newTitle, Color newColour, int newPosition, boolean newFavourite) {
		stackPane = new StackPane();
		stackPane.setPrefSize(200,100);
		initializeTitleLabel(newTitle);
		setColour(newColour);
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

	@Override
	public void setColour(Color newColour) {
		colour = newColour;
		stackPane.setBackground(new Background(new BackgroundFill(colour, new CornerRadii(7), new Insets(1,1,1,1))));
	}

	// Other methods
	@Override
	protected void initializeView() {
		stackPane.getChildren().clear();
		this.getChildren().clear();
		stackPane.getChildren().addAll(titleLabel, favouriteGroup);
		this.getChildren().add(stackPane);
	}

	protected void initializeIcons() {
		favouriteGroup = new Group();
		ImageView imageView;
		StackPane.setAlignment(favouriteGroup, Pos.BOTTOM_RIGHT);
		String path;
		if (favourite) {
			path = "src/assets/icons/favourite/ic_star_white_18dp.png";
		} else {
			path = "src/assets/icons/favourite/ic_star_border_white_18dp.png";
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

	public void initializeTitleLabel(String text) {
		setTitleText(text);
		titleLabel = new Label(titleText);
		StackPane.setAlignment(titleLabel, Pos.TOP_LEFT);
		titleLabel.setStyle("-fx-font-weight: bold");
		titleLabel.setPadding(new Insets(3,3,3,3));
		titleLabel.setTextFill(Color.rgb(255,255,255,1.0));
	}

}